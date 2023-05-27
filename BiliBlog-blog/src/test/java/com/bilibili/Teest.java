package com.bilibili;

/**
 * @ClassName Teest
 * @Description TODO
 * @Version 1.0
 */
public class Teest {
    public static void outNumber(int n){
        System.out.println(n);// 1237 2474 4948 9896
        if (n > 5000){
            System.out.println(n);//9896
            return;
        }
        outNumber(2*n);
        System.out.println(n);//4948 2474 1237
    }

    public static void main(String[] args) {
        outNumber(1237);
    }

}
