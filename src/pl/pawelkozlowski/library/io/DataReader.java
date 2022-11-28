package pl.pawelkozlowski.library.io;

import pl.pawelkozlowski.library.model.Book;
import pl.pawelkozlowski.library.model.Magazine;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);

    public Book readAndCreateBook() {
        System.out.println("Tytuł");
        String title = sc.nextLine();
        System.out.println("Autor");
        String author = sc.nextLine();
        System.out.println("Wydawnictwo");
        String publisher = sc.nextLine();
        System.out.println("ISBN:");
        String isbn = sc.nextLine();
        System.out.println("Rok wydania:");
        int releaseDate = getInt();
        System.out.println("Liczba stron");
        int pages = getInt();
        return  new Book(title, author, releaseDate, pages, publisher, isbn);
    }
    public Magazine readAndCreateMagazine() {
        System.out.println("Tytuł");
        String title = sc.nextLine();
        System.out.println("Wydawnictwo");
        String publisher = sc.nextLine();
        System.out.println("Język");
        String language = sc.nextLine();
        System.out.println("Rok:");
        int year = getInt();
        System.out.println("Miesiąc:");
        int mounth = getInt();
        System.out.println("Dzień");
        int day = getInt();
        return  new Magazine(title, publisher, language, year, mounth, day);
    }

    public int getInt() {
        int number = sc.nextInt();
        sc.nextLine();
        return number;

    }
    public void close () {
        sc.close();
    }
}
