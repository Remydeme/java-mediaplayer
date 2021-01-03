package musicHub.util;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class LivreAudio extends  Media {

    String auteur;
    Langue langue;
    Categorie categorie;
    String contenue = "";



    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

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

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }
    @Override
    public void setID(String ID) {
        super.setID(ID);
    }

    /**
     * Create a new audio book
     * */
    public static LivreAudio createAudioBook(){
        Scanner scan = new Scanner(System.in);
        LivreAudio livreAudio = new  LivreAudio();
        System.out.println("\n \n Create a new audio book  \n ");
        System.out.println("|----- Entrer le contenue du livre audio -------|\n ");
        String contenue = scan.nextLine();
        System.out.println("|----- Entrer le titre -------|\n ");
        String titre = scan.nextLine();
        System.out.println("|----- Entrer le nom de l'auteur du livre audio -------|\n ");
        String auteur = scan.nextLine();
        System.out.println("|----- Entrer la duree du livre audio -------|\n ");
        int duree = scan.nextInt();
        System.out.println("|----- Entrer l'id du livre audio -------|\n ");
        String id = scan.nextLine();
        Langue langue = choisirLaLangue();
        Categorie categorie = choisirLaCategorie();

        livreAudio.auteur = auteur;
        livreAudio.contenue = contenue;
        livreAudio.langue = langue;
        livreAudio.ID = id;
        livreAudio.date = Calendar.getInstance().getTime();
        livreAudio.titre = titre;
        livreAudio.Duree = duree;
        livreAudio.categorie  = categorie;
        return livreAudio;
    }

    public static Langue choisirLaLangue() {
        Scanner scan = new Scanner(System.in);
        System.out.println("|Quel est la langue de votre livre|\n ");
        System.out.println("|1- Francais |\n|2- Anglais |\n|3- Espagnol |\n|4- Allemand |\n|5- Italien |\n ");
        int index = scan.nextInt();
        switch (index){
            case 1:
                return Langue.Francais;
            case 2:
                return Langue.Anglais;
            case 3:
                return Langue.Espagnol;
            case 4:
                return Langue.Allemand;
            case 5:
                return Langue.Italien;
            default:
                return Langue.Anglais;
        }
    }

    public static Categorie choisirLaCategorie() {
        Scanner scan = new Scanner(System.in);
        System.out.println("|Quel est la categorie de votre livre|\n ");
        System.out.println("|1- Documentaire |\n|2- Discours |\n|3- Jeunesse |\n|4- Roman |\n|5- Theatre |\n ");
        int index = scan.nextInt();
        switch (index){
            case 1:
                return Categorie.Documentaire;
            case 2:
                return Categorie.Discours;
            case 3:
                return Categorie.Jeunesse;
            case 4:
                return Categorie.Roman;
            case 5:
                return Categorie.Theatre;
            default:
                return Categorie.Roman;
        }
    }

    public static void writeLivreAudio(Document document, LivreAudio l){
        Element livreAudio = document.createElement("livreAudio");
        Attr monAttribut = document.createAttribute("id");
        monAttribut.setValue(l.getID());
        livreAudio.setAttributeNode(monAttribut);
        Element duree = document.createElement("duree");
        Element titre = document.createElement("titre");
        Element artiste = document.createElement("artiste");
        Element date = document.createElement("date");
        Element categorie = document.createElement("categorie");
        Element langue = document.createElement("langue");
        Element contenue = document.createElement("contenue");
        duree.appendChild(document.createTextNode(String.valueOf(l.getDuree())));
        livreAudio.appendChild(duree);
        titre.appendChild(document.createTextNode(l.getTitre()));
        livreAudio.appendChild(titre);
        artiste.appendChild(document.createTextNode(l.getAuteur()));
        livreAudio.appendChild(artiste);
        categorie.appendChild(document.createTextNode(String.valueOf(l.getCategorie())));
        livreAudio.appendChild(categorie);
        langue.appendChild(document.createTextNode(String.valueOf(l.getLangue())));
        livreAudio.appendChild(langue);
        contenue.appendChild(document.createTextNode(l.getContenue()));
        livreAudio.appendChild(contenue);
        date.appendChild(document.createTextNode(String.valueOf(l.getDate())));
        // call WriteChansons
    }

    public static LivreAudio readLivreAudioFronElement(Element eElement){
        try {
            if (eElement != null) {
                LivreAudio livreAudio = new LivreAudio();
                String id = eElement.getAttribute("id");
                System.out.println("chanson id : " + id);
                livreAudio.setID(id);
                String titre = eElement.getElementsByTagName("titre").item(0).getTextContent();
                livreAudio.setTitre(titre);
                System.out.println("titre : " + titre);
                int duree = Integer.parseInt(eElement.getElementsByTagName("duree").item(0).getTextContent());
                livreAudio.setDuree(duree);
                System.out.println("duree : " + duree);
                Categorie categorie = Categorie.getCategorie(eElement.getElementsByTagName("categorie").item(0).getTextContent());
                livreAudio.setCategorie(categorie);
                System.out.println("categorie : " + categorie);
                String auteur = eElement.getElementsByTagName("auteur").item(0).getTextContent();
                livreAudio.setAuteur(auteur);
                System.out.println("auteur : " + auteur);
                String contenue = eElement.getElementsByTagName("contenue").item(0).getTextContent();
                livreAudio.setContenue(contenue);
                System.out.println("contenue : " + contenue);
                return livreAudio;
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
