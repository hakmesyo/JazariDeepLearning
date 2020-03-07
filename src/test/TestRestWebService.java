/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


//import class_encryption.FactorySerial;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import utils.FactoryUtils;

public class TestRestWebService {
    public static void main(String[] args) {
//        test_1();
        test_2();
    }

    private static void test_1() {
        try {

            URL url = new URL("http://localhost/jim/rest.php?wonderName=Taj%20Mahal");//your url i.e fetch data from .
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
                System.out.println(output);
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

    private static void test_2() {
        String msg=FactoryUtils.encrypt("inference:webcam");
        System.out.println("from java encrypted msg = " + msg);
        System.out.println("decrypted message:"+FactoryUtils.decrypt(msg));
        String str=FactoryUtils.getREST("http://inferencemachine.cezerilab.com/rest", "rest.php?msg="+msg);
        System.out.println("gelen mesaj:"+str+" decrypt hali:"+FactoryUtils.decrypt(str));
        if (str.equals("2hVMTsSoQYoTjr8VXO8pfQ==")) {
            String mac_address=FactoryUtils.getMacAddress();            
            System.out.println("ok mac address is "+mac_address+ " ip:"+FactoryUtils.getIPAddress());
//            String serial_no=FactorySerial.getDeviceSerial();
//            System.out.println("hardware serial number:"+serial_no);
//            System.out.println("AES hardware serial number:"+FactoryUtils.encrypt(serial_no));
        }
        
//        System.out.println("açılmış hali:"+FactoryUtils.decrypt("rgspP+87YgoGb4CJqE2zmNW6+F2oAu1yEkwgwL+8OIMlDP0SbohQAMyYRFy13Fa3oUAsiH4RYXaQDqeZ+5feO3CEOtLKWddlKP7hTE+zBJ7ej8kforvuwv0SUvQtarSrqFEpAFofrE4mKLpfTdrdPhLDWAAg7IJZEqJgMb9e0Ous6PSzLGV6porWLloz9ED0pkQB/KMfAW21BtP3hDDdqvhl0GRfavgAZImZcCdNRJ36lz6X8h84Fkp+KrtwHAhk2uoZ7h5OMP6Fz29m7y7wrjKzRsQT3Dy4Pqs7whkp7nFJ7jaJtpCWYglfjhzvVCc5T0nyu9AbP4jUvY4bxdSYTnxJT5/R7OuDrF0fgThESOxN335k6kB+Cph91dl9Pciecvqq9BEkL4GUFmzwkAVihhB1Yp/x+psXk77LZPO72Q2jYKRkhFB+pm5ZOj8nRPjuLRsSuO0zrU895ryko7Cot/1lywvBT4a/hl3FePa5N97g81DZ+SRG5gLhr+6V2iEl/M0juCkVw+88WTw1dKuer+Vf0GMkF7Z91shyt2w8eA4="));
    }
}