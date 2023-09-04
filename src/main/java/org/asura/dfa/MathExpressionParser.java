package org.asura.dfa;

import java.util.ArrayList;
import java.util.List;

public class MathExpressionParser {

    private enum State {
        START, NUMBER, OPERATOR
    }

    public static List<Object> parseExpression(String expression) throws Exception {
        List<Object> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        State currentState = State.START;

        for (char c : expression.toCharArray()) {
            switch (currentState) {
                case START -> {
                    if (Character.isDigit(c) || c == '.') {
                        currentToken.append(c);
                        currentState = State.NUMBER;
                    } else if (isOperator(c)) {
                        tokens.add(String.valueOf(c));
                        currentState = State.OPERATOR;
                    } else if (!Character.isWhitespace(c)) {
                        throw new Exception("Invalid character: " + c);
                    }
                }
                case NUMBER -> {
                    if (Character.isDigit(c) || c == '.') {
                        currentToken.append(c);
                    } else if (isOperator(c)) {
                        tokens.add(parseNumber(currentToken.toString()));
                        tokens.add(String.valueOf(c));
                        currentToken.setLength(0);
                        currentState = State.START;
                    } else if (!Character.isWhitespace(c)) {
                        throw new Exception("Invalid character: " + c);
                    }
                }
            }
        }

        if (currentState == State.NUMBER) {
            tokens.add(parseNumber(currentToken.toString()));
        }

        return tokens;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static Object parseNumber(String str) {
        return Double.parseDouble(str);
    }

    public static void main(String[] args) {
        try {
            String expression = " 01+2*3.5-4 / 2";
            List<Object> tokens = parseExpression(expression);
            System.out.println(tokens);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
