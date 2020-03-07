/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import gui.FrameVideoSnap;
import gui.PanelPict;
import gui.PanelVideoSnap;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PSurface;
import processing.event.MouseEvent;
import processing.video.*;

public class VideoSnapProcessing extends PApplet {

    static VideoSnapProcessing pt;
    static FrameVideoSnap frame_ref;
    boolean runOnce = false;
    boolean isRecord = false;
    static PSurface ps;
    static Movie movie;
    static PImage img;
    int k = 0;
    int rx = 50, ry = 50, rw = 224, rh = 224;
    int dx, dy;
    int resize_px, resize_py;
    boolean isDraggedRect = false;
    boolean isResizeRect_top_left = false;
    boolean isResizeRect_top_right = false;
    boolean isResizeRect_bottom_left = false;
    boolean isResizeRect_bottom_right = false;
    boolean isResizeRectY_top = false;
    boolean isResizeRectY_bottom = false;
    boolean isResizeRectX_left = false;
    boolean isResizeRectX_right = false;
    int not_avaliable_counter = 0;
    private boolean isRoiPressed = false;
    private PImage latestImage;
    static float duration;
    static int ref_frame_panel_width;
    static int ref_frame_panel_height;
    int size = 100;
    private boolean isCtrlPressed;
    private int adaptive_width = 0;
    private int adaptive_height = 0;
    private boolean isMovieSizeAdjust;

    @Override
    public void settings() {
        System.out.println("settings");
//        size(1000, 1000);
    }

