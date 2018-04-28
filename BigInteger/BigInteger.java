// 2017-18570 Sungchan Yi
// Dept. of Computer Science and Engineering

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigInteger {
    public static final String QUIT_COMMAND = "quit"; // quit command
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다."; // error message to print
    int[] num = new int[201]; // element 0 : Sign bit (MSE), everything else is a digit

    // Pattern: (space) (+-) (space) (digit) (space) (+-*) (space) (+-) (space) (digit) (space)
    public static final Pattern exp = Pattern.compile("^(\\s*)([+\\-]?)(\\s*)(\\d+)(\\s*)([+\\-*])(\\s*)([+\\-]?)(\\s*)(\\d+)(\\s*)$");
    public BigInteger() {} // Default constructor

    public BigInteger(String s) { // constructor using a given String
        char c = s.charAt(0); // Take the first letter of string to check if it has a sign
        int len = s.length(); // How long is the string?
        if(c == '-') { // number has negative sign
            // num[0] = 9; // assign 9 to MSE (redundant)
            num[200] = 10 - (s.charAt(len - 1) - '0');
            int temp = num[200] / 10;  // variable carry
            num[200] %= 10;
            int i = 1;
            // Extract the number value from string and change it to 10's complement
            for(; i < len - 1; i++) {
                num[200 - i] = 9 - (s.charAt(len - 1 - i) - '0') + temp;
                temp = num[200 - i] / 10;
                num[200 - i] %= 10;
            }
            for(; i < 201; i++) { // fill the rest of the space with 9
                num[200 - i] = 9 + temp;
                temp = num[200 - i] / 10;
                num[200 - i] %= 10;
            }
        } else if(c == '+') { // number has positive sign
            // num[0] = 0; //assign 0 to MSE (redundant)
            for(int i = 0; i < len - 1; i++) {
                // extract number value from string
                num[200 - i] = s.charAt(len - 1 - i) - '0';
            }
        } else { // nothing. == positive.
            for(int i = 0; i < len; i++) {
                num[200 - i] = s.charAt(len - 1 - i) - '0';
            }
        }
        /*for(int i = 0; i < num.length; i++) {
            System.out.print(num[i]);
        } System.out.println();*/

    }

    public BigInteger negate() { // Changes the number to 10's complement number
        // take 9's complement and add 1
        BigInteger res = new BigInteger();
        res.num[200] = 10 - (this.num[200]);
        int i = 1, temp = res.num[200] / 10; // counter, carry
        res.num[200] %= 10;
        for(; i < 201; i++) {
            res.num[200 - i] = 9 - (this.num[200 - i]) + temp;
            temp = res.num[200 - i] / 10;
            res.num[200 - i] %= 10;
        }
        return res;
    }

    public BigInteger add(BigInteger BigInt) { // adds two BigInts
        BigInteger res = new BigInteger(); // Result
        int temp = 0; // Carry
        for(int i = 200; i >= 0; i--) { // Iterate for each elements of the array num and add
            res.num[i] = this.num[i] + BigInt.num[i] + temp;
            temp = res.num[i] / 10;
            res.num[i] %= 10;
        }
        return res;
    }

    public BigInteger subtract(BigInteger BigInt) { // subtracts two BigInts
        // Negate the second BigInt and add to the first BigInt
        return this.add(BigInt.negate());
    }

    public BigInteger multiply(BigInteger BigInt) { // multiplies two BigInts
        BigInteger res = new BigInteger();
        int sign1 = this.num[0], sign2 = BigInt.num[0];
        // Check for signs of given BigIntegers
        // If a number is negative, negate it and then multiply then negate it again
        if(sign1 == 0 && sign2 == 0) { // + * +
            // Don't have to do anything
        } else if(sign1 == 0 && sign2 == 9) { // + * -
            BigInteger temp = BigInt.negate();
            res = this.multiply(temp);
            res = res.negate();
            return res;
        } else if(sign1 == 9 && sign2 == 0) { // - * +
            BigInteger temp = this.negate();
            res = BigInt.multiply(temp);
            res = res.negate();
            return res;
        } else { // - * -
            BigInteger temp1 = this.negate();
            BigInteger temp2 = BigInt.negate();
            res = temp1.multiply(temp2);
            res.num[0] = 0;
            return res;
        }
        int temp = 0, loc; // carry, location to add at
        for(int j = 200; j >= 100; j--) { // Elementary School Multiplication Algorithm
            for(int i = 200; i >= 100; i--) {
                loc = i + j - 200;
                res.num[loc] += this.num[i] * BigInt.num[j];
            }
        }
        for(int i = 200; i > 0; i--) { // Take care of carry-ups
            res.num[i] += temp;
            temp = res.num[i] / 10;
            res.num[i] %= 10;
        }
        res.num[0] += temp;

        return res;
    }

    public String toString() { // Changes the BigInt to string
        StringBuilder sb = new StringBuilder("");
        if(this.num[0] == 9) { // negative sign
            sb.append("-");
            BigInteger temp = this.negate(); // take 10's complement and make it positive
            sb.append(temp.toString()); // call toString
        } else { // positive or zero
            int i = 1; //counter
            while(i < 200 && this.num[i] == 0) { // pass zeros in the front of the list
                i++;
            }
            for(; i < 201; i++) { // append each char starting from the non-zero digit
                sb.append(this.num[i]);
            }
        }
        return sb.toString();
    }

    static BigInteger evaluate(String input) throws IllegalArgumentException {
        String replacementText = "$2$4|$6|$8$10"; // sign, int, operand, sign, int
        Matcher m = exp.matcher(input); // Match!
        String[] operandList = m.replaceAll(replacementText).split("\\|"); // get list of elements
        /*for(int i = 0; i < operandList.length; i++) {
            System.out.print(operandList[i] + ",");
        }*/
        BigInteger int1 = new BigInteger(operandList[0]); // First Number
        BigInteger int2 = new BigInteger(operandList[2]); // Second Number
        BigInteger res = new BigInteger(); // result BigInt
        switch(operandList[1]) { // choose operand
            case "+":
                res = int1.add(int2);
                break;
            case "-":
                res = int1.subtract(int2);
                break;
            case "*":
                res = int1.multiply(int2);
                break;
            default:
                break;
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        try (InputStreamReader isr = new InputStreamReader(System.in)) {
            try (BufferedReader reader = new BufferedReader(isr)) {
                boolean done = false;
                while (!done) {
                    String input = reader.readLine(); // Input
                    if(input.equals("quit"))
                        break; // Quits if entered quit
                    try {
                        done = processInput(input);
                    } catch (Exception e) {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }

    static boolean processInput(String input) {
        boolean quit = isQuitCmd(input);
        if (quit) {
            return true;
        } else {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
            return false;
        }
    }
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
