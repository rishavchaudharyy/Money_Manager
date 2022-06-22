package com.example.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

;

public class MainActivity2 extends AppCompatActivity {
    Button btnAdd, btnClear;
    ListView lstView;
    EditText txt;
    EditText price;
    DatabaseController dbController;
    SharedPreferences sp;
    SharedPreferences sp1;
    int pr=0;
    int prEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
       // btnClear = (Button) findViewById(R.id.btnClear);
        txt = (EditText) findViewById(R.id.txtCompanyName);
        price =(EditText) findViewById(R.id.price);

        lstView = (ListView) findViewById(R.id.lstview);
       // prEdit=Integer.valueOf(price.getText().toString());
        sp1 = getApplicationContext().getSharedPreferences("Price", Context.MODE_PRIVATE);
        sp = getSharedPreferences("Price", Context.MODE_PRIVATE);





    }

    public void AddData(View v) {
        prEdit = Integer.valueOf(price.getText().toString());
        SharedPreferences.Editor editor = sp.edit();
        pr = sp1.getInt("pp", 0);
        editor.putInt("pp", pr + prEdit);
        editor.commit();
        if (price != null && txt != null) {

            Intent i = new Intent(MainActivity2.this, MainActivity.class);
            i.putExtra("data", txt.getText().toString());
            i.putExtra("price", Integer.valueOf(price.getText().toString()));
            i.putExtra("total", pr);
            startActivity(i);
        }
    }

    public void ClearData(View v) {
        txt.setText("");
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