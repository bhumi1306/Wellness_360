package com.example.wellness360;

import static android.content.Context.ALARM_SERVICE;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Task;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Dashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView totalcalories;
    private TextView stepcountvalue;

    private int todaySteps = 0;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.getDefault());
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    private BarChart barChart;
    private int[] weeklyStepCounts = new int[7];

    CardView cd;
    private Button  choosedate, savedata;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "MyPrefs1";
    private static final String DATA_KEY_PREFIX = "data1_";
    private static final String PREFS_NAME = "StepCountsPrefs";
    private static final String STEP_COUNT_KEY_PREFIX = "step_count_";


    EditText bg, fd, ec;
    TextView today;
    PieChart pieChart;

    private ViewPager2 viewPager;
    private List<Integer> imageList = Arrays.asList(
            R.drawable.healthyfood,
            R.drawable.skincare,
            R.drawable.meditate,
            R.drawable.fit,
            R.drawable.nutrition,
            R.drawable.mindfood,
            R.drawable.breath
    );
    private String[] webPages = {
            "https://www.helpguide.org/articles/healthy-eating/eating-well-as-you-age.htm",
            "https://www.everydayhealth.com/skin-and-beauty/top-tips-for-healthy-winter-skin.aspx",
            "https://www.apa.org/topics/mindfulness/meditation",
            "https://www.medicalnewstoday.com/articles/best-exercises#dumbbell-rows",
            "https://www.healthline.com/nutrition/27-health-and-nutrition-tips#avoid-up-fs",
            "https://www.helpguide.org/articles/diets/mindful-eating.htm",
            "https://www.everydayhealth.com/wellness/deep-breathing/"
    };
    private static final long AUTO_SCROLL_DELAY = 3000;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;
    Button notify;
    private ArrayList<BarEntry> barEntriesArrayList = new ArrayList<>();
    ArrayList labels;
    Button datepicker;
    TextView sampletext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        totalcalories=(TextView) root.findViewById(R.id.goalsteps);
        stepcountvalue= root.findViewById(R.id.textView18);
        bg = (EditText) root.findViewById(R.id.bg);
        fd = (EditText) root.findViewById(R.id.fd);
        today= (TextView) root.findViewById(R.id.textView6);
        choosedate=(Button) root.findViewById(R.id.buttoncheck);
        savedata= (Button) root.findViewById(R.id.buttonsave);
        pieChart = (PieChart) root.findViewById(R.id.piechart);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        savedata.setOnClickListener(v -> saveData());
        choosedate.setOnClickListener(v -> showDatePickerDialog());
        updateDate();

        viewPager = root.findViewById(R.id.viewPager);
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter();
        viewPager.setAdapter(imagePagerAdapter);
        startAutoScroll();


        notify = root.findViewById(R.id.notif);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Notificationdisplay.class);
                startActivity(intent);
            }
        });

        barChart = root.findViewById(R.id.bar_chart);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                .requestServerAuthCode("961999398444-59q3mskg72oekfd27tkpielhko7748lk.apps.googleusercontent.com")
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), options);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null && GoogleSignIn.hasPermissions(account, Fitness.SCOPE_ACTIVITY_READ_WRITE)) {
            readDailyStepCountAndCalories();
        } else {
            startActivityForResult(googleSignInClient.getSignInIntent(), GOOGLE_FIT_PERMISSIONS_REQUEST_CODE);
        }

//        datepicker = root.findViewById(R.id.clickButton);
//        sampletext = root.findViewById(R.id.textSample);
//        datepicker.setOnClickListener(view -> showDatePicker());

        return root;
    }
