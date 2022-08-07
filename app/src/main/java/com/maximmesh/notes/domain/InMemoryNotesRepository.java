package com.maximmesh.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNotesRepository implements NotesRepository {

   private ArrayList<Note> data = new ArrayList<>();

   private Executor executor = Executors.newSingleThreadExecutor();

   private Handler handler = new Handler(Looper.getMainLooper()); //Handler - средство по доставке сообщений какому-либо потоку

   public InMemoryNotesRepository(){
      data.add(new Note(UUID.randomUUID().toString(), "Заметка 1", "Описаие заметки 1", new Date()));
      data.add(new Note(UUID.randomUUID().toString(), "Заметка 2", "Описаие заметки 2", new Date()));
      data.add(new Note(UUID.randomUUID().toString(), "Заметка 3", "Описаие заметки 3", new Date()));
      data.add(new Note(UUID.randomUUID().toString(), "Заметка 4", "Описаие заметки 4", new Date()));
      data.add(new Note(UUID.randomUUID().toString(), "Заметка 5", "Описаие заметки 5", new Date()));
      data.add(new Note(UUID.randomUUID().toString(), "Заметка 6", "Описаие заметки 6", new Date()));
      data.add(new Note(UUID.randomUUID().toString(), "Заметка 7", "Описаие заметки 7", new Date()));

   }

   @Override
   public void getAll(CallBack<List<Note>> callback) {

      executor.execute(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(2000L);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            handler.post(new Runnable() { //благодаря Handler выполним callback в основном потоке
               @Override
               public void run() {
                  callback.onSuccess(data);
               }
            });

         }
      });
   }
}
