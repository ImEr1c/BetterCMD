package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;

public class CDBackCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app)
    {
        File currentDir = app.getMemoryManager().getCurrentDirectory();

        File parentFile = currentDir.getParentFile();

        app.getMemoryManager().changeDirectory(parentFile);
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("cdb - Change directory to the parent directory");
    }

    @Override
    public int requiredParametersCount()
    {
        return 0;
    }
}
