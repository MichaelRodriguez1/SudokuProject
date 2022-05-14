package app.models.match;

public enum Difficulty {

    SIMPLE("Simple"),
    EASY("Facil"),
    INTERMEDIATE("Normal"),
    EXPERT("Dificil");

    private String text;

    Difficulty(String text) {
        this.text = text;
    }

    public static Difficulty get(String text){
        for (Difficulty difficulty: Difficulty.values()) {
            if (difficulty.text.equals(text)){
                return difficulty;
            }
        }
        return null;
    }
}
