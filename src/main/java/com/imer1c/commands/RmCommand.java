package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.commands.arguments.Argument;
import com.imer1c.commands.arguments.Arguments;
import com.imer1c.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class RmCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws IOException
    {
        CMDMemoryManager memoryManager = app.getMemoryManager();

        File f = memoryManager.convert(args.getParameter(0));

        Files.walkFileTree(f.toPath(), new FileVisitor(app));
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("rm <name> - Remove a file or directory with the specified path");
        cmd.write("");
        cmd.write("Parameters: <name> - The name or path of the file or directory you want removed");
    }

    @Override
    public int requiredParametersCount()
    {
        return 1;
    }

    private static class FileVisitor extends SimpleFileVisitor<Path> {
        private final BetterCMDApplication app;

        private FileVisitor(BetterCMDApplication app)
        {
            this.app = app;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
        {
            this.app.write("rm " + dir);
            File file = dir.toFile();
            file.delete();

            return super.postVisitDirectory(dir, exc);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
        {
            this.app.write("rm " + file);
            File f = file.toFile();
            f.delete();

            return super.visitFile(file, attrs);
        }
    }
}
