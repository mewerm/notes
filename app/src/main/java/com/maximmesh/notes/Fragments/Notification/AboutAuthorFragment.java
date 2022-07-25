package com.maximmesh.notes.Fragments.Notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maximmesh.notes.R;

public class AboutAuthorFragment extends Fragment {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_about_author, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      Button buttonOk = view.findViewById(R.id.bottom_ok);
      buttonOk.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            requireActivity().getSupportFragmentManager().popBackStack();
         }
      });
   }
}