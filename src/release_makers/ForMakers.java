/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

önemli:
1- dist teki lib klasörünün altına processing/videos altındaki windows64 klasörünü eklemelisin çünkü jna bunlara ihtiyaç duyuyor.
 */
package release_makers;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gui.CameraSetting;
import gui.FrameVideoSnap;
import gui.PanelPicture;
import gui.PanelWebCam;
import gui.Settings;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;
import static jazari.FactoryStrategies.executePythonScript;
import utils.FactoryUtils;
import utils.SerialLib;
import utils.SerialType;
import utils.SocketServer;
import webcam.VideoCap;

/**
 *
 * @author BAP1
 */
public class ForMakers extends javax.swing.JFrame implements SerialPortEventListener {

    {
        System.setProperty("jna.library.path", "processing_3.5.3\\video\\windows64");
    }
    private int[] a = {1, 2, 3};

    final int[] getA() {
        return a;
    }

    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {
        "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0", // Raspberry Pi
        "/dev/ttyUSB0", // Linux
        "COM6", // Windows for solenoid valve
        "COM7" // 
    };
    private SerialPort serialPort;
    private SerialLib slib = new SerialLib(this);
    private SerialType st = null;

    private boolean flag_btn_data_source = false;
    private boolean flag_btn_output_format = false;
    private boolean flag_btn_execute = false;
    private int second_layer_width = 270;
    private DefaultListModel list_model = new DefaultListModel();
//    private Webcam webcam = null;
    public BufferedImage bf = null;
    public List<String> list_class_labels = new ArrayList<>();
    private boolean flag_webcam_stop = false;
    private String comPort = null;
    private static VideoCap webcam = new VideoCap(0,640,480);
    static int prev_cam_id=0;
    static int prev_cam_width=640;
    static int prev_cam_height=480;

    /**
     * Creates new form ForMakers
     */
    public ForMakers() {
        initComponents();
        panel_data_source.setVisible(false);
        //panel_learning_model.setVisible(false);
        panel_output_format.setVisible(false);
        btn_execute.setEnabled(false);
        btn_data_source.setEnabled(true);
        btn_data_source.setSize(180, 150);
        DefaultCaret caret = (DefaultCaret) txt_log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        list_attempts.setModel(list_model);
        FactoryUtils.startJavaServer();
        FactoryUtils.delay(1000);
        tabbed_pane_view.setEnabledAt(0, false);
        tabbed_pane_view.setEnabledAt(1, false);
        tabbed_pane_view.setSelectedIndex(2);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        List<Webcam> lst = Webcam.getWebcams();
//        if (lst.size() > 0) {
//            ComboBoxModel model = new DefaultComboBoxModel(lst.toArray());
//            combo_cam.setModel(model);
//        } else {
//            JOptionPane.showMessageDialog(this, "No connected cameras found");
//        }
        getPanel().setPanelSize(getPanel().getWidth(), getPanel().getHeight());
        getPanel().setSquareCropSize(224);
        tabbedpane_dataset_source.setEnabledAt(1, false);
        tabbedpane_dataset_source.setEnabledAt(2, false);
        tabbedpane_dataset_source.setEnabledAt(3, false);
        txt_out_pins.setVisible(false);
        jLabel1.setVisible(false);
        btn_browse.setVisible(false);
        btn_videoSnap.setVisible(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (FactoryUtils.isNetAvailable()) {
                            lbl_internet_status.setBackground(Color.green);
                        } else {
                            lbl_internet_status.setBackground(Color.red);
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ForMakers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        group_data_source = new javax.swing.ButtonGroup();
        group_model = new javax.swing.ButtonGroup();
        group_output_format = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        btn_data_source = new javax.swing.JButton();
        btn_model = new javax.swing.JButton();
        btn_execute = new javax.swing.JButton();
        panel_data_source = new javax.swing.JPanel();
        radio_webcam = new javax.swing.JRadioButton();
        radio_folder = new javax.swing.JRadioButton();
        radio_in_arduino = new javax.swing.JRadioButton();
        radio_video = new javax.swing.JRadioButton();
        radio_in_arduino1 = new javax.swing.JRadioButton();
        lbl_test_train = new javax.swing.JLabel();
        btn_browse = new javax.swing.JButton();
        btn_videoSnap = new javax.swing.JButton();
        panel_learning_model = new javax.swing.JPanel();
        radio_test = new javax.swing.JRadioButton();
        radio_train = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_internet_status = new javax.swing.JLabel();
        btn_output = new javax.swing.JButton();
        panel_output_format = new javax.swing.JPanel();
        radio_out_arduino = new javax.swing.JRadioButton();
        radio_graphic = new javax.swing.JRadioButton();
        radio_out_websocket = new javax.swing.JRadioButton();
        radio_text = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txt_out_pins = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        scroll_pane_attempts = new javax.swing.JScrollPane();
        list_attempts = new javax.swing.JList<>();
        tabbed_pane_view = new javax.swing.JTabbedPane();
        panel_dataset = new javax.swing.JPanel();
        tabbedpane_dataset_source = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        panel_webcam = new PanelPicture();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        chk_flip = new javax.swing.JCheckBox();
        btn_connectCam = new javax.swing.JToggleButton();
        btn_add_new_class = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_project_name = new javax.swing.JTextField();
        btn_camSettings = new javax.swing.JButton();
        tabbed_pane_class_labels = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        panel_training_canvas = new javax.swing.JPanel();
        panel_results = new javax.swing.JPanel();
        scroll_pane_log = new javax.swing.JScrollPane();
        txt_log = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jazari Inference Machine for Makers V.1.0");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setEnabled(false);

        btn_data_source.setBackground(new java.awt.Color(255, 153, 153));
        btn_data_source.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_data_source.setIcon(new javax.swing.ImageIcon(getClass().getResource("/release_makers/icons/data_set.png"))); // NOI18N
        btn_data_source.setText("<html>Veri Kaynağı<br><center></center></html>");
        btn_data_source.setEnabled(false);
        btn_data_source.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_data_source.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_data_source.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_data_sourceActionPerformed(evt);
            }
        });

