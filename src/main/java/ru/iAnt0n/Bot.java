package ru.iAnt0n;

import ru.iAnt0n.dao.SheetsValuesDAO;
import ru.iAnt0n.model.SheetValues;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.iAnt0n.sheets.SheetsService;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private final SheetsValuesDAO sheetsValuesDAO;
    private final SheetsService sheetsService;

    public Bot(SheetsValuesDAO dao, SheetsService sheetsService) {
        super();
        this.sheetsValuesDAO = dao;
        this.sheetsService = sheetsService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String inMessage = update.getMessage().getText();
            if ("/start".equals(inMessage)) {
                SendMessage sendMessage = new SendMessage()
                        .setChatId(chatId)
                        .setText("Для подписки на обновление ячеек таблицы введите сообщения в следующем формате\n" +
                                "<Id таблицы> <Интервал отслеживаемых значений>");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                String[] params = inMessage.split("\\s+");
                try {
                    SheetValues sv = new SheetValues();
                    sv.setChatId(chatId);
                    sv.setSheetId(params[0]);
                    sv.setRange(params[1]);
                    List<List<Object>> values = sheetsService.getValueRange(sv.getSheetId(), sv.getRange()).getValues();
                    sv.setValues(new ArrayList<>());
                    if (values != null) {
                        for (List<Object> l : values) {
                            for (Object o : l) {
                                sv.addValue(o.toString());
                            }
                        }
                    }
                    sheetsValuesDAO.addSheet(sv);
                    SendMessage message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText("Отслеживание добавлено");
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "sheets_update_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    public SheetsValuesDAO getSheetsValuesDAO() {
        return sheetsValuesDAO;
    }

    public SheetsService getSheetsService() {
        return sheetsService;
    }
}
