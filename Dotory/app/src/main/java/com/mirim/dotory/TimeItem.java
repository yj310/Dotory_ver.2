package com.mirim.dotory;

public class TimeItem {

    private int key;
    private boolean use;
    private String start;
    private String end;


    public TimeItem() {
    }

    public TimeItem(int key, boolean use, String start, String end) {
        this.key = key;
        this.use = use;
        this.start = start;
        this.end = end;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
