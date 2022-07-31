import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.function.BiConsumer;

/*
* @author Alessandro Pianetta
* */
public class TestGiver {
    // Scanner is set as instance variable as I didn't want to create multiple instances while gathering user input for test answers
    private final Scanner input = new Scanner(System.in);

    // Array of Strings that represent stored student answers
    private final String[] studentAnswers;

    // Array of strings to cache test answers so that the file didn't need to be scanned twice
    private final String[] cachedAnswers;

    // Score instance variable as I check points while caching the answers
    private int score = 0;

    // Constructor; accepts an integer representing the # of questions and answers to initialize studentAnswers and cachedAnswers instance variables
    TestGiver (int testLength) {
        this.studentAnswers = new String[testLength];
        this.cachedAnswers = new String[testLength];
    }

    // Reads and iterates through a file
    public void readFile (String sourceUri, BiConsumer<String, Integer> callback) throws FileNotFoundException {
        // Gets URI from passed-in String and creates a new File instance
        File sourceFile = new File(sourceUri);
        // Instantiates Scanner to read file
        Scanner reader = new Scanner(sourceFile);
        // Counter for keeping track of position in arrays
        int currentLine = 0;
        while (reader.hasNext()) {
            // For each line of text in the file...
            String nextLine = reader.nextLine();
            // Run callback method on that line, passing in the currentLine #
            callback.accept(nextLine, currentLine);
            // Augment current line by 1
            currentLine++;
        }
        reader.close();
    }

    // Asks test taker the questions found in the questions file and gathers answers
    public void askQuestion(String line, int currentLine) throws ArrayIndexOutOfBoundsException {
        // Counter for formatting purposes
        int count = 0;
        // \n in .txt file was printing '\n' to the console rather than a newline, so I opted for a different formatting method.
        // Splits question into 5 array items, 1 for the question and 4 for the answers.
        String[] question = line.split("\\|", 5);
        for (String segment:question) {
            String questionSegment = segment;
            if (count > 0) {
                // If not the first item, adds indentation for legibility
                questionSegment = "    " + questionSegment;
            }
            count++;
            System.out.println(questionSegment);
        }

        // Gathers user answer
        String answer = this.input.next();
        // If user enters invalid answer, loops until the user puts in a valid one. Will also exit out when uses inputs 'quit' to console
        while (!(answer.equalsIgnoreCase("A") || answer.equalsIgnoreCase("B") || answer.equalsIgnoreCase("C") || answer.equalsIgnoreCase("D"))) {
            System.out.println("Sorry, your answer must be A, B, C, or D. Please try again.");
            answer = this.input.next();

            if (answer.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
        // Adds student answer to array
        this.studentAnswers[currentLine] = answer.toUpperCase();
    }

    // Adds answers read by readFile into the cachedAnswers array, checks studentAnswers, and tallies up correct answers
    public void cacheAndCheckAnswers (String answer, int currentLine) throws ArrayIndexOutOfBoundsException {
        this.cachedAnswers[currentLine] = answer;
        if (answer.equals(this.studentAnswers[currentLine])) {
            this.score++;
        }
    }

    // Generates results file
    public void generateResults() throws FileNotFoundException {
        try {
            // Selects and creates new file within the /results folder
            File results = new File("results/report.txt");
            boolean createdNewFile = results.createNewFile();

            // If the file already exists, will throw exception
            if (!createdNewFile) {
                throw new FileAlreadyExistsException("File already exists.");
            }

            // If file does not already exist, writes to the file the user's answer, the correct answer, and whether the user was correct or not
            PrintWriter output = new PrintWriter(results);
            int i = 0;
            for (String studentAnswer:this.studentAnswers) {
                String yourAnswer = "Your answer was: " + studentAnswer;
                String cachedAnswer = "The correct answer is: " + this.cachedAnswers[i];
                boolean answerIsCorrect = studentAnswer.equals(this.cachedAnswers[i]);
                output.println(yourAnswer + " | " + cachedAnswer + " || " + (answerIsCorrect ? "Correct!" : "Incorrect..."));
                i++;
            }
            output.println("\n" + "**************************************************************" + "\n");

            // Calculates percentage of answers correct
            double userPoints = this.score;
            double totalPoints = this.studentAnswers.length;
            double percentageCorrect = userPoints / totalPoints;

            // Prints the total score in integer and percentage formats
            output.println("Total score: " + this.score + "/20 (" + new DecimalFormat("##%").format(percentageCorrect) + ")\n");

            // Prints overall result of test. If user got 14 or fewer questions correctly, finalResult is updated to recognize that.
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
