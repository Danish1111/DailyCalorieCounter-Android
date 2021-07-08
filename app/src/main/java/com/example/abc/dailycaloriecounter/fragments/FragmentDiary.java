package com.example.abc.dailycaloriecounter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.abc.dailycaloriecounter.HorizontalCalendar;
import com.example.abc.dailycaloriecounter.R;
import com.example.abc.dailycaloriecounter.utils.HorizontalCalendarListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Calendar;


public class FragmentDiary extends Fragment {
   private HorizontalCalendar horizontalCalendar;
   private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(horizontalCalendar!=null){
            horizontalCalendar=null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.horizontal_progress_bar_carbs);
        progressBar.animate();
        progressBar.setProgress(100);
        CircularProgressBar circularProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        circularProgressBar.setColor(ContextCompat.getColor(getActivity(), R.color.ios_orange));
   //     circularProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.cobalt_black));
   //     circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.card_corner_radius));
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(65, animationDuration); // Default duration = 1500ms

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(getActivity(), R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.selectDate(Calendar.getInstance(), true);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
            }
        });


    }
}
