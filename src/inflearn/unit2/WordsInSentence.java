package inflearn.unit2;

import java.util.Scanner;


/*
 *설명
 *한 개의 문장이 주어지면 그 문장 속에서 가장 긴 단어를 출력하는 프로그램을 작성하세요.
 *문장속의 각 단어는 공백으로 구분됩니다.
 *
 *[Input] it is time to study
 *[Output] study
 *
 * 1. 받은 문자열을 띄어쓰기 단위로 쪼갠다
 * 2. 쪼갠 문자열을 String 배열에 대입
 * 3. 배열에 담긴 문자열 길이 비교해서 길이가 긴 것을 대입
 */
public class WordsInSentence {
    public String solution(String str) {
        String answer = "";
        for (String s : str.split(" ")) {
            if (answer.length() < s.length()) {
                answer = s;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        WordsInSentence wordsInSentence = new WordsInSentence();

        Scanner in = new Scanner(System.in);

        String input = in.nextLine();
        System.out.println(wordsInSentence.solution(input));
        return;
    }
}
