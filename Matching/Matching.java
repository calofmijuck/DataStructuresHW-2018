import java.io.*;
import java.util.*;

public class Matching {
	private static HashTable<MyString, AVLTree<MyString>> table; // table
	private static ArrayList<String> strings; // input string data
	private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				command(input);
			} catch (IOException e) {
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) throws Exception {
		if(input == null || input.length() == 0) {
			throw new Exception("Illegal Command!");
		} else {
			if(input.charAt(0) == '<') {
				hashProcess(input.substring(2));
			} else if(input.charAt(0) == '@') {
				printData(Integer.parseInt(input.substring(2)));
			} else if(input.charAt(0) == '?') {
				searchPattern(input.substring(2));
			} else {
				throw new IllegalArgumentException("Illegal Command!");
			}
		}
	}

	private static void hashProcess(String input) throws Exception { // hash input strings
		table = new HashTable<MyString, AVLTree<MyString>>(100);
		strings = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new File(input));
			int strcnt = 0; // counts the total number of strings
			while(sc.hasNext()) {
				++strcnt;
				String curr = sc.nextLine(); // current string
				strings.add(curr); // save to list
				for(int i = 1; i <= curr.length() - 5; ++i) { // hash each substring
					String subStr = curr.substring(i - 1, i + 5); // current substring
					MyString mySubStr = new MyString(subStr); // placeholder
					AVLTree<MyString> tree = table.get(mySubStr); // get the tree
					if(tree == null) { // if the slot is empty create a new tree
						tree = new AVLTree<MyString>();
					}
					tree.insert(mySubStr, strcnt, i); // insert item to the tree
					table.put(mySubStr, tree); // put tree into table
				}
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File Not Found!");
		}
	}

	private static void printData(int input) throws Exception { // preorder traversal of items in slot
		AVLTree<MyString> tree = table.at(input);
		if(tree != null) {
			bw.write(tree.preorder());
		} else {
			bw.write("EMPTY");
		}
		bw.write("\n");
		bw.flush();
	}

	private static void searchPattern(String input) throws Exception { // search pattern
		try {
			StringBuilder sb = new StringBuilder();
			MyString pattern = new MyString(input.substring(0, 6)); // the first 6 letters
			AVLTree<MyString> tree= table.get(pattern);
			TreeNode<MyString> tNode = tree.search(pattern); // may throw NullPointerException
			// tNode holds the search results (LinkedList)
			LinkedList<Pair<Integer, Integer>> list = tNode.getList(); // may throw NullPointerException

			// from this part, the given search pattern exists.
			Iterator<Pair<Integer, Integer>> it = list.iterator(); // iterate through the list
			Pair<Integer, Integer> curr; // current pair
			while(it.hasNext()) {
				try {
					curr = it.next();
					int first = curr.getFirst(), second = curr.getSecond(); // get the indices
					if(input.length() == 6) { // if the length of given search pattern is <= 6
						sb.append(curr.toString()); // occurrence found, just append
						sb.append(" ");
					} else { //check if characters after the 6 chars are equal
						boolean flag = strings.get(first - 1).substring(second + 5, second + 5 + input.length() - 6).equals(input.substring(6));
						if(flag) { // equal
							sb.append(curr.toString());
							sb.append(" ");
						} // else do nothing
					}
				} catch(StringIndexOutOfBoundsException e) { // indices may go out of bounds
					// Don't do anything
				}
			}
			if(sb.length() == 0) { // none were found
				bw.write(new Pair<Integer, Integer>(0, 0).toString());
			} else {
				bw.write(sb.deleteCharAt(sb.length() - 1).toString());
			}
		} catch (NullPointerException npe) { // throws NullPointerException when the six characters are not found
			bw.write(new Pair<Integer, Integer>(0, 0).toString());
		}
		bw.write("\n");
		bw.flush();
	}
}
