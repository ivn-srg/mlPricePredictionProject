package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.internal.view.SupportMenu;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpStatus;

public class GraphCobaltActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public LineChart chart;
    public List<Date> dateList = new ArrayList();
    public ArrayList<String> dates = new ArrayList<>();
    public ArrayList<Double> katalize = new ArrayList<>();
    public ArrayList<Double> kobaltPrices = new ArrayList<>();
    public ArrayList<Double> kursPrices = new ArrayList<>();
    public ArrayList<Double> molPrices = new ArrayList<>();
    public ArrayList<Double> nickelPrices = new ArrayList<>();
    public List<String> numberList = new ArrayList();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Double godt = Double.parseDouble(getIntent().getStringExtra("inputgodt"));
        Double normCo = Double.parseDouble(getIntent().getStringExtra("inputNormCo"));
        Double normKa = Double.parseDouble(getIntent().getStringExtra("inputNormKa"));
        Double normMo = Double.parseDouble(getIntent().getStringExtra("inputNormMo"));
        Double normMoO = Double.parseDouble(getIntent().getStringExtra("inputNormMoO"));

        parsePriceData("prognos_nikel.txt", dates, this.nickelPrices);

        parsePriceData("prognos_kurs.txt", this.dates, this.kursPrices);

        parsePriceData("prognos_kobalt.txt", this.dates, this.kobaltPrices);

        parsePriceData("prognos_mol.txt", this.dates, this.molPrices);
        for (int i = 0; i < this.kursPrices.size(); i++) {
            katalize.add(godt + (kursPrices.get(i) * kobaltPrices.get(i) * normCo * normKa / 1000.0d) + (kursPrices.get(i) * molPrices.get(i) * normMoO * normMo / 1000.0d));
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("График цены катализатора");
        LineChart lineChart = findViewById(R.id.chart);
        this.chart = lineChart;
        lineChart.setNoDataText("Постройте график для отображения");
        this.chart.setNoDataTextColor(getResources().getColor(R.color.material_dynamic_neutral50));
        Button drawButton = findViewById(R.id.draw_button);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"DefaultLocale", "SetTextI18n", "RestrictedApi"})
            public void onClick(View v) {
                ArrayList<Entry> entriesFirst = new ArrayList<>();
                for (int i = 0; i < katalize.size(); i++) {
                    entriesFirst.add(new Entry(i, katalize.get(i).floatValue()));
                }
                float maxVal = Float.MIN_VALUE;
                float minVal = Float.MAX_VALUE;
                Iterator<Entry> it = entriesFirst.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    if (entry.getY() > maxVal) {
                        maxVal = entry.getY();
                    }
                    if (entry.getY() < minVal) {
                        minVal = entry.getY();
                    }
                }
                TextView maxPrice = findViewById(R.id.textViewMaxPrice);
                maxPrice.setText("Максимальная цена за период: " + String.format("%1$,.2f", maxVal) + " ₽/кг");
                TextView minPrice = findViewById(R.id.textViewMinPrice);
                minPrice.setText("Минимальная цена за период: " + String.format("%1$,.2f", minVal) + " ₽/кг");

                chart.setTouchEnabled(true);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
                chart.setDrawGridBackground(false);
                chart.setPinchZoom(true);
                chart.setBackgroundColor(getResources().getColor(R.color.white));
                chart.getLegend().setEnabled(false);   // Hide the legend
                chart.getDescription().setText("");

                LineDataSet datasetFirst = new LineDataSet(entriesFirst, "Прогнозируемая цена");
                float highestVisibleX = chart.getHighestVisibleX();
                LimitLine maxLimitLine = new LimitLine(maxVal, "Максимальная цена");
                maxLimitLine.setLineColor(SupportMenu.CATEGORY_MASK);
                maxLimitLine.setLineWidth(2.0f);
                chart.getAxisLeft().addLimitLine(maxLimitLine);
                LimitLine minLimitLine = new LimitLine(minVal, "Минимальная цена");
                minLimitLine.setLineColor(getColor(R.color.blue));
                minLimitLine.setLineWidth(2.0f);
                chart.getAxisLeft().addLimitLine(minLimitLine);
                datasetFirst.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                datasetFirst.setDrawFilled(true);
                datasetFirst.setDrawIcons(false);
                datasetFirst.setDrawFilled(true);
                datasetFirst.setColor(Color.parseColor("#57a0ff"));
                datasetFirst.setLineWidth(2.0f);
                datasetFirst.setDrawCircles(false);
                datasetFirst.setDrawValues(false);
                YAxis left = chart.getAxisLeft();
                XAxis xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawLabels(false);

                left.setDrawGridLines(false);
                left.setDrawZeroLine(true);
                left.setSpaceTop(5.0f);
                left.setSpaceBottom(5.0f);
                chart.getAxisRight().setEnabled(false);
                chart.getViewPortHandler().setMaximumScaleY(10.0f);
                chart.getViewPortHandler().setMaximumScaleX(10.0f);
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(datasetFirst);
                chart.setData(new LineData((List<ILineDataSet>) dataSets));
                chart.invalidate();
                chart.animateX(1000);


