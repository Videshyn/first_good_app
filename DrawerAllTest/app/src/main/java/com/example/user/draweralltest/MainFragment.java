package com.example.user.draweralltest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by User on 31.08.2017.
 */

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.main, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.main_img);
        img.setImageResource(R.drawable.hello);
        TextView textView = (TextView) view.findViewById(R.id.main_txt);
        textView.setText("The app welcomes you");
        return view;
    }
}
