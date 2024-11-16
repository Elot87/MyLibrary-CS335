// Authors: Elliott Cepin & Aarush Parvataneni
// Filename: Main.java
// Description: Contains the GUI View for the program.

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static HashMap<String, JPanel> panels = new HashMap<String, JPanel>(); // hashmap that stores all panels we use in this GUI
    private static JFrame f=new JFrame("My Library"); // f is the Frame that presents our GUI
    private static ArrayList<Book> books = new ArrayList<Book>();

    // sets up the panel for the main page
    private static void createMainPanel() {
    	 JPanel p = new JPanel();
    	 p.setLayout(null);
         ArrayList<String> commands = new ArrayList<String>();
         //commands.add("help");
         commands.add("addBook");
         commands.add("addBooks");
         commands.add("search");
         commands.add("getBooks");
         commands.add("suggestRead");
         commands.add("setToRead");
         commands.add("rate");

         p.setSize(400,400);  
         
         ArrayList<JButton> buttons = new ArrayList<JButton>();
         int index = 0;
         for (String s : commands){
             buttons.add(new JButton(s));
             buttons.get(index).setBounds(100 * (index % 4), 100  + (35 * (1 + (index / 4))), 95, 30);
             buttons.get(index).addActionListener(click -> {
            	changePanel(panels.get(s)); 
             });
             p.add(buttons.get(index));  
             index++;                     
         }
         panels.put("main", p);
    }

    // sets up the panel where we can addBook
    private static void createAddBookPanel() {
    	// add Book
        JPanel addBook = new JPanel();
        addBook.setLayout(null);
        JTextField title = new JTextField("Title", 25);
        JTextField author = new JTextField("Author", 25);
        JButton submit = new JButton("Submit");
        title.setBounds(0, 0, 400, 30);
        
        addBook.add(title);
        addBook.setBounds(0,0,400,400);
        author.setBounds(0,45,400,30);
        addBook.add(author);
        submit.setBounds(250, 90, 100, 30);
        addBook.add(submit);
        addBook.add(createMainPgButton());
        
        JLabel results = new JLabel("");
        JScrollPane scp = new JScrollPane(results);
        scp.setBounds(10, 150, 380, 200);
        addBook.add(scp);
        panels.put("addBook", addBook);
        
        submit.addActionListener(click -> {
            String retStr = LibraryLogic.addBook(books, title.getText(), author.getText());
            results.setText(retStr);
        });
        
    }

    // creates the panel for the addBooks command 	
    private static void createAddBooksPanel() {
    	// add Books
        JPanel addBooks = new JPanel();
        addBooks.setLayout(null);
        JTextField title = new JTextField("filename", 25);
        JButton submit = new JButton("Submit");
        title.setBounds(0, 0, 400, 30);

        addBooks.add(title);
        addBooks.setBounds(0,0,400,400);
        submit.setBounds(250, 90, 100, 30);
        addBooks.add(submit);
        addBooks.add(createMainPgButton());
        
        JTextArea results = new JTextArea("");
        results.setEditable(false);
        JScrollPane scp = new JScrollPane(results);
        scp.setBounds(10, 150, 380, 200);
        addBooks.add(scp);
        
        submit.addActionListener(click -> {
            String retStr = LibraryLogic.addBooks(books, title.getText());
            if (retStr == null) {results.setText("File not found\nPlease input valid filename");}
            else {results.setText(retStr);}
        });
        panels.put("addBooks", addBooks);
    }

    // creates the Panel for the search command
    private static void createSearchPanel() {
    	// search
        JPanel search = new JPanel();
        search.setLayout(null);
        JTextField search_title = new JTextField("Title/Author/Rating(1-5)", 25);
        JButton submit = new JButton("Submit");
        submit.setBounds(280, 100, 100, 30);
        search.add(submit);
        String[] choices = {"Title", "Author", "Rating"};
        JComboBox<String> searchChoice = new JComboBox<>(choices);
        JLabel searchBy = new JLabel("Search by:");
        searchBy.setBounds(75, 100, 75, 50);
        search_title.setBounds(75, 40, 175, 40);
        search.setBounds(0,0,400,400);
        searchChoice.setBounds(150, 100, 100, 40);
        search.add(searchChoice);
        search.add(search_title);
        search.add(searchBy);
        search.add(createMainPgButton());
        
        JTextArea results = new JTextArea("");
        results.setEditable(false);
        JScrollPane scp = new JScrollPane(results);
        scp.setBounds(10, 150, 380, 200);
        search.add(scp);
        
        submit.addActionListener(click -> {
        	String retStr;
        	if (searchChoice.getSelectedItem().equals("Title")) {
                retStr = LibraryLogic.searchBooks(books, search_title.getText(), true); // search by title and obtain the books in string form
        	} else if (searchChoice.getSelectedItem().equals("Author")) {
                retStr = LibraryLogic.searchBooks(books, search_title.getText(), false); // search by author and obtain the books in string form
        	} else {
			// check if a valid input (int between 1-5) has been inputted
        		if (search_title.getText().length() == 1 && search_title.getText().charAt(0) > '0' && search_title.getText().charAt(0) < '6') {
                    retStr = LibraryLogic.searchBooksByRating(books, Integer.parseInt(search_title.getText()));
        		} else {
        			retStr = "Please provide a rating between 1-5";
        		}
        	}
            results.setText(retStr);
        });
        
        panels.put("search", search);
    }

    // creates the panel for the setToRead command
    private static void createReadPanel() {
    	// setToRead
        JPanel setToRead = new JPanel();
        setToRead.setLayout(null);
        JTextField title = new JTextField("Title", 25);
        JTextField author = new JTextField("Author", 25);
        JButton submit = new JButton("Submit");
        title.setBounds(0, 0, 400, 30);

        setToRead.add(title);
        setToRead.setBounds(0,0,400,400);
        author.setBounds(0,45,400,30);
        setToRead.add(author);
        
        submit.setBounds(250, 90, 100, 30);
        setToRead.add(submit);
        setToRead.add(createMainPgButton());
        
        JLabel results = new JLabel("");
        JScrollPane scp = new JScrollPane(results);
        scp.setBounds(10, 150, 380, 200);
        setToRead.add(scp);
        
        submit.addActionListener(click -> {
        	String retStr = LibraryLogic.setRead(books, title.getText(), author.getText());
        	results.setText(retStr);
        });
        
        panels.put("setToRead", setToRead);
    }

    // creates the panel for the rate command
    private static void createRatePanel() {
    	// rate
        JPanel rate = new JPanel();
        rate.setLayout(null);
        JTextField title = new JTextField("Title");
        JTextField author = new JTextField("Author");
        JButton submit = new JButton("Submit");
        Integer[] ratings = {1, 2, 3, 4, 5};
        JLabel rateLab = new JLabel("Rating");
        JComboBox<Integer> rateVal = new JComboBox<>(ratings);
        rateLab.setBounds(15, 80, 50, 15);
        rateVal.setBounds(15, 100, 50, 30);
        rate.add(rateVal);       
        rate.add(rateLab);
        title.setBounds(0, 40, 400, 30);
        rate.add(title);
        rate.setBounds(0,0,400,400);
        author.setBounds(0,0,400,30);
        rate.add(author);
        submit.setBounds(250, 150, 100, 30);
        rate.add(submit);
        rate.add(createMainPgButton());
        
        JLabel results = new JLabel("");
        JScrollPane scp = new JScrollPane(results);
        scp.setBounds(10, 150, 380, 200);
        rate.add(scp);
        
        submit.addActionListener(click -> {
        	Book rateBook = LibraryLogic.getBooksWithName(title.getText(), author.getText(), books);
        	if (rateBook == null) {results.setText("The Book with the given title & author doesn't exist!");}
        	else {rateBook.setRating(rateVal.getSelectedIndex() + 1); results.setText(title.getText() + " has been rated!");}
        });
        panels.put("rate", rate);
    }

    // creates the panel for getBooks
    private static void createGetBooksPanel() {
    	// get books
        JPanel getBooks = new JPanel();
        getBooks.setLayout(null);
        JButton gb_author_button = new JButton("Author");
        //JButton gb_rating_button = new JButton("Rating");
        JButton gb_title_button = new JButton("Title");
        JButton gb_read_button = new JButton("Read");
        JButton gb_unread_button = new JButton("Unread");
        JLabel instruction = new JLabel("Select a filter...");
        instruction.setBounds(0,0,200, 30);
        getBooks.setBounds(0,0,400,400);
        gb_author_button.setBounds(105, 50, 100, 40);
        gb_unread_button.setBounds(105, 100, 100, 40);
        gb_read_button.setBounds(215, 50, 100, 40);
        gb_title_button.setBounds(215, 100, 100, 40);
        getBooks.add(gb_author_button);
        getBooks.add(gb_unread_button);
        getBooks.add(gb_title_button);
        getBooks.add(gb_read_button);
        getBooks.add(instruction);
        getBooks.add(createMainPgButton());
        
        JTextArea results = new JTextArea("");
        results.setEditable(false);
        JScrollPane scp = new JScrollPane(results);
        scp.setBounds(10, 150, 380, 200);
        getBooks.add(scp);
        
        gb_author_button.addActionListener(click -> { // when user wants to sort by author
        	String retStr;
        	ArrayList<Book> sortedBooks = LibraryLogic.getSortedBooks(books, "author");
        	if (sortedBooks.size() == 0) {retStr = "Your Library contains no books!";}
        	else {
        		StringBuilder sb = new StringBuilder();
        		for (Book b : sortedBooks) {
        			sb.append(b.toString() + "\n");
        		}
        		retStr = sb.toString();
        	}
        	results.setText(retStr);
        });
        
        gb_title_button.addActionListener(click -> { // when user wants to sort by title
        	String retStr;
        	ArrayList<Book> sortedBooks = LibraryLogic.getSortedBooks(books, "title");
        	if (sortedBooks.size() == 0) {retStr = "Your Library contains no books!";}
        	else {
        		StringBuilder sb = new StringBuilder();
        		for (Book b : sortedBooks) {
        			sb.append(b.toString() + "\n");
        		}
        		retStr = sb.toString();
        	}
        	results.setText(retStr);
        });
        
        gb_read_button.addActionListener(click -> { // when user wants to sort by read
        	String retStr;
        	ArrayList<Book> sortedBooks = LibraryLogic.getSortedBooks(books, "read");
        	if (sortedBooks.size() == 0) {retStr = "You haven't read any books in your library!";}
        	else {
        		StringBuilder sb = new StringBuilder();
        		for (Book b : sortedBooks) {
        			sb.append(b.toString() + "\n");
        		}
        		retStr = sb.toString();
        	}
        	results.setText(retStr);
        });
        
        gb_unread_button.addActionListener(click -> { // when user wants to sort by unread
        	String retStr;
        	ArrayList<Book> sortedBooks = LibraryLogic.getSortedBooks(books, "unread");
        	if (sortedBooks.size() == 0) {retStr = "You have read every book in your library!";}
        	else {
        		StringBuilder sb = new StringBuilder();
        		for (Book b : sortedBooks) {
        			sb.append(b.toString() + "\n");
        		}
        		retStr = sb.toString();
        	}
        	results.setText(retStr);
        });
        
        panels.put("getBooks", getBooks);
    }

    // creates panel where user can ask for book suggestion 
    private static void createSuggestPanel() {
    	// suggestRead
        JPanel suggestRead = new JPanel();
        suggestRead.setLayout(null);
        JTextArea suggestion = new JTextArea("");
        suggestion.setEditable(false);
        suggestion.setBounds(0,0,400,60);
        suggestRead.setBounds(0,0,400,400);
        suggestRead.add(suggestion);
        JButton submit = new JButton("Get Suggestion!");
        submit.setBounds(100, 150, 200, 30);
        suggestRead.add(submit);
        suggestRead.add(createMainPgButton());
        
        submit.addActionListener(click -> {
        	Book b = LibraryLogic.selectUnread(books);
        	if (b == null) {suggestion.setText("Congratulations, you have read every book in your library!");}
        	else {suggestion.setText(b.toString());}
        });
        
        panels.put("suggestRead", suggestRead);

    }

    // method for creating the button that reroutes the user to the main page
    private static JButton createMainPgButton() {
    	JButton ret = new JButton("Return to Main");
    	ret.setBounds(250, 350, 150, 30);
    	ret.addActionListener(click -> changePanel(panels.get("main")));
    	return ret;
    }

    // combines all the above methods to create all the panels
    private static void createAndShowGUI() {
       
    	createMainPanel();  	
    	createAddBookPanel();
    	createAddBooksPanel();
        createSearchPanel();
        createReadPanel();   
        createRatePanel();
        createGetBooksPanel();
        createSuggestPanel();
                       
    }

    // changes the panel displayed when we need to
    private static void changePanel( JPanel p) {
		f.getContentPane().removeAll();
		p.setVisible(true);
		f.add(p);	
		f.revalidate(); 
		f.repaint();
		f.setVisible(true);
	}

  // main method that is run by the program
  public static void main(String[] args) {
      f.setLayout(null);
      f.setSize(500,500); 
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to terminate the program when we close the window
      createAndShowGUI();
      panels.get("main").setVisible(true);
      f.add(panels.get("main"));
      f.setVisible(true);
      
  }
}
	
	
	
	
