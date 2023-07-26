package inflearn.unit7;


/*
    자연수 N이 주어지면 1부터 N 까지의 원소를 갖는 집합의 부분집합을 모두 출력하는 프로그램
    을 작성하세요.
*/
public class Subset_7_6 {
    static int n;
    static int[] ch;

    public void DFS(int L) {
        if (L == n + 1) {
            String tmp = "";
            for(int i = 1; i <= n; i++){
                if(ch[i] == 1) tmp +=(i + " ");
            }
            if(tmp.length() > 0)
                System.out.println(tmp);
        } else {
            ch[L] = 1;
            DFS(L + 1);
            ch[L] = 0;
            DFS(L + 1);
        }
    }

    public static void main(String[] args) {
        Subset_7_6 app = new Subset_7_6();
        n = 5;
        ch = new int[n + 1];
        app.DFS(1);
    }
}
