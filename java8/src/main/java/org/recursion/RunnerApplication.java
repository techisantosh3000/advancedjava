package org.recursion;

public class RunnerApplication {
    public static void main(String[] args) {
        // System.out.println("Factorial of 5 =  "+findFactorial(3));
        // print1ToN(5, 1);
        // printNTo1(5);
        // print1ToN(10);
        // fibbo(5);
        // System.out.println("val power(2,3) = "+power(2,4));
        System.out.println(negativePower(2, -3));
        System.out.println(negativePowerInbuilt(2, -3));
    }

    public static int findFactorial(int input) {
        if (input == 1) {
            return 1;
        }
        return input * findFactorial(input - 1);
    }

    public static void print1ToN(int end, int start) {
        if (start == end) {
            System.out.print(end + "===");
            // stack overflow without below statement
            //return;
        }
        System.out.print(start + ",");
        print1ToN(end, start + 1);
    }

    public static void print1ToN(int end) {
        if (end == 0) {
            return;
        }
        print1ToN(end - 1);
        System.out.println(end);
    }

    public static void printNTo1(int end) {
        if (end == 1) {
            System.out.println(1);
            return;
        }
        System.out.println(end);
        printNTo1(end - 1);

    }

    public static int fibbo(int n) {
        if (n < 1) {
            return n;
        }

        return fibbo(n - 1) + fibbo(n - 2);
    }

    // for positive cases only.
    public static int power(int a, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return a;
        }

        return a * power(a, n - 1);
    }

    // Failed
    public static double negativePower(int a, int n) {
        if (n == 0) {
            return 1;
        }

        return (double) 1 / (negativePower(a, Math.abs(n) - 1));
    }

    public static double negativePowerInbuilt(int a, int n) {
        return Math.pow(a, n);
    }

    public static boolean ifNumberIsPowerOf3(int num) {
        return false;
    }

    public static void sorting(){

    }


}
