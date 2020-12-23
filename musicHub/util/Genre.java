package util;

public enum Genre {
    Jazz,
    Classique,
    HipHop,
    Rock,
    Rap;

    public static Genre getGender(String value) throws JMusicHubException {
        String lowered = value.toLowerCase();
        switch (lowered) {
            case "jazz":
                return Genre.Jazz;
            case "hiphop":
                return Genre.HipHop;
            case "rock":
                return Genre.Rock;
            case "rap":
                return Genre.Rap;
            case "classique":
                return Genre.Classique;
            default:
                throw new JMusicHubException();
        }
    }
}
