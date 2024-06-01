package org.functional.sample;

import java.util.ArrayList;

@FunctionalInterface
interface FunInt1{
    int operation(int a, int b);
}

public class SampleTest {

    @FunctionalInterface
    interface FunInt2{
       void sayMessage(String message);
    }

    public static void main(String[] args) {

        SampleFunctionalInterface sample = (int x) -> System.out.println(2 * x);
        sample.abstractFun(5);
        sample.abstractFun(100);

        SampleFunctionalInterface sample1 = (int y) -> System.out.println(2000 * y);
        sample1.abstractFun(-1);

        ArrayList<Integer> arrL = new ArrayList<>();
        arrL.add(1);
        arrL.add(2);
        arrL.add(3);
        arrL.add(4);

        arrL.forEach(n -> System.out.println(n));

        System.out.println("------------------");

        arrL.forEach(n -> {
            if (n % 2 == 0) System.out.println(n);
        });

        System.out.println("------------------");

        

        System.out.println("------------------");

    }
}
