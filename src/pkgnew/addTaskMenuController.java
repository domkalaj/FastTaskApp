
package pkgnew;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import static pkgnew.Main.todayList;


/**
 *
 * @author danie
 */
public class addTaskMenuController  {
    
    @FXML  
    TextField nameTaskField;
    @FXML
    TextField ownerField;
    @FXML
    TextField categoryField;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField dueLabel;
    @FXML
    TextField priorityLabel;
    
    @FXML
    public ImageView ratingOne;
    @FXML
    public ImageView ratingTwo;
    @FXML
    public ImageView ratingThree;
    @FXML
    public ImageView ratingFour;
    @FXML
    public ImageView ratingFive;

    public Stage stage;
    private Scene scene;
    private Parent root;
    public String starClicked;

    public void createTask(ActionEvent event) throws Exception, ParseException {

        String taskName = nameTaskField.getText();
        String owner = ownerField.getText();
        String category = categoryField.getText();
        String date;
        String due = null;
        String priority = starClicked;
        Date datePickerDate = null;
        if(datePicker.getValue() == null)
            date = "No date selected";
        else{
            date = datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")); //converts LocalDate -> String
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy" , Locale.ENGLISH);
            datePickerDate = sdf.parse(date);            
        }

        due = Task.generateDueInLabel(datePickerDate);
 
        
        Main.todoList.add(new Task(taskName, owner, category, date, Main.totalCount, due, priority)); //placeholder args
        
        String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));               
        if(dateNow.contentEquals(date)){
            todayList.add(new Task(taskName, owner, category, date, Main.totalCount, due, priority));
        }  
        
        viewMainMenu(event);
        
        storeTask(Main.totalCount, taskName, owner, category, date, priority);     
        
        Main.incrementTotalCount();
        
        //NOTE: DUE IS NOT STORED ON LOCAL MACHINE, AS IT DIFFERS FROM DAY TO DAY.
        //RATHER, IT IS GENERATED BASED ON THE DATE OF THE TASK.
         
    }
    
    public void viewMainMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));	
        root = loader.load();
        
        MainMenuController mainMenuController = loader.getController();       
        mainMenuController.displayTasks();
        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        
     public void mousePressedStarOne () {
        System.out.println("Star One is Pressed.");
        starClicked = "One";
    }
    
    public void mousePressedStarTwo () {
        System.out.println("Star Two is Pressed.");
        starClicked = "Two";
    }
    
    public void mousePressedStarThree () {
        System.out.println("Star Three is Pressed.");
        starClicked = "Three";
    }
    
    public void mousePressedStarFour () {
        System.out.println("Star Four is Pressed.");
        starClicked = "Four";
    }
    
    public void mousePressedStarFive () {
        System.out.println("Star Five is Pressed.");
        starClicked = "Five";
    }
    
    public void mouseReleased() {
        Task.setStarOpacity(starClicked, ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive);
    }
       
    private void storeTask(int count, String taskName, String owner, String category, String date, String priority) throws Exception{
        Main.decrypt();
        FileWriter taskWriter = new FileWriter(Main.path, true);
        taskWriter.append("todo," + count + "," + taskName + "," + owner + "," + category + "," + date + "," + priority + "\n");//store information in the folder
        taskWriter.close();
        Main.encrypt();
    }
    
}
