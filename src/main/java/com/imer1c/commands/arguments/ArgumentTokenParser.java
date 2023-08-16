package com.imer1c.commands.arguments;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class ArgumentTokenParser {

    private static final char[] CHARS = {'/', '+', '-', '.', ',', '-', ':', '*', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '=', '$', '?', '&', '_', '\\'};

    private final List<Token<?>> tokens = new ArrayList<>();
    private final String s;
    private int index;

    public ArgumentTokenParser(String s)
    {
        this.s = s;

        while (this.index < this.s.length())
        {
            Token<?> e = this.parseNext();
            this.tokens.add(e);

            if (e == null)
            {
                this.advance();
            }
        }
    }

    private int advance()
    {
        return this.index++;
    }

    private char current()
    {
        return this.s.charAt(this.index);
    }

    public Token<?> parseNext()
    {
        char c = this.current();

        if (c == '-')
        {
            return new Token<>(Token.TokenType.DASH, this.advance(), '-');
        }

        if (c == '"')
        {
            return new Token<>(Token.TokenType.QUOTATION_MARK, this.advance(), '"');
        }

        if (c == ' ')
        {
            int start = this.index;

            while (this.current() == ' ')
            {
                this.advance();
            }

            return new Token<>(Token.TokenType.SPACE, start, this.s.substring(start, this.index - 1));
        }

        if (Character.isDigit(c))
        {
            int start = this.index;

            while (this.index < this.s.length() && Character.isDigit(this.current()))
            {
                this.advance();
            }

            return new Token<>(Token.TokenType.NUMBER, start, Integer.parseInt(this.s.substring(start, this.index)));
        }

        if (isLetter(c))
        {
            int start = this.index;

            while (this.index < this.s.length() && isLetter(this.current()))
            {
                this.advance();
            }

            return new Token<>(Token.TokenType.WORD, start, this.s.substring(start, this.index).replace("\\n", "\n"));
        }


        System.out.println("UNABLE TO FIND TOKEN TYPE FOR " + c);
        return null;
    }

    private boolean isLetter(char c)
    {
        return Character.isLetter(c) || ArrayUtils.contains(CHARS, c);
    }

    public List<Token<?>> getTokens()
    {
        return tokens;
    }
}
