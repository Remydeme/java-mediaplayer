package musicHub.util;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Chanson extends MusicMedia {

    protected String contenue;
    protected Genre genre;


    @Override
    public Date getDate() {
        return super.getDate();
    }

    @Override
    public void setDate(Date date) {
        super.setDate(date);
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

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Create a new audio book
     * */
    public static Chanson createSong(){
        Scanner scan = new Scanner(System.in);
        Chanson chanson = new Chanson();
        System.out.println("\n \n Enregistrer une nouvelle chanson \n ");
        System.out.println("|----- Entrer les paroles de la chanson -------|\n ");
        String contenue = scan.nextLine();
        System.out.println("|----- Entrer le titre -------|\n ");
        String titre = scan.nextLine();
        System.out.println("|----- Entrer le nom du chanteur -------|\n ");
        String auteur = scan.nextLine();
        System.out.println("|----- Entrer la duree de la chanson-------|\n ");
        int duree = scan.nextInt();
        System.out.println("|----- Entrer l'id de la chanson-------|\n ");
        String id = scan.nextLine();
        Genre genre = choisirGenre();

        chanson.chanteur = auteur;
        chanson.contenue = contenue;
        chanson.genre = genre;
        chanson.ID = id;
        chanson.date = Calendar.getInstance().getTime();
        chanson.titre = titre;
        chanson.Duree = duree;
        return chanson;
    }

    public static Genre choisirGenre() {
        Scanner scan = new Scanner(System.in);
        System.out.println("|Quel est le genre de votre chanson |\n ");
        System.out.println("|1- HipHop |\n|2- Jazz |\n|3- Classique |\n|4- Rap|\n|5- Rock |\n ");
        int index = scan.nextInt();
        switch (index){
            case 1:
                return Genre.HipHop;
            case 2:
                return Genre.Jazz;
            case 3:
                return Genre.Classique;
            case 4:
                return Genre.Rap;
            case 5:
                return Genre.Rock;
            default:
                return Genre.Classique;
        }
    }

    public static void writeAlbum(Document document, Chanson c){
        Element chanson = document.createElement("chanson");
        Attr monAttribut = document.createAttribute("id");
        monAttribut.setValue(c.getID());
        chanson.setAttributeNode(monAttribut);
        Element duree = document.createElement("duree");
        Element titre = document.createElement("titre");
        Element artiste = document.createElement("artiste");
        Element date = document.createElement("date");
        Element genre = document.createElement("genre");
        Element contenue = document.createElement("contenue");
        duree.appendChild(document.createTextNode(String.valueOf(c.getDuree())));
        chanson.appendChild(duree);
        titre.appendChild(document.createTextNode(c.getTitre()));
        chanson.appendChild(titre);
        artiste.appendChild(document.createTextNode(c.getChanteur()));
        chanson.appendChild(artiste);
        genre.appendChild(document.createTextNode(String.valueOf(c.getGenre())));
        chanson.appendChild(genre);
        contenue.appendChild(document.createTextNode(c.getContenue()));
        chanson.appendChild(contenue);
        date.appendChild(document.createTextNode(String.valueOf(c.getDate())));
        // call WriteChansons
    }

    public static Chanson readChansonFronElement(Element eElement){
        try {
            if (eElement != null) {
                Chanson chanson = new Chanson();
                String id = eElement.getAttribute("id");
                System.out.println("chanson id : " + id);
                chanson.setID(id);
                String titre = eElement.getElementsByTagName("titre").item(0).getTextContent();
                chanson.setTitre(titre);
                System.out.println("titre : " + titre);
                int duree = Integer.parseInt(eElement.getElementsByTagName("duree").item(0).getTextContent());
                chanson.setDuree(duree);
                System.out.println("duree : " + duree);
                Genre genre = Genre.getGender(eElement.getElementsByTagName("genre").item(0).getTextContent());
                chanson.setGenre(genre);
                System.out.println("genre : " + genre);
                String artiste = eElement.getElementsByTagName("artiste").item(0).getTextContent();
                chanson.setChanteur(artiste);
                System.out.println("artiste : " + artiste);
                String contenue = eElement.getElementsByTagName("contenue").item(0).getTextContent();
                chanson.setContenue(contenue);
                System.out.println("contenue : " + contenue);
                return chanson;
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



