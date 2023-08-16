package com.imer1c.commands.arguments;

import java.util.List;
import java.util.Map;

public class Arguments {

    public static Arguments parse(String s)
    {
        ArgumentTokenParser tokenParser = new ArgumentTokenParser(s);
        ArgumentParser parser = new ArgumentParser(tokenParser);

        return new Arguments(parser.getParameters(), parser.getArguments());
    }

    private final List<String> parameters;
    private final Map<String, List<Argument<?>>> argumentMap;

    public Arguments(List<String> parameters, Map<String, List<Argument<?>>> arguments)
    {
        this.parameters = parameters;
        this.argumentMap = arguments;
    }

    public String getParameter(int index)
    {
        return this.parameters.get(index);
    }

    public int parameters()
    {
        return this.parameters.size();
    }

    public boolean contains(String id)
    {
        return this.argumentMap.containsKey(id);
    }

    public List<Argument<?>> getValues(String id)
    {
        return this.argumentMap.get(id);
    }

    public<E> Argument<E> getFirstValue(String id)
    {
        return (Argument<E>) this.argumentMap.get(id).get(0);
    }
}
