package musicHub.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Media player store the albums, element and playlist
 * */
import util.*;

public class mediaPlayer {
    Map<String, List<Media>> mediaplayer = new HashMap<String, List<Media>>();

    // this method take a list of media and a key in parameter and put them in the mediaplayermap
    void addMedia(String key, List<Media> mediaList) {
        mediaplayer.put(key, mediaList);
    }

    static Map<String, String> help = new HashMap<String, String>() {
        {
            put("c", "Ajout d'une nouvelle chanson.");
            put("a", "Ajout d'un nouvel album.");
            put("+", "Ajout d'une chanson existante à un album.");
            put("l", "Ajout d'un livre existant à une playlist.");
            put("-", "Suppression d'une playlist");
            put("s", "Sauvegarde des playlists, albums, livres audio dans un fichier XML.");
            put("h", "aide avec les détailles des commandes");
        }
    };


     public void run(){
        try {
            List<Media> playlists = null;
            try {
                playlists = Playlist.readLivreAudioFromXLM("../files/playlist.xml");
            } catch (JMusicHubFileDoesntExistException e) {
                e.printStackTrace();
            }
            List<Media> albums = null;
            try {
                albums = Album.readAlbumsFromXLM("../files/albums.xml");
            } catch (JMusicHubFileDoesntExistException e) {
                e.printStackTrace();
            }
            mediaplayer.put("albums", albums);
            mediaplayer.put("playlists", playlists);
            Boolean isOn = true;
            byte[] choice = "h".getBytes();

            System.out.println("\n\n\n\n ---------------------- Data have been loaded -------------------------");
            while (isOn){
                switch (choice[0]){
                    case 'h':
                        mediaPlayer.help();
                        break;
                    case 'a':

                        break;
                    case 'l':

                        break;
                    case '+':

                        break;
                    case '-':
                        break;
                    case 's':
                        break;
                    default:
                        isOn = false;
                }
                System.out.println("Quel est votre choix ? (entrer h pour afficher l'aide ou entrer pour quitter)");
                choice = System.in.readNBytes(2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
     }


    static void help() {
        for (Map.Entry<String, String> entry : help.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
