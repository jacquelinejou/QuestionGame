//Jacqueline Jou
//Stores the answer to yes/no questions asked in 20 questions
public class QuestionNode {
    public String data;
    public QuestionNode left;
    public QuestionNode right;


    public QuestionNode(String data) {
        this(data, null, null);
    }

    public QuestionNode(String data, QuestionNode yes, QuestionNode no) {
        this.data = data;
        this.left = yes;
        this.right = no;
    }
}
