package server;

import javafx.util.Pair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        class cours implements Comparable<cours> {
            String codeDuCours;  // Code du cours
            String nom;  // Nom du cours
            String session; // saison de la session

            // Fonction de comparaison pour le trie des héros
            //public int getId() {
            //    return session;
            //}

            @Override
            public int compareTo(cours ceCours) {
                return this.session.compareTo(ceCours.session);
            }

            public cours(String codeDuCours, String nom, String session) {
                this.codeDuCours = codeDuCours;
                this.nom = nom;
                this.session = session;
            }
        }
        
        List<cours> toutLesCours = new ArrayList<cours>();

        try {
            File mesCours = new File("C:\\Users\\alexa\\IdeaProjects\\IFT1025-TP2-server\\src\\main\\java\\server\\data\\cours.txt");
            Scanner lecture = new Scanner(mesCours);
            while (lecture.hasNextLine()) {
                String data = lecture.nextLine();
                System.out.println( "Data = " + data);
                String[] champsDeCours = data.split("\t", 3);
                cours ceCours = new cours(champsDeCours[0], champsDeCours[1], champsDeCours[2]);
                toutLesCours.add(ceCours);
                }
            lecture.close();
        } catch (FileNotFoundException x) {
            System.out.println("Une erreur s'est produite.");
        }
       
        // Trier les cours par session
        Collections.sort( toutLesCours );

        try {
            for (int j = 0; j < toutLesCours.size(); j++) {
                System.out.println( toutLesCours.get(j).codeDuCours );
                objectOutputStream.writeObject(toutLesCours.get(j).codeDuCours);
                objectOutputStream.writeObject(toutLesCours.get(j).nom);
                objectOutputStream.writeObject(toutLesCours.get(j).session);
                objectOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
      System.out.println( "HandleRegistration");
      try {
          String Reg = objectInputStream.readObject().toString();
          System.out.println( " Registre: " + Reg );
          objectOutputStream.writeObject( "OK" );
      }
      catch( Exception e) {
          System.out.println( "Inputstream problem");
      }
    }
}
