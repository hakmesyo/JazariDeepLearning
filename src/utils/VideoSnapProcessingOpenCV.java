/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import gui.FrameVideoSnap;
import gui.PanelPict;
import gui.PanelVideoSnap;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import processing.awt.PSurfaceAWT;
import video.VideoReadProcess;

public class VideoSnapProcessingOpenCV extends JPanel {

    static VideoSnapProcessingOpenCV pt;
    static FrameVideoSnap frame_ref;
    boolean runOnce = false;
    boolean isRecord = false;
    static VideoCapture movie;
    static BufferedImage img;
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
    private BufferedImage latestImage;
    static float duration;
    static float fps;
    static int ref_frame_panel_width;
    static int ref_frame_panel_height;
    int size = 100;
    private boolean isCtrlPressed;
    private int adaptive_width = 0;
    private int adaptive_height = 0;
    private boolean isMovieSizeAdjust;
    private Mat frame;
    int current_frame_count = 0;

    public VideoSnapProcessingOpenCV() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void setPath(FrameVideoSnap ref, String path) {
        frame = new Mat();
        frame_ref = ref;
        ref_frame_panel_width = frame_ref.getPanel().getWidth();
        ref_frame_panel_height = frame_ref.getPanel().getHeight();
        movie = new VideoCapture(path);
        duration = (float) movie.get(Videoio.CAP_PROP_FRAME_COUNT);
        fps = (float) movie.get(Videoio.CAP_PROP_FPS);
        System.out.println("duration:" + duration);
        movie.read(frame);
        img = FactoryUtils.toBufferedImage(frame);
        adjustMovieSize(img);
        latestImage = img;
        frame_ref.getPanel().setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        frame_ref.getPanel().removeAll();
        System.out.println("referans frame's panel default w,h:" + ref_frame_panel_width + "," + ref_frame_panel_height);
        System.out.println("movie w,h:" + img.getWidth() + "," + img.getHeight());
        System.out.println(FactoryUtils.getDefaultDirectory() + "");
        latency=1000/fps;
        mouseEventHandler();
        keyboardEventHandler();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    if (movie.read(frame)) {
//                        img = FactoryUtils.toBufferedImage(frame);
//                        current_frame_count++;
//                        repaint();
//                    }
//                    if (fps < 40) {
//                        try {
//                            Thread.sleep(1000 / (int) fps);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(VideoReadProcess.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            }
//        }).start();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
            int n = e.getScrollAmount();
            frame_ref.getSlider().setValue(frame_ref.getSlider().getValue() - n);
        } else {
            // pass the event on to the scroll pane
            getParent().dispatchEvent(e);
        }
    }

    long prev_t = System.currentTimeMillis();
    float latency=0f;

    @Override
    public void paint(Graphics gr) {
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.setColor(Color.GREEN);
        int wPanel = this.getWidth();
        int hPanel = this.getHeight();
//        if (movie != null && isRoiPressed) {
        if (movie != null && movie.isOpened()) {
            if (movie.read(frame) && img != null) {
                if (fps < 40) {
                    if (System.currentTimeMillis() - prev_t > latency) {
                        prev_t = System.currentTimeMillis();
                        img = FactoryUtils.toBufferedImage(frame);
                        current_frame_count++;
                        frame_ref.getBar().setValue((int) (current_frame_count / duration * 100) + 1);
                        gr.drawImage(img, 0, 0, this);
                        if (isCtrlPressed) {
                            PanelVideoSnap pan_snap = (PanelVideoSnap) frame_ref.getClassLabelTabbedPane().getSelectedComponent();
                            if (pan_snap != null) {
                                PanelPict pp = new PanelPict();
                                pp.setPreferredSize(new Dimension(size, size));
                                img = img.getSubimage(rx, ry, rw, rh);
                                pp.setImage((BufferedImage) img, size);
                                JPanel pan = pan_snap.getCurrentPanel();
                                pan.add(pp, 0);
                                FactoryUtils.saveImage(img, "/dataset/images/captured_video/" + (k++) + ".jpg");
                                pan.revalidate();
                            }
                        }
                        drawROI(gr);
                        not_avaliable_counter = 0;
                    }
                }
            }
        }
        gr.drawImage(img, 0, 0, this);
        drawROI(gr);
    }

    private void drawROI(Graphics gr) {
        gr.setColor(Color.yellow);

        //corners
        gr.drawRect(rx - 10, ry - 10, 10, 10);
        gr.drawRect(rx + rw, ry - 10, 10, 10);
        gr.drawRect(rx - 10, ry + rh, 10, 10);
        gr.drawRect(rx + rw, ry + rh, 10, 10);

        //edges
        gr.drawRect(rx + rw / 2 - 5, ry - 12, 10, 10);
        gr.drawRect(rx - 12, ry + rh / 2, 10, 10);
        gr.drawRect(rx + rw / 2 - 5, ry + rh, 10, 10);
        gr.drawRect(rx + rw, ry + rh / 2, 10, 10);

        gr.drawRect(rx - 2, ry - 2, rw + 2, rh + 2);
        gr.drawString("Rect(" + rx + "," + ry + "," + rw + "," + rh + ")", rx + rw / 4, ry + rh + 24);
    }

