package com.imer1c.commands.jsoninfo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class JsonObject {
    private final List<Pair<String, String>> variables = new ArrayList<>();
    private final String name;

    public JsonObject(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void addVariable(String id, String type)
    {
        this.variables.add(Pair.of(id, type));
    }

    public List<Pair<String, String>> getVariables()
    {
        return variables;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof JsonObject js))
        {
            return false;
        }

        return js.name.equals(this.name);
    }
}
