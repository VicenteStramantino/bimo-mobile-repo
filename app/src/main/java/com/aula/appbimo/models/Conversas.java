package com.aula.appbimo.models;

public class Conversas {
    private String contactName;
    private String lastMessage;
    private String messageTime;
    private boolean isMessageRead;
    private String profileImageUrl;

    public Conversas(String contactName, String lastMessage, String messageTime, boolean isMessageRead, String profileImageUrl) {
        this.contactName = contactName;
        this.lastMessage = lastMessage;
        this.messageTime = messageTime;
        this.isMessageRead = isMessageRead;
        this.profileImageUrl = profileImageUrl;
    }

    public Conversas() {
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public boolean isMessageRead() {
        return isMessageRead;
    }

    public void setMessageRead(boolean messageRead) {
        isMessageRead = messageRead;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String toString() {
        return "Conversas: " + contactName + ", " + lastMessage + ", " + messageTime + ", " + isMessageRead + ", " + profileImageUrl;
    }
}
