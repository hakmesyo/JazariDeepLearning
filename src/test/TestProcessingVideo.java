/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import gui.PanelVideo;
import processing.core.PImage;
import processing.video.*;
import javax.swing.JPanel;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PSurface;

/**
 *
 * @author BAP1
 */
public class TestProcessingVideo extends PApplet {
    PanelVideo ref;
    Movie movie;
    PImage img = null;
    int k = 0;

    @Override
    public void settings() {
        size(240, 426);
    }

    @Override
    public void setup() {
    }


    @Override
    public void draw() {
        if (ref != null) {
//            if (ref.isVideoStart) {
//                if (movie.available()) {
//                    movie.read();
//                    img = movie.copy();
//                    image(img, 0, 0);
////            img.save(sketchPath() + "/dataset/images/captured_video/" + (k++) + ".jpg");
//                    println(frameRate);
//                }
//            }
        }

    }

//    public static void main(String[] args) {
//        String[] appletArgs = new String[]{"test.TestProcessingVideo"};
//        PApplet.main(appletArgs);
//    }
//    
    public static void load(JPanel pan) {
        TestProcessingVideo pt = new TestProcessingVideo();
        if (pan instanceof PanelVideo) {
            pt.ref = (PanelVideo) pan;
        }
        PSurface ps = pt.initSurface();
        ps.setSize(pan.getWidth(), pan.getHeight());
        PSurfaceAWT.SmoothCanvas smoothCanvas = (PSurfaceAWT.SmoothCanvas) ps.getNative();
        pan.add(smoothCanvas);
        ps.startThread();
    }

}
