package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;

public class ExitCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws Exception
    {
        System.exit(0);
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("exit - Exit the Cmd");
    }

    @Override
    public int requiredParametersCount()
    {
        return 0;
    }
}
