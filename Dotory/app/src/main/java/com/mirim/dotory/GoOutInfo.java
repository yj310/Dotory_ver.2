package com.mirim.dotory;

import java.io.Serializable;

public class GoOutInfo implements Serializable {

    private String enter_time;
    private String expect_time;
    private String go_out_time;
    private String name;
    private String place;
    private String reason;
    private int room;
    private String state;

    public GoOutInfo() {
    }

    public GoOutInfo(String expect_time, String go_out_time, String name, String place, String reason, int room) {
        this.enter_time = "";
        this.expect_time = expect_time;
        this.go_out_time = go_out_time;
        this.name = name;
        this.place = place;
        this.reason = reason;
        this.room = room;
        this.state = "외출중";
    }

    public String getEnter_time() {
        return enter_time;
    }

    public void setEnter_time(String enter_time) {
        this.enter_time = enter_time;
    }

    public String getExpect_time() {
        return expect_time;
    }

    public void setExpect_time(String expect_time) {
        this.expect_time = expect_time;
    }

    public String getGo_out_time() {
        return go_out_time;
    }

    public void setGo_out_time(String go_out_time) {
        this.go_out_time = go_out_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
}
