package test;


import jazari.CallBackInterface;
import utils.FactoryUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DELL LAB
 */
public class TestJavaWebSocketServer {

    private static long t1=FactoryUtils.tic();
    
    private static void onMessageReceived(String str) {        
        System.out.println(str);
        //System.out.println(str+" elapsed time is "+(System.nanoTime()-t1)/(1000000.0d));
        //t1=System.nanoTime();
        t1=FactoryUtils.toc(t1);
        
    }

    public static void main(String[] args) {
        FactoryUtils.startJavaServer(new CallBackInterface() {
            @Override
            public void onReceive(String str) {
                onMessageReceived(str);
            }

        });
        FactoryUtils.delay(1000);

    }
}
