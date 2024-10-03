// Authors: Elliott Cepin & Aarush Parvataneni
// Filename: LibraryLogic.java
// Description: static class which implements the logic used by the interface.
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;



public class LibraryLogic {
	
	// writing a custom soritng algorithm when things like Collections.sort() exist is a fools errand;
	// however, since I was not in charge of the book class, and didn't realize soon enough that this would be important
	// the book class does not implement any interfaces. Thusly, I couldn't use Collections.sort()
	// Luckily, this does allow me to easily scate around the fact that there would have been complications
	// with there being 4 required ways to sort the lists.
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
	
	// if all books are read, returns null
	public static Book selectUnread(ArrayList<Book> books){
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
	
	public static ArrayList<Book> getBooksWithName(String name, ArrayList<Book> books){
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

	public static Book selectItem(String query, ArrayList<Book> bookList){
		
		System.out.println(query);		
		for (int i=0; i<bookList.size(); i++){
			System.out.println("\t" + (i+1) + ") '" +bookList.get(i).getName() + "', by " +bookList.get(i).getName() + ".");
		}
		
		return bookList.get(selectInt(1, bookList.size()) - 1);
			
		
	}

	public static String selectItem(String query, String[] itemList){
		
		System.out.println(query);		
		for (int i=0; i<itemList.length; i++){
			System.out.println("\t" + (i+1) + ") " +itemList[i]);
		}
		
		return itemList[selectInt(1, itemList.length)-1];
			
		
	}


	
	public static int selectInt(String query, int a, int b){
		if (!query.equals(""))
			System.out.println(query);
		
		int number = 0;
		boolean numberSelected = false;
		Scanner scanner = new Scanner(System.in);
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
	
	public static int selectInt(String query, int b){
		return selectInt(query, 0, b);
	}
	public static int selectInt(int a, int b){
		return selectInt("", a, b);
	}
	
	public static int selectInt(int b){
		return selectInt("", 0, b);
	}




}