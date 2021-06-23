package com.mirim.dotory;

import java.io.Serializable;

public class EnterInfo implements Serializable {

    private String email;
    private String enter_time;
    private String name;
    private int room;
    private String state;
    private double temp;

    public EnterInfo() {
    }

    public EnterInfo(String email, String enter_time, String name, int room, String state, double temp) {
        this.email = email;
        this.enter_time = enter_time;
        this.name = name;
        this.room = room;
        this.state = state;
        this.temp = temp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnter_time() {
        return enter_time;
    }

    public void setEnter_time(String enter_time) {
        this.enter_time = enter_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
