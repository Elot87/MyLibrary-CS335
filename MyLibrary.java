// This just contains the stuff I am supposed to add to MyLibrary.java

import java.util.Scanner;
import java.util.Collections;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class MyLibrary {
	
	// Made books a static class variable; this way we don't have to pass it on as a 
	// argument everytime
	
	private static ArrayList<Book> books = new ArrayList<Book>();

	// Author: Aarush
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		String command;
		System.out.println("Welcome to your own personal library!");
		System.out.println("Currently your library is empty");
		while (true) {
			System.out.println("Please enter your command (Enter 'help' for the list of allowed commands)");
			command = input.nextLine();
			switch(command) {
				case "exit":
					input.close();
					return;
				case "addBook":
					addBook(input);
					break;
				case "addBooks":
					addBooks(input);
					break;
				case "search":
					search(input);
					break;
				case "setToRead":
					setToRead(input);
					break;
				case "rate":
					rate(input);
					break;
				case "getBooks":
					getBooks(input);
					break;
				case "suggestRead":
					suggestRead();
					break;
				case "help":
					printHelp();
					break;
				default:
					System.out.println("Please input a valid command.");
					break;
			}
		}

	}

	private static void printHelp() {
		System.out.println();
		System.out.println("To add a single book, please enter the command 'addBook'");
		System.out.println("To add multiple books, please enter the command 'addBooks'");
		System.out.println("To search for a book, please enter the command 'search'");
		System.out.println("To update the status of a book you've read, please enter the command 'setToRead'");
		System.out.println("To rate a book, please enter the command 'rate'");
		System.out.println("To get a sorted list of all the books in the library, please enter the command 'getBooks'");
		System.out.println("If you are feeling lucky and want us to suggest an unread book, please enter the command 'suggestRead'");
		System.out.println("To leave the library, please enter the command 'exit'");
	}

	// Author: Aarush
	private static void search(Scanner input) {
		String method;
		while (true) {
			System.out.print("Method of Searching(Please type TITLE , AUTHOR or RATING):");
			method = input.nextLine();
			if (method.equals("TITLE")) {
				LibraryLogic.searchBooks(books, input.nextLine(), true);
				return;
			} else if (method.equals("AUTHOR")) {
				LibraryLogic.searchBooks(books, input.nextLine(), false);
				return;
			} 
			else if (method.equals("RATING")) {
				int rating = LibraryLogic.selectInt(input, "What rating would you like to select? (integer 1-5)", 1, 5);
				LibraryLogic.searchBooksByRating(books, rating);
				return;
			}
			
			System.out.println("Please input valid method");
		}
	}
	
	// Author: Aarush
	private static void addBook(Scanner input) {
		System.out.println("Please enter book title:");
		String title = input.nextLine();
		System.out.println("Please enter book author:");
		String author = input.nextLine();
		Book newBook = new Book(title, author);
		books.add(newBook);
		System.out.println("Book Added!");
	}
	
	// Author: Aarush
	// I required the user to input both title and author to set the book as read
	// if more than one copy of the same book exist in the library, both get set to read 
	private static void setToRead(Scanner input) {
		System.out.println("Please enter book title:");
		String title = input.nextLine();
		System.out.println("Please enter book author:");
		String author = input.nextLine();
		LibraryLogic.setRead(books, title, author);
	}
	
	// added default case
	// 
	private static void getBooks(Scanner input){	
		if (books.size() == 0) {System.out.println("There are no books in your Library");}
		String[] options = {"title", "author", "read", "unread"};
		String selection = LibraryLogic.selectItem(input, "How would you like the books sorted?", options);
	
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
			default:
				System.out.println("Error, Input Validation Failed"); 
				return;
			// may need a default case to handle the imaginary case where the input validation doesn't work, just so that Java is happy :)
		}
		if (sorted.size() == 0) {
			return;
		}
		System.out.println("The books, sorted by " + selection + ":");
		for (int i=0; i<sorted.size(); i++){
			System.out.println(sorted.get(i).toString());
		}	
	}
	
	private static void suggestRead(){
		Book selected = LibraryLogic.selectUnread(books);	
		
		if (selected == null){
			System.out.println("Congrats! You've read all the books; there are none to suggest.");
			return;
		}
		
		System.out.println(selected.toString());
	}
	
	// Put title first & then author in the constructor method. made the same change in the Book class as well
	// Scanner changes
	// Seperating this into logic and interface didn't make sense. 
	private static void addBooks(Scanner input){

		Scanner fromFile;
		System.out.println("What file are the books in?");
		File file;
		try {
			file = new File(input.nextLine());
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
			books.add(new Book(cur.substring(0, index), cur.substring(index+1)));
			count++;
		}
		fromFile.close();
		
		System.out.println(count + " books added.");

	
	}
	
	private static void rate(Scanner scanner){

		Book bookToRate;	
	
		System.out.println("What book would you like to rate?");
		String chosenBook = scanner.nextLine();
		
		ArrayList<Book> booksWithSelectedName = LibraryLogic.getBooksWithName(chosenBook, books);
		
		if (booksWithSelectedName.size() == 0){
			System.out.println("\"" + chosenBook + "\" does not appear to exist.");
			System.out.println("If you would like to add it, use the addBook command");
			;
			return;
		} else if (booksWithSelectedName.size() > 1){
			System.out.println("It looks like there are multiple books with that title.");
			
			bookToRate = LibraryLogic.selectItem(scanner, "Please select a book by entering the corresponding number.", booksWithSelectedName);
		} else {
			// index 0 being the only option makes this case easy :)
			bookToRate = booksWithSelectedName.get(0);
		}
	
		int rating = LibraryLogic.selectInt(scanner, "What would you like to rate the book? (integer 1-5)", 1, 5);
		bookToRate.setRating(rating);
	}
	
	}
