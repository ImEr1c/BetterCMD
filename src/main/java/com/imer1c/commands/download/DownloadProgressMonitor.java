package com.imer1c.commands.download;

public class DownloadProgressMonitor {
    private final int updateTime;
    private int downloaded;
    private int percentage;
    private int lines;

    public DownloadProgressMonitor(int total)
    {
        this.updateTime = total / 100;
    }

    public void update(int downloaded)
    {
        if (this.percentage < 100)
        {
            this.percentage++;
        }

        if (this.percentage % 5 == 0)
        {
            this.lines++;
        }

        this.downloaded += downloaded;
    }
    public int getLines()
    {
        return lines;
    }

    public int getPercentage()
    {
        return percentage;
    }

    public int getToRead()
    {
        return this.updateTime;
    }

    public int getDownloaded()
    {
        return downloaded;
    }
}
