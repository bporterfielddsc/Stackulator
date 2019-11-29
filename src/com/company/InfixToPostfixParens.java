package com.company;

import javax.print.DocFlavor;
import java.util.*;

public class InfixToPostfixParens
{
    public static class SyntaxErrorException extends Exception
    {
        SyntaxErrorException(String message)
        {
            super(message);
        }
    }

    private static final String OPERATORS = "-+*/()";
    private static final int[] PRECEDENCE = {1,1,2,2,-1,-1};
    private static final String PATTERN = "\\d+\\.\\d*|\\d+|" + "\\p{L}[\\p{L}\\p{N}]*" + "|[" + OPERATORS + "]";
    private final Deque<Character> operatorStack = new ArrayDeque<>();
    private final StringJoiner postfix = new StringJoiner(" ");

    public static String convert(String infix) throws SyntaxErrorException
    {
        InfixToPostfixParens infixToPostfixParens = new InfixToPostfixParens();
        infixToPostfixParens.convertToPostfix(infix);
        return infixToPostfixParens.getPostfix();
    }

    private String getPostfix()
    {
        return postfix.toString();
    }

    private String convertToPostfix(String infix) throws SyntaxErrorException
    {
        try
        {
            String nextToken;
            Scanner scan = new Scanner(infix);
            while ((nextToken = scan.findInLine(PATTERN)) != null)
            {
                char firstChar = nextToken.charAt(0);
                if(Character.isLetter(firstChar) || Character.isDigit(firstChar))
                {
                    postfix.add(nextToken);
                }
                else if(isOperator(firstChar))
                {
                    processOperator(firstChar);
                }
                else
                {
                    throw new SyntaxErrorException("Unexpected Character Encountered: " + firstChar);
                }
            }

            while(!operatorStack.isEmpty())
            {
                char op = operatorStack.pop();

                if(op == '(') throw new SyntaxErrorException("Unmatched opening parenthesis");
                postfix.add(new Character(op).toString());
            }
            return postfix.toString();
        }
        catch (NoSuchElementException ex)
        {
            throw new SyntaxErrorException("Syntax Error: The stack is empty");
        }

    }

    private void processOperator(char op)
    {
        if(operatorStack.isEmpty() || op == '(')
        {
            operatorStack.push(op);
        }

        else {
            char topOp = operatorStack.peek();
            if(precedence(op) > precedence(topOp))
            {
                operatorStack.push(op);
            }

            else
            {
                while(!operatorStack.isEmpty() && precedence(op) <= precedence(topOp))
                {
                    operatorStack.pop();

                    if(topOp == '(')
                    {
                        break;
                    }
                    postfix.add(new Character(topOp).toString());

                    if(!operatorStack.isEmpty())
                    {
                        topOp = operatorStack.peek();
                    }
                }

                if(op != ')')
                {
                    operatorStack.push(op);
                }
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
