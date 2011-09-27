package com.studiomobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

import java.util.ArrayList;

public class EyeActivity extends Activity {
    final ArrayList<FluffyImageListAdapter.ImageListItem> listItems = new ArrayList<FluffyImageListAdapter.ImageListItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listItems.add(new FluffyImageListAdapter.ImageListItem(R.drawable.fluffy_widget, "fluffy normal"));
        listItems.add(new FluffyImageListAdapter.ImageListItem(R.drawable.fluffy_pressed, "fluffy pressed"));
        listItems.add(new FluffyImageListAdapter.ImageListItem(R.drawable.fluffy_with_message, "new incoming message"));

        setContentView(R.layout.list_view);
        setTitle("Fluffy states");
        FluffyImageListAdapter adapter = new FluffyImageListAdapter(this, listItems);
        ListView listView = (ListView) this.findViewById(R.id.imageListView);
        listView.setAdapter(adapter);
    }
}
