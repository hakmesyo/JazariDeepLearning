/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import enums.EnumBackEnd;
import enums.EnumDataSource;
import enums.EnumLearningMode;
import gui.FramePicture;
import interfaces.Configuration;
import interfaces.InterfaceCallBack;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import jazari.ConfigurationJavaScript;
import jazari.JDL_JavaScript;
import jazari.JDL_Python;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import utils.FactoryUtils;

/**
 * **************************************************************************************************************
 * FOLLOW INSTRUCTIONS
 * **************************************************************************************************************
 * For JavaScript you should first install node to your machine. Then in
 * command/terminal window type "npm install http-server -g". After the
 * installation, type http-server within the desired folder at cmd window If you
 * have trained model from teachable machine API you should save the model
 * folder in your specific problem folder. Model folder should contains three
 * files; weights.bin, model.json, metadata.json.
 *
 * @author Thinkable Machine Team Ver:24.12.2019
 */
public class TestJavaScript {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private static long t = FactoryUtils.tic();

    public static void main(String[] args) {
//        openCVCamera();
        executeWebCamTest();
//        executeOfflineTest();
    }

    private static void executeWebCamTest() {
        JDL_JavaScript jdlp = new JDL_JavaScript();
        Configuration config = new ConfigurationJavaScript()
                .setDataSource(EnumDataSource.CAMERA)
                .setLearningMode(EnumLearningMode.TEST)
                .setModelPath(FactoryUtils.getDefaultDirectory() + "\\models\\js\\deneme")
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

    private static void executeOfflineTest() {
        JDL_Python jdlp = new JDL_Python();
        Configuration config = new ConfigurationJavaScript()
                .setTestFolderPath(FactoryUtils.getDefaultDirectory() + "\\dataset\\pistachio\\test")
                .setBackEnd(EnumBackEnd.CPU)
                .setDataSource(EnumDataSource.IMAGE_FILE)
                .setLearningMode(EnumLearningMode.TEST)
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

    private static void openCVCamera() {
        VideoCapture camera = new VideoCapture(0);
        camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        if (!camera.isOpened()) {
            System.out.println("Error accessing camera");
        } else {
            new Thread(new Runnable() {
                private boolean isCancelled;

                @Override
                public void run() {
                    Mat webcamImage = new Mat();
                    FramePicture fp = new FramePicture();
                    fp.setVisible(true);
                    long t = FactoryUtils.tic();
                    long t1 = FactoryUtils.tic();
                    while (!isCancelled) {
                        t1 = FactoryUtils.toc("overall cost:", t1);
                        System.out.println("");
                        camera.read(webcamImage);
                        t = FactoryUtils.toc("camera read cost:", t);

//                        Mat dif=webcamImage.clone();
//                        Mat gaussf_45=webcamImage.clone();
//                        Imgproc.GaussianBlur(gaussf_45, gaussf_45,new Size(45,45), 0);
//                        Mat gaussf_11=webcamImage.clone();
//                        Imgproc.GaussianBlur(gaussf_11, gaussf_11,new Size(11,11), 0);
//                        
//                        Imgproc.cvtColor(gaussf_45, gaussf_45, Imgproc.COLOR_BGR2GRAY);
//                        Imgproc.cvtColor(gaussf_11, gaussf_11, Imgproc.COLOR_BGR2GRAY);
//                        
//                        Core.absdiff(gaussf_11, gaussf_45,dif);
                        
//                        BufferedImage img = FactoryUtils.toBufferedImage(dif);
                        BufferedImage img = FactoryUtils.toBufferedImage(webcamImage);
                        t = FactoryUtils.toc("convert cost:", t);
                        fp.setImage(img);
                        t = FactoryUtils.toc("panel paint cost:", t);

                    }
                }

            }).start();
        }
    }

    private static void processImage(Mat webcamImage) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            public Void doInBackground() {
                return processPict(webcamImage);
            }

        };
        worker.execute();
    }

    private static Void processPict(Mat mat) {
//        Mat destination = new Mat(mat.rows(),mat.cols(),mat.type());
        Imgproc.GaussianBlur(mat, mat,new Size(45,45), 0);
//        destination.copyTo(mat);
        return null;
    }
}


