package ru.iAnt0n.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.List;

@Entity
@IdClass(CompositeId.class)
public class SheetValues {
    @Id
    private long chatId;
    @Id
    private String sheetId;

    private String range;

    @ElementCollection
    private List<String> values;

    public SheetValues(){}

    public SheetValues(long chatId, String sheetId){
        this.chatId = chatId;
        this.sheetId = sheetId;
    }

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValue(String v){
        values.add(v);
    }


}
