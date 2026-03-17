package org.interview.predicate;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
