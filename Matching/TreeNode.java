// TreeNode for AVL Tree with LinkedList
public class TreeNode<E extends Comparable<E>> implements Comparable<TreeNode<E>> {
    private E item;
    private LinkedList<Pair<Integer, Integer>> list;
    private TreeNode<E> left;
    private TreeNode<E> right;
    private int leftHeight;
    private int rightHeight;

    public TreeNode(E newItem) {
        this.item = newItem;
        this.list = new LinkedList<>();
        this.left = null;
        this.right = null;
    }

    public E getItem() {
        return this.item;
    }

    public void setItem(E newItem) {
        this.item = newItem;
    }

    public TreeNode<E> getLeft() {
        return this.left;
    }

    public void setLeft(TreeNode<E> left) {
        this.left = left;
    }

    public TreeNode<E> getRight() {
        return this.right;
    }

    public void setRight(TreeNode<E> right) {
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

    public int getBalance() {
        return this.rightHeight - this.leftHeight;
    }

    // returns max(rightHeight, leftHeight)
    public int getMaxHeight() {
        return Math.max(rightHeight, leftHeight);
    }

    @Override
    public int compareTo(TreeNode<E> tNode) {
        return this.getItem().compareTo(tNode.getItem());
    }
}
