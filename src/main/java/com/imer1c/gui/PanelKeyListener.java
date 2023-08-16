package com.imer1c.gui;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class PanelKeyListener implements KeyListener {

    private final BetterCMDApplication cmd;
    private final CmdPanel panel;
    private String lastFileSearch;
    private boolean ctrl;

    public PanelKeyListener(CmdPanel panel, BetterCMDApplication cmd)
    {
        this.panel = panel;
        this.cmd = cmd;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        this.processKey(e.getKeyChar(), e.getKeyChar());

        RenderingThread renderingThread = this.cmd.getRenderingThread();
        renderingThread.requestRepaint();
    }

    private void processKey(char c, int key)
    {
        StringBuilder currentLine = this.panel.getCurrentLine();

        if (key == 8)
        {
            if (!currentLine.isEmpty())
            {
                currentLine.deleteCharAt(currentLine.length() - 1);
            }
        }
        else if (key == 10)
        {
            Thread lineThread = new Thread(this::newLine);
            lineThread.start();
        }
        else if (!this.ctrl && key != 9)
        {
            currentLine.append(c);
        }
    }

    public void newLine()
    {
        StringBuilder currentLine = this.panel.getCurrentLine();

        CMDMemoryManager memoryManager = this.cmd.getMemoryManager();

        if (currentLine != null)
        {
            String line = currentLine.toString();

            this.cmd.write(memoryManager.getCurrentDirectory().getAbsolutePath() + ">" + currentLine);

            if (!currentLine.isEmpty())
            {
                memoryManager.rememberCommand(line);
            }

            this.cmd.run(line);
        }

        memoryManager.resetIndexes();

        this.panel.setCurrentLine(new StringBuilder());
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        CMDMemoryManager memoryManager = this.cmd.getMemoryManager();

        int keyCode = e.getKeyCode();

        if (keyCode == 17)
        {
            this.ctrl = true;
        }
        else if (keyCode == 9)
        {
            StringBuilder line = this.panel.getCurrentLine();
            String s = this.lastWord(line.toString());
            boolean b = memoryManager.containsFileWithName(s);
            File nextFile = memoryManager.nextFile(b ? this.lastFileSearch : s);

            if (!b)
            {
                this.lastFileSearch = s;
            }

            if (!line.isEmpty())
            {
                int i = line.lastIndexOf(" ");

                line.delete(i == -1 ? 0 : i, line.length());
            }

            if (nextFile == null)
            {
                return;
            }

            line.append(nextFile.getName());
        }
        else if (keyCode == 38)
        {
            RenderingThread renderingThread = this.cmd.getRenderingThread();
            String str = memoryManager.olderCommand();

            if (str == null)
            {
                return;
            }

            StringBuilder line = new StringBuilder(str);

            this.panel.setCurrentLine(line);

            renderingThread.requestRepaint();
        }
        else if (keyCode == 40)
        {
            RenderingThread renderingThread = this.cmd.getRenderingThread();
            String str = memoryManager.newerCommand();

            if (str == null)
            {
                return;
            }

            StringBuilder line = new StringBuilder(str);

            this.panel.setCurrentLine(line);

            renderingThread.requestRepaint();
        }
        else if (this.ctrl && keyCode == 86)
        {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
            {
                return;
            }

            try
            {
                Object data = clipboard.getData(DataFlavor.stringFlavor);

                StringBuilder currentLine = this.panel.getCurrentLine();
                currentLine.append(data);
            }
            catch (UnsupportedFlavorException | IOException ex)
            {
                throw new RuntimeException(ex);
            }
        }
        else if (this.ctrl && keyCode == 8)
        {
            StringBuilder currentLine = this.panel.getCurrentLine();
            int i = currentLine.lastIndexOf(" ");

            currentLine.delete(i == -1 ? 0 : i, currentLine.length());
        }
    }

    private String lastWord(String s)
    {
        int beginIndex = s.lastIndexOf(' ');
        return s.substring(beginIndex == -1 ? 0 : beginIndex);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == 17)
        {
            this.ctrl = false;
        }
    }
}
