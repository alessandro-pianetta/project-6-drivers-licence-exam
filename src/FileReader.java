import javax.swing.*;
import java.io.File;

public class FileReader {
    public File chooseFile() {
        JFileChooser fileChooser = new JFileChooser("C:/Users/aapiane09/IdeaProjects/DriversLicenceExam/src");
        File file = null;
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }
}
