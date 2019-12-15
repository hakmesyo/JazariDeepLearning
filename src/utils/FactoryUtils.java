/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import jazari.CallBackInterface;
import jazari.CallableImageInterface;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

/**
 *
 * @author BAP1
 */
public class FactoryUtils {
    public static boolean stopServer = false;
    public static boolean isConnectPythonServer = false;
    public static SocketServer s;
    public static WebSocketClient client;
    public static String currDir = System.getProperty("user.dir");
    public static final AtomicBoolean running = new AtomicBoolean(false);

    public static String getDefaultDirectory() {
        String workingDir = System.getProperty("user.dir");
        return workingDir;
    }
    
    public static void startJavaServer(CallBackInterface cb) {
        new Thread(() -> {
            try {
                int port = 8887;
                s = new SocketServer(port,cb);
                s.start();
                System.out.println("Java WebSocket Server started on port: " + s.getPort());
                BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
                running.set(true);
                while (running.get()) {
                    try {
                        if (sysin.ready()) {
                            String in;
                            try {
                                in = sysin.readLine();
//                                s.broadcast(in);
                                if (in.equals("exit")) {
                                    try {
                                        s.stop(1000);
                                        System.out.println("Java Server is stopping");
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    break;
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        Thread.sleep(1);
                    } catch (IOException ex) {
                        Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                System.out.println("Java Server was stopped");
                s.stop();
            } catch (UnknownHostException ex) {
                Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    public static void stopWebsocketServer() {
        System.out.println("stop server command stop server flag is true");
        stopServer = true;
    }

    public static WebSocketClient connectPythonServer() {
        isConnectPythonServer = true;
        try {
            client = new WebSocketClient(new URI("ws://localhost:8888"), new Draft_6455()) {

                @Override
                public void onMessage(String message) {
                    System.out.println("incoming message from python server = " + message);
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("You connected to python server: " + getURI() + "\n");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason + "\n");
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("Can't connect to python server:8888");
//                    new Thread(() -> {
//                        System.out.println("Remote Python Server is starting now");
//                        executeCommand("python " + FactoryUtils.currDir + "\\scripts\\python\\server.py");
//                    }).start();
//                    System.out.println("Exception occured but it was fixed...\n" + ex + "\n");
//                    isConnectPythonServer = false;
//                    System.out.println("düzeltmek için bir daha deniyor");
//                    connectPythonServer();
//                    isConnectPythonServer = false;
                }
            };
            client.connect();
            
//            if (isConnectPythonServer) {
//                client.connect();
//            }

        } catch (URISyntaxException ex) {
            System.out.println("ws://localhost:8888" + " is not a valid WebSocket URI\n");
            isConnectPythonServer = false;
        }
        return client;
    }

    public static void delay(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void executeCommand(String cmd) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Run this on Windows, cmd, /c = terminate after this run
//        processBuilder.command("cmd.exe", "/c", "ping -n 3 google.com");
        //processBuilder.command("cmd.exe","","cd D:\Anaconda3\envs\cpu");
//        processBuilder.command("cmd.exe", "", "cd D:\\Anaconda3\\envs\\cpu");
//        processBuilder.command("cmd.exe", "/c", "python "+cmd);
//        processBuilder.directory(new File("C:\\Users\\DELL LAB\\Envs\\dl4c_cpu"));
//        processBuilder.command("cmd.exe", "workon dl4c_cpu");
//        processBuilder.command("cmd.exe","/c","workon dl4c_cpu"," & ", cmd);
//        processBuilder.command("cmd.exe", "/c", "python --version");
//        processBuilder.command("cmd.exe", "/c", cmd);
        processBuilder.command("cmd.exe", "/c", cmd);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
            if (exitCode == 1) {
                BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorString = error.readLine();
                while (errorString != null) {
                    System.out.println("errorString = " + errorString);
                    errorString = error.readLine();
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeToFile(String row) {
        writeToFile(getFileFromChooserSave(getDefaultDirectory()).getAbsolutePath(), row);
    }

    public static void writeToFile(String row, boolean currentDir) {
        if (currentDir) {
            writeToFile(getFileFromChooser(getDefaultDirectory()).getAbsolutePath(), row);
        } else {
            writeToFile(getFileFromChooser().getAbsolutePath(), row);
        }

    }

    public static void writeToFile(String path, String row) {
        Writer out;
        try {
            try {
                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(path), "UTF-8"));
                try {
                    try {
                        out.write(row);
                    } catch (IOException ex) {
                        Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } finally {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static File getFileFromChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("save as file");
        chooser.setSize(new java.awt.Dimension(45, 37)); // Generated
//        chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        File file = null;
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return file;
        }
        return file;
    }

    public static File getFileFromChooser(String folderPath) {
        JFileChooser chooser = new JFileChooser(folderPath);
        chooser.setDialogTitle("save as file");
        chooser.setSize(new java.awt.Dimension(45, 37)); // Generated
//        chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        File file = null;
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return file;
        }
        return file;
    }

    public static File getFileFromChooserSave() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("save as file");
        chooser.setSize(new java.awt.Dimension(45, 37)); // Generated
//        chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        File file = null;
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return file;
        }
        return file;
    }

    public static File getFileFromChooserSave(String folderPath) {
        JFileChooser chooser = new JFileChooser(folderPath);
        chooser.setDialogTitle("save as file");
        chooser.setSize(new java.awt.Dimension(45, 37)); // Generated
//        chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        File file = null;
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return file;
        }
        return file;
    }

    public static File getFileFromChooserLoad() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("select file");
        chooser.setSize(new java.awt.Dimension(45, 37)); // Generated
//        chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        File file = null;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return file;
        }
        return file;
    }

    public static File getFileFromChooserLoad(String folderPath) {
        JFileChooser chooser = new JFileChooser(folderPath);
        chooser.setDialogTitle("select file");
        chooser.setSize(new java.awt.Dimension(45, 37)); // Generated
//        chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        File file = null;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return file;
        }
        return file;
    }

    public static File browseDirectory() {
        return browseDirectory(getDefaultDirectory());
    }

    public static File browseDirectory(String path) {
        JFileChooser chooser = new JFileChooser(path);
        chooser.setDialogTitle("Browse Directory");
        chooser.setSize(new java.awt.Dimension(45, 37)); // Generated
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        File file = null;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
        } else {
            System.out.println("No Selection ");
        }
        return file;
    }

    public static File browseFile() {
        return getFileFromChooserLoad();
    }

    public static File browseFile(String path) {
        return getFileFromChooserLoad(path);
    }

    public static String[] clone(String[] s) {
        String[] ret=new String[s.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i]=s[i];
        }
        return ret;
    }
    
    public static long tic() {
        long currentTime = System.nanoTime();
        return currentTime;
    }

    public static long toc(String msg, long tic) {
        long toc = System.nanoTime();
        double elapsed = (toc - tic) / (1000000.0d);
        System.out.println(msg + elapsed + " miliSecond");
        return toc;
    }

    public static long toc(long tic) {
        long toc = System.nanoTime();
        double elapsed = (toc - tic) / (1000000.0d);
        System.out.println("Elapsed Time:" + formatDouble(elapsed) + " miliSecond");
        return toc;
    }
    
    public static double formatDouble(double num) {
        double q = 0;
        try {
            DecimalFormat df = new DecimalFormat("#.000");
            q = Double.parseDouble(df.format(num).replace(",", "."));
        } catch (Exception e) {
//            e.printStackTrace();
            return -10000000000000.0;
        }
        return q;
    }
    
    public static File[] getDirectories(String path){
        File[] directories = new File(path).listFiles(File::isDirectory);
        return directories;
    }
    
    public static File[] getFiles(String path){
        File[] files = new File(path).listFiles(File::isFile);
        return files;
    }
}
