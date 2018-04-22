package Solver;

public class Word
{
    private String word;
    private Boolean isUsed;

    public Word(String word, Boolean isUsed) {
        this.word = word;
        this.isUsed = isUsed;
    }

    public String getWord() {
        return word;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }
}