//    private void showDatePicker() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                getContext(),
//                (view, year, month, dayOfMonth) -> {
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(year, month, dayOfMonth);
//
//                    // Get the selected day's step count from SharedPreferences
//                    int selectedDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//                    int stepCount = getStepCountForDay(selectedDayOfWeek);
//
//                    // Display the step count for the selected day
//                    showStepCountForSelectedDay(stepCount);
//                },
//                Calendar.getInstance().get(Calendar.YEAR),
//                Calendar.getInstance().get(Calendar.MONTH),
//                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//        );
//        datePickerDialog.show();
//    }
//    private void showStepCountForSelectedDay(int stepCount) {
//        sampletext.setText("Step count: " + stepCount);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                readDailyStepCountAndCalories();


            } else {
                Toast.makeText(requireContext(), "Google Fit permissions required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readDailyStepCountAndCalories() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Task<DataReadResponse> responseTask = Fitness.getHistoryClient(requireContext(), GoogleSignIn.getLastSignedInAccount(requireContext()))
                .readData(readRequest);

        responseTask.addOnSuccessListener(dataReadResponse -> {
            int totalSteps = 0;
            float totalCalories = 0f;

            for (Bucket bucket : dataReadResponse.getBuckets()) {
                for (DataSet dataSet : bucket.getDataSets()) {
                    for (com.google.android.gms.fitness.data.DataPoint dp : dataSet.getDataPoints()) {
                        if (dp.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA)) {
                            int steps = dp.getValue(Field.FIELD_STEPS).asInt();
                            totalSteps += steps;
                        } else if (dp.getDataType().equals(DataType.TYPE_CALORIES_EXPENDED)) {
                            float calories = dp.getValue(Field.FIELD_CALORIES).asFloat();
                            totalCalories += calories;
                        }
                    }
                }
            }

            stepcountvalue.setText(String.valueOf(totalSteps));
            fd.setText(String.valueOf(totalSteps));
            totalcalories.setText(String.valueOf(totalCalories));

        });

        responseTask.addOnFailureListener(e -> {
            Toast.makeText(requireContext(), "Failed to fetch data from Google Fit.", Toast.LENGTH_SHORT).show();
        });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barChart = view.findViewById(R.id.bar_chart);
        fetchTodayStepCount();
    }
    private void saveStepCountForDay(int dayOfWeek, int stepCount) {
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String key = STEP_COUNT_KEY_PREFIX + dayOfWeek;
        editor.putInt(key, stepCount);
        editor.apply();
    }

    private int getStepCountForDay(int dayOfWeek) {
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String key = STEP_COUNT_KEY_PREFIX + dayOfWeek;
        return preferences.getInt(key, 0);
    }

    private void fetchTodayStepCount() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Task<DataReadResponse> responseTask = Fitness.getHistoryClient(requireContext(), GoogleSignIn.getLastSignedInAccount(requireContext()))
                .readData(readRequest);

        responseTask.addOnSuccessListener(dataReadResponse -> {
            for (Bucket bucket : dataReadResponse.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    List<com.google.android.gms.fitness.data.DataPoint> dataPoints = dataSet.getDataPoints();
                    for (com.google.android.gms.fitness.data.DataPoint dp : dataPoints) {
                        int steps = dp.getValue(Field.FIELD_STEPS).asInt();
                        todaySteps = steps;
                    }
                }
            }
            int currentDayOfWeek = getCurrentDayOfWeek();
            saveStepCountForDay(currentDayOfWeek, todaySteps);
//            weeklyStepCounts[getCurrentDayOfWeek()] = todaySteps;
            updateChart();
        }).addOnFailureListener(e -> {
            Toast.makeText(requireContext(), "Failed to fetch data from Google Fit.", Toast.LENGTH_SHORT).show();
            Log.e("StepData", "Error fetching data: " + e.getMessage());
        });
    }

    private void updateChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();

        int currentDayOfWeek = getCurrentDayOfWeek();
        for (int i = 0; i < 7; i++) {
            int storedStepCount = getStepCountForDay(i);
            if (i <= currentDayOfWeek) {
                entries.add(new BarEntry(i, storedStepCount));
            } else {
                entries.add(new BarEntry(i, 0));
            }
        }

