import javax.swing.*;
import java.io.File;
import java.util.Scanner;
import java.util.function.Consumer;


public class FileReader {
    int currentLine = 0;
    int score = 0;
    String[] studentAnswers = new String[20];
    String[] cachedAnswers = new String[20];

    public void readFile (File source, Consumer<String> callback) throws Exception {
        Scanner input = new Scanner(source);
        while (input.hasNext()) {
            String nextLine = input.nextLine();
            callback.accept(nextLine);
            this.currentLine++;
        }
        input.close();
        this.currentLine = 0;
    }

    public void askQuestion(String line) {
        int count = 0;
        Scanner input = new Scanner(System.in);
        String[] question = line.split("\\|", 5);
        for (String segment:question) {
            String questionSegment = segment;
            if (count > 0) {
                questionSegment = "    " + questionSegment;
            }
            count++;
            System.out.println(questionSegment);
        }

        // TODO ADD ERROR CHECKING

        String answer = input.next();
        this.studentAnswers[currentLine] = answer.toUpperCase();
    }

    public void checkAnswers (String answer) {
        cachedAnswers[currentLine] = answer;
        if (answer.equals(this.studentAnswers[currentLine])) {
            score++;
        }
    }

    public void generateResults() throws NullPointerException {
        File file = new File("results/user_results.txt");
        try {
            boolean createdNewFile = file.createNewFile();
            if (createdNewFile) {
                System.out.println("File created @ " + file.getCanonicalPath());

            } else {
                throw new Exception("File already exists.");
            }
        } catch (Exception err) {
            // TODO Add logic to create new file w/ name like results_1, results_2, etc. and rerun recursively?
            System.out.println("Exception: " + err.getMessage());
            err.printStackTrace();
        }
    }
}
