package com.example.test.amrtest;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xxjy.amrwbenc.AmrWbEncoder;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private File destFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        destFile = new File(getCacheDir(), "test_amr_wb.amr");
    }

    public void convert(View view) {

        InputStream wavInputStream = null;
        FileOutputStream outputStream = null;
        try {
            wavInputStream = getResources().getAssets().open("test_16k.wav");
            //noinspection ResultOfMethodCallIgnored
            wavInputStream.skip(44);

            //AMR头文件"#!AMR-WB\n"
            byte[] header = new byte[]{0x23, 0x21, 0x41, 0x4D, 0x52, 0x2D, 0x57, 0x42, 0x0A};

            byte[] wavBuffer = new byte[640];
            byte[] outBuffer = new byte[640];
            if (!destFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                destFile.createNewFile();
            }
            outputStream = new FileOutputStream(destFile);
            outputStream.write(header);

            AmrWbEncoder.init();
            int readSize;
            while ((readSize = wavInputStream.read(wavBuffer)) != -1) {
                if (readSize > 0) {
                    short[] buffer = bytes2shorts(wavBuffer);

                    int encodedSize = AmrWbEncoder.encode(AmrWbEncoder.Mode.MD1825.ordinal(), buffer, outBuffer, 0);
                    if (encodedSize > 0) {
                        try {
                            outputStream.write(outBuffer, 0, encodedSize);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Toast.makeText(this, "Convert Success", Toast.LENGTH_SHORT).show();
            play();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(wavInputStream);
            closeStream(outputStream);
            try {
                AmrWbEncoder.exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void play(View view) {
        play();
    }

    private void play() {
        if (!destFile.exists()) {
            return;
        }
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(destFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }

    private static void closeStream(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static short[] bytes2shorts(byte[] input) {
        short[] buffer = new short[input.length / 2];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (short) (((input[2 * i] & 0xFF)) | ((input[2 * i + 1] & 0xFF) << 8));
        }
        return buffer;
    }

}
