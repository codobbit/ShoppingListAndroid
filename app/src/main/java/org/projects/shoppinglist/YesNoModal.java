package org.projects.shoppinglist;

/**
 * Created by Alice on 10/11/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.widget.EditText;

public class YesNoModal {
    AlertDialog.Builder alert;
    Resources resources;
    String userInput="";


    public YesNoModal(Context context, String title, String message, String defaultInput) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        final EditText input = new EditText(context);
        input.setText(defaultInput);
        alert.setView(input);

        resources = context.getResources();

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userInput = input.getText().toString();
                clickYes();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                clickNo();
            }
        });

    }

    public YesNoModal(Context context, String title, String message) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        resources = context.getResources();
        final EditText input = new EditText(context);


        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userInput = input.getText().toString();
                clickYes();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                clickNo();
            }
        });


    }

    public void clickYes()
    {

    }

    public void clickNo()
    {
    }


    public void show()
    {
        alert.show();
    }

}
