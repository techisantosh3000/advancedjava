package org.interview.predicate;

@FunctionalInterface
public interface Function<T,R>{
    R apply(T t);
}
