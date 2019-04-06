package alg;

public class Node {
    boolean deleted;
    int value;
    int height;
    int deletedBelow;
    Node left;
    Node right;

    public Node(int value, int height) {
        this.deleted = false;
        this.value = value;
        this.height = height;
        this.deletedBelow = 0;
        this.left = null;
        this.right = null;
    }
}
