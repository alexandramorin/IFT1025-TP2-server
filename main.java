/** 
* @author Alexandra Morin
* @version 1.0
* @since 17-04-2023
*/
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<String> code = new ArrayList<String>(); // nouvelle liste pour le code des cours (ex: IFT1025)
        List<String> nom = new ArrayList<String>(); // nouvelle liste pour le nom des cours (ex: Programmation 2)
        List<String> session = new ArrayList<String>(); // nouvelle liste pour la session où le cours se donne (ex: Hiver)
        ChoiceBox<Object> choiceBox = new ChoiceBox<Object>();
        ListView<String> listView = new ListView<String>();
        ObservableList<String> list = FXCollections.observableArrayList();

    	// IO streams
        try {
          ObjectOutputStream toServer = null;
          ObjectInputStream fromServer = null;

          // Création d'un socket pour connecter au serveur
          Socket socket = new Socket("localhost", 1337);

          // Création d'un flux d’entrée to recevoir les données du serveur
          toServer = new ObjectOutputStream(socket.getOutputStream());
          fromServer = new ObjectInputStream(socket.getInputStream());

          String lecture = "CHARGER Moi";
          toServer.writeObject(lecture);
          try {
            byte[] b = new byte[100];

            String line;
            try {
              while(( line = fromServer.readObject().toString()) != null ) {
                 code.add(line);
                 nom.add( fromServer.readObject().toString());
                 session.add( fromServer.readObject().toString());
              } 
            /**
            * Si la classe n'est pas trouver, un avertissement est envoyé
            */
            } catch( ClassNotFoundException e ) {
              System.out.println("Erreur" ); 
            }
          /**
          * S'il y a une erreur, on interrompt le programme
          */
          } catch (IOException e) {
            Thread.currentThread().interrupt();
          }
        } catch( Exception e ) {
        	System.out.println( "Oops, le serveur n'est pas en fonction !");
        }
        List<String>Saison = new ArrayList<String>();
        for( int i = 0; i < code.size(); i++ ) {
          Saison.add( session.get(i) );
        }
        List<String> SaisonSansDoublon = new ArrayList<>( new HashSet<>(Saison));

    	StackPane root = new StackPane();

     	Button btn = new Button();
        btn.setText("Charger");
	    btn.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent event) {
              listView.setItems(list);
              listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

              list.clear();
              for( int i = 0; i < session.size(); i++ )  {
                if ( session.get(i).equals( choiceBox.getSelectionModel().getSelectedItem() ))  {
                  list.add( code.get(i) + " " + nom.get(i) );
                }
              }       
          }
        });

        HBox hbox = new HBox(30); // create a HBox to hold 2 vboxes        
         
        // create a vbox with a textarea that grows vertically
        VBox vbox = new VBox(20);        
        Label lbName = new Label("Liste des cours");
        /* Creating a choice box with default constructor */
        choiceBox.getItems().add(SaisonSansDoublon.get(0));
        choiceBox.getItems().add(SaisonSansDoublon.get(1));
        choiceBox.getItems().add(SaisonSansDoublon.get(2));

        vbox.getChildren().addAll(lbName, listView, choiceBox, btn);

        
        // create a vbox that grows horizontally inside the hbox
        VBox vbox2 = new VBox(10);        
        Label lbName2 = new Label("Formulaire d'inscription");
        
        HBox hboxPrenom = new HBox( 10 );
        TextField Prenom = new TextField();
        Label LblPrenom = new Label( "Prenom");
        hboxPrenom.getChildren().addAll( LblPrenom, Prenom );
        
        HBox hboxNom = new HBox( 10 );
        TextField Nom = new TextField();
        Label LblNom = new Label( "Nom");
        hboxNom.getChildren().addAll( LblNom, Nom );
        
        HBox hboxCourriel = new HBox( 10 );
        TextField Courriel = new TextField();
        Label LblCourriel = new Label( "Courriel");
        hboxCourriel.getChildren().addAll( LblCourriel, Courriel );

        HBox hboxMatricule = new HBox( 10 );
        TextField Matricule = new TextField();
        Label LblMatricule = new Label( "Matricule");
        hboxMatricule.getChildren().addAll( LblMatricule, Matricule );

        Button envoyer = new Button();
        envoyer.setText("Envoyer");
	    envoyer.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent event) {
            ObjectOutputStream toServer = null;
            ObjectInputStream fromServer = null;
        	
            String Envoie = Prenom.getText() + " " + Nom.getText() + " " + Courriel.getText() + " " + Matricule.getText();
            // Create an input stream to receive data from the server
            // Create a socket to connect to the server
            try {
	            Socket socket1 = new Socket("localhost", 1337);
	            toServer = new ObjectOutputStream(socket1.getOutputStream());
	            fromServer = new ObjectInputStream(socket1.getInputStream());
	
	            String Inscrire = "INSCRIRE";
	            toServer.writeObject( Inscrire ); 
	            toServer.writeObject( Envoie );
	            System.out.println( "Envoie complete" );
	            toServer.flush();
	
	            try {
	              Thread.sleep(1000);
	            } catch ( Exception e ) {
	            }
	            try {
	              System.out.println( fromServer.readObject().toString()  );
	            } catch (ClassNotFoundException e) {
	              System.out.println( "Exception" ); 
	            } 
	            socket1.close();
	            fromServer.close();
	            toServer.close();
            }
           catch( IOException e) {
        	 System.out.println( "Socket ?");
           }
              
          }
        });

        
        vbox2.getChildren().addAll(lbName2, hboxPrenom, hboxNom, hboxCourriel, hboxMatricule, envoyer);
           
        HBox.setHgrow(vbox2, Priority.ALWAYS);
 
        // the next two lines behave equally - try to comment the first line out and use the 2nd line
        hbox.setPadding(new Insets(20));
//        StackPane.setMargin(hbox, new Insets(20));
 
        hbox.getChildren().addAll(vbox, vbox2);
        root.getChildren().add(hbox);
        Scene scene = new Scene(root, 500, 300); // the stack pane is the root node

        
        primaryStage.setTitle("Inscription UdeM" );
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {

        launch(args);
    }
}
