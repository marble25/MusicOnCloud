package org.iptime.mascore.musiconcloud;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Owner on 2017-03-08.
 */

public class UserNameDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog, null))
                .setTitle("닉네임 입력")
                // Add action buttons
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)getDialog().findViewById(R.id.username);
                String userName = editText.getText().toString();
                new CheckUserName().execute(userName);
            }
        });
        return dialog;
    }

    public class CheckUserName extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            int result = Global.databaseController.checkUserName(params[0]);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == -1) {
                Toast.makeText(Global.context, "네트워크 상태를 확인해 보십시오.", Toast.LENGTH_LONG).show();
            } else if(result == 0) {
                Toast.makeText(Global.context, "동일한 닉네임이 존재합니다. 다른 닉네임을 입력하십시오.", Toast.LENGTH_LONG).show();
            } else if(result == 1) {
                Toast.makeText(Global.context, "성공했습니다.", Toast.LENGTH_LONG).show();

                dismiss();
            }
        }
    }
}
