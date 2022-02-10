package inflearn.unit7;

import java.util.Scanner;

/*
* N 입력으로 양의 정수 N이 입력되면 2개 이상의 연속된 자연수의 합으로 정수 N을 표현하는 방법의 가짓수를 출력하는 프로그램을 작성하세요.
* 입력 : 15
* 출력 : 3
* 7 + 8,
* 4 + 5 + 6,
* 1 + 2 + 3 + 4 + 5
* */
public class SumNativeNumber_7_12 {
    private int solution(int n){
        int answer = 0, cnt = 1;

        n--;
        while(n > 0){
            cnt++;
            n = n - cnt;
            if(n % cnt == 0) answer++;
        }

        return answer;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        SumNativeNumber_7_12 app = new SumNativeNumber_7_12();
        System.out.println(app.solution(n));
    }
}
