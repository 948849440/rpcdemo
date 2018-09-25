package com.summer;

public class ServerClient {

    public static void main(String[] args) {
        ISayHello sayHello = new SayHelloImpl();
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.init(10086);
        skeleton.sign("sayHello",sayHello);
        skeleton.start();
    }
}
