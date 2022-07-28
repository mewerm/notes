package com.maximmesh.notes.Constructor;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Note implements Parcelable {

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
   private static final Random random = new Random();
   private static ArrayList<Note> notes;
   private static ArrayList<Note> notez;
   private static int counter;

   static {
      notes = new ArrayList<>();
      for (int i = 0; i < 30; i++) {
         notes.add(Note.getNote(i));
      }
   }

   private int id;
   private String title;
   private String description;
   private LocalDateTime creationDate;

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

   public static ArrayList<Note> getNotes() {
      return notes;
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
}