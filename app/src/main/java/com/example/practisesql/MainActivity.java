package com.example.practisesql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
//        dbManager.Open();

        Add_Dialog add_dialog = new Add_Dialog(this);



        fab_ad = findViewById(R.id.fab_addc);

        lst = findViewById(R.id.lst);
        fab_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "FAB CLICK",Snackbar.LENGTH_LONG).show();

                add_dialog.show();


            }
        });

        FetchDatabase();
    }

    public void FetchDatabase() {

        dbManager.Open();
        Cursor  fetch = dbManager.fetch();
        dbManager.Close();

        adapter= new SimpleCursorAdapter(getApplicationContext(), R.layout.custom_list, fetch, fromDB, toUI);

        lst=findViewById(R.id.lst);
        registerForContextMenu(lst);
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

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();






        switch(item.getItemId())
        {
            case R.id.edit:
                Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show();



                TextView tv_id = (TextView) menuInfo.targetView.findViewById(R.id.no);
                TextView tv_currency= (TextView) menuInfo.targetView.findViewById(R.id.txt1);
                TextView tv_country = (TextView) menuInfo.targetView.findViewById(R.id.txt2);




                String id = tv_id.getText().toString();


                String country = tv_country.getText().toString();

                String currency = tv_currency.getText().toString();


                Intent modifyIntent = new Intent(MainActivity.this, Edit_details.class);

//                modifyIntent.putExtra("id",id);

                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("country", country);
                bundle.putString("currency", currency);



                modifyIntent.putExtra("bundle",bundle);

                startActivity(modifyIntent);





                break;
            case R.id.delete:
//                Toast.makeText(this, "Delete clicked", Toast.LENGTH_SHORT).show();

                TextView d_id = (TextView) menuInfo.targetView.findViewById(R.id.no);

                Long _id = Long.valueOf(d_id.getText().toString());

                dbManager.Open();
                dbManager.Delete(_id);
                dbManager.Close();

                startActivity(new Intent(MainActivity.this,MainActivity.class));
                Toast.makeText(this, "Deleted "+d_id.getText().toString(), Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onContextItemSelected(item);
    }
}