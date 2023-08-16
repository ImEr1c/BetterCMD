package com.imer1c.commands.http;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.ICommand;
import com.imer1c.commands.arguments.Argument;
import com.imer1c.commands.arguments.Arguments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestCommand implements ICommand {
    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws IOException
    {
        String method = args.getParameter(0);
        String link = args.getParameter(1);

        List<Argument<?>> properties = args.getValues("p");

        Map<String, String> httpProperties = new HashMap<>();

        if (properties != null)
        {
            for (Argument<?> property : properties)
            {
                String s = property.get();

                String[] split = s.split("=");

                httpProperties.put(split[0], split[1]);
            }
        }

        String json = null;
        if (args.contains("j"))
        {
            json = args.getFirstValue("j").get();

            if (!json.startsWith("{"))
            {
                File f = new File(json);

                if (!f.exists())
                {
                    app.write("Json data not valid and file not found with name " + json);
                    return;
                }

                FileInputStream inputStream = new FileInputStream(f);
                json = new String(inputStream.readAllBytes());
                inputStream.close();
            }
        }

        if (method.equalsIgnoreCase("GET"))
        {
            HttpUtil.sendHttpGetRequest(link, httpProperties, app, args.contains("f"));
        }
        else if (method.equalsIgnoreCase("POST"))
        {
            HttpUtil.sendHttpPostRequest(link, httpProperties, json, app, args.contains("f"));
        }
        else
        {
            app.write("Unsupported Http Request method: " + method);
        }
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {
        cmd.write("hreq <method> <link> [-p <property>] [-j <jsonData>] - Send a HTTP Request to a server and receive the response along with the response code");
        cmd.write("");
        cmd.write("Parameters:");
        cmd.write("  <method> - The HTTP Request method to send the request (GET or POST)");
        cmd.write("  <link> - The link to send the HTTP Request to");
        cmd.write("");
        cmd.write("Optional Arguments:");
        cmd.write("  -p - The properties (or headers) for the request, must be typed in this format: name=val");
        cmd.write("  -j - The json data (or file name) for the POST request to get the json info to send");
        cmd.write("  -f - Format the json response");
    }

    @Override
    public int requiredParametersCount()
    {
        return 2;
    }
}
