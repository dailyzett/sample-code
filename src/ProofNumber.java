import java.util.Locale;
import java.util.Scanner;

public class ProofNumber {
    public int[] solution(String input1, char input2) {
        int[] answer = new int[input1.length()];
        int p = 1000;

        for(int i = 0; i < input1.length(); i++){
            if(input1.charAt(i) == input2){
                p = 0;
            }else{
                p++;
            }
            answer[i] = p;
        }

        for(int i = input1.length() - 1; i >= 0; i--){
            if(input1.charAt(i) == input2) p = 0;
            else{
                p++;
                answer[i] = Math.min(answer[i], p);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input1 = in.next();
        char input2 = in.next().charAt(0);

        ProofNumber proofNumber = new ProofNumber();
        for(int x : proofNumber.solution(input1, input2)){
            System.out.print(x + " ");
        }
    }
}
