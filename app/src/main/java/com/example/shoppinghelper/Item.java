package com.example.shoppinghelper;

public class Item {
    String name, konzum, tisak, spar;

    public Item(){}

    public Item(String name, String konzum, String spar, String tisak){
        this.name = name;
        this.konzum = konzum;
        this.spar = spar;
        this.tisak = tisak;
    }

    public String getName(){return name;}
    public String getKonzum(){return konzum;}
    public String getTisak(){return tisak;}
    public String getSpar(){return spar;}

    public void setName(String name){this.name = name;}
    public void setKonzum(String konzum){this.konzum = konzum;}
    public void setTisak(String tisak){this.tisak = tisak;}
    public void setSpar(String spar){this.spar = spar;}

}
