package com.vaibhavi.message.activities;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CameraCaptureActivity extends AppCompatActivity {
    private static final int REQ_CAMERA = 300;

    @Override
    protected void onCreate(@Nullable Bundle s) {
        super.onCreate(s);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQ_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            // TODO: save and return path
            Intent out = new Intent();
            out.putExtra("bitmap", bmp);
            setResult(Activity.RESULT_OK, out);
            finish();
        } else finish();
    }
}
