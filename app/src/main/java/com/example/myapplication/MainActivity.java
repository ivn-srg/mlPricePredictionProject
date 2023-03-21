package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonModel1 = findViewById(R.id.button_model1);
        Button buttonModel2 = findViewById(R.id.button_model2);
        Button buttonModel3 = findViewById(R.id.button_model3);

        buttonModel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setContentView(R.layout.activity_graph);
                Intent intent = new Intent(MainActivity.this, GraphCobaltActivity.class);
                intent.putExtra("model", "Model 1");
                startActivity(intent);
            }
        });

        buttonModel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GraphCobaltActivity.class);
                intent.putExtra("model", "Model 2");
                startActivity(intent);
            }
        });

        buttonModel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GraphCobaltActivity.class);
                intent.putExtra("model", "Model 3");
                startActivity(intent);
            }
        });
    }
}