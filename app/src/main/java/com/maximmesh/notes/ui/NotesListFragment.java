package com.maximmesh.notes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximmesh.notes.R;
import com.maximmesh.notes.di.Dependencies;
import com.maximmesh.notes.domain.Note;

import java.util.List;
import java.util.Objects;

public class NotesListFragment extends Fragment {


   public NotesListFragment() {
      super(R.layout.fragment_notes_list);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      RecyclerView notesList = view.findViewById(R.id.notes_list); //находим RecycleView

      notesList.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)); //тут обозначем как будем отображать

      //делаем разделитель Divader
      DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL);
      dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_divider)));

      notesList.addItemDecoration(dividerItemDecoration);
      NotesAdapter adapter = new NotesAdapter();
      adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
         @Override
         public void onNoteClicked(Note note) {
            Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
         }
      });

      notesList.setAdapter(adapter);

      List<Note> notes = Dependencies.NOTES_REPOSITORY.getAll();

      adapter.setData(notes);

      adapter.notifyDataSetChanged();

   }
}