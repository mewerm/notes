package com.maximmesh.notes.domain;

import java.util.List;

public interface NotesRepository {

   void getAll(CallBack<List<Note>> callback); //наш список заметок

   void addNote(String title, String message, CallBack<Note> callback);
   void removeNote(Note note,CallBack<Void> callback);

   void updateNote(Note note, String title, String message, CallBack<Note> callback);
}