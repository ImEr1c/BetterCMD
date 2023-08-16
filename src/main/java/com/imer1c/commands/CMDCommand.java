package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;
import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class CMDCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws Exception
    {
        String[] command = ArrayUtils.addAll(new String[]{"cmd.exe", "/c"}, formatParameters(args));

        boolean wait = args.contains("w");
        boolean redirect = !args.contains("p");
        boolean dir = args.contains("d");

        ProcessBuilder builder = new ProcessBuilder(command);

        if (redirect)
        {
            builder.redirectErrorStream(true);
        }

        if (dir)
        {
            builder.directory(app.getMemoryManager().convert(args.getFirstValue("d").get()));
        }

        Process p = builder.start();

        if (redirect)
        {
            InputStream inputStream = p.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();

            while (line != null)
            {
                app.write(line);
                line = reader.readLine();
            }
        }
        else if (wait)
        {
            p.waitFor();
        }

        if (p.isAlive() && wait)
        {
            p.waitFor();
        }
    }

    private String[] formatParameters(Arguments args)
    {
        String[] params = new String[args.parameters()];

        for (int i = 0; i < args.parameters(); i++)
        {
            params[i] = args.getParameter(i);
        }

        return params;
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("cmd <command> [-d <directory>] [-w] [-p] - Run a cmd command");
        cmd.write("");
        cmd.write("Parameters:");
        cmd.write("  <command> - The command");
        cmd.write("");
        cmd.write("Optional Arguments:");
        cmd.write("  -p - To not redirect output to the cmd process");
        cmd.write("  -d <directory> - Set the directory");
        cmd.write("  -w - Wait for the process to finish");
    }

    @Override
    public int requiredParametersCount()
    {
        return 1;
    }
}
