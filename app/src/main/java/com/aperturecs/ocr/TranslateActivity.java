package com.aperturecs.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

public class TranslateActivity extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");
        File file = new File(filePath);

        Bitmap croppedImage = BitmapFactory.decodeFile(file.getAbsolutePath());

        final ImageView croppedImageView = (ImageView)findViewById(R.id.croppedImageView);
        croppedImageView.setImageBitmap(croppedImage);

        String string = null;

        /*
        *
        *
        * 영
        * 상
        * 처
        * 리
        *
        *
        * */

        try {
            sendStringToServer(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.delete();

    }

    private String sendStringToServer(String string) throws IOException {
        OkHttpClient client = new OkHttpClient();

//        {“from_lang” : “en”, “to_lang”:”ko”, “string” : 번역할 영문}
        String json = "{\"from_lang\" : \"en\", \"to_lang\":\"ko\", \"string\" : \"}" + string + "\"}";

        return post("thedeblur.com:7276", json);
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
