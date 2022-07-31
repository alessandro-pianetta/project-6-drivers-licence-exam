import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.function.BiConsumer;


public class TestGiver {
    private final Scanner input = new Scanner(System.in);
    private final String[] studentAnswers;
    private final String[] cachedAnswers;
    private int score = 0;

    TestGiver (int testLength) {
        this.studentAnswers = this.cachedAnswers = new String[testLength];
    }

    public void readFile (String sourceUri, BiConsumer<String, Integer> callback) throws FileNotFoundException {
        File sourceFile = new File(sourceUri);
        Scanner reader = new Scanner(sourceFile);
        int currentLine = 0;
        while (reader.hasNext()) {
            String nextLine = reader.nextLine();
            callback.accept(nextLine, currentLine);
            currentLine++;
        }
        reader.close();
    }

    public void askQuestion(String line, int currentLine) throws ArrayIndexOutOfBoundsException {
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

    public void cacheAndCheckAnswers (String answer, int currentLine) throws ArrayIndexOutOfBoundsException {
        cachedAnswers[currentLine] = answer;
        if (answer.equals(this.studentAnswers[currentLine])) {
            score++;
        }
    }

    public void generateResults() throws FileNotFoundException {
        try {
            File results = new File("results/report.txt");
            boolean createdNewFile = results.createNewFile();

            if (!createdNewFile) {
                throw new FileAlreadyExistsException("File already exists.");
            }

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


            double userPoints = this.score;
            double totalPoints = this.studentAnswers.length;
            double percentageCorrect = userPoints / totalPoints;
            output.println("Total score: " + (this.score)+ "/20 (" + new DecimalFormat("##%").format(percentageCorrect) + ")\n");
            String finalResult = "Congratulations! You passed with flying colors!";
            if (this.score < 15) {
                finalResult = "Sorry, you didn't pass. Try again soon!";
            }
            output.println(finalResult);

            output.close();
        } catch (FileAlreadyExistsException err) {
            System.out.println("Error: File already exists! Please move, rename, or delete existing file.");
            System.out.println(err.getMessage());
            err.printStackTrace();
            System.exit(2);
        } catch (IOException err) {
            System.out.println("Something went wrong when finding or creating the file.");
            System.out.println(err.getMessage());
            err.printStackTrace();
            System.exit(2);
        }
    }
}
