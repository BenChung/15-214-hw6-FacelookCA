package org.scubaguy.facelook.rules;

/**
 *
 * This interface allows for the creation of threaded rules. If you don't know what that means, DO NOT USE THIS. Use Rule instead, and the world will be a better place.
 * If you write anything using this, make sure it doesn't do anything that would be thread unsafe. If it is unsafe, then runtime behavior is non-deterministic. IMPLEMENT AT YOUR OWN PERIL
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public interface ThreadableRule extends Rule {
}
