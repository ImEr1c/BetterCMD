package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.commands.arguments.Argument;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;

public class MkdirCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app)
    {
        CMDMemoryManager memoryManager = app.getMemoryManager();

        File dir = memoryManager.convert(args.getParameter(0));

        dir.mkdirs();
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("mkdir <dirName> - Make a directory with the specified name");
        cmd.write("");
        cmd.write("Parameters: <dirName> - The new directory name");
    }

    @Override
    public int requiredParametersCount()
    {
        return 1;
    }
}
