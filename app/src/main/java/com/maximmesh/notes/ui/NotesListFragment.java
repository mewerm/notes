package com.maximmesh.notes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximmesh.notes.R;
import com.maximmesh.notes.di.Dependencies;
import com.maximmesh.notes.domain.CallBack;
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

      notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)); //тут обозначем как будем отображать

      //делаем разделитель Divader
      DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
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

      getParentFragmentManager()
      .setFragmentResultListener(AddNoteBottomSheetDialogFragment.KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
         @Override
         public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

            int index = adapter.addNote(note);
            adapter.notifyItemInserted(index); //обнови вставку элемента по такой-то позиции(лучше чем нотифайДатаСетЧей - оно меняет весь видимый список)

          //если список длинный и необходимо доскролиться вниз:
            notesList.smoothScrollToPosition(index);
         }
      });
      view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            new AddNoteBottomSheetDialogFragment()
            .show(getParentFragmentManager(), "AddNoteBottomSheetDialogFragment");
         }
      });


      ProgressBar progressBar = view.findViewById(R.id.progress); //прогресс бар
      progressBar.setVisibility(View.VISIBLE); //в xml скрыли, тут показали

      //так делает асинхронный метод неблоирующий на запрос заметок
      Dependencies.NOTES_REPOSITORY.getAll(new CallBack<List<Note>>() {
         @Override
         public void onSuccess(List<Note> data) {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE); //тут прогресс бар скрыли
         }

         @Override
         public void onError(Throwable exception) {
            progressBar.setVisibility(View.GONE); //тут прогресс бар скрыли в случае ошибки

         }
      }); //NOTES_REPOSITORY - репозиторий с методом getAll() который возвращает список заметок,
      //забирает их их из InMemoryNotesRepository
   }
}