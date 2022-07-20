package com.maximmesh.notes;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.Arrays;

public class DescriptionFragment extends Fragment {

   static final String SELECTED_NOTE = "note";
   private Note note;

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
      return inflater.inflate(R.layout.fragment_description, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      Bundle arguments = getArguments();

      MotionLabel motionLabel = view.findViewById(R.id.btnBack);
      motionLabel.setOnClickListener(v -> {
         requireActivity().getSupportFragmentManager().popBackStack();
      });

      if (arguments != null) {

         //int index = arguments.getInt(SELECTED_NOTE);
         Note paramNote = (Note)arguments.getParcelable(SELECTED_NOTE);
         note = Arrays.stream(Note.getNotes()).filter( n -> n.getId() == paramNote.getId()).findFirst().get();



         TextView tvTitle = view.findViewById(R.id.tvTitle);
         tvTitle.setText(note.getTitle());
         tvTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
               note.setTitle(tvTitle.getText().toString());
               updateData();
               //Note.getNotes()[index].setTitle(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) { }
         });

         TextView tvDescription = view.findViewById(R.id.tvDescription);
         tvDescription.setText(note.getDescription());
      }
   }

   @RequiresApi(api = Build.VERSION_CODES.N)
   private void updateData(){
      NotesFragment notesFragment = (NotesFragment) requireActivity().getSupportFragmentManager().getFragments().stream().filter( fragment -> fragment instanceof NotesFragment)
      .findFirst().get();
      notesFragment.initNotes();

   }

   public static DescriptionFragment newInstance(int index) {
      DescriptionFragment fragment = new DescriptionFragment();
      Bundle args = new Bundle();
      args.putInt(SELECTED_NOTE, index);
      fragment.setArguments(args);
      return fragment;
   }

   public static DescriptionFragment newInstance(Note note) {
      DescriptionFragment fragment = new DescriptionFragment();
      Bundle args = new Bundle();
      args.putParcelable(SELECTED_NOTE, note);
      fragment.setArguments(args);
      return fragment;
   }


}