import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        TestGiver proctor = new TestGiver();
        try {
            System.out.println("Please enter only 'A', 'B', 'C', or 'D' to answer each question. Case does not matter. Enter 'quit' to exit application.");
            proctor.readFile("assets/questions.txt", proctor::askQuestion);
            proctor.readFile("assets/DMVanswers.txt", proctor::cacheAndCheckAnswers);
            proctor.generateResults();
        } catch (FileNotFoundException err) {
            System.out.println("Error: File not found! Please confirm that you entered the correct file URI and try again.");
            System.out.println(err.getMessage());
            err.printStackTrace();
            System.exit(2);
        }  catch (ArrayIndexOutOfBoundsException err) {
            System.out.println("Error: You may have made a mistake when entering the total number of questions and/or answers. Please confirm you input the correct numbers and try again.");
            System.out.println("You entered: " + err.getMessage());
            err.printStackTrace();
            System.exit(2);
        } catch (Exception err) {
            System.out.println("Error: Something went wrong.");
            System.out.println(err.getMessage());
            err.printStackTrace();
            System.exit(2);
        }
    }
}