package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws IOException
    {
        CMDMemoryManager memoryManager = app.getMemoryManager();

        File from = memoryManager.convert(args.getParameter(0));
        File to = memoryManager.convert(args.getParameter(1));

        this.copyDirectory(from, to, app);
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("copy <from> <to> - Copy files from a directory to another (or files)");
        cmd.write("");
        cmd.write("Parameters:");
        cmd.write("  <from> - The directory (or file) to copy from");
        cmd.write("  <to> - The directory (or file) to copy to");
    }

    @Override
    public int requiredParametersCount()
    {
        return 2;
    }

    private void copyDirectory(File from, File to, BetterCMDApplication app) throws IOException
    {
        app.write(from.getAbsolutePath());
        app.write(to.getAbsolutePath());

        Files.walkFileTree(from.toPath(), new FileVisitor(app, from, to));
    }

    private static class FileVisitor extends SimpleFileVisitor<Path> {

        private final BetterCMDApplication app;
        private final String from;
        private final Path to;

        private FileVisitor(BetterCMDApplication app, File from, File to)
        {
            this.app = app;
            this.from = from.getAbsolutePath();
            this.to = to.toPath();
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
        {
            Path dest = Paths.get(this.to.toString(), dir.toString().substring(this.from.length()));

            this.app.write(dir + " -> " + dest);

            dest.toFile().mkdirs();

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
        {
            Path dest = Paths.get(this.to.toString(), file.toString().substring(this.from.length()));

            this.app.write(file + " -> " + dest);

            Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);

            return super.visitFile(file, attrs);
        }
    }
}
