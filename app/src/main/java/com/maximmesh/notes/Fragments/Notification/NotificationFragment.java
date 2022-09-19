package com.maximmesh.notes.Fragments.Notification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.snackbar.Snackbar;
import com.maximmesh.notes.R;

public class NotificationFragment extends Fragment {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      if(savedInstanceState != null){
         requireActivity().getSupportFragmentManager().popBackStack();
      }
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_notification, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      requireActivity().getSupportFragmentManager().setFragmentResultListener("FRAGMENT_DIALOG_RESULT",
      getViewLifecycleOwner(), new FragmentResultListener() {
         @Override
         public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            showToast2(result.getString("MESSAGE"));
         }
      });


      view.findViewById(R.id.button_snack_bar).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            doDialog();
         }
      });

      view.findViewById(R.id.button_dialog_custom_view).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showMyCustomDialogFragment();
         }
      });
   }

   private void showToast() {
      Toast.makeText(getContext(), "Уже поздно, Деньги улетели, Спасибо", Toast.LENGTH_SHORT).show();

   }

   private void showToast(String text) {
      Toast.makeText(getContext(), "Благодарим Вас", Toast.LENGTH_LONG).show();

   }
   private void showToast2(String text) {
      Toast.makeText(getContext(), "Почти готово, осталось отправить деньги", Toast.LENGTH_LONG).show();

   }

   private void showSnackBar(View view) {
      Snackbar.make(view.findViewById(R.id.notification_container_view), "Деньги отправляются", Snackbar.LENGTH_SHORT)
      .setAction("Вернуть деньги", new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showToast();
            doSleep();
            showToast("str");
         }
      })
      .show();
   }

   private void doDialog() {
      new AlertDialog.Builder(getContext())
      .setTitle("Подтверждение транзакции")
      .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            showSnackBar(requireView());

         }
      }).show();
   }

   private void doSleep() {
      try {
         Thread.sleep(5000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   private void showMyCustomDialogFragment(){
      new MyCustomDialogFragment().show(requireActivity().getSupportFragmentManager(), "MY_CUSTOM_DIALOG");
   }
}