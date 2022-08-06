package com.maximmesh.notes.domain;

import java.util.List;

public interface NotesRepository {

   List<Note> getAll();
}