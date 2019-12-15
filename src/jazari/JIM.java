/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.java_websocket.client.WebSocketClient;
import utils.FactoryScripts;
import utils.FactoryUtils;

/**
 *
 * @author BAP1
 */
public final class JIM {
    
    private static JIM jim;
    private static BackEnd back_end;                                              
    private static ProgrammingLanguage programming_language; 
    private static DataSource data_source;
    private static String model_path;
    private static String scriptFile=null;
    private static String[] classLabels=null;
    private static boolean isConnectToPythonServer=false;
//    public static WebSocketClient client;
    

    private JIM(CallBackInterface cb) {
        FactoryUtils.startJavaServer(cb);
        FactoryUtils.delay(1000);
    }
    
    public static JIM getInstance(CallBackInterface cb) {
        if (jim == null) {
            jim = new JIM(cb);
        }
        return jim;
    }


    /**
     * 
     * @param pl
     * @param be
     * @param modelPath
     * @param ds
     * @param class_labels
     * @param cb
     * @return 
     */
    public static JIM transferLearningModelForImages(
            ProgrammingLanguage pl,
            BackEnd be,
            String modelPath,
            DataSource ds,
            String[] class_labels,
            CallBackInterface cb
            
    ) {
        jim=getInstance(cb);
        isConnectToPythonServer=false;
        back_end=be;
        data_source=ds;
        programming_language=pl;
        model_path=modelPath; 
        classLabels=FactoryUtils.clone(class_labels);
        scriptFile=FactoryScripts.generateScriptFilePythonWebCamTestTransferLearning(programming_language,back_end,data_source,model_path,classLabels);
        return jim;
    }
    
    public static JIM transferLearningModelForOfflineTestImages(
            ProgrammingLanguage pl,
            BackEnd be,
            String modelPath,
            DataSource ds,
            String test_path,
            CallBackInterface cb
            
    ) {
        jim=getInstance(cb);
        isConnectToPythonServer=false;
        back_end=be;
        data_source=ds;
        programming_language=pl;
        model_path=modelPath; 
        scriptFile=FactoryScripts.generateScriptFilePythonOfflineFileTestTransferLearning(programming_language,back_end,data_source,test_path,model_path);
        return jim;
    }
    
    
    public static JIM transferLearningModelForOnlineTestImage(
            ProgrammingLanguage pl,
            BackEnd be,
            String modelPath,
            DataSource ds,
            CallBackInterface cb
            
    ) {
        jim=getInstance(cb);
        isConnectToPythonServer=true;
        back_end=be;
        data_source=ds;
        programming_language=pl;
        model_path=modelPath; 
        scriptFile=FactoryScripts.generateScriptFilePythonOnlineFileTestTransferLearning(programming_language,back_end,data_source,model_path);
        return jim;
    }
    
    /**
     * 
     * @param pl
     * @param be
     * @param modelPath
     * @param crossValidationRatio
     * @param datasetRootPath
     * @param learningRate
     * @param batchSize
     * @param epoch
     * @param cb
     * @return 
     */
    public static JIM CNN_TransferLearningModelForTraining(
            ProgrammingLanguage pl,
            BackEnd be,
            String modelPath,
            double crossValidationRatio,
            String datasetRootPath,
            double learningRate,
            double batchSize,
            double epoch,
            CallBackInterface cb
            
    ) {
        jim=getInstance(cb);
        return jim;
    }

    public JIM checkWebSocketStatus() {
        System.out.println("WebSocket Status is " + FactoryUtils.stopServer);
        return jim;
    }

    public JIM sendMessageToPythonServer(String msg) {
        if (FactoryUtils.client != null) {
            System.out.println("java sent msg = " + msg);
            FactoryUtils.client.send(msg);
        }
        return jim;
    }

//    /**
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
//     *
//     * @param str
//     * @return
//     */
    public JIM setBackEnd(BackEnd backEnd) {
        if (backEnd.equals("python")) {
            if (true) {
                FactoryUtils.connectPythonServer();
                FactoryUtils.delay(1000);
            }

        } else if (backEnd.equals("js")) {

        } else if (backEnd.equals("dl4j")) {

        } else {
            System.out.println("back end only be python, js or dl4j");
        }
        return jim;
    }

    public JIM setInferenceModel(String path) {
        return jim;
    }

    public JIM setProgrammingLanguage(ProgrammingLanguage p) {
        return jim;
    }

    public JIM setInferenceResponseTime(InferenceResponseTime inferenceResponseTime) {
        return jim;
    }

    public JIM setLearningType(LearningType learningType) {
        return jim;
    }

    public JIM setDataSource(DataSource dataSource) {
        return jim;
    }

    public JIM setCallBackFunction(CallBackInterface cbi) {
        return jim;
    }

    public JIM setLearningMode(LearningMode learningMode) {
        return jim;
    }

    public void execute() {
        if (scriptFile==null) {
            System.out.println("Severe Error has been occured script file could not generated");
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FactoryUtils.executeCommand("python "+scriptFile);               
                }
            }).start();
//            FactoryUtils.delay(5000);
//            if (isConnectToPythonServer) {
//                client=FactoryUtils.connectPythonServer();
//            }
        }
    }

    
}
