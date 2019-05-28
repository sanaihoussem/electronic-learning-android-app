package com.sim.treasity.Models;

/**
 * Created by houcem on 10/12/2017.
 */

public class quiz {
    private String question;
    private String reponse1;
    private String reponse2;
    private String reponse3;
    private String reponse4;
    private String bonneReponse;
    private String note;
    private String difficulte;

    public quiz() {
    }

    public quiz(String question, String reponse1, String reponse2, String reponse3, String reponse4, String bonneReponse, String note, String difficulte) {
        this.question = question;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponse3 = reponse3;
        this.reponse4 = reponse4;
        this.bonneReponse = bonneReponse;
        this.note = note;
        this.difficulte = difficulte;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse1() {
        return reponse1;
    }

    public void setReponse1(String reponse1) {
        this.reponse1 = reponse1;
    }

    public String getReponse2() {
        return reponse2;
    }

    public void setReponse2(String reponse2) {
        this.reponse2 = reponse2;
    }

    public String getReponse3() {
        return reponse3;
    }

    public void setReponse3(String reponse3) {
        this.reponse3 = reponse3;
    }

    public String getReponse4() {
        return reponse4;
    }

    public void setReponse4(String reponse4) {
        this.reponse4 = reponse4;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    @Override
    public String toString() {
        return "quiz{" +
                "question='" + question + '\'' +
                ", reponse1='" + reponse1 + '\'' +
                ", reponse2='" + reponse2 + '\'' +
                ", reponse3='" + reponse3 + '\'' +
                ", reponse4='" + reponse4 + '\'' +
                ", bonneReponse='" + bonneReponse + '\'' +
                ", note='" + note + '\'' +
                ", difficulte='" + difficulte + '\'' +
                '}';
    }
}
