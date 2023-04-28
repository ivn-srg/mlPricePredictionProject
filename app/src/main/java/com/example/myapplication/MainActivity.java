package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public static final String TAG = MainActivity.class.getSimpleName();
    public int numberOfKey;
    public String result;
    public boolean successResponse;
    /* access modifiers changed from: private */
    public SwipeRefreshLayout swipeRefreshLayout;
    /* access modifiers changed from: private */
    public TextView tvCobalt;
    /* access modifiers changed from: private */
    public TextView tvDollar;
    /* access modifiers changed from: private */
    public TextView tvMolybdenum;
    /* access modifiers changed from: private */
    public TextView tvNickel;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.tvDollar = findViewById(R.id.tvDollar);
        this.tvNickel = findViewById(R.id.tvNickel);
        this.tvMolybdenum = findViewById(R.id.tvMolybdenum);
        this.tvCobalt = findViewById(R.id.tvCobalt);
        new DownloadPageTask().execute("https://metallicheckiy-portal.ru/servis/mp_inform_lme3.php?p2=2&p10=2&p11=2&p12=2&p3=2&p4=2");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        actionBar.setTitle(R.string.app_name);
        Button btnModel = findViewById(R.id.button_model1);
        btnModel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FieldManager.class);
                intent.putExtra("model", "Model Co");
                MainActivity.this.startActivity(intent);
            }
        });
        SwipeRefreshLayout swipeRefreshLayout2 = findViewById(R.id.swipe_refresh_layout);
        this.swipeRefreshLayout = swipeRefreshLayout2;
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                MainActivity.this.swipeRefreshLayout.setColorSchemeColors();
                new DownloadPageTask().execute("https://metallicheckiy-portal.ru/servis/mp_inform_lme3.php?p2=2&p10=2&p11=2&p12=2&p3=2&p4=2");
                MainActivity.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];
            Element name = null;
            Element price = null;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("tr"); // выбираем все строки таблицы
                StringBuilder sb = new StringBuilder();
                for (Element row : rows) {
                    Elements td = row.select("td");
                    if (td.size() >= 2) {
                        name = td.select("nobr").first(); // выбираем первый тег <b> в строке
                        price = td.select("nobr").get(1);

                    }
                    if (name != null && price != null) {
                        sb.append(name.text()).append(" - ").append(price.text()).append("\n"); // добавляем название и цену в StringBuilder
                    }
                    Element finalPrice = price;
                    switch (name.text()) {
                        case "Золото":

                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    tvDollar.setText(finalPrice.text() + " ₽/kg");
                                }
                            });
                            break;
                        case "Кобальт":
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    tvCobalt.setText(finalPrice.text() + " $/tn");
                                }
                            });
                            break;
                        case "Молибден":
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    tvMolybdenum.setText(Double.parseDouble(finalPrice.text()) * 1000 * 2.2 + " $/tn");
                                }
                            });
                            break;
                        case "Никель":
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    assert finalPrice != null;
                                    tvNickel.setText(finalPrice.text() + " $/tn");
                                }
                            });
                            break;
                    }
                }
                return sb.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error downloading page", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d(TAG, result);
            }
        }
    }
}
