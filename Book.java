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
	
	// @pre rating < 6 && rating > 0
	public void setRating(int rate) {
		rating = rate;
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


class SortByAuthor implements Comparator<Book>{
	public int compare(Book a, Book b){
		return a.getAuthor().compareTo(b.getAuthor());
	}
}

class SortByName implements Comparator<Book>{
	public int compare(Book a, Book b){
		return a.getName().compareTo(b.getName());
	}
}
