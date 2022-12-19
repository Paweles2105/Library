package pl.pawelkozlowski.library.io.file;

import pl.pawelkozlowski.library.exception.NoSuchFileTypeException;
import pl.pawelkozlowski.library.io.ConsolePrinter;
import pl.pawelkozlowski.library.io.DataReader;

import java.io.File;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }
    
    public FileManager build() {
        printer.printLine("Wybierz foramt danych");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileManager();
            case CSV:
                return new CsvFileManager();
            default:
                throw new NoSuchFileTypeException("Nieobsługiwany typ danych");
        }

    }
private FileType getFileType(){
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                  result = FileType.valueOf(type);
                  typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobsługiwany typ danych, wybież ponownie.");
}
        } while (!typeOk);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}
