package inflearn;

/*
* 전위 순회: 부모 - 왼쪽 - 오른쪽
* 중위 순회: 왼쪽 - 부모 - 오른쪽
* 후위 순회: 왼쪽 - 오른쪽 - 부모
* */

class Node{
    int data;
    Node lt, rt;
    public Node(int val){
        data = val;
        lt=rt=null;
    }
}

public class DFS_7_5 {
    Node root;
    public void DFS(Node root){
        if(root == null) return;
        else{
            DFS(root.lt);
            System.out.print(root.data + " ");
            DFS(root.rt);
        }
    }

    public static void main(String[] args) {
        DFS_7_5 tree = new DFS_7_5();
        tree.root = new Node(1);
        tree.root.lt = new Node(2);
        tree.root.rt = new Node(3);
        tree.root.lt.lt = new Node(4);
        tree.root.lt.rt = new Node(5);
        tree.root.rt.lt = new Node(6);
        tree.root.rt.rt = new Node(7);
        tree.DFS(tree.root);
    }
}

