package de.golgolex.cloudnet.simplifier.manager.cloudplayer;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 14:39
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

import de.dytanic.cloudnet.common.INameable;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.ext.bridge.player.ICloudOfflinePlayer;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.golgolex.cloudnet.simplifier.CloudSimplifier;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class CloudPlayerManager {

    private final IPlayerManager iPlayerManager;

    public CloudPlayerManager(IPlayerManager iPlayerManager) {
        this.iPlayerManager = iPlayerManager;
    }

    public void getOnlinePlayer(UUID uniqueId, Consumer<Optional<ICloudPlayer>> consumer) {
        this.iPlayerManager.getOnlinePlayerAsync(uniqueId)
                .onComplete(cloudPlayer -> consumer.accept(Optional.ofNullable(cloudPlayer)))
                .onFailure(throwable -> {
                    consumer.accept(Optional.empty());
                    throwable.printStackTrace();
                });
    }

    public Optional<ICloudPlayer> getOnlinePlayer(UUID uniqueId) {
        return Optional.ofNullable(this.iPlayerManager.getOnlinePlayer(uniqueId));
    }

    public Optional<ICloudPlayer> getOnlinePlayer(String name) {
        return Optional.ofNullable(this.iPlayerManager.getFirstOnlinePlayer(name));
    }

    public void getOnlinePlayer(String name, Consumer<Optional<ICloudPlayer>> consumer) {
        this.iPlayerManager.getFirstOnlinePlayerAsync(name)
                .onComplete(cloudPlayer -> consumer.accept(Optional.ofNullable(cloudPlayer)))
                .onFailure(throwable -> {
                    consumer.accept(Optional.empty());
                    throwable.printStackTrace();
                });
    }

    public void getOfflinePlayer(UUID uniqueId, Consumer<Optional<ICloudOfflinePlayer>> consumer) {
        this.iPlayerManager.getOfflinePlayerAsync(uniqueId)
                .onComplete(offlinePlayer -> consumer.accept(Optional.ofNullable(offlinePlayer)))
                .onFailure(throwable -> {
                    consumer.accept(Optional.empty());
                    throwable.printStackTrace();
                });
    }

    public Optional<ICloudOfflinePlayer> getOfflinePlayer(String name) {
        return Optional.ofNullable(this.iPlayerManager.getFirstOfflinePlayer(name));
    }

    public Optional<ICloudOfflinePlayer> getOfflinePlayer(UUID uniqueId) {
        return Optional.ofNullable(this.iPlayerManager.getOfflinePlayer(uniqueId));
    }

    public void getOfflinePlayer(String name, Consumer<Optional<ICloudOfflinePlayer>> consumer) {
        this.iPlayerManager.getFirstOfflinePlayerAsync(name)
                .onComplete(offlinePlayer -> consumer.accept(Optional.ofNullable(offlinePlayer)))
                .onFailure(throwable -> {
                    consumer.accept(Optional.empty());
                    throwable.printStackTrace();
                });
    }

    public void getPlayerName(UUID uniqueId, Consumer<Optional<String>> consumer) {
        this.getOfflinePlayer(uniqueId, optional -> consumer.accept(optional.map(INameable::getName)));
    }

    public Optional<String> getPlayerName(UUID uniqueId) {
        return this.getOfflinePlayer(uniqueId).map(INameable::getName);
    }

    public void getPlayerUUID(String name, Consumer<Optional<UUID>> consumer) {
        this.getOfflinePlayer(name, optional -> consumer.accept(optional.map(ICloudOfflinePlayer::getUniqueId)));
    }

    public Optional<UUID> getPlayerUUID(String name) {
        return this.getOfflinePlayer(name).map(ICloudOfflinePlayer::getUniqueId);
    }

    public void sendChatMessage(UUID uniqueId, String message) {
        this.iPlayerManager.getPlayerExecutor(uniqueId).sendChatMessage(message);
    }

    public void getPlayerProxy(UUID uniqueId, Consumer<Optional<String>> consumer) {
        this.getOnlinePlayer(uniqueId, optional -> consumer.accept(optional.map(c -> c.getLoginService().getServerName())));
    }

    public void getPlayerServer(UUID uniqueId, Consumer<Optional<String>> consumer) {
        this.getOnlinePlayer(uniqueId, optional -> consumer.accept(optional.map(c -> c.getConnectedService().getServerName())));
    }

    public Optional<String> getPlayerAddress(UUID uniqueId) {
        ICloudPlayer cloudPlayer = this.getOnlinePlayer(uniqueId).orElse(null);
        if(cloudPlayer != null) {
            return Optional.ofNullable(cloudPlayer.getNetworkConnectionInfo().getAddress().getHost());
        }
        return this.getOfflinePlayer(uniqueId).map(c -> c.getLastNetworkConnectionInfo().getAddress().getHost());
    }

    public IPermissionUser getIPermissionUser(String playerName) {
        return CloudSimplifier.getSimplifier().getCloudNetAPI().getPermissionManagement().getUser(this.iPlayerManager.getFirstOfflinePlayer(playerName).getUniqueId());
    }

    public IPermissionUser getIPermissionUser(UUID uuid) {
        return CloudSimplifier.getSimplifier().getCloudNetAPI().getPermissionManagement().getUser(uuid);
    }

    public IPermissionGroup getIPermissionGroup(String userName) {
        return CloudSimplifier.getSimplifier().getCloudNetAPI().getPermissionManagement().getHighestPermissionGroup(getIPermissionUser(userName));
    }

    public IPermissionGroup getIPermissionGroup(UUID uuid) {
        return CloudSimplifier.getSimplifier().getCloudNetAPI().getPermissionManagement().getHighestPermissionGroup(getIPermissionUser(uuid));
    }

    public IPermissionGroup getIPermissionGroup(IPermissionUser iPermissionUser) {
        return CloudSimplifier.getSimplifier().getCloudNetAPI().getPermissionManagement().getHighestPermissionGroup(getIPermissionUser(iPermissionUser.getName()));
    }

    public IPlayerManager getIPlayerManager() {
        return iPlayerManager;
    }
}
