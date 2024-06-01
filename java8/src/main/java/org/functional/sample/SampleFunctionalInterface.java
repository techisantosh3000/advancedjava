package org.functional.sample;

@FunctionalInterface
public interface SampleFunctionalInterface {
    void abstractFun(int x);

    default void normalFun() {
        System.out.println("Hello");
    }
}
