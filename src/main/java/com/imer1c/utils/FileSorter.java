package com.imer1c.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileSorter implements Comparator<File> {
    @Override
    public int compare(File o1, File o2)
    {
        if (o1.isDirectory() && !o2.isDirectory())
        {
            return -1;
        }
        else if (!o1.isDirectory() && o2.isDirectory())
        {
            return 1;
        }
        else
        {
            String name1 = o1.getName();
            String name2 = o2.getName();

            String num1 = extractFinalNumbers(name1);
            String num2 = extractFinalNumbers(name2);

            if (num1 != null && num2 != null)
            {
                name1 = name1.replace(num1, "");
                name2 = name2.replace(num2, "");
            }

            int nameCompare = name1.compareTo(name2);

            if (num1 != null && num2 != null && nameCompare == 0)
            {
                int i1 = Integer.parseInt(num1);
                int i2 = Integer.parseInt(num2);

                return Integer.compare(i1, i2);
            }

            return nameCompare;
        }
    }

    private String extractFinalNumbers(String s)
    {
        char[] chars = s.toCharArray();

        int n = chars.length - 1;

        for (int i = chars.length - 1; i >= 0; i--)
        {
            if (Character.isDigit(s.charAt(i)))
            {
                n--;
            }
            else
            {
                break;
            }
        }

        if (n == chars.length - 1)
        {
            return null;
        }

        return s.substring(n + 1);
    }
}
