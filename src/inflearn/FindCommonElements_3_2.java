package inflearn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FindCommonElements_3_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] nElement = new int[n];
        for (int i = 0; i < n; i++) {
            nElement[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[] mElement = new int[m];
        for (int i = 0; i < m; i++) {
            mElement[i] = sc.nextInt();
        }

        Arrays.sort(nElement);
        Arrays.sort(mElement);

        FindCommonElements_3_2 app = new FindCommonElements_3_2();
        for(int x : app.solution(n, m, nElement, mElement))
            System.out.print(x + " ");
    }

    private ArrayList<Integer> solution(int n, int m, int[] nElement, int[] mElement) {
        ArrayList<Integer> answer = new ArrayList<>();

        int p1 = 0;
        int p2 = 0;

        while(p1 < n && p2 < m){
            if(nElement[p1] == mElement[p2]){
                answer.add(nElement[p1]);
                p1++;
                p2++;
            }else if(nElement[p1] < mElement[p2]){
                p1++;
            }else{
                p2++;
            }
        }

        return answer;
    }
}
