package com.maximmesh.notes;

import static com.maximmesh.notes.DescriptionFragment.SELECTED_NOTE;

import android.content.res.Configuration;
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

   Note note;
   View dataContainer;

   public NotesFragment() {
   }

   @Override
   public void onSaveInstanceState(@NonNull Bundle outState) {
      outState.putParcelable(SELECTED_NOTE, note);
      super.onSaveInstanceState(outState);
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_notes, container, false);

   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      if (savedInstanceState != null) {
         note = (Note) savedInstanceState.getParcelable(SELECTED_NOTE);
      }

      dataContainer = view.findViewById(R.id.data_container);
      initNotes(dataContainer);

      if (isLandscape()) {
         showLandNoteDetails(note);
      }

   }

   private boolean isLandscape() {
      return getResources().getConfiguration().orientation
      == Configuration.ORIENTATION_LANDSCAPE;
   }

   public void initNotes() {
      initNotes(dataContainer);
   }

   private void initNotes(View view) {
      LinearLayout layoutView = (LinearLayout) view;
      layoutView.removeAllViews();
      for (int i = 0; i < Note.getNotes().length; i++) {

         TextView tv = new TextView(getContext());
         tv.setText(Note.getNotes()[i].getTitle());
         tv.setTextSize(25);
         tv.computeScroll();
         layoutView.addView(tv);

         final int index = i;
         tv.setOnClickListener(v -> {
            showNoteDetails(Note.getNotes()[index]);
         });
      }
   }

   private void showNoteDetails(Note note) {
      this.note = note;
      if (isLandscape()) {
         showLandNoteDetails(note);
      } else {
         showPortNoteDetails(note);
      }
   }

   private void showPortNoteDetails(Note note) {
      DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(note);
      FragmentManager fragmentManager =
      requireActivity().getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.add(R.id.notes_container, descriptionFragment); // замена  фрагмента
      fragmentTransaction.addToBackStack("");
      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      fragmentTransaction.commit();
   }

   private void showLandNoteDetails(Note note) {
      DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(note);
      FragmentManager fragmentManager =
      requireActivity().getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.note_container, descriptionFragment); // замена  фрагмента
      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      fragmentTransaction.commit();
   }
}