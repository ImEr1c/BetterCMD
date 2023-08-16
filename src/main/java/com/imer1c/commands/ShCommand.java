package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;
import java.util.Scanner;

public class ShCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws Exception
    {
        String script = args.getParameter(0);

        File f = app.getMemoryManager().convert(script);

        Scanner sc = new Scanner(f);
        CommandManager commandManager = app.getCommandManager();

        while (sc.hasNextLine())
        {
            String line = sc.nextLine();

            commandManager.run(line, app);
        }

        sc.close();
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("Run scripts");
    }

    @Override
    public int requiredParametersCount()
    {
        return 1;
    }
}
