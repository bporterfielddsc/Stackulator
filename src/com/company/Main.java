package com.company;

import javax.swing.*;
//I tried to do it the way we talked about in your office, but I couldn't get it to work that way.
public class Main {

    public static void main(String[] args)
    {
        String infix = JOptionPane.showInputDialog("insert an infix equation");

        if (infix.contains("("))
        {
            InfixToPostfixParens answer1 = new InfixToPostfixParens();

            String postfix1 = null;
            try
            {
                postfix1 = answer1.convert(infix);
            } catch (InfixToPostfixParens.SyntaxErrorException e)
            {
                e.printStackTrace();
            }

            PostfixEvaluator eval1 = new PostfixEvaluator();

            int result1 = 0;
            try
            {
                result1 = eval1.eval(postfix1);
            } catch (PostfixEvaluator.SyntaxErrorException e)
            {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "your answer is " + result1);
        }

        else
        {
            InfixtoPostfix answer2 = new InfixtoPostfix();

            String postfix2 = null;
            try
            {
                postfix2 = answer2.convert(infix);
            } catch (InfixtoPostfix.SyntaxErrorException e)
            {
                e.printStackTrace();
            }

            PostfixEvaluator eval2 = new PostfixEvaluator();

            int result2 = 0;
            try
            {
                result2 = eval2.eval(postfix2);
            } catch (PostfixEvaluator.SyntaxErrorException e)
            {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "your answer is " + result2);
        }
    }
}
