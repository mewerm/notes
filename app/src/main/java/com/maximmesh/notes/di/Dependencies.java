package com.maximmesh.notes.di;

import android.content.Context;
import com.maximmesh.notes.domain.NotesRepository;
import com.maximmesh.notes.domain.SharedPrefNotesRepository;

public class Dependencies { //класс зависимотси

   private static  NotesRepository notesRepository;

   public static NotesRepository getNotesRepository(Context context){

      if(notesRepository == null){
         notesRepository = new SharedPrefNotesRepository(context);
      }

      return notesRepository;
   }
}