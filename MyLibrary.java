// Authors: Elliott Cepin & Aarush Parvataneni
// Filename: MyLibrary.java
// Description: User interface for library

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MyLibrary {

  private static ArrayList<Book> books = new ArrayList<Book>();

  // This is the main method that runs the program
  public static void main(String args[]) {
    Scanner input = new Scanner(System.in);
    String command, title, author;
    String output;
    System.out.println("Welcome to your own personal library!");
    System.out.println("Currently your library is empty");
    while (true) {
      System.out.println("Please enter your command (Enter 'help' for the list of allowed commands)");
      command = input.nextLine();
      switch (command) {
        case "exit": // exits the program with the return statement
          input.close();
          return;
        case "addBook":
          System.out.print("Title:");
          title = input.nextLine();
          System.out.print("Author:");
          author = input.nextLine();
          output = addBook(title, author);
          System.out.println(output);
          break;
        case "addBooks":
          output = addBooks(input);
          System.out.println(output);
          break;
        case "search":
          output = search(input);
          System.out.println(output);          
          break;
        case "setToRead":
          output = setToRead(input);
          System.out.println(output);
          break;
        case "rate":
          output = rate(input);
          System.out.println(output);
          break;
        case "getBooks":
          output = getBooks(input);
          System.out.println(output);
          break;
        case "suggestRead":
          output = suggestRead();
          System.out.println(output);
          break;
        case "help":
          printHelp();
          break;
        default: // When we get an invalid command
          System.out.println("Please input a valid command.");
          break;
      }
    }

  }

  // This method prints out all the valid commands and what they do when the User
  // asks for it
  private static void printHelp() {
    System.out.println();
    System.out.println("To add a single book, please enter the command 'addBook'");
    System.out.println("To add multiple books, please enter the command 'addBooks'");
    System.out.println("To search for a book, please enter the command 'search'");
    System.out.println("To update the status of a book you've read, please enter the command 'setToRead'");
    System.out.println("To rate a book, please enter the command 'rate'");
    System.out.println("To get a sorted list of all the books in the library, please enter the command 'getBooks'");
    System.out.println(
        "If you are feeling lucky and want us to suggest an unread book, please enter the command 'suggestRead'");
    System.out.println("To leave the library, please enter the command 'exit'");
  }

  // This method allows the user to set whether they want to search book by Title,
  // Author, or Rating
  // and then asks for the appropriate information and retrieves all books that
  // match the condition.
  // @params - Scanner input: the user input stream
  private static String search(Scanner input) {
    String method;
    while (true) {
      System.out.print("Method of Searching(Please type TITLE , AUTHOR or RATING):");
      method = input.nextLine();
      // if valid input is given, the loop will return after searching for the books
      if (method.equals("TITLE")) {
        // true argument here infers we search by title
        return LibraryLogic.searchBooks(new ArrayList<Book>(books), input.nextLine(), true);
      } else if (method.equals("AUTHOR")) {
        // false argument here infers we search by author
        return LibraryLogic.searchBooks(new ArrayList<Book>(books), input.nextLine(), false);
      } else if (method.equals("RATING")) {
        // select the rating the user wants to see and then search
        int rating = selectInt(input, "What rating would you like to select? (integer 1-5)", 1, 5);
        return LibraryLogic.searchBooksByRating(new ArrayList<Book>(books), rating);
      }
      // this is only printed out if a non-valid input is given
      System.out.println("Please input valid method");
    }
  }

  // This method asks the user for the books title and author and adds it to the
  // library
  // @params - Scanner input: the user input stream
  private static String addBook(String title, String author) {
    Book newBook = new Book(title, author);
    for (Book b : books) {
    	if (title.equals(b.getName()) && author.equals(b.getAuthor())) {
    		return "Book already exists in library!";
    	}
    }
    books.add(newBook);
    return ("Book Added!");
  }

  // This method requires the user to input both title and author to set the book
  // as read
  // if more than one copy of the same book exist in the library, both get set to
  // read
  // @params - Scanner input: the user input stream
  private static String setToRead(Scanner input) {
    System.out.println("Please enter book title:");
    String title = input.nextLine();
    System.out.println("Please enter book author:");
    String author = input.nextLine();
    return LibraryLogic.setRead(books, title, author);
  }

  private static String getBooks(Scanner input) {
	StringBuilder sb = new StringBuilder();
    if (books.size() == 0) {
      return ("There are no books in your Library");
    }
    String[] options = { "title", "author", "read", "unread" };
    String selection = selectItem(input, "How would you like the books sorted?", options);
    ArrayList<Book> sorted = LibraryLogic.getSortedBooks(new ArrayList<Book>(books), selection);
    sb.append("The books, sorted by " + selection + ":\n");
    for (int i = 0; i < sorted.size(); i++) {
      sb.append(sorted.get(i).toString() + "\n");
    }
    return sb.toString();
  }

  // Picks a random unread book and presents it to the user
  //
  // Logic is abstracted away
  private static String suggestRead() {
    Book selected = LibraryLogic.selectUnread(new ArrayList<Book>(books));

    if (selected == null) {
      return ("Congrats! You've read all the books; there are none to suggest.");
    }

    return (selected.toString());
  }

  // Takes a file name with books formatted bookTitle;bookAuthor.
  // adds each item to our running list of books
  // @params - Scanner input: the user input stream
  // @pre - scanner is not null
  private static String addBooks(Scanner input) {
    System.out.println("What file are the books in?");
    String filename = input.nextLine();
    File file;
    Scanner fromFile;
    try {
      file = new File(filename);
      fromFile = new Scanner(file);
    } catch (FileNotFoundException e){
      // would be easy to make this more descriptive.
      System.out.println("File not found");
      System.out.println("Please input valid filename");
      return addBooks(input);
    }
    ArrayList<Book> booksFromFile = LibraryLogic.getBooksFromFile(fromFile);
    books.addAll(booksFromFile);
    return (booksFromFile.size() + " books added.");
  }

  // prompts user for the desired book, clarifying if there are multiple books
  // with that name, and sets the rating for that book based on the user's request
  //
  // Although some simple control flow logic remains,
  // the process of selecting the book and getting information from the user is
  // abstracted into the LibraryLogic class
  // @params - Scanner input: the user input stream
  // @pre scanner is not null
  private static String rate(Scanner scanner) {
    Book bookToRate;
    StringBuilder sb = new StringBuilder();
    System.out.println("What book would you like to rate?");
    String chosenBook = scanner.nextLine();
    System.out.println("Who is the author of the book?");
    String chosenAuthor = scanner.nextLine();
    Book book = LibraryLogic.getBooksWithName(chosenBook,chosenAuthor, books);

    // If no book with that name exist
    if (book == null) {
      sb.append("\"" + chosenBook + "\" by " + chosenAuthor + "does not appear to exist.\n");
      sb.append("If you would like to add it, use the addBook command");
      return sb.toString();
    }

    int rating = selectInt(scanner, "What would you like to rate the book? (integer 1-5)", 1, 5);
    book.setRating(rating);
    return "Rating updated!";
  }

  // interface function for user selection of a book from a list of books
  // @params - ArrayList bookList: datastructure that contains books
  //           String query: string that will be displayed to the user
  //           Scanner scanner: user input scanner
  // @pre parameters should not be null
  private static Book selectItem(Scanner scanner, String query, ArrayList<Book> bookList){

    System.out.println(query);		
    for (int i=0; i<bookList.size(); i++){
      System.out.println(Integer.toString(i+1) + ")" + bookList.get(i).toString());
    }

    return bookList.get(selectInt(scanner, 1, bookList.size()) - 1);


  }

  // user interface function for selecting a particular string from a set of options
  // @params - String[] itemList: datastructure that contains options as strings
  //           String query: string that will be displayed to the user
  //           Scanner scanner: user input scanner
  // @pre parameters should not be null
  private static String selectItem(Scanner scanner, String query, String[] itemList){

    System.out.println(query);		
    for (int i=0; i<itemList.length; i++){
      System.out.println("\t" + (i+1) + ") " +itemList[i]);
    }

    return itemList[selectInt(scanner, 1, itemList.length)-1];


  }


  // user interface function for picking a number within a range a..b
  // @params - Scanner scanner: user input scanner
  //           String query: string that will be dispalyed to the user
  //           int a: string that will be displayed to the user
  //           int b: user input scanner
  // @pre a < b
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

  // select int with empty query
  private static int selectInt(Scanner scanner, int a, int b){
    return selectInt(scanner, "", a, b);
  }

}
