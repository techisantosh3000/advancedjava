package com.design.pattern.creational;

public class CustomSingleton {
    public static void main(String[] args) {
        // Eager Singleton
        Runnable runnableEagerSingleton = () -> {
            EagerSingleton eagerSingleton = EagerSingleton.getInstance();
            System.out.println("object1 "+eagerSingleton.hashCode() );
        };

        Thread t1 = new Thread(runnableEagerSingleton);
        Thread t2 = new Thread(runnableEagerSingleton);

        // t1.start();
        // t2.start();

        // Lazy Singleton
        Runnable runnableLazySingleton = () -> {
            LazySingletonNotSynchronized lazySingletonNotsynchronized = LazySingletonNotSynchronized.getInstance();
            System.out.println("object2 "+ lazySingletonNotsynchronized.hashCode());
        };

        Thread t3 = new Thread(runnableLazySingleton);
        Thread t4 = new Thread(runnableLazySingleton);
        Thread t5 = new Thread(runnableLazySingleton);

//        t3.start();
//        t4.start();
//        t5.start();

        // Lazy Singleton Synchronized
        Runnable runnableLazySingletonSynchronized = () -> {
            LazySingletonSynchronized lazySingletonSynchronized = LazySingletonSynchronized.getInstance();
            System.out.println("object3 "+ lazySingletonSynchronized.hashCode());
        };

        Thread t6 = new Thread(runnableLazySingletonSynchronized);
        Thread t7 = new Thread(runnableLazySingletonSynchronized);
        Thread t8 = new Thread(runnableLazySingletonSynchronized);

        t6.start();
        t7.start();
        t8.start();



    }
}

class EagerSingleton {
    // Instance created at class-load time
    private static final EagerSingleton instance = new EagerSingleton();

    // Private constructor to prevent instantiation
    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}

class LazySingletonNotSynchronized {
    private static LazySingletonNotSynchronized instance;

    private LazySingletonNotSynchronized(){

    }

    public static LazySingletonNotSynchronized getInstance(){
        if(instance == null){
            instance = new LazySingletonNotSynchronized();
        }
        return instance;
    }
}

class LazySingletonSynchronized{
    private static LazySingletonSynchronized lazySingletonSynchronized;

    private LazySingletonSynchronized(){

    }

    public static synchronized LazySingletonSynchronized getInstance(){
        if(lazySingletonSynchronized == null){
            lazySingletonSynchronized = new LazySingletonSynchronized();
        }
        return lazySingletonSynchronized;
    }

}

