package com.imer1c.commands.arguments;

public class Token<E> {
    private final TokenType type;
    private final int index;
    private final E value;

    public Token(TokenType type, int index, E value)
    {
        this.type = type;
        this.index = index;
        this.value = value;
    }

    public int getIndex()
    {
        return index;
    }

    public TokenType getType()
    {
        return type;
    }

    public E getValue()
    {
        return value;
    }

    public enum TokenType {
        WORD,
        SPACE,
        NUMBER,
        QUOTATION_MARK,
        DASH
    }
}
