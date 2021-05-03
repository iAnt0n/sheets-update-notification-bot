package ru.iAnt0n;

import ru.iAnt0n.dao.SheetsValuesDAO;
import ru.iAnt0n.dao.SheetsValuesDAOImpl;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import ru.iAnt0n.sheets.DbComparator;
import ru.iAnt0n.sheets.SheetsService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            SheetsService sheetsService = new SheetsService();
            SheetsValuesDAO sheetsValuesDAO = new SheetsValuesDAOImpl();
            Bot bot = new Bot(sheetsValuesDAO, sheetsService);
            botsApi.registerBot(bot);
            final ScheduledExecutorService scheduler =
                    Executors.newScheduledThreadPool(1);
            scheduler.scheduleWithFixedDelay(new DbComparator(bot), 0, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
