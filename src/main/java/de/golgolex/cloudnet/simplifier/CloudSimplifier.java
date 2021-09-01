package de.golgolex.cloudnet.simplifier;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 14:30
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

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.golgolex.cloudnet.simplifier.bootstraps.bukkit.BukkitCloudSimplifier;
import de.golgolex.cloudnet.simplifier.bootstraps.bungeecord.ProxiedCloudSimplifier;
import de.golgolex.cloudnet.simplifier.listener.cloud.CloudMessageListener;
import de.golgolex.cloudnet.simplifier.manager.cloudplayer.CloudPlayerManager;
import de.golgolex.cloudnet.simplifier.manager.permission.CloudPermissionManager;
import de.golgolex.cloudnet.simplifier.manager.proxy.CloudProxyManager;
import de.golgolex.cloudnet.simplifier.manager.server.CloudServerManager;
import de.golgolex.cloudnet.simplifier.objectives.claydriveruser.SimplifierUserCache;
import de.golgolex.cloudnet.simplifier.objectives.message.MessagingBuilder;

public abstract class CloudSimplifier {

    private static volatile CloudSimplifier simplifier;
    private final String cloudPrefix;
    private final CloudPlayerManager cloudPlayerManager;
    private final SimplifierUserCache clayDriverUsers;
    private final CloudPermissionManager cloudPermissionManager;

    public CloudSimplifier() {
        CloudSimplifier.simplifier = this;
        this.cloudPrefix = "§8[§eCloud§8] §7";
        this.cloudPlayerManager = new CloudPlayerManager(CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class));
        this.clayDriverUsers = new SimplifierUserCache();
        this.cloudPermissionManager = new CloudPermissionManager();
        this.registerCloudListener(new CloudMessageListener());
    }

    public CloudNetDriver getCloudNetAPI() {
        return CloudNetDriver.getInstance();
    }

    public static CloudSimplifier getSimplifier() {
        return simplifier;
    }

    public void registerCloudListener(Object object) {
        CloudNetDriver.getInstance().getEventManager().registerListener(object);
    }

    public void registerCloudListener(Object... object) {
        CloudNetDriver.getInstance().getEventManager().registerListeners(object);
    }

    public CloudPermissionManager getCloudPermissionManager() {
        return cloudPermissionManager;
    }

    public CloudServerManager getCloudServerManager() {
        return new CloudServerManager();
    }

    public CloudProxyManager getCloudProxyManager() {
        return new CloudProxyManager();
    }

    public SimplifierUserCache getSimplifierUsers() {
        return clayDriverUsers;
    }

    public String getCloudPrefix() {
        return cloudPrefix;
    }

    public MessagingBuilder createChannelMessaging() {
        return new MessagingBuilder();
    }

    public CloudPlayerManager getCloudPlayerManager() {
        return cloudPlayerManager;
    }

    public BukkitCloudSimplifier getBukkitCloudSimplifier() {
        return (BukkitCloudSimplifier) CloudSimplifier.simplifier;
    }

    public ProxiedCloudSimplifier getProxiedCloudSimplifier() {
        return (ProxiedCloudSimplifier) CloudSimplifier.simplifier;
    }
}
