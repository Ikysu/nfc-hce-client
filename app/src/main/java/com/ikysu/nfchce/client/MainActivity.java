package com.ikysu.nfchce.client;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private NfcAdapter nfcAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    @Override
    public void onPause(){
        super.onPause();
        nfcAdapter.disableReaderMode(this);
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        IsoDep isoDep = IsoDep.get(tag);
        try {
            isoDep.connect();

            byte[] data = isoDep.transceive(Utils.hex2byte("00A4040007A0000002471001"+"1234567890"));
            log(Utils.byte2hex(data));

            isoDep.close();
        } catch (IOException e) {
            Toast.makeText(this, "Что то пошло не так", Toast.LENGTH_SHORT).show();
        }
    }

    public void log(String text){
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}