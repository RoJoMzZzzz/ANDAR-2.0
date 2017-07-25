package com.research.andrade.andar;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class More extends Fragment {

    private Button scanQrClass, tracker, rating, messLog, callLog, ref, exitBtn, aboutBtn;

    public More() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        scanQrClass = (Button) view.findViewById(R.id.btnQrCodeScanner);
        tracker = (Button) view.findViewById(R.id.btnTracker);
        rating = (Button) view.findViewById(R.id.btnRate);
        messLog = (Button) view.findViewById(R.id.btnMessageLog);
        callLog = (Button) view.findViewById(R.id.btnCallLog);
        ref = (Button) view.findViewById(R.id.btnReferences);
        exitBtn = (Button) view.findViewById(R.id.btnExit);
        aboutBtn = (Button) view.findViewById(R.id.btnAbout);

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),About.class));
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder
                        .setMessage("Are you sure you want to exit the application?")
                        .setCancelable(false)
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.exit(0);
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setTitle("Exit Application");
                alertDialog.show();
            }
        });

        scanQrClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ScanQR.class));
            }
        });

        tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MapsActivity.class));
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Rate.class));
            }
        });

        messLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageLog.class));
            }
        });

        callLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CallLogs.class));
            }
        });

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), References.class));
            }
        });

        return view;
    }

}
