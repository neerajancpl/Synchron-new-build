package com.synchron.ncpl.synchron;

/**
 * Created by NCPL on 5/23/2016.
 */
public class Chat {
    private String message;
    private String author;
    //private String image;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Chat() {
    }

    Chat(String message, String author) {
        this.message = message;
        this.author = author;
        //this.author=image;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
    /*public String getImage() {
        return image;
    }*/
}

