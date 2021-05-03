package ru.iAnt0n.sheets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import com.google.api.services.sheets.v4.model.ValueRange;
import ru.iAnt0n.util.PropertiesReader;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsService {
    private static final String API_KEY= PropertiesReader.getAPIKey();
    private final Sheets sheetsAPI;

    public SheetsService() throws GeneralSecurityException, IOException {
        sheetsAPI = initService();
    }

    private Sheets initService() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Sheets.Builder(httpTransport, jsonFactory, null)
                .setApplicationName("Sheets-Update-ru.iAnt0n.Bot")
                .setGoogleClientRequestInitializer(new SheetsRequestInitializer(API_KEY))
                .build();
    }

    public ValueRange getValueRange(String spreadsheetId, String range) throws IOException {
        Sheets.Spreadsheets.Values.Get request = sheetsAPI
                .spreadsheets()
                .values()
                .get(spreadsheetId, range);
        return request.execute();
    }
}
