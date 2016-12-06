package org.projects.shoppinglist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alice on 20/09/2016.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "com.example.StateChange";
    private Context context = this;
    private DatabaseReference firebase;

    //    ArrayAdapter<ShoppingItem> adapter;
    ListView listView;
    ArrayList<ShoppingItem> bag = new ArrayList<ShoppingItem>();

    FirebaseListAdapter<ShoppingItem> firebaseListAdapter;

    String modalItemName="";
    int modalItemQuantity=0;
    boolean newModalOpen;

//    EditText editText;
//    EditText quantityField;

    //material menu
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton addButton, removeButton, settingsButton, clearButton;

    public FirebaseListAdapter getMyAdapter() {
        return firebaseListAdapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getActionBar().setHomeButtonEnabled(true); //this means we can click "home"
        firebase = FirebaseDatabase.getInstance().getReference().child("items");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.floating_menu);

        addButton = (FloatingActionButton) findViewById(R.id.material_edit_new);
        removeButton = (FloatingActionButton) findViewById(R.id.material_delete);
        settingsButton = (FloatingActionButton) findViewById(R.id.material_settings);
        clearButton = (FloatingActionButton) findViewById(R.id.material_clear);

        listView = (ListView) findViewById(R.id.list);


        firebaseListAdapter = new FirebaseListAdapter<ShoppingItem>(this, ShoppingItem.class, android.R.layout.simple_list_item_checked, firebase) {
            @Override
            protected void populateView(View view, ShoppingItem item, int position) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1); //standard android id.
                textView.setText(item.toString());
            }
        };

        getMyAdapter().notifyDataSetChanged();

        listView.setAdapter(firebaseListAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

//        editText = (EditText) findViewById(R.id.yourShoppingItem);
//        quantityField = (EditText) findViewById(R.id.editQuantity);

        addButton.setOnClickListener(ClickAddButton);
        removeButton.setOnClickListener(ClickRemoveButton);
        settingsButton.setOnClickListener(ClickSettingsButton);
        clearButton.setOnClickListener(ClearAllButton);
    }


    View.OnClickListener ClickAddButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          AddItemModal();
        }
    };

    View.OnClickListener ClickRemoveButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listView.getCheckedItemPosition() != ListView.INVALID_POSITION) {
                int index = listView.getCheckedItemPosition();
                getMyAdapter().getRef(index).setValue(null);
            } else {
                Toast.makeText(getApplicationContext(), "Select an item first.", Toast.LENGTH_SHORT).show();
            }
            getMyAdapter().notifyDataSetChanged();
        }
    };

    View.OnClickListener ClickSettingsButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         viewSettings(v);
        }
    };


    View.OnClickListener ClearAllButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearList();
        }
    };

    private ArrayList<ShoppingItem> AddToFirebase(String text, int count) {
        boolean found = false;
        for (int i = 0; i < firebaseListAdapter.getCount(); i++) {
            if (firebaseListAdapter.getItem(i).getItemName().equalsIgnoreCase(text)) {
                int newcount = firebaseListAdapter.getItem(i).getItemCount() + count;
                firebaseListAdapter.getItem(i).SetItemCount(newcount);
                found = true;
            }
        }
        if (!found) {
            firebase.push().setValue(new ShoppingItem(text, count));
        }
        getMyAdapter().notifyDataSetChanged();
        return bag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Action bar
//Here we put the code for what should happen in the app once
//the user selects one of our menu items (regardless of whether
//it is in the actionbar or in the overflow menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItem_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, sendList());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

//            case R.id.menuItem_settings:
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivityForResult(intent, 1);
//
//                return true;
//            case R.id.menuItem_clearList:
//                clearList();
//                return true;
        }

        return false; //we did not handle the event
    }
//opens the settings fragment
    public void viewSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, 1);
    }
        // Modal for clearing the list with Are you sure? Y/N
    public void clearList() {
        YesNoModal dialog = new YesNoModal(this, "", "Are you sure you want to clear the list? ") {
            @Override
            public void clickNo() {
                super.clickNo();
            }

            @Override
            public void clickYes() {
                for (int i = 0; i < firebaseListAdapter.getCount(); i++) {
                    getMyAdapter().getRef(i).setValue(null);
                }
                getMyAdapter().notifyDataSetChanged();
                super.clickYes();
            }
        };
        dialog.show();


    }

    public void AddItemModal(){
       final AddNewItemModal newItemModal = new AddNewItemModal(this, "New item",modalItemName, modalItemQuantity) {
        @Override
        public void clickNo() {
            newModalOpen = false;
            super.clickNo();
        }

        @Override
        public void clickYes() {

            modalItemName = this.newItemName;
            modalItemQuantity = Integer.valueOf(this.newItemQuantity.toString());
            AddToFirebase(modalItemName, modalItemQuantity);
            newModalOpen = false;

            super.clickYes();
        }
    };
        newModalOpen = true;
        newItemModal.show();
}
    //Persistance
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //ALWAYS CALL THE SUPER METHOD
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        /* Here we put code now to save the state */
//        outState.putInt("quantity", modalItemQuantity);
//        outState.putString("item", modalItemName);
        outState.putBoolean("isModalOpen", newModalOpen);

        outState.putParcelableArrayList("savedBag", bag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        //MOST UI elements will automatically store the information
        //if we call the super.onRestoreInstaceState
        //but other data will be lost.
        super.onRestoreInstanceState(savedState);
        Log.i(TAG, "onRestoreInstanceState");
//        this.modalItemName= savedState.getString("item");
//        this.modalItemQuantity= savedState.getInt("quantity");

        this.bag.clear();
        ArrayList<ShoppingItem> items = savedState.getParcelableArrayList("savedBag");
        for (ShoppingItem item : items
                ) {
            AddToFirebase(item.getItemName(), item.getItemCount());
        }
        getMyAdapter().notifyDataSetChanged();
        if(savedState.getBoolean("isModalOpen")){
            AddItemModal();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
//Preferences
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode==1) //from settings
    {
        String name = PreferencesFragment.getName(this);
        String message = "Welcome, "+name;
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_LONG);
        toast.show();
    }
    super.onActivityResult(requestCode, resultCode, data);
}

    //send lise
    public String sendList(){
        String list= "";
        for (int i = 0; i < firebaseListAdapter.getCount(); i++) {
            list += firebaseListAdapter.getItem(i).toString() + "\n" ;
        }
        return list;
    }



}
