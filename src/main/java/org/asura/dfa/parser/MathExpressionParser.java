package org.asura.dfa.parser;

import org.asura.dfa.exceptions.ExpressionException;
import org.asura.dfa.exceptions.NumberException;

import java.util.ArrayList;
import java.util.List;

public class MathExpressionParser {

    private enum State {
        START, NUMBER, OPERATOR
    }

    public List<Object> parseExpression(String expression) throws Exception {
        final List<Object> tokens = new ArrayList<>();
        final StringBuilder currentToken = new StringBuilder();
        State currentState = State.START;

        final char[] charArray = expression.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (Character.isWhitespace(c)) {
                continue;
            }

            final boolean isLastChar = i == charArray.length - 1;

            switch (currentState) {
                case START -> {
                    if (Character.isDigit(c) || c == '.') {
                        currentToken.append(c);
                        currentState = State.NUMBER;
                    } else if (isOperator(c)) {
                        throw new ExpressionException();
                    }
                }
                case NUMBER -> {
                    if (Character.isDigit(c) || c == '.') {
                        currentToken.append(c);
                    } else if (isOperator(c)) {
                        if (isLastChar) {
                            throw new ExpressionException();
                        }
                        tokens.add(parseNumber(currentToken.toString()));
                        tokens.add(c);
                        currentToken.setLength(0);
                        currentState = State.OPERATOR;
                    }
                }
                case OPERATOR -> {
                    if (Character.isDigit(c)) {
                        currentState = State.NUMBER;
                        currentToken.append(c);
                    } else {
                        throw new ExpressionException();
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

    private static Object parseNumber(String str) throws NumberException {
        try {
            final double number = Double.parseDouble(str);
            if (str.startsWith("0") && number > 0) {
                throw new NumberException();
            }
            return number;
        } catch (NumberFormatException e) {
            throw new NumberException();
        }
    }
}
