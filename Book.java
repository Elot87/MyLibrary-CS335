// Authors: Elliot & Aarush Parvataneni
// Filename: Book.java
// Description: Creates the class for the Book objects to be used in MyLibrary.

public class Book {

	private String author;
	private String bookName;
	private boolean isRead;
	private int rating;
	
	public Book(String auth, String name) {
		author = auth;
		bookName = name;
		isRead = false;
		rating = -1;
	}
	
	// rating has to be between 1-5
	public void setRating(int rate) {
		if (rate < 6 && rate > 0) {
			rating = rate;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	// rating being -1 indicates that the rating hasn't been set yet
	public int getRating() {
		return rating;
	}
	
	public void setRead() {
		isRead = true;
	}
	
	public boolean getRead() {
		return isRead;
	}
	
	public String getName() {
		return bookName;
	}
	
	public String getAuthor() {
		return author;
	}

	public String toString() {
		if (rating == -1) {
			return bookName + ": " + author + "\nRating: N/A";
		}
		return bookName + ": " + author + "\nRating: " + Integer.toString(rating);
	}
	
}

