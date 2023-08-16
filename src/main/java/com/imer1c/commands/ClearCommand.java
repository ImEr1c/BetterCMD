package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.commands.arguments.Arguments;

public class ClearCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app)
    {
        CMDMemoryManager memoryManager = app.getMemoryManager();

        memoryManager.clearList();
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("clear - Clear the screen");
    }

    @Override
    public int requiredParametersCount()
    {
        return 0;
    }
}
