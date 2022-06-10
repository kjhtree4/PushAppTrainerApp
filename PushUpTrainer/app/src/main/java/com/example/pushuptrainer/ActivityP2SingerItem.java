package com.example.pushuptrainer;

public class ActivityP2SingerItem {
    String Name;

    public ActivityP2SingerItem(String name){
        this.Name=name;
    }
    public String getName(){
        return Name;
    }
    public String setName(String name){
        return this.Name=name;
    }
}