        btn_model.setBackground(new java.awt.Color(204, 255, 204));
        btn_model.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_model.setIcon(new javax.swing.ImageIcon(getClass().getResource("/release_makers/icons/learning_model.png"))); // NOI18N
        btn_model.setText("<html>Öğrenme Tipi<br><center></center></html>");
        btn_model.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_model.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_model.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modelActionPerformed(evt);
            }
        });

        btn_execute.setBackground(new java.awt.Color(153, 153, 255));
        btn_execute.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_execute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/release_makers/icons/execute.png"))); // NOI18N
        btn_execute.setText("Değerlendir");
        btn_execute.setEnabled(false);
        btn_execute.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_execute.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_execute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_executeActionPerformed(evt);
            }
        });

        panel_data_source.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 102)));

        group_data_source.add(radio_webcam);
        radio_webcam.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_webcam.setSelected(true);
        radio_webcam.setText("Webcam");
        radio_webcam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_webcamItemStateChanged(evt);
            }
        });
        radio_webcam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_webcamActionPerformed(evt);
            }
        });

        group_data_source.add(radio_folder);
        radio_folder.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_folder.setText("Veri Klasörü");
        radio_folder.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_folderItemStateChanged(evt);
            }
        });

        group_data_source.add(radio_in_arduino);
        radio_in_arduino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_in_arduino.setText("Arduino/Sensör");
        radio_in_arduino.setEnabled(false);
        radio_in_arduino.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_in_arduinoItemStateChanged(evt);
            }
        });
        radio_in_arduino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_in_arduinoActionPerformed(evt);
            }
        });
        radio_in_arduino.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                radio_in_arduinoPropertyChange(evt);
            }
        });

        group_data_source.add(radio_video);
        radio_video.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_video.setText("Video");
        radio_video.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_videoItemStateChanged(evt);
            }
        });

        group_data_source.add(radio_in_arduino1);
        radio_in_arduino1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_in_arduino1.setText("Web Socket");
        radio_in_arduino1.setEnabled(false);
        radio_in_arduino1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_in_arduino1ItemStateChanged(evt);
            }
        });
        radio_in_arduino1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_in_arduino1ActionPerformed(evt);
            }
        });
        radio_in_arduino1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                radio_in_arduino1PropertyChange(evt);
            }
        });

        lbl_test_train.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_test_train.setText("Input for TEST");

        btn_browse.setText("...");

        btn_videoSnap.setText("...");
        btn_videoSnap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_videoSnapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_data_sourceLayout = new javax.swing.GroupLayout(panel_data_source);
        panel_data_source.setLayout(panel_data_sourceLayout);
        panel_data_sourceLayout.setHorizontalGroup(
            panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_data_sourceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_data_sourceLayout.createSequentialGroup()
                        .addGroup(panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(radio_folder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(radio_video, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_browse, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_videoSnap, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(panel_data_sourceLayout.createSequentialGroup()
                        .addGroup(panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radio_webcam, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radio_in_arduino)
                            .addComponent(radio_in_arduino1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panel_data_sourceLayout.createSequentialGroup()
                .addComponent(lbl_test_train)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_data_sourceLayout.setVerticalGroup(
            panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_data_sourceLayout.createSequentialGroup()
                .addComponent(lbl_test_train)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_webcam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_video)
                    .addComponent(btn_videoSnap))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_data_sourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_folder)
                    .addComponent(btn_browse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_in_arduino, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_in_arduino1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        panel_learning_model.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 204, 0)));
        panel_learning_model.setForeground(new java.awt.Color(0, 204, 0));

        group_model.add(radio_test);
        radio_test.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_test.setSelected(true);
        radio_test.setText("Önceden Eğitilmiş");
        radio_test.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_testItemStateChanged(evt);
            }
        });
        radio_test.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_testActionPerformed(evt);
            }
        });

        group_model.add(radio_train);
        radio_train.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_train.setText("Sıfırdan Eğitilecek");
        radio_train.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_trainItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("TEST or TRAIN");

        jLabel7.setText("Internet Connection Status");

        lbl_internet_status.setBackground(new java.awt.Color(255, 0, 0));
        lbl_internet_status.setOpaque(true);

        javax.swing.GroupLayout panel_learning_modelLayout = new javax.swing.GroupLayout(panel_learning_model);
        panel_learning_model.setLayout(panel_learning_modelLayout);
        panel_learning_modelLayout.setHorizontalGroup(
            panel_learning_modelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_learning_modelLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_learning_modelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_learning_modelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_internet_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_learning_modelLayout.createSequentialGroup()
                        .addGroup(panel_learning_modelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radio_train, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radio_test, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(0, 33, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_learning_modelLayout.setVerticalGroup(
            panel_learning_modelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_learning_modelLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_internet_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_test)
                .addGap(18, 18, 18)
                .addComponent(radio_train)
                .addGap(16, 16, 16))
        );

        btn_output.setBackground(new java.awt.Color(153, 255, 255));
        btn_output.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_output.setIcon(new javax.swing.ImageIcon(getClass().getResource("/release_makers/icons/output.png"))); // NOI18N
        btn_output.setText("Çıktı Formatı");
        btn_output.setEnabled(false);
        btn_output.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_output.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_output.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_outputActionPerformed(evt);
            }
        });

        panel_output_format.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        group_output_format.add(radio_out_arduino);
        radio_out_arduino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_out_arduino.setText("Arduino");
        radio_out_arduino.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_out_arduinoItemStateChanged(evt);
            }
        });
        radio_out_arduino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_out_arduinoActionPerformed(evt);
            }
        });

        group_output_format.add(radio_graphic);
        radio_graphic.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_graphic.setText("Grafik");
        radio_graphic.setEnabled(false);
        radio_graphic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_graphicActionPerformed(evt);
            }
        });

        group_output_format.add(radio_out_websocket);
        radio_out_websocket.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_out_websocket.setText("Web Socket");
        radio_out_websocket.setEnabled(false);
        radio_out_websocket.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_out_websocketItemStateChanged(evt);
            }
        });
        radio_out_websocket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_out_websocketActionPerformed(evt);
            }
        });

        group_output_format.add(radio_text);
        radio_text.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radio_text.setSelected(true);
        radio_text.setText("Text");
        radio_text.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radio_textItemStateChanged(evt);
            }
        });
        radio_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_textActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Output");

        txt_out_pins.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_out_pins.setText("D8,D9");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("PINS:");

        javax.swing.GroupLayout panel_output_formatLayout = new javax.swing.GroupLayout(panel_output_format);
        panel_output_format.setLayout(panel_output_formatLayout);
        panel_output_formatLayout.setHorizontalGroup(
            panel_output_formatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_output_formatLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_output_formatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_output_formatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(radio_out_websocket, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addGroup(panel_output_formatLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(txt_out_pins, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(radio_out_arduino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radio_text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radio_graphic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_output_formatLayout.setVerticalGroup(
            panel_output_formatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_output_formatLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_graphic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_out_websocket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_out_arduino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_output_formatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_out_pins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        list_attempts.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        scroll_pane_attempts.setViewportView(list_attempts);

        tabbed_pane_view.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tabbedpane_dataset_source.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        panel_webcam.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_webcam.setPreferredSize(new java.awt.Dimension(800, 467));

        javax.swing.GroupLayout panel_webcamLayout = new javax.swing.GroupLayout(panel_webcam);
        panel_webcam.setLayout(panel_webcamLayout);
        panel_webcamLayout.setHorizontalGroup(
            panel_webcamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_webcamLayout.setVerticalGroup(
            panel_webcamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Kamerayı Seç");

        chk_flip.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chk_flip.setText("flip");

        btn_connectCam.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_connectCam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/release_makers/icons/start.png"))); // NOI18N
        btn_connectCam.setText("Başlat");
        btn_connectCam.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_connectCam.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_connectCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectCamActionPerformed(evt);
            }
        });

        btn_add_new_class.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_add_new_class.setIcon(new javax.swing.ImageIcon(getClass().getResource("/release_makers/icons/add_new.png"))); // NOI18N
        btn_add_new_class.setText("Yeni Sınıf Etiketi Ekle");
        btn_add_new_class.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_add_new_class.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_add_new_class.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_new_classActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Proje Adı:");

        txt_project_name.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_project_name.setForeground(new java.awt.Color(255, 0, 0));
        txt_project_name.setText("Nesne_tespit_1");
        txt_project_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_project_nameActionPerformed(evt);
            }
        });

        btn_camSettings.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_camSettings.setText("Settings");
        btn_camSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_camSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_connectCam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chk_flip))
                    .addComponent(btn_add_new_class, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_project_name))
                    .addComponent(btn_camSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(chk_flip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_camSettings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_connectCam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_project_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_add_new_class)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        tabbed_pane_class_labels.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_webcam, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbed_pane_class_labels, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabbed_pane_class_labels)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(panel_webcam, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabbedpane_dataset_source.addTab("Kamera", jPanel5);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 982, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 671, Short.MAX_VALUE)
        );

        tabbedpane_dataset_source.addTab("Ses", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 982, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 671, Short.MAX_VALUE)
        );

        tabbedpane_dataset_source.addTab("Leap Motion", jPanel8);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 982, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 671, Short.MAX_VALUE)
        );

        tabbedpane_dataset_source.addTab("Arduino", jPanel10);

        javax.swing.GroupLayout panel_datasetLayout = new javax.swing.GroupLayout(panel_dataset);
        panel_dataset.setLayout(panel_datasetLayout);
        panel_datasetLayout.setHorizontalGroup(
            panel_datasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedpane_dataset_source)
        );
        panel_datasetLayout.setVerticalGroup(
            panel_datasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedpane_dataset_source)
        );

        tabbed_pane_view.addTab("Veri Girişi", panel_dataset);

        javax.swing.GroupLayout panel_training_canvasLayout = new javax.swing.GroupLayout(panel_training_canvas);
        panel_training_canvas.setLayout(panel_training_canvasLayout);
        panel_training_canvasLayout.setHorizontalGroup(
            panel_training_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );
        panel_training_canvasLayout.setVerticalGroup(
            panel_training_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        tabbed_pane_view.addTab("Derin Öğrenme Katmanları", panel_training_canvas);

        txt_log.setColumns(20);
        txt_log.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txt_log.setRows(5);
        scroll_pane_log.setViewportView(txt_log);

        jButton1.setText("Temizle");

        jButton2.setText("Kaydet");

        javax.swing.GroupLayout panel_resultsLayout = new javax.swing.GroupLayout(panel_results);
        panel_results.setLayout(panel_resultsLayout);
        panel_resultsLayout.setHorizontalGroup(
            panel_resultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_pane_log, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
            .addGroup(panel_resultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        panel_resultsLayout.setVerticalGroup(
            panel_resultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_resultsLayout.createSequentialGroup()
                .addComponent(scroll_pane_log, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_resultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(10, 10, 10))
        );

        tabbed_pane_view.addTab("Sonuç Raporu", panel_results);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Denemeler");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_output, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_model, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_execute, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_data_source, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_data_source, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_learning_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scroll_pane_attempts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(panel_output_format, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbed_pane_view)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tabbed_pane_view)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_model, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(panel_learning_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_data_source, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_data_source, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_output, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(panel_output_format, javax.swing.GroupLayout.PREFERRED_SIZE, 151, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_execute, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scroll_pane_attempts))))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_data_sourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_data_sourceActionPerformed
//        flag_btn_data_source = !flag_btn_data_source;
        flag_btn_data_source = true;
        panel_data_source.setVisible(flag_btn_data_source);
        //btn_execute.setEnabled(flag_btn_learning_model && flag_btn_data_source && flag_btn_output_format);
        btn_output.setEnabled(flag_btn_data_source);
        //panel_output_format.setVisible(flag_btn_learning_model && flag_btn_output_format);
        btn_browse.setVisible(false);
        btn_videoSnap.setVisible(false);
    }//GEN-LAST:event_btn_data_sourceActionPerformed

    private void btn_modelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modelActionPerformed
    }//GEN-LAST:event_btn_modelActionPerformed

    private void radio_webcamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_webcamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_webcamActionPerformed

    private void btn_executeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_executeActionPerformed
        if (!FactoryUtils.isNetAvailable()) {
            FactoryUtils.showMessage("warning:Internet connection must be established beforehand");
            return;
        }
        scroll_pane_attempts.setVisible(true);
//        scroll_pane_log.setVisible(true);
        if (!FactoryUtils.running.get()) {
            FactoryUtils.startJavaServer();
            FactoryUtils.delay(1000);
        }
        txt_log.setVisible(true);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        FactoryUtils.nAttempts++;
        list_model.addElement("Deneme " + FactoryUtils.nAttempts);
        btn_execute.setEnabled(false);
        this.revalidate();
        execute();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ForMakers.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ForMakers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!SocketServer.isClientConnectedNow) {
                        btn_execute.setEnabled(true);
                        break;
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_btn_executeActionPerformed

    private void radio_testActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_testActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_testActionPerformed

    private void btn_outputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_outputActionPerformed
//        flag_btn_output_format = !flag_btn_output_format;
        flag_btn_output_format = true;
        panel_output_format.setVisible(flag_btn_output_format);
        btn_execute.setEnabled(flag_btn_data_source && flag_btn_output_format);
        if (flag_btn_output_format) {
            if (!panel_data_source.isVisible()) {
                setSize(getWidth() + second_layer_width, this.getHeight());
            }
        } else {
            if (!panel_data_source.isVisible()) {
                setSize(getWidth() - second_layer_width, this.getHeight());
            }
        }

    }//GEN-LAST:event_btn_outputActionPerformed

    private void radio_out_arduinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_out_arduinoActionPerformed

    }//GEN-LAST:event_radio_out_arduinoActionPerformed

    private void radio_graphicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_graphicActionPerformed
    }//GEN-LAST:event_radio_graphicActionPerformed

    private void radio_in_arduinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_in_arduinoActionPerformed

    }//GEN-LAST:event_radio_in_arduinoActionPerformed

    private void radio_in_arduinoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_radio_in_arduinoPropertyChange

    }//GEN-LAST:event_radio_in_arduinoPropertyChange

    private void radio_in_arduinoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_in_arduinoItemStateChanged
    }//GEN-LAST:event_radio_in_arduinoItemStateChanged

    private void radio_out_arduinoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_out_arduinoItemStateChanged
        jLabel1.setVisible(radio_out_arduino.isSelected());
        txt_out_pins.setVisible(radio_out_arduino.isSelected());
        if (radio_out_arduino.isSelected()) {
            comPort = FactoryUtils.inputBox("Arduinoyu bağladığınız com portunu yazınız", "COM3");
            if (comPort != null) {
                if (st != null) {
                    st.serialPort.removeEventListener();
                    st.serialPort.close();
                    st = null;
                    System.out.println("B.Serial Port was closed successfully");
                }
                st = slib.serialInitialize(serialPort, comPort, 9600);
                if (st != null) {
                    FactoryUtils.showMessage("Arduino ile " + comPort + " üzerinden bağlantı sağlanmıştır");
                } else {
                    FactoryUtils.showMessage("Bağlantı kurulamadı com portunu yanlış olabilir veya arduino usb girişine bağlanmadı");
                }

            }
        }
    }//GEN-LAST:event_radio_out_arduinoItemStateChanged

    private void radio_trainItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_trainItemStateChanged
        if (radio_train.isSelected()) {
            btn_execute.setText("Eğit");
            btn_data_source.setEnabled(true);
            btn_output.setEnabled(false);
            btn_execute.setEnabled(false);
            panel_data_source.setVisible(false);
            panel_output_format.setVisible(false);
            tabbed_pane_view.setEnabledAt(0, true);
            tabbed_pane_view.setEnabledAt(1, true);
            tabbed_pane_view.setSelectedIndex(0);
//            panel_output_format.setVisible(false);
//            scroll_pane_attempts.setVisible(false);
//            btn_output.setEnabled(false);
//            tabbed_pane_view.setEnabledAt(0, true);
//            tabbed_pane_view.setEnabledAt(1, true);
//            tabbed_pane_view.setSelectedIndex(0);
//            lbl_test_train.setText("Input for TRAIN");
//            if (!panel_data_source.isVisible()) {
//                btn_data_source.doClick();
//            }
        }
