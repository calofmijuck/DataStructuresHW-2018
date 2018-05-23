public class AVLTree<E extends Comparable<E>> implements Comparable<E> {
    private TreeNode<E> root;

    public AVLTree() {
        root = null;
    }

    public void insert(E newItem, int i, int j) {
        root = insert(root, newItem, i, j);
    }

    private TreeNode<E> insert(TreeNode<E> root, E newItem, int i, int j) {
        if(root == null) { // create new node and initialize heights for left and right subtree
            root = new TreeNode<>(newItem);
            root.setLeftHeight(0);
            root.setRightHeight(0);
        } else {
            int cmp = root.getItem().compareTo(newItem);
            if(cmp > 0) { // go left and modify height
                root.setLeft(insert(root.getLeft(), newItem, i, j));
                root.setLeftHeight(root.getLeft().getMaxHeight() + 1);
            } else if(cmp < 0) { // go right and modify height
                root.setRight(insert(root.getRight(), newItem, i, j));
                root.setRightHeight(root.getRight().getMaxHeight() + 1);
            } else { // append to the node if same item exists
                root.appendList(new Pair<>(i, j));
            }
        }
        int balanceFactor = root.getBalance();
        if(balanceFactor > 1) { // Node is Right Heavy
            if(root.getRight().getBalance() >= 0) { // right child is right heavy or balanced
                return leftRotate(root);
            } else { // right child is left heavy
                return rightLeftRotate(root);
            }
        } else if(balanceFactor < -1) { // Node if Left Heavy
            if(root.getLeft().getBalance() <= 0) { // left child is left heavy or balanced
                return rightRotate(root);
            } else { // left child is right heavy
                return leftRightRotate(root);
            }
        } else {
            return root;
        }
    }

    // root: node to be rotated left, right: right child of root
    private TreeNode<E> leftRotate(TreeNode<E> root) {
        TreeNode<E> right = root.getRight();
        root.setRight(right.getLeft());
        right.setLeft(root);
        if(root.getRight() != null) {
            root.setRightHeight(root.getRight().getMaxHeight() + 1);
        } else {
            root.setRightHeight(0);
        }
        right.setLeftHeight(root.getMaxHeight() + 1);
        return right;
    }

    // root: node to be rotated right, left: left child of root
    private TreeNode<E> rightRotate(TreeNode<E> root) {
        TreeNode<E> left = root.getLeft();
        root.setLeft(left.getRight());
        left.setRight(root);
        if(root.getLeft() != null) {
            root.setLeftHeight(root.getLeft().getMaxHeight() + 1);
        } else {
            root.setLeftHeight(0);
        }
        left.setRightHeight(root.getMaxHeight() + 1);
        return left;
    }

    // right rotate root.right and left rotate root
    private TreeNode<E> rightLeftRotate(TreeNode<E> root) {
        root.setRight(rightRotate(root.getRight()));
        root.setRightHeight(root.getRight().getMaxHeight() + 1);
        return leftRotate(root);
    }

    // left rotate root.left and right rotate root
    private TreeNode<E> leftRightRotate(TreeNode<E> root) {
        root.setLeft(leftRotate(root.getLeft()));
        root.setLeftHeight(root.getLeft().getMaxHeight() + 1);
        return rightRotate(root);
    }

    public String printSideways() {
        return printSideways(root, "");
    }

    public String printSideways(TreeNode root, String indent) {
        StringBuilder sb = new StringBuilder();
        if(root != null) {
            sb.append(printSideways(root.getRight(), indent + "    "));
            sb.append(indent + root.getItem() + "\n");
            sb.append(printSideways(root.getLeft(), indent + "    "));
        }
        return sb.toString();
    }

    public TreeNode<E> search(E item) {
        return search(root, item);
    }

    public TreeNode<E> search(TreeNode<E> root, E item) {
        if(root == null) {
            return null;
        } else{
            int cmp = root.getItem().compareTo(item);
            if(cmp == 0) {
                return root;
            } else if(cmp > 0) {
                return search(root.getLeft(), item);
            } else {
                return search(root.getRight(), item);
            }
        }
    }


    public String preorder() {
        StringBuilder res = preorder(root);
        return res.deleteCharAt(res.length() - 1).toString();
    }

    public StringBuilder preorder(TreeNode<E> root) {
        StringBuilder sb = new StringBuilder();
        if(root != null) {
            sb.append(root.getItem());
            sb.append(" ");
            sb.append(preorder(root.getLeft()));
            sb.append(preorder(root.getRight()));
        }
        return sb;
    }

    @Override
    public int compareTo(E o) {
        return 0;
    }
}
