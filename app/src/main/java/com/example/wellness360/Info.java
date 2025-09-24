package com.example.wellness360;

public class Info {

    String email;
    String paswd;
    int id;
    public Info(){   }
    public Info(int id, String email, String paswd){
        this.id= id;
        this.email = email;
        this.paswd = paswd;
    }
    public Info(String email, String paswd){
        this.email = email;
        this.paswd = paswd;
    }
    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPaswd(){
        return this.paswd;
    }

    public void setPaswd(String paswd){
        this.paswd = paswd;
    }
}
