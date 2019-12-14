
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

    public static void main(String[] args) {

        JIM.Test_CNN_TransferLearningModel(
                ProgrammingLanguage.PYTHON,
                BackEnd.CPU,
                "C:\\python_data\\models\\keras_model_pistachio.h5",
                DataSource.CAMERA,
                new String[]{"Closed","Open"},
                (String str) -> {
                    doSomething(str);
                })
                .execute();
        ;
    }
}
