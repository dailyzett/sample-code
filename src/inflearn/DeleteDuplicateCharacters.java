package inflearn;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class DeleteDuplicateCharacters {
    public Set<Character> solution(String input){

        char[] chArr = input.toCharArray();
        Set<Character> hash = new LinkedHashSet<>();

        for (char c : chArr) {
            hash.add(c);
        }

        return hash;
    }

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        String input = in.next();

        DeleteDuplicateCharacters app = new DeleteDuplicateCharacters();
        Set<Character> hash = app.solution(input);

        for (Character character : hash) {
            System.out.print(character);
        }
    }
}
