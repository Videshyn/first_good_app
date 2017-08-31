package com.example.user.draweralltest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by User on 30.08.2017.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();
    private ArrayList<String> prices = new ArrayList<>();
    private Context context;


    public RecAdapter(Context context) {
        this.context = context;
        fillArrays();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.img);

        Glide.with(context).load(links.get(position)).into(imageView);

        TextView name = (TextView) cardView.findViewById(R.id.product_name);
        TextView price = (TextView) cardView.findViewById(R.id.product_price);
        name.setText(names.get(position));
        price.setText(prices.get(position));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProducts.class);
                intent.putExtra("p_name", names.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    private void fillArrays(){
        DbHelper dbHelper = new DbHelper(context);

        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query("Products", null, null, null, null, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                int namesColumnIndex = cursor.getColumnIndex("p_name");
                int linksColumnIndex = cursor.getColumnIndex("p_link");
                int pricesColumnIndex = cursor.getColumnIndex("p_price");
                do{
                    links.add(cursor.getString(linksColumnIndex));
                    names.add(cursor.getString(namesColumnIndex));
                    prices.add(cursor.getString(pricesColumnIndex));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
    }

}
