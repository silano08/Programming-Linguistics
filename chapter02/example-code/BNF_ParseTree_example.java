import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

import static org.junit.Assert.assertEquals;

public class BNF_ParseTree_example {

    private PushbackInputStream input;
    private int token;
    private int value;

    private static final int AND = 1;
    private static final int OR = 2;
    private static final int NOT = 3;
    private static final int NOT_EQUAL = 4;
    private static final int EQUAL = 5;
    private static final int LESS = 6;
    private static final int LESS_EQUAL = 7;
    private static final int GREATER = 8;
    private static final int GREATER_EQUAL = 9;
    private static final int PLUS = 10;
    private static final int MINUS = 11;
    private static final int MULTIPLY = 12;
    private static final int DIVIDE = 13;
    private static final int LEFT_PAREN = 14;
    private static final int RIGHT_PAREN = 15;
    private static final int TRUE = 16;
    private static final int FALSE = 17;
    private static final int NUMBER = 18;
    private static final int EOF = -1;

    public BNF_ParseTree_example(PushbackInputStream input) {
        this.input = input;
    }

    public static void main(String[] args) throws IOException {
        String input = "1 + 2 * (3 + 4) == 15 / 5";
        PushbackInputStream inputStream = new PushbackInputStream(new ByteArrayInputStream(input.getBytes()));
        BNF_ParseTree_example parser = new BNF_ParseTree_example(inputStream);
        int result = parser.parseExpr();
        System.out.println("Result: " + result);
    }

//    @Test
//    public void Test() throws IOException {
//        String input = "1 + 2 * (3 + 4)";
//        PushbackInputStream inputStream = new PushbackInputStream(new ByteArrayInputStream(input.getBytes()));
//        BNF_ParseTree_example parser = new BNF_ParseTree_example(inputStream);
//        int result = parser.parseExpr();
//        assertEquals(result,15);
//    }

    private int getToken() throws IOException {
        int nextByte;
        while ((nextByte = input.read()) != -1) {
            char nextChar = (char) nextByte;
            if (nextChar == ' ' || nextChar == '\t' || nextChar == '\r') {
                // ignore white space
                continue;
            } else if (Character.isDigit(nextChar)) {
                // parse number token
                value = parseNumber(nextChar);
                return NUMBER;
            } else {
                // parse operator or boolean token
                switch (nextChar) {
                    case '&':
                        nextByte = input.read();
                        if (nextByte == '&') {
                            return  AND;
                        } else {
                            throw new RuntimeException("Invalid token: " + nextChar);
                        }
                    case '|':
                        nextByte = input.read();
                        if (nextByte == '|') {
                            return  OR;
                        } else {
                            throw new RuntimeException("Invalid token: " + nextChar);
                        }
                    case '!':
                        nextByte = input.read();
                        if (nextByte == '=') {
                            return  NOT_EQUAL;
                        } else {
                            input.unread(nextByte);
                            return  NOT;
                        }
                    case '=':
                        nextByte = input.read();
                        if (nextByte == '=') {
                            return  EQUAL;
                        } else {
                            throw new RuntimeException("Invalid token: " + nextChar);
                        }
                    case '<':
                        nextByte = input.read();
                        if (nextByte == '=') {
                            return  LESS_EQUAL;
                        } else {
                            input.unread(nextByte);
                            return  LESS;
                        }
                    case '>':
                        nextByte = input.read();
                        if (nextByte == '=') {
                            return  GREATER_EQUAL;
                        } else {
                            input.unread(nextByte);
                            return  GREATER;
                        }
                    case '+':
                        return  PLUS;
                    case '-':
                        return  MINUS;
                    case '*':
                        return  MULTIPLY;
                    case '/':
                        return  DIVIDE;
                    case '(':
                        return  LEFT_PAREN;
                    case ')':
                        return  RIGHT_PAREN;
                    case 't':
                        input.unread(nextByte);
                        if (parseBoolean("true")) {
                            return  TRUE;
                        } else {
                            throw new RuntimeException("Invalid token: " + nextChar);
                        }
                    case 'f':
                        input.unread(nextByte);
                        if (parseBoolean("false")) {
                            return  FALSE;
                        } else {
                            throw new RuntimeException("Invalid token: " + nextChar);
                        }
                    default:
                        throw new RuntimeException("Invalid token: " + nextChar);
                }
            }
        }
        return  EOF;
    }

