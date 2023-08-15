package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton toggleButton;
    boolean hasCameraFlash = false;
    boolean flashon = false;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toggleButton=findViewById(R.id.power_btn);
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCameraFlash) {
                    if (flashon) {
                        flashon = false;
                        toggleButton.setImageResource(R.drawable.power_off);
                        try {
                            flashLightoff();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        flashon = true;
                        toggleButton.setImageResource(R.drawable.power_on);
                        try {
                            flashLighton();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No flas available on your device", Toast.LENGTH_SHORT).show();
                }
            }

//            private void flashLightoff() {
        });
    }
    private void flashLighton() throws CameraAccessException {
        CameraManager cameraManager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,true);
        Toast.makeText(MainActivity.this, "flash light on", Toast.LENGTH_SHORT).show();
    }
    private void flashLightoff() throws CameraAccessException {
        CameraManager cameraManager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,false);
        Toast.makeText(MainActivity.this, "Flash light off", Toast.LENGTH_SHORT).show();
    }
}

