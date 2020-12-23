package musicHub;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * Media player store the albums, element and playlist
 * */
import musicHub.util.*;
import musicHub.util.JMusicHubFileDoesntExistException;

public class MediaPlayer {
    Map<String, List<Media>> mediaplayer = new HashMap<String, List<Media>>();

    static public void main(String[] args){
        MediaPlayer mp = new MediaPlayer();
        mp.run();
    }

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
            put("h", "Aide avec les détailles des commandes");
            put("P", "Afficher les playlists");
            put("A", "Afficher les albums");
        }
    };

    /***
     * Add a song to an existing playlist
     */
    private void addSongToPlaylist(){
        System.out.println("Quel est le morceau que vous souhaitez ajouter ? Entrer l'index");
        for (var index = 0; index < mediaplayer.get("elements").size(); index++){
            var element = mediaplayer.get("elements").get(index);
            if (element instanceof Chanson){
                System.out.println(index + " - " + ((Chanson)element).getTitre());
            }
        }
        System.out.println("\n \n -----------| \n Index value : ");
        Scanner scan = new Scanner(System.in);
        var songIndex = scan.nextInt();
        var chanson = mediaplayer.get("elements").get(songIndex);
        if (songIndex >= 0 && songIndex < mediaplayer.get("elements").size()){
            System.out.println("Dans quel playlist ? Entrer l'index :");
            for (var index = 0; index < mediaplayer.get("playlists").size(); index++){
                var playlist = (Playlist)mediaplayer.get("playlists").get(index);
                System.out.println(index + " - " + playlist.getTitre());
            }
            System.out.println("\n \n -----------| \n Index value : ");
            int playlistIndex = scan.nextInt();
            if (playlistIndex >= 0 && playlistIndex < mediaplayer.get("playlists").size()){
                ((Playlist)mediaplayer.get("playlists").get(playlistIndex)).addMedia(chanson);
                System.out.println("Votre chanson à été ajouté à la playlist");
                return;
            }
        }
        System.out.println("La valeur de l'index est invalide");
    }

    /***
     * Create a playlist using existing song from element list
     */
    private void createPlaylist(){
        System.out.println("Quel est le morceau que vous souhaitez ajouter ? Entrer l'index");
        for (var index = 0; index < mediaplayer.get("elements").size(); index++){
            var element = mediaplayer.get("elements").get(index);
            if (element instanceof Chanson){
                System.out.println(index + " - " + ((Chanson)element).getTitre());
            }
        }
        System.out.println("\n \n -----------| \n Index value : ");
        Scanner scan = new Scanner(System.in);
        var songIndex = scan.nextInt();
        var chanson = mediaplayer.get("elements").get(songIndex);
    }

    /***
     * Delete a playlist from the playlists list
     */
    private void deletePlaylist() throws IOException {
        System.out.println("Entrez l'index de la playlist que vous souhaitez supprimer");
        for (var index = 0; index < mediaplayer.get("playlists").size(); index++){
            var playlist = (Playlist)mediaplayer.get("playlists").get(index);
            System.out.println(index + " - " + playlist.getTitre());
        }
        System.out.println("\n \n -----------| \n Index value : ");
        Scanner scan = new Scanner(System.in);
        int playlistIndex = scan.nextInt();
        if (playlistIndex >= 0 && playlistIndex < mediaplayer.get("playlists").size()){
            mediaplayer.get("playlists").remove(playlistIndex);
            return;
        }
        System.out.println("La valeur de l'index est invalide");
    }

    /***
     * Create a playlist using existing song from element list
     */
    void afficherAlbum(){
        System.out.println("Entrez l'index de la playlist que vous souhaitez afficher");
        for (var index = 0; index < mediaplayer.get("albums").size(); index++){
            var playlist = (Album)mediaplayer.get("albums").get(index);
            System.out.println(index + " - " + playlist.getTitre());
        }
        System.out.println("\n \n -----------| \n Index value : ");
        Scanner scan = new Scanner(System.in);
        int playlistIndex = scan.nextInt();
        if (playlistIndex >= 0 && playlistIndex < mediaplayer.get("albums").size()){
            var selectedPlaylist = (Album)mediaplayer.get("albums").get(playlistIndex);
            for (var media : selectedPlaylist.getChansons()){
                media.description();
            }
            return;
        }
        System.out.println("La valeur de l'index est invalide");
    }

    void afficherPlaylists(){
        System.out.println("Entrez l'index de la playlist que vous souhaitez afficher");
        for (var index = 0; index < mediaplayer.get("playlists").size(); index++){
            var playlist = (Playlist)mediaplayer.get("playlists").get(index);
            System.out.println(index + " - " + playlist.getTitre());
        }
        System.out.println("\n \n -----------| \n Index value : ");
        Scanner scan = new Scanner(System.in);
        int playlistIndex = scan.nextInt();
        if (playlistIndex >= 0 && playlistIndex < mediaplayer.get("playlists").size()){
            var selectedPlaylist = (Playlist)mediaplayer.get("playlists").get(playlistIndex);
            for (var media : selectedPlaylist.getMedias()){
                media.description();
            }
            return;
        }
        System.out.println("La valeur de l'index est invalide");
    }



     public void run(){
        try {
            List<Media> playlists = null;
            try {
                playlists = Playlist.readPlaylistsFromXLM("files/playlist.xml");
            } catch (JMusicHubFileDoesntExistException e) {
                e.printStackTrace();
            }
            List<Media> albums = null;
            try {
                albums = Album.readAlbumsFromXLM("files/albums.xml");
            } catch (JMusicHubFileDoesntExistException e) {
                e.printStackTrace();
            }
            List<Media> elements = null;
            try {
               elements = Playlist.readElementFromXML("files/element.xml");
            } catch (JMusicHubFileDoesntExistException e) {
                e.printStackTrace();
            }
            mediaplayer.put("albums", albums);
            mediaplayer.put("playlists", playlists);
            mediaplayer.put("elements", elements);
            Boolean isOn = true;
            byte[] choice = "h".getBytes();

            System.out.println("\n\n\n\n ---------------------- Data have been loaded -------------------------");
            while (isOn){
                switch (choice[0]){
                    case 'h':
                        MediaPlayer.help();
                        break;
                    case 'a':
                        break;
                    case 'l':

                        break;
                    case '+':
                        addSongToPlaylist();
                        break;
                    case '-':
                        deletePlaylist();
                        break;
                    case 's':
                        break;
                    case 'P':
                        afficherPlaylists();
                        break;
                    case 'A':
                        afficherAlbum();
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
