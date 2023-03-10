package com.example.practisesql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Edit_details extends AppCompatActivity implements View.OnClickListener {



    Button upbtn,dltbtn;
    TextView edit_id;
    EditText edttxt;
    EditText edttxt1;

    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);


        setTitle("Modify Record");
        upbtn = findViewById(R.id.upbt);
        dltbtn = findViewById(R.id.dltbtn);

        edit_id = (TextView) findViewById(R.id.idtxt1);
        edttxt = (EditText) findViewById(R.id.cntry);
        edttxt1 = (EditText) findViewById(R.id.crncy);


        Intent intent = getIntent();

//      Bundle extras =  intent.getExtras();

        Bundle extras = intent.getBundleExtra("bundle");
      String id = extras.getString("id");
      String country = extras.getString("country");
      String currency = extras.getString("currency");






      edit_id.setText(id);
      edttxt.setText(currency);
      edttxt1.setText(country);



      upbtn.setOnClickListener(this);
      dltbtn.setOnClickListener(this);

      dbManager = new DBManager(this);
      dbManager.Open();




    }

    @Override
    public void onClick(View v) {

        Long _id = Long.valueOf(edit_id.getText().toString());

        String country = edttxt.getText().toString();
        String currency = edttxt1.getText().toString();


        switch (v.getId()){

            case R.id.upbt:

                dbManager.Update(_id,country,currency);
                dbManager.Close();

                ReturnHome();




                break;

            case R.id.dltbtn:


                dbManager.Delete(_id);
                dbManager.Close();

                ReturnHome();




                break;

        }


    }

    private void ReturnHome() {

        Intent intent = new Intent(Edit_details.this, MainActivity.class);
        startActivity(intent);
    }
}