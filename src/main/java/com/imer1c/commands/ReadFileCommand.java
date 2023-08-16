package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;
import com.imer1c.commands.http.HttpUtil;

import java.io.*;
import java.util.Scanner;

public class ReadFileCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws IOException
    {
        File f = app.getMemoryManager().convert(args.getParameter(0));

        if (!f.exists())
        {
            app.write("File doesn't exist");
            return;
        }

        if (f.isDirectory())
        {
            app.write("Cannot read a directory");
            return;
        }

        app.write("File: " + f.getName());
        app.write("");

        if (args.contains("f"))
        {
            FileInputStream inputStream = new FileInputStream(f);

            String s = new String(inputStream.readAllBytes());

            HttpUtil.printFormattedJson(s, app);
            return;
        }

        Scanner scanner = new Scanner(f);

        while (scanner.hasNextLine())
        {
            app.write(scanner.nextLine());
        }

        scanner.close();
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("rf <name> - Read and display a file's content");
        cmd.write("");
        cmd.write("Parameters: <name> - The file's name or path to read from");
        cmd.write("");
        cmd.write("Arguments: ");
        cmd.write("  -f - Format json data");
    }

    @Override
    public int requiredParametersCount()
    {
        return 1;
    }
}
