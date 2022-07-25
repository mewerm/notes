package com.maximmesh.notes.Fragments.Notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.maximmesh.notes.R;

public class MyCustomDialogFragment extends DialogFragment {

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View customView = inflater.inflate(R.layout.custom_dialog_view, null);
      customView.findViewById(R.id.button_custom_view).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String str = customView.<EditText>findViewById(R.id.edit_text_custom_view).getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("MESSAGE", str);

            requireActivity().getSupportFragmentManager().setFragmentResult("FRAGMENT_DIALOG_RESULT", bundle);
            dismiss(); //закрывает диалоговое окно(диалоговый фрагмент) (типа finish())

         }
      });
      return customView;
   }

}
