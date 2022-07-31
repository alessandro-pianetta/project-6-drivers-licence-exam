import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.function.Consumer;


public class FileReader {
    private int currentLine = 0;
    private double score = 0;
    private String[] studentAnswers = new String[20];
    private String[] cachedAnswers = new String[20];

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
        // TODO Combine with generate results? Any point to not check both at the same time?
        cachedAnswers[currentLine] = answer;
        if (answer.equals(this.studentAnswers[currentLine])) {
            score++;
        }
    }

    public void generateResults() throws NullPointerException {
        File results = new File("results/report.txt");
        try {
            boolean createdNewFile = results.createNewFile();
            if (createdNewFile) {
                System.out.println("File created @ " + results.getCanonicalPath());
                PrintWriter output = new PrintWriter(results);

                int i = 0;
                for (String studentAnswer:studentAnswers) {
                    String yourAnswer = "Your answer was: " + studentAnswer;
                    String cachedAnswer = "The correct answer is: " + cachedAnswers[i];
                    boolean answerIsCorrect = studentAnswer.equals(cachedAnswers[i]);
                    output.println(yourAnswer + " | " + cachedAnswer + " || " + (answerIsCorrect ? "Correct!" : "Incorrect..."));
                    i++;
                }
                int score = (int) this.score;
                output.println("\n" + "**************************************************************" + "\n");
                output.println("Total score: " + (score)+ "/20" + "\n");

                String finalResult = "Congratulations! You passed with flying colors!";
                // TODO Change to this.score <= 15
                if ((this.score / 20) <= 0.75) {
                    finalResult = "Sorry, you didn't pass. Try again soon!";
                }
                output.println(finalResult);
                output.close();
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
