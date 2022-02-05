package inflearn;

import java.util.Scanner;

/*
4. 연속 부분수열
    N개의 수로 이루어진 수열이 주어집니다.
    이 수열에서 연속부분수열의 합이 특정숫자 M이 되는 경우가 몇 번 있는지 구하는 프로그램을 작성하세요.
    만약 N=8, M=6이고 수열이 다음과 같다면
    1 2 1 3 1 1 1 2
    합이 6이 되는 연속부분수열은 {2, 1, 3}, {1, 3, 1, 1}, {3, 1, 1, 1}로 총 3가지입니다.
*/
public class ContinuousSequence3_4 {
    private int solution(int n, int m, int[] nArray) {
        int answer = 0, sum = 0, lt = 0;
        for (int rt = 0; rt < n; rt++) {
            sum += nArray[rt];
            if (sum == m)
                answer++;
            while (sum >= m) {
                sum -= nArray[lt++];
                if (sum == m)
                    answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[] nArray = new int[n];
        for (int i = 0; i < n; i++) {
            nArray[i] = sc.nextInt();
        }

        ContinuousSequence3_4 app = new ContinuousSequence3_4();
        System.out.println(app.solution(n, m, nArray));
    }
}
