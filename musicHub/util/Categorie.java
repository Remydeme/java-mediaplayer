package util;

public enum Categorie {
    Jeunesse,
    Roman,
    Theatre,
    Discours,
    Documentaire;

    public static Categorie getCategorie(String value) throws JMusicHubException {
        String lowered = value.toLowerCase();
        switch (lowered) {
            case "documentaire":
                return Categorie.Documentaire;
            case "discours":
                return Categorie.Discours;
            case "theatre":
                return Categorie.Theatre;
            case "roman":
                return Categorie.Roman;
            case "jeunesse":
                return Categorie.Jeunesse;
            default:
                throw new JMusicHubException();
        }
    }
}
