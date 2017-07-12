package net.kerfuffle.eggsaresize;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdavis on 7/12/2017.
 */

public class SheetsUtil implements Runnable
{

    private boolean SET_CELL = false, SET_CELLS = false;

    private com.google.api.services.sheets.v4.Sheets mService = null;
    private Exception mLastError = null;
    private volatile boolean running = false;

    private String sheet;

    public SheetsUtil(GoogleAccountCredential credential, String sheet)
    {
        this.sheet=sheet;

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("EggsAreSize")
                .build();
    }

    public void run()
    {
        while (running)
        {
            if (SET_CELL) {
                try {
                    ValueRange body = new ValueRange()
                            .setRange(tempCell)
                            .setValues(putInSheetsFormat(tempCellStr));
                    UpdateValuesResponse result =
                            this.mService.spreadsheets().values().update(sheet, tempCell, body)
                                    .setValueInputOption("RAW")
                                    .execute();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                SET_CELL = false;
            }

            if (SET_CELLS)
            {
                SET_CELLS = false;
            }
        }
    }

    private String tempCell, tempCellStr;
    public void setCell(String cell, String str)
    {
        tempCell = cell;
        tempCellStr = str;
        SET_CELL = true;
    }

    private List<List<Object>> putInSheetsFormat(String str)  {

        List<Object> data1 = new ArrayList<Object>();
        data1.add (str);

        List<List<Object>> data = new ArrayList<List<Object>>();
        data.add (data1);

        return data;
    }

}
