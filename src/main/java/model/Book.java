package model;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String imageUrl;
    private int quantity;
    private String category;
    private String isbn;

    public Book(String title, String author, String publisher, String description, String imageUrl, int quantity, String category, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
        this.isbn = isbn;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public int getQuantity() { return quantity; }
    public String getCategory() {return category;}
    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
