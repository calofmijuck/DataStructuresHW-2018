import java.util.LinkedList;

public class TreeNode<E extends Comparable<E>> implements Comparable<TreeNode<E>> {
    private E item;
    private LinkedList<Pair<Integer, Integer>> list;
    private TreeNode left;
    private TreeNode right;
    private int leftHeight;
    private int rightHeight;

    public TreeNode(E newItem) {
        this.item = newItem;
        this.list = new LinkedList<>();
        this.left = null;
        this.right = null;
    }

    public TreeNode(E newItem, TreeNode left, TreeNode right) {
        this.item = newItem;
        this.list = new LinkedList<>();
        this.left = left;
        this.right = right;
    }

    public E getItem() {
        return this.item;
    }

    public void setItem(E newItem) {
        this.item = newItem;
    }

    public TreeNode getLeft() {
        return this.left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return this.right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public LinkedList<Pair<Integer, Integer>> getList() {
        return this.list;
    }

    // appends a pair to the end of the list
    public void appendList(Pair<Integer, Integer> pair) {
        this.list.add(pair);
    }

    public void setLeftHeight(int leftHeight) {
        this.leftHeight = leftHeight;
    }

    public int getLeftHeight() {
        return this.leftHeight;
    }

    public void setRightHeight(int rightHeight) {
        this.rightHeight = rightHeight;
    }

    public int getRightHeight() {
        return this.rightHeight;
    }

    @Override
    public int compareTo(TreeNode<E> tNode) {
        return this.getItem().compareTo(tNode.getItem());
    }
}