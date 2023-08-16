package com.imer1c.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class LimitedList<E> extends LinkedList<E> {

    private final int limit;

    public LimitedList(int limit)
    {
        super();

        this.limit = limit;
    }

    public boolean isFull()
    {
        return this.size() == this.limit;
    }

    public int lastIndex()
    {
        return this.size() - 1;
    }

    @Override
    public boolean add(E e)
    {
        if (this.size() >= this.limit)
        {
            this.limitObjects();
        }

        return super.add(e);
    }

    @Override
    public void add(int index, E element)
    {
        if (this.size() >= this.limit)
        {
            this.limitObjects();
        }

        super.add(index, element);
    }

    private void limitObjects()
    {
        while (this.size() >= this.limit)
        {
            this.remove(0);
        }
    }
}
