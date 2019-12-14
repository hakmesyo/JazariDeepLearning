
import jazari.BackEnd;
import jazari.DataSource;
import jazari.JIM;
import jazari.ProgrammingLanguage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BAP1
 */
public class TestUsage {
    private static void doSomething(String str){
        System.out.println("str = " + str);
    }

//     * for python backend please pip install those in cmd window. First of all download latest
//     * python 64 bit version (python 3.7.5 date:12.12.2019) and install properly
//     * and allow add path variable and restart the pc. Then install those libraries with pip 
//     * 1- cmd: python -m pip install –upgrade pip 
//     * 2- cmd: pip install tensorflow 
//     * 3- cmd: pip install tensorflow-gpu (optional) 
//     * 4- cmd: pip install opencv-python 
//     * 5- cmd: pip install websocket-client 
//     * 6- cmd: pip install websocket-server 
//     * 7- cmd: pip install pillow 
//     * 8- cmd: pip install matplotlib 
//     * 9- cmd: pip install keras

    public static void main(String[] args) {

        JIM.Test_CNN_TransferLearningModel(
                ProgrammingLanguage.PYTHON,
                BackEnd.CPU,
                "models\\keras_model_pistachio.h5",
                DataSource.CAMERA,
                new String[]{"Closed","Open"},
                (String str) -> {
                    doSomething(str);
                })
                .execute();
        ;
    }
}
