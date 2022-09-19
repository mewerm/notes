package com.maximmesh.notes.Fragments;

import static com.maximmesh.notes.Fragments.DescriptionFragment.SELECTED_NOTE;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.maximmesh.notes.Constructor.Note;
import com.maximmesh.notes.R;


public class NotesFragment extends Fragment {

   Note note;
   View dataContainer;

   public NotesFragment() {
   }

   @Override
   public void onSaveInstanceState(@NonNull Bundle outState) {

      if (note == null) {
         note = Note.getNotes().get(0);
      }

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

      addNote(view, Note.getNoteNumbers());

      if (isLandscape()) {
         showLandNoteDetails(note);
      }

   }

   private boolean isLandscape() {
      return getResources().getConfiguration().orientation
      == Configuration.ORIENTATION_LANDSCAPE;
   }

   /**
    * Не понимаю как добавить новый элемент
    * @param
    */
   public void addNote(View view, int index) {
      FloatingActionButton actionButton = view.findViewById(R.id.btn_add);
      actionButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            Toast.makeText(getActivity(), "Добавляем новую заметку", Toast.LENGTH_LONG).show();
         }
      });
   }

   public void initNotes(boolean isDelete) {
      if (isDelete) {

      }
      initNotes(dataContainer);
   }

   private void initNotes(@NonNull View view) {
      LinearLayout layoutView = (LinearLayout) view;
      layoutView.removeAllViews();
      for (int i = 0; i < Note.getNotes().size(); i++) {

         TextView tv = new TextView(getContext());
         tv.setText(Note.getNotes().get(i).getTitle());
         tv.setTextSize(24);
         layoutView.addView(tv);

         final int index = i;
         initPopupMenu(layoutView, tv, index);
         tv.setOnClickListener(v -> {
            showNoteDetails(Note.getNotes().get(index));
         });
      }
   }

   private void initPopupMenu(LinearLayout rootView, TextView view, int index) {
      view.setOnLongClickListener(v -> {
         Activity activity = requireActivity();
         PopupMenu popupMenu = new PopupMenu(activity, view);
         activity.getMenuInflater().inflate(R.menu.notes_popup, popupMenu.getMenu());

         popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
               switch (menuItem.getItemId()) {
                  case R.id.action_popup_delete:
                     Note.getNotes().remove(index);
                     rootView.removeView(view);
                     Snackbar.make(rootView, "Заметка удалена", Snackbar.LENGTH_LONG).show();
                     break;
               }

               return true;
            }
         });
         popupMenu.show();
         return true;
      });
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