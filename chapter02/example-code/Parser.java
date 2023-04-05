//public class Parser {
//
//    private Lexer lexer;
//    private Token currentToken;
//
//    Parser(String input) {
//        this.lexer = new Lexer(input);
//        this.currentToken = this.lexer.nextToken();
//    }
//
//    int parseExpr() {
//        int result = parseAExp();
//        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
//            TokenType op = currentToken.type;
//            consume(op);
//            int term = parseAExp();
//            if (op == TokenType.PLUS) {
//                result += term;
//            } else if (op == TokenType.MINUS) {
//                result -= term;
//            }
//        }
//        return result;
//    }
//
//    int parseAExp() {
//        int result = parseTerm();
//        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
//            TokenType op = currentToken.type;
//            consume(op);
//            int term = parseTerm();
//            if (op == TokenType.PLUS) {
//                result += term;
//            } else if (op == TokenType.MINUS) {
//                result -= term;
//            }
//        }
//        return result;
//    }
//
//    int parseTerm() {
//        int result = parseFactor();
//        while (currentToken.type == TokenType.MULTIPLY || currentToken.type == TokenType.DIVIDE) {
//            TokenType op = currentToken.type;
//            consume(op);
//            int factor = parseFactor();
//            if (op == TokenType.MULTIPLY) {
//                result *= factor;
//            } else if (op == TokenType.DIVIDE) {
//                result /= factor;
//            }
//        }
//        return result;
//    }
//
//    int parseFactor() {
//        int result = 0;
//        if (currentToken.type == TokenType.NUMBER) {
//            result = Integer.parseInt(currentToken.value);
//            consume(TokenType.NUMBER);
//        } else if (currentToken.type == TokenType.LEFT_PAREN) {
//            consume(TokenType.LEFT_PAREN);
//            result = parseAExp();
//            consume(TokenType.RIGHT_PAREN);
//        }
//        return result;
//    }
//
//    private void consume(TokenType expectedType) {
//        if (currentToken.type == expectedType) {
//            currentToken = lexer.nextToken();
//        } else {
//            throw new RuntimeException("Unexpected token: " + currentToken.type);
//        }
//    }
//
//}
