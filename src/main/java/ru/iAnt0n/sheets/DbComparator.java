package ru.iAnt0n.sheets;

import ru.iAnt0n.Bot;
import ru.iAnt0n.util.UtilCheck;
import ru.iAnt0n.dao.SheetsValuesDAO;
import ru.iAnt0n.model.SheetValues;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DbComparator implements Runnable {
    private final Bot bot;
    private final Logger logger = Logger.getLogger(DbComparator.class.getName());

    public DbComparator(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        logger.info("Check for changes at "+LocalDateTime.now());
        SheetsValuesDAO dao = bot.getSheetsValuesDAO();
        SheetsService sheetsService = bot.getSheetsService();
        List<SheetValues> table = dao.getValues();
        if (table!=null) {
            for (SheetValues sv : table) {
                try {
                    List<List<Object>> valueRange = sheetsService.getValueRange(sv.getSheetId(), sv.getRange()).getValues();
                    List<String> newValues = new ArrayList<>();
                    if (valueRange!=null) {
                        for (List<Object> l : valueRange) {
                            for (Object o : l) {
                                newValues.add(o.toString());
                            }
                        }
                        if (!UtilCheck.checkEquality(sv.getValues(), newValues)) {
                            SheetValues newSv = new SheetValues();
                            newSv.setChatId(sv.getChatId());
                            newSv.setSheetId(sv.getSheetId());
                            newSv.setRange(sv.getRange());
                            newSv.setValues(newValues);
                            dao.merge(newSv);
                            SendMessage message = new SendMessage();
                            message.setChatId(sv.getChatId());
                            message.setText("Произошло изменение в таблице https://docs.google.com/spreadsheets/d/" + sv.getSheetId());
                            bot.execute(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
