package com.research.andrade.andar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * A simple {@link Fragment} subclass.
 */
public class Emergency extends Fragment {

    private Switch savingMode, flashlight, strobeFlashlight, screenFlashlight, alarm;
    private Button messageContacts, emergencyContacts, callAuthority, angQRnaIto;
    private MediaPlayer mp;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    private Camera.Parameters params;
    private int i=0;

    public Emergency() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);


        savingMode = (Switch) view.findViewById(R.id.swtSavingMove);
        flashlight = (Switch) view.findViewById(R.id.swtFlashlight);
        strobeFlashlight = (Switch) view.findViewById(R.id.swtStrobe);
        screenFlashlight = (Switch) view.findViewById(R.id.swtScreenFlash);
        alarm = (Switch) view.findViewById(R.id.swtAlarm);

        messageContacts = (Button) view.findViewById(R.id.btnMessage);
        emergencyContacts = (Button) view.findViewById(R.id.btnContacts);
        callAuthority = (Button) view.findViewById(R.id.btnCall);
        angQRnaIto = (Button) view.findViewById(R.id.btnQRCode);

        checkSupport();
        getCamera();

        angQRnaIto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MyQR.class));
            }
        });


        messageContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MessageContact2.class));
            }
        });

        emergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EmergencyContacts.class));
            }
        });

        callAuthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EmergencyHotlines2.class));
            }
        });

        savingMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    alarm.setChecked(false);
                    Toast.makeText(getActivity(), "Battery Saving Mode", Toast.LENGTH_SHORT).show();
                }
            }
        });

        flashlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    screenFlashlight.setChecked(false);
                    strobeFlashlight.setChecked(false);
                    Toast.makeText(getActivity(), "Flashlight", Toast.LENGTH_SHORT).show();
                    if (isFlashOn) {
                        // turn off flash
                        turnOffFlash();
                    } else {
                        // turn on flash
                        turnOnFlash();
                    }

                } else {

                    turnOffFlash();

                }
            }
        });

        strobeFlashlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    flashlight.setChecked(false);
                    screenFlashlight.setChecked(false);
                    strobeFlashlight.setChecked(true);
                    Toast.makeText(getActivity(), "Strobe Flashlight", Toast.LENGTH_SHORT).show();
                    blinkFlash();
                } else{
                    if(isFlashOn)
                    turnOffFlash();
                }

            }
        });

        screenFlashlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (screenFlashlight.isChecked()) {
                    flashlight.setChecked(false);
                    strobeFlashlight.setChecked(false);
                    Toast.makeText(getActivity(), "Screen Flashlight", Toast.LENGTH_SHORT).show();
                    MaxBright();
                } else
                    MinBright();
            }
        });

        alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(getActivity(), "Alarm", Toast.LENGTH_SHORT).show();
                    savingMode.setChecked(false);
                    PlayAlarm();
                } else {
                    mp.stop();
                }

            }
        });

        return view;
    }


    private void MaxBright(){
        try {
            //sets manual mode and brightnes 255
            Settings.System.putInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);  //this will set the manual mode (set the automatic mode off)
            Settings.System.putInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);  //this will set the brightness to maximum (255)

            //refreshes the screen
            int br = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.screenBrightness = (float) br / 255;
            getActivity().getWindow().setAttributes(lp);

        } catch (Exception e) {}
    }

    private void MinBright(){
        try {

            Settings.System.putInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);  //this will set the manual mode (set the automatic mode off)

            //refreshes the screen
            int br = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.screenBrightness = (float) br / 1;
            getActivity().getWindow().setAttributes(lp);

        } catch (Exception e) {}
    }


    private void PlayAlarm() {
        mp = MediaPlayer.create(getActivity(), R.raw.alarm);
        mp.setLooping(true);
        mp.start();
    }

    private void checkSupport () {

        Boolean isFlashAvailable = getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {

            strobeFlashlight.setEnabled(false);
            flashlight.setEnabled(false);

            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle("Error!!");
            alert.setMessage("Your device does'nt support flashlight!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alert.show();
            return;

        }

    }

    @SuppressLint("LongLogTag")
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }


    private void turnOnFlash() {

        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }


            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;


        }


    }

    private void turnOffFlash() {

        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

        }

    }

    private void blinkFlash() {

        String myString = "0101010101";
        long blinkDelay = 50; //Delay in ms
        for (int i = 0; i < myString.length(); i++) {
            if (myString.charAt(i) == '0') {
                turnOnFlash();
            } else {
                turnOffFlash();
            }
            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();

        // on resume turn on the flash
        if(hasFlash)
            turnOnFlash();
    }

    @Override
    public void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    public void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }



}

