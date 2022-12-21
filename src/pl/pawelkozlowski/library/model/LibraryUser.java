package pl.pawelkozlowski.library.model;


import java.util.List;
import java.util.ArrayList;

public class LibraryUser extends User {

    private List<Publication> publicationHistory = new ArrayList<>();
    private List<Publication> borrowedPublications = new ArrayList<>();

    public LibraryUser(String firstName, String lastName, String pesel) {
        super(firstName, lastName, pesel);
    }

    public List<Publication> getPublicationHistory() {
        return publicationHistory;
    }

    public List<Publication> getBorrowedPublications() {
        return borrowedPublications;
    }

    public void addPublicationToHistory (Publication pub) {
        publicationHistory.add(pub);
    }

    public void borrowedPublications(Publication pub){
        borrowedPublications.add(pub);

    }

    @Override
    public String toCsv() {
        return getFirstName() + ";" + getLastName() + ";" + getPesel();
    }

    public boolean returnPublication(Publication pub) {
        boolean result = false;
        if (borrowedPublications.contains(pub)) {
            borrowedPublications.remove(pub);
            addPublicationToHistory(pub);
            result = true;

        }
        return result;
    }
}