//        else {
//            btn_execute.setText("Değerlendir");
//            if (panel_data_source.isVisible()) {
//                btn_output.setEnabled(true);
//            }
//            tabbed_pane_view.setEnabledAt(0, false);
//            tabbed_pane_view.setEnabledAt(1, false);
//            tabbed_pane_view.setSelectedIndex(2);
//        }
    }//GEN-LAST:event_radio_trainItemStateChanged

    private void radio_webcamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_webcamItemStateChanged
        if (radio_train.isSelected()) {
            if (radio_webcam.isSelected()) {
                tabbedpane_dataset_source.setEnabledAt(0, true);
                tabbedpane_dataset_source.setSelectedIndex(0);
                tabbedpane_dataset_source.setEnabledAt(1, false);
                tabbedpane_dataset_source.setEnabledAt(2, false);
                tabbedpane_dataset_source.setEnabledAt(3, false);
            }
        }
    }//GEN-LAST:event_radio_webcamItemStateChanged

    
    public static void updateCamera(int id,int width,int height){
        prev_cam_id=id;
        prev_cam_width=width;
        prev_cam_height=height;
        if (webcam!=null) {
            webcam.closeCamera();
        }        
        webcam=new VideoCap(id, width, height);        
    }
    
    private void btn_connectCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectCamActionPerformed
        if (btn_connectCam.isSelected()) {
            btn_camSettings.setEnabled(false);
            btn_connectCam.setText("Durdur");
            flag_webcam_stop = false;
            if (webcam==null) {
                updateCamera(prev_cam_id, prev_cam_width, prev_cam_height);
            }
            if (webcam != null) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    public Void doInBackground() {
                        return getLiveVideoStream();
                    }
                };
                worker.execute();
            }
        } else {
            btn_camSettings.setEnabled(true);
            btn_connectCam.setText("Başlat");
            flag_webcam_stop = true;
        }

    }//GEN-LAST:event_btn_connectCamActionPerformed

    private void btn_add_new_classActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_new_classActionPerformed
        int n = tabbed_pane_class_labels.getTabCount();
        String str = FactoryUtils.inputBox("Sınıf Etiketi", "class_" + (n + 1));
        if (isClassNameValid(str)) {
            double aspr=1.0*prev_cam_width/prev_cam_height;
            tabbed_pane_class_labels.addTab(str, new PanelWebCam(this, txt_project_name.getText(), str,aspr));
            tabbed_pane_class_labels.setSelectedIndex(n);
        }

    }//GEN-LAST:event_btn_add_new_classActionPerformed

    private void txt_project_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_project_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_project_nameActionPerformed

    private void radio_out_websocketItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_out_websocketItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_out_websocketItemStateChanged

    private void radio_out_websocketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_out_websocketActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_out_websocketActionPerformed

    private void radio_in_arduino1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_in_arduino1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_in_arduino1ItemStateChanged

    private void radio_in_arduino1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_in_arduino1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_in_arduino1ActionPerformed

    private void radio_in_arduino1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_radio_in_arduino1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_in_arduino1PropertyChange

    private void radio_videoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_videoItemStateChanged
        if (radio_train.isSelected()) {
            btn_videoSnap.setVisible(radio_video.isSelected());
            if (radio_video.isSelected()) {
                tabbedpane_dataset_source.setSelectedIndex(3);
                tabbedpane_dataset_source.setEnabledAt(0, false);
                tabbedpane_dataset_source.setEnabledAt(1, false);
                tabbedpane_dataset_source.setEnabledAt(2, false);
                tabbedpane_dataset_source.setEnabledAt(3, false);
            }
        } else {
            btn_videoSnap.setVisible(false);
        }
    }//GEN-LAST:event_radio_videoItemStateChanged

    private void radio_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_textActionPerformed

    private void radio_testItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_testItemStateChanged
        if (radio_test.isSelected()) {
            btn_execute.setText("Değerlendir");
            lbl_test_train.setText("Input for TEST");
            btn_data_source.setEnabled(true);
            btn_output.setEnabled(false);
            btn_execute.setEnabled(false);
            panel_data_source.setVisible(false);
            panel_output_format.setVisible(false);
            tabbed_pane_view.setEnabledAt(0, false);
            tabbed_pane_view.setEnabledAt(1, false);
            tabbed_pane_view.setEnabledAt(2, true);
            tabbed_pane_view.setSelectedIndex(2);
        }
    }//GEN-LAST:event_radio_testItemStateChanged

    private void radio_textItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_textItemStateChanged
