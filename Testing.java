import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class Testing {
	@Test
	public void addBooksTest() {
		ArrayList<Book> bookList = new ArrayList<Book>();
		String filename = "books2.txt";
		String fakeFilename = "oogabooga.txt";
		
		assertEquals(null, LibraryLogic.addBooks(bookList, fakeFilename));
		assertEquals("5 books added.", LibraryLogic.addBooks(bookList, filename));
	}
	
	@Test
	public void addBookTest() {
		ArrayList<Book> bookList = new ArrayList<Book>();
		String author = "C.S. Lewis";
		String title = "Letters to Malcom";
		
		assertEquals("Book Added!", LibraryLogic.addBook(bookList, title, author));
		assertEquals("Book already exists in library!", LibraryLogic.addBook(bookList, title, author));
		
	}
	
	@Test
	public void searchBooksTest() {
		ArrayList<Book> bookList = new ArrayList<Book>();
		String author = "C.S. Lewis";
		String title = "Letters to Malcom";
		String fakeTitle = "A Guide to Prospecting";
		String fakeAuthor = "Old Man Jenkens";
		Book asBook = new Book(author, title);
		
		LibraryLogic.addBook(bookList, title, author);
		
		assertEquals("There were no books in your library with the given condition", LibraryLogic.searchBooks(bookList, fakeAuthor, false));
		assertEquals("There were no books in your library with the given condition", LibraryLogic.searchBooks(bookList, fakeTitle, true));
		assertEquals(bookList.get(0).toString() + "\n", LibraryLogic.searchBooks(bookList, title, true));
		assertEquals(bookList.get(0).toString() + "\n", LibraryLogic.searchBooks(bookList, author, false));
	}
	
	@Test
	public void setReadTest() {
		ArrayList<Book> bookList = new ArrayList<Book>();
		String author = "C.S. Lewis";
		String title = "Letters to Malcom";
		String fakeTitle = "A Guide to Prospecting";
		String fakeAuthor = "Old Man Jenkens";
		
		LibraryLogic.addBook(bookList, title, author);
		
		assertEquals("Updated!", LibraryLogic.setRead(bookList, title, author));
		assertEquals("There were no books in your library with the given title and author", LibraryLogic.setRead(bookList, fakeTitle, fakeAuthor));
	}
	
	@Test
	public void sortingMethodsTest() {
		String author = "C.S. Lewis";
		String title = "Letters to Malcom";
		String fakeTitle = "A Guide to Prospecting";
		String fakeAuthor = "Old Man Jenkens";
		Book asBook = new Book(author, title);
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		ArrayList<Book> sortedByTitle = new ArrayList<Book>();
		ArrayList<Book> sortedByAuthor = new ArrayList<Book>();
		ArrayList<Book> sortedByRating = sortedByAuthor;   // alias

		
		// set up sorted by Title
		LibraryLogic.addBook(sortedByTitle, fakeTitle, fakeAuthor);
		LibraryLogic.addBook(sortedByTitle, title, author);
		
		// set up sorted by Author
		LibraryLogic.addBook(sortedByAuthor, title, author);
		LibraryLogic.addBook(sortedByAuthor, fakeTitle, fakeAuthor);
		
		// set up main list
		LibraryLogic.addBook(bookList, title, author);
		LibraryLogic.addBook(bookList, fakeTitle, fakeAuthor);
		
		ArrayList<Book> sorted = LibraryLogic.getSortedBooks(bookList, "title");
		
		for (int i=0; i<sorted.size(); i++) {
			assertEquals(sortedByTitle.get(i).getName(), sorted.get(i).getName());
		}
		
		sorted = LibraryLogic.getSortedBooks(bookList, "author");
		
		for (int i=0; i<sorted.size(); i++) {
			assertEquals(sortedByAuthor.get(i).getName(), sorted.get(i).getName());
		}
		
		assertEquals("Updated!", LibraryLogic.setRead(bookList, fakeTitle, fakeAuthor));
		assertEquals("There were no books in your library with the given title and author", LibraryLogic.setRead(bookList, title, fakeAuthor));
		
		sorted = LibraryLogic.getSortedBooks(bookList, "read");
		
		for (int i=0; i<sorted.size(); i++) {
			assertEquals(sortedByTitle.get(i).getName(), sorted.get(i).getName());
		}
		
		sorted = LibraryLogic.getSortedBooks(bookList, "unread");
		
		for (int i=0; i<sorted.size(); i++) {
			assertEquals(sortedByAuthor.get(i).getName(), sorted.get(i).getName());
		}
		
		
		
		
		
		
	}
	
	@Test
	public void selectUnreadTest() {
		String author = "C.S. Lewis";
		String title = "Letters to Malcom";
		String fakeTitle = "A Guide to Prospecting";
		String fakeAuthor = "Old Man Jenkens";
		Book asBook = new Book(author, title);
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		LibraryLogic.addBook(bookList, title, author);
		LibraryLogic.addBook(bookList, fakeTitle, fakeAuthor);
		
		assertEquals("Updated!", LibraryLogic.setRead(bookList, fakeTitle, fakeAuthor));
		
		assertEquals(title, LibraryLogic.selectUnread(bookList).getName());
	}
	
	@Test
	public void searchBooksByRating() {
		String author = "C.S. Lewis";
		String title = "Letters to Malcom";
		String fakeTitle = "A Guide to Prospecting";
		String fakeAuthor = "Old Man Jenkens";
		Book asBook = new Book(author, title);
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		LibraryLogic.addBook(bookList, title, author);
		LibraryLogic.addBook(bookList, fakeTitle, fakeAuthor);
		
		bookList.get(1).setRating(4);
		
		assertEquals("There were no books in your library with the given rating", LibraryLogic.searchBooksByRating(bookList, 5));
		assertEquals(bookList.get(1).toString() + "\n", LibraryLogic.searchBooksByRating(bookList, 4));
		
		
	}
	
	
	
	
}
