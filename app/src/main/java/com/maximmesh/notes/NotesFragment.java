package com.maximmesh.notes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class NotesFragment extends Fragment {


   public NotesFragment() {
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
      View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

      TextView textView = rootView.findViewById(R.id.header);

      textView.setTypeface(Typeface.DEFAULT_BOLD);


      return rootView;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      initNotesList(view);
   }


   private void initNotesList(View view) {
      LinearLayout linearLayout = (LinearLayout) view;
      String[] notes = getResources().getStringArray(R.array.notes); //загружаю список заметок
      //динамически добавляю TextView (список элементов)
      for (int i = 0; i < notes.length; i++) {
         TextView textView = new TextView(getContext()); //в рамках каждой итерации создаю TextView
         textView.setText(notes[i]);
         textView.setTextSize(22);
         linearLayout.addView(textView);

         final int index = i;

         textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO: Отобразить список с описанием заметки
               showDescriptionNotes(index);
            }
         });

      }

   }


   private void showDescriptionNotes(int index) {
      DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(index);
       requireActivity()
       .getSupportFragmentManager()
       .beginTransaction()
       .add(R.id.fragment_container,descriptionFragment)
       .addToBackStack("") //добавляем очищение от DescriptionFragment
       .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //плавный камбэк to NotesFragment
       .commit();


   }

}