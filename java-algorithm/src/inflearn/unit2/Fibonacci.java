package inflearn.unit2;

import java.util.ArrayList;
import java.util.Scanner;

public class Fibonacci {

    public ArrayList<Integer> solution(int num) {
        ArrayList<Integer> answer = new ArrayList<>();
        answer.add(1);
        answer.add(1);

        for (int i = 0; i < num - 2; i++) {
            answer.add(answer.get(i) + answer.get(i + 1));
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        Fibonacci app = new Fibonacci();
        for (int x : app.solution(num)) {
            System.out.print(x + " ");
        }
    }
}
