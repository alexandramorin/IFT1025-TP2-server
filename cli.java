/** 
* @author Alexandra Morin
* @version 1.0
* @since 17-04-2023
*/
package program;

import java.io.*;
import java.net.*;
import java.util.*;

/**
* Cette méthode est le coté client du programme
*/
public class cli {

  public static void main(String[] args) {
    List<String> code = new ArrayList<String>(); // nouvelle liste pour le code des cours (ex: IFT1025)
    List<String> nom = new ArrayList<String>(); // nouvelle liste pour le nom des cours (ex: Programmation 2)
    List<String> session = new ArrayList<String>(); // nouvelle liste pour la session où le cours se donne (ex: Hiver)

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

      Scanner keyboard = new Scanner(System.in);
      /**
      * Création de l'application pour que les clients puissent choisir leurs cours
      * Débutent par la session qu'ils/elles desirent, par la suite, les cours offert cette session et l'inscription au cours
      */
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
      
    /**
    * Le client entre ses données personnelle pour s'inscrire au cours qu'il/elle desire
    */
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

    // Création à nouveau d'un socket pour connecter au serveur
    // Création à nouveau d'un flux d’entrée to recevoir les données du serveur
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
    /**
    * Impression de message félicitant le client de son inscription au cours choisi
    */
    System.out.println( "Félicitations! Inscription reussie de " + Prenom + " au cours " + codeDuCours );
  } catch( IOException e ) {
    System.out.println( " Exception ici" );
  }
  }
}
