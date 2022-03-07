package com.coding.movie.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coding.movie.R;
import com.google.android.material.button.MaterialButton;

public class WebServiceHelper {

    public static void showDialogError(final String text,final Activity mActivity, final Context mContext) {
        if(!((Activity) mContext).isFinishing())
        {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
            LayoutInflater inflater = mActivity.getLayoutInflater();
            @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_box_layout, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);
            final AlertDialog alertDialog = dialogBuilder.create();
            MaterialButton btnOk = dialogView.findViewById(R.id.btnOk);
            TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
            tvMessage.setText(text);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }

    }

}
