package com.maximmesh.notes.di;

import com.maximmesh.notes.domain.InMemoryNotesRepository;
import com.maximmesh.notes.domain.NotesRepository;

public class Dependencies {

   public static final NotesRepository NOTES_REPOSITORY = new InMemoryNotesRepository();
}