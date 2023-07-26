package inflearn.unit7;

import java.util.Scanner;

/*
10진수 N이 입력되면 2진수로 변환하여 출력하는 프로그램을 작성하세요. 단 재귀함수를 이용
해서 출력해야 합니다.
*/

public class BinaryOutput_7_2 {
    public void solution(int n) {
        if(n == 0) return;
        else{
            solution(n / 2);
            System.out.print(n % 2);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        BinaryOutput_7_2 app = new BinaryOutput_7_2();
        app.solution(n);
    }
}
