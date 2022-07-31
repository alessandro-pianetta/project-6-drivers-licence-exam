import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader();
            File questions = reader.chooseFile();
            reader.readFile(questions, reader::askQuestion);
            File answers = reader.chooseFile();
            System.out.println(Arrays.toString(reader.studentAnswers));
            reader.readFile(answers, reader::checkAnswers);
            System.out.println(reader.score);
        } catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
        }
    }
}