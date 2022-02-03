package inflearn;

/* Two Pointer 이용해서 풀기
 * */

import java.util.ArrayList;
import java.util.Scanner;

public class SumTwoArray_3_1_answer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int firstNum = sc.nextInt();
        int[] firstArray = new int[firstNum];
        for (int i = 0; i < firstNum; i++) {
            firstArray[i] = sc.nextInt();
        }

        int secondNum = sc.nextInt();
        int[] secondArray = new int[secondNum];
        for (int i = 0; i < secondNum; i++) {
            secondArray[i] = sc.nextInt();
        }

        SumTwoArray_3_1_answer app = new SumTwoArray_3_1_answer();
        for (int x : app.solution(firstNum, secondNum, firstArray, secondArray))
            System.out.print(x + " ");
    }

    public ArrayList<Integer> solution(int firstNum, int secondNum, int[] firstArray, int[] secondArray) {
        ArrayList<Integer> answer = new ArrayList<>();

        int tmp1 = 0;
        int tmp2 = 0;

        while (tmp1 < firstNum && tmp2 < secondNum) {
            if (firstArray[tmp1] < secondArray[tmp2]) {
                answer.add(firstArray[tmp1++]);
            } else
                answer.add(secondArray[tmp2++]);
        }

        while (tmp1 < firstNum)
            answer.add(firstArray[tmp1++]);

        while (tmp2 < secondNum) {
            answer.add(secondArray[tmp2++]);
        }

        return answer;
    }

}
