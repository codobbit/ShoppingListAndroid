package org.projects.shoppinglist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Alice on 05/12/2016.
 */

public class AddNewItemModal {
    AlertDialog.Builder alert;
    Resources resources;
    String newItemName="";
    String newItemQuantity="";


    public AddNewItemModal(Context context, String title, String savedItemName, int savedItemQuantity) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(title);

//        create a layout for the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText itemName = new EditText(context);
        itemName.setHint("e.g. Apple");
        layout.addView(itemName);

        final EditText itemQuantity = new EditText(context);
        itemQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemQuantity.setHint("10");
        layout.addView(itemQuantity);


        if (savedItemName != ""){
            itemName.setText(savedItemName);
        }
        if (savedItemQuantity > 0){
            itemQuantity.setText(savedItemQuantity);
        }

        alert.setView(layout);

        resources = context.getResources();

        alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                newItemName = itemName.getText().toString();
                newItemQuantity = itemQuantity.getText().toString();
                clickYes();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
