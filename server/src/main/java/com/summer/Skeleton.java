package com.summer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Skeleton {

    private static Skeleton skeleton = new Skeleton();
    private Map<String,SkeletonNet> netCache = new ConcurrentHashMap<>();

    private int port;

    public Skeleton() {
    }

    public void init(int port){
        this.port = port;
    }

    public static Skeleton getInstance(){
        return skeleton;
    }

    public void sign(String id,Object serviceImpl){
        SkeletonNet skeletonNet = new SkeletonNet(id,serviceImpl);
        netCache.putIfAbsent(id,skeletonNet);
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server start");
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(()->{
                    try {
                        InputStream in = socket.getInputStream();
                        ObjectInputStream objIn = new ObjectInputStream(in);
                        String id = objIn.readUTF();
                        if(!netCache.containsKey(id)){
                            throw new Exception("no cache");
                        }
                        SkeletonNet skeletonNet = netCache.get(id);
                        skeletonNet.start(socket,objIn);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
