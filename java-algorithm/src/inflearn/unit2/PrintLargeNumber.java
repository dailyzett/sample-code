package inflearn.unit2;

import java.util.ArrayList;
import java.util.Scanner;


/* 요구사항
* 1. 첫 줄에 자연수 N 입력
* 2. 그 다음 줄에 N개의 정수 입력
* 3. 입력받은 배열을 바로 뒤 순서 배열과 비교
* 4. 비교 후 조건에 맞게 배열에 추가
* */
public class PrintLargeNumber {

    public ArrayList<Integer> solution(int input, int[] arr){
        ArrayList<Integer> answer = new ArrayList<>();
        answer.add(arr[0]);

        // (3)
        for(int i = 1; i < arr.length; i++){

            // (4)
            if(arr[i] > arr[i-1])
                answer.add(arr[i]);
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // (1)
        int inputInt = sc.nextInt();

        // (2)
        int[] intArr = new int[inputInt];
        for(int i = 0; i < inputInt; i++){
            intArr[i] = sc.nextInt();
        }

        PrintLargeNumber app = new PrintLargeNumber();
        for(int x : app.solution(inputInt, intArr))
            System.out.print(x + " ");
    }
}
