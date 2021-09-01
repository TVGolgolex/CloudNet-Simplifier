package de.golgolex.cloudnet.simplifier.listener.bungeecord;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 16:23
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
import de.golgolex.cloudnet.simplifier.objectives.claydriveruser.ISimplifierUser;
import de.golgolex.cloudnet.simplifier.objectives.claydriveruser.SimplifierUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeConnectionListener implements Listener {

    @EventHandler
    public void handle(PostLoginEvent postLoginEvent) {
        final ProxiedPlayer proxiedPlayer = postLoginEvent.getPlayer();
        CloudSimplifier.getSimplifier().getSimplifierUsers().registerClayDriverUser(
                new SimplifierUser(proxiedPlayer.getName(), proxiedPlayer.getUniqueId()));
    }

    @EventHandler
    public void handle(PlayerDisconnectEvent playerDisconnectEvent) {
        final ProxiedPlayer proxiedPlayer = playerDisconnectEvent.getPlayer();
        final ISimplifierUser iSimplifierUser = CloudSimplifier.getSimplifier().getSimplifierUsers().getIClayDriverUser(proxiedPlayer.getName());
        CloudSimplifier.getSimplifier().getSimplifierUsers().unregisterClayDriverUser(iSimplifierUser);
    }
}
