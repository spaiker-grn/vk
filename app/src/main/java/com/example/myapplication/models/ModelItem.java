package com.example.myapplication.models;

import com.example.myapplication.R;
import com.example.myapplication.serviceclasses.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дмитрий on 06.11.2017.
 */

//rename class
public final class ModelItem {

    private final String mDescription;
    private final int mImgSource;
    //    'm' prefix
    private final boolean img;

    private ModelItem(final String mDescription, final int mImgSource, final boolean img) {
        this.mDescription = mDescription;
        this.mImgSource = mImgSource;
        this.img = img;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImgSource() {
        return mImgSource;
    }

    public boolean isImg() {
        return img;
    }

    public static List<ModelItem> getFakeItems() {

        final String description = MyApplication.getAppContext().getResources().getString(R.string.lorem);
        final List<ModelItem> itemList = new ArrayList<>();
        itemList.add(new ModelItem(description, R.drawable.derevo_serdce, true));
        itemList.add(new ModelItem(description, R.drawable.dom_zima, true));
        itemList.add(new ModelItem(description, R.drawable.gora_rozovoe, false));
        itemList.add(new ModelItem(description, R.drawable.kanoe, false));
        itemList.add(new ModelItem(description, R.drawable.sklon_derevo, true));
        itemList.add(new ModelItem(description, R.drawable.tropiki_palmy_plyazh_priroda, false));
        itemList.add(new ModelItem(description, R.drawable.vodopad_priroda, true));

        return itemList;
    }
}
