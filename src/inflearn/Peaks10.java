package inflearn;

import java.util.Scanner;

public class Peaks10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] peak = new int[n + 2][n + 2];

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                peak[i][j] = sc.nextInt();
            }
        }

// 테스트 출력
//        for(int[] y : peak){
//            for(int z : y){
//                System.out.print(z + " ");
//            }
//            System.out.println();
//        }

        Peaks10 app = new Peaks10();
        System.out.println(app.solution(n, peak));
    }

    private int solution(int n, int[][] peak) {
        int answer = 0;

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (peak[i][j] > peak[i - 1][j] &&
                        peak[i][j] > peak[i][j - 1] &&
                        peak[i][j] > peak[i + 1][j] &&
                        peak[i][j] > peak[i][j + 1]) {
                    answer++;
                }
            }
        }

        return answer;
    }
}
