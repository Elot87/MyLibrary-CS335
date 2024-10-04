// Authors: Elliot & Aarush Parvataneni
// Filename: Book.java
// Description: Creates the class for the Book objects to be used in MyLibrary.

import java.util.Comparator;

public class Book {

	private String author;
	private String bookName;
	private boolean isRead;
	private int rating;
	
	public Book(String name, String auth) {
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
		if (isRead) { // to decide whether to display read or unread
			if (rating == -1) { // unrated
				return bookName + " by " + author + " |	Rating: N/A |	Status: Read";
			}
			return bookName + " by " + author + " |	Rating: " + Integer.toString(rating) + " |	Status: Read";
		}
		
		if (rating == -1) { // unrated
			return bookName + " by " + author + " |	Rating: N/A	|	Status: Unread";
		}
		return bookName + " by " + author + " |	Rating: " + Integer.toString(rating) + " |	Status: Unread";
		
	} 
	
}


public class SortByAuthor implements Comparator<Book>{
	public int compare(Book a, Book b){
		return a.getAuthor().compareTo(b.getAuthor());
	}
}

public class SortByName implements Comparator<Book>{
	public int compare(Book a, Book b){
		return a.getName().compareTo(b.getName());
	}
}
