package com.example.matchpairsmemorygame;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class YourRecordsActivity extends AppCompatActivity {

    private ListView lvRecords;
    private List<String> recordsList;
    Button btBack;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_records);

        lvRecords = findViewById(R.id.lvRecords);
        btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener(v -> {
            Intent intent = new Intent(YourRecordsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        recordsList = new ArrayList<>();


        try {
            // Open the database
            db = SQLiteDatabase.openDatabase("/data/data/com.example.matchpairsmemorygame/GamesLog", null, SQLiteDatabase.CREATE_IF_NECESSARY);

            // Select all records from the GamesLog table
            String selectRecords = "SELECT * FROM GamesLog";
            Cursor cursor = db.rawQuery(selectRecords, null);//no where , use null
            //after database query, would return a cursor object as a result

            // Add the records to the recordsList
            while (cursor.moveToNext()) {
                String record = String.format("%s, %s, %d moves", cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                recordsList.add(record);
            }
            cursor.close();
            db.close();

        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Display the records in the ListView using an ArrayAdapter
        ArrayAdapter<String> adapter;
        if (recordsList.isEmpty()) {
            recordsList.add("No record has been found");
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordsList);
        } else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordsList);
        }
        // android.R.layout.simple_list_item_1 specifies the appearance of each item in the ListView.
        lvRecords.setAdapter(adapter);//displays the data in the ListView.
    }
}
