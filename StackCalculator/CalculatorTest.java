import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class CalculatorTest {
	static Stack<Long> numStack = new Stack<Long>(); // stack for numbers
	static Stack<Character> opStack = new Stack<Character>(); // stack for operators
	static String operator_list = "^~*/%+-"; // operator list

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
		boolean unary_flag = true, number_flag = true; // flags for unary operator and number
		int parencount = 0; // counter for parentheses
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
				postfix.append(sb + " "); // append to the postfix expression
				sb = new StringBuilder(); // reset
				number_flag = false; // number cannot come anymore
				unary_flag = false; // - cannot come after number
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
						while (!opStack.isEmpty() && !compare(eq[i], opStack.peek()) && (!isRightAssoc(opStack.peek()) || !isRightAssoc(eq[i]))) {
							char operator = opStack.pop();
							postfix.append(operator + " "); // append to the postfix expression
						}
						opStack.push(eq[i]); // push current operator
					}
				}
				unary_flag = true; // unary can come after operator
			} else if (eq[i] == '(') { // check open paren.
				++parencount;
				opStack.push(eq[i]);
				unary_flag = true; // unary - can come after open paren.
			} else if (eq[i] == ')') { // check close paren.
				--parencount;
				int operand_count = 0; // counts how many operands exist between (, ).
				while (!opStack.isEmpty() && !(opStack.peek() == '(')) {
					char operator = opStack.pop();
					postfix.append(operator + " "); // append to the postfix expression
					operand_count++;
				}
				if(operand_count == 0) { // input contains ()
					throw new Exception("Nothing between parens");
				}
				opStack.pop(); // pop the open paren
			}
		}
		if (parencount != 0) { // parenthesis do not match
			throw new Exception("Error! Paren Mismatch!");
		}
		while (!opStack.isEmpty()) { // pop the remaining operands left in the stack
			postfix.append(opStack.pop() + " ");
		}
		long res = calculate(postfix.toString()); // calculate the value
		System.out.println(postfix.deleteCharAt(postfix.length() - 1).toString()); // there will be an extra space at the end, so print everything except that
		System.out.println(res);
	}

	private static long calculate(String postfix) throws Exception {
		Scanner sc = new Scanner(postfix); // scanner for scanning the postfix expression
		Long op1, op2; // operands
		char operator; // operator
		while (sc.hasNext()) {
			String curr = sc.next();
			if (isNum(curr.charAt(0))) { // not operand ( starts with number)
				numStack.push(Long.parseLong(curr)); // push number
			} else {
				try {
					if ("^*/%+-".contains(curr)) { // binary operator
						op2 = numStack.pop(); // pop 2 elements from stack
						op1 = numStack.pop();
						operator = curr.charAt(0);
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
		}
		// If the calculations are done correctly, there should be only one item left in the stack
		if (numStack.size() != 1) { // there are more than 1 items left in stack
			throw new Exception("Error in Expression Evalutaion!");
		}
		return numStack.pop();
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

	private static boolean isRightAssoc(char c) { // right associative operators
		return (c == '^') || (c == '~');
	}

	private static long pow(long op1, long op2) throws Exception { // calculates op1 to the op2 power (op1 ^ op2)
		 if(op1 == 0 && op2 < 0) { // 0 to the negative powers cause ERROR
		 	throw new Exception("Divided by 0!");
		 } else {
		 	return (long) Math.pow(op1, op2);
		 }
	}


}

