package com.example.notesapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp.R;
import com.example.notesapp.controller.NotesAdapter;
import com.example.notesapp.database.NotesDataBase;
import com.example.notesapp.listeners.NotesListener;
import com.example.notesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListener {
	
	public static final int REQUEST_CODE_UPDATE_NOTE = 2;
	public static final int REQUEST_CODE_SHOW_NOTE = 3;
	private static final int REQUEST_CODE_ADD_NOTE = 1;
	private final List < Note > noteList = new ArrayList <> ( );
	private RecyclerView noteRecView;
	private NotesAdapter notesAdapter;
	private int noteClickedPosition = - 1;
	
	@Override
	
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_main );
		
		findViewById ( R.id.imageAddNoteMain ).setOnClickListener ( v -> startActivityForResult ( new Intent ( MainActivity.this , CreateNoteActivity.class ) , REQUEST_CODE_ADD_NOTE ) );
		noteRecView = findViewById ( R.id.notesRecView );
		noteRecView.setLayoutManager ( new
				StaggeredGridLayoutManager ( 2 , StaggeredGridLayoutManager.VERTICAL ) );
		
		notesAdapter = new NotesAdapter ( noteList , this );
		noteRecView.setAdapter ( notesAdapter );
		
		getNotes ( REQUEST_CODE_SHOW_NOTE , false );
	}
	
	@Override
	protected void onStart ( ) {
		super.onStart ( );
		getNotes ( REQUEST_CODE_SHOW_NOTE , false );
	}
	
	private void getNotes ( final int requestCode , final boolean isNoteDeleted ) {
		
		@SuppressLint ( "StaticFieldLeak" )
		class getTaskNote extends AsyncTask < Void, Void, List < Note > > {
			
			@Override
			protected List < Note > doInBackground ( Void... voids ) {
				return NotesDataBase.getDatabase ( getApplicationContext ( ) ).noteDao ( ).getAllNotes ( );
			}
			
			@Override
			protected void onPostExecute ( List < Note > notes ) {
				noteList.clear ( );
				super.onPostExecute ( notes );
				if ( requestCode == REQUEST_CODE_SHOW_NOTE ) {
					noteList.addAll ( notes );
					notesAdapter.notifyDataSetChanged ( );
				}
				else if ( requestCode == REQUEST_CODE_ADD_NOTE ) {
					noteList.add ( 0 , notes.get ( 0 ) );
					notesAdapter.notifyItemInserted ( 0 );
					noteRecView.smoothScrollToPosition ( 0 );
				}
				else if ( requestCode == REQUEST_CODE_UPDATE_NOTE ) {
					noteList.remove ( noteClickedPosition );
					if ( isNoteDeleted ) {
						notesAdapter.notifyItemChanged ( noteClickedPosition );
					}
					else {
						noteList.add ( noteClickedPosition , notes.get ( noteClickedPosition ) );
						notesAdapter.notifyItemChanged ( noteClickedPosition );
					}
				}
			}
		}
		
		new getTaskNote ( ).execute ( );
	}
	
	@Override
	protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data ) {
		super.onActivityResult ( requestCode , resultCode , data );
		if ( requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK ) {
			getNotes ( REQUEST_CODE_ADD_NOTE , false );
		}
		else if ( requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK ) {
			if ( data != null ) {
				getNotes ( REQUEST_CODE_UPDATE_NOTE , data.getBooleanExtra ( "isNoteDeleted" , false ) );
			}
		}
	}
	
	@Override
	public void onNoteClicked ( Note note , int position ) {
		noteClickedPosition = position;
		Intent intent = new Intent ( this , CreateNoteActivity.class );
		intent.putExtra ( "isViewOrUpdate" , true );
		intent.putExtra ( "note" , note );
		startActivity ( intent );
	}
}