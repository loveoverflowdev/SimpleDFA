package org.asura.dfa;

import org.asura.dfa.exceptions.ExpressionException;
import org.asura.dfa.exceptions.NumberException;
import org.asura.dfa.parser.MathExpressionParser;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        final MathExpressionParser parser = new MathExpressionParser();

        try {
            String expression = " 100.0     +2*3.5-4 / 2";
            List<Object> tokens = parser.parseExpression(expression);
            System.out.println(tokens);
        } catch (ExpressionException e) {
            System.out.println("ExpressionException");
        } catch (NumberException e) {
            System.out.println("NumberException");
        } catch (Exception e) {
            System.out.println("Undefined Exception");
        }
    }
}
