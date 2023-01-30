package com.example.docs;

public class Model {

    private int id;
    private byte[] proavatar;
    private String username;

    public Model(int id, byte[] proavatar, String username) {
        this.id = id;
        this.proavatar = proavatar;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProavatar() {
        return proavatar;
    }

    public void setProavatar(byte[] proavatar) {
        this.proavatar = proavatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
