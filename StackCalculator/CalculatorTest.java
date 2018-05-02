import java.io.*;
import java.util.Stack;

public class CalculatorTest {
	private static Stack<Long> numStack = new Stack<>(); // stack for numbers
	private static Stack<Character> opStack = new Stack<>(); // stack for operators
	private static String operator_list = "^~*/%+-"; // operator list

	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;
				command(input);
			} catch (Exception e) {
				System.out.println("ERROR");
			}
			numStack.clear();
			opStack.clear();
			// After a line of calculation, reset all stacks.
		}
	}

	private static void command(String input) throws Exception {
		char[] eq = input.toCharArray(); // infix expression to scan
		StringBuilder sb = new StringBuilder(); // for scanning numbers
		StringBuilder postfix = new StringBuilder(); // for storing postfix
		boolean unary_flag = true, number_flag = true, paren_flag = false; // flags for unary operator, number, parenthesis
		int paren_count = 0; // counter for parentheses
		for (int i = 0; i < eq.length; ++i) { // loop for the whole input string
			if (isNum(eq[i])) {
				if (!number_flag) { // number cannot come right now
					throw new Exception("Error!");
				}
				while (i < eq.length && isNum(eq[i])) { // get number
					sb.append(eq[i]);
					++i;
				}
				i--;
				postfix.append(sb); // append to the postfix expression
				postfix.append(" ");
				numStack.push(Long.parseLong(sb.toString())); // push the scanned number
				sb = new StringBuilder(); // reset
				number_flag = false; // number cannot come anymore
				unary_flag = false; // - cannot come after number
				paren_flag = false;
			} else if (isOp(eq[i])) {
				number_flag = true; // number can come after operand
				if (opStack.isEmpty() || opStack.peek() == '(') { // if the stack is empty, push
					if (unary_flag && eq[i] == '-') { // check for unary -
						opStack.push('~'); // push ~ instead of -
						continue;
					}
					opStack.push(eq[i]);
				} else {
					if (unary_flag && eq[i] == '-') { // if unary, change - to ~
						eq[i] = '~';
					}
					if (compare(eq[i], opStack.peek())) { // precedence: current > stack
						opStack.push(eq[i]);
					} else { // precedence: current <= stack
						// pop while: the stack has an element, precedence: current > stack,
						// both operands have same precedence and is left associative
						while (!opStack.isEmpty() && !compare(eq[i], opStack.peek()) && (isLeftAssoc(opStack.peek()) || isLeftAssoc(eq[i]))) {
							char operator = opStack.pop();
							calculate(operator);
							postfix.append(operator); // append to the postfix expression
							postfix.append(" ");
						}
						opStack.push(eq[i]); // push current operator
					}
				}
				unary_flag = true; // unary can come after operator
				paren_flag = false;
			} else if (eq[i] == '(') { // check open paren.
				++paren_count;
				opStack.push(eq[i]);
				unary_flag = true; // unary - can come after open paren.
				paren_flag = true; // ( happened
			} else if (eq[i] == ')') { // check close paren.
				--paren_count;
				// int operand_count = 0; // counts how many operands exist between (, ).
				while (!opStack.isEmpty() && !(opStack.peek() == '(')) {
					char operator = opStack.pop();
					calculate(operator);
					postfix.append(operator); // append to the postfix expression
					postfix.append(" ");
				}
				if(paren_flag) { // was the previous char ( ?
					throw new Exception("No valid expression between parenthesis");
				}
				opStack.pop(); // pop the open paren
			}
		}
		if (paren_count != 0) { // parenthesis do not match
			throw new Exception("Error! Paren Mismatch!");
		}
		while (!opStack.isEmpty()) { // pop the remaining operands left in the stack
			char operator = opStack.pop();
			postfix.append(operator);
			postfix.append(" ");
			calculate(operator);
		}
		// Check ! if the stack has only 1 element left here.
		if(numStack.size() != 1) {
			throw new Exception("Error in Evaluation!");
		}
		System.out.println(postfix.deleteCharAt(postfix.length() - 1).toString()); // there will be an extra space at the end, so print everything except that

		System.out.println(numStack.pop());
	}

	private static void calculate(char operator) throws Exception {
		// performs calculation each time an operand is popped from the opStack
		Long op1, op2; // operands
		try {
			if ("^*/%+-".indexOf(operator) >= 0) { // binary operator
				op2 = numStack.pop(); // pop 2 elements from stack
				op1 = numStack.pop();
				switch (operator) {
					case '+':
						numStack.push(op1 + op2);
						break;
					case '-':
						numStack.push(op1 - op2);
						break;
					case '%':
						numStack.push(op1 % op2);
						break;
					case '/':
						numStack.push(op1 / op2);
						break;
					case '*':
						numStack.push(op1 * op2);
						break;
					case '^':
						numStack.push(pow(op1, op2));
						break;
					default:
						break;
				}
			} else { // unary -
				op1 = numStack.pop();
				numStack.push(-op1);
			}
		} catch (Exception e) { // tried to pop too many items from stack
			throw new Exception("Error! Empty Stack!");
		}
	}


	private static boolean isNum(char c) { // checks if it a number
		int val = c - '0';
		return (val >= 0) && (val < 10);
	}

	private static boolean isOp(char c) { // checks if it is a operator
		return operator_list.contains(Character.toString(c));
	}

	private static boolean compare(char op1, char op2) {
		// returns true if op1 's precedence is greater than or equal to op2's precedence
		return (getPre(op1) > getPre(op2));
	}

	private static int getPre(char operator) {
		// returns the precedence of operator. Higher the value, higher the precedence
		String op = Character.toString(operator);
		if ("^".contains(op)) {
			return 10;
		} else if ("~".contains(op)) {
			return 9;
		} else if ("*/%".contains(op)) {
			return 8;
		} else if ("+-".contains(op)) {
			return 7;
		} else if ("(".contains(op)) {
			return 6;
		} else if (")".contains(op)) {
			return 5;
		} else return 4;
	}

	private static boolean isLeftAssoc(char c) { // right associative operators
		return (c != '^') && (c != '~');
	}

	private static long pow(long op1, long op2) throws Exception { // calculates op1 to the op2 power (op1 ^ op2)
		 if(op1 == 0 && op2 < 0) { // 0 to the negative powers cause ERROR
		 	throw new Exception("Divided by 0!");
		 } else {
		 	return (long) Math.pow(op1, op2);
		 }
	}
}