    private int parseNumber(char firstChar) throws IOException {
        int value = Character.getNumericValue(firstChar);
        int nextByte;
        while ((nextByte = input.read()) != -1) {
            char nextChar = (char) nextByte;
            if (Character.isDigit(nextChar)) {
                value = value * 10 + Character.getNumericValue(nextChar);
            } else {
                input.unread(nextByte);
                return value;
            }
        }
        return value;
    }

    private boolean parseBoolean(String expectedValue) throws IOException {
        for (int i = 0; i < expectedValue.length(); i++) {
            char expectedChar = expectedValue.charAt(i);
            int nextByte = input.read();
            if (nextByte == -1) {
                throw new RuntimeException("Unexpected end of input");
            }
            char nextChar = (char) nextByte;
            if (nextChar != expectedChar) {
                throw new RuntimeException("Unexpected character: " + nextChar);
            }
        }
        return true;
    }

    public int parseExpr() throws IOException {
        int result = parseBExp();
        while (token ==  AND || token ==  OR) {
            int op = token;
            consume(op);
            int term = parseBExp();
            if (op ==  AND) {
                result = result & term;
            } else if (op ==  OR) {
                result = result | term;
            }
        }
        if (token !=  EOF) {
            throw new RuntimeException("Unexpected token: " + token);
        }
        return result;
    }

    private int parseBExp() throws IOException {
        int result = parseAExp();
        if (token ==  EQUAL || token ==  NOT_EQUAL || token ==  LESS ||
                token ==  LESS_EQUAL || token ==  GREATER || token ==  GREATER_EQUAL) {
            int op = token;
            consume(op);
            int term = parseAExp();
            switch (op) {
                case  EQUAL:
                    result = result == term ? 1 : 0;
                    break;
                case  NOT_EQUAL:
                    result = result != term ? 1 : 0;
                    break;
                case  LESS:
                    result = result < term ? 1 : 0;
                    break;
                case  LESS_EQUAL:
                    result = result <= term ? 1 : 0;
                    break;
                case  GREATER:
                    result = result > term ? 1 : 0;
                    break;
                case  GREATER_EQUAL:
                    result = result >= term ? 1 : 0;
                    break;
            }
        }
        return result;
    }

    private int parseAExp() throws IOException {
        int result = parseTerm();
        while (token ==  PLUS || token ==  MINUS) {
            int op = token;
            consume(op);
            int term = parseTerm();
            if (op ==  PLUS) {
                result += term;
            } else if (op ==  MINUS) {
                result -= term;
            }
        }
        return result;
    }

    private int parseTerm() throws IOException {
        int result = parseFactor();
        while (token ==  MULTIPLY || token ==  DIVIDE) {
            int op = token;
            consume(op);
            int factor = parseFactor();
            if (op ==  MULTIPLY) {
                result *= factor;
            } else if (op ==  DIVIDE) {
                result /= factor;
            }
        }
        return result;
    }

    private int parseFactor() throws IOException {
        int result;
        if (token ==  MINUS) {
            consume( MINUS);
            result = -parseFactor();
        } else if (token ==   NUMBER) {
            result = value;
            consume(  NUMBER);
        } else if (token ==   LEFT_PAREN) {
            consume(  LEFT_PAREN);
            result = parseBExp();
            consume(  RIGHT_PAREN);
        } else if (token ==   TRUE) {
            consume(  TRUE);
            result = 1;
        } else if (token ==   FALSE) {
            consume(  FALSE);
            result = 0;
        } else {
            throw new RuntimeException("Unexpected token: " + token);
        }
        return result;
    }


    private void consume(int expectedToken) throws IOException {
        if (token == expectedToken) {
            token = getToken();
        } else {
            throw new RuntimeException("Unexpected token: " + token);
        }
    }

}
