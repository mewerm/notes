package com.maximmesh.notes;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class DescriptionFragment extends Fragment {

   static final String ARG_INDEX = "index";







   public DescriptionFragment() {
      // Required empty public constructor
   }



   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);



   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_description, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      Bundle arguments = getArguments();


      if(arguments != null){
         int index = arguments.getInt(ARG_INDEX);

         TextView textView = view.findViewById(R.id.fragment_description_text_view);
         TypedArray description = getResources().obtainTypedArray(R.array.description_notes);
         textView.setText(description.getResourceId(index, 0));
         description.recycle();
      }




   }


   public static DescriptionFragment newInstance(int index){
      DescriptionFragment fragment = new DescriptionFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_INDEX, index);
      fragment.setArguments(args);
      return fragment;


   }

}