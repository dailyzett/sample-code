import java.util.Locale;
import java.util.Scanner;

public class ProofNumber {
    public int solution(String input){
        String s = input.toLowerCase().replaceAll("[a-z]","");
        int a =  Integer.parseInt(s);
        return a;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        ProofNumber app = new ProofNumber();
        System.out.println(app.solution(input));
    }
}
