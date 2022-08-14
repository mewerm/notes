package com.maximmesh.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maximmesh.notes.R;
import com.maximmesh.notes.di.Dependencies;
import com.maximmesh.notes.domain.CallBack;
import com.maximmesh.notes.domain.Note;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment {

   public static final String ADD_KEY_RESULT = "AddNoteBottomSheetDialogFragment_ADD_KEY_RESULT";
   public static final String UPDATE_KEY_RESULT = "AddNoteBottomSheetDialogFragment_UPDATE_KEY_RESULT";
   public static final String ARG_NOTE = "ARG_NOTE";

   public static AddNoteBottomSheetDialogFragment addInstance() {
      return new AddNoteBottomSheetDialogFragment();
   }

   public static AddNoteBottomSheetDialogFragment editInstance(Note note) {

      Bundle args = new Bundle();
      args.putParcelable(ARG_NOTE, note);
      AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();
      fragment.setArguments(args);
      return fragment;
   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_add_note_bottom_sheet, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      Note noteToEdit = null;

      if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
         noteToEdit = getArguments().getParcelable(ARG_NOTE);

      }

      EditText title = view.findViewById(R.id.title);
      EditText message = view.findViewById(R.id.massage);

      if (noteToEdit != null) {
         title.setText(noteToEdit.getTitle());
         message.setText(noteToEdit.getMessage());
      }

      Button btnSave = view.findViewById(R.id.save);

      Note finalNoteToEdit = noteToEdit;
      Note finalNoteToEdit1 = noteToEdit;
      btnSave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            btnSave.setEnabled(false); //чтобы не мог 10 раз нажимать на кнопку сохранить)

            if (finalNoteToEdit != null) {
               Dependencies.getNotesRepository(requireContext()).updateNote(finalNoteToEdit1, title.getText().toString(), message.getText().toString(), new CallBack<Note>() {
                  @Override
                  public void onSuccess(Note data) {
                     Bundle bundle = new Bundle();
                     bundle.putParcelable(ARG_NOTE, data);
                     getParentFragmentManager().setFragmentResult(UPDATE_KEY_RESULT, bundle);

                  }

                  @Override
                  public void onError(Throwable exception) {
                     btnSave.setEnabled(true);
                  }
               });
            } else {
               Dependencies.getNotesRepository(requireContext()).addNote(title.getText().toString(), message.getText().toString(), new CallBack<Note>() {
                  @Override
                  public void onSuccess(Note data) {

                     Bundle bundle = new Bundle();
                     bundle.putParcelable(ARG_NOTE, data);

                     getParentFragmentManager().setFragmentResult(ADD_KEY_RESULT, bundle);

                     btnSave.setEnabled(true); //разрешаем тапать по кнопке сохранить
                     dismiss(); //закрываем шторку
                  }

                  @Override
                  public void onError(Throwable exception) {
                     btnSave.setEnabled(false); //запрещаем
                  }
               });

            }
         }
      });
   }
}
