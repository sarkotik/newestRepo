package com.example.brian.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.id.message;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editEmail;
    EditText editPhone;
    Button btnAddData;
    Button searchButton;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);

        //Add layout variables
        editName = (EditText) findViewById(R.id.editText_name);
        editEmail = (EditText) findViewById(R.id.editText_email);
        editPhone = (EditText) findViewById(R.id.editText_phone);
        btnAddData = (Button) findViewById(R.id.button_add);
        editSearch = (EditText) findViewById(R.id.editText_search);
        searchButton = (Button) findViewById(R.id.button_search);

    }

    public void addData(View v)
    {
        boolean isInserted = myDb.insertData(editName.getText().toString(), editEmail.getText().toString(), editPhone.getText().toString());

        if(isInserted == true){
            Log.d("MyContact", "Success inserting data");
            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("MyContact", "Failure inserting data");
            //insert Toast message here:
        }
    }

    public void viewData(View v)
    {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No data is found in the database");
            Log.d("Error", "No data found in database");
            Toast.makeText(this, "no data in database", Toast.LENGTH_SHORT).show();

            //output a message using log d and Toast
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //set up a loop with the cursor (res), using method moveToNext
        //   append each clmn to the buffer
        // display message using showMessage
        if (res != null)
        {
            res.moveToFirst();
            for (int i = 0; i < res.getCount(); i++) {
                for (int j = 0; j < res.getColumnNames().length; j++) {
                    buffer.append(res.getString(j) + "\n");
                }
                buffer.append("\n");
                res.moveToNext();
            }
        }
        showMessage("Data", buffer.toString());

    }

    private void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true); //cancel using back button
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void search(View v)
    {

        Cursor c = myDb.getAllData();
        StringBuffer b = new StringBuffer();

        if (c != null)
        {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++)
            {
                if (c.getString(1).equals(editSearch.getText().toString()))
                {
                    for (int j = 0; j < c.getColumnNames().length; j++)
                    {
                        b.append(c.getString(j) + "\n");
                    }
                    b.append("\n");
                }
                c.moveToNext();
            }
            showMessage("Contact Name: " + editSearch.getText().toString(), b.toString());
        }
    }
}
