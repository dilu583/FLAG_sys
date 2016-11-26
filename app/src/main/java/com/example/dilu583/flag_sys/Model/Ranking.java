package com.example.dilu583.flag_sys.Model;

/**
 * Created by dilu583 on 10/24/2016.
 */
public class Ranking {

    private int Id;
    private int Score;

    public Ranking(int id, int score) {
        Id = id;
        Score = score;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
