package com.maximmesh.notes.Constructor;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.maximmesh.notes.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Note implements Parcelable {

   private static final Random random = new Random();
   private static ArrayList<Note> notes;
   private static int counter;
   private static int noteNumbers;
   private int id;
   private String title;
   private String description;
   private LocalDateTime creationDate;

   static {
      noteNumbers = 32;
      notes = new ArrayList<>();
      for (int i = 0; i <= noteNumbers; i++) {
         notes.add(Note.getNote(i));
      }
   }

   {
      id = ++counter;
   }
   public Note(String title, String description, LocalDateTime creationDate) {
      this.title = title;
      this.description = description;
      this.creationDate = creationDate;
   }
   protected Note(Parcel parcel) {
      id = parcel.readInt();
      title = parcel.readString();
      description = parcel.readString();
      creationDate = (LocalDateTime) parcel.readSerializable();
   }

   public static void setNoteNumbers(int noteNumbers) {
      Note.noteNumbers = noteNumbers;
   }

   public static ArrayList<Note> getNotes() {
      return notes;
   }

   public static void setNotes(ArrayList<Note> notes) {
      Note.notes = notes;
   }

   @SuppressLint("DefaultLocale")
   public static Note getNote(int index) {
      String title = String.format("Заметка %d", index);
      String description = String.format("Описание заметки %d", index);
      LocalDateTime creationDate = LocalDateTime.now().plusDays(-random.nextInt(5));
      return new Note(title, description, creationDate);
   }

   public int getId() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public LocalDateTime getCreationDate() {
      return creationDate;
   }

   public static int getNoteNumbers() {
      return noteNumbers;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeInt(getId());
      parcel.writeString(getTitle());
      parcel.writeString(getDescription());
      parcel.writeSerializable(getCreationDate());
   }

   public static final Creator<Note> CREATOR = new Creator<Note>() {
      @Override
      public Note createFromParcel(Parcel in) {
         return new Note(in);
      }

      @Override
      public Note[] newArray(int size) {
         return new Note[size];
      }
   };
}