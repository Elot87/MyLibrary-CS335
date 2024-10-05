// Authors: Elliott Cepin & Aarush Parvataneni
// Filename: LibraryLogic.java
// Description: static class which implements the logic used by the interface.
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;


public class LibraryLogic {
	// Author: Aarush 
	// @pre books != null
	static void searchBooks(ArrayList<Book> books, String condition, boolean title) {
		books = sortByAuthor(books);
		int empty = 1;
		for (Book b: books) {
			if ((title && condition.equals(b.getName())) || (!title && condition.equals(b.getAuthor()))) {
				System.out.println(b.toString());
				empty = 0;
			}
		}
		if (empty == 1) {
			System.out.println("There were no books in your library with the given condition");
		}
	}
	
	// Author: Aarush
	// @pre books != null
	static void setRead(ArrayList<Book> books, String title, String author) {
		int empty = 1;
		for (Book b: books) {
			if (title.equals(b.getName()) && author.equals(b.getAuthor())) {
				b.setRead();
				empty = 0;
			}
		}
		if (empty == 1) {
			System.out.print("There were no books in your library with the given title and author");
		} else {System.out.print("Upgraded!");}
	}
	
	// Author: Aarush
	// @pre books != null
	static void searchBooksByRating(ArrayList<Book> books, int rating) {
		books = sortByAuthor(books);
		int empty = 1; // variable to check if the list is empty
		for (Book b: books) {
			if (b.getRating() == rating) {
				System.out.println(b.toString());
				empty = 0;
			}
		}
		if (empty == 1) {
			System.out.print("There were no books in your library with the given condition");
		}
	}

	// @pre books != null
	static ArrayList<Book> sortByTitle(ArrayList<Book> books){
		ArrayList<Book> sortedBooks = new ArrayList<Book>(books);
		Collections.sort(sortedBooks, new SortByName());
		return sortedBooks;
	}
	// @pre books != null
	static ArrayList<Book> sortByAuthor(ArrayList<Book> books){
		ArrayList<Book> sortedBooks = new ArrayList<Book>(books);
		Collections.sort(sortedBooks, new SortByAuthor());
		return sortedBooks;
	}
	
	// Put in a couple of output messages to indicate the list is empty. Can change the message if needed
	static ArrayList<Book> sortByRead(ArrayList<Book> books, boolean readFlag){
		ArrayList<Book> sortedBooks = new ArrayList<Book>();
		
		books = sortByAuthor(books);
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getRead() == readFlag) {
				sortedBooks.add(books.get(i));
			}
		}
		if (sortedBooks.size() == 0) {
			if (readFlag) System.out.println("You haven't read any books in your library.");
			else System.out.println("You have read every book in the library");
		}
		Collection.sort(sortedBooks, new SortByName()); // returns the books in the default sorted by title
		return sortedBooks;
	
	}

	// if all books are read, returns null
	// @pre books != null
	static Book selectUnread(ArrayList<Book> books){
		// to maintain the order of the books
		ArrayList<Book> shuffledBooks = new ArrayList<Book>(books);
		Collections.shuffle(shuffledBooks);
		
		Book selected = null;
		for (int i=0; i<shuffledBooks.size(); i++){
			if (!shuffledBooks.get(i).getRead())
				selected = shuffledBooks.get(i);	
		}
			
		return selected;

	}

	// @pre books != null
	static ArrayList<Book> getBooksWithName(String name, ArrayList<Book> books){
		ArrayList<Book> booksWithSelectedName = new ArrayList<Book>();
		// Checking to see if there are multiple books with that name
		// *books variable to be replaced with whatever variable we use for the list of all books
		for (Book cur : books){
			if (cur.getName().equals(name)){
				booksWithSelectedName.add(cur);
			}
		}
		
		return booksWithSelectedName;
	
	}

	// used the Book class toString method to print out the books
	static Book selectItem(Scanner scanner, String query, ArrayList<Book> bookList){
		
		System.out.println(query);		
		for (int i=0; i<bookList.size(); i++){
			System.out.println(bookList.get(i).toString());
		}
		
		return bookList.get(selectInt(scanner, 1, bookList.size()) - 1);
			
		
	}
	
	static String selectItem(Scanner scanner, String query, String[] itemList){
		
		System.out.println(query);		
		for (int i=0; i<itemList.length; i++){
			System.out.println("\t" + (i+1) + ") " +itemList[i]);
		}
		
		return itemList[selectInt(scanner, 1, itemList.length)-1];
			
		
	}


	
	static int selectInt(Scanner scanner, String query, int a, int b){
		if (!query.equals(""))
			System.out.println(query);
		
		int number = 0;
		boolean numberSelected = false;
		String intString = "";
	
		while (!numberSelected){
			intString = scanner.nextLine();
			try {
				number = Integer.parseInt(intString);
				if (number < a || number > b){
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e){
				System.out.println("\"" + intString + "\" is not a valid input. Please enter an integer from " + a + " to " + b);
				continue;
			}
	
	
			numberSelected = true;	
		}

		return number;
	
	}

	// deleted a couple of the selectInt methods since they weren't used anywhere
	private static int selectInt(Scanner scanner, int a, int b){
		return selectInt(scanner, "", a, b);
	}

	// returns null on failure
	public static Scanner getFile(Scanner scanner){
		File file;
		try {
			file = new File(input.nextLine());
			fromFile = new Scanner(file);
		} catch (FileNotFoundException e){
			// would be easy to make this more descriptive.
			System.out.println("File not found");
			return null;
		}

		return fromFile;
	}

	public static ArrayList<Book> getBooksFromFile(Scanner scanner){
		String input = scanner.nextLine();
		Scanner fromFile = getFile(input);
		ArrayList<Book> books = new ArrayList<Book>();
		
		String cur;
		int index;
		while (fromFile.hasNextLine()){
			cur = fromFile.nextLine();
			index = cur.indexOf(';');
			if (index == -1) continue;
			books.add(new Book(cur.substring(0, index), cur.substring(index+1)));
		}
		fromFile.close();

		return books;
	}

	// @pre {"title", "author", "read", "unread"}.contains(sortingMethod) && books != null
	public static ArrayList<Book> getSortedBooks(ArrayList<Book> books, String sortingMethod){
		ArrayList<Book> sorted = new ArrayList<Book>();
		switch(sortingMethod){
			case "title":
				sorted = sortByTitle(books);
				break;
			case "author":
				sorted = sortByAuthor(books);
				break;
			case "read":
				sorted = sortByRead(books, true);
				break;
			case "unread":
				sorted = sortByRead(books, false);
				break;
			default:
				System.out.println("Error, Input Validation Failed"); 
				return null;
				// precondition broken
		}

		return sorted;

		
	}



}
