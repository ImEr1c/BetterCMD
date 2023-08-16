package com.imer1c.commands.arguments;

public class Argument<O> {
    private final String id;
    private final O value;

    public Argument(String id, O value)
    {
        this.id = id;
        this.value = value;
    }

    public <E> E get()
    {
        return (E) this.value;
    }

    public String getId()
    {
        return id;
    }
}
