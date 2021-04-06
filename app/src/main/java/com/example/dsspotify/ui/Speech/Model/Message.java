package com.example.dsspotify.ui.Speech.Model;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class Message implements IMessage {

    /*...*/

    public String id;
    public String text;
    public Date createdAt;
    public Author author;

    public Message(String id, String text, Date createdAt,String userID) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.author = new Author(userID,"khalil","");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}