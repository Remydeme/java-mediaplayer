package musicHub.util;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends Media {

    protected List<Media> medias = new ArrayList<>();

    @Override
    public String getTitre() {
        return super.getTitre();
    }

    @Override
    public int getDuree() {
        return super.getDuree();
    }

    @Override
    public void setTitre(String titre) {
        super.setTitre(titre);
    }

    @Override
    public void setDuree(int duree) {
        super.setDuree(duree);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    // insert a new media in the playlist
    public void addMedia(Media media){
        if (media != null){
            medias.add(media);
        }
    }

    // build a playlist from a XML.Node
    public static Playlist readPlaylistFromNode(Node node){
        try {
            var playlistNode = (Element)node;
            NodeList nChansonList = playlistNode.getElementsByTagName("chanson");
            NodeList nLivreAudioList = playlistNode.getElementsByTagName("livreAudio");
            System.out.println("----------------------------");
            Playlist playlist = new Playlist();
            int playlistDuree = Integer.parseInt(playlistNode.getElementsByTagName("duree").item(0).getTextContent());
            var playlistTitre  = playlistNode.getElementsByTagName("titre").item(0).getTextContent();
            String albumId = playlistNode.getAttribute("id");
            playlist.setDuree(playlistDuree);
            playlist.setTitre(playlistTitre);
            playlist.setID(albumId);
            for (int temp = 0; temp < nChansonList.getLength(); temp++) {
                Node nNode = nChansonList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Chanson chanson = Chanson.readChansonFronElement(eElement);
                    playlist.addMedia(chanson);
                }
            }
            for (int temp = 0; temp < nLivreAudioList.getLength(); temp++) {
                Node nNode = nLivreAudioList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    LivreAudio livreAudio = LivreAudio.readLivreAudioFronElement(eElement);
                    playlist.addMedia(livreAudio);
                }
            }
            return playlist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // build a playlist from a XML.Node
    public static List<Media> readElementFromXML(String path) throws JMusicHubFileDoesntExistException{
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nChansonList = doc.getElementsByTagName("chanson");
            NodeList nLivreAudioList = doc.getElementsByTagName("livreAudio");

            List<Media> medias = new ArrayList<>();
            for (int temp = 0; temp < nChansonList.getLength(); temp++) {
                Node nNode = nChansonList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Chanson chanson = Chanson.readChansonFronElement(eElement);
                    medias.add(chanson);
                }
            }
            for (int temp = 0; temp < nLivreAudioList.getLength(); temp++) {
                Node nNode = nLivreAudioList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    LivreAudio livreAudio = LivreAudio.readLivreAudioFronElement(eElement);
                    medias.add(livreAudio);
                }
            }
            return medias;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // write playlist
    public static void writePlaylist(Document document, Playlist p){
        Element album = document.createElement("playlist");
        Attr monAttribut = document.createAttribute("id");
        monAttribut.setValue(p.getID());
        album.setAttributeNode(monAttribut);
        Element duree = document.createElement("duree");
        Element titre = document.createElement("titre");
        album.appendChild(duree);
        album.appendChild(document.createTextNode(String.valueOf(p.getDuree())));
        album.appendChild(titre);
        album.appendChild(document.createTextNode(String.valueOf(p.getTitre())));
        // call WriteChansons
        for(Media media : p.getMedias()){
            if (media instanceof Chanson){
                Chanson.writeAlbum(document, (Chanson) media);
            }else if (media instanceof LivreAudio){
                LivreAudio.writeLivreAudio(document, (LivreAudio) media);
            }
        }
    }

    /**
     * create an xml file and save the build playlist in it
     */
    public static void writePlaylists(String path, List<Playlist> playlists) throws UnsupportedEncodingException {
        Document document = null;
        DocumentBuilderFactory fabrique = null;
        try {
            fabrique = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fabrique.newDocumentBuilder();
            document = builder.newDocument();
            Element racine = (Element) document.createElement("playlists");
            document.appendChild(racine);
            for (Playlist playlist : playlists){
                Playlist.writePlaylist(document, playlist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Create a list of playlist from an XML File
     * */
    public static List<Media> readPlaylistsFromXLM(String path) throws JMusicHubFileDoesntExistException {
        try {

            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("playlists");

            System.out.println("----------------------------");
            List<Media> playlists = new ArrayList<>();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                var playlist = musicHub.util.Playlist.readPlaylistFromNode(nNode);
                playlists.add(playlist);
            }
            return playlists;
        } catch (FileNotFoundException e) {
            throw new JMusicHubFileDoesntExistException();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
