package com.example.dainguyen.gametrucxanh;



public class topic {
    int id;
    String info;
    int image,background;
    String answer,stranswer;

    public topic(int id, String info, int image, int background, String answer, String stranswer) {
        this.id = id;
        this.info = info;
        this.image = image;
        this.background = background;
        this.answer = answer;
        this.stranswer = stranswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStranswer() {
        return stranswer;
    }

    public void setStranswer(String stranswer) {
        this.stranswer = stranswer;
    }
}
