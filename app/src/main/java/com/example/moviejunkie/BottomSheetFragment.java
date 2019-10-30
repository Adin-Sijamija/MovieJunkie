package com.example.moviejunkie;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviejunkie.Dialogs.bottomSheetDialigInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment  {


    private bottomSheetDialigInterface mCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback=(bottomSheetDialigInterface) context;
        }catch (ClassCastException e){
            //Toast.makeText(context, "Error:CUSTOM", Toast.LENGTH_SHORT).show();
        }
    }

    private int Status=0;

    private Button WatchingButton;
    private Button PlannedButton;
    private Button WatchedButton;
    private Button CancleButton;
    private Button SaveButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            Status = getArguments().getInt("STATUS");

        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view

        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet, null);

        WatchingButton = view.findViewById(R.id.StatusWatchingButtond);
        PlannedButton = view.findViewById(R.id.StatusPlanedButton);
        WatchedButton = view.findViewById(R.id.StatusWatchedButton);
        CancleButton = view.findViewById(R.id.StatusCancelButton);
        SaveButton = view.findViewById(R.id.StatusSaveButton);

        //Toast.makeText(getContext(), "Status::"+Status, Toast.LENGTH_SHORT).show();

        switch (Status) {
            case 1:
                WatchingButton.getBackground().setTint(getResources().getColor(R.color.colorSecondary));
                break;
            case 2:
                PlannedButton.getBackground().setTint(getResources().getColor(R.color.colorSecondary));
                break;
            case 3:
                WatchedButton.getBackground().setTint(getResources().getColor(R.color.colorSecondary));
                break;
                default:
                    break;
        }


        setListners();



        dialog.setContentView(view);
    }

    private void setListners() {

        WatchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(), "Watching clicked", Toast.LENGTH_SHORT).show();
                Status=1;
                WatchingButton.getBackground().setTint(getResources().getColor(R.color.colorSecondary));
                PlannedButton.getBackground().setTint(Color.TRANSPARENT);
                WatchedButton.getBackground().setTint(Color.TRANSPARENT);
            }
        });

        PlannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(), "Watching clicked", Toast.LENGTH_SHORT).show();

                Status=2;
                PlannedButton.getBackground().setTint(getResources().getColor(R.color.colorSecondary));
                WatchingButton.getBackground().setTint(Color.TRANSPARENT);
                WatchedButton.getBackground().setTint(Color.TRANSPARENT);
            }
        });


        WatchedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(), "Watching clicked", Toast.LENGTH_SHORT).show();

                Status=3;
                WatchedButton.getBackground().setTint(getResources().getColor(R.color.colorSecondary));
                WatchingButton.getBackground().setTint(Color.TRANSPARENT);
                PlannedButton.getBackground().setTint(Color.TRANSPARENT);
            }
        });

        CancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DBK", "onClick: STATUS::"+Status);
                mCallback.ChangeStatus(Status);
                dismiss();
            }
        });


    }


}