//                chart.setTouchEnabled(true);
//                chart.setDragEnabled(true);
//                chart.setScaleEnabled(true);
//                chart.setDrawGridBackground(false);
//                chart.setPinchZoom(true);
//                chart.setBackgroundColor(getResources().getColor(R.color.white));
//                Description description = new Description();
//                description.setText("");
//                chart.setDescription(description);
//                chart.getAxisLeft().setDrawLabels(false);
//                chart.getXAxis().setDrawLabels(false);
//
//                chart.getLegend().setEnabled(false);   // Hide the legend
//
//                LineDataSet datasetFirst = new LineDataSet(entriesFirst, "Прогнозируемая цена");
//                float highestVisibleX = chart.getHighestVisibleX();
//                LimitLine maxLimitLine = new LimitLine(maxVal, "Максимальная цена");
//                maxLimitLine.setLineColor(SupportMenu.CATEGORY_MASK);
//                maxLimitLine.setLineWidth(2.0f);
//                chart.getAxisLeft().addLimitLine(maxLimitLine);
//                LimitLine minLimitLine = new LimitLine(minVal, "Минимальная цена");
//                minLimitLine.setLineColor(getColor(R.color.blue));
//                minLimitLine.setLineWidth(2.0f);
//                chart.getAxisLeft().addLimitLine(minLimitLine);
//                datasetFirst.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//                datasetFirst.setDrawFilled(true);
//                datasetFirst.setDrawIcons(false);
//                datasetFirst.setDrawFilled(true);
//                datasetFirst.setColor(Color.parseColor("#57a0ff"));
//                datasetFirst.setLineWidth(2.0f);
//                datasetFirst.setDrawCircles(false);
//                datasetFirst.setDrawValues(false);
//                YAxis left = chart.getAxisLeft();
//                XAxis xAxis = chart.getXAxis();
//
//                // Свойства оси X
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                xAxis.setGranularity(1f);
//                xAxis.setLabelCount(dateList.size(), true);
//                xAxis.setAvoidFirstLastClipping(true);
//                xAxis.setDrawGridLines(false);
//                xAxis.setDrawLabels(true);
//                xAxis.setLabelRotationAngle(-45);
//                xAxis.setTextSize(10f);
//                xAxis.setTextColor(Color.BLACK);
//                xAxis.setCenterAxisLabels(true);
//                xAxis.setDrawAxisLine(true);
//                xAxis.setAxisLineColor(Color.BLACK);
//                xAxis.enableGridDashedLine(10f, 10f, 0f);
//                xAxis.setAxisMinimum(0f);
//                xAxis.setAxisMaximum(dates.size() - 1);
//                xAxis.setLabelCount(dates.size());
//
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                left.setDrawGridLines(false);
//                left.setDrawZeroLine(true);
//                left.setSpaceTop(5.0f);
//                left.setSpaceBottom(5.0f);
//                chart.getAxisRight().setEnabled(false);
//                chart.getViewPortHandler().setMaximumScaleY(10.0f);
//                chart.getViewPortHandler().setMaximumScaleX(10.0f);
//                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//                dataSets.add(datasetFirst);
//                chart.setData(new LineData(dataSets));
//                chart.invalidate();
//                chart.animateX(1000);
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void parsePriceData(String filename, ArrayList<String> dates2, ArrayList<Double> prices) {
        try {
            InputStream inputStream = getAssets().open(filename);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    try {
                        String readLine = reader.readLine();
                        String line = readLine;
                        if (readLine != null) {
                            String[] parts = line.split("\t");
//                            @SuppressLint("SimpleDateFormat") Date  new SimpleDateFormat("yyyy-MM-dd").parse(parts[0]);
                            String date = parts[0];
                            Double value = Double.parseDouble(parts[1]);
                            if (!dates2.isEmpty()) {
                                dates2.clear();
                            }
                            dates2.add(date);
                            prices.add(value);
                        } else {
                            reader.close();
                            inputStream.close();
                            reader.close();
                            return;
                        }
                    } catch (Throwable th) {
                        reader.close();
                        throw th;
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (Throwable th2) {
                th2.addSuppressed(th2);
            }
        } catch (IOException e3) {
            throw new RuntimeException(e3);
        }
    }
}
