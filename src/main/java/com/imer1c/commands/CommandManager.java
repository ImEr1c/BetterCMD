package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.commands.arguments.Arguments;
import com.imer1c.commands.download.DLCommand;
import com.imer1c.commands.http.HttpRequestCommand;
import com.imer1c.commands.jsoninfo.JsonInfoCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, ICommand> commands = new HashMap<>();
    private boolean commandRunning;

    public void init()
    {
        register("clear", new ClearCommand());
        register("cd", new CDCommand());
        register("dir", new DirCommand());
        register("rm", new RmCommand());
        register("copy", new CopyCommand());
        register("cdb", new CDBackCommand());
        register("wf", new WriteFileCommand());
        register("mkdir", new MkdirCommand());
        register("echo", new PrintCommand());
        register("dl", new DLCommand());
        register("rf", new ReadFileCommand());
        register("hreq", new HttpRequestCommand());
        register("ji", new JsonInfoCommand());
        register("exit", new ExitCommand());
        register("proc", new ProcessCommand());
        register("cmd", new CMDCommand());
        register("sh", new ShCommand());
    }

    private void register(String cmd, ICommand command)
    {
        this.commands.put(cmd, command);
    }

    public synchronized void run(String s, BetterCMDApplication app)
    {
        if (s.isEmpty() || this.isCommandRunning())
        {
            return;
        }

        String[] split = s.split(" ");

        String cmd = split[0];
        String args = s.substring(cmd.length());

        this.commandRunning = true;

        this.run(cmd, args, app);
    }

    private void run(String cmd, String args, BetterCMDApplication app)
    {
        ICommand command = this.commands.get(cmd);

        if (command == null)
        {
            this.commandRunning = false;
            return;
        }

        try
        {
            Arguments arguments = Arguments.parse(args);

            if (arguments.contains("h") && arguments.getFirstValue("h").get() == null)
            {
                command.printHelpPage(app);
            }
            else if (arguments.parameters() < command.requiredParametersCount())
            {
                app.write("Incorrect Syntax for command " + cmd + ", use the -h flag to show the correct syntax of that command");
            }
            else
            {
                command.execute(cmd, arguments, app);
            }
        }
        catch (Exception e)
        {
            app.write("  " + e.getMessage());

            StackTraceElement[] stackTrace = e.getStackTrace();

            for (StackTraceElement stackTraceElement : stackTrace)
            {
                app.write("    " + stackTraceElement.toString());
            }

            e.printStackTrace();
        }

        CMDMemoryManager memoryManager = app.getMemoryManager();

        if (!memoryManager.getLines().getLast().isEmpty())
        {
            app.write("");
        }

        this.commandRunning = false;

        app.getRenderingThread().requestRepaint();
    }

    public boolean isCommandRunning()
    {
        return commandRunning;
    }
}
