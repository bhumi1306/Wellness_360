package com.example.wellness360;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Date;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Diary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Diary extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Diary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Diary.
     */
    // TODO: Rename and change types and number of parameters
    public static Diary newInstance(String param1, String param2) {
        Diary fragment = new Diary();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView textvdate, textcheck;
    private Button complete;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private EditText ed4;
    private EditText ed5;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "MyPrefs2";
    private static final String DATA_KEY_PREFIX = "data2_";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_diary, container,false);
        textvdate = view.findViewById(R.id.textView5);
        final MainActivity3 mainActivity3= (MainActivity3) getActivity();
        ed1 =view.findViewById(R.id.addbk);
        ed2 =view.findViewById(R.id.addlc);
        ed3 =view.findViewById(R.id.adddn);
        ed4 =view.findViewById(R.id.addsn);
        ed5 =view.findViewById(R.id.addexc);
        complete = view.findViewById(R.id.button7);
        textcheck = view.findViewById(R.id.textView8);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        textcheck.setOnClickListener(v -> showDatePickerDialog());
        updateDate();

        return view;
    }

    private void updateDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        textvdate.setText(formattedDate);
    }

    private void saveData() {
        String currentDate = textvdate.getText().toString();
        String data1 = ed1.getText().toString();
        String data2 = ed2.getText().toString();
        String data3 = ed3.getText().toString();
        String data4 = ed4.getText().toString();
        String data5 = ed5.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATA_KEY_PREFIX + currentDate+"_1", data1);
        editor.putString(DATA_KEY_PREFIX + currentDate+"_2", data2);
        editor.putString(DATA_KEY_PREFIX + currentDate+"_3", data3);
        editor.putString(DATA_KEY_PREFIX + currentDate+"_4", data4);
        editor.putString(DATA_KEY_PREFIX + currentDate+"_5", data5);
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
        String savedData = sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate, "No data");
        ed1.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_1","Add"));
        ed2.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_2","Add"));
        ed3.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_3","Add"));
        ed4.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_4","Add"));
        ed5.setText(sharedPreferences.getString(DATA_KEY_PREFIX + selectedDate +"_5","Add"));
        textvdate.setText(selectedDate);
}



}