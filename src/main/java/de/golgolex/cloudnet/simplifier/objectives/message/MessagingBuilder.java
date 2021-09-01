package de.golgolex.cloudnet.simplifier.objectives.message;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 15:02
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
import de.dytanic.cloudnet.driver.channel.ChannelMessage;
import de.dytanic.cloudnet.driver.channel.ChannelMessageTarget;

public class MessagingBuilder {

    private String channel;
    private String message;
    private JsonDocument jsonDocument;
    private ChannelMessageTarget.Type type;
    private String sendTo;

    public MessagingBuilder sendTo(String sendTo) {
        this.sendTo = sendTo;
        return this;
    }

    public MessagingBuilder channel(String channel) {
        this.channel = channel;
        return this;
    }

    public MessagingBuilder message(String message) {
        this.message = message;
        return this;
    }

    public MessagingBuilder jsonDocument(JsonDocument jsonDocument) {
        this.jsonDocument = jsonDocument;
        return this;
    }

    public MessagingBuilder channelMessageTarget(ChannelMessageTarget.Type type) {
        this.type = type;
        return this;
    }

    public void send() {
        if (sendTo == null) return;
        ChannelMessage
                .builder()
                .channel(this.channel)
                .message(this.message)
                .json(this.jsonDocument)
                .target(this.type, this.sendTo)
                .build()
                .send();
    }

    public ChannelMessageTarget.Type getType() {
        return type;
    }

    public JsonDocument getJsonDocument() {
        return jsonDocument;
    }

    public String getChannel() {
        return channel;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getMessage() {
        return message;
    }

}
