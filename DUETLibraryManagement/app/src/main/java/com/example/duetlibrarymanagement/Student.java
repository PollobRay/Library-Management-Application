package com.example.duetlibrarymanagement;

//Model class
public class Student
{
    private String id;
    private String name;
    private String dept;
    private String takenBooks;
    private String book1; //key of book
    private String book1IssueDate;
    private String book2; //key of book
    private String book2IssueDate;
    private String book3; //key of book
    private String book3IssueDate;
    private String fines;

    public Student() {
        id=" ";
        name="";
        dept="";
        takenBooks="";
        book1="";
        book1IssueDate="";
        book2="";
        book2IssueDate="";
        book3="";
        book3IssueDate="";
        fines="";
    }

    public Student(String dept,String id, String name) {
        this.id = id;
        this.name = name;
        this.dept=dept;
        takenBooks="0";
        book1="";
        book1IssueDate="";
        book2="";
        book2IssueDate="";
        book3="";
        book3IssueDate="";
        fines="0";
    }

    public Student(String book1, String book1IssueDate, String book2, String book2IssueDate, String book3, String book3IssueDate, String dept,  String fines,String id, String name, String takenBooks) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.takenBooks = takenBooks;
        this.book1 = book1;
        this.book1IssueDate = book1IssueDate;
        this.book2 = book2;
        this.book2IssueDate = book2IssueDate;
        this.book3 = book3;
        this.book3IssueDate = book3IssueDate;
        this.fines = fines;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTakenBooks() {
        return takenBooks;
    }

    public void setTakenBooks(String takenBooks) {
        this.takenBooks = takenBooks;
    }

    public String getBook1() {
        return book1;
    }

    public void setBook1(String book1) {
        this.book1 = book1;
    }

    public String getBook1IssueDate() {
        return book1IssueDate;
    }

    public void setBook1IssueDate(String book1IssueDate) {
        this.book1IssueDate = book1IssueDate;
    }

    public String getBook2() {
        return book2;
    }

    public void setBook2(String book2) {
        this.book2 = book2;
    }

    public String getBook2IssueDate() {
        return book2IssueDate;
    }

    public void setBook2IssueDate(String book2IssueDate) {
        this.book2IssueDate = book2IssueDate;
    }

    public String getBook3() {
        return book3;
    }

    public void setBook3(String book3) {
        this.book3 = book3;
    }

    public String getBook3IssueDate() {
        return book3IssueDate;
    }

    public void setBook3IssueDate(String book3IssueDate) {
        this.book3IssueDate = book3IssueDate;
    }

    public String getFines() {
        return fines;
    }

    public void setFines(String fines) {
        this.fines = fines;
    }
}

