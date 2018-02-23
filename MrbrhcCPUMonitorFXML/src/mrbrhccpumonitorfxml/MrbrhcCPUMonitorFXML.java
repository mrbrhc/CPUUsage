/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrbrhccpumonitorfxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mason
 */
public class MrbrhcCPUMonitorFXML extends Application {
    //
    public String appName = "CPU Monitor";
    
    //
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLDocumentController cpuInfo = new FXMLDocumentController();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle(appName);
        stage.setScene(scene);
        stage.show();
        
        cpuInfo.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
