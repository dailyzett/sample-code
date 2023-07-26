package inflearn.unit2;

import java.util.Scanner;

public class Mentoring2_12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] stNum = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                stNum[i][j] = sc.nextInt();
            }
        }

        Mentoring2_12 app = new Mentoring2_12();
        System.out.print(app.solution(n, m, stNum));
    }

    public int solution(int numberOfStudent, int mathCount, int[][] resultOfStudent) {
        int answer = 0;
        int count = 0;

        for (int i = 1; i <= numberOfStudent; i++) {
            for (int j = 1; j <= numberOfStudent; j++) {
                count = 0;
                for (int k = 0; k < mathCount; k++) {
                    int positionI = 0;
                    int positionJ = 0;
                    for (int s = 0; s < numberOfStudent; s++) {
                        if (resultOfStudent[k][s] == i) {
                            positionI = s;
                        } else if (resultOfStudent[k][s] == j) {
                            positionJ = s;
                        }
                    }
                    if (positionI < positionJ)
                        count++;
                }
                if (count == mathCount) {
                    answer++;
                }
            }
        }
        return answer;
    }
}
