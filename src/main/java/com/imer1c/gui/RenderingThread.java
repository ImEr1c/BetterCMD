package com.imer1c.gui;

import javax.swing.*;

public class RenderingThread extends Thread {
    private final JPanel panel;
    private boolean requested;

    public RenderingThread(JPanel panel)
    {
        this.panel = panel;
    }

    public void requestRepaint()
    {
        this.requested = true;
    }

    @Override
    public void run()
    {
        long paintInterval = 1000000000 / 60;
        long nextTime = System.nanoTime();

        while (panel.isVisible())
        {
            if (this.requested)
            {
                this.panel.repaint();
                this.requested = false;
            }

            long time = System.nanoTime();

            long left = Math.max(nextTime - time, 0);

            nextTime += paintInterval;

            try
            {
                Thread.sleep(left / 1000000);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
