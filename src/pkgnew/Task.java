/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author neved
 */
public class Task{

    String taskName;
    String owner;
    String category;   
    String date;
    int count;
    String due;
    String priority;
    public static final Task BLANK = new Task("", "", "", "", -1, "", "");
    
    public Task(String taskName, String owner, String category, String date, int count, String due, String priority){
        this.taskName = taskName;
        this.owner = owner;
        this.category = category;
        this.date = date;
        this.count = count;
        this.due = due;
        this.priority = priority;
    }
    
    public Parent getTask() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("finalTask.fxml"));
        
        Label taskNameLabel = (Label)root.lookup("#taskName"); 
        Label ownerLabel = (Label)root.lookup("#ownerLabel");
        Label categoryLabel = (Label)root.lookup("#categoryLabel");
        Label dateLabel = (Label)root.lookup("#ddLabel");
        AnchorPane taskPane = (AnchorPane)root.lookup("#taskPane");
        //label being correlated with the named label in the scenebuilder fxml
        Label dueLabel = (Label)root.lookup("#dueLabel");
        Label priorityLabel = (Label)root.lookup("#priorityLabel");
        ImageView finStarOne = (ImageView)root.lookup("#finStarOne");
        ImageView finStarTwo = (ImageView)root.lookup("#finStarTwo");
        ImageView finStarThree = (ImageView)root.lookup("#finStarThree");
        ImageView finStarFour = (ImageView)root.lookup("#finStarFour");
        ImageView finStarFive = (ImageView)root.lookup("#finStarFive");
                
        taskNameLabel.setText(taskName);
        ownerLabel.setText(owner);
        categoryLabel.setText(category);
        dateLabel.setText(date);
        
        String newId = "" + count;
        taskPane.setId(newId);
        //deleteBtn.setId(newId); //will be able to get rid of this line
        
        dueLabel.setText(due);        
        priorityLabel.setText("Priority Level: \n" + priority);
        
        setStarOpacity(priority, finStarOne, finStarTwo, finStarThree, finStarFour, finStarFive);
           
        return root;        
    }
    
    public static void setStarOpacity(String priority, ImageView one, ImageView two, 
            ImageView three, ImageView four, ImageView five){
        if(priority == null)
            return;
        if (priority.contentEquals("One")){
            one.setOpacity(1);
            two.setOpacity(.5);
            three.setOpacity(.5);
            four.setOpacity(.5);
            five.setOpacity(.5);
        } else if (priority.contentEquals("Two")) {
            one.setOpacity(1);
            two.setOpacity(1);
            three.setOpacity(.5);
            four.setOpacity(.5);
            five.setOpacity(.5);
        } else if (priority.contentEquals("Three")) {
            one.setOpacity(1);
            two.setOpacity(1);
            three.setOpacity(1);
            four.setOpacity(.5);
            five.setOpacity(.5);
        } else if (priority.contentEquals("Four")) {
            one.setOpacity(1);
            two.setOpacity(1);
            three.setOpacity(1);
            four.setOpacity(1);
            five.setOpacity(.5);
        } else if (priority.contentEquals("Five")) {
            one.setOpacity(1);
            two.setOpacity(1);
            three.setOpacity(1);
            four.setOpacity(1);
            five.setOpacity(1);
        }
    }
    
    public static String generateDueInLabel(Date date) throws ParseException{
        String due;
        LocalDate rightNow = LocalDate.now(); //local date class
        //System.out.println(rightNow);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy" , Locale.ENGLISH);
        String formattedNowDate = rightNow.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")); //Makes the localDate into a string
        
        if (date == null) {
            due = "";
        }
        else {
            Date currentDate = sdf.parse(formattedNowDate);
            long dateSubtraction = date.getTime() - currentDate.getTime();
            TimeUnit timeInDays = TimeUnit.DAYS;
            long dueDateConversion = timeInDays.convert(dateSubtraction, TimeUnit.MILLISECONDS);
            if ((dueDateConversion) == 1) {
                due = "Task Is Due In the Next 24 Hours!";
            }
            else if(dueDateConversion == 0){
                due = "Task is due today!"; //having the FXML line 'due' spit out "Due in: _____ Days!"
            }
            else if(dueDateConversion > 1){
                due = "Due in: " + (String.valueOf(dueDateConversion)) + " Days!";
            }
            else{ 
                due = "Task was due " + Math.abs(dueDateConversion) + " Days ago!";
            }
                
        }
        
        return due;
    }
    

}
    

    

    

