package com.imer1c.commands.download;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.ICommand;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DLCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments arguments, BetterCMDApplication app) throws IOException
    {
        String link = arguments.getParameter(0);
        String fileName = arguments.getParameter(1);
        File file = new File(fileName);

        this.download(link, file, app);
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("dl <link> <fileName> - Download a file from the internet");
        cmd.write("");
        cmd.write("Parameters: ");
        cmd.write("  <link> - The link to download from");
        cmd.write("  <fileName> - The file name to output to");
    }

    @Override
    public int requiredParametersCount()
    {
        return 2;
    }

    private void download(String link, File file, BetterCMDApplication app) throws IOException
    {
        URL url = new URL(link);
        URLConnection urlConnection = url.openConnection();
        int contentLength = urlConnection.getContentLength();

        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(file);

        DownloadProgressMonitor progressMonitor = new DownloadProgressMonitor(contentLength);

        int toRead = progressMonitor.getToRead();

        app.write(this.updateLine(0, 0, file.getName(), "", 0));

        long last = System.nanoTime();
        int downloadedPerSecond = 0;
        String speed = "";

        while (true)
        {
            byte[] read = inputStream.readNBytes(toRead);

            if (read.length == 0)
            {
                break;
            }

            outputStream.write(read);

            progressMonitor.update(read.length);

            int percentage = progressMonitor.getPercentage();
            int lines = progressMonitor.getLines();

            String line = this.updateLine(lines, percentage, file.getName(), speed, progressMonitor.getDownloaded());

            app.modifyLastLine(line);

            downloadedPerSecond += read.length;

            if (System.nanoTime() - last >= 1000000000)
            {
                speed = downloadedPerSecond / 1000000 + "mb/s";
                downloadedPerSecond = 0;
                last = System.nanoTime();
            }

        }

        inputStream.close();
        outputStream.close();

        System.out.println("Finish");
    }

    private String updateLine(int lines, int percentage, String speed, String fileName, int totalDownloaded)
    {
        return String.format("%s [%s>%s] %s %s Total: %s", percentage + "%", "=".repeat(lines), " ".repeat(40 - Math.min(40, lines * 2)), fileName, speed, totalDownloaded);
    }
}
