package com.mirim.dotory.student;

public class StudentUser {

    private String name;
    private int room;
    private String email;
    private String phone;
    private String guardian_phone;
    private String address_load;
    private String address_detail;
    private int grade;
    private int school_class;
    private int class_number;
    private int birth_year;
    private int birth_month;
    private int birth_day;

    public StudentUser(){}

    public StudentUser(String name, int room, String email, String phone, String guardian_phone, String address_load, String address_detail, int grade, int school_class, int class_number, int birth_year, int birth_month, int birth_day) {
        this.name = name;
        this.room = room;
        this.email = email;
        this.phone = phone;
        this.guardian_phone = guardian_phone;
        this.address_load = address_load;
        this.address_detail = address_detail;
        this.grade = grade;
        this.school_class = school_class;
        this.class_number = class_number;
        this.birth_year = birth_year;
        this.birth_month = birth_month;
        this.birth_day = birth_day;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGuardian_phone() {
        return guardian_phone;
    }

    public void setGuardian_phone(String guardian_phone) {
        this.guardian_phone = guardian_phone;
    }

    public String getAddress_load() {
        return address_load;
    }

    public void setAddress_load(String address_load) {
        this.address_load = address_load;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSchool_class() {
        return school_class;
    }

    public void setSchool_class(int school_class) {
        this.school_class = school_class;
    }

    public int getClass_number() {
        return class_number;
    }

    public void setClass_number(int class_number) {
        this.class_number = class_number;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(int birth_month) {
        this.birth_month = birth_month;
    }

    public int getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(int birth_day) {
        this.birth_day = birth_day;
    }
}