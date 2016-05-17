package com.chicken.invasion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by pedramshirmohammad on 16-05-16.
 */
public class CreateWeapon extends Activity {


    Button takeAPic;
    ImageView croppedPic;
    File image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_weapon_layout);
        takeAPic = (Button)findViewById(R.id.takeAPic);
        croppedPic = (ImageView)findViewById(R.id.imageView2);

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        image = new File(sdCardDirectory, "testings.jpg");



        takeAPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageDownload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imageDownload.putExtra("crop","true");
                imageDownload.putExtra("aspectX",1);
                imageDownload.putExtra("aspectY",1);
                imageDownload.putExtra("outputX",256);
                imageDownload.putExtra("outputY",256);
                imageDownload.putExtra("return-data",true);
                startActivityForResult(imageDownload,2);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            Bitmap immage = extras.getParcelable("data");
            Bitmap dafd = getCroppedBitmap(immage);
            croppedPic.setImageBitmap(dafd);
        }


        //SAVE PIC
        boolean success = false;

        // Encode the file as a PNG image.
        BitmapDrawable drawable = (BitmapDrawable) croppedPic.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success) {
            Toast.makeText(getApplicationContext(), "Image saved with success",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error during image saving", Toast.LENGTH_LONG).show();
        }
        //END SAVE PIC

    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

}
