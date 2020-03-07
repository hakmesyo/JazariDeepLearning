package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import utils.FactoryUtils;

public class PanelPicture extends JPanel {

    private BufferedImage currBufferedImage;
    private JRadioButtonMenuItem items[];
    private final JPopupMenu popupMenu = new JPopupMenu();
    private int fromLeft = 10;
    private int fromTop = 30;
    private int w;
    private int h;
    private int size = 0;

    public void setPanelSize(int w, int h) {
        this.w = w;
        this.h = h;
        this.setSize(w, h);
        repaint();
    }

    public void setImage(BufferedImage image) {
        currBufferedImage = image;
        if (currBufferedImage != null) {
            double aspect_ratio = 640.0 / 480.0;
            size = (int) (h * aspect_ratio)+1;
//            currBufferedImage = FactoryUtils.resizeAspectRatio(image, size, size);

//            Rectangle rect=new Rectangle((currBufferedImage.getHeight()-h)/2,(currBufferedImage.getWidth()-w)/2,  w,h);
//            currBufferedImage=FactoryUtils.cropImage(currBufferedImage, rect);
//            System.out.println(getImageSize());
            //currBufferedImage = FactoryUtils.resizeAspectRatio(currBufferedImage, size, size);
        }
        repaint();
    }

    public void setSquareCropSize(int n) {
        size = n;
    }

    public BufferedImage getImage() {
        return currBufferedImage;
    }

    @Override
    public void paint(Graphics gr) {
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.setColor(Color.GREEN);
        int wPanel = this.getWidth();
        int hPanel = this.getHeight();
        if (currBufferedImage != null) {
            int wImg = currBufferedImage.getWidth();
            int hImg = currBufferedImage.getHeight();
            fromLeft = (wPanel - wImg) / 2;
            fromTop = (hPanel - hImg) / 2;
            gr.drawImage(currBufferedImage, fromLeft, fromTop, this);

            gr.setColor(Color.blue);
            gr.drawRect(fromLeft, fromTop, currBufferedImage.getWidth(), currBufferedImage.getHeight());

            this.paintComponents(gr);
        }
        gr.setColor(Color.red);
        gr.drawRect(0, 0, wPanel - 1, hPanel - 1);
        gr.drawRect(1, 1, wPanel - 3, hPanel - 3);
    }

    public String getImageSize() {
        String str = "[" + currBufferedImage.getHeight() + "," + currBufferedImage.getWidth() + "] ";
        return str;
    }

    public void saveImage(String fileName) {
        FactoryUtils.saveImage(currBufferedImage, fileName);
    }

}
