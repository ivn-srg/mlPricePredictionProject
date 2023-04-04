package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "ik9iu6ou0ur5guyaug4clfnaj70yp1oztmh6qh8g5ajgv41p54vr1o6qvbwk";
    private MetalAPI metalApi;
    private TextView tvDollar, tvNickel, tvMolybdenum, tvCobalt;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // находим TextView для отображения цен
        tvDollar = findViewById(R.id.tvDollar);
        tvNickel = findViewById(R.id.tvNickel);
        tvMolybdenum = findViewById(R.id.tvMolybdenum);
        tvCobalt = findViewById(R.id.tvCobalt);

        // request
        // Создать объект Retrofit
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        // Создать экземпляр интерфейса MetalApi
        metalApi = retrofit.create(MetalAPI.class);

        // Выполнить запрос и обработать ответ
        Call<JsonObject> call = metalApi.getPrices(API_KEY, "USD", "NI,MO,LCO,RUB");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject prices = response.body().getAsJsonObject();
                    if (prices.get("rates") != null) {
                        // Получить цены активов
                        double nickelPrice = prices.get("rates").getAsJsonObject().get("NI").getAsDouble();
                        double dollarPrice = prices.get("rates").getAsJsonObject().get("RUB").getAsDouble();
                        double molybdenumPrice = prices.get("rates").getAsJsonObject().get("MO").getAsDouble();
                        double cobaltPrice = prices.get("rates").getAsJsonObject().get("LCO").getAsDouble();

                        tvDollar.setText(String.format("%.3f ₽", dollarPrice));
                        tvNickel.setText(String.format("%.3f $", nickelPrice));
                        tvMolybdenum.setText(String.format("%.3f $", molybdenumPrice));
                        tvCobalt.setText(String.format("%.3f $", cobaltPrice));

                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        Toast.makeText(MainActivity.this, "Нет информации о цене металлов", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            ;

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка получения данных о цене металлов", Toast.LENGTH_SHORT).show();
            }
        });



    // Получить ссылку на ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Установить цвет фона ActionBar
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        // Установить цвет текста заголовка
        actionBar.setTitle(R.string.app_name);

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

//        // Add swipe to refresh functionality
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Execute request and handle response
                Call<JsonObject> call = metalApi.getPrices(API_KEY, "USD", "NI,MO,LCO,RUB");
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            JsonObject prices = response.body().getAsJsonObject();
                            if (prices.get("rates") != null) {
                                // Получить цены активов
                                double nickelPrice = prices.get("rates").getAsJsonObject().get("NI").getAsDouble();
                                double dollarPrice = prices.get("rates").getAsJsonObject().get("RUB").getAsDouble();
                                double molybdenumPrice = prices.get("rates").getAsJsonObject().get("MO").getAsDouble();
                                double cobaltPrice = prices.get("rates").getAsJsonObject().get("LCO").getAsDouble();

                                tvDollar.setText(String.format("%.3f ₽", dollarPrice));
                                tvNickel.setText(String.format("%.3f $", nickelPrice));
                                tvMolybdenum.setText(String.format("%.3f $", molybdenumPrice));
                                tvCobalt.setText(String.format("%.3f $", cobaltPrice));

                                swipeRefreshLayout.setRefreshing(false);
                            } else {
                                Toast.makeText(MainActivity.this, "Нет информации о цене металлов", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    ;

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Ошибка получения данных о цене металлов", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });}
}