//        for (int i = 0; i < 7; i++) {
//            if (i == currentDayOfWeek) {
//                entries.add(new BarEntry(i, weeklyStepCounts[i]));
//            }
//        if (i == 0) {
//            entries.add(new BarEntry(i, 53));
//        }
//        if (i == 1) {
//            entries.add(new BarEntry(i, 938));
//        }
//        if (i == 2) {
//            entries.add(new BarEntry(i, 762));
//        }
//        if (i == 4) {
//            entries.add(new BarEntry(i, 0));
//        }
//        if (i == 5) {
//            entries.add(new BarEntry(i, 0));
//        }
//            if (i == 6) {
//                entries.add(new BarEntry(i, 0));
//            }
//            else {
//                entries.add(new BarEntry(i,0));
//            }
//        }

        setupBarChart(entries);
    }

    private void setupBarChart(ArrayList<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, "Weekly Steps");
        dataSet.setColor(Color.parseColor("#8D65F3"));

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);


        String[] days = getWeekDates();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return days[(int) value % days.length];
            }
        });

        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);

        barChart.animateY(1000);
        barChart.invalidate();
    }

    private int getCurrentDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private String[] getWeekDates() {
        String[] days = new String[7];
        Calendar calendar = Calendar.getInstance();
        int todayIndex = getCurrentDayOfWeek();

        calendar.add(Calendar.DAY_OF_WEEK, -todayIndex);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            days[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        return days;
    }
    private class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_cardview, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageResource(imageList.get(position));
            holder.cardView.setOnClickListener(v -> openWebPage(webPages[position]));
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        private void openWebPage(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }

        private class ImageViewHolder extends RecyclerView.ViewHolder {
            private CardView cardView;
            private ImageView imageView;

            public ImageViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
    private void startAutoScroll() {
        runnable = () -> {
            viewPager.setCurrentItem(currentPage++, true);
            if (currentPage >= imageList.size()) {
                currentPage = 0;
            }
            startAutoScroll();
        };
        handler.postDelayed(runnable, AUTO_SCROLL_DELAY);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopAutoScroll();
    }

    private void stopAutoScroll() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

//    private void loadDataAndUpdatePieChart() {
//        String savedGoal = sharedPreferences.getString("DATA_KEY_PREFIX", "");
//        String savedSteps = sharedPreferences.getString("DATA_KEY_PREFIX", "");
//        if (!savedGoal.isEmpty()) {
//            bg.setText(savedGoal);
//        }
//        if (!savedSteps.isEmpty()) {
//            fd.setText(savedSteps);
//        }
//
//        setData();
//    }

    private void setData() {
        pieChart.clearChart();
        pieChart.addPieSlice(
                new PieModel(
                        "Base Goal",
                        Integer.parseInt(bg.getText().toString()),
                        Color.parseColor("#5D5D5D")));
        pieChart.addPieSlice(
                new PieModel(
                        "Steps",
                        Integer.parseInt(fd.getText().toString()),
                        Color.parseColor("#CA91F3")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Exercise",
//                        Integer.parseInt(ec.getText().toString()),
//                        Color.parseColor("#FF7300")));
        pieChart.startAnimation();
    }


    private void updateDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        today.setText(formattedDate);
    }

    private void saveData() {
        String currentDate = today.getText().toString();
        String data1 = bg.getText().toString();
        String data2 = fd.getText().toString();
//        String data3 = ec.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATA_KEY_PREFIX + currentDate+"_1", data1);
        editor.putString(DATA_KEY_PREFIX + currentDate+"_2", data2);
//        editor.putString(DATA_KEY_PREFIX + currentDate+"_3", data3);
        editor.apply();
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String selectedDateFormatted = df.format(selectedDate.getTime());
                    showDataForSelectedDate(selectedDateFormatted);
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private void showDataForSelectedDate(String selectedDate) {
        String savedData = sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate, "");
        bg.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_1",""));
        fd.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_2",""));
//        ec.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_3",""));
        today.setText(selectedDate);
        String data1 = sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate + "_1","0");
        String data2 = sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate + "_2","0");
//        String data3 = sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate + "_3","0");
        if(!data1.equals("0") ||!data2.equals("0")) {
            setData();
        }
    }

}