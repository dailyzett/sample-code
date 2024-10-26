package inflearn.unit2;

import java.util.Scanner;

public class StringCompression {
    public String solution(String str) {
        str = str + " ";
        String answer = "";
        int count = 1;

        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) != str.charAt(i + 1)) {
                answer += str.charAt(i);
                if (count > 1) {
                    answer += String.valueOf(count);
                }
                count = 1;
            } else {
                count++;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        StringCompression app = new StringCompression();
        System.out.println(app.solution(input));
    }
}
