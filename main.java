package application;
	

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.shape.VLineTo; 
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path;   

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

     	Button btn = new Button();
        btn.setText("Charger");
	    //btn.setTranslateX( 100 );
	    //btn.setTranslateY(40);
	    btn.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent event) {
            System.out.println("CHARGER");
          }
        });
	    root.getChildren().add(btn);
        
        HBox hbox = new HBox(30); // create a HBox to hold 2 vboxes        
         
        // create a vbox with a textarea that grows vertically
        VBox vbox = new VBox(10);        
        Label lbName = new Label("Liste des cours");
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(100);
        VBox.setVgrow(textArea, Priority.ALWAYS);        
        vbox.getChildren().addAll(lbName, textArea);
         
        // create a vbox that grows horizontally inside the hbox
        VBox vbox2 = new VBox(10);        
        Label lbName2 = new Label("Formulaire d'inscription");
        
        HBox hboxPrenom = new HBox( 10 );
        TextField Prenom = new TextField();
        Label LblPrenom = new Label( "Prenom");
        Prenom.setPromptText("Prenom");
        hboxPrenom.getChildren().addAll( LblPrenom, Prenom );
        
        TextField Nom = new TextField();
        TextField Courriel = new TextField();
        TextField Matricule = new TextField();
        Nom.setPromptText("Nom");
        Courriel.setPromptText("Courriel");
        Matricule.setPromptText("Matricule");
        vbox2.getChildren().addAll(lbName2, LblPrenom, Prenom, Nom, Courriel, Matricule);
           
        HBox.setHgrow(vbox2, Priority.ALWAYS);
 
        // the next two lines behave equally - try to comment the first line out and use the 2nd line
        hbox.setPadding(new Insets(20));
//        StackPane.setMargin(hbox, new Insets(20));
 
        hbox.getChildren().addAll(vbox, vbox2);
        root.getChildren().add(hbox);
        Scene scene = new Scene(root, 500, 300); // the stack pane is the root node

        /* Creating a choice box with default constructor */
        ChoiceBox<Object> choiceBox = new ChoiceBox<Object>();
        choiceBox.getItems().add("Printemps");
        choiceBox.getItems().add("Automne");
        choiceBox.getItems().add("Hiver");

        choiceBox.setTranslateY(40);      
        /* Creating a tile pane for adding choice box */
        root.getChildren().add(choiceBox);
        
        primaryStage.setTitle("Inscription UdeM" );
        primaryStage.setScene(scene);
        primaryStage.show();
    	
//    	Button btn = new Button();
//        btn.setText("Charger");
//        btn.setTranslateX( 100 );
//        btn.setTranslateY(40);
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("CHARGER");
//            }
//        });
//
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        
//        ListView<String> listViewReference = new ListView<String>();
//        /* adding items to the list view */
//        listViewReference.getItems().add("First Item");
//        listViewReference.getItems().add("Second Item");
//        listViewReference.getItems().add("Third Item");
//        listViewReference.getItems().add("Fourth Item");
//        listViewReference.getItems().add("Fifth Item");
//        /* creating vertical box to add item objects */
//        VBox vBox = new VBox(listViewReference); 
//        /* creating scene */
//        Scene List = new Scene(vBox, 220, 270);
//        
//        Scene scene = new Scene(root, 300, 250);
//
//        primaryStage.setTitle("Inscription UdeM" );
//        
//        
//        /* Creating a choice box with default constructor */
//        ChoiceBox<Object> choiceBox = new ChoiceBox<Object>();
//        choiceBox.getItems().add("Printemps");
//        choiceBox.getItems().add("Automne");
//        choiceBox.getItems().add("Hiver");
//
//        choiceBox.setTranslateY(40);      
//        /* Creating a tile pane for adding choice box */
//        root.getChildren().add(choiceBox);
//        
//        Separator separator = new Separator();
//        separator.setOrientation(Orientation.VERTICAL);
//        
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        
//        //VBox root2 = new VBox();     
//
//        //Path path = new Path();
//        //path.getElements().add(new MoveTo(100f, 100f));
//        //path.getElements().add(new VLineTo(100f));
//
//        //root2.getChildren().addAll(path);
//        //scene.setRoot(root2);

    }
    public static void main(String[] args) {
        launch(args);
    }
}
