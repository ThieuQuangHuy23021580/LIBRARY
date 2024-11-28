package controller.libraryapp;

import model.Book;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyCfOWbVT84KCF8MS0AL7gwN4S8XNvSFy1k";  // Replace with your actual Google API key

    // Method to search for a book by its query (or ISBN)
    public static Book searchBookByISBN(String isbn) {
        try {
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Deserialize the response using Gson
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);

            JsonArray items = jsonResponse.getAsJsonArray("items");

            if (items != null && items.size() > 0) {
                JsonObject bookInfo = items.get(0).getAsJsonObject().getAsJsonObject("volumeInfo");

                String title = bookInfo.has("title") ? bookInfo.get("title").getAsString() : "No title";
                String author = bookInfo.has("authors") ? bookInfo.getAsJsonArray("authors").get(0).getAsString() : "No author";
                String publisher = bookInfo.has("publisher") ? bookInfo.get("publisher").getAsString() : "No publisher";
                String description = bookInfo.has("description") ? bookInfo.get("description").getAsString() : "No description";
                String imageUrl = bookInfo.has("imageLinks") ? bookInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : "No image";
                String isbnCode = getISBNFromVolumeInfo(bookInfo); // Fetch ISBN

                // Default quantity
                int quantity = 10;

                // Extract the category (subject) information
                String category = "Unknown Category";  // Default category
                if (bookInfo.has("categories")) {
                    JsonArray categories = bookInfo.getAsJsonArray("categories");
                    if (categories != null && categories.size() > 0) {
                        category = categories.get(0).getAsString();  // Use the first category
                    }
                }

                // Return the Book object including the ISBN
                return new Book(title, author, publisher, description, imageUrl, quantity, category, isbnCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to extract ISBN from volumeInfo (if available)
    private static String getISBNFromVolumeInfo(JsonObject volumeInfo) {
        if (volumeInfo.has("industryIdentifiers")) {
            JsonArray identifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
            for (int i = 0; i < identifiers.size(); i++) {
                JsonObject identifier = identifiers.get(i).getAsJsonObject();
                if (identifier.get("type").getAsString().equals("ISBN_13")) {
                    return identifier.get("identifier").getAsString(); // Return ISBN-13
                } else if (identifier.get("type").getAsString().equals("ISBN_10")) {
                    return identifier.get("identifier").getAsString(); // Return ISBN-10
                }
            }
        }
        return "No ISBN"; // Return if no ISBN found
    }
}
