package com.maximmesh.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maximmesh.notes.R;
import com.maximmesh.notes.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


   public OnNoteClicked getNoteClicked() {
      return noteClicked;
   }

   private OnNoteClicked noteClicked;


   public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd,MM,HH:mm", Locale.getDefault()); //паттер формат даты.

   private final List<Note> data = new ArrayList<>();

   public void setData(Collection<Note> notes) {
      data.addAll(notes);
   }

   public void setNoteClicked(OnNoteClicked noteClicked) {
      this.noteClicked = noteClicked;
   }

   interface OnNoteClicked{
      void onNoteClicked(Note note);
   }

   @NonNull
   @Override
   public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

      NotesViewHolder holder = new NotesViewHolder(itemView);
      return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
      Note note = data.get(position);
      holder.title.setText(note.getTitle());
      holder.message.setText(note.getMessage());
      holder.date.setText(simpleDateFormat.format(note.getCrateAt()));

   }

   @Override
   public int getItemCount() {
      return data.size();
   }

   public class NotesViewHolder extends RecyclerView.ViewHolder {

      TextView title;
      TextView message;
      TextView date;

      public NotesViewHolder(@NonNull View itemView) {
         super(itemView);

         title = itemView.findViewById(R.id.title);
         message = itemView.findViewById(R.id.message);
         date = itemView.findViewById(R.id.date);

         itemView.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(noteClicked != null){
                  int clickedPosition = getAdapterPosition();
                  noteClicked.onNoteClicked(data.get(clickedPosition));
               }
            }
         });
      }
   }
}