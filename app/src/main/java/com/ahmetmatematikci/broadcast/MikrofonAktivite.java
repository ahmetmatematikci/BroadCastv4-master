package com.ahmetmatematikci.broadcast;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MikrofonAktivite extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private static final String DOSYAADI ="ses.3gpp";

    //Kayit
    boolean mKayit = false;
    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder;
    FileOutputStream mDosyayaz;
    Button mKayitDugmesi;

    //oynat
    boolean mOynat = false;
    Button mOynatDugmesi;
    FileInputStream mDosyaoku;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mikrofon_aktivite);
        mKayitDugmesi = (Button)findViewById(R.id.mikrokayit);
        mOynatDugmesi = (Button)findViewById(R.id.mikrooynat);
    }

    private void kayitBaslat() {
        mediaRecorder = new MediaRecorder();

        try {
            mDosyayaz = openFileOutput(DOSYAADI, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(mDosyayaz.getFD());
            mediaRecorder.prepare();
            mediaRecorder.start();
            mKayit = true;
            mKayitDugmesi.setText("kaydet Durdur");
            mOynatDugmesi.setEnabled(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void kayitDurdur(){
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        mKayit = false;
        mKayitDugmesi.setText("Kaydet");
        try {
            mDosyayaz.close();
            mOynatDugmesi.setEnabled(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void oynatBaslat() {
        mediaPlayer = new MediaPlayer();
        try {
            mDosyaoku = openFileInput(DOSYAADI);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setDataSource(mDosyaoku.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mOynat = true;
            mOynatDugmesi.setEnabled(false);
            mOynatDugmesi.setText("Oynat Durdur");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void oynatDurdur() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mOynat = false;
        mOynatDugmesi.setEnabled(true);
        mOynatDugmesi.setText("Oyanet başlat");

        try {
            mDosyaoku.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        oynatDurdur();

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.mikrokayit:
                if (mKayit)
                    kayitDurdur();
                else
                kayitBaslat();
                break;
            case  R.id.mikrooynat:
                if (mOynat)
                    oynatDurdur();
                else
                oynatBaslat();
                break;
        }
    }
}
