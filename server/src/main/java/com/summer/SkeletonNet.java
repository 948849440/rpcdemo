package com.summer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class SkeletonNet {
    private String id;
    private Object objImpl;

    public SkeletonNet(String id, Object objImpl) {
        this.id = id;
        this.objImpl = objImpl;
    }

    public void start(Socket socket, ObjectInputStream objIn) {
        try {
            String methodName = objIn.readUTF();
            Class<?>[] parameterTypes = (Class<?>[])objIn.readObject();
            Object[] parameters = (Object[])objIn.readObject();
            Class invoketClass = objImpl.getClass();
            Method method = invoketClass.getMethod(methodName,parameterTypes);
            Object result = method.invoke(objImpl,parameters);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
