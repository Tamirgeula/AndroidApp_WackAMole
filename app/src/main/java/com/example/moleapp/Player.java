package com.example.moleapp;

public class Player  {
    enum status{WIN,LOSE, OUT_OF_TIME}

    public static final int PLAYER_INIT_TIME=30;
    public static final int WIN_HITS=30;
    public static final int LOST_MISSES=3;

    private String name;
    private int hits=0;
    private int misses=0;
    private status res;




    public Player(String name){
        this.name=name;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public void hit(){
        hits++;
    }
    public void miss(){
        misses++;
    }
    public String getName() {
        return name;
    }

    public status getRes() {
        return res;
    }

    public void setRes(status res) {
        this.res = res;
    }
}
