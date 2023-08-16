package com.imer1c.commands.arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentParser {
    private final List<Token<?>> tokens;
    private final List<String> parameters;
    private final Map<String, List<Argument<?>>> arguments;
    private int index;

    public ArgumentParser(ArgumentTokenParser parser)
    {
        this.tokens = parser.getTokens();
        this.arguments = new HashMap<>();
        this.parameters = new ArrayList<>();

        while (this.index < this.tokens.size())
        {
            this.parseNext();
        }
    }

    private void advance()
    {
        this.index++;
    }

    private Token<?> current()
    {
        if (this.index == this.tokens.size())
        {
            return null;
        }

        return this.tokens.get(this.index);
    }

    private void skipSpaces()
    {
        while (this.index < this.tokens.size() && this.current().getType() == Token.TokenType.SPACE)
        {
            this.advance();
        }
    }

    public void parseNext()
    {
        this.skipSpaces();

        Token<?> current = this.current();

        if (current.getType() == Token.TokenType.DASH)
        {
            this.advance();

            String name = (String) this.current().getValue();

            this.advance();
            this.skipSpaces();

            Token<?> t = this.current();
            this.skipSpaces();

            Argument<?> arg;
            if (t == null || t.getType() == Token.TokenType.DASH)
            {
                arg = new Argument<>(name, null);
            }
            else
            {
                int i = this.index;

                while (this.index < this.tokens.size() && this.current().getType() != Token.TokenType.DASH)
                {
                    this.advance();
                }

                List<Token<?>> tokens = this.tokens.subList(i, this.index);
                StringBuilder builder = new StringBuilder();

                tokens.forEach(token -> builder.append(token.getValue()));

                System.out.println(builder.toString());

                arg = new Argument<>(name, builder.toString());
            }

            List<Argument<?>> arguments = this.arguments.computeIfAbsent(arg.getId(), s -> new ArrayList<>());

            arguments.add(arg);
        }

        if (current.getType() == Token.TokenType.WORD || current.getType() == Token.TokenType.NUMBER)
        {
            this.parameters.add(String.valueOf(current.getValue()));

            this.advance();
        }
    }

    public Map<String, List<Argument<?>>> getArguments()
    {
        return arguments;
    }

    public List<String> getParameters()
    {
        return parameters;
    }
}