//        if (radio_text.isSelected()) {
//            st = null;
//        }
    }//GEN-LAST:event_radio_textItemStateChanged

    private void radio_folderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radio_folderItemStateChanged
        if (radio_train.isSelected()) {
            btn_browse.setVisible(radio_folder.isSelected());
            if (radio_folder.isSelected()) {
                tabbedpane_dataset_source.setSelectedIndex(3);
                tabbedpane_dataset_source.setEnabledAt(0, false);
                tabbedpane_dataset_source.setEnabledAt(1, false);
                tabbedpane_dataset_source.setEnabledAt(2, false);
                tabbedpane_dataset_source.setEnabledAt(3, false);
            }
        } else {
            btn_browse.setVisible(false);
        }
    }//GEN-LAST:event_radio_folderItemStateChanged

    private void btn_videoSnapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_videoSnapActionPerformed
        new FrameVideoSnap().setVisible(true);
    }//GEN-LAST:event_btn_videoSnapActionPerformed

    private void btn_camSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_camSettingsActionPerformed
        new CameraSetting(prev_cam_id,prev_cam_width,prev_cam_height).setVisible(true);
    }//GEN-LAST:event_btn_camSettingsActionPerformed

    private Void getLiveVideoStream() {
        bf = null;
        while (true) {
            if (flag_webcam_stop) {
                bf = null;
                getPanel().setImage(null);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ForMakers.class.getName()).log(Level.SEVERE, null, ex);
                }
                webcam.closeCamera(); 
                webcam=null;
                return null;
            } else {
                bf = webcam.getOneFrame();
                if (chk_flip.isSelected()) {
                    bf = FactoryUtils.flipVertical(bf);
                }
                getPanel().setImage(bf);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ForMakers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public PanelPicture getPanel() {
        return (PanelPicture) panel_webcam;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ForMakers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForMakers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForMakers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForMakers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForMakers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_new_class;
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_camSettings;
    private javax.swing.JToggleButton btn_connectCam;
    private javax.swing.JButton btn_data_source;
    private javax.swing.JButton btn_execute;
    private javax.swing.JButton btn_model;
    private javax.swing.JButton btn_output;
    private javax.swing.JButton btn_videoSnap;
    private javax.swing.JCheckBox chk_flip;
    private javax.swing.ButtonGroup group_data_source;
    private javax.swing.ButtonGroup group_model;
    private javax.swing.ButtonGroup group_output_format;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lbl_internet_status;
    private javax.swing.JLabel lbl_test_train;
    private javax.swing.JList<String> list_attempts;
    private javax.swing.JPanel panel_data_source;
    private javax.swing.JPanel panel_dataset;
    private javax.swing.JPanel panel_learning_model;
    private javax.swing.JPanel panel_output_format;
    private javax.swing.JPanel panel_results;
    private javax.swing.JPanel panel_training_canvas;
    private javax.swing.JPanel panel_webcam;
    private javax.swing.JRadioButton radio_folder;
    private javax.swing.JRadioButton radio_graphic;
    private javax.swing.JRadioButton radio_in_arduino;
    private javax.swing.JRadioButton radio_in_arduino1;
    private javax.swing.JRadioButton radio_out_arduino;
    private javax.swing.JRadioButton radio_out_websocket;
    private javax.swing.JRadioButton radio_test;
    private javax.swing.JRadioButton radio_text;
    private javax.swing.JRadioButton radio_train;
    private javax.swing.JRadioButton radio_video;
    private javax.swing.JRadioButton radio_webcam;
    private javax.swing.JScrollPane scroll_pane_attempts;
    private javax.swing.JScrollPane scroll_pane_log;
    private javax.swing.JTabbedPane tabbed_pane_class_labels;
    private javax.swing.JTabbedPane tabbed_pane_view;
    private javax.swing.JTabbedPane tabbedpane_dataset_source;
    private javax.swing.JTextArea txt_log;
    private javax.swing.JTextField txt_out_pins;
    private javax.swing.JTextField txt_project_name;
    // End of variables declaration//GEN-END:variables

    private void execute() {
        if (radio_webcam.isSelected()) {
            if (radio_test.isSelected()) {
                Settings.modelPath = FactoryUtils.browseKerasModel(FactoryUtils.getDefaultDirectory() + "\\models\\python", "Select Keras h5 Model");
                int dt = 0;
                executePythonScript(st, txt_out_pins, txt_log, "webcam", false, dt);
            }
        } else if (radio_folder.isSelected()) {
            if (radio_test.isSelected()) {
                Settings.modelPath = FactoryUtils.browseKerasModel(FactoryUtils.getDefaultDirectory() + "\\models\\python", "Select Keras h5 Model");
                int dt = 0;
                executePythonScript(st, txt_out_pins, txt_log, "folder", false, dt);
            }
        }
    }

    public JTabbedPane getClassLabelTabbedPane() {
        return tabbed_pane_class_labels;
    }

    public JToggleButton getWebCamButton() {
        return btn_connectCam;
    }

    private boolean isClassNameValid(String str) {
        int n = tabbed_pane_class_labels.getTabCount();
        for (int i = 0; i < n; i++) {
            if (tabbed_pane_class_labels.getTitleAt(i).equals(str)) {
                FactoryUtils.showMessage("Aynı isimde sınıf ismi var değişik bir isim yazmalısınız");
                return false;
            }
        }
        return true;
    }

    int m = 0;

    @Override
    public void serialEvent(SerialPortEvent spe) {
        if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = st.input.readLine();
                System.out.println("from arduino = " + inputLine + " toplam:" + (++m));

//                if (inputLine.equals("9")) {
//                    System.out.println("received msg:" + inputLine);
//                }
            } catch (Exception e) {
                System.err.println("err:" + e.toString());
            }
        }
    }

    private void loadVideo() {
//        File video_file=FactoryUtils.browseFile();
//        if (video_file!=null) {
//            ((PanelVideo)panel_video).isVideoStart=true;
//        }else{
//            ((PanelVideo)panel_video).isVideoStart=false;
//        }
    }

}
