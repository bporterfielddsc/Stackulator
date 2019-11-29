package com.company;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class PostfixEvaluator
{
    public static class SyntaxErrorException extends Exception
    {
        SyntaxErrorException(String message)
        {
            super(message);
        }
    }

    private static final String OPERATORS = "+-*/";
    private static int evalOp(char op, Deque<Integer> operandStack)
    {
        int rhs = operandStack.pop();
        int lhs = operandStack.pop();
        int result = 0;

        switch (op)
        {
            case '+' : result = lhs + rhs;
                       break;
            case '-' : result = lhs-rhs;
                       break;
            case '/' : result = lhs/rhs;
                       break;
            case '*' : result = lhs*rhs;
                       break;
        }
        return result;
    }

    private static boolean isOperator(char ch)
    {
        return OPERATORS.indexOf(ch) != -1;
    }

    public static int eval(String expression) throws SyntaxErrorException
    {
        Deque<Integer> operandStack = new ArrayDeque<>();

        String [] tokens = expression.split("\\s+");
        try
        {
            for (String nextToken : tokens)
            {
                char firstChar = nextToken.charAt(0);

                if (Character.isDigit(firstChar))
                {
                    int value = Integer.parseInt(nextToken);
                    operandStack.push(value);
                }

                else if (isOperator(firstChar))
                {
                    int result = evalOp(firstChar, operandStack);
                    operandStack.push(result);
                }

                else
                {
                    throw new SyntaxErrorException("Invalid character encountered: " + firstChar);
                }
            }

            int answer = operandStack.pop();

            if (operandStack.isEmpty())
            {
                return answer;
            }
            else
            {
                throw new SyntaxErrorException("Syntax Error: Stack should be empty");
            }

        }

        catch (NoSuchElementException ex)
        {
            throw new SyntaxErrorException("Syntax Error: Stack is empty");
        }
    }
}

