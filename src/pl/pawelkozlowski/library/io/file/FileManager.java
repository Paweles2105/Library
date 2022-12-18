package pl.pawelkozlowski.library.io.file;

import pl.pawelkozlowski.library.model.Library;

public interface FileManager {
    Library importData();
    void exportData(Library library);

}
