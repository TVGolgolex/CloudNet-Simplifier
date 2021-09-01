package de.golgolex.cloudnet.simplifier.commands;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 14:41
# Class created by: Pascal
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation 
# files (the "Software"),
# to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, 
# distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software 
# is furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
# INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE 
# AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
#  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
# 
===========================================================================================================================
*/

import de.dytanic.cloudnet.common.document.gson.JsonDocument;
import de.dytanic.cloudnet.driver.channel.ChannelMessageTarget;
import de.golgolex.cloudnet.simplifier.CloudSimplifier;
import de.golgolex.cloudnet.simplifier.objectives.user.ISimplifierUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CloudSimplifierCommand implements CommandExecutor, TabCompleter {

    public CloudSimplifierCommand() {
        PluginCommand command = Bukkit.getPluginCommand("cloudsimplifier");
        command.setAliases(Collections.singletonList("clouds"));
        command.setTabCompleter(this);
        command.setExecutor(this);
        command.setPermission("de.golgolex.cloudnet.simplifier");
        command.setDescription("Manage the cloud");
    }

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender commandSender, Command command, String s, String[] strings) {
        final ISimplifierUser iSimplifierUser = CloudSimplifier.getSimplifier().getSimplifierUsers().getIClayDriverUser(commandSender.getName());

        command.testPermission(commandSender);

        if (strings.length == 0) {
            sendUsage(iSimplifierUser);
            return true;
        }

        if (strings[0].equalsIgnoreCase("maintenance")) {

            if (!(strings[1].equalsIgnoreCase("true") || strings[1].equalsIgnoreCase("false"))) {
                iSimplifierUser.sendChatMessage(CloudSimplifier.getSimplifier().getCloudPrefix() + "Please use true or false");
                return true;
            }

            iSimplifierUser.sendChatMessage(CloudSimplifier.getSimplifier().getCloudPrefix() + "Maintenance will be changed...");
            CloudSimplifier.getSimplifier().createChannelMessaging()
                    .channel("claycloud_api")
                    .message("updateMaintenance")
                    .jsonDocument(JsonDocument.newDocument().append("state", Boolean.parseBoolean(strings[1])))
                    .channelMessageTarget(ChannelMessageTarget.Type.GROUP)
                    .sendTo("Proxy")
                    .send();
            iSimplifierUser.sendChatMessage(CloudSimplifier.getSimplifier().getCloudPrefix() + "Maintenance was sent do system...");

            return true;
        }

        if (strings[0].equalsIgnoreCase("setSlots")) {
            if (!this.checkIsNumber(strings[1])) {
                iSimplifierUser.sendChatMessage(CloudSimplifier.getSimplifier().getCloudPrefix() + "§7Please enter a number");
                return false;
            }

            iSimplifierUser.sendChatMessage(CloudSimplifier.getSimplifier().getCloudPrefix() + "Slots will be changed...");
            CloudSimplifier.getSimplifier().createChannelMessaging()
                    .channel("claycloud_api")
                    .message("updateSlots")
                    .jsonDocument(JsonDocument.newDocument().append("slots", Integer.parseInt(strings[1])))
                    .channelMessageTarget(ChannelMessageTarget.Type.GROUP)
                    .sendTo("Proxy")
                    .send();
            iSimplifierUser.sendChatMessage(CloudSimplifier.getSimplifier().getCloudPrefix() + "Slots was updated.");
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(org.bukkit.command.CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("maintenance", "setSlots");
        } else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("maintenance")) {
                return Arrays.asList("true", "false");
            } else if (strings[0].equalsIgnoreCase("setSlots")) {
                int random = ThreadLocalRandom. current(). nextInt(10, 500 + 1);
                return Collections.singletonList(String.valueOf(random));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private void sendUsage(ISimplifierUser iSimplifierUser) {
        final PluginCommand pluginCommand = CloudSimplifier.getSimplifier().getBukkitCloudSimplifier()
                .getPlugin().getServer().getPluginCommand("cloudsimplifier");

        iSimplifierUser.sendChatMessages(
                CloudSimplifier.getSimplifier().getCloudPrefix() + "",
                CloudSimplifier.getSimplifier().getCloudPrefix() + "§e/" + pluginCommand.getName() +" maintenance <true/false>",
                CloudSimplifier.getSimplifier().getCloudPrefix() + "§e/" + pluginCommand.getName() + " setSlots <Slots>",
                CloudSimplifier.getSimplifier().getCloudPrefix() + "",
                CloudSimplifier.getSimplifier().getCloudPrefix() + "§7Permission§8: " + pluginCommand.getPermission(),
                CloudSimplifier.getSimplifier().getCloudPrefix() + "§7Aliases§8: " + pluginCommand.getAliases().stream(),
                CloudSimplifier.getSimplifier().getCloudPrefix() + ""
        );
    }

    private boolean checkIsNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
