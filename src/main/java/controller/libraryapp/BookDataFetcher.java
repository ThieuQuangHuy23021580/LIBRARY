package controller.libraryapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Book;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookDataFetcher {

    private static final String API_KEY = "AIzaSyCfOWbVT84KCF8MS0AL7gwN4S8XNvSFy1k";  // Replace with your Google API key
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=subject:%s&maxResults=40&startIndex=%d&key=";

    // Method to fetch books from Google Books API by category
    public static List<Book> fetchBooksFromGoogleByCategory(String category, int numberOfBooks) throws Exception {
        List<Book> books = new ArrayList<>();
        int startIndex = 0;

        while (books.size() < numberOfBooks) {
            String urlString = String.format(GOOGLE_BOOKS_API_URL, category, startIndex) + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the API
            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                Gson gson = new Gson();
                JsonObject response = gson.fromJson(reader, JsonObject.class);

                // Check if the response contains the "items" field
                if (response.has("items")) {
                    JsonArray items = response.getAsJsonArray("items");

                    // Loop through each book in the response and map it to Book object
                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        JsonObject volumeInfo = item.getAsJsonObject("volumeInfo");

                        String title = volumeInfo.get("title").getAsString();
                        String author = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").get(0).getAsString() : "Unknown Author";
                        String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown Publisher";
                        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
                        String imageUrl = volumeInfo.has("imageLinks") ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : "No image";
                        int quantity = 10; // Default quantity

                        // Extract category (subject) information from the API response
                        String bookCategory = category;  // Set the category based on the API query
                        if (volumeInfo.has("categories")) {
                            JsonArray categories = volumeInfo.getAsJsonArray("categories");
                            if (categories != null && categories.size() > 0) {
                                bookCategory = categories.get(0).getAsString(); // Use the first category
                            }
                        }

                        // Create a Book object and add it to the list
                        Book book = new Book(title, author, publisher, description, imageUrl, quantity, bookCategory);
                        books.add(book);

                        if (books.size() >= numberOfBooks) {
                            break;
                        }
                    }

                    // Increase the startIndex for the next batch of books
                    startIndex += 40;  // maxResults is 40 per page, you can adjust this as per your need
                } else {
                    System.out.println("No 'items' field found in API response.");
                    break;  // Exit the loop if no items were found
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error fetching data from Google Books API.");
                break;  // Exit the loop if an error occurs
            }
        }

        return books;
    }

    // Method to add books to the database from different categories
    public static void addBooksToDatabaseByCategory() {
        String[] categories = {
                "fiction",               // Fiction
                "education",             // Education
                "comics+graphic+novels", // Comics & Graphic Novels
                "business+economics",    // Business & Economics
                "health+fitness",        // Health & Fitness
                "others"                 // Others
        };

        for (String category : categories) {
            try {
                // Fetch books from Google Books API by category
                List<Book> books = fetchBooksFromGoogleByCategory(category, 100); // Fetch 100 books per category
                for (Book book : books) {
                    // Insert each book into the database
                    DatabaseUtil.insertBook(book);
                }
                System.out.println("Books for category " + category + " successfully added to the database.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while fetching or adding books for category " + category);
            }
        }
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        addBooksToDatabaseByCategory();
    }
}
