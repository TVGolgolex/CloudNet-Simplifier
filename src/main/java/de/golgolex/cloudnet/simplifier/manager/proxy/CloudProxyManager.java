package de.golgolex.cloudnet.simplifier.manager.proxy;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 21:33
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
import de.dytanic.cloudnet.ext.syncproxy.AbstractSyncProxyManagement;
import de.dytanic.cloudnet.ext.syncproxy.configuration.SyncProxyConfiguration;
import de.dytanic.cloudnet.ext.syncproxy.configuration.SyncProxyProxyLoginConfiguration;

import java.util.List;

public class CloudProxyManager {

    private final AbstractSyncProxyManagement syncProxyManagement;
    private final SyncProxyProxyLoginConfiguration loginConfiguration;

    public void changeMaintenance(boolean state) {
        if(loginConfiguration == null) return;
        loginConfiguration.setMaintenance(state);
        SyncProxyConfiguration.updateSyncProxyConfigurationInNetwork(this.syncProxyManagement.getSyncProxyConfiguration());
    }

    public boolean isMaintenance() {
        return loginConfiguration == null || loginConfiguration.isMaintenance();
    }

    public void addToWhitelist(String name) {
        if (loginConfiguration == null)
            return;
        loginConfiguration.getWhitelist().add(name);
    }

    public void removeFromWhitelist(String name) {
        if (loginConfiguration == null)
            return;
        loginConfiguration.getWhitelist().remove(name);
    }

    public List<String> listWhitelist() {
        return loginConfiguration == null ? null : loginConfiguration.getWhitelist();
    }


    public void setMaxPlayerSize(int size) {
        if (loginConfiguration == null) return;
        loginConfiguration.setMaxPlayers(size);
    }

    public int getMayPlayerSize() {
        return loginConfiguration == null ? -1 : loginConfiguration.getMaxPlayers();
    }

    public CloudProxyManager() {
        syncProxyManagement = CloudNetDriver.getInstance().getServicesRegistry()
                .getFirstService(AbstractSyncProxyManagement.class);
        loginConfiguration = this.syncProxyManagement.getLoginConfiguration();
    }

}
