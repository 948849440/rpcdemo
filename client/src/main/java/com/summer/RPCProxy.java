package com.summer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class RPCProxy {

    @SuppressWarnings("unchecked")
    public static <T> T getClient(String id,Class<T> clazz, String ip, int port){

        return  (T) Proxy.newProxyInstance(RPCProxy.class.getClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object arg0, Method method, Object[] parameters) throws Throwable {
                Socket socket = new Socket(ip, port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeUTF(id);
                out.writeUTF(method.getName());
                out.writeObject(method.getParameterTypes());
                out.writeObject(parameters);
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                return in.readObject();
            }
        });
    }
}
