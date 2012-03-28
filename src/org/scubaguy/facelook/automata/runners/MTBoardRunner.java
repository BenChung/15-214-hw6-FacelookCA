package org.scubaguy.facelook.automata.runners;

import org.scubaguy.facelook.CellularAutomata;
import org.scubaguy.facelook.rules.Rule;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.rules.ThreadableRule;
import org.scubaguy.facelook.runners.BoardRunner;
import org.scubaguy.facelook.runners.RunnerFactory;
import org.scubaguy.facelook.runners.RunnerStrictness;

import java.util.*;
import java.util.concurrent.*;

/**
 * A multithreaded implementation of BoardRunner
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class MTBoardRunner implements BoardRunner {
    private ExecutorService execService = Executors.newCachedThreadPool();
    private CellularAutomata ca;
    private Rule rule;

    private static class RFactory implements RunnerFactory {
        @Override
        public boolean isSuitable(Rule rule) {
            return rule instanceof ThreadableRule;
        }

        @Override
        public RunnerStrictness getStrictness() {
            return RunnerStrictness.MEDIUM;
        }

        @Override
        public BoardRunner buildRunner(CellularAutomata ca, Rule rule) {
            return new MTBoardRunner(ca, (ThreadableRule)rule);
        }
    }

    public static RunnerFactory getFactory() {
        return new RFactory();
    }

    public MTBoardRunner(CellularAutomata ca, ThreadableRule rule) {
        this.ca = ca;
        this.rule = rule;
    }

    public void tick(int n) {
        Board from = ca.getTop();
        EditableBoard to = ca.getBottom();

        int w = from.getWidth()/n;
        int h = from.getHeight()/n;

        LinkedList<Callable<Integer>> toExec = new LinkedList<Callable<Integer>>();

        for (int i = 0; i <= from.getWidth()/w; i++) {
            for (int j = 0; j <= from.getHeight()/h; j++) {
                int startY = j * h;
                int startX = i * w;
                int endY = ((j+1)*h < from.getHeight())?(j + 1) * h:(from.getHeight()/h)*h + from.getHeight()%h;
                int endX = ((i+1)*w < from.getWidth())?(i + 1) * w:(from.getWidth()/w)*w + from.getWidth()%w;
                toExec.add(new RuleExecuter(from, to, new Region(startY, endY, startX, endX), rule));
            }
        }
        try {
            execService.invokeAll((Collection<Callable<Integer>>)toExec);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    
    private class KV implements Comparable<KV> {
        public int dNum;
        public long time;
        public KV(int dNum) {
            this.dNum = dNum;
        }
        @Override
        public int compareTo(KV o) {
            return (int) (o.time - time);
        }
    }
    private BlockingQueue<KV> toProc = new LinkedBlockingQueue<KV>();
    private BlockingQueue<KV> pQ = new PriorityBlockingQueue<KV>();
    
    @Override
    public void tick() {
        if (toProc.size() == 0) {
            int best = (pQ.size() > 0)?pQ.peek().dNum:16;
            Random randGen = new Random();
            for (int i = 0; i < 16; i++) {
                int n = randGen.nextInt(10) - 5 + best;
                n = (n < 1)?1:n;
                toProc.add(new KV(n));
            }
        }
        KV timing = toProc.poll();
        long hpTime = System.nanoTime();
        tick(timing.dNum);
        timing.time = System.nanoTime() - hpTime;
        pQ.add(timing);
    }

    private class Region {
        private int top;
        private int bottom;
        private int right;
        private int left;


        public Region(int top, int bottom, int left, int right) {
            if (bottom < top || left > right || top < 0 || bottom < 0 || left < 0 || right < 0) {
                throw new RuntimeException();
            }
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
        }

        public int getTop() {
            return top;
        }

        public int getBottom() {
            return bottom;
        }

        public int getRight() {
            return right;
        }

        public int getLeft() {
            return left;
        }

        public int getWidth() {
            return right-left;
        }


        public int getHeight() {
            return bottom-top;
        }

        public int remapX(int x) {
            return left + x;
        }

        public int remapY(int y) {
            return top + y;
        }
    }

    private class RuleExecuter implements Callable<Integer> {
        private Board from;
        private EditableBoard to;
        private Region region;
        private Rule rule;

        public RuleExecuter(Board from, EditableBoard to, Region region, Rule rule) {
            this.from = from;
            this.to = to;
            this.region = region;
            this.rule = rule;

        }

        @Override
        public Integer call() throws Exception {
            for (int i = 0; i < region.getWidth(); i++)
                for (int j = 0; j < region.getHeight(); j++)
                    to.setState(rule.getState(from, region.remapX(i), region.remapY(j)), region.remapX(i),region.remapY(j));
            return 0;
        }
    }
}
