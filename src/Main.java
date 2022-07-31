import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader();
            File questions = new File("assets/questions.txt");
            reader.readFile(questions, reader::askQuestion);
            File answers = new File("assets/DMVanswers.txt");
            System.out.println(Arrays.toString(reader.studentAnswers));
            reader.readFile(answers, reader::checkAnswers);
            System.out.println(reader.score);
            reader.generateResults();
        } catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            err.printStackTrace();
        }
    }
}