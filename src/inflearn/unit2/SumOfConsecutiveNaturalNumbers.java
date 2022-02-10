package inflearn.unit2;

import java.util.Scanner;

/*
N 입력으로 양의 정수 N이 입력되면 2개 이상의 연속된 자연수의 합으로 정수 N을 표현하는 방법의 가짓수를 출력하는 프로그램을 작성하세요.
만약 N=15이면
7+8=15
4+5+6=15
1+2+3+4+5=15
*/
public class SumOfConsecutiveNaturalNumbers {
    private int solution(int n) {
        int answer = 0, sum = 0, lt = 1;
        for (int rt = 1; rt < n; rt++) {
            sum += rt;
            if (sum == n)
                answer++;
            while (sum >= n) {
                sum -= lt;
                lt++;
                if (sum == n) answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        SumOfConsecutiveNaturalNumbers app = new SumOfConsecutiveNaturalNumbers();
        System.out.println(app.solution(n));
    }
}
