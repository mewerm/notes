package com.maximmesh.notes.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InMemoryNotesRepository implements NotesRepository {

   private ArrayList<Note> data = new ArrayList<>();

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
   public List<Note> getAll() {
      return data;
   }
}
