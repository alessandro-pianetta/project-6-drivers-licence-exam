import javax.swing.*;
import java.io.File;
import java.util.Scanner;
import java.util.function.Consumer;


public class FileReader {
    int currentLine = 0;
    String[] studentAnswers = new String[20];

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
        String answer = input.next();
        this.studentAnswers[currentLine] = answer;
    }
    public File chooseFile() {
        JFileChooser fileChooser = new JFileChooser("C:/Users/aapiane09/IdeaProjects/DriversLicenceExam/src");
        File file = null;
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }
}
