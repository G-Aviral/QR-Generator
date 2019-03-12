package com.example.avira.makeqr;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    EditText text;
    Button gen_btn;
    ImageView image;
    String text2Qr;
    Button savebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request permission to save qr
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        //Code

        text=(EditText)findViewById(R.id.text);
        gen_btn=(Button)findViewById(R.id.gen_btn);
        image = (ImageView)findViewById(R.id.image);
        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2Qr = text.getText().toString().trim();
                Toast.makeText(MainActivity.this,text2Qr,Toast.LENGTH_LONG).show();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);


                    //saving QR

                    //Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.a);

                    File path = Environment.getExternalStorageDirectory();
                    //File path = Environment.getRootDirectory();
                    //CREATE FOLDER
                    File dir = new File(path + "//Playoff");
                    dir.mkdirs();

                    File file = new File(dir,"Booking QR.png");

                    OutputStream out;
                    try {
                        out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
                        out.flush();
                        out.close();
                        Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_LONG).show();

                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }

                    //end of saving QR code




                }
                catch (WriterException e)
                {
                    e.printStackTrace();
                }
            }
        });

/*
        savebtn=(Button)findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saving QR

                Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.a);

                File path = Environment.getExternalStorageDirectory();
                //File path = Environment.getRootDirectory();
                //CREATE FOLDER
                File dir = new File(path + "//Playoff");
                dir.mkdirs();

                File file = new File(dir,"Booking QR.png");

                OutputStream out;
                try {
                    out = new FileOutputStream(file);
                    image.compress(Bitmap.CompressFormat.PNG,100,out);
                    out.flush();
                    out.close();
                    Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_LONG).show();

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                //end of saving QR code

            }
        });*/


    }
}
