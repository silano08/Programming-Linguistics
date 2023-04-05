//class Lexer {
//    private String input;
//    private int pos;
//
//    Lexer(String input) {
//        this.input = input;
//        this.pos = 0;
//    }
//
//    Token nextToken() {
//        while (pos < input.length()) {
//            char ch = input.charAt(pos);
//
//            if (Character.isWhitespace(ch)) {
//                pos++;
//            } else if (Character.isDigit(ch)) {
//                int start = pos;
//                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
//                    pos++;
//                }
//                return new Token(TokenType.NUMBER, input.substring(start, pos));
//            } else {
//                pos++;
//                switch (ch) {
//                    case '+': return new Token(TokenType.PLUS, "+");
//                    case '-': return new Token(TokenType.MINUS, "-");
//                    case '*': return new Token(TokenType.MULTIPLY, "*");
//                    case '/': return new Token(TokenType.DIVIDE, "/");
//                    case '(': return new Token(TokenType.LEFT_PAREN, "(");
//                    case ')': return new Token(TokenType.RIGHT_PAREN, ")");
//                    // Implement more cases for other tokens.
//                    default:
//                        throw new RuntimeException("Unexpected character: " + ch);
//                }
//            }
//        }
//
//        return new Token(TokenType.EOF, "");
//    }
//}
