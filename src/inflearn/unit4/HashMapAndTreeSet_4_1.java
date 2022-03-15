package inflearn.unit4;

import java.util.HashMap;
import java.util.Scanner;

// https://cote.inflearn.com/contest/10/problem/04-01
public class HashMapAndTreeSet_4_1 {
    private char solution(int n, String str) {
        char answer = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (char x : str.toCharArray()) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        int max = Integer.MIN_VALUE;
        for (char key : map.keySet()) {
            if (map.get(key) > max) {
                max = map.get(key);
                answer = key;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String str = sc.next();

        HashMapAndTreeSet_4_1 T = new HashMapAndTreeSet_4_1();
        System.out.println(T.solution(n, str));
    }
}
