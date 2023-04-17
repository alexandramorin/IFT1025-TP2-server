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

        //System.out.println( "Sleep 1" );
        //Thread.sleep(1000);
        String line;
        try {
          while(( line = fromServer.readObject().toString()) != null ) {
             System.out.println( "ReadObject" );
             code.add(line);
             nom.add( fromServer.readObject().toString());
             session.add( fromServer.readObject().toString());
          } 
          System.out.println("Tout lue"); 
        } catch( ClassNotFoundException e ) {
          System.out.println(" Poute" ); 
        }
        System.out.println( "Catchinggg.");
      } catch (IOException e) {
//      } catch (InterruptedException e) {
        System.out.println( "Exception" );
        Thread.currentThread().interrupt();
      }
      System.out.println( "Catching.");

      System.out.println( "Scanning..." );
      Scanner keyboard = new Scanner(System.in);
      while( true ) {
        System.out.println( "*** Bienvenue au portail d'inscription de cours de l'UDEM ***" );
        System.out.println( "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours" );

       List<String>Saison = new ArrayList<String>();
       for( int i = 0; i < code.size(); i++ ) {
         Saison.add( session.get(i) );
       }
       List<String> SaisonSansDoublon = new ArrayList<>( new HashSet<>(Saison));
       for( int i = 0; i < SaisonSansDoublon.size(); i++ ) {
         System.out.println( i + 1 + ". " + SaisonSansDoublon.get(i) );
       }
       System.out.print("> Choix: ");
       int Choix = keyboard.nextInt() - 1;

       System.out.println("Les cours offserts pendant la sesion " + SaisonSansDoublon.get(Choix) + " sont:");

       int Choix2 = 0;
       for( int i = 0; i < session.size(); i++ )  {
         if ( session.get(i).equals( SaisonSansDoublon.get(Choix)) ) {
           System.out.println( Choix2+1 + ". " + code.get(i) + " " + nom.get(i) );
           Choix2++;
         }
       }
       System.out.print("> Choix: ");
       int Choix3 = keyboard.nextInt() - 1;

       System.out.println( "1. Consulter les cours oofferts pour una autre session" );
       System.out.println( "2. Inscription a un cours" );
       System.out.print( "> Choix: " );
       int Choix4 = keyboard.nextInt() - 1;
       String rien = keyboard.nextLine();
       if ( Choix4 == 0 ) {
         continue;
       }
      break;
    }
    System.out.print( "Veuillez saisir votre prenom: " );  
    String Prenom = keyboard.nextLine();

    System.out.print( "Veuillez saisir votre nom: " );  
    String Nom = keyboard.nextLine();

    System.out.print( "Veuillez saisir votre email: " );  
    String Courriel = keyboard.nextLine();

    System.out.print( "Veuillez saisir votre matricule: " );  
    int matricule = keyboard.nextInt();

    System.out.print( "Veuillez saisir le code du cours: " ); 
    String rien = keyboard.nextLine(); 
    String codeDuCours = keyboard.nextLine();

    // Create an input stream to receive data from the server
    // Create a socket to connect to the server
    Socket socket1 = new Socket("localhost", 1337);
    toServer = new ObjectOutputStream(socket1.getOutputStream());
    fromServer = new ObjectInputStream(socket1.getInputStream());

    String Inscrire = "INSCRIRE";
    toServer.writeObject( Inscrire ); 
    toServer.writeObject( Prenom + " " + Nom + " " + Courriel + " " + matricule + " " + codeDuCours );
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
    fromServer.close();
    toServer.close();
    System.out.println( "F'licitations! Inscription reussie de " + Prenom + " au cours " + codeDuCours );
  } catch( IOException e ) {
    System.out.println( " Exception ici" );
  }
  }
}
