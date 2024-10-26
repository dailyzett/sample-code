package inflearn.unit3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SumTwoArray_3_1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int firstNum = sc.nextInt();
        Integer[] firstArray = new Integer[firstNum];
        for (int i = 0; i < firstNum; i++) {
            firstArray[i] = sc.nextInt();
        }

        int secondNum = sc.nextInt();
        Integer[] secondArray = new Integer[secondNum];
        for (int i = 0; i < secondNum; i++) {
            secondArray[i] = sc.nextInt();
        }

        SumTwoArray_3_1 app = new SumTwoArray_3_1();
        Integer[] answer = app.solution(firstArray, secondArray);

        for (Integer x : answer) {
            System.out.print(x + " ");
        }
    }

    private Integer[] solution(Integer[] firstArray, Integer[] secondArray) {
        List<Integer> fList = new ArrayList(Arrays.asList(firstArray));
        List<Integer> sList = new ArrayList(Arrays.asList(secondArray));

        fList.addAll(sList);
        Integer[] dest = fList.toArray(new Integer[0]);
        Arrays.sort(dest);

        return dest;
    }
}
