import java.util.Scanner;

/*
* N개의 단어가 주어지면 각 단어를 뒤집어 출력하는 프로그램을 작성하세요.
*
* 1. String 배열 입력을 받는다
* 2. String 배열을 뒤집는 함수를 만든다.
*   2-1. StringBuilder 에서 reverse 함수를 이용해 리턴
* 3. 출력한다
* */
public class FlipWord {
    public StringBuilder solution(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.reverse();

        return sb;
    }

    public static void main(String[] args) {
        FlipWord flipWord = new FlipWord();
        Scanner in = new Scanner(System.in);

        int input1 = in.nextInt();
        String[] strArr = new String[input1];

        for(int i = 0; i < input1; i++){
            String temp = in.next();
            strArr[i] = flipWord.solution(temp).toString();
        }

        for(int i = 0; i < input1; i++){
            System.out.println(strArr[i]);
        }
    }

}

