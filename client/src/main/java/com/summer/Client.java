package com.summer;

public class Client {

    public static void main(String[] args) {
        ISayHello sayHello = RPCProxy.getClient("sayHello",ISayHello.class,"127.0.0.1",10086);
        System.out.println("result:"+sayHello.sayHello("xia"));
    }
}
