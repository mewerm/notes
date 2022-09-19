package com.maximmesh.notes.domain;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FireStoreNotesRepository implements NotesRepository {

   //делаем константные ключи для dataBase
   private static final String KEY_TITLE = "title";
   private static final String KEY_MESSAGE = "message";
   private static final String KEY_CREATED_AT = "createdAt";

   private static final String NOTES = "notes";

   private final FirebaseFirestore firestore = FirebaseFirestore.getInstance(); //для работы с фаерстор

   @Override
   public void getAll(CallBack<List<Note>> callback) {
      //получаем список всех заметок:
      firestore.collection(NOTES)
      .get() //получаем все заметки
      .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
         @Override
         public void onSuccess(QuerySnapshot queryDocumentSnapshots) { //интерпритация, что все хорошо

            ArrayList<Note> result = new ArrayList<>();

            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

               //формируем заметку:
               String id = documentSnapshot.getId();

               String title = documentSnapshot.getString(KEY_TITLE);
               String message = documentSnapshot.getString(KEY_MESSAGE);
               Date createdAt = documentSnapshot.getDate(KEY_CREATED_AT);

               //возвращаем пользователю:
               result.add(new Note(id, title, message, createdAt));

            }

            callback.onSuccess(result);

         }
      });
   }

   @Override
   public void addNote(String title, String message, CallBack<Note> callback) {
      //добавление заметки:

      HashMap<String, Object> data = new HashMap<>(); //создаем коллекцию

      Date createdAt = new Date(); //просто текущая дата

      data.put(KEY_TITLE, title);
      data.put(KEY_MESSAGE, message);
      data.put(KEY_CREATED_AT, createdAt);

      firestore.collection(NOTES)
      .add(data) //получаем все заметки
      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() { //подписываемся, когда все удалось(мы создали заметку полностью)
         @Override
         public void onSuccess(DocumentReference documentReference) {

            callback.onSuccess(new Note(documentReference.getId(), title, message, createdAt));

         }
      });
   }

   @Override
   public void removeNote(Note note, CallBack<Void> callback) {
      //удаляем заметку:

      firestore.collection(NOTES)
      .document(note.getId())
      .delete()
      .addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void unused) {
            callback.onSuccess(unused);
         }
      });
   }

   @Override
   public void updateNote(Note note, String title, String message, CallBack<Note> callback) {
// обновляем данные при редактировании

      HashMap<String, Object> data = new HashMap<>();
      data.put(KEY_TITLE, title);
      data.put(KEY_MESSAGE, message);

      firestore.collection(NOTES)
      .document(note.getId())
      .update(data)
      .addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void unused) {
            Note result =  new Note(note.getId(), title, message, note.getCrateAt());
         }
      });
   }
}
