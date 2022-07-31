import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader();
            File questions = new File("assets/questions.txt");
            reader.readFile(questions, reader::askQuestion);
            File answers = new File("assets/DMVanswers.txt");
            reader.readFile(answers, reader::cacheAndCheckAnswers);
            reader.generateResults();
        } catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            err.printStackTrace();
        }
    }
}