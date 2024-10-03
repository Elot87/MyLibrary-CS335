// This just contains the stuff I am supposed to add to MyLibrary.java

import java.util.Scanner;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class MyLibrary {
/* 
	public static void main(String args[]){
		ArrayList<Book> books = new ArrayList<Book>();
		addBooks(books);
		getBooks(books);
		getBooks(books);
		getBooks(books);
		getBooks(books);
		rate(books);
		suggestRead(books);

	}
*/

	static public void getBooks(ArrayList<Book> books){	
		String[] options = {"title", "author", "read", "unread"};
		String selection = LibraryLogic.selectItem("How would you like the books sorted?", options);
	
		ArrayList<Book> sorted = new ArrayList<Book>();
		switch(selection){
			case "title":
				sorted = LibraryLogic.sortByTitle(books);
				break;
			case "author":
				sorted = LibraryLogic.sortByAuthor(books);
				break;
			case "read":
				sorted = LibraryLogic.sortByRead(books, true);
				break;
			case "unread":
				sorted = LibraryLogic.sortByRead(books, false);
				break;
			// may need a default case to handle the imaginary case where the input validation doesn't work, just so that Java is happy :)
		}
		
		System.out.println("The books, sorted by " + selection + ":");
		String read;
		for (int i=0; i<sorted.size(); i++){
			read = (sorted.get(i).getRead()) ? "read" : "unread";
			System.out.println("\t'" + sorted.get(i).getName() + "', by " + sorted.get(i).getAuthor() + " status = " + read);
		}	
	}
	
	public static void suggestRead(ArrayList<Book> books){
		Book selected = LibraryLogic.selectUnread(books);	
		
		if (selected == null){
			System.out.println("You've read all the books; there are none to suggest.");
			return;
		}
		
		System.out.println("\t'" +selected.getName()+ "', by " + selected.getAuthor());
	}
	
	// Seperating this into logic and interface didn't make sense. 
	public static void addBooks(ArrayList<Book> books){
		Scanner fromUser = new Scanner(System.in);
		Scanner fromFile;
		System.out.println("What file are the books in?");
		File file;
		try {
			file = new File(fromUser.nextLine());
			fromFile = new Scanner(file);
		} catch (FileNotFoundException e){
			// would be easy to make this more descriptive.
			System.out.println("File not found");

			return;
		}

		

		String cur;
		int index;
		int count = 0;
		while (fromFile.hasNextLine()){
			cur = fromFile.nextLine();
			index = cur.indexOf(';');
			if (index == -1) continue;
			// *If possible, I would like to use Aarush's addBook function, if I can get my hands on it; this works for now.

			// whoever decided that *title* should go AFTER author was legitemately crazy.
			books.add(new Book(cur.substring(index+1), cur.substring(0, index)));
			count++;
		}
	
		System.out.println(count + " books added.");

	
	}
	
	public static void rate(ArrayList<Book> books){
		Scanner scanner = new Scanner(System.in);
		Book bookToRate;	
	
		System.out.println("What book would you like to rate?");
		String chosenBook = scanner.nextLine();
		
		ArrayList<Book> booksWithSelectedName = LibraryLogic.getBooksWithName(chosenBook, books);
		
		if (booksWithSelectedName.size() == 0){
			System.out.println("\"" + chosenBook + "\" does not appear to exist.");
			// ^System.out.println("If you would like to add it, use the add command");
			;
			return;
		} else if (booksWithSelectedName.size() > 1){
			System.out.println("It looks like there are multiple books with that author.");
			
			bookToRate = LibraryLogic.selectItem("Please select a book by entering the corresponding number.", booksWithSelectedName);
		} else {
			// index 0 being the only option makes this case easy :)
			bookToRate = booksWithSelectedName.get(0);
		}
	
		int rating = LibraryLogic.selectInt("What would you like to rate the book? (integer 1-5)", 1, 5);
		bookToRate.setRating(rating);
		;	
	}
	
	}