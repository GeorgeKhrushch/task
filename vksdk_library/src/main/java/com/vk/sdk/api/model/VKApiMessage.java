//
//  Copyright (c) 2014 VK.com
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy of
//  this software and associated documentation files (the "Software"), to deal in
//  the Software without restriction, including without limitation the rights to
//  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
//  the Software, and to permit persons to whom the Software is furnished to do so,
//  subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in all
//  copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
//  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
//  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
//  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//

/**
 * Message.java
 * vk-android-sdk
 *
 * Created by Babichev Vitaly on 19.01.14.
 * Copyright (c) 2014 VK. All rights reserved.
 */
package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.sdk.util.DateFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A message object describes a private message
 */
@SuppressWarnings("unused")
public class VKApiMessage extends VKApiModel implements Identifiable, Parcelable {

    /**
     * 	Message ID. (Not returned for forwarded messages), positive number
     */
    public int id;

    /**
     * For an incoming message, the user ID of the author. For an outgoing message, the user ID of the receiver.
     */
    public int user_id;
    public int from_id;

    /**
     * 	Date (in Unix time) when the message was sent.
     */
    public long date;

    /**
     * Message status (false — not read, true — read). (Not returned for forwarded messages.)
     */
    public boolean read_state;

    /**
     * Message type (false — received, true — sent). (Not returned for forwarded messages.)
     */
    public boolean out;
    public boolean isCrypted;

    /**
     * Title of message or chat.
     */
    @JsonView(Views.Manager.class)
    public String title;

    /**
     * Body of the message.
     */
    public String body;

    @JsonView(Views.Manager.class)
    public String bodyShort;

    /**
     * List of media-attachments;
     */
    public VKAttachments attachments = new VKAttachments();

    /**
     * Array of forwarded messages (if any).
     */
    public VKList<VKApiMessage> fwd_messages;

    /**
     *	Whether the message contains smiles (false — no, true — yes).
     */
    public boolean emoji;

    /**
     * Whether the message is deleted (false — no, true — yes).
     */
    @JsonView(Views.Manager.class)
    public boolean deleted;

    @JsonView(Views.Manager.class)
    public VKApiChat chat;

    public String dateString;

    public VKApiMessage(JSONObject from) throws JSONException {
		parse(from);
	}
    /**
     * Fills a Message instance from JSONObject.
     */
    public VKApiMessage parse(JSONObject source) throws JSONException {
        id = source.optInt("id");
        user_id = source.optInt("user_id");
        date = source.optLong("date");
        from_id = source.optInt("from_id");
        dateString = String.valueOf(DateFormatter.getShortDate(date));
        attachments.fill(source.optJSONArray("attachments"));
        fwd_messages = new VKList<>(source.optJSONArray("fwd_messages"), VKApiMessage.class);
        read_state = ParseUtils.parseBoolean(source, "read_state");
        out = ParseUtils.parseBoolean(source, "out");
        title = source.optString("title");
        body = source.optString("body");
        bodyShort = VKApiPost.shortString(50, body);
        emoji = ParseUtils.parseBoolean(source, "emoji");
        deleted = ParseUtils.parseBoolean(source, "deleted");
        chat = new VKApiChat(source);
        return this;
    }

    /**
     * Creates empty Country instance.
     */
    public VKApiMessage() {

    }

    @Override
    public int getId() {
        return id;
    }

    public boolean isChat(){
        return chat.id != 0;
    }

    public String toJsonString(){
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writerWithView(Views.Normal.class).writeValueAsString(this);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Message", json);
        return json;
    }

    public VKApiMessage fromJsonString(String json) throws Exception{
        /*VKApiMessage message ;
        message = new VKApiMessage(new JSONObject(json));
        Log.d("JSON", message.body + " |" + message.id + " | " + message.attachments.size()
                + " | " + message.out);*/
        return new VKApiMessage(new JSONObject(json));
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof VKApiMessage))
            return false;

        try {
            VKApiMessage equals = (VKApiMessage) o;
            return this.getId() == equals.getId()
                    && this.body.equals(equals.body);
        }catch (ClassCastException e){
            return false;
        }
    }

    public boolean hasAttachments(){
        return attachments.size() !=0 || fwd_messages.size() != 0;
    }

    public interface MessageDecryptListener{
        void onFinishDecrypt(VKApiMessage message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.from_id);
        dest.writeLong(this.date);
        dest.writeByte(this.read_state ? (byte) 1 : (byte) 0);
        dest.writeByte(this.out ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.bodyShort);
        dest.writeParcelable(this.attachments, flags);
        dest.writeParcelable(this.fwd_messages, flags);
        dest.writeByte(this.emoji ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCrypted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.deleted ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.chat, flags);
        dest.writeString(this.dateString);
    }

    protected VKApiMessage(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.from_id = in.readInt();
        this.date = in.readLong();
        this.read_state = in.readByte() != 0;
        this.out = in.readByte() != 0;
        this.title = in.readString();
        this.body = in.readString();
        this.bodyShort = in.readString();
        this.attachments = in.readParcelable(VKAttachments.class.getClassLoader());
        this.fwd_messages = in.readParcelable(VKList.class.getClassLoader());
        this.emoji = in.readByte() != 0;
        this.isCrypted = in.readByte() != 0;
        this.deleted = in.readByte() != 0;
        this.chat = in.readParcelable(VKApiChat.class.getClassLoader());
        this.dateString = in.readString();
    }

    public static final Creator<VKApiMessage> CREATOR = new Creator<VKApiMessage>() {
        @Override
        public VKApiMessage createFromParcel(Parcel source) {
            return new VKApiMessage(source);
        }

        @Override
        public VKApiMessage[] newArray(int size) {
            return new VKApiMessage[size];
        }
    };
}
