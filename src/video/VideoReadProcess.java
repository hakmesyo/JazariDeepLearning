package video;

import org.opencv.core.*;
import org.opencv.videoio.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import utils.FactoryUtils;

public class VideoReadProcess {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        approach_1();
//        approach_2();
    }


    private static void approach_1() {
        //Create new MAT object
        Mat frame = new Mat();
        //Create new VideoCapture object
//        VideoCapture camera = new VideoCapture("dataset/videos/videoplayback.mp4");
//        VideoCapture camera = new VideoCapture("dataset/videos/640.mov");
        VideoCapture camera = new VideoCapture("C:\\Users\\elcezerilab\\Downloads\\test_1.mp4");
        //Create new JFrame object
        JFrame jframe = new JFrame("Video Title");
        //Inform jframe what to do in the event that you close the program
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create a new JLabel object vidpanel
        JLabel vidPanel = new JLabel();
        //assign vidPanel to jframe
        jframe.setContentPane(vidPanel);
        //set frame size
        jframe.setSize(1920, 1080);
        //make jframe visible
        jframe.setVisible(true);
        double fps = 24;
        fps = camera.get(Videoio.CAP_PROP_FPS);
        System.out.println("fps = " + fps);
        double frameCount=camera.get(Videoio.CAP_PROP_FRAME_COUNT);
        System.out.println("frameCount = " + frameCount);
        while (true) {
            //If next video frame is available
            if (camera.read(frame)) {
                ImageIcon image = new ImageIcon(FactoryUtils.toBufferedImage(frame));
                //Update the image in the vidPanel
                vidPanel.setIcon(image);
                //Update the vidPanel in the JFrame
                vidPanel.repaint();
            }
            if (fps < 40) {
                try {
                    Thread.sleep(1000 / (int) fps);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VideoReadProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void approach_2() {
        Mat matOrig = new Mat();
        VideoCapture capture = new VideoCapture("dataset/videos/640.mov");
        if (capture.isOpened()) {
            while (true) {
                capture.read(matOrig);
                // get some meta data about frame.              
                double fps = capture.get(Videoio.CAP_PROP_FPS);
                double frameCount = capture.get(Videoio.CAP_PROP_FRAME_COUNT);
                double h = capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
                double w = capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
                double posFrames = capture.get(Videoio.CAP_PROP_POS_FRAMES);
                double posMsec = capture.get(Videoio.CAP_PROP_POS_MSEC);
                double speed = capture.get(Videoio.CAP_PROP_SPEED);
                System.out.println("fps:" + fps + " framecount:" + frameCount + " speed:" + speed);
                if (!matOrig.empty()) {
                    System.out.println("frame is not empty");
                }
            }
        }
    }
}
