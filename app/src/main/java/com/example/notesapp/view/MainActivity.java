package com.example.notesapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp.R;
import com.example.notesapp.controller.NotesAdapter;
import com.example.notesapp.database.NotesDataBase;
import com.example.notesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	
	private RecyclerView noteRecView;
	private final List < Note > noteList = new ArrayList <> ( );
	private NotesAdapter notesAdapter;
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_main );
		
		findViewById ( R.id.imageAddNoteMain ).setOnClickListener ( v -> startActivity ( new Intent ( MainActivity.this , CreateNoteActivity.class ) ) );
		noteRecView = findViewById ( R.id.notesRecView );
		noteRecView.setLayoutManager ( new
				StaggeredGridLayoutManager ( 2 , StaggeredGridLayoutManager.VERTICAL ) );
		
		notesAdapter = new NotesAdapter ( noteList );
		noteRecView.setAdapter ( notesAdapter );
		
//		getNotes ( );
	}
	
	@Override
	protected void onStart ( ) {
		super.onStart ( );
		getNotes ();
	}
	
	private void getNotes ( ) {
		
		@SuppressLint ( "StaticFieldLeak" )
		class getTaskNote extends AsyncTask < Void, Void, List < Note > > {
			
			@Override
			protected List < Note > doInBackground ( Void... voids ) {
				return NotesDataBase.getDatabase ( getApplicationContext ( ) ).noteDao ( ).getAllNotes ( );
			}
			
			@Override
			protected void onPostExecute ( List < Note > notes ) {
				super.onPostExecute ( notes );
				Log.d ( "MY_NOTES" , notes.toString ( ) );
				if ( noteList.size ( ) == 0 ) {
					noteList.addAll ( notes );
					notesAdapter.notifyDataSetChanged ( );
				}
				else {
					noteList.add ( 0 , notes.get ( 0 ) );
					notesAdapter.notifyItemInserted ( 0 );
				}
				noteRecView.smoothScrollToPosition ( 0
				);
			}
		}
		
		new getTaskNote ( ).execute ( );
	}
}