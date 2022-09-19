package com.maximmesh.notes.di;

import com.maximmesh.notes.domain.FireStoreNotesRepository;
import com.maximmesh.notes.domain.NotesRepository;

public class Dependencies { //класс зависимотси

   private static final NotesRepository NOTES_REPOSITORY = new FireStoreNotesRepository();

   public static NotesRepository getNotesRepository(){
      return NOTES_REPOSITORY;
   }
}