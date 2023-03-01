package com.example.practisesql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_ad;
    ListView lst;
    HashMap<String, String> dlitem;
    List<HashMap<String, String>> lstitem;
    DBManager dbManager;
    Cursor fetch;
    SimpleCursorAdapter adapter;


    final String [] fromDB = new String[]{
            DBHelper._ID,
            DBHelper._NAME,
            DBHelper._CURRENCY
    };

    final  int[] toUI = new int[]{
            R.id.no,
            R.id.txt1,
            R.id.txt2
    };
//    String[] arr= {"India","Japan","Korea","China","Pakistan","USA","Russia","Brazil"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.Open();

        Add_Dialog add_dialog = new Add_Dialog(this);

        FetchDatabase();

        fab_ad = findViewById(R.id.fab_addc);
        fab_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "FAB CLICK",Snackbar.LENGTH_LONG).show();

                add_dialog.show();

                registerForContextMenu(lst);
            }
        });
    }

    public void FetchDatabase() {

//        dbManager.Open();
        fetch = dbManager.fetch();
        dbManager.Close();

        adapter= new SimpleCursorAdapter(getApplicationContext(), R.layout.custom_list, fetch, fromDB, toUI);

        lst=findViewById(R.id.lst);
        lst.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== R.id.add){
            Toast.makeText(this, "Add clicked", Toast.LENGTH_SHORT).show();

            Add_Dialog add_dialog=new Add_Dialog(this);
            add_dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context,menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.edit:
                Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "Delete clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}