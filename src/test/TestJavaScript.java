/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.BackEnd;
import jazari.CallBackInterface;
import jazari.DataSource;
import jazari.JIM;
import jazari.ProgrammingLanguage;
import utils.FactoryUtils;

/**
 *
 * @author BAP1
 */
public class TestJavaScript {
    public static void main(String[] args) {
        
    }
    
    public static void doSomething(String s){
        System.out.println(s);
    }
    /**
     * bu metod daha önceden öğrenilmiş (teachable machine deki gibi) bir modeli
     * kaydettikten sonra production için pratik bir şekilde kullanmaya yarar. 
     * Bunun için anlık test örneklerinin webcami stream ederek yakalar ve sonucunu 
     * observable (veya functional interface üzerinden) doSomethng metodunu tetikleyerek
     * asenkron bir şekilde biiznillah çalıştırır.
     */
    private static void doTransferLearningOnlineWebCamTestJS() {
        JIM.pythonTransferLearningWebCam(
            BackEnd.CPU,
            FactoryUtils.currDir+"\\models\\keras_model_pistachio.h5",
            DataSource.CAMERA,
            new String[]{"Closed","Open"}, new CallBackInterface() {
                @Override
                public void onReceive(String str) {
                    doSomething(str);
                }
            })
                .execute();
    }
}
