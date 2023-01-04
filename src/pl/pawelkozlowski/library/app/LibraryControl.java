package pl.pawelkozlowski.library.app;

import pl.pawelkozlowski.library.exception.*;
import pl.pawelkozlowski.library.io.ConsolePrinter;
import pl.pawelkozlowski.library.io.DataReader;
import pl.pawelkozlowski.library.io.file.FileManager;
import pl.pawelkozlowski.library.io.file.FileManagerBuilder;
import pl.pawelkozlowski.library.model.*;
import pl.pawelkozlowski.library.model.comparator.AlphabeticalTitleComparator;

import java.util.Comparator;
import java.util.InputMismatchException;

public class LibraryControl {

private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;
    private Library library = new Library();

    public LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Zaimportowano dane z piku");
        } catch (DataImportException | InvalidDataException e) {
        printer.printLine(e.getMessage());
        printer.printLine("Zainicjonowano nową bazę.");
        library = new Library();
        }
    }
    public void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option){
                case ADD_BOOKS:
                    addBook();
                    break;
                case ADD_MAGAZINES:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case EXIT:
                    exit();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUser();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie");

            }
        } while (option != Option.EXIT);
    }

    private void printUser() {
        printer.printUsers(library.getSortedUsers(new Comparator<LibraryUser>() {
            @Override
            public int compare(LibraryUser p1, LibraryUser p2) {
                return p1.getLastName().compareToIgnoreCase(p2.getLastName());
            }
        }));

    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }


    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzono wartość która nie jest liczbą, spróbój ponownie:");
            }
        }
        return option;
        }

    private void printMagazines() {
        printer.printMagazines(library.getSortedPublications(new AlphabeticalTitleComparator()));
            
        }


    private void addMagazine() {
        try {
        Magazine magazine = dataReader.readAndCreateMagazine();
        library.addPublication(magazine);
    } catch (InputMismatchException e) {
        printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane.");
    } catch (ArrayIndexOutOfBoundsException e) {
        printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnego magazynu");
    }
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine))
                printer.printLine("Usunięto magazyn.");
            else
                printer.printLine("Brak wskazanego magazynu.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        }
    }

    private void exit() {
        try {
        fileManager.exportData(library);
        printer.printLine("Export danych do pliku zakończony powodzeniem");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Koniec programu");
        dataReader.close();
    }

    private void printBooks() {
        printer.printBooks(library.getSortedPublications(new AlphabeticalTitleComparator()));
    }

    private void addBook() {
        try {

            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnej książki");
        }
    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book))
                printer.printLine("Usunięto książkę.");
            else
                printer.printLine("Brak wskazanej książki.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        }
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję:");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());

        }
    }

    private enum Option {
        EXIT(0, "wyjście z programu"),
        ADD_BOOKS(1, "dodanie nowej książki"),
        ADD_MAGAZINES(2, "dodanie nowego magazynu"),
        PRINT_BOOKS(3, "wyświetl dostępne książki"),
        PRINT_MAGAZINES(4, "wyświetl dostępne magazyny"),
        DELETE_BOOK(5, "Usuń książkę"),
        DELETE_MAGAZINE(6, "Usuń magazyn"),
        ADD_USER(7, "Dodaj czytelnika"),
        PRINT_USERS(8, "Wyświetl czytelników");

        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }
    }
}
