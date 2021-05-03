package ru.iAnt0n.model;

import java.io.Serializable;

public class CompositeId implements Serializable {
    private long chatId;
    private String sheetId;

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }
}
