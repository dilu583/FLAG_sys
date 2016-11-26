package com.example.dilu583.flag_sys.Model;

/**
 * Created by dilu583 on 10/24/2016.
 */
public class Question {
    private int ID;
    private String Image;
    private String AnswerA;
    private String AnswerB;
    private String AnswerC;
    private String AnswerD;
    private String CorrectAnswer;

    public Question(int ID, String image, String answerA, String answerB, String answerC, String answerD, String correctAnswer) {
        this.ID = ID;
        Image = image;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        CorrectAnswer = correctAnswer;
    }

    public int getID() {
        return ID;
    }

    public String getImage() {
        return Image;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }
}
