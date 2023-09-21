package com.example.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTxtName, editTxtAge;
    private Switch switch_activeCustomer;
    private Button btnViewAll, btnAdd;
    private ListView customer_listView;

    private DatabaseHelper databaseHelper;

    ArrayAdapter<CustomerModel> customerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        initViews();

        databaseHelper = new DatabaseHelper(MainActivity.this);

        //the list of customers are shown when the app is loaded
        showCustomerOn_ListView(databaseHelper);

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

                //clear the fields after adding
                editTxtName.getText().clear();
                editTxtAge.getText().clear();
                switch_activeCustomer.setChecked(false);

                //automatically show the updated list view
                showCustomerOn_ListView(databaseHelper);

            }
        });

        //onclick view all
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                //get all data
                List<CustomerModel> allData = databaseHelper.retrieveData();

                //show list view
                showCustomerOn_ListView(databaseHelper);

                //Toast.makeText(MainActivity.this, allData.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        //onclick the listview. the item will be deleted after clicked
        customer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomerModel clickedCustomer= (CustomerModel) parent.getItemAtPosition(position);

                //delete record
                databaseHelper.deleteOne(clickedCustomer);

                //display the updated list view
                showCustomerOn_ListView(databaseHelper);

                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();


            }
        });


    }

    //show list view
    private void showCustomerOn_ListView(DatabaseHelper databaseHelper) {
        //adapter
        customerArrayAdapter= new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                databaseHelper.retrieveData());

        //set adapter
        customer_listView.setAdapter(customerArrayAdapter);
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