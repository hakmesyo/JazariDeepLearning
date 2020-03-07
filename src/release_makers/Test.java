/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package release_makers;


/**
 *
 * @author BAP1
 */
public class Test {
    public static void main(String[] args) {
        ForMakers mp=new ForMakers();
        int[] q=mp.getA();
        System.out.println("q[0] = " + q[0]);
        q[0]=21;
        System.out.println("q[0] = " + q[0]);
    }
}
