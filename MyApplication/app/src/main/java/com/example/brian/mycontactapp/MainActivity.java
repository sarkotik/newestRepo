package com.example.brian.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editEmail;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);

        //Add layout variables
        editName = (EditText) findViewById(R.id.editText_name);
        editEmail = (EditText) findViewById(R.id.editText_email);

    }

    public void addData(View v)
    {
        boolean isInserted = myDb.insertData(editName.getText().toString(), editEmail.getText().toString());

        if(isInserted == true){
            Log.d("MyContact", "Success inserting data");
            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("MyContact", "Failure inserting data");
            //insert Toast message here:
        }
    }

    public void viewData(View d) {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No data is found in the database");
            //output a message using log d and Toast
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //set up a loop with the cursor (res), using method moveToNext
        //   append each clmn to the buffer
        // display message using showMessage
        showMessage("Data", buffer.toString());

    }

    private void showMessage(String error, String s){

    }
}
