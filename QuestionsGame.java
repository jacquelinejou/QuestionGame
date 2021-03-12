//Jacqueline Jou
//Creates a game of 20 questions that adapts to previous games and stored information
import java.util.*;
import java.io.*;

public class QuestionsGame {
    private QuestionNode overallRoot;
    private Scanner console;

    /*
     *Creates a new 20 questions game storing only "computer"
     */
    public QuestionsGame() {
        console = new Scanner(System.in);
        this.overallRoot = new QuestionNode("computer");
    }

    /*
     *Replaces current data with new data from a file and reads it. Assume the file is legal and 
     *in standard format.
     *@param input - reads the inputted information (questions and answers)
     */
    public void read(Scanner input) {
        overallRoot = readHelper(input);
    }
    
    /*
     *Helps read the inputted information and stores it. Assumes file is lefal and in standard
     *format. 
     *@param input - reads the inputted information (questions and answers)
     *@return root - updated knowledge (questions and answers)
     */
    private QuestionNode readHelper(Scanner input) {
        String type = input.nextLine();
        String data = input.nextLine();
        QuestionNode root = new QuestionNode(data);
        if (type.equals("Q:")) {
            root.left = readHelper(input);
            root.right = readHelper(input);
        }
        return root;
    }

    /*
     *Stores current questions in a new file (Standard format) that can be used to play a later 
     *game.
     *@param output - output file to print all the questions and answers to
     */
    public void write(PrintStream output) {
        print(overallRoot, output);
    }
    
    /*
     *Prints out the current questions and answers to a new file (Standard format) to use in a 
     *later game.
     *@param root - the stored questions and answers in this game
     *@param output - output file to print all the questions and answers to
     */
    private void print(QuestionNode root, PrintStream output) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                output.println("A:");
            }
            else {
                output.println("Q:");
            }
            output.println(root.data);
            print(root.left, output);
            print(root.right, output);
        }
    }

    /*
     *Uses the current stored questions/data to complete a game. After reaching a guess, if guess
     *is correct, print that computer won. If guess is incorrect, ask user for what object they
     *were thinking of, a question to get closer to that object from the computer's guess, and
     *whether the answer to that question is yes or no. This new object and question will be stored
     *for use in future games.
     */
    public void askQuestions() {
        overallRoot = askQuestions(overallRoot);
    }
    
    /*
     *Prints out if guess is correct. If guess is incorrect, asks for name of user's object, a 
     *distinguishing question, and the answer to that question. Stores this information in this'
     *storage of information (questions and answers)
     *@param root - the stored questions and answers in this game 
     *@return - the updated stored questions and answers in this game after this round of game
     */
    private QuestionNode endGame(QuestionNode root) {
        if (yesTo("Would your object happen to be " + root.data + "?")) {
            System.out.println("Great, I got it right!");
        }
        else {
            System.out.print("What is the name of your object? ");
            String object = console.nextLine();
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String question = console.nextLine();
            boolean answer = yesTo("And what is the answer for your object?");
            QuestionNode newQuestion = new QuestionNode(question);
            if (answer) {
                newQuestion.right = root;
                newQuestion.left = new QuestionNode(object);
            }
            else {
                newQuestion.left = new QuestionNode(root.data);
                newQuestion.right = new QuestionNode(object);
            }
            root = newQuestion;
        }
        return root;
    }
    
    /*
     *Asks identifying questions to narrow down guess. After asking appropriate questions, reaches
     *a guess.
     *@param root - the stored questions and answers in this game
     *@return - the final guess
     */
    private QuestionNode askQuestions(QuestionNode root) {
        if (root.left != null) {
            if (yesTo(root.data)) {
                root.left = askQuestions(root.left);
            }
            else {
                root.right = askQuestions(root.right);
            }
        }
        else {
            root = endGame(root);
        }
        return root;
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
