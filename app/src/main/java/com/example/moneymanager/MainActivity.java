package com.example.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

Button b1;
TextView t1;
TextView t2;
    int finalprice=9;
    Button btnAdd, btnClear;
    ListView lstView;
    EditText txtCompanyName;
    DatabaseController dbController;
    SharedPreferences sp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=findViewById(R.id.button1);
        txtCompanyName = (EditText) findViewById(R.id.txtCompanyName);
        lstView = (ListView) findViewById(R.id.lstview);
        t1=findViewById(R.id.textView2);
        Bundle extras = getIntent().getExtras();
        String userName="";
        int price=0;
        sp1 = getApplicationContext().getSharedPreferences("Price", Context.MODE_PRIVATE);


        if (extras != null) {
            userName = extras.getString("data");
            price=extras.getInt("price");
            finalprice=extras.getInt("final");

        }
        t1.setText(sp1.getInt("pp",0)+"");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);

            }
        });




        try {
            if (TextUtils.isEmpty(userName))
                Toast.makeText(this, "Enter Money", Toast.LENGTH_SHORT).show();
            else {
                dbController = new DatabaseController(this);
                String s = dbController.InsertData("Spent Rs"+" "+price+" "+"on "+userName);
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                FillList();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        FillList();

}
    public void FillList() {
        try {
            int[] id = {R.id.txtListElement};
            String[] CompanyName = new String[]{"CompanyName"};
            if (dbController == null)
                dbController = new DatabaseController(this);
            SQLiteDatabase sqlDb = dbController.getReadableDatabase();
            Cursor c = dbController.getCompanies();
            Log.w("", c.toString());

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.list_template, c, CompanyName, id, 0);
            lstView.setAdapter(adapter);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }



}