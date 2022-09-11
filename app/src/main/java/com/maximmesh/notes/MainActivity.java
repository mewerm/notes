package com.maximmesh.notes;


import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      NotesFragment notesFragment = new NotesFragment();//сначала создаем фрагметн


      if (savedInstanceState == null) {
         getSupportFragmentManager()//обращаемся к фрагмент менеджеру (p.s. в рамках одной транзакции можно работать с несколькими фрагментами
         .beginTransaction()
         .replace(R.id.notes_container, notesFragment) //удаляем предыдущий фрагмент, добавляем новый. если add, то поверх старого добавляем новый
         .commit();
      }

   }
}
