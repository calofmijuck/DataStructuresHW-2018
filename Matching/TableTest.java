public class TableTest {
    public static void main(String[] args) {
        HashTable<MyString, AVLTree<LinkedList<Pair<Integer, Integer>>>> table = new HashTable<>(100);
        // string is given as input
        // hash
        // get the AVL tree
        // search if the string already exists
        // no : create new linked list and insert it
        // yes : get the linked list and append a new pair
        // use TreeNode's setItem method to change the linked list << ??? Is this necessary?
        //      Can I just retreive the linked list and append a new pair, so that it will change the tree itself?
        AVLTree<LinkedList<Pair<Integer, Integer>>> entry = table.get(new MyString("abcdef"));
        // entry.insert()
    }
}
