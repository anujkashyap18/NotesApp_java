package com.example.notesapp.controller;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter < NotesAdapter.NoteVieHolder > {
	
	private List < Note > notes;
	
	public NotesAdapter ( List < Note > notes ) {
		this.notes = notes;
	}
	
	@NonNull
	@Override
	public NoteVieHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new NoteVieHolder ( LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.item_container_note,parent,false ) );
	}
	
	@Override
	public void onBindViewHolder ( @NonNull NotesAdapter.NoteVieHolder holder , int position ) {
		holder.setNote ( notes.get ( position ) );
	}
	
	@Override
	public int getItemCount ( ) {
		return notes.size ();
	}
	
	@Override
	public int getItemViewType ( int position ) {
		return position;
	}
	
	static class NoteVieHolder extends RecyclerView.ViewHolder {
		
		TextView textTitle, textSubtitle, textDateAndTime;
		LinearLayout layoutNote;
		
		public NoteVieHolder ( @NonNull View itemView ) {
			super ( itemView );
			textTitle = itemView.findViewById ( R.id.textTitle );
			textSubtitle = itemView.findViewById ( R.id.textSubtitle );
			textDateAndTime = itemView.findViewById ( R.id.textDateAndTime );
			layoutNote = itemView.findViewById (R.id.layoutNote );
		}
		
		void setNote ( Note note ) {
			textTitle.setText ( note.getTitle ( ) );
			if ( note.getSubtitle ( ).trim ( ).isEmpty ( ) ) {
				textSubtitle.setVisibility ( View.GONE );
			}
			else {
				textSubtitle.setText ( note.getNoteText ( ) );
			}
			textDateAndTime.setText ( note.getDateTime ( ) );
			
			GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground ();
			if ( note.getColor () != null ){
				gradientDrawable.setColor ( Color.parseColor ( note.getColor () ) );
			} else {
				gradientDrawable.setColor ( Color.parseColor ( "#333333" ) );
			}
		}
	}
}
