package util;

abstract class MusicMedia extends Media {
    protected String chanteur;

    public String getChanteur() {
        return chanteur;
    }

    public void setChanteur(String chanteur) {
        this.chanteur = chanteur;
    }
}
