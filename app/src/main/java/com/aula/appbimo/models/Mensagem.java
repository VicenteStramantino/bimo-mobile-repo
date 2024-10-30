package com.aula.appbimo.models;

import java.util.Date;

public class Mensagem {
    private String text;
    private boolean isFromUser;
    private Date timestamp;

    public Mensagem(String text, boolean isFromUser, Date timestamp) {
        this.text = text;
        this.isFromUser = isFromUser;
        this.timestamp = timestamp;
    }

    public Mensagem() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFromUser() {
        return isFromUser;
    }

    public void setFromUser(boolean fromUser) {
        isFromUser = fromUser;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Mensagem{" +
                "text='" + text + '\'' +
                ", isFromUser=" + isFromUser +
                ", timestamp=" + timestamp +
                '}';
    }
}
