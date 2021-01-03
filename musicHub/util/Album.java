package musicHub.util;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;

import org.w3c.dom.*;

public class Album extends MusicMedia implements Comparable<Album>{
    // publication date
    protected Date sortie;
    protected List<Chanson> chansons = new ArrayList<>();
    protected Genre genre;
    // track the next song to play
    protected int cursor = 0;

    // Return a random song
    Chanson getRandomSong() {
        int max = chansons.size();
        if (max < 0) {
            return null;
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, max);
        return chansons.get(randomNum);
    }

    @Override
    public String getTitre() {
        return super.getTitre();
    }

    @Override
    public int getDuree() {
        return super.getDuree();
    }

    @Override
    public String getID() {
        return super.getID();
    }

    @Override
    public void setTitre(String titre) {
        super.setTitre(titre);
    }

    @Override
    public void setDuree(int duree) {
        super.setDuree(duree);
    }

    @Override
    public void setID(String ID) {
        super.setID(ID);
    }

    @Override
    public String getChanteur() {
        return super.getChanteur();
    }

    @Override
    public void setChanteur(String chanteur) {
        super.setChanteur(chanteur);
    }

    Chanson getNexSong() {
        this.cursor += 1;
        return chansons.get(this.cursor);
    }

    public Date getSortie() {
        return sortie;
    }

    public void setSortie(Date sortie) {
        this.sortie = sortie;
    }

    public List<Chanson> getChansons() {
        return chansons;
    }

    public void setChansons(List<Chanson> chansons) {
        this.chansons = chansons;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void ajoutChanson(Chanson c) {
        this.chansons.add(c);
    }

    // read and album from a node
    public static Album readAlbumFromNode(Node node) throws JMusicHubException{
        try {
            var albumNode = (Element)node;
            NodeList nList = albumNode.getElementsByTagName("chanson");
            System.out.println("Current node name " + node.getNodeName());
            System.out.println("----------------------------");
            Album album = new Album();
            int albumDuree = Integer.parseInt(albumNode.getElementsByTagName("duree").item(0).getTextContent());
            var albumTitre  = albumNode.getElementsByTagName("titre").item(0).getTextContent();
            var albumArtiste  = albumNode.getElementsByTagName("artiste").item(0).getTextContent();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            String albumDateString  = albumNode.getElementsByTagName("date").item(0).getTextContent();
            Date albumDate = formatter1.parse(albumDateString);
            Genre albumGenre = Genre.getGender(albumNode.getElementsByTagName("genre").item(0).getTextContent());
            String albumId = albumNode.getAttribute("id");
            album.setDuree(albumDuree);
            album.setTitre(albumTitre);
            album.setChanteur(albumArtiste);
            album.setSortie(albumDate);
            album.setGenre(albumGenre);
            album.setID(albumId);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Chanson chanson = Chanson.readChansonFronElement(eElement);
                    album.ajoutChanson(chanson);
                }
            }
            return album;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int compareTo(Album o){
        if (this.date != null && o.date != null){
            return this.date.compareTo(o.date);
        }
        return 1;
    }

    // read albums from XML file
    public static List<Media> readAlbumsFromXLM(String path) throws JMusicHubFileDoesntExistException {
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("album");

            System.out.println("----------------------------");
            List<Media> albums = new ArrayList<>();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                var album = readAlbumFromNode(nNode);
                albums.add(album);
            }
            return albums;
        } catch (FileNotFoundException e) {
            throw new JMusicHubFileDoesntExistException();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void writeAlbum(Document document, Album a){
        Element album = document.createElement("album");
        Attr monAttribut = document.createAttribute("id");
        monAttribut.setValue(a.getID());
        album.setAttributeNode(monAttribut);
        Element duree = document.createElement("duree");
        Element titre = document.createElement("titre");
        Element artiste = document.createElement("artiste");
        album.appendChild(duree);
        album.appendChild(document.createTextNode(String.valueOf(a.getDuree())));
        album.appendChild(titre);
        album.appendChild(document.createTextNode(String.valueOf(a.getTitre())));
        album.appendChild(artiste);
        album.appendChild(document.createTextNode(String.valueOf(a.getChanteur())));
        // call WriteChansons
        for(Chanson chanson : a.getChansons()){
            Chanson.writeAlbum(document, chanson);
        }
    }

    public static void writeAlbums(String path, List<Media> albums) throws UnsupportedEncodingException {
        Document document = null;
        DocumentBuilderFactory fabrique = null;
        try {
            fabrique = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fabrique.newDocumentBuilder();
            document = builder.newDocument();
            Element racine = (Element) document.createElement("albums");
            document.appendChild(racine);
            for (Media m : albums){
                Album.writeAlbum(document, (Album) m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}