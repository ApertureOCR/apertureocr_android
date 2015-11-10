package com.aperturecs.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureCropActivity extends AppCompatActivity {

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_crop);

        Intent intent = getIntent();

        /*bitmap = (Bitmap)intent.getExtras().get("IMAGE");

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);*/

        String fileName = intent.getStringExtra("fileName");
        TextView textView = (TextView)findViewById(R.id.intentTextView);
        textView.setText(fileName);
    }
}
