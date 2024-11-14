// Authors: Elliott Cepin & Aarush Parvataneni
// Filename: LibraryLogic.java
// Description: static class which implements the logic used by the interface.
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;


public class LibraryLogic {
  // This method searches for all the books that match the given condition and prints them out for the
  // user to view.
  // @params - ArrayList books: datastructure that contains all the books in the library
  //           String condition: the title or author we want to search book by
  //			 boolean title: true for if we want to search by title, false for if we want to search by author
  // @pre books must not be null
  static void searchBooks(ArrayList<Book> books, String condition, boolean title) {
    int empty = 1; // variable to indicate whether or nor this condition yielded results
    for (Book b: books) {
      // if statement basically checks for whether we are searching by title or by author
      // and then checks if the title or author match based on that. First part of the if statement
      // is for when we are searching by Title and second part for Author
      if ((title && condition.equals(b.getName())) || (!title && condition.equals(b.getAuthor()))) {
        System.out.println(b.toString());
        empty = 0;
      }
    }
    // if condition didn't yield results let the user know
    if (empty == 1) {
      System.out.println("There were no books in your library with the given condition");
    }
  }

  // This method sets the Book with the matching title and author in the Library to read
  // @params - ArrayList books: datastructure that contains all the books in the library
  //           String title: title of book we want to read
  //			 String author: author of book we want to read
  // @pre books must not be null
  static void setRead(ArrayList<Book> books, String title, String author) {
    int empty = 1; // variable to indicate whether or nor this condition yielded results
    for (Book b: books) {
      if (title.equals(b.getName()) && author.equals(b.getAuthor())) {
        b.setRead();
        empty = 0;
      }
    }
    // if condition didn't yield results let the user know
    if (empty == 1) {
      System.out.println("There were no books in your library with the given title and author");
    } else {System.out.println("Updated!");}
  }

  // This method earches for all the books that match the given rating and prints them out for the
  // user to view.
  // @params - ArrayList books: datastructure that contains all the books in the library
  //           int rating: rating of books we want to retrieve
  // @pre : books must not be null
  static void searchBooksByRating(ArrayList<Book> books, int rating) {
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

  // puts books in order by title
  // @params - ArrayList<Book> books: hold all books in library
  // @pre - books != null
  static ArrayList<Book> sortByTitle(ArrayList<Book> books){
    ArrayList<Book> sortedBooks = new ArrayList<Book>(books);
    Collections.sort(sortedBooks, new SortByName());
    return sortedBooks;
  }

  // puts books in order by authoer
  // @params - ArrayList<Book> books: hold all books in library
  // @pre - books != null
  static ArrayList<Book> sortByAuthor(ArrayList<Book> books){
    ArrayList<Book> sortedBooks = new ArrayList<Book>(books);
    Collections.sort(sortedBooks, new SortByAuthor());
    return sortedBooks;
  }

  // puts all read or unread (depending on the flag) books into a file
  // @params - ArrayList<Books> books: all books in library
  // @pre - params != null
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
    Collections.sort(sortedBooks, new SortByName()); // returns the books in the default sorted by title
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

  // returns a list of books with a particular name
  // @params - ArrayList books: datastructure that contains all the books in the library
  //           String name: we will select all books with this name
  // @pre books & name must not be null
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

  

  // user interface function for selecting a particular string from a set of options
  // @params - String input: the name of the file we will retrieve text from
  // @pre input must not be null
  public static Scanner getFile(String input){
    File file;
    Scanner fromFile;
    try {
      file = new File(input);
      fromFile = new Scanner(file);
    } catch (FileNotFoundException e){
      // would be easy to make this more descriptive.
      System.out.println("File not found");
      return null;
    }

    return fromFile;
  }

  // used to get all the books from a file formatted title;author
  // @params - String input: used to get user inputed file name
  // @pre - scanner must not be null
  public static ArrayList<Book> getBooksFromFile(String input){
	Scanner fromFile = getFile(input);
	ArrayList<Book> books = new ArrayList<Book>();
	if (fromFile == null) {
		System.out.println("File doesn't exist. Please input valid filename");
		return getBooksFromFile(scanner);
	}
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

  // function for selecting how to sort and then sorting the library of books
  // @param - ArrayList books: data structure containing all books in the library
  //          String sortingMethod: user selected sorting method, sorted by title, author or read status 
  // @pre {"title", "author", "read", "unread"}.contains(sortingMethod)
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
