package test;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import enums.EnumBackEnd;
import enums.EnumDataSource;
import jazari.JDL;
import enums.EnumDevelopmentPlatform;
import interfaces.InterfaceCallBack;
import utils.FactoryUtils;

    /*
     * for python backend please pip install those in cmd window. First of all download latest
     * python 64 bit version (python 3.7.5 date:12.12.2019) and install properly
     * and allow add path variable and restart the pc. Then install those libraries with pip 
     * 1- cmd: python -m pip install –-upgrade pip 
     * 2- cmd: pip install tensorflow 
     * 3- (optional if you don't have gpu, skip this item) cmd: pip install tensorflow-gpu  
     * 4- cmd: pip install opencv-python 
     * 5- cmd: pip install websocket-client 
     * 6- cmd: pip install websocket-server 
     * 7- cmd: pip install pillow 
     * 8- cmd: pip install matplotlib 
     * 9- cmd: pip install keras
     */

public class DeprecatedTestPython {
    private static long t=FactoryUtils.tic();    
 
    private static void doSomething(String str){
        System.out.println(str);
        t=FactoryUtils.toc(t);
    }
    
    public static void main(String[] args) {
        doTransferLearningOnlineWebCamTestPythonCPU();
//        doTransferLearningOfflineTestPythonCPUfile();
//        doTransferLearningOnlineTestPythonCPUfile();
    }

    /**
     * bu metod daha önceden öğrenilmiş (teachable machine deki gibi) bir modeli
     * kaydettikten sonra production için pratik bir şekilde kullanmaya yarar. 
     * Bunun için anlık test örneklerinin webcami stream ederek yakalar ve sonucunu 
     * observable (veya functional interface üzerinden) doSomethng metodunu tetikleyerek
     * asenkron bir şekilde biiznillah çalıştırır.
     */
    private static void doTransferLearningOnlineWebCamTestPythonCPU() {
        JDL.pythonTransferLearningWebCam(EnumBackEnd.CPU,
                FactoryUtils.currDir+"\\models\\keras_model_pistachio.h5",
                EnumDataSource.CAMERA,
                new String[]{"Closed","Open"}, 
                DeprecatedTestPython::doSomething)
                .execute();
    }
    
    /**
     * bu metod daha önceden öğrenilmiş (teachable machine deki gibi) bir modeli
     * kaydettikten sonra production için pratik bir şekilde kullanmaya yarar. 
     * Bunun için offline bir şekilde harddiskte kaydetmiş olduğunuz örnekleri kullarak sonucunu 
     * observable (veya functional interface üzerinden) doSomethng metodunu tetikleyerek
     * asenkron bir şekilde biiznillah çalıştırır.
     */
    private static void doTransferLearningOfflineTestPythonCPUfile() {
        JDL.transferLearningModelForOfflineTestImages(EnumDevelopmentPlatform.PYTHON,
                EnumBackEnd.CPU,
                FactoryUtils.currDir+"\\models\\keras_model_pistachio.h5",
                EnumDataSource.IMAGE_FILE,
                "dataset\\pistachio\\test", 
                DeprecatedTestPython::doSomething)
                .execute();
    }
    
    
    
    /**
     * bu metod daha önceden öğrenilmiş (teachable machine deki gibi) bir modeli
     * kaydettikten sonra production için pratik bir şekilde kullanmaya yarar. 
     * Bunun için anlık (realtime) test örneklerinin python dışında mesela javada 
     * webcam açılarak yapıldığı veya görüntü dosyalarını tek tek python serverene bağlanıp
     * gönderim işlemini yapar ve sonucunu 
     * observable (veya functional interface üzerinden) doSomethng2 metodunu tetikleyerek
     * asenkron bir şekilde biiznillah çalıştırır.
     */
    
    private static void doTransferLearningOnlineTestPythonCPUfile() {
        JDL.transferLearningModelForOnlineTestImage(EnumDevelopmentPlatform.PYTHON,
                EnumBackEnd.CPU,
                FactoryUtils.currDir+"\\models\\keras_model_pistachio.h5",
                EnumDataSource.IMAGE_FILE,
                (String str) -> {
                    doSomething2(str);
                })
                .execute();
    }

    private static void doSomething2(String str) {
        if (str.equals("received:python client is ready for constructing python server")) {
            FactoryUtils.client=FactoryUtils.connectToPythonServer(new InterfaceCallBack() {
                @Override
                public void onMessageReceived(String str) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            FactoryUtils.delay(1000);
            predictOnebyOne();
            FactoryUtils.client.send("stop");
        }else{
            //buraya gelen str bilgisini (sınıf index bilgisini tutar) production ortamındaki asıl metoda tevcih etmelisin.
            System.out.println("str = " + str);
        }
    }
    
    private static void predictOnebyOne(){
        
        if (FactoryUtils.client==null) {
            System.out.println("python server connection was not established something went wrong");
            return;
        }
        String testPath=FactoryUtils.currDir+"\\dataset\\pistachio\\test";
        String openPath=testPath+"\\open";
        String closedPath=testPath+"\\closed";
        File[] files=FactoryUtils.getFiles(openPath);
        for (File file : files) {
            FactoryUtils.client.send(file.getAbsolutePath());
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(DeprecatedTestPython.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        files=FactoryUtils.getFiles(closedPath);
        for (File file : files) {
            FactoryUtils.client.send(file.getAbsolutePath());
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(DeprecatedTestPython.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void topla(){
        new Thread(() -> {
            //kdjfksjdhf
        }).start();
    }
    
}
