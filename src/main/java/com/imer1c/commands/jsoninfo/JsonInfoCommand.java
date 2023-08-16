package com.imer1c.commands.jsoninfo;

import com.google.gson.Gson;
import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.ICommand;
import com.imer1c.commands.arguments.Arguments;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonInfoCommand implements ICommand {

    private static final Gson GSON = new Gson();

    private int currentWriting;
    private List<JsonObject> objects;

    @Override
    public void execute(String cmd, Arguments args, BetterCMDApplication app) throws Exception
    {
        String json = args.getParameter(0);

        if (!json.startsWith("{"))
        {
            FileInputStream inputStream = new FileInputStream(json);
            json = new String(inputStream.readAllBytes());
            inputStream.close();
        }

        this.objects = new ArrayList<>();
        this.objects.add(new JsonObject("Main"));

        Map<String, Object> map = GSON.fromJson(json, Map.class);

        map.forEach(this::recursive);

        this.objects.forEach(jsonObject -> {
            List<Pair<String, String>> variables = jsonObject.getVariables();

            app.write(jsonObject.getName());
            app.write("");

            variables.forEach(pair -> app.write(pair.getLeft() + ": " + pair.getRight()));

            app.write("");
            app.write("");
        });
    }

    private void recursive(Object s, Object o)
    {
        if (o == null)
        {
            return;
        }

        String type = o.getClass().getName();

        if (o instanceof Map map)
        {
            type = (String) s;

            int writingIndex = this.currentWriting;

            JsonObject e = new JsonObject((String) s);

            if (!this.objects.contains(e))
            {
                this.objects.add(e);
            }

            this.currentWriting = this.objects.size() - 1;
            map.forEach(this::recursive);

            this.currentWriting = writingIndex;
        }

        s = StringUtils.capitalize((String) s);

        this.objects.get(this.currentWriting).addVariable((String) s, type);
    }

    @Override
    public void printHelpPage(BetterCMDApplication cmd)
    {

    }

    @Override
    public int requiredParametersCount()
    {
        return 1;
    }
}
