package de.golgolex.cloudnet.simplifier.bootstraps.bukkit;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 14:36
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

import de.golgolex.cloudnet.simplifier.CloudSimplifier;
import de.golgolex.cloudnet.simplifier.commands.CloudSimplifierCommand;
import de.golgolex.cloudnet.simplifier.listener.bukkit.BukkitConnectionListener;
import org.bukkit.plugin.Plugin;

public class BukkitCloudSimplifier extends CloudSimplifier {

    private final Plugin plugin;

    public BukkitCloudSimplifier(Plugin plugin) {
        this.plugin = plugin;
        this.getPlugin().getServer().getPluginManager().registerEvents(new BukkitConnectionListener(), this.plugin);
        new CloudSimplifierCommand();
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
