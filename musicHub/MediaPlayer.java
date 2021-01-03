package musicHub;

import java.io.IOException;
import java.util.*;
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
            put("p", "Créer une playlist");
            put("P", "Afficher les playlists rangées par nom");
            put("A", "Afficher les albums");
            put("y", "Afficher le contenue d'une playlist");
            put("u", "Afficher les morceau d'un album");
            put("t", "Afficher les titres d'albums triés par date de sortie");
            put("T", "Afficher les titres d'un album rangé par genre");
            put("L", "Livres audio rangés par auteur");
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

    /**
     * Add a new audio book to a Elements list
     * */
    private void ajoutLivreAudioElement(){
        try {
            LivreAudio livreAudio = LivreAudio.createAudioBook();
            mediaplayer.get("elements").add(livreAudio);
            System.out.println("Le livre audio a bien été ajouté à votre liste d'éléments");
        }catch (Exception e){
            System.out.println("Vous avez entré une valeur invalide au clavier : " + e.getMessage());
        }
    }

    /**
     * Add a new song to a Elements list
     * */
    private void ajoutChansonElement(){
        try {
            Chanson chanson = Chanson.createSong();
            mediaplayer.get("elements").add(chanson);
            System.out.println("La nouvelle chanson a bien été ajoutée à votre liste d'éléments");
        }catch (Exception e){
            System.out.println("Vous avez entré une valeur invalide au clavier : " + e.getMessage());
        }
    }



    /***
     * Delete a playlist from the playlists list
     */
    private void deletePlaylist() throws IOException {
        try {
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
        }catch (Exception e){
            System.out.println("Vous avez entré une valeur invalide au clavier : " + e.getMessage());
        }
    }

    /***
     * Display all the album song
     */
    void afficherUnAlbum(){
        try{
            System.out.println("Entrez l'index de l'album que vous souhaitez afficher");
            for (var index = 0; index < mediaplayer.get("albums").size(); index++){
                var albums = (Album)mediaplayer.get("albums").get(index);
                System.out.println(index + " - " + albums.getTitre());
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
        } catch (Exception e){
            System.out.println("Vous avez entré une valeur invalide au clavier : " + e.getMessage());
        }
    }

    /**
     *  Display all the albums titles order by inc date of release
     */
    void afficherAlbumTrierParDateSortie() {
        Album[] albums = mediaplayer.get("albums").toArray(new Album[0]);
        Arrays.sort(albums);
        for (var index = 0; index < albums.length; index++) {
            var album = albums[index];
            System.out.println(index + " - " + album.getTitre());
        }
    }

    /**
     * Display playlist title order by title name
     * */
    void afficherPlaylistParOrdreAlphabetique() {
        Playlist[] playlists = mediaplayer.get("playlists").toArray(new Playlist[0]);
        Arrays.sort(playlists);
        for (var index = 0; index < playlists.length; index++) {
            var album = playlists[index];
            System.out.println(index + " - " + album.getTitre());
        }
    }

    /**
     * Display a playlist content
     * */
    void afficherUnePlaylist(){
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
            String choice = "h";

            System.out.println("\n\n\n\n ---------------------- Data have been loaded -------------------------");
            while (isOn){
                switch (choice.charAt(0)){
                    case 'h':
                        MediaPlayer.help();
                        break;
                    case 'c':
                        ajoutChansonElement();
                    case 'a':
                        break;
                    case 'l':
                        ajoutLivreAudioElement();
                        break;
                    case '+':
                        addSongToPlaylist();
                        break;
                    case '-':
                        deletePlaylist();
                        break;
                    case 's':
                        Album.writeAlbums("./albums_save.xml", mediaplayer.get("albums"));
                        Playlist.writePlaylists("./playlists_save.xml", mediaplayer.get("playlists"));
                        break;
                    case 'A':
                        afficherUnAlbum();
                        break;
                    case 't':
                        afficherAlbumTrierParDateSortie();
                        break;
                    case 'y':
                        afficherUnePlaylist();
                    case 'p':
                        System.out.println("Not implemented");
                        break;
                    case 'P':
                        afficherPlaylistParOrdreAlphabetique();
                        break;
                    default:
                        isOn = false;
                }
                System.out.println("---------\n\n Quel est votre choix ? (entrer h pour afficher l'aide ou entrer pour quitter)");
                Scanner scan = new Scanner(System.in);
                choice = scan.nextLine();
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