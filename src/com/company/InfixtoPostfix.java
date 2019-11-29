package com.company;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class InfixtoPostfix
{
    public static class SyntaxErrorException extends Exception
    {
        SyntaxErrorException(String message)
        {
            super(message);
        }
    }
    private final Deque<Character> operatorStack = new ArrayDeque<>();
    private static final String OPERATORS = "+-*/";
    private static final int[] PRECEDENCE ={1,1,2,2};
    private final StringJoiner postfix = new StringJoiner(" ");

    public static String convert(String infix) throws SyntaxErrorException
    {
        InfixtoPostfix infixToPostfix = new InfixtoPostfix();
        infixToPostfix.convertToPostfix(infix);
        return infixToPostfix.getPostfix();
    }

    private String getPostfix()
    {
        return postfix.toString();
    }

    private void convertToPostfix(String infix) throws SyntaxErrorException
    {
        String [] tokens = infix.split("\\s+");
        try
        {
            for (String nextToken : tokens)
            {
                char firstChar = nextToken.charAt(0);
                if (Character.isJavaIdentifierStart(firstChar) || Character.isDigit(firstChar))
                {
                    postfix.add(nextToken);
                }
                else if (isOperator(firstChar))
                {
                    processOperator(firstChar);
                }
                else
                {
                    throw new SyntaxErrorException("Unexpected Character Encountered: " + firstChar);
                }
            }
            while (!operatorStack.isEmpty())
            {
                char op = operatorStack.pop();
                postfix.add(new Character(op).toString());
            }
        }
        catch (NoSuchElementException ex)
        {
            throw new SyntaxErrorException("Syntax Error: the stack is empty");
        }
    }
    private void processOperator(char op)
    {
        if(operatorStack.isEmpty())
        {
            operatorStack.push(op);
        }
        else {
            char topOp = operatorStack.peek();
            if (precedence(op) > precedence(topOp))
            {
                operatorStack.push(op);
            }
            else
            {
                while (!operatorStack.isEmpty() && precedence(op) <= precedence(topOp))
                {
                    operatorStack.pop();
                    postfix.add(new Character(topOp).toString());
                    if(!operatorStack.isEmpty())
                    {
                        topOp = operatorStack.peek();
                    }
                }
                operatorStack.push(op);
            }
        }
    }

    private static boolean isOperator(char ch)
    {
        return OPERATORS.indexOf(ch) != -1;
    }

    private static int precedence(char op)
    {
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }
}

