package musicHub.util;

import java.util.Date;

public abstract class Media{
    // musicHub.util.Media title
    protected String titre;
    // Delay in secondes
    protected int Duree;
    // Unique media identifier
    protected String ID;
    // publication date
    protected Date date;

    public String getTitre() {
        return titre;
    }

    public int getDuree() {
        return Duree;
    }

    public String getID() {
        return ID;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDuree(int duree) {
        Duree = duree;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void description(){
        System.out.println(getID() + " - "+ titre + " " + getDuree() + " " + getDuree());
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
