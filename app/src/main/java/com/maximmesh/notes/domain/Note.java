package com.maximmesh.notes.domain;

import java.util.Date;

public class Note {

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
}