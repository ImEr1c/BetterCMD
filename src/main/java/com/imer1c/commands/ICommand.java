package com.imer1c.commands;

import com.imer1c.BetterCMDApplication;
import com.imer1c.commands.arguments.Arguments;

public interface ICommand {
    void execute(String cmd, Arguments args, BetterCMDApplication app) throws Exception;

    void printHelpPage(BetterCMDApplication cmd);

    int requiredParametersCount();
}
