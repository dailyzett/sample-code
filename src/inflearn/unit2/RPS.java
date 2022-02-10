package inflearn.unit2;

import java.util.Scanner;

/*가위바위보
 * */
public class RPS {

    public char[] solution(int num, int[] aInfo, int[] bInfo) {
        char[] answer = new char[num];

        for (int i = 0; i < num; i++) {
            if (Math.abs(aInfo[i] - bInfo[i]) == 1) {
                if (aInfo[i] > bInfo[i]) {
                    answer[i] = 'A';
                } else if (aInfo[i] < bInfo[i]) {
                    answer[i] = 'B';
                }
            } else if (aInfo[i] - bInfo[i] == 0) {
                answer[i] = 'D';
            } else {
                if (aInfo[i] > bInfo[i]) {
                    answer[i] = 'B';
                } else if (aInfo[i] < bInfo[i]) {
                    answer[i] = 'A';
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();
        int[] aInfo = new int[num];
        int[] bInfo = new int[num];

        for (int i = 0; i < aInfo.length; i++) {
            aInfo[i] = sc.nextInt();
        }

        for (int i = 0; i < bInfo.length; i++) {
            bInfo[i] = sc.nextInt();
        }

        RPS app = new RPS();
        for (char x : app.solution(num, aInfo, bInfo)) {
            System.out.println(x);
        }
    }
}
