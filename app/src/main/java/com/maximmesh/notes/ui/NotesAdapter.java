package com.maximmesh.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.maximmesh.notes.R;
import com.maximmesh.notes.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

   private Fragment fragment;

   private OnNoteClicked noteClicked;


   public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd,MM,HH:mm", Locale.getDefault()); //паттер формат даты.

   private final List<Note> data = new ArrayList<>();

   public NotesAdapter(Fragment fragment) {
      this.fragment = fragment;
   }

   public void setData(Collection<Note> notes) {
      data.addAll(notes);
   }

   public void setNoteClicked(OnNoteClicked noteClicked) {
      this.noteClicked = noteClicked;
   }

   public OnNoteClicked getNoteClicked() {
      return noteClicked;
   }

   public int addNote(Note note) {
      data.add(note);

      return data.size() -1;
   }

   public void removeNote(Note selectedNote) {
      data.remove(selectedNote);
   }

   public void replaceNote(Note note, int selectedPosition) {
      data.set(selectedPosition, note);
   }

   interface OnNoteClicked{
      void onNoteClicked(Note note);

      void onNoteLongClicked(Note note, int position);
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

         CardView cardView =  itemView.findViewById(R.id.card_view);

         fragment.registerForContextMenu(cardView);

         cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(noteClicked != null){
                  int clickedPosition = getAdapterPosition();
                  noteClicked.onNoteClicked(data.get(clickedPosition));
               }
            }
         });

         cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               cardView.showContextMenu(); //показываем контекст меню

               if(noteClicked != null){
                  int clickedPosition = getAdapterPosition();

                  noteClicked.onNoteLongClicked(data.get(clickedPosition), clickedPosition);
               }

               return true; //если тру, то будет работать только на лонги, если фолс, то будут пробрасываться и одиночные клики
            }
         });
      }
   }
}