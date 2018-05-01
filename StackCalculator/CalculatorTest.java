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
				numStack.clear();
				opStack.clear();
			} catch (Exception e) {
				System.out.println("ERROR");
				numStack.clear();
				opStack.clear();
				// System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
				//e.printStackTrace();
			}
		}
	}

	private static void command(String input) throws Exception {
		char[] eq = input.toCharArray(); // infix expression to scan
		StringBuilder sb = new StringBuilder(); // for scanning numbers
		StringBuilder postfix = new StringBuilder(); // for storing postfix
		boolean unary_flag = true, number_flag = true; // flags for unary operator and number
		int parencount = 0; // counter for parentheses
		for (int i = 0; i < eq.length; ++i) {
			if (isNum(eq[i])) {
				if (!number_flag) {
					throw new Exception("Error!");
				}
				while (i < eq.length && isNum(eq[i])) { // get number
					sb.append(eq[i]);
					++i;
				}
				i--;
				postfix.append(sb + " ");
				//System.out.print(sb.toString() + " ");
				sb = new StringBuilder();
				number_flag = false; // number cannot come anymore
				unary_flag = false; // - cannot come after number
			} else if (isOp(eq[i])) {
				number_flag = true;
				if (opStack.isEmpty() || opStack.peek() == '(') { // if the stack is empty, push
					// - may come after operator
					if (unary_flag && eq[i] == '-') {
						opStack.push('~');
						continue;
					}
					opStack.push(eq[i]);
					unary_flag = true;
				} else {
					if (compare(eq[i], opStack.peek())) { // precedence: current > stack
						opStack.push(eq[i]);
					} else { // precedence: current <= stack
						if (unary_flag && eq[i] == '-') { // if unary, change - to ~
							eq[i] = '~';
						}
						// pop while the stack has an element, precedence: current > stack,
						// both operands have same precedence and is left associative
						while (!opStack.isEmpty() && !compare(eq[i], opStack.peek()) && (!isRightAssoc(opStack.peek()) || !isRightAssoc(eq[i]))) {
							char operator = opStack.pop();
							postfix.append(operator + " ");
							// System.out.print(operator + " ");
						}
						opStack.push(eq[i]);
						unary_flag = true;
					}
				}
			} else if (eq[i] == '(') {
				++parencount;
				opStack.push(eq[i]);
				unary_flag = true;
			} else if (eq[i] == ')') {
				--parencount;
				while (!opStack.isEmpty() && !(opStack.peek() == '(')) {
					char operator = opStack.pop();
					postfix.append(operator + " ");
					//System.out.print(operator + " ");
				}
				opStack.pop();
			}
		}
		if (parencount != 0) {
			throw new Exception("Error! Paren Mismatch!");
		}
		while (!opStack.isEmpty()) {
			postfix.append(opStack.pop() + " ");
			//System.out.print(opStack.pop() + " ");
		}
		System.out.println(postfix.deleteCharAt(postfix.length() - 1).toString());
		long res = calculate(postfix.toString()); // calculate the value
		System.out.println(res);
	}

	private static long calculate(String postfix) throws Exception {
		Scanner sc = new Scanner(postfix);
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
								numStack.push((long) Math.pow(op1, op2));
								break;

							default:
								break;
						}
					} else { // unary -
						op1 = numStack.pop();
						numStack.push(-op1);
					}
				} catch (Exception e) {
					throw new Exception("Error! Empty Stack!");
				}
			}
		}
		if (numStack.size() != 1) {
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

//	 private static long pow(long op1, long op2) throws Exception { // calculates op1 to the op2 power (op1 ^ op2)
//		if (op2 == 0) {
//			if (op1 == 0) {
//				throw new Exception();
//			}
//			return 1;
//		} else if (op2 == 1) {
//			return op1;
//		} else if (op2 % 2 == 0) {
//			return pow(op1 * op1, op2 / 2);
//		} else if (op2 % 2 == 1) {
//			return op1 * pow(op1, op2 - 1);
//		} else if (op1 == 0 && op2 < 0) {
//			throw new Exception();
//		} else {
//			return pow((1 / op1), -op2);
//		}


}

