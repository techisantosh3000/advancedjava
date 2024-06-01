package org.ds.learningyard;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LearningYardRunner {
    public static void main(String[] args) {
        findSmallestAndSecondSmallest();
    }

    static void printEvenNumbers() {

    }

    static void printEvenNumbersDivisibleBy7() {

    }

    static void fizzBuzz() {
        List<Integer> dataSet = Arrays.asList(0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        for (int e : dataSet) {
            if (e % 3 == 0 && e % 5 == 0) {
                System.out.println("FIZZBUZZ");
            } else if (e % 3 == 0) {
                System.out.println("FIZZ");
            } else if (e % 5 == 0) {
                System.out.println("BUZZ");
            } else {
                System.out.println(e);
            }
        }//for
    }//fizzbuzz

    static void preAndPostIncrement() {
        int i = 0;
        System.out.println(++i);
        System.out.println(++i);
        System.out.println(++i);

        System.out.println("BREAKING BAD");

        int j = 0;
        System.out.println(j++);
        System.out.println(j++);
        System.out.println(j++);
    }

    static void calculateDigit(int input) {
        int digit = 0;
        for (int n = input; n > 0; n = n / 10) {
            ++digit;
        }
        System.out.println("Number of digits for input "+digit);
    }

    static void calculateSumOfDigitUpToN(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(calculateSumOfDigit(i));
        }
    }

    static void calculateSumAsSixForFirstTenNumbers(int N, int K) {
        // wrong
        int[] arr = {6, 15, 24, 33, 42, 51, 60, 105, 114, 123};
        System.out.println("Length : "+arr.length);

        

    }

    static int calculateSumOfDigit(int input) {
        int sum = 0;
        while (input != 0) {
            int d = input % 10;
            sum = sum + d;
            input = input / 10;
        }

        return sum;
    }
    static void calculateCharacter(String input){

    }

    //10-01-2024
    static void checkIfNumberIsPowerOfTwo(){

    }

    static void findLargestCommonDivisor(){

    }

    static void findLeastCommonDivisor(){
        
    }

    // Arrays
    // 1.Find smallest and second smallest
    public static void findSmallestAndSecondSmallest(){
        int[] input = {1,-5,10,15,0,0,0};
        Arrays.sort(input);
        Arrays.stream(input).forEach(e -> System.out.print(e+" "));
    }

    public static void findSmallestAndSecondSmallestWithoutSorting(){
        int[] input = {1,-5,10,15,0,0,0};
        Arrays.sort(input);
        Arrays.stream(input).forEach(e -> System.out.print(e+" "));
    }

    public static void deDuplicateArray(int[] input){

    }
}