    @Override
    public void setup() {
        System.out.println("setup");
        ps.setResizable(true);
//        frame.setResizable(true);
        movie.play();
        duration = movie.duration();
        System.out.println("duration:" + duration);
        while (!movie.available()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(VideoSnapProcessing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("şu anda available oldu ");
        movie.read();
        img = movie.copy();
        latestImage = img;
        adjustMovieSize(img);
//        ps.setSize(img.width, img.height);
        ps.setSize(adaptive_width, adaptive_height);
        PSurfaceAWT.SmoothCanvas smoothCanvas = (PSurfaceAWT.SmoothCanvas) ps.getNative();
        frame_ref.getPanel().setPreferredSize(new Dimension(img.width, img.height));
        frame_ref.getPanel().removeAll();
        frame_ref.getPanel().add(smoothCanvas);
        System.out.println("referans frame's panel default w,h:" + ref_frame_panel_width + "," + ref_frame_panel_height);
        System.out.println("movie w,h:" + img.width + "," + img.height);
//        int w = frame_ref.getWidth();
//        if (img.width > ref_frame_panel_width) {
//            w = frame_ref.getWidth() + (img.width - ref_frame_panel_width);
//        }
//        int h = frame_ref.getHeight() + 10;
//        if (img.height > ref_frame_panel_height) {
//            h = frame_ref.getHeight() + (img.height - ref_frame_panel_height) + 10;
//        }
//        System.out.println("w,h:" + w + "," + h);
//        frame_ref.setSize(new Dimension(w, h));
//        frame_ref.revalidate();
        movie.stop();
        System.out.println(FactoryUtils.getDefaultDirectory() + "");
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        int e = event.getCount();
        frame_ref.getSlider().setValue(frame_ref.getSlider().getValue()-e);
//        println(e);

    }

    @Override
    public void draw() {
        if (movie != null && isRoiPressed) {
            if (movie.available()) {
                movie.read();
                frame_ref.getBar().setValue((int) (movie.time() / duration * 100) + 1);
                img = movie.copy();
                if (isMovieSizeAdjust) {
                    img.resize(adaptive_width, adaptive_height);
                }
                image(img, 0, 0);
                latestImage = img;
                if (isCtrlPressed) {
                    PanelVideoSnap pan_snap = (PanelVideoSnap) frame_ref.getClassLabelTabbedPane().getSelectedComponent();
                    if (pan_snap != null) {
                        PanelPict pp = new PanelPict();
                        pp.setPreferredSize(new Dimension(size, size));
                        img = img.get(rx, ry, rw, rh);
                        pp.setImage((BufferedImage) img.getNative(), size);
                        JPanel pan = pan_snap.getCurrentPanel();
                        pan.add(pp, 0);
                        img.save(sketchPath() + "/dataset/images/captured_video/" + (k++) + ".jpg");
                        pan.revalidate();
                    }
                }
                drawROI();
                not_avaliable_counter = 0;
            }
        } else {
            if (latestImage == null) {
                background(0);
            } else {
                image(latestImage, 0, 0);
            }
            drawROI();
        }
    }

    private void drawROI() {
        fill(255, 255, 0);
        noStroke();

        //corners
        rect(rx - 10, ry - 10, 10, 10);
        rect(rx + rw, ry - 10, 10, 10);
        rect(rx - 10, ry + rh, 10, 10);
        rect(rx + rw, ry + rh, 10, 10);

        //edges
        rect(rx + rw / 2 - 5, ry - 12, 10, 10);
        rect(rx - 12, ry + rh / 2, 10, 10);
        rect(rx + rw / 2 - 5, ry + rh, 10, 10);
        rect(rx + rw, ry + rh / 2, 10, 10);

        strokeWeight(2);
        noFill();
        stroke(255, 255, 0);
        rect(rx - 2, ry - 2, rw + 2, rh + 2);
        textSize(14);
        fill(255, 255, 0);
        text("Rect(" + rx + "," + ry + "," + rw + "," + rh + ")", rx + rw / 4, ry + rh + 24);
    }

    @Override
    public void mouseReleased() {
        isRoiPressed = false;
        movie.pause();
    }

    @Override
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == CONTROL) {
                isCtrlPressed = true;
            }
        }
    }

    @Override
    public void keyReleased() {
        isCtrlPressed = false;
    }

    @Override
    public void mousePressed() {
        if (mouseButton == LEFT) {
            if (mouseX > rx && mouseX < rx + rw && mouseY > ry && mouseY < ry + rh) {
                dx = mouseX - rx;
                dy = mouseY - ry;
                isDraggedRect = true;
                isRoiPressed = true;
                movie.speed(frame_ref.video_speed);
                movie.play();
            } else {
                isDraggedRect = false;
            }

            //corners
            if (mouseX > rx - 10 && mouseX < rx && mouseY > ry - 10 && mouseY < ry) {
                isResizeRect_top_left = true;
                resize_px = mouseX;
                resize_py = mouseY;
            } else {
                isResizeRect_top_left = false;
            }
            if (mouseX > rx + rw && mouseX < rx + rw + 10 && mouseY > ry - 10 && mouseY < ry) {
                isResizeRect_top_right = true;
                resize_px = mouseX;
                resize_py = mouseY;
            } else {
                isResizeRect_top_right = false;
            }
            if (mouseX > rx - 10 && mouseX < rx && mouseY > ry + rh && mouseY < ry + rh + 10) {
                isResizeRect_bottom_left = true;
                resize_px = mouseX;
                resize_py = mouseY;
            } else {
                isResizeRect_bottom_left = false;
            }
            if (mouseX > rx + rw && mouseX < rx + rw + 10 && mouseY > ry + rh && mouseY < ry + rh + 10) {
                isResizeRect_bottom_right = true;
                resize_px = mouseX;
                resize_py = mouseY;
            } else {
                isResizeRect_bottom_right = false;
            }

            //edges
            if (mouseX > rx + rw / 2 - 5 && mouseX < rx + rw / 2 + 5 && mouseY > ry - 10 && mouseY < ry) {
                isResizeRectY_top = true;
                resize_py = mouseY;
            } else {
                isResizeRectY_top = false;
            }
            if (mouseX > rx + rw / 2 - 5 && mouseX < rx + rw / 2 + 5 && mouseY > ry + rh && mouseY < ry + rh + 10) {
                isResizeRectY_bottom = true;
                resize_py = mouseY;
            } else {
                isResizeRectY_bottom = false;
            }
            if (mouseX > rx - 10 && mouseX < rx && mouseY > ry + rh / 2 - 5 && mouseY < ry + rh / 2 + 5) {
                isResizeRectX_left = true;
                resize_px = mouseX;
            } else {
                isResizeRectX_left = false;
            }
            if (mouseX > rx + rw && mouseX < rx + rw + 10 && mouseY > ry + rh / 2 - 5 && mouseY < ry + rh / 2 + 5) {
                isResizeRectX_right = true;
                resize_px = mouseX;
            } else {
                isResizeRectX_right = false;
            }
        } else if (mouseButton == RIGHT) {
            if (mouseX > rx && mouseX < rx + rw && mouseY > ry && mouseY < ry + rh) {
                dx = mouseX - rx;
                dy = mouseY - ry;
                isDraggedRect = true;
                isRoiPressed = true;
                movie.speed(-frame_ref.video_speed);
                movie.play();
            } else {
                isDraggedRect = false;
            }
        }
    }

    @Override
    public void mouseMoved() {
        //inside the roi
        if (mouseX > rx && mouseX < rx + rw && mouseY > ry && mouseY < ry + rh) {
            cursor(MOVE);
        } //corners
        else if (mouseX > rx - 10 && mouseX < rx && mouseY > ry - 10 && mouseY < ry) {
            cursor(CROSS);
        } else if (mouseX > rx + rw && mouseX < rx + rw + 10 && mouseY > ry - 10 && mouseY < ry) {
            cursor(CROSS);
        } else if (mouseX > rx - 10 && mouseX < rx && mouseY > ry + rh && mouseY < ry + rh + 10) {
            cursor(CROSS);
        } else if (mouseX > rx + rw && mouseX < rx + rw + 10 && mouseY > ry + rh && mouseY < ry + rh + 10) {
            cursor(CROSS);
        } //edges
        else if (mouseX > rx + rw / 2 - 5 && mouseX < rx + rw / 2 + 5 && mouseY > ry - 10 && mouseY < ry) {
            cursor(CROSS);
        } else if (mouseX > rx + rw / 2 - 5 && mouseX < rx + rw / 2 + 5 && mouseY > ry + rh && mouseY < ry + rh + 10) {
            cursor(CROSS);
        } else if (mouseX > rx - 10 && mouseX < rx && mouseY > ry + rh / 2 - 5 && mouseY < ry + rh / 2 + 5) {
            cursor(CROSS);
        } else if (mouseX > rx + rw && mouseX < rx + rw + 10 && mouseY > ry + rh / 2 - 5 && mouseY < ry + rh / 2 + 5) {
            cursor(CROSS);
        } else {
            cursor(ARROW);
        }
    }

    @Override
    public void mouseDragged() {
        switch (mouseButton) {
            case LEFT:
                if (isDraggedRect) {
                    rx = mouseX - dx;
                    ry = mouseY - dy;
                }

                //corners
                if (isResizeRect_top_left) {
                    ry = mouseY + 5;
                    rh = rh - (mouseY - resize_py);
                    resize_py = mouseY;
                    rx = mouseX + 5;
                    rw = rw - (mouseX - resize_px);
                    resize_px = mouseX;
                }
                if (isResizeRect_top_right) {
                    ry = mouseY + 5;
                    rh = rh - (mouseY - resize_py);
                    resize_py = mouseY;
                    rx = mouseX - rw - 5;
                    rw = rw - (resize_px - mouseX);
                    resize_px = mouseX;
                }
                if (isResizeRect_bottom_left) {
                    ry = mouseY - rh - 5;
                    rh = rh - (resize_py - mouseY);
                    resize_py = mouseY;
                    rx = mouseX + 5;
                    rw = rw - (mouseX - resize_px);
                    resize_px = mouseX;
                }
                if (isResizeRect_bottom_right) {
                    ry = mouseY - rh - 5;
                    rh = rh - (resize_py - mouseY);
                    resize_py = mouseY;
                    rx = mouseX - rw - 5;
                    rw = rw - (resize_px - mouseX);
                    resize_px = mouseX;
                }

                if (isResizeRectY_top) {
                    ry = mouseY + 10;
                    rh = rh - (mouseY - resize_py);
                    resize_py = mouseY;
                }
                if (isResizeRectY_bottom) {
                    rh = rh - (resize_py - mouseY);
                    resize_py = mouseY;
                }
                if (isResizeRectX_left) {
                    rx = mouseX + 10;
                    rw = rw - (mouseX - resize_px);
                    resize_px = mouseX;
                }
                if (isResizeRectX_right) {
                    rw = rw - (resize_px - mouseX);
                    resize_px = mouseX;
                }
                break;
            case RIGHT:
                if (isDraggedRect) {
                    rx = mouseX - dx;
                    ry = mouseY - dy;
                }
                break;
            case CENTER:
                if (isDraggedRect) {
                    rx = mouseX - dx;
                    ry = mouseY - dy;
                }
                break;
            default:
                break;
        }
    }

    public static VideoSnapProcessing load(FrameVideoSnap ref, String path) {
        frame_ref = ref;
        ref_frame_panel_width = frame_ref.getPanel().getWidth();
        ref_frame_panel_height = frame_ref.getPanel().getHeight();
        pt = new VideoSnapProcessing();
        movie = new Movie(pt, path);
        ps = pt.initSurface();
        ps.setSize(100, 100);
        ps.startThread();
        return pt;
    }

    private void adjustMovieSize(PImage img) {
        float w_ratio = 1.0f * ref_frame_panel_width / img.width;
        float h_ratio = 1.0f * ref_frame_panel_height / img.height;
        isMovieSizeAdjust = true;
        if (w_ratio >= 1.0f && h_ratio >= 1.0f) {
            isMovieSizeAdjust = false;
            adaptive_width = img.width;
            adaptive_height = img.height;
            System.out.println("n0 need adjust size:" + adaptive_width + "," + adaptive_height);
            return;
        }

        if (w_ratio < 1.0f && h_ratio >= 1.0f) {
            adaptive_width = (int) (img.width * w_ratio);
            adaptive_height = (int) (img.height * w_ratio);
        } else if (w_ratio >= 1.0f && h_ratio < 1.0f) {
            adaptive_width = (int) (img.width * h_ratio);
            adaptive_height = (int) (img.height * h_ratio);
        } else {
            if (w_ratio <= h_ratio) {
                adaptive_width = (int) (img.width * w_ratio);
                adaptive_height = (int) (img.height * w_ratio);
            } else {
                adaptive_width = (int) (img.width * h_ratio);
                adaptive_height = (int) (img.height * h_ratio);
            }
        }
        System.out.println("adjust size:" + adaptive_width + "," + adaptive_height);
    }

}
