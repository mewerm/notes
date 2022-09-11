package com.maximmesh.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.fragment.app.Fragment;

import java.util.Optional;

public class DescriptionFragment extends Fragment {

   static final String SELECTED_NOTE = "note";
   private Note note;

   public DescriptionFragment() {
      // Required empty public constructor
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

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setHasOptionsMenu(true);

   }

   @Override
   public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);

      inflater.inflate(R.menu.description_menu, menu);


      MenuItem itemAbout = menu.findItem(R.id.action_about); //убираем кнопку выйти из приложения из меню когда в фрагменте описания заметки находимся
      MenuItem itemExit = menu.findItem(R.id.action_exit); //убираем кнопку выйти из приложения из меню когда в фрагменте описания заметки находимся
      if (itemExit != null) {
         itemExit.setVisible(false);
      }
      if (itemAbout != null) {
         itemAbout.setVisible(false);
      }
   }

   @Override //этот метод, чтобы обработать нажатие на кнопку в меню
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      if (item.getItemId() == R.id.action_delete) {
         //TODO: Удаление заметки.
         Note.getNotes().remove(note);
         updateData();
         if (!isLandScape()) {
            requireActivity()
            .getSupportFragmentManager()
            .popBackStack();
            return true;
         }

      }
      return super.onOptionsItemSelected(item);
   }

   private boolean isLandScape() {
      return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
   }


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      if (savedInstanceState == null) {
         setHasOptionsMenu(true);
      }
      return inflater.inflate(R.layout.fragment_description, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      Bundle arguments = getArguments();

      MotionLabel buttonBack = view.findViewById(R.id.btnBack);
      if (buttonBack != null) {
         buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
         });
      }

      if (arguments != null) {

         Note paramNote = (Note) arguments.getParcelable(SELECTED_NOTE);
         if (paramNote != null) {
            Optional<Note> selectedNote = Note.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst();
            note = selectedNote.orElseGet(() -> Note.getNotes().get(0));
         }
      }


      TextView tvTitle = view.findViewById(R.id.tvTitle);

      tvTitle.setText(note.getTitle());

      tvTitle.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            note.setTitle(tvTitle.getText().toString());
            updateData();

         }

         @Override
         public void afterTextChanged(Editable editable) {

         }
      });

      TextView tvDescription = view.findViewById(R.id.tvDescription);
      tvDescription.setText(note.getDescription());
   }



   private void updateData() {
      NotesFragment notesFragment = (NotesFragment) requireActivity().getSupportFragmentManager().getFragments().stream().filter(fragment -> fragment instanceof NotesFragment)
      .findFirst().get();
      notesFragment.initNotes();

   }


}