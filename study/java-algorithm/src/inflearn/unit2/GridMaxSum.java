package inflearn.unit2;

import java.util.Scanner;

public class GridMaxSum {
    public int solution(int size, int[][] grid) {
        int answer = 0;

        int sum1 = 0;
        int sum2 = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum1 += grid[i][j];
                sum2 += grid[j][i];
            }
            answer = Math.max(answer, sum1);
            answer = Math.max(answer, sum2);

            sum1 = 0;
            sum2 = 0;
        }

        for (int i = 0; i < size; i++) {
            sum1 += grid[i][i];
            sum2 += grid[i][size - 1 - i];
        }
        answer = Math.max(answer, (Math.max(sum1, sum2)));
        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int[][] grid = new int[num][num];

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        GridMaxSum app = new GridMaxSum();
        System.out.println(app.solution(num, grid));
    }
}
