package application;
	

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.shape.VLineTo; 
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path;   



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Charger");
        btn.setTranslateX( 100 );
        btn.setTranslateY(40);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("CHARGER");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        ListView<String> listViewReference = new ListView<String>();
        /* adding items to the list view */
        listViewReference.getItems().add("First Item");
        listViewReference.getItems().add("Second Item");
        listViewReference.getItems().add("Third Item");
        listViewReference.getItems().add("Fourth Item");
        listViewReference.getItems().add("Fifth Item");
        /* creating vertical box to add item objects */
        VBox vBox = new VBox(listViewReference); 
        /* creating scene */
        Scene List = new Scene(vBox, 220, 270);
        
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Inscription UdeM" );
        
        
        /* Creating a choice box with default constructor */
        ChoiceBox<Object> choiceBox = new ChoiceBox<Object>();
        choiceBox.getItems().add("Printemps");
        choiceBox.getItems().add("Automne");
        choiceBox.getItems().add("Hiver");

        choiceBox.setTranslateY(40);      
        /* Creating a tile pane for adding choice box */
        root.getChildren().add(choiceBox);
        
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //VBox root2 = new VBox();     

        //Path path = new Path();
        //path.getElements().add(new MoveTo(100f, 100f));
        //path.getElements().add(new VLineTo(100f));

        //root2.getChildren().addAll(path);
        //scene.setRoot(root2);

    }
    public static void main(String[] args) {
        launch(args);
    }
}

