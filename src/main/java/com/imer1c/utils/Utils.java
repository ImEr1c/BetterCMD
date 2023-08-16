package com.imer1c.utils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static final FileSorter FILE_SORTER = new FileSorter();

    public static File[] listFiles(File f)
    {
        File[] files = f.listFiles();

        Arrays.sort(files, FILE_SORTER);

        return files;
    }

    public static List<String> splitAllStrings(List<String> list, FontMetrics metrics, int width)
    {
        List<String> strings = new ArrayList<>();

        for (String s : list)
        {
            String[] split = splitStrings(s, metrics, width);

            strings.addAll(Arrays.asList(split));
        }

        return strings;
    }

    public static String[] splitStrings(String s, FontMetrics metrics, int width)
    {
        if (metrics.stringWidth(s) < width)
        {
            return new String[]{s};
        }

        String[] splitInWords = s.split("\\s+");

        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < splitInWords.length; i++)
        {
            String word = splitInWords[i];

            if (metrics.stringWidth(line + " " + word) < width)
            {
                if (i > 0)
                {
                    line.append(" ");
                }

                line.append(word);
            }
            else
            {
                lines.add(line.toString());
                line = new StringBuilder(word);
            }
        }
        lines.add(line.toString());

        return lines.toArray(String[]::new);
    }
}
