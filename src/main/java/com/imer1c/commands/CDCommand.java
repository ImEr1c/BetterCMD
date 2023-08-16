package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;

public class CDCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app)
    {
        CMDMemoryManager memoryManager = app.getMemoryManager();

        File dir = memoryManager.convert(args.getParameter(0));

        if (!dir.exists())
        {
            app.write("Can't open " + dir.getPath());
            return;
        }

        memoryManager.changeDirectory(dir);
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("cd <dir> - Change directory to a specific directory");
        cmd.write("");
        cmd.write("Parameters: dir - The directory that you want to change to");
    }

    @Override
    public int requiredParametersCount()
    {
        return 1;
    }
}