    private void adjustMovieSize(BufferedImage img) {
        float w_ratio = 1.0f * ref_frame_panel_width / img.getWidth();
        float h_ratio = 1.0f * ref_frame_panel_height / img.getHeight();
        isMovieSizeAdjust = true;
        if (w_ratio >= 1.0f && h_ratio >= 1.0f) {
            isMovieSizeAdjust = false;
            adaptive_width = img.getWidth();
            adaptive_height = img.getHeight();
            System.out.println("n0 need adjust size:" + adaptive_width + "," + adaptive_height);
            return;
        }

        if (w_ratio < 1.0f && h_ratio >= 1.0f) {
            adaptive_width = (int) (img.getWidth() * w_ratio);
            adaptive_height = (int) (img.getHeight() * w_ratio);
        } else if (w_ratio >= 1.0f && h_ratio < 1.0f) {
            adaptive_width = (int) (img.getWidth() * h_ratio);
            adaptive_height = (int) (img.getHeight() * h_ratio);
        } else {
            if (w_ratio <= h_ratio) {
                adaptive_width = (int) (img.getWidth() * w_ratio);
                adaptive_height = (int) (img.getHeight() * w_ratio);
            } else {
                adaptive_width = (int) (img.getWidth() * h_ratio);
                adaptive_height = (int) (img.getHeight() * h_ratio);
            }
        }
        System.out.println("adjust size:" + adaptive_width + "," + adaptive_height);
    }

