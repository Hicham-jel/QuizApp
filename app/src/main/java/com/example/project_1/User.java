package com.example.project_1;

public class User {
    String userId;
    int score;
    public User() {
    }

    public User(String userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
