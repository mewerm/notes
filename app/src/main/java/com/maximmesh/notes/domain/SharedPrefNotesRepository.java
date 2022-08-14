package com.maximmesh.notes.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SharedPrefNotesRepository implements NotesRepository {


   //чтобы сохранить, нужно сначала заполучить из контекста:
   private Context context;

   private SharedPreferences sharedPreferences; //берем преференсы

   public SharedPrefNotesRepository(Context context) { // делаем конструктор
      this.context = context;
      sharedPreferences = context.getSharedPreferences("NOTES", Context.MODE_PRIVATE); //MODE_PRIVATE на данный момент самый актуальный режим записи чтения файла.. первый аругемент просто описание
   }

   @Override
   public void getAll(CallBack<List<Note>> callback) {

   }

   @Override
   public void addNote(String title, String message, CallBack<Note> callback) {
      //сохранять будем сразу списком
      ArrayList<Note> data = new ArrayList<>();

      Note note = new Note(UUID.randomUUID().toString(), title, message, new Date());
      //UUID.randomUUID - индификатор так добавляем. Также передали заголовок, тело и дату создания заметки)

      Gson gson = new Gson(); //создали объект gson

      String json = gson.toJson(note); //передаем методу toJson тот объект, который хотим представить в виде строки

      json.length();

      /*
      data.add(new Note(UUID.randomUUID().toString(), title, message, new Date())); //UUID.randomUUID - индификатор так добавляем. Также передали заголовок, тело и дату создания заметки

      sharedPreferences.edit() //для сохранения, берем заметку
      .putString()
*/

   }

   @Override
   public void removeNote(Note note, CallBack<Void> callback) {

   }

   @Override
   public void updateNote(Note note, String title, String message, CallBack<Note> callback) {

   }
}
