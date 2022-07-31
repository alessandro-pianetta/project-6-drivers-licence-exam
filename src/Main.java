import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader();
            File questions = reader.chooseFile();
            System.out.println(questions);
        } catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
        }
    }
}