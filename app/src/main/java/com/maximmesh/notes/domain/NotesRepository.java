package com.maximmesh.notes.domain;

import java.util.List;

public interface NotesRepository {

   void getAll(CallBack<List<Note>> callback); //наш список заметок

   void addNote(String title, String message, CallBack<Note> callback);
}