package org.thread.pool;

public class Analysis {
    public static void main(String[] args) {
        System.out.println("Hi! How are you ?");
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

        if(singleton1.hashCode() == singleton2.hashCode()){
            System.out.println("si");
        }else{
            System.out.println("blah");
        }
    }
}

class Singleton {
    private static Singleton instance;

    private Singleton(){

    }

    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }

        return instance;
    }
}
