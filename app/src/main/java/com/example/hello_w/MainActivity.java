package com.example.hello_w;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);
        ArrayList<String> dishes = new ArrayList<>( );
        dishes.add("Indian");
        dishes.add("Italian");
        dishes.add("Chinese");
        dishes.add("Japanese");
        dishes.add("French");
        dishes.add("Spanish");
        dishes.add("Middle Eastern");
        dishes.add("Mexican");
        dishes.add("Greek");
        dishes.add("Korean");
        dishes.add("British");
        dishes.add("Filipino");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dishes);
        myListView.setAdapter(arrayAdapter);

    }
}