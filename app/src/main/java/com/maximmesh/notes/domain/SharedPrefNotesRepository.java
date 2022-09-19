package com.maximmesh.notes.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SharedPrefNotesRepository implements NotesRepository {

   private static final String KEY_SAVED_NOTES = "KEY_SAVED_NOTES"; //ключ сохраненных заметок для sharPref

   //чтобы сохранить, нужно сначала заполучить из контекста:
   private Context context;

   private SharedPreferences sharedPreferences; //берем преференсы

   public SharedPrefNotesRepository(Context context) { // делаем конструктор
      this.context = context;
      sharedPreferences = context.getSharedPreferences("NOTES", Context.MODE_PRIVATE); //MODE_PRIVATE на данный момент самый актуальный режим записи чтения файла.. первый аругемент просто описание
   }

   @Override
   public void getAll(CallBack<List<Note>> callback) {

      //получение списка заметок:

      Gson gson = new Gson(); //создали объект gson

      String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]"); //дефолтное значение "пустой список" для json это скобки "[]"

      Type type = new TypeToken<ArrayList<Note>>() {}.getType(); //это конструкция, для получения типизированные по дженерику колекции. то есть мы не
      //сможем передать в  gson.fromJson(savedData, List<Note>.class) - не можем передать напрямую List<Note>.class. только через эту обертку TypeToken

      List<Note> savedNotes = gson.fromJson(savedData, type);//чтобы преобразовать строку в объекты нужна обертка та, что выше.

      callback.onSuccess(savedNotes);
   }

   @Override
   public void addNote(String title, String message, CallBack<Note> callback) {

      Note note = new Note(UUID.randomUUID().toString(), title, message, new Date());
      //UUID.randomUUID - индификатор так добавляем. Также передали заголовок, тело и дату создания заметки)

      Gson gson = new Gson(); //создали объект gson

      String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]"); //дефолтное значение "пустой список" для json это скобки "[]"

      Type type = new TypeToken<ArrayList<Note>>() {}.getType(); //это конструкция, для получения типизированные по дженерику колекции. то есть мы не
      //сможем передать в  gson.fromJson(savedData, List<Note>.class) - не можем передать напрямую List<Note>.class. только через эту обертку TypeToken

      List<Note> savedNotes = gson.fromJson(savedData, type);//чтобы преобразовать строку в объекты нужна обертка та, что выше.

      savedNotes.add(note); //добавляем в список сохраненных заметок новую заметку

      String toWrite = gson.toJson(savedNotes, type); //получили строку

      sharedPreferences.edit()
      .putString(KEY_SAVED_NOTES, toWrite) //положили стркоу
      .apply();

      callback.onSuccess(note);

   }

   @Override
   public void removeNote(Note note, CallBack<Void> callback) {

   }

   @Override
   public void updateNote(Note note, String title, String message, CallBack<Note> callback) {

   }
}
