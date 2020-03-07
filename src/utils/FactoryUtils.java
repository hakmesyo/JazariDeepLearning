/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import gui.Settings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import interfaces.InterfaceCallBack;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import static java.util.Collections.list;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;
import org.opencv.core.Mat;
//import oshi.SystemInfo;
//import oshi.hardware.CentralProcessor;
//import oshi.hardware.ComputerSystem;
//import oshi.hardware.HardwareAbstractionLayer;
//import oshi.software.os.OperatingSystem;

/**
 *
 * @author BAP1
 */
public class FactoryUtils {

    public static boolean stopServer = false;
    public static boolean isConnectPythonServer = false;
    public static SocketServer server;
    public static WebSocketClient client;
    public static String currDir = System.getProperty("user.dir");
    public static final AtomicBoolean running = new AtomicBoolean(false);
    public static InterfaceCallBack icbf = null;
    public static int nAttempts = 0;
    private static final String key = String.valueOf(new char[]{'2', '6', 'k', 'o', 'z', 'Q', 'a', 'K', 'w', 'R', 'u', 'N', 'J', '2', '4', 't'});
    private static final String initVector = String.valueOf(new char[]{'8', 'C', '7', '7', 'E', 'E', 'F', 'E', '9', '8', '6', '8', 'F', '5', '4', '8'});

