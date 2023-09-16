package com.example.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTxtName, editTxtAge;
    private Switch switch_activeCustomer;
    private Button btnViewAll, btnAdd;
    private ListView customer_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        initViews();

        //on click add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerModel customerModel;

                try {

                //get from edit text
                int id= -1;
                String name= editTxtName.getText().toString();
                int age= Integer.parseInt( editTxtAge.getText().toString() );
                boolean isChecked= switch_activeCustomer.isChecked();


                     customerModel= new CustomerModel(id, name, age, isChecked );

                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "error creating customer", Toast.LENGTH_SHORT).show();

                    //for tracking the error
                    customerModel=new CustomerModel(-1, "error", 0, false);
                }

                //DB
                DatabaseHelper databaseHelper= new DatabaseHelper(MainActivity.this);

                boolean isAdded_toDB = databaseHelper.addOne(customerModel);

                Toast.makeText(MainActivity.this, "added to the db? "+ isAdded_toDB, Toast.LENGTH_SHORT).show();

            }
        });

        //onclick view all
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "View all", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //init
    private void initViews(){
        editTxtName= findViewById(R.id.editTxtName);
        editTxtAge= findViewById(R.id.editTxtAge);

        switch_activeCustomer= findViewById(R.id.switch_activeCustomer);

        btnViewAll= findViewById(R.id.btnViewAll);
        btnAdd= findViewById(R.id.btnAdd);

        customer_listView= findViewById(R.id.customer_listView);

    }
}