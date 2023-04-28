package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FieldManager extends AppCompatActivity {
    TextInputLayout godtLayout;
    TextInputLayout normCoLayout;
    TextInputLayout normKaLayout;
    TextInputLayout normMoLayout;
    TextInputLayout normMoOLayout;
    TextInputEditText tiel_name;
    TextInputEditText tiel_name1;
    TextInputEditText tiel_name2;
    TextInputEditText tiel_name3;
    TextInputEditText tiel_name4;
    TextInputEditText tiel_name5;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Ввод параметров");

        this.godtLayout = findViewById(R.id.til_name);
        this.normCoLayout = findViewById(R.id.til_name1);
        this.normKaLayout = findViewById(R.id.til_name2);
        this.normMoLayout = findViewById(R.id.til_name3);
        this.normMoOLayout = findViewById(R.id.til_name4);

        this.tiel_name = findViewById(R.id.el_name);
        this.tiel_name1 = findViewById(R.id.el_name1);
        this.tiel_name2 = findViewById(R.id.el_name2);
        this.tiel_name3 = findViewById(R.id.el_name3);
        this.tiel_name4 = findViewById(R.id.el_name4);
        ((Button) findViewById(R.id.next_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (FieldManager.this.tiel_name.getText().toString().isEmpty() ||
                        FieldManager.this.tiel_name1.getText().toString().isEmpty() ||
                        FieldManager.this.tiel_name2.getText().toString().isEmpty() ||
                        FieldManager.this.tiel_name3.getText().toString().isEmpty() ||
                        FieldManager.this.tiel_name4.getText().toString().isEmpty()) {
                    Toast.makeText(FieldManager.this, "Заполните, пожалуйста, все поля", Toast.LENGTH_LONG).show();

                    return;
                }
                Intent intent = new Intent(FieldManager.this, GraphCobaltActivity.class);
                intent.putExtra("inputgodt", FieldManager.this.tiel_name.getText().toString());
                intent.putExtra("inputNormCo", FieldManager.this.tiel_name1.getText().toString());
                intent.putExtra("inputNormKa", FieldManager.this.tiel_name2.getText().toString());
                intent.putExtra("inputNormMo", FieldManager.this.tiel_name3.getText().toString());
                intent.putExtra("inputNormMoO", FieldManager.this.tiel_name4.getText().toString());
                FieldManager.this.startActivity(intent);
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
