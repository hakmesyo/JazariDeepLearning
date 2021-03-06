/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import test.TestEmbedProcessingFrame;
import utils.FactoryUtils;
import utils.WrapLayout;

/**
 *
 * @author BAP1
 */
public class PanelVideoSnap extends javax.swing.JPanel {

    FrameVideoSnap frm;
    private boolean mousePressed;
    private List<BufferedImage> list_image = new ArrayList();
    private String tab_title;
    private String project_name;

    /**
     * Creates new form PanelWebCam
     *
     * @param ref
     */
    public PanelVideoSnap(FrameVideoSnap ref, String pr_name, String title) {
        project_name = pr_name;
        tab_title = title;
        frm = ref;
        initComponents();
        panel.setLayout(new WrapLayout());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbl_adet = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_delay = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                panelComponentAdded(evt);
            }
        });
        jScrollPane1.setViewportView(panel);

        btn_save.setText("Kaydet");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_delete.setText("Sil");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        jLabel1.setText("Adet:");

        lbl_adet.setText("0");

        jLabel3.setText("bekleme:");

        txt_delay.setText("10");

        jLabel4.setText("ms");

        jLabel5.setText("Yakalamak için CTRL'ye basılı tutarak ROI'yi drag ediniz");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_adet, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_delay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_delay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(lbl_adet))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        frm.getClassLabelTabbedPane().remove(this);
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        saveImages();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void panelComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_panelComponentAdded
        lbl_adet.setText(""+panel.getComponents().length);
    }//GEN-LAST:event_panelComponentAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_adet;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField txt_delay;
    // End of variables declaration//GEN-END:variables

    int size = 100;

//    private void acquireImage() {
//        mousePressed = true;
//        int delay = Integer.parseInt(txt_delay.getText());
//        new Thread() {
//            public void run() {
//                while (mousePressed) {
//                    if (frm.getWebCamButton().isSelected()) {
//                        PanelPict pp = new PanelPict();
//                        pp.setPreferredSize(new Dimension(size, size));
//                        pp.setImage(frm.bf, size);
//                        panel.add(pp, 0);
//                        
//                        double aspect_ratio = 640.0 / 480.0;
//                        int sz = (int) (224 * aspect_ratio) + 1;
//                        BufferedImage img = FactoryUtils.resizeAspectRatio(frm.bf, sz, sz);
//                        Rectangle rect = new Rectangle((img.getHeight() - 224) / 2, (img.getWidth() - 224) / 2, 224, 224);
//                        img = FactoryUtils.cropImage(img, rect);
////                        System.out.println(img.getWidth() + "," + img.getHeight());
//                        list_image.add(0, img);
//                        jLabel2.setText(panel.getComponentCount() + "");
//                        panel.revalidate();
//                    }
//                    try {
//                        Thread.sleep(delay);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(PanelVideoSnap.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//
//        }.start();
//    }

    private void saveImages() {
        String dir_path = "dataset\\" + project_name + "\\" + tab_title;
        if (FactoryUtils.isFolderExist(dir_path)) {
            if (FactoryUtils.confirmDialog("Hedef klasör mevcut üzerine yazılacak devam edilsin mi?") == 0) {
                saveAllImages(dir_path);
            }
        } else {
            if (!FactoryUtils.isFolderExist("dataset\\" + project_name)) {
                FactoryUtils.makeDirectory("dataset\\" + project_name);
            }
            FactoryUtils.makeDirectory(dir_path);
            saveAllImages(dir_path);
        }
    }

    private void saveAllImages(String dir_path) {
        String name = "img_";
        int k = 0;
        for (BufferedImage img : list_image) {
            name = "img_" + (k++) + ".jpg";
            FactoryUtils.saveImage(img, dir_path + "\\" + name);
        }
    }
    
    public JPanel getCurrentPanel(){
        return panel;
    }
}
