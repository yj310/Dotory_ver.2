package com.mirim.dotory.student;

public class StudentUser {

    private String email;           // 학교이메일
    private String password;        // 비밀번호

    private String name;            // 이름
    private Birth birth;            // 생일정보
    private ClassInfo classInfo;    // 학번
    private String callNum;         // 휴대전화 번호
    private String guardCallNum;    // 보호자 휴대전화 번호
    private Address address;        // 주소 정보
    private int room;               // 호실




    // 생성자
    public StudentUser() { }

    public StudentUser(String email, String password, String name, Birth birth, ClassInfo classInfo, String callNum, String guardCallNum, Address address, int room) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.classInfo = classInfo;
        this.callNum = callNum;
        this.guardCallNum = guardCallNum;
        this.address = address;
        this.room = room;
    }


    // getter setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Birth getBirth() {
        return birth;
    }

    public void setBirth(Birth birth) {
        this.birth = birth;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public String getCallNum() {
        return callNum;
    }

    public void setCallNum(String callNum) {
        this.callNum = callNum;
    }

    public String getGuardCallNum() {
        return guardCallNum;
    }

    public void setGuardCallNum(String guardCallNum) {
        this.guardCallNum = guardCallNum;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

}

class Birth {
    private int year;               // 생년
    private int month;              // 생월
    private int day;                // 생일



    // 생성자
    public Birth() { }

    public Birth(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    // getter setter
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

}

class ClassInfo {
    private int grade;              // 학년
    private int classroom;          // 반
    private int numer;              // 번호



    // 생성자
    public ClassInfo() { }

    public ClassInfo(int grade, int classroom, int numer) {
        this.grade = grade;
        this.classroom = classroom;
        this.numer = numer;
    }

    // getter setter
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

}

class Address {
    private String address_load;    // 도로명주소
    private String address_detail;  // 상세주소
    private String address_postal;  // 우편주소
    private String grup;            // 지방/서울/경인



    // 생성자
    public Address() { }

    public Address(String address_load, String address_detail, String address_postal, String grup) {
        this.address_load = address_load;
        this.address_detail = address_detail;
        this.address_postal = address_postal;
        this.grup = grup;
    }

    // getter setter
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

    public String getAddress_postal() {
        return address_postal;
    }

    public void setAddress_postal(String address_postal) {
        this.address_postal = address_postal;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }
}