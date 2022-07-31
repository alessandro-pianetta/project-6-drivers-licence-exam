import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader();
            File questions = reader.chooseFile();
            reader.readFile(questions, reader::askQuestion);
        } catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
        }
    }
}