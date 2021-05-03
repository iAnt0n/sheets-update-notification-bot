package ru.iAnt0n.dao;

import ru.iAnt0n.model.SheetValues;

import java.util.List;

public interface SheetsValuesDAO {
    void addSheet(SheetValues s);
    List<SheetValues> getValues();
    void merge(SheetValues s);
}
