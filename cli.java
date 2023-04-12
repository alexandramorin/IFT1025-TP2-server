package program;
import java.io.*;
import java.net.*;
import java.util.*;

public class cli {

  public static void main(String[] args) {
    List<String> code = new ArrayList<String>();
    List<String> nom = new ArrayList<String>();
    List<String> session = new ArrayList<String>();

    // IO streams
    try {
      ObjectOutputStream toServer = null;
      ObjectInputStream fromServer = null;

      // Create a socket to connect to the server
      Socket socket = new Socket("localhost", 1337);

      // Create an input stream to receive data from the server
      toServer = new ObjectOutputStream(socket.getOutputStream());
      fromServer = new ObjectInputStream(socket.getInputStream());

      String lecture = "CHARGER Moi";
      toServer.writeObject(lecture);
      try {
        byte[] b = new byte[100];

        System.out.println( "Sleep 1" );
        Thread.sleep(1000);
        String line;
        try {
           while(( line = fromServer.readObject().toString()) != null ) {
             code.add(line);
             nom.add( fromServer.readObject().toString());
             session.add( fromServer.readObject().toString());
          }  
        } catch( ClassNotFoundException e ) {
          System.out.println(" Poute" ); 
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    fromServer.close();
  } catch( IOException e ) {
  }
  System.out.println( "*** Bienvenue au portail d'inscription de cours de l'UDEM ***" );
  System.out.println( "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours" );
  for( int i = 0; i < code.size(); i++ ) {
    System.out.println( i + ". " + session.get(i) );
  }
}
}

