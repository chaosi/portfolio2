package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.ResultSet;
import java.util.LinkedList;


public class Controller {
   private SearchDb searchDb = new SearchDb();

   private ObservableList<String> hourList = FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
   private ObservableList<String> minList = FXCollections.observableArrayList("00","05","10","15","20","25","30","35","40","45","50","55");


   @FXML
   TextField fromText;

   @FXML
   TextField toText;

    @FXML
    ChoiceBox choiceBoxHour;

    @FXML
    ChoiceBox choiceBoxMin;



    @FXML
    ListView viewTrains;

    @FXML
    private void initialize() // set ChoiceBox items on start
    {
        choiceBoxHour.setItems(hourList);
        choiceBoxMin.setItems(minList);
    }






   public  void  search() // executed from GUI "Search" button
   {

       ///  checks database for station names most like Start with wildcard '%' to typed in string in textfield and returns to textfield
        fromText.setText(searchDb.FindStation(fromText.getText()));
        toText.setText(searchDb.FindStation(toText.getText()));


       //  get "filtereddataset" from time and station input, gets every instance of mentioned station after input time from DB.
      LinkedList output = searchDb.setDataTraindataset(fromText.getText(),toText.getText(),choiceBoxHour.getValue().toString(),choiceBoxMin.getValue().toString());


        // clearing
       String ResultString ="";
       viewTrains.getItems().clear();



       //adding list items to viewlist
       for (int i = 0; i < output.size();i++ )
       {
            viewTrains.getItems().add(output.get(i).toString());
       }


   }








}
