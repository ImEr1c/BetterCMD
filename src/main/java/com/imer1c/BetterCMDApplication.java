package com.imer1c;

import com.imer1c.commands.CommandManager;
import com.imer1c.gui.CmdPanel;
import com.imer1c.gui.MouseWheelListener;
import com.imer1c.gui.RenderingThread;

import javax.swing.*;

public class BetterCMDApplication {
    private static BetterCMDApplication instance;

    public static void init()
    {
        instance = new BetterCMDApplication();
    }

    public static BetterCMDApplication getInstance()
    {
        return instance;
    }

    private final CMDMemoryManager memoryManager;
    private final RenderingThread renderingThread;
    private final CommandManager commandManager;
    private final JFrame frame;

    public BetterCMDApplication()
    {
        this.commandManager = new CommandManager();
        this.commandManager.init();

        this.memoryManager = new CMDMemoryManager();
        this.frame = this.initFrame();

        this.renderingThread = new RenderingThread((JPanel) this.frame.getContentPane());
        this.renderingThread.start();
    }

    public void write(String s)
    {
        this.memoryManager.write(s);
        this.renderingThread.requestRepaint();
    }

    public void modifyLastLine(String s)
    {
        this.memoryManager.modifyLastLine(s);
        this.renderingThread.requestRepaint();
    }

    public void run(String s)
    {
        this.commandManager.run(s, this);
    }

    public boolean isCommandRunning()
    {
        return this.commandManager.isCommandRunning();
    }

    public CommandManager getCommandManager()
    {
        return commandManager;
    }

    public RenderingThread getRenderingThread()
    {
        return renderingThread;
    }

    public CMDMemoryManager getMemoryManager()
    {
        return memoryManager;
    }

    private JFrame initFrame()
    {
        JFrame frame = new JFrame();
        CmdPanel panel = new CmdPanel(this);
        frame.setContentPane(panel);
        frame.setFocusTraversalKeysEnabled(false);
        frame.addKeyListener(panel.getKeyListener());
        frame.addMouseWheelListener(new MouseWheelListener(panel, this));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }

    public JFrame getFrame()
    {
        return frame;
    }
}
