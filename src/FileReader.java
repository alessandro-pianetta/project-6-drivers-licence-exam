import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.function.BiConsumer;


public class FileReader {
    private final Scanner input = new Scanner(System.in);
    private final String[] studentAnswers = new String[20];
    private final String[] cachedAnswers = new String[20];
    private int score = 0;

    public void readFile (File source, BiConsumer<String, Integer> callback) throws Exception {
        Scanner reader = new Scanner(source);
        int currentLine = 0;
        while (reader.hasNext()) {
            String nextLine = reader.nextLine();
            callback.accept(nextLine, currentLine);
            currentLine++;
        }
        input.close();
    }

    public void askQuestion(String line, int currentLine) {
        int count = 0;
        String[] question = line.split("\\|", 5);
        for (String segment:question) {
            String questionSegment = segment;
            if (count > 0) {
                questionSegment = "    " + questionSegment;
            }
            count++;
            System.out.println(questionSegment);
        }

        String answer = this.input.next();

        while (!(answer.equalsIgnoreCase("A") || answer.equalsIgnoreCase("B") || answer.equalsIgnoreCase("C") || answer.equalsIgnoreCase("D"))) {
            System.out.println("Sorry, your answer must be A, B, C, or D. Please try again.");
            answer = this.input.next();

            if (answer.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }

        this.studentAnswers[currentLine] = answer.toUpperCase();
    }

    public void cacheAndCheckAnswers (String answer, int currentLine) {
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
                output.println("\n" + "**************************************************************" + "\n");
                output.println("Total score: " + (this.score)+ "/20" + "\n");

                String finalResult = "Congratulations! You passed with flying colors!";
                if (this.score < 15) {
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
