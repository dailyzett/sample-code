package inflearn;

import java.util.Scanner;

public class TakeTheRank {
    public int[] solution(int num, int[] score) {
        int[] answer = new int[num];
        int rank = 1;

        for (int i = 0; i < num; i++) {
            rank = 1;
            for (int j = 0; j < num; j++) {
                if (score[j] > score[i]) rank++;
            }
            answer[i] = rank;
        }
        return answer;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int[] score = new int[num];

        for (int i = 0; i < num; i++) {
            score[i] = sc.nextInt();
        }

        TakeTheRank app = new TakeTheRank();
        for (int x : app.solution(num, score)) {
            System.out.print(x + " ");
        }
    }
}
