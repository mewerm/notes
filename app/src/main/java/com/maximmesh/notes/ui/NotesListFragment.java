package com.maximmesh.notes.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.maximmesh.notes.R;
import com.maximmesh.notes.di.Dependencies;
import com.maximmesh.notes.domain.CallBack;
import com.maximmesh.notes.domain.Note;

import java.util.List;
import java.util.Objects;

public class NotesListFragment extends Fragment {

   ProgressBar progressBar;
   private Note selectedNote;
   private int selectedPosition;
   private NotesAdapter adapter;


   public NotesListFragment() {
      super(R.layout.fragment_notes_list);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
/*
      //дата пикер
      MaterialDatePicker<Long> datePicker =
      MaterialDatePicker.Builder.datePicker()
      .setTitleText("Выберите дату")
      .build();

      datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
         @Override
         public void onPositiveButtonClick(Object selection) {

         }
      });

      datePicker.show(getParentFragmentManager(), "MaterialDatePicker");*/

      RecyclerView notesList = view.findViewById(R.id.notes_list); //находим RecycleView

      notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)); //тут обозначем как будем отображать

      DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator(); //работа с анимациями
      defaultItemAnimator.setAddDuration(3000L);
      notesList.setItemAnimator(defaultItemAnimator);

      //делаем разделитель Divader
      DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
      dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_divider)));
      adapter = new NotesAdapter(this);
      notesList.addItemDecoration(dividerItemDecoration);
      adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
         @Override
         public void onNoteClicked(Note note) {
            Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onNoteLongClicked(Note note, int position) {
            selectedNote = note;
            selectedPosition = position;
         }
      });

      notesList.setAdapter(adapter);

      getParentFragmentManager()
      .setFragmentResultListener(AddNoteBottomSheetDialogFragment.ADD_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
         @Override
         public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

            int index = adapter.addNote(note);
            adapter.notifyItemInserted(index); //обнови вставку элемента по такой-то позиции(лучше чем нотифайДатаСетЧей - оно меняет весь видимый список)

            //если список длинный и необходимо доскролиться вниз:
            notesList.smoothScrollToPosition(index);
         }
      });

      getParentFragmentManager()
      .setFragmentResultListener(AddNoteBottomSheetDialogFragment.UPDATE_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
         @Override
         public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

            adapter.replaceNote(note, selectedPosition);

            adapter.notifyItemChanged(selectedPosition);
         }
      });

      view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            AddNoteBottomSheetDialogFragment.addInstance()
            .show(getParentFragmentManager(), "AddNoteBottomSheetDialogFragment");
         }
      });

      progressBar = view.findViewById(R.id.progress); //прогресс бар
      progressBar.setVisibility(View.VISIBLE); //в xml скрыли, тут показали

      //так делает асинхронный метод неблоирующий на запрос заметок
      Dependencies.getNotesRepository(requireContext()).getAll(new CallBack<List<Note>>() {
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

   @Override
   public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);

      MenuInflater menuInflater = getActivity().getMenuInflater();
      menuInflater.inflate(R.menu.menu_notes_contex, menu);
   }

   @Override
   public boolean onContextItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
         case R.id.action_delete: //если произошло нажатие по Удлаить

            progressBar.setVisibility(View.VISIBLE);

            Dependencies.getNotesRepository(requireContext()).removeNote(selectedNote, new CallBack<Void>() {
               @Override
               public void onSuccess(Void data) {
                  progressBar.setVisibility(View.GONE);

                  adapter.removeNote(selectedNote);

                  adapter.notifyItemRemoved(selectedPosition);
               }

               @Override
               public void onError(Throwable exception) {

               }
            });

            Toast.makeText(requireContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
            return true;//обрабатываем нажатие
         case R.id.action_edit:
            AddNoteBottomSheetDialogFragment.editInstance(selectedNote)
            .show(getParentFragmentManager(), "AddNoteBottomSheetDialogFragment");
            return true;
      }
      return super.onContextItemSelected(item);
   }
}