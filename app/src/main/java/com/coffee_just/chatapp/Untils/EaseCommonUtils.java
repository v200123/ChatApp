package com.coffee_just.chatapp.Untils;

import com.coffee_just.chatapp.Constant;
import com.hyphenate.chat.EMConversation;

public class EaseCommonUtils {
    /**
     * 将应用的会话类型转化为SDK的会话类型
     *
     * @param chatType
     * @return
     */
    public static EMConversation.EMConversationType getConversationType(int chatType) {
        if (chatType == Constant.CHATTYPE_SINGLE) {
            return EMConversation.EMConversationType.Chat;
        } else if (chatType == Constant.CHATTYPE_GROUP) {
            return EMConversation.EMConversationType.GroupChat;
        } else {
            return EMConversation.EMConversationType.ChatRoom;
        }
    }
}
