package com.summer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class SkeletonNet {
    private String id;
    private Object objImpl;
    private Socket socket;

    public SkeletonNet(String id, Object objImpl) {
        this.id = id;
        this.objImpl = objImpl;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public void start() {
        InputStream in = null;
        try {
            in = socket.getInputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);
            String methodName = objIn.readUTF();
            Class<?>[] parameterTypes = (Class<?>[])objIn.readObject();
            Object[] parameters = (Object[])objIn.readObject();
            Class invoketClass = objImpl.getClass();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
