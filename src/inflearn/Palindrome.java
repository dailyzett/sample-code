package inflearn;

import java.util.Scanner;

public class Palindrome {
    public Boolean solution(String input){
        int leftIndex = 0;
        int rightIndex = input.length() - 1;
        boolean yesorno = false;

        char[] charInput = input.toLowerCase().toCharArray();

        for(int i = 0; i < input.length() / 2; i++){
            if(charInput[leftIndex] == charInput[rightIndex]){
                yesorno = true;
                leftIndex++;
                rightIndex--;
            }else{
                yesorno = false;
                break;
            }
        }
        return yesorno;
    }

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        String input = in.next();

        Palindrome app = new Palindrome();
        if(app.solution(input)){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
    }
}
