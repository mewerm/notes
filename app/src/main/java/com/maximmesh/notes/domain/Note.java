package com.maximmesh.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {

   private String id;

   private String title;

   private String message;

   private Date cratedAt;

   public Note(String id, String title, String message, Date cratedAt) {
      this.id = id;
      this.title = title;
      this.message = message;
      this.cratedAt = cratedAt;
   }

   public String getId() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public String getMessage() {
      return message;
   }

   public Date getCrateAt() {
      return cratedAt;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {

   }
}