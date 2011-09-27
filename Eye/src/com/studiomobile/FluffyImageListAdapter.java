package com.studiomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class FluffyImageListAdapter extends BaseAdapter {
    private ArrayList<ImageListItem> items;
    private LayoutInflater inflater;


    public static class ImageListItem {
        private int imgId;
        private String comment;

        ImageListItem(int imgId, String comment) {
            this.imgId = imgId;
            this.comment = comment;
        }

        public int getImgId() {
            return imgId;
        }
        public String getComment() {
            return comment;
        }
    }

    public FluffyImageListAdapter(Context context, ArrayList<ImageListItem> items) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }


    public int getCount() {
        return items.size();
    }

    public Object getItem(int i) {
        return items.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout row = (LinearLayout) view;
        if (row == null) {
            row = (LinearLayout) inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        ImageListItem item = items.get(i);
        ImageView imageIcon = (ImageView) row.findViewById(R.id.image_of_fluffy);
        TextView imageComment = (TextView) row.findViewById(R.id.image_comment);
        imageComment.setText(item.getComment());
        imageIcon.setImageResource(item.getImgId());
        return row;
    }

}