    private void keyboardEventHandler() {
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_CONTROL) {
                    isCtrlPressed = true;
                    System.out.println("ctrlye basıldı");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_CONTROL) {
                    isCtrlPressed = false;
                    System.out.println("ctrlden çekildi");
                }
            }
        });
    }

    private void mouseEventHandler() {
        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (e.getX() > rx && e.getX() < rx + rw && e.getY() > ry && e.getY() < ry + rh) {
                        dx = e.getX() - rx;
                        dy = e.getY() - ry;
                        isDraggedRect = true;
                        isRoiPressed = true;
                        fps = (frame_ref.video_speed);
                        playForward();
                    } else {
                        isDraggedRect = false;
                    }

                    //corners
                    if (e.getX() > rx - 10 && e.getX() < rx && e.getY() > ry - 10 && e.getY() < ry) {
                        isResizeRect_top_left = true;
                        resize_px = e.getX();
                        resize_py = e.getY();
                    } else {
                        isResizeRect_top_left = false;
                    }
                    if (e.getX() > rx + rw && e.getX() < rx + rw + 10 && e.getY() > ry - 10 && e.getY() < ry) {
                        isResizeRect_top_right = true;
                        resize_px = e.getX();
                        resize_py = e.getY();
                    } else {
                        isResizeRect_top_right = false;
                    }
                    if (e.getX() > rx - 10 && e.getX() < rx && e.getY() > ry + rh && e.getY() < ry + rh + 10) {
                        isResizeRect_bottom_left = true;
                        resize_px = e.getX();
                        resize_py = e.getY();
                    } else {
                        isResizeRect_bottom_left = false;
                    }
                    if (e.getX() > rx + rw && e.getX() < rx + rw + 10 && e.getY() > ry + rh && e.getY() < ry + rh + 10) {
                        isResizeRect_bottom_right = true;
                        resize_px = e.getX();
                        resize_py = e.getY();
                    } else {
                        isResizeRect_bottom_right = false;
                    }

                    //edges
                    if (e.getX() > rx + rw / 2 - 5 && e.getX() < rx + rw / 2 + 5 && e.getY() > ry - 10 && e.getY() < ry) {
                        isResizeRectY_top = true;
                        resize_py = e.getY();
                    } else {
                        isResizeRectY_top = false;
                    }
                    if (e.getX() > rx + rw / 2 - 5 && e.getX() < rx + rw / 2 + 5 && e.getY() > ry + rh && e.getY() < ry + rh + 10) {
                        isResizeRectY_bottom = true;
                        resize_py = e.getY();
                    } else {
                        isResizeRectY_bottom = false;
                    }
                    if (e.getX() > rx - 10 && e.getX() < rx && e.getY() > ry + rh / 2 - 5 && e.getY() < ry + rh / 2 + 5) {
                        isResizeRectX_left = true;
                        resize_px = e.getX();
                    } else {
                        isResizeRectX_left = false;
                    }
                    if (e.getX() > rx + rw && e.getX() < rx + rw + 10 && e.getY() > ry + rh / 2 - 5 && e.getY() < ry + rh / 2 + 5) {
                        isResizeRectX_right = true;
                        resize_px = e.getX();
                    } else {
                        isResizeRectX_right = false;
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if (e.getX() > rx && e.getX() < rx + rw && e.getY() > ry && e.getY() < ry + rh) {
                        dx = e.getX() - rx;
                        dy = e.getY() - ry;
                        isDraggedRect = true;
                        isRoiPressed = true;
                        fps = (frame_ref.video_speed);
                        playBackward();
                    } else {
                        isDraggedRect = false;
                    }
                }
            }

            public void mouseReleased(java.awt.event.MouseEvent e) {
                isRoiPressed = false;
                //movie.pause();
            }

            private void playForward() {
            }

            private void playBackward() {
            }

        });

        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseMoved(java.awt.event.MouseEvent e) {
                //inside the roi
                if (e.getX() > rx && e.getX() < rx + rw && e.getY() > ry && e.getY() < ry + rh) {
                    new Cursor(Cursor.MOVE_CURSOR);
                } //corners
                else if (e.getX() > rx - 10 && e.getX() < rx && e.getY() > ry - 10 && e.getY() < ry) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } else if (e.getX() > rx + rw && e.getX() < rx + rw + 10 && e.getY() > ry - 10 && e.getY() < ry) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } else if (e.getX() > rx - 10 && e.getX() < rx && e.getY() > ry + rh && e.getY() < ry + rh + 10) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } else if (e.getX() > rx + rw && e.getX() < rx + rw + 10 && e.getY() > ry + rh && e.getY() < ry + rh + 10) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } //edges
                else if (e.getX() > rx + rw / 2 - 5 && e.getX() < rx + rw / 2 + 5 && e.getY() > ry - 10 && e.getY() < ry) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } else if (e.getX() > rx + rw / 2 - 5 && e.getX() < rx + rw / 2 + 5 && e.getY() > ry + rh && e.getY() < ry + rh + 10) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } else if (e.getX() > rx - 10 && e.getX() < rx && e.getY() > ry + rh / 2 - 5 && e.getY() < ry + rh / 2 + 5) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } else if (e.getX() > rx + rw && e.getX() < rx + rw + 10 && e.getY() > ry + rh / 2 - 5 && e.getY() < ry + rh / 2 + 5) {
                    new Cursor(Cursor.CROSSHAIR_CURSOR);
                } else {
                    new Cursor(Cursor.DEFAULT_CURSOR);
                }
                //repaint();
            }

            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (isDraggedRect) {
                        rx = e.getX() - dx;
                        ry = e.getY() - dy;
                    }

                    //corners
                    if (isResizeRect_top_left) {
                        ry = e.getY() + 5;
                        rh = rh - (e.getY() - resize_py);
                        resize_py = e.getY();
                        rx = e.getX() + 5;
                        rw = rw - (e.getX() - resize_px);
                        resize_px = e.getX();
                    }
                    if (isResizeRect_top_right) {
                        ry = e.getY() + 5;
                        rh = rh - (e.getY() - resize_py);
                        resize_py = e.getY();
                        rx = e.getX() - rw - 5;
                        rw = rw - (resize_px - e.getX());
                        resize_px = e.getX();
                    }
                    if (isResizeRect_bottom_left) {
                        ry = e.getY() - rh - 5;
                        rh = rh - (resize_py - e.getY());
                        resize_py = e.getY();
                        rx = e.getX() + 5;
                        rw = rw - (e.getX() - resize_px);
                        resize_px = e.getX();
                    }
                    if (isResizeRect_bottom_right) {
                        ry = e.getY() - rh - 5;
                        rh = rh - (resize_py - e.getY());
                        resize_py = e.getY();
                        rx = e.getX() - rw - 5;
                        rw = rw - (resize_px - e.getX());
                        resize_px = e.getX();
                    }

                    if (isResizeRectY_top) {
                        ry = e.getY() + 10;
                        rh = rh - (e.getY() - resize_py);
                        resize_py = e.getY();
                    }
                    if (isResizeRectY_bottom) {
                        rh = rh - (resize_py - e.getY());
                        resize_py = e.getY();
                    }
                    if (isResizeRectX_left) {
                        rx = e.getX() + 10;
                        rw = rw - (e.getX() - resize_px);
                        resize_px = e.getX();
                    }
                    if (isResizeRectX_right) {
                        rw = rw - (resize_px - e.getX());
                        resize_px = e.getX();
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if (isDraggedRect) {
                        rx = e.getX() - dx;
                        ry = e.getY() - dy;
                    }
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    if (isDraggedRect) {
                        rx = e.getX() - dx;
                        ry = e.getY() - dy;
                    }
                }
                repaint();
//                System.out.println("dragged");
            }
        });
    }

}
