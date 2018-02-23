/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrbrhccpumonitorfxml;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
//import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author Mason
 */


public class FXMLDocumentController implements Initializable {
    
    public boolean buttonOneFlag = false;
    public boolean buttonTwoFlag = false;
    
    public Timeline timeline;
    public KeyFrame keyFrame;
    
    public double cpuUsage=0;
    public double cpuDigital=0;
    public double tickTimeInSeconds = 1.0;
    public double angleHandImage=302;
    
    public int recordCounter = 0;
    public int totalRecordCounter = 0;
    public String nullRecord = "--.--%";
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss a");
 
    @FXML
    private ImageView handImage;
    
    @FXML
    private Label digitalDisplay;
    
    @FXML
    private Label labelOne;
    
    @FXML
    private Label labelTwo;
    
    @FXML
    Label labelThree;
    
    @FXML
    private VBox root;
    
    @FXML
    private HBox buttonContainer;
    
    @FXML
    private Button buttonTwo;
    
    @FXML
    private Button buttonOne;
    
    
    public FXMLDocumentController(){
        setupTimer();
    }
    
    public void start(){
        
    }
    
    @FXML
    private void handleStart(ActionEvent event){
        if(buttonTwoFlag == false){//handleStart
            
            buttonTwo.setText("Stop");
            buttonOne.setText("Record");
            buttonTwoFlag = true;
            
            timeline.play();
            
        }
        else{ //handleStop
            buttonTwo.setText("Start");
            buttonOne.setText("Reset");
            buttonTwoFlag = false;
            buttonOneFlag = true;
            
            timeline.pause();
        }
      
    }
    
    
    
    @FXML
    private void handleRecord(ActionEvent event){
        if(buttonOneFlag == false){
            if(buttonTwoFlag == false){
                return;
            }
            //do record
            buttonTwo.setText("Stop");
            buttonOne.setText("Record");
//            buttonOneFlag = true;
            totalRecordCounter = recordCounter % 3 +1;
            
            DecimalFormat df = new DecimalFormat("0.00");
       
        ++recordCounter; 
        //format dateTime
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss aa");
        String formattedDate = dateFormat.format(new Date()).toString();
       
        if(totalRecordCounter == 1){
            labelOne.setText("Record" + recordCounter + ": " + df.format(cpuDigital) + "%" + " at " + formattedDate );
        }
        if(totalRecordCounter == 2){
            labelTwo.setText("Record" + recordCounter + ": " + df.format(cpuDigital) + "%" + " at " + formattedDate);
        }
        if(totalRecordCounter == 3){
            labelThree.setText("Record"+ recordCounter + ": " + df.format(cpuDigital) +"%" + " at " + formattedDate);
        }

        }
        else{
            //do reset
            buttonTwo.setText("Start");
            buttonOne.setText("Record");
            buttonOneFlag = false;
            
            //reset variables
            recordCounter = 0;
            cpuUsage = 0;
            cpuDigital = 0;
            labelOne.setText(nullRecord);
            labelTwo.setText(nullRecord);
            labelThree.setText(nullRecord);
            digitalDisplay.setText(nullRecord);
            handImage.setRotate(224);
        }
        
    }
    
    
    
//    private VBox getRoot(){
//    return root;
//    }
    
    public void setupTimer(){
        keyFrame = new KeyFrame(Duration.millis(tickTimeInSeconds*1000),(ActionEvent) ->{
            update();
        });
        
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        
        
    }
    
    private void update(){
        cpuUsage = getCPUUsage();
        double rotation = cpuUsage * angleHandImage + 224;
        handImage.setRotate(rotation);
        
        cpuDigital = (cpuUsage*100)%100;
        
        DecimalFormat df = new DecimalFormat("0.00");      

        
        digitalDisplay.setText(df.format(cpuDigital) + "%");
        
        
    }
    
    private static double getCPUUsage() {
        OperatingSystemMXBean operatingSystemMXBean =
        ManagementFactory.getOperatingSystemMXBean();
        double value = 0;
        for(Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
        method.setAccessible(true);
            if (method.getName().startsWith("getSystemCpuLoad")
                && Modifier.isPublic(method.getModifiers())) {
            try {
                value = (double) method.invoke(operatingSystemMXBean);
            } catch (Exception e) {
                value = 0;
                }
        return value;
        }
        }
    return value;
}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
