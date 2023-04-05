//public class Calculator {
//    public static void main(String[] args) {
//        String input = "1 + 2 * (3 + 4)";
//        Parser parser = new Parser(input);
//        int result = parser.parseExpr();
//        System.out.println("Result: " + result);
//
//        /*
//        * <expr> -> <bexp> { & ‹bexp> | "' «bexp> } i !‹expr› i true ! false
//<bexp> -> <aexp> [ ‹relop> <aexp> ]
//‹relop> -> == | != | < | > | <= | >=
//<aexp> -> <term> { + <term> | - <term> }
//<term> -> <factor> {  * ‹factor> | / ‹factor> }
//<factor> -> [-] ( <number> | ( <aexp> ) )
//<number> -> <digit> { <digit> }
//*/
//    }
//}
