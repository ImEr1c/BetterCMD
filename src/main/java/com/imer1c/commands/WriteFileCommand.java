package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFileCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws IOException
    {
        File f = app.getMemoryManager().convert(args.getParameter(0));

        boolean over = !args.contains("over");
        FileWriter writer = new FileWriter(f, over);

        if (over)
        {
            writer.append("\n");
        }

        String text = this.formatArgs(args);

        writer.write(text);
        writer.close();
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        System.out.println("Mhm");
        cmd.write("wf <fileName> <content> [-over] - Write a file with the specific name or path and content");
        cmd.write("Parameters:");
        cmd.write("  <fileName> - The file's name or path");
        cmd.write("  <content> - The content to be written");
        cmd.write("");
        cmd.write("Arguments:");
        cmd.write("  -over - Use this argument to delete all data written to the file before writing");
    }

    @Override
    public int requiredParametersCount()
    {
        return 2;
    }

    private String formatArgs(Arguments args)
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < args.parameters(); i++)
        {
            builder.append(args.getParameter(i)).append(" ");
        }

        return builder.toString();
    }
}
