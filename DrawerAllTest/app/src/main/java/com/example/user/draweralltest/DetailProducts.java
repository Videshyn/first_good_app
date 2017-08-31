package com.example.user.draweralltest;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by User on 31.08.2017.
 */

public class DetailProducts extends Activity implements View.OnClickListener{
    private TextView name;
    private TextView description;
    private TextView price;
    private ImageView img;
    private Button btnAdd;
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_products);

        initUI();
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String pname = getIntent().getStringExtra("p_name");
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * from Products WHERE p_name=?", new String[] {pname});
        if (cursor != null){
            cursor.moveToLast();
                int nameColumnIndex = cursor.getColumnIndex("p_name");
                int priceColumnIndex = cursor.getColumnIndex("p_price");
                int imgColumnIndex = cursor.getColumnIndex("p_link");
                int descriptionColumnIndex = cursor.getColumnIndex("p_discription");
                name.setText(cursor.getString(nameColumnIndex));
                price.setText(cursor.getString(priceColumnIndex));
                description.setText(cursor.getString(descriptionColumnIndex));
                Glide.with(this).load(cursor.getString(imgColumnIndex)).into(img);
        }
        cursor.close();
        database.close();
        dbHelper.close();

        btnAdd.setOnClickListener(this);
    }

    private  void initUI(){
        name = (TextView) findViewById(R.id.full_product_name);
        price = (TextView) findViewById(R.id.full_products_price);
        description = (TextView) findViewById(R.id.description);
        img = (ImageView) findViewById(R.id.full_products_img);
        btnAdd = (Button) findViewById(R.id.btn_basket);
        dbHelper = new DbHelper(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_basket){
            Toast.makeText(DetailProducts.this, "Added", Toast.LENGTH_SHORT).show();
            try {
                dbHelper.createDataBase();
                dbHelper.openDataBase();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            database = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("p_basket", "true");
            int id = database.update("Products", cv, "p_name=?", new String[] {name.getText().toString()});
            database.close();
            dbHelper.close();
        }
    }
}
