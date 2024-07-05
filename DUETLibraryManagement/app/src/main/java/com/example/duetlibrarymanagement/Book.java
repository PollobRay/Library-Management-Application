package com.example.duetlibrarymanagement;

//model Book class
public class Book
{
    private String title;
    private String author;
    private String copies;
    private String edition;
    private String publication;
    private String category;

    public Book() {
        this.title = "";
        this.author = "";
        this.copies = "";
        this.edition = "";
        this.publication = "";
        this.category = "";
    }

    public Book(String author, String category, String copies, String edition, String publication, String title) {
        this.title = title;
        this.author = author;
        this.copies = copies;
        this.edition = edition;
        this.publication = publication;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
