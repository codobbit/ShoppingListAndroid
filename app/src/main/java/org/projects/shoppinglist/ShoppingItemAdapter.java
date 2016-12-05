package org.projects.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Alice on 20/09/2016.
 */
public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {
    public Context context;

    public ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> listItems){
        super(context,0,listItems);
        this.context = context;
    }
    public ShoppingItemAdapter getShoppingItemAdapter(){
        return this;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ShoppingItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapted_list_view, parent, false);
        }
        return convertView; }


}
