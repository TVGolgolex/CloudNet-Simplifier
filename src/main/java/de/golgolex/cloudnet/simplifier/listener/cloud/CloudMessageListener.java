package de.golgolex.cloudnet.simplifier.listener.cloud;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 21:40
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
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.channel.ChannelMessageReceiveEvent;
import de.golgolex.cloudnet.simplifier.CloudSimplifier;

public class CloudMessageListener {

    @EventListener
    public void handle(ChannelMessageReceiveEvent event) {
        if (event.getChannel().equalsIgnoreCase("claycloud_api")) {

            if (event.getMessage().equalsIgnoreCase("updateMaintenance")) {
                JsonDocument jsonDocument = event.getData();
                CloudSimplifier.getSimplifier().getCloudProxyManager().changeMaintenance(jsonDocument.getBoolean("state"));
                return;
            }

            if (event.getMessage().equalsIgnoreCase("updateSlots")) {
                JsonDocument jsonDocument = event.getData();
                CloudSimplifier.getSimplifier().getCloudProxyManager().setMaxPlayerSize(jsonDocument.getInt("slots"));
                return;
            }

        }
    }

}
