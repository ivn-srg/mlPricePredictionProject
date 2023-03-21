package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


public class GraphCobaltActivity extends Activity {

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

//        chart = findViewById(R.id.chart);
//        chart = new LineChart(this);
//        // chart.addView(chart);
        Button button = findViewById(R.id.draw_button);
        Button btnReturn = findViewById(R.id.btnReturn);
        chart = findViewById(R.id.chart);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Массив координат точек
                ArrayList<Entry> entriesFirst = new ArrayList<>();
                for (int i = 0; i < 365; i++) {
                    float val = (float) (Math.random() * 10);
                    entriesFirst.add(new Entry(i, val));
                }
//                entriesFirst.add(new Entry(1f, 5f));
//                entriesFirst.add(new Entry(2f, 2f));
//                entriesFirst.add(new Entry(3f, 1f));
//                entriesFirst.add(new Entry(4f, -3f));
//                entriesFirst.add(new Entry(5f, 4f));
//                entriesFirst.add(new Entry(6f, 1f));

//                chart.getDescription().setEnabled(false);
                chart.setTouchEnabled(true);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
                chart.setDrawGridBackground(false);
                chart.setPinchZoom(true);
                chart.setBackgroundColor(Color.parseColor("#fce4d9"));

                // На основании массива точек создадим первую линию с названием
                LineDataSet datasetFirst = new LineDataSet(entriesFirst, "Прогнозируемая цена");
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
                chart.setNoDataText("Построите график для отображения");
                chart.setNoDataTextColor(R.color.white);

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

        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
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
}
