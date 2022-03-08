package com.coding.movie.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coding.movie.R;
import com.coding.movie.ui.imdb.ImdbActivity;
import com.google.android.material.button.MaterialButton;

public class WebServiceHelper {
    public static void dialogBox(final String msg,final Activity mActivity, final Context mContext) {
        if(!((Activity) mContext).isFinishing()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
            LayoutInflater inflater = mActivity.getLayoutInflater();
            @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_box, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);
            final AlertDialog alertDialog = dialogBuilder.create();
            TextView tvThanks = dialogView.findViewById(R.id.tv_message);
            MaterialButton btnThanks = dialogView.findViewById(R.id.btn_ok);
            tvThanks.setText(msg);
            btnThanks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Intent intent = new Intent(mContext, ImdbActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.finish();
                }
            });
            alertDialog.show();
        }
    }

}
