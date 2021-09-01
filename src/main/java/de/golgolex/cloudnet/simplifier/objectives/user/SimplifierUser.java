package de.golgolex.cloudnet.simplifier.objectives.user;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 15:34
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

import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.PermissionCheckResult;
import de.dytanic.cloudnet.ext.bridge.player.ICloudOfflinePlayer;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.golgolex.cloudnet.simplifier.CloudSimplifier;

import java.util.UUID;

public class SimplifierUser implements ISimplifierUser {

    private final String name;
    private final UUID uniqueId;

    public SimplifierUser(String name, UUID uniqueId) {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueID() {
        return this.uniqueId;
    }

    @Override
    public ICloudPlayer getICloudPlayer() {
        return CloudSimplifier.getSimplifier().getCloudPlayerManager().getIPlayerManager().getOnlinePlayer(this.uniqueId);
    }

    @Override
    public ICloudOfflinePlayer getICloudOfflinePlayer() {
        return CloudSimplifier.getSimplifier().getCloudPlayerManager().getIPlayerManager().getFirstOfflinePlayer(this.name);
    }

    @Override
    public IPermissionUser getIPermissionUser() {
        return CloudSimplifier.getSimplifier().getCloudPlayerManager().getIPermissionUser(this.uniqueId);
    }

    @Override
    public IPermissionGroup getIPermissionGroup() {
        return CloudSimplifier.getSimplifier().getCloudPlayerManager().getIPermissionGroup(this.uniqueId);
    }

    @Override
    public void sendChatMessage(String message) {
        this.getICloudPlayer().getPlayerExecutor().sendChatMessage(message);
    }

    @Override
    public void sendChatMessage(String[] messages) {
        for (String message : messages) {
            this.sendChatMessage(message);
        }
    }

    @Override
    public void sendChatMessages(String... messages) {
        for (String message : messages) {
            this.sendChatMessage(message);
        }
    }

    @Override
    public PermissionCheckResult hasPermissions(String permission) {
        return this.getIPermissionGroup().hasPermission(permission);
    }
}
