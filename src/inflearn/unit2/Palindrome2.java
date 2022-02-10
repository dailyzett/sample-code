package inflearn.unit2;/*
* 8. 팰린드롬(replaceAll 정규식 이용)
* */

import java.util.Scanner;

public class Palindrome2 {
    public boolean solution(String input){
        String str = input.toLowerCase().replaceAll("[^a-z]", "");
        String tmp = new StringBuilder(str).reverse().toString();
        return str.equals(tmp);
    }

    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        String input = in.nextLine();

        Palindrome2 app = new Palindrome2();
        if(app.solution(input)){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
    }
}
