package com.imer1c;

import com.imer1c.utils.LimitedList;
import com.imer1c.utils.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class CMDMemoryManager {
    private final LimitedList<String> lines = new LimitedList<>(100);
    private final LimitedList<String> lastCommands = new LimitedList<>(10);
    private File[] files;
    private int lastCommandIndex, filesIndex;
    private File currentDirectory;

    public CMDMemoryManager()
    {
        this.currentDirectory = new File(System.getProperty("user.dir"));

        this.write("Better CMD by ImEr1c");
        this.write("");
    }

    public void clearList()
    {
        this.lines.clear();
    }

    public void write(String s)
    {
        this.lines.add(s);
    }

    public void rememberCommand(String s)
    {
        this.lastCommands.add(s);
    }

    public void changeDirectory(File currentDirectory)
    {
        this.currentDirectory = currentDirectory;
    }

    public File getCurrentDirectory()
    {
        return currentDirectory;
    }

    public LimitedList<String> getLines()
    {
        return lines;
    }

    public String olderCommand()
    {
        if (this.checkLastCmdsIndex(this.lastCommandIndex - 1))
        {
            return null;
        }

        this.lastCommandIndex--;

        return this.lastCommands.get(this.lastCommandIndex);
    }

    public String newerCommand()
    {
        if (this.checkLastCmdsIndex(this.lastCommandIndex + 1))
        {
            return null;
        }

        this.lastCommandIndex++;

        return this.lastCommands.get(this.lastCommandIndex);
    }

    private boolean checkLastCmdsIndex(int i)
    {
        return i >= this.lastCommands.size() || i < 0;
    }

    public File currentFile()
    {
        if (this.files == null)
        {
            return null;
        }

        return this.files[this.filesIndex];
    }

    public File nextFile(String s)
    {
        if (this.files == null)
        {
            return null;
        }

        int fi = this.filesIndex;

        do
        {
            if (this.filesIndex + 1 >= this.files.length)
            {
                this.filesIndex = 0;
            }
            else
            {
                this.filesIndex++;
            }

            if (this.filesIndex == fi)
            {
                return null;
            }
        } while (s != null && !this.files[this.filesIndex].getName().contains(s));

        return this.files[this.filesIndex];
    }

    public boolean containsFileWithName(String s)
    {
        for (File file : this.files)
        {
            if (file.getName().equals(s))
            {
                return true;
            }
        }

        return false;
    }

    public void resetIndexes()
    {
        this.lastCommandIndex = this.lastCommands.size();
        this.files = Utils.listFiles(this.currentDirectory);
        this.filesIndex = 0;
    }

    public File convert(String s)
    {
        File f = new File(s);

        if (!f.isAbsolute())
        {
            return new File(this.currentDirectory, s);
        }

        return f;
    }

    public void modifyLastLine(String s)
    {
        int i = this.lines.lastIndex();

        this.lines.set(i, s);
    }
}