    public static String getMacAddress() {
        InetAddress ip;
        StringBuilder sb = new StringBuilder();;
        try {
            ip = InetAddress.getLocalHost();
//            System.out.println("Current IP address : " + ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            System.out.print("Current MAC address : ");
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
//            System.out.println(sb.toString());
            return sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getIPAddress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ip.getHostAddress();
    }

//    public static String generateLicenseKey() {
//        SystemInfo systemInfo = new SystemInfo();
//        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
//        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
//        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
//        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();
//
//        String vendor = operatingSystem.getManufacturer();
//        String processorSerialNumber = computerSystem.getSerialNumber();
//        String processorIdentifier = centralProcessor.getIdentifier();
//        int processors = centralProcessor.getLogicalProcessorCount();
//
//        String delimiter = "#";
//
//        return vendor
//                + delimiter
//                + processorSerialNumber
//                + delimiter
//                + processorIdentifier
//                + delimiter
//                + processors
//                + delimiter
//                + getMacAddress();
//    }
    public static final String getSerialNumber() {
        String sn = null;
        OutputStream os = null;
        InputStream is = null;

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec(new String[]{"wmic", "bios", "get", "serialnumber"});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        os = process.getOutputStream();
        is = process.getInputStream();

        try {
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = new Scanner(is);
        try {
            while (sc.hasNext()) {
                String next = sc.next();
                if ("SerialNumber".equals(next)) {
                    sn = sc.next().trim();
                    break;
                }
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (sn == null) {
            throw new RuntimeException("Cannot find computer SN");
        }

        return sn;
    }

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String getREST(String urlPath, String command) {
        String ret = "";
        try {
//            URL url = new URL("http://localhost/jim/restfulPHP.php?wonderName=Taj%20Mahal");//your url i.e fetch data from .
            URL url = new URL(urlPath + "/" + command);//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                ret = output;
//                System.out.println(output);
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return ret;
    }

    public static boolean isNetAvailable() {
        try {
            final URL url = new URL("http://www.cezerilab.com/");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public static String getDefaultDirectory() {
        String workingDir = System.getProperty("user.dir");
        return workingDir;
    }

    public static void startJavaServer() {
        new Thread(() -> {
            try {
                int port = 8887;
                server = new SocketServer(port);
                server.start();
                System.out.println("Java WebSocket Server started on port: " + server.getPort());
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
                                        server.stop(1000);
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
                server.stop();
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

    public static WebSocketClient connectToPythonServer(InterfaceCallBack cb) {
        icbf = cb;
        isConnectPythonServer = true;
        try {
            client = new WebSocketClient(new URI("ws://localhost:8888"), new Draft_6455()) {

                @Override
                public void onMessage(String message) {
//                    System.out.println("incoming message from python server = " + message);
                    icbf.onMessageReceived(message);
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
                }
            };
            client.connect();
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

    public static void executeCommand(String program, String command, boolean isClosed) {
//        ExecutorService pool = Executors.newSingleThreadExecutor();
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
        if (isClosed) {
            processBuilder.command(program, "/c", command);
        } else {
            processBuilder.command(program, command);
        }
//
//        try {
//
//            Process process = processBuilder.start();
//
//            System.out.println("cmd activated");
//            ProcessReadTask task = new ProcessReadTask(process.getInputStream());
//            Future<List<String>> future = pool.submit(task);
//
//            List<String> result = future.get(5, TimeUnit.SECONDS);
//            for (String s : result) {
//                System.out.println(s);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.shutdown();
//        }

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

    public static String browseKerasModel(String path, String title) {
        File model_file = FactoryUtils.browseFile(path, title);
        if (model_file != null) {
            Settings.modelPath = model_file.getAbsolutePath();
        }
        return Settings.modelPath;
    }

    public static void browseJSModel(String path) {
        File model_file = FactoryUtils.browseDirectory(path);
        if (model_file != null) {
            Settings.modelPath = model_file.getAbsolutePath();
        }
    }

    public static void showMessage(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    private static class ProcessReadTask implements Callable<List<String>> {

        private InputStream inputStream;

        public ProcessReadTask(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public List<String> call() {
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.toList());
        }
    }

    public static void executeCmdCommand(String cmd) {
        executeCommand("cmd.exe", cmd, true);
    }

    public static void executeCmdCommand(String cmd, boolean isClosedAfter) {
        executeCommand("cmd.exe", cmd, isClosedAfter);
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

    public static File getFileFromChooserLoad(String path) {
        JFileChooser chooser = new JFileChooser(currDir + "/dataset/" + path);
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

    public static File getFileFromChooserLoad(String folderPath, String title) {
        JFileChooser chooser = new JFileChooser(folderPath);
        chooser.setDialogTitle(title);
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
        return getFileFromChooserLoad("");
    }

    public static File browseVideoFile() {
        return getFileFromChooserLoad("videos");
    }

    public static File browseFile(String path, String title) {
        return getFileFromChooserLoad(path, title);
    }

    public static String[] clone(String[] s) {
        String[] ret = new String[s.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = s[i];
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

    public static File[] getDirectories(String path) {
        File[] directories = new File(path).listFiles(File::isDirectory);
        return directories;
    }

    public static File[] getFiles(String path) {
        File[] files = new File(path).listFiles(File::isFile);
        return files;
    }

    public static double[][] imageToPixelsDouble(BufferedImage img) {
        if (img == null) {
            return null;
        }
        double[][] original = new double[img.getHeight()][img.getWidth()]; // where we'll put the image
        if ((img.getType() == BufferedImage.TYPE_CUSTOM)
                || (img.getType() == BufferedImage.TYPE_INT_RGB)
                || (img.getType() == BufferedImage.TYPE_INT_ARGB)
                || (img.getType() == BufferedImage.TYPE_3BYTE_BGR)
                || (img.getType() == BufferedImage.TYPE_4BYTE_ABGR)) {
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    original[i][j] = img.getRGB(j, i);
                }
            }
        } else {
            Raster image_raster = img.getData();
            //get pixel by pixel
            int[] pixel = new int[1];
            int[] buffer = new int[1];

            // declaring the size of arrays
            original = new double[img.getHeight()][img.getWidth()];

            //get the image in the array
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    pixel = image_raster.getPixel(j, i, buffer);
                    original[i][j] = pixel[0];
                }
            }
        }
        return original;
    }

    public static void saveImageWithFormat(BufferedImage img, String path, String fileType) {
        File outputFile = new File(path);
        try {
            ImageIO.write(img, fileType, outputFile);
        } catch (IOException ex) {
            Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean saveImage(BufferedImage img, String fileName) {
        File file = new File(fileName);
        String extension = FactoryUtils.getFileExtension(fileName);
        boolean ret = false;
        try {
            ret = ImageIO.write(img, extension, file);
        } catch (IOException ex) {
            Logger.getLogger(FactoryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public static String getFileExtension(File file) {
        String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('.') + 1);
        return extension;
    }

    public static String getFileExtension(String str) {
        String extension = str.substring(str.lastIndexOf('.') + 1);
        return extension;
    }

    public static BufferedImage flipVertical(BufferedImage image) {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
        BufferedImage bf = buildTransformed(image, at);

//        BufferedImage bf = new BufferedImage(
//                image.getWidth(), image.getHeight(),
//                BufferedImage.TYPE_3BYTE_BGR);
//        Graphics2D g = image.createGraphics();
//        g.drawImage(image , 0,0,-image.getWidth(),image.getHeight(),null);
        return bf;
    }

    public static BufferedImage buildTransformed(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     *
     * @param src - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    public static BufferedImage resizeAspectRatio(BufferedImage src, int w, int h) {
        int finalw = w;
        int finalh = h;
        double factor = 1.0d;
        if (src.getWidth() > src.getHeight()) {
            factor = ((double) src.getHeight() / (double) src.getWidth());
            finalh = (int) (finalw * factor);
        } else {
            factor = ((double) src.getWidth() / (double) src.getHeight());
            finalw = (int) (finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();

        BufferedImage img = new BufferedImage(finalw, finalh, BufferedImage.TYPE_3BYTE_BGR);
        g2 = img.createGraphics();
        g2.drawImage(resizedImg, 0, 0, finalw, finalh, null);
        g2.dispose();

//        return resizedImg;
        return img;
    }

    public static String inputBox(String msg, String def) {
        return JOptionPane.showInputDialog(msg, def);
    }

    public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        if (src.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            return src.getSubimage(rect.y, rect.x, rect.width, rect.height);
        } else {
            return toBGR(src.getSubimage(rect.y, rect.x, rect.width, rect.height));
        }
    }

    public static BufferedImage toBGR(BufferedImage image) {
        return toNewColorSpace(image, BufferedImage.TYPE_3BYTE_BGR);
    }

    final public static BufferedImage toNewColorSpace(BufferedImage image, int newType) {
        BufferedImage ret = null;
        try {
            ret = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    newType);
            ColorConvertOp xformOp = new ColorConvertOp(null);
            xformOp.filter(image, ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean isFolderExist(String dirName) {
        File theDir = new File(dirName);
        if (theDir.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFileExist(String path) {
        File fl = new File(path);
        if (fl.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static void makeDirectory(String fn) {
        new File(fn).mkdir();
    }

    public static int confirmDialog(String msg) {
        return JOptionPane.showConfirmDialog(null, msg);
    }

    public static String readFile(String fileName) {
        String ret = "";
        File file = new File(fileName);
        if (!file.exists()) {
            showMessage(fileName + " isminde bir dosya yok");
            return ret;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                ret = ret + s + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ret;
        }
        return ret;
    }

    public static void sendDataToSerialPort(SerialType st, String s1) {
        String s = s1 + "\n";
        try {
            st.output.write(s.getBytes());
            st.output.flush();
            System.out.println("message was sent to arduino:" + s1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static BufferedImage toBufferedImage(Mat matrix) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        byte[] b = new byte[matrix.channels() * matrix.cols() * matrix.rows()];
        matrix.get(0, 0, b);
        BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}
