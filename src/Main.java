import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] answers = {"A", "B", "B", "B", "D", "C", "A", "C", "B", "B", "C", "D", "D", "A", "D", "C", "B", "B", "A", "D"};
        int score = 0;

        for (String correctAnswer:answers) {
            System.out.println("What's the correct answer?");
            String userAnswer = input.next().toUpperCase();
            if (userAnswer.equals(correctAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect...");
            }
        }
        System.out.println("Your final score is " + score + " points.");
    }
}