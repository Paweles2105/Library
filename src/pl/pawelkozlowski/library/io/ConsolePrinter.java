package pl.pawelkozlowski.library.io;

import pl.pawelkozlowski.library.model.Book;
import pl.pawelkozlowski.library.model.LibraryUser;
import pl.pawelkozlowski.library.model.Magazine;
import pl.pawelkozlowski.library.model.Publication;

import java.util.Collection;
import java.util.Collections;

public class ConsolePrinter {
    public void printBooks(Collection<Publication> publications) {

        long count = publications.stream()
                .filter(p -> p instanceof Book)
                .map(p -> p.toString())
                .peek(s -> printLine(s))
                .count();
        if (count == 0) {
            printLine("Brak książek w bibliotece");
        }
    }

    public void printMagazines(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(p -> p instanceof Magazine)
                .map(p -> p.toString())
                .peek(s -> printLine(s))
                .count();
        if (count == 0) {
            printLine("Brak magazynów w bibliotece");
        }
    }

    public void printUsers(Collection<LibraryUser> users) {
        users.stream()
                .map(LibraryUser::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text) {
        System.out.println(text.toUpperCase());
    }

}
