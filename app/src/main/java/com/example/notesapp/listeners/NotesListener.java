package com.example.notesapp.listeners;

import com.example.notesapp.model.Note;

public interface NotesListener {
	void onNoteClicked ( Note note , int position );
}
