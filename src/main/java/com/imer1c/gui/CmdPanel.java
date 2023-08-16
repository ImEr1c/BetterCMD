package com.imer1c.gui;

import com.imer1c.BetterCMDApplication;
import com.imer1c.CMDMemoryManager;
import com.imer1c.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdPanel extends JPanel {

    private static final int FONT_HEIGHT = 15;
    private static final int SKIP_HEIGHT = 20;

    private final BetterCMDApplication cmd;
    private final PanelKeyListener keyListener;
    private StringBuilder currentLine;
    private int offset;

    public CmdPanel(BetterCMDApplication cmd)
    {
        this.cmd = cmd;
        this.keyListener = new PanelKeyListener(this, cmd);
        Dimension dimension = new Dimension(1280, 720);

        this.setPreferredSize(dimension);
        this.setSize(dimension);

        this.keyListener.newLine();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        CMDMemoryManager memoryManager = this.cmd.getMemoryManager();

        g.setColor(Color.BLACK);
        int width = this.getWidth();
        g.fillRect(0, 0, width, this.getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri (Body)", Font.PLAIN, FONT_HEIGHT));

        FontMetrics fontMetrics = g.getFontMetrics();
        List<String> list = Utils.splitAllStrings(memoryManager.getLines(), fontMetrics, width);

        String dir = memoryManager.getCurrentDirectory().getAbsolutePath() + ">";

        String line = dir + this.currentLine.toString();
        String[] lines = Utils.splitStrings(line, fontMetrics, width);
        int reserveHeight = lines.length * SKIP_HEIGHT;
        int i;

        if (SKIP_HEIGHT * (list.size() + 1) < this.getHeight())
        {
            list.addAll(Arrays.asList(lines));

            i = SKIP_HEIGHT;

            for (String s : list)
            {
                g.drawString(s, 0, i);

                i += SKIP_HEIGHT;

                if (i >= this.getHeight() - reserveHeight)
                {
                    break;
                }
            }
        }
        else
        {
            i = this.getHeight() - SKIP_HEIGHT;

            int offset = Math.min(this.offset, this.maximumScrollOffset(list.size()));
            this.offset = offset;

            list.addAll(list.size() - 1, Arrays.asList(lines));

            for (int j = list.size() - 1 - offset; j >= 0; j--)
            {
                g.drawString(list.get(j), 0, i);

                i -= SKIP_HEIGHT;

                if (i <= 0)
                {
                    break;
                }
            }
        }
    }

    private int maximumScrollOffset(int size)
    {
        return Math.abs((this.getHeight() - size * SKIP_HEIGHT) / SKIP_HEIGHT);
    }

    public StringBuilder getCurrentLine()
    {
        return currentLine;
    }

    public void setCurrentLine(StringBuilder currentLine)
    {
        this.currentLine = currentLine;
    }

    public PanelKeyListener getKeyListener()
    {
        return keyListener;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }
}
