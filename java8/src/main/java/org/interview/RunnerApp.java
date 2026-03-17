package org.interview;


import org.interview.model.Employee;
import org.interview.model.Apple;
import org.interview.predicate.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * Youtube reference : airhacks.news
 * Book reference : Java 8 in Action
 */

/**
 * Note
 * Lambda func can be used in the context of FI.
 * Lambda is implementation for concrete method.
 */
public class RunnerApp {
    // Data set.
    static List<Employee> employeeList = new ArrayList<Employee>();
    static List<Apple> appleList;

    static {
        employeeList.add(new Employee(111, "Jiya Brein", 32, "Female", "HR", 2011, 25000.0));
        employeeList.add(new Employee(122, "Paul Niksui", 25, "Male", "Sales And Marketing", 2015, 13500.0));
        employeeList.add(new Employee(133, "Martin Theron", 29, "Male", "Infrastructure", 2012, 18000.0));
        employeeList.add(new Employee(144, "Murali Gowda", 28, "Male", "Product Development", 2014, 32500.0));
        employeeList.add(new Employee(155, "Nima Roy", 27, "Female", "HR", 2013, 22700.0));
        employeeList.add(new Employee(166, "Iqbal Hussain", 43, "Male", "Security And Transport", 2016, 10500.0));
        employeeList.add(new Employee(177, "Manu Sharma", 35, "Male", "Account And Finance", 2010, 27000.0));
        employeeList.add(new Employee(188, "Wang Liu", 31, "Male", "Product Development", 2015, 34500.0));
        employeeList.add(new Employee(199, "Amelia Zoe", 24, "Female", "Sales And Marketing", 2016, 11500.0));
        employeeList.add(new Employee(200, "Jaden Dough", 38, "Male", "Security And Transport", 2015, 11000.5));
        employeeList.add(new Employee(211, "Jasna Kaur", 27, "Female", "Infrastructure", 2014, 15700.0));
        employeeList.add(new Employee(222, "Nitin Joshi", 25, "Male", "Product Development", 2016, 28200.0));

        appleList = Arrays.asList(new Apple.AppleBuilder().setColor("GREEN").setWeight(80.0).build(),
                new Apple.AppleBuilder().setColor("GREEN").setWeight(155.0).build(),
                new Apple.AppleBuilder().setColor("RED").setWeight(120.0).build(),
                new Apple.AppleBuilder().setColor("RED").setWeight(70.0).build());


    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // sortByNameUsingComparator(employeeList);
        // sortByNameUsingComparable(employeeList);
        // analysisOnApplePassingBehaviourAsParameter();
        // usageOfCallable();
        // usageOfRunnable();
        // System.out.println(map(Arrays.asList("Santosh","Kumar","Sundar Ray"), String::length));
        findAverageLength();
    }

    // Write a Java 8 program to find the average length of strings in a list of strings?
    public static void findAverageLength(){
        // sample data
        List<String> strings = Arrays.asList("apple", "banana", "orange", "grape", "kiwi");

        double averageLength = strings.stream().mapToInt(e -> e.length()).average().orElse(0.0);

        System.out.println("averageLength ---> "+averageLength);


    }

    //sort the employee list by ascending order of name

    /**
     * @param input
     */
    private static void sortByNameUsingComparator(final List<Employee> input) {
        input.stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName())).forEach(e -> System.out.println(e.getName()));
    }

    /**
     * @param input
     */
    private static void sortByNameUsingComparable(final List<Employee> input) {
        // Fails if model does not implements comparable
        input.stream().sorted().forEach(e -> System.out.println(e.getName()));
    }

    /**
     * Analyse behaviour as parameter.
     */
    private static void analysisOnApplePassingBehaviourAsParameter() {
        filterApples(appleList, new AppleGreenColorPredicate()).forEach(a -> System.out.println(a.toString()));
        filterApples(appleList, new AppleHeavyWeightPredicate()).forEach(a -> System.out.println(a.toString()));
    }

    /**
     * @param input
     * @param p
     * @return
     */
    private static List<Apple> filterApples(List<Apple> input, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple o : input) {
            if (p.test(o)) {
                result.add(o);
            }
        }
        return result;
    }

    /**
     * Filters a list of elements based on the condition defined by the given predicate.
     * <p>
     * This is a generic method that can work with any type of elements. The method
     * iterates through the provided list, applies the given predicate to each element,
     * and returns a new list containing only the elements that satisfy the predicate's condition.
     * </p>
     *
     * @param <T>   the type of elements in the list
     * @param input the list of elements to be filtered
     * @param p     the predicate to apply to each element; only elements for which the predicate
     *              evaluates to {@code true} will be included in the result list
     * @return a list of elements that satisfy the predicate
     * @throws NullPointerException if the input list or predicate is {@code null}
     *
     *                              <p>Example Usage:</p>
     *                              <pre>
     *                              {@code
     *                              List<Apple> apples = ...;
     *                              List<Apple> redApples = filterApples(apples, apple -> apple.getColor().equals("red"));
     *                              }
     *                              </pre>
     */
    private static <T> List<T> filterApples(List<T> input, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T o : input) {
            if (p.test(o)) {
                result.add(o);
            }
        }
        return result;
    }

    // Callable vs Runnable
    private static void usageOfCallable() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CallableUsage task = new CallableUsage();
        Future<String> future = executor.submit(task);
        System.out.println(future.get().toString());
    }

    /**
     *
     */
    private static void usageOfRunnable(){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        RunnableImpl runner = new RunnableImpl();
        Thread t = new Thread(runner);
        t.start();
        executor.submit(t);
    }

    /**
     *
     * @param listInput
     * @param consumer
     * @param <T>
     */
    private static <T> void forEach(List<T> listInput , Consumer<T> consumer){
        for(T i : listInput){
            consumer.accept(i);
        }
    }

    /**
     *
     * @param input
     * @param f
     * @return
     * @param <T>
     * @param <R>
     */
    private static <T,R> List<R> map(List<T> input , Function<T,R> f){
        List<R> result = new ArrayList<>();
        for(T t : input){
            result.add( f.apply(t));
        }
        return result;
    }


}//RunnerApp

class CallableUsage implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "Usage of callable.";
    }
}//CallableUsage

class RunnableImpl implements Runnable {

    @Override
    public void run() {
        System.out.println("Usage of runnable");
    }
}//RunnableImpl

