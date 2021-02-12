package com.example.vocabularybattle.model;

public class WordSource {
    private String sourceName;
    private Word word;
    private String imageUrl;
    public WordSource(){

    }
    public WordSource(String sourceName, Word word,String imageUrl) {
        this.sourceName = sourceName;
        this.word = word;
        this.imageUrl=imageUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
