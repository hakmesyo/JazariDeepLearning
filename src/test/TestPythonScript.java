/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import enums.EnumBackEnd;
import enums.EnumDataSource;
import enums.EnumLearningMode;
import interfaces.Configuration;
import interfaces.InterfaceCallBack;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import jazari.ConfigurationPythonScript;
import jazari.JDL_Python;
import utils.FactoryUtils;

/*
 * **************************************************************************************************************
 * FOLLOW INSTRUCTIONS
 * **************************************************************************************************************
 * For python you should pip install following instructions in cmd window. First
 * of all download latest python 64 bit version (python 3.7.5 date:12.12.2019)
 * and install it properly while installing check add path variable and you have
 * to restart the pc. Then install those libraries with pip Winkey+R --> write
 * cmd and press enter then black command window appears. Afterwards type
 * followings one by one. note that you should skip 3 (GPU) installation stage
 * since there might be some issues regarding gpu settings
 *
 * 1- python -m pip install –-upgrade pip 
 * 2- pip install tensorflow 
 * 3- (optional if you don't have gpu, skip this item) pip install tensorflow-gpu 
 * 4- pip install opencv-python 
 * 5- pip install websocket-client 
 * 6- pip install pillow 
 * 7- pip install matplotlib 
 * 8- pip install keras
 *
 * @author Thinkable Machine Team Ver:24.12.2019
 */
public class TestPythonScript {

    private static long t = FactoryUtils.tic();

    public static void main(String[] args) {
//        executeWebCamTest();
        executeOfflineFolderTest();
//        executeOfflineFileTest();
    }

    private static void executeWebCamTest() {
        JDL_Python jdlp = new JDL_Python();
        Configuration config = new ConfigurationPythonScript()
                .setBackEnd(EnumBackEnd.CPU)
                .setDataSource(EnumDataSource.CAMERA)
                .setLearningMode(EnumLearningMode.TEST)
                .setClassLabels(new String[]{"Closed", "Open"})
                .setModelPath(FactoryUtils.getDefaultDirectory() + "\\models\\python\\pistachio\\keras_model.h5")
                .setCallBackFunction(new InterfaceCallBack() {
                    @Override
                    public void onMessageReceived(String str) {
                        invokeMethod(str);
                    }

                });
        jdlp.setConfiguration(config);
        jdlp.build();
        jdlp.execute();
    }

    private static void invokeMethod(String str) {
        System.out.println(str);
        t = FactoryUtils.toc(t);
    }

    private static void executeOfflineFolderTest() {
        JDL_Python jdlp = new JDL_Python();
        Configuration config = new ConfigurationPythonScript()
                .setBackEnd(EnumBackEnd.CPU)
                .setDataSource(EnumDataSource.IMAGE_FOLDER)
                .setLearningMode(EnumLearningMode.TEST)
                .setTestFolderPath(FactoryUtils.getDefaultDirectory() + "\\dataset\\pistachio\\test")
                .setModelPath(FactoryUtils.getDefaultDirectory() + "\\models\\python\\pistachio\\keras_model.h5")
                .setCallBackFunction(new InterfaceCallBack() {
                    @Override
                    public void onMessageReceived(String str) {
                        invokeMethod(str);
                    }

                });
        jdlp.setConfiguration(config);
        jdlp.build();
        jdlp.execute();
    }

    private static void executeOfflineFileTest() {
        JDL_Python jdlp = new JDL_Python();
        Configuration config = new ConfigurationPythonScript()
                .setBackEnd(EnumBackEnd.CPU)
                .setDataSource(EnumDataSource.IMAGE_FILE)
                .setLearningMode(EnumLearningMode.TEST)
                .setModelPath(FactoryUtils.getDefaultDirectory() + "\\models\\python\\pistachio\\keras_model.h5")
                .setCallBackFunction(new InterfaceCallBack() {
                    @Override
                    public void onMessageReceived(String str) {
                        invokeMethod2(str);
                    }

                });
        jdlp.setConfiguration(config);
        jdlp.build();
        jdlp.execute();
    }

    private static int k = 0;
    private static int hit = 0;

    private static void invokeMethod2(String str) {
        if (str.equals("python client is ready now")) {
            System.out.println("connection was established");
            FactoryUtils.delay(1000);
            predictOnebyOne();
            FactoryUtils.delay(1000);
            FactoryUtils.server.broadcast("stop");
        } else {
            if (str.contains("<->")) {
                String[] msg = str.split("<->");
                if (msg.length > 0) {
                    String prediction = msg[0];
                    String actual = msg[1];
                    if (prediction.equals(actual)) {
                        hit++;
                    }
                    k++;
                    double acc = 1.0 * hit / k;
                    System.out.println("predicted as " + prediction + " actual was " + actual + " accuracy:" + acc);
                }
            } else {
                System.out.println(str);
            }
        }
    }

    private static void predictOnebyOne() {
        k = hit = 0;
        String testPath = FactoryUtils.currDir + "\\dataset\\pistachio\\test";
        String closedPath = testPath + "\\closed";
        String openPath = testPath + "\\open";
        File[] files = FactoryUtils.getFiles(closedPath);
        for (File file : files) {
            FactoryUtils.server.broadcast("0;" + file.getAbsolutePath());
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(DeprecatedTestPython.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        files = FactoryUtils.getFiles(openPath);
        for (File file : files) {
            FactoryUtils.server.broadcast("1;" + file.getAbsolutePath());
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(DeprecatedTestPython.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
