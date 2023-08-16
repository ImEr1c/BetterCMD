package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.commands.arguments.Argument;
import com.imer1c.commands.arguments.Arguments;
import com.imer1c.utils.FileSorter;
import com.imer1c.utils.Utils;

import java.io.File;
import java.util.Arrays;

public class DirCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app)
    {
        CMDMemoryManager memoryManager = app.getMemoryManager();

        File dir = args.parameters() == 0? memoryManager.getCurrentDirectory() : memoryManager.convert(args.getParameter(0));

        File[] files = Utils.listFiles(dir);

        if (files == null)
        {
            app.write("Can't open " + dir.getPath());
            return;
        }

        for (File file : files)
        {
            app.write(file.getName());
        }
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("dir [directory] - Show all files and folders available in a directory");
        cmd.write("");
        cmd.write("Parameters: [directory] - optional for specifying a directory to show the files and folders from");
    }

    @Override
    public int requiredParametersCount()
    {
        return 0;
    }
}
