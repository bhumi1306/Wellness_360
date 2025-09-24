package com.example.wellness360;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Plan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Plan extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Plan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Plan.
     */
    // TODO: Rename and change types and number of parameters
    public static Plan newInstance(String param1, String param2) {
        Plan fragment = new Plan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private CardView cardView;
    private CardView cardView1;
    private CardView cardView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_plan, container, false);
        cardView = (CardView) root.findViewById(R.id.plan1);
        cardView1 = (CardView) root.findViewById(R.id.plan2);
        cardView2 = (CardView) root.findViewById(R.id.plan3);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.purp1));
                    openplan1();
            }
        });
        cardView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.purp1));
                    openplan2();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.purp1));
                    openplan3();
            }
        });
        return root;
    }
    public void openplan1(){
        Intent intent = new Intent(getActivity(), Meditationmusic.class);
        startActivity(intent);
    }
    public void openplan2(){
        Intent intent = new Intent(getActivity(), Stepsplan.class);
        startActivity(intent);
    }
    public void openplan3(){
        Intent intent = new Intent(getActivity(), Healthymeal.class);
        startActivity(intent);
    }
}