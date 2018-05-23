public class AVLTest {
    public static void main(String[] args) {
        AVLTree<Character> tree = new AVLTree<>();
        tree.insert('M', 0, 0);
        tree.insert('N', 0, 0);
//        tree.insert('O', 0, 0);
//        tree.insert('L', 0, 0);
//        tree.insert('K', 0, 0);
//        tree.insert('S', 0, 0);
//        tree.insert('P', 0, 0);
//        tree.insert('B', 0, 0);
//        tree.insert('I', 0, 0);
//        tree.insert('A', 0, 0);
//        tree.insert('T', 0, 0);
//        tree.insert('S', 0, 0);
//        tree.insert('U', 0, 0);
//        tree.insert('Q', 0, 0);
//        tree.insert('C', 0, 0);
//        tree.insert('D', 0, 0);
//        tree.insert('F', 0, 0);
//        tree.insert('E', 0, 0);
//        tree.insert('G', 0, 0);
//        tree.insert('Z', 0, 0);
//        tree.insert('X', 0, 0);
//        tree.insert('H', 0, 0);
//        tree.insert('J', 0, 0);
//        tree.insert('V', 0, 0);
//        tree.insert('W', 0, 0);
//        tree.insert('Y', 0, 0);
//        tree.insert('R', 0, 0);

//        TreeNode<Character> tNode = tree.search('M');
//        tNode.setItem('A');
        System.out.println(tree.printSideways());
        System.out.println(tree.preorder() + ":");
    }
}
