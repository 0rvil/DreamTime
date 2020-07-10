package com.dreamtime.dreamtimearcade;



import java.io.Serializable;


public class Model implements Serializable {
    private  String Name;
    private String Email;
    private int Coin_Toss_High_Score;
    private int Rps_High_Score;
    private int TicTacToe_High_Score;
    private int High_Score;

    public Model() {
    }
    public Model(String name,int high_Score){
        this.Name = name;
        this.High_Score = high_Score;
    }
    public Model(String name, String email, int ticTacToe_High_Score, int coin_Toss_High_Score, int rps_High_Score, int high_Score){
        this.Name = name;
        this.Email = email;
        this.TicTacToe_High_Score = ticTacToe_High_Score;
        this.Coin_Toss_High_Score = coin_Toss_High_Score;
        this.Rps_High_Score = rps_High_Score;
        this.High_Score = high_Score;
    }

    public int getHigh_Score() {
        return High_Score;
    }

    public void setHigh_Score(int high_Score) {
        High_Score = high_Score;
    }

    public String getName() {
        return Name;
    }

    public  String getEmail() {
        return Email;
    }

    public  int getCoin_Toss_High_Score() {
        return Coin_Toss_High_Score;
    }

    public  int getRps_High_Score() {
        return Rps_High_Score;
    }

    public  int getTicTacToe_High_Score() {
        return TicTacToe_High_Score;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setCoin_Toss_High_Score(int coin_Toss_High_Score) {
        Coin_Toss_High_Score = coin_Toss_High_Score;
    }

    public  void setRps_High_Score(int rps_High_Score) {
        Rps_High_Score = rps_High_Score;
    }

    public  void setTicTacToe_High_Score(int ticTacToe_High_Score) {
        TicTacToe_High_Score = ticTacToe_High_Score;
    }
}
