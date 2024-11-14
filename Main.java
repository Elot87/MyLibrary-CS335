import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Main {
    static private String message;
    private static void createAndShowGUI() {
        JFrame f=new JFrame("My Library");
        JPanel p = new JPanel();
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("help");
        commands.add("add book");
        commands.add("add books");
        commands.add("do thing");
        commands.add("do thing");
        commands.add("do thing");
        commands.add("do thing");
        commands.add("do thing");

        ArrayList<JButton> buttons = new ArrayList<JButton>();
        int index = 0;
        for (String s : commands){
            buttons.add(new JButton(s));
            buttons.get(index).setBounds(100 * (index % 4), 100  + (35 * (1 + (index / 4))), 95, 30);
            p.add(buttons.get(index));  
            p.setSize(400,400);  
            
            p.setVisible(true);   
            index++;
            f.setVisible(true);

            
        }
        f.setLayout(null);
        f.add(p);
        f.setSize(400,400);  
        f.setVisible(true);


        JPanel input = new JPanel();
        
        JTextField getText = new JTextField(48);
        input.add(getText);
        p.setVisible(false);
        f.add(input);
        input.setVisible(false);
        input.setBounds(0,300,400,30);

        // add Book
        JPanel addBook = new JPanel();
        JTextField title = new JTextField("Title");
        JTextField author = new JTextField("Author");
        JButton submit = new JButton("Submit");
        f.add(addBook);
        title.setBounds(0, 0, 400, 30);
        
        addBook.add(title);
        addBook.setVisible(false);
        addBook.setBounds(0,0,400,400);
        author.setBounds(0,45,400,30);
        addBook.add(author);
        submit.setBounds(250, 90, 100, 30);
        addBook.add(submit);
        
        
        
        // add Books
        JPanel addBooks = new JPanel();
        JTextField title2 = new JTextField("filename");
        JButton submit2 = new JButton("Submit");
        f.add(addBooks);
        title2.setBounds(0, 0, 400, 30);

        addBooks.add(title2);
        addBooks.setVisible(false);
        addBooks.setBounds(0,0,400,400);
        submit2.setBounds(250, 90, 100, 30);
        addBooks.add(submit2);
        
        // search
        JPanel search = new JPanel();
        JTextField search_title = new JTextField("Query");
        JButton author_button = new JButton("Author");
        JButton rating_button = new JButton("Rating");
        JButton title_button = new JButton("Title");

        f.add(search);
        search.setLayout(null);
        search.setBounds(0,0,400,400);
        author_button.setBounds(20, 50, 100, 40);
        rating_button.setBounds(150, 50, 100, 40);
        title_button.setBounds(280, 50, 100, 40);
        search_title.setBounds(0,0,400,30);
        search.add(author_button);
        search.add(rating_button);
        search.add(title_button);
        search.add(search_title);
        
        search.setVisible(false);
        
        
        
        
        
        
        // setToRead
        JPanel setToRead = new JPanel();
        JTextField setToRead_title = new JTextField("Title");
        JTextField setToRead_author = new JTextField("Author");
        JButton setToRead_submit = new JButton("Submit");
        f.add(setToRead);
        setToRead_title.setBounds(0, 0, 400, 30);

        setToRead.add(setToRead_title);
        setToRead.setVisible(false);
        setToRead.setBounds(0,0,400,400);
        setToRead_author.setBounds(0,45,400,30);
        setToRead.add(setToRead_author);
        
        setToRead_submit.setBounds(250, 90, 100, 30);
        setToRead.add(setToRead_submit);

        
        // rate
        JPanel rate = new JPanel();
        JTextField rate_title = new JTextField("Title");
        JTextField rate_author = new JTextField("Author");
        JButton rate_submit = new JButton("Submit");
        JButton rate1 = new JButton("1");
        JButton rate2 = new JButton("2");
        JButton rate3 = new JButton("3");
        JButton rate4 = new JButton("4");
        JButton rate5 = new JButton("5");
        rate.setLayout(null);
        rate1.setBounds(15, 90, 50, 30);
        rate2.setBounds(95, 90, 50, 30);
        rate3.setBounds(175, 90, 50, 30);
        rate4.setBounds(255, 90, 50, 30);
        rate5.setBounds(335, 90, 50, 30);
        rate.add(rate1);
        rate.add(rate2);
        rate.add(rate3);
        rate.add(rate4);
        rate.add(rate5);

                
        f.add(rate);
        
        
        
        

        
        rate_title.setBounds(0, 40, 400, 30);
        rate.add(rate_title);
        rate.setVisible(false);
        rate.setBounds(0,0,400,400);
        rate_author.setBounds(0,0,400,30);
        rate.add(rate_author);
        rate_submit.setBounds(250, 150, 100, 30);
        rate.add(rate_submit);
        // get books
        JPanel getBooks = new JPanel();
        JButton gb_author_button = new JButton("Author");
        //JButton gb_rating_button = new JButton("Rating");
        JButton gb_title_button = new JButton("Title");
        JButton gb_read_button = new JButton("Read");
        JButton gb_unread_button = new JButton("Unread");
        JLabel instruction = new JLabel("Select a filter...");
        instruction.setBounds(0,0,200, 30);
        

        f.add(getBooks);
        getBooks.setLayout(null);
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

        getBooks.setVisible(false);
        // suggestRead
        JPanel suggestRead = new JPanel();
        JLabel suggestion = new JLabel("BOOK NAME, BY AUTHOR");
        JButton confirmation = new JButton("ok!");
        suggestion.setBounds(0,0,400,30);
        confirmation.setBounds(250, 30, 100, 30);
        suggestRead.setBounds(0,0,400,400);
        suggestRead.setLayout(null);

        f.add(suggestRead);
        suggestRead.add(suggestion);
        suggestRead.add(confirmation);
        suggestRead.setVisible(true);

        //
        

        
        
    }
    
  public static void main(String[] args) {
    createAndShowGUI();
  }
}
