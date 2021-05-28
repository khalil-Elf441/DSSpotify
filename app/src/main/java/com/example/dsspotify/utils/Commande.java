package com.example.dsspotify.utils;

public class Commande {


    String DictionnaireWord;
    String targetMusic;

    public Commande(String dictionnaireWord, String targetMusic) {
        DictionnaireWord = dictionnaireWord;
        this.targetMusic = targetMusic;
    }

    public String getDictionnaireWord() {
        return DictionnaireWord;
    }

    public void setDictionnaireWord(String dictionnaireWord) {
        DictionnaireWord = dictionnaireWord;
    }

    public String getTargetMusic() {
        return targetMusic;
    }

    public void setTargetMusic(String targetMusic) {
        this.targetMusic = targetMusic;
    }
}
