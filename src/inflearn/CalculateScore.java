package inflearn;

import java.util.Scanner;

public class CalculateScore {
    public int solution(int num, int[] score) {
        int answer = 0;
        int[] totalScore = new int[num];

        for (int i = 0; i < num; i++) {
            if (score[i] == 0) {
                totalScore[i] = 0;
            } else if (score[i] >= 1 && (i - 1) >= 0) {
                totalScore[i] = totalScore[i - 1] + 1;
            } else{
                totalScore[i] = 1;
            }
        }

        for (int x : totalScore) {
            answer += x;
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

        CalculateScore app = new CalculateScore();
        System.out.println(app.solution(num, score));
    }
}
