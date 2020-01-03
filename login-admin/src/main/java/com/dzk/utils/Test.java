package com.dzk.utils;

public class Test {
    public int Fibonacci(int n){
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        return Fibonacci(n-2)+Fibonacci(n-1);
    }

    public static void main(String[] args) {
        Test test = new Test();

        System.out.println(test.Fibonacci(0));
        System.out.println(test.Fibonacci(1));
        System.out.println(test.Fibonacci(6));
    }
}
