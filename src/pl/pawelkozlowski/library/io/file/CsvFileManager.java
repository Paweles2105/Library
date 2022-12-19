package pl.pawelkozlowski.library.io.file;

import pl.pawelkozlowski.library.exception.DataExportException;
import pl.pawelkozlowski.library.exception.DataImportException;
import pl.pawelkozlowski.library.model.Library;
import pl.pawelkozlowski.library.model.Publication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileManager implements FileManager {
    private static final String FILE_NAME = "Library.csv";

    @Override
    public Library importData() {
        throw new DataImportException("not implemented");
    }

    @Override
    public void exportData(Library library) {
        Publication[] publications = library.getPublications();
        try (var fileWriter = new FileWriter(FILE_NAME);
             var bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Publication publication : publications) {
                bufferedWriter.write(publication.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + FILE_NAME);
        }
    }
}
