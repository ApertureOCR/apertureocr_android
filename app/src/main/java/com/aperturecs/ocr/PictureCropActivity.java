package com.aperturecs.ocr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureCropActivity extends AppCompatActivity {

    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_crop);

        context = this;

        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");
        File cropImageFile = new File(filePath);
        Bitmap cropImage = null;
        if(cropImageFile.exists()) {
            cropImage = BitmapFactory.decodeFile(cropImageFile.getAbsolutePath());
        }

        final CropImageView cropImageView = (CropImageView)findViewById(R.id.cropImageView);

        // Set image for cropping
        cropImageView.setImageBitmap(cropImage);

        Button cropButton = (Button)findViewById(R.id.crop_button);

        cropButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(PictureCropActivity.this.getApplicationContext(), TranslateActivity.class);
            @Override
            public void onClick(View v) {
                // Get cropped image, and show result.
                Bitmap croppedImage = cropImageView.getCroppedBitmap();
                String fileName = "Aperture_translating_"+System.currentTimeMillis()+".jpg";
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                if(croppedImage!=null) {
                    File dir = new File(Environment.getExternalStorageDirectory()+"/aperture/temp/");
                    if(!dir.isDirectory())
                        dir.mkdir();

                    File file = new File(Environment.getExternalStorageDirectory()+"/aperture/temp/", fileName);

                    try(FileOutputStream fos = new FileOutputStream(file)){
                        croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                        fos.flush();
                    } catch(FileNotFoundException e){
                        e.printStackTrace();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }

                    /*갤러리에 추가하는 인텐트*/
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    context.sendBroadcast(mediaScanIntent);

                    intent.putExtra("filePath", Environment.getExternalStorageDirectory()+"/aperture/temp/"+fileName);
                    startActivity(intent);
                }
            }
        });
    }
}
