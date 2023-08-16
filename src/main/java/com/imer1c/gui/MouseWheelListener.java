package com.imer1c.gui;

import com.imer1c.BetterCMDApplication;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {

    private final CmdPanel panel;
    private final BetterCMDApplication app;

    public MouseWheelListener(CmdPanel panel, BetterCMDApplication app)
    {
        this.panel = panel;
        this.app = app;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        int offset = this.panel.getOffset();

        if (e.getUnitsToScroll() < 0)
        {
            this.panel.setOffset(offset + 1);
        }
        else if (offset > 0)
        {
            this.panel.setOffset(offset - 1);
        }

        this.app.getRenderingThread().requestRepaint();
    }
}
