// Authors: Elliott Cepin & Aarush Parvataneni
// Filename: LibraryLogic.java
// Description: static class which implements the logic used by the interface.
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;



public class LibraryLogic {
	// Author: Aarush 
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

/*	
	// to be changed to Comparator class
	public static ArrayList<Book> sortByTitle(ArrayList<Book> books){
		ArrayList<Book> sortedBooks = new ArrayList<Book>();
	
		// *Here, I am using a public "books" variable that may or may not exist, but there has got to be some name for the list of books
		for (int i=0; i<books.size(); i++){
			if (i == 0){
				sortedBooks.add(books.get(i));
				continue;
			}
	
			for (int j=0; j<sortedBooks.size(); j++){
				if (sortedBooks.get(j).getName().compareTo(books.get(i).getName()) > 0){
					sortedBooks.add(j, books.get(i));
					break;
				}
			}
	
			if (i == sortedBooks.size()) sortedBooks.add(books.get(i));
		}
	
		return sortedBooks;
	}
*/

	static ArrayList<Book> sortByTitle(ArrayList<Book> books){
		ArrayList<Book> sortedBooks = new ArrayList<Book>(books);
		Collections.sort(sortedBooks, new SortByName());
		return sortedBooks;
	}

	/*
	public static ArrayList<Book> sortByAuthor(ArrayList<Book> books){
		ArrayList<Book> sortedBooks = new ArrayList<Book>();
	
		// *same stipulation as prior
		for (int i=0; i<books.size(); i++){
			if (i == 0){
				sortedBooks.add(books.get(i));
				continue;
			}
		
			for (int j=0; j<sortedBooks.size(); j++){
				if (sortedBooks.get(j).getAuthor().compareTo(books.get(i).getAuthor()) > 0){
					sortedBooks.add(j, books.get(i));
					break;
					
				}
			}
			
			// if nothing was added, add the next book. I do not know if this is a necessary step.
			if (i == sortedBooks.size()) sortedBooks.add(books.get(i));
		}
		
		return sortedBooks;
	}
*/
	static ArrayList<Book> sortByAuthor(ArrayList<Book> books){
		ArrayList<Book> sortedBooks = new ArrayList<Book>(books);
		Collections.sort(sortedBooks, new SortByAuthor());
		return sortedBooks;
	}

	/*
	public static ArrayList<Book> sortByRead(ArrayList<Book> books, boolean readFlag){
		ArrayList<Book> sortedBooks = new ArrayList<Book>();
		
		// *again, same stipulation
		for (int i=0; i<books.size(); i++){
			if (i == 0){
				sortedBooks.add(books.get(i));
			}
	
			for (int j=0; j<sortedBooks.size(); j++){
				if (sortedBooks.get(j).getRead() == readFlag){
					sortedBooks.add(j, books.get(i));
					break;
				}
			}
	
			if (sortedBooks.size() == i) sortedBooks.add(books.get(i));
		}
		
		return sortedBooks;
	
	}
	
	

	public static ArrayList<Book> sortByRead(ArrayList<Book> books, boolean readFlag){
		ArrayList<Book> sortedBooks = new ArrayList<Book>(books);
		if (readFlag) Collections.sort(sortedBooks, new SortByRead());
		else Collections.sort(sortedBooks, new SortByUnread());
		return sortedBooks;

	}
	*/
	
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
	



}
