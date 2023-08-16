package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;

public class PrintCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app)
    {
        app.write(formatArgs(args));
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("print <message> - Print the message formed of all possible parameters");
    }

    @Override
    public int requiredParametersCount()
    {
        return 0;
    }

    private String formatArgs(Arguments args)
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < args.parameters(); i++)
        {
            builder.append(args.getParameter(i)).append(" ");
        }

        return builder.toString();
    }
}
