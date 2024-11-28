package controller.libraryapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Book;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BookDataFetcher {

    private static final String API_KEY = "AIzaSyCfOWbVT84KCF8MS0AL7gwN4S8XNvSFy1k";  // Replace with your Google API key
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=subject:%s&maxResults=40&startIndex=%d&key=";

    // Method to fetch books from Google Books API by category
    public static List<Book> fetchBooksFromGoogleByCategory(String category, int numberOfBooks) throws Exception {
        List<Book> books = new ArrayList<>();
        int startIndex =0;

        while (books.size() < numberOfBooks) {
            // Encode the category to handle spaces and special characters
            String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);
            String urlString = String.format(GOOGLE_BOOKS_API_URL, encodedCategory, startIndex) + API_KEY;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                Gson gson = new Gson();
                JsonObject response = gson.fromJson(reader, JsonObject.class);

                // Check total number of items available in the category
                int totalItems = response.has("totalItems") ? response.get("totalItems").getAsInt() : 0;

                if (response.has("items")) {
                    JsonArray items = response.getAsJsonArray("items");

                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        JsonObject volumeInfo = item.getAsJsonObject("volumeInfo");

                        // Extract book information as before
                        String title = volumeInfo.get("title").getAsString();
                        String author = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").get(0).getAsString() : "Unknown Author";
                        String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown Publisher";
                        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
                        String imageUrl = volumeInfo.has("imageLinks") ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : "No image";
                        int quantity = 10;

                        String bookCategory = "Other";
                        if (volumeInfo.has("categories")) {
                            JsonArray categories = volumeInfo.getAsJsonArray("categories");
                            for (int j = 0; j < categories.size(); j++) {
                                String currentCategory = categories.get(j).getAsString();
                                if (currentCategory.contains(category)) {
                                    bookCategory = currentCategory;
                                    break;
                                }
                            }
                        }

                        String isbn = "Unknown ISBN";
                        if (volumeInfo.has("industryIdentifiers")) {
                            JsonArray industryIdentifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
                            for (int k = 0; k < industryIdentifiers.size(); k++) {
                                JsonObject identifier = industryIdentifiers.get(k).getAsJsonObject();
                                if (identifier.get("type").getAsString().equals("ISBN_13")) {
                                    isbn = identifier.get("identifier").getAsString();
                                    break;
                                }
                            }
                        }

                        // Create a Book object and add it to the list
                        Book book = new Book(title, author, publisher, description, imageUrl, quantity, bookCategory, isbn);
                        books.add(book);

                        // If the number of fetched books reaches the requested amount, break the loop
                        if (books.size() >= numberOfBooks || books.size() >= totalItems) {
                            break;
                        }
                    }

                    startIndex += 40;  // Move to the next page of results

                    // If we have fetched all available books, stop the loop
                    if (books.size() >= totalItems) {
                        break;
                    }
                } else {
                    System.out.println("No 'items' field found in API response.");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error fetching data from Google Books API.");
                break;
            }
        }

        return books;
    }


    // Method to add books to the database from different categories
    public static void addBooksToDatabaseByCategory() {
        String[] categories = {
                "Fiction",               // Fiction
                "Education",// Education
                "Comics & Graphic Novels",// Comics & Graphic Novels
                "Business & Economics",
                "Health & Fitness",   // Business & Economics
                "Others"                 // Others
        };

        int targetCount = 100; // Target number of books per category

        for (String category : categories) {
            try {
                // Check the current number of books in the database for this category
                int currentCount = DatabaseUtil.getBookCountByCategory(category);

                // Calculate how many more books are needed
                if (currentCount < targetCount) {
                    int booksToFetch = targetCount - currentCount;

                    System.out.println("Category: " + category + " | Current: " + currentCount
                            + " | Fetching: " + booksToFetch + " more books.");

                    // Fetch only the required number of books
                    List<Book> books = fetchBooksFromGoogleByCategory(category, booksToFetch);

                    // Insert fetched books into the database
                    for (Book book : books) {
                        DatabaseUtil.insertBook(book);
                    }

                    System.out.println("Category: " + category + " now has " + targetCount + " books.");
                } else {
                    System.out.println("Category: " + category + " already has " + currentCount + " books.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while processing category: " + category);
            }
        }
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        addBooksToDatabaseByCategory();
    }
}
