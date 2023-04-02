package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;


public class GraphCobaltActivity extends AppCompatActivity {

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Получить ссылку на ActionBar
        ActionBar actionBar = getSupportActionBar();

// Установить цвет фона ActionBar
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

// Установить иконку кнопки "вверх"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Установить цвет текста заголовка
        actionBar.setTitle(R.string.app_name);

//        chart = findViewById(R.id.chart);
//        chart = new LineChart(this);
//        // chart.addView(chart);
        Button button = findViewById(R.id.draw_button);
        chart = findViewById(R.id.chart);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {
                // Массив координат точек
                ArrayList<Entry> entriesFirst = new ArrayList<>();
                for (int i = 0; i < 365; i++) {
                    float val = (float) (Math.random() * 10);
                    entriesFirst.add(new Entry(i, val));
                }
                float maxVal = Float.MIN_VALUE;
                float minVal = Float.MAX_VALUE;
                for (Entry entry : entriesFirst) {
                    if (entry.getY() > maxVal) {
                        maxVal = entry.getY();
                    }
                    if (entry.getY() < minVal) {
                        minVal = entry.getY();
                    }
                }

                TextView max_value = findViewById(R.id.textViewMaxPrice);
                max_value.setText("Максимальная цена за период: " + String.format("%.2f", maxVal) + " долл./т");
                TextView min_value = findViewById(R.id.textViewMinPrice);
                min_value.setText("Минимальная цена за период: " + String.format("%.2f", minVal) + " долл./т");



//                chart.getDescription().setEnabled(false);
                chart.setTouchEnabled(true);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
                chart.setDrawGridBackground(false);
                chart.setPinchZoom(true);
                chart.setBackgroundColor(Color.parseColor("#fce4d9"));

                // На основании массива точек создадим первую линию с названием
                LineDataSet datasetFirst = new LineDataSet(entriesFirst, "Прогнозируемая цена");
                chart.setNoDataText("Построите график для отображения");
                chart.setNoDataTextColor(R.color.green);

                // Макс.мин. цены на графике
                float maxX = chart.getHighestVisibleX();

                // Получаем максимальное значение на оси Y
                float maxY = maxVal; // chart.getAxisLeft().getAxisMaximum();

                // Добавляем вертикальную линию для отображения максимальной цены
                LimitLine maxLimitLine = new LimitLine(maxY, "Максимальная цена");
                maxLimitLine.setLineColor(Color.RED);
                maxLimitLine.setLineWidth(2f);
                chart.getAxisLeft().addLimitLine(maxLimitLine);

                // Получаем координаты на оси X для точки с минимальной ценой
                // float minX = chart.getLowestVisibleX();

                // Получаем минимальное значение на оси Y
                float minY = minVal; // chart.getAxisLeft().getAxisMinimum();

                // Добавляем вертикальную линию для отображения минимальной цены
                LimitLine minLimitLine = new LimitLine(minY, "Минимальная цена");
                minLimitLine.setLineColor(Color.GREEN);
                minLimitLine.setLineWidth(2f);
                chart.getAxisLeft().addLimitLine(minLimitLine);


                // График будет заполненным
                datasetFirst.setDrawFilled(true);
                datasetFirst.setDrawIcons(false);
                datasetFirst.setDrawFilled(true);
                datasetFirst.setColor(Color.parseColor("#00b984"));
                datasetFirst.setLineWidth(2f);
                datasetFirst.setDrawCircles(false);
                datasetFirst.setDrawValues(false);
                datasetFirst.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                YAxis left = chart.getAxisLeft();
//                left.setDrawLabels(false); // no axis labels
//                left.setDrawAxisLine(false); // no axis line
                left.setDrawGridLines(false); // no grid lines
                left.setDrawZeroLine(true); // draw a zero line
                left.setSpaceTop(30);
                left.setSpaceBottom(30);
                chart.getAxisRight().setEnabled(false); // no right axis

                // Увеличиваем масштаб
                chart.getViewPortHandler().setMaximumScaleY(10f);
                chart.getViewPortHandler().setMaximumScaleX(10f);


                // Линии графиков соберем в один массив
                ArrayList<ILineDataSet> dataSets = new ArrayList();
                dataSets.add(datasetFirst);

                // Создадим переменную  данных для графика
                LineData data = new LineData(dataSets);
                // Передадим данные для графика в сам график
                chart.setData(data);
                chart.invalidate();

                // График будет анимироваться 0.5 секунды
                chart.animateY(500);


                // Не забудем отправить команду на перерисовку кадра, иначе график не отобразится
                // Но если график анимируется, то он отрисуется самостоятельно, так что
                // команда ниже не обязательна
            }
        });



//        // Создаем данные для графика
//        ArrayList<Entry> values = new ArrayList<>();
//
//
//
//        // Создаем LineDataSet и задаем ему параметры
//        LineDataSet set1 = new LineDataSet(values, "DataSet 1");

//
//        // Создаем LineData и добавляем в него наш LineDataSet
//        LineData data = new LineData(set1);
//
//
//        // Задаем некоторые параметры осей
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//
//        YAxis yAxis = chart.getAxisLeft();
//        yAxis.setDrawGridLines(false);
//
//        YAxis rightAxis = chart.getAxisRight();
//        rightAxis.setEnabled(false);
//
//        // Добавляем нашу LineData на график и обновляем его
//        chart.setData(data);
//        // chart.invalidate();
//
//        // Добавляем легенду на график
//        Legend legend = chart.getLegend();
//        legend.setEnabled(false);
//        chart.animateY(500);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
