package com.maximmesh.notes;


import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      NotesFragment notesFragment = new NotesFragment();//сначала создаем фрагметн
      getSupportFragmentManager()//обращаемся к фрагмент менеджеру (p.s. в рамках одной транзакции можно работать с несколькими фрагментами
      .beginTransaction()
      .replace(R.id.fragment_container, notesFragment) //удаляем предыдущий фрагмент, добавляем новый. если add, то поверх старого добавляем новый
      .commit();

      TextView resultDataPicker = findViewById(R.id.tv_result_data_picker);
      DatePicker datePicker = findViewById(R.id.datePicker);

      ImageButton imageButton = findViewById(R.id.add_button);
      imageButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Добавляем новую заметку", Toast.LENGTH_LONG).show();
         }
      });


   }
}