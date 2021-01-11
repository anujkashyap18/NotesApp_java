package com.example.notesapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.R;
import com.example.notesapp.database.NotesDataBase;
import com.example.notesapp.model.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {
	
	private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
	private TextView textDateTime;
	private String selectedNoteColor;
	private View viewSubtitleIndicator;
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_create_note );
		
		findViewById ( R.id.back ).setOnClickListener ( v -> onBackPressed ( ) );
		
		inputNoteTitle = findViewById ( R.id.inputNoteTitle );
		inputNoteSubtitle = findViewById ( R.id.inputNoteSubtitle );
		inputNoteText = findViewById ( R.id.inputNote );
		textDateTime = findViewById ( R.id.textTimeDate );
		viewSubtitleIndicator = findViewById ( R.id.viewSubtitleIndicator );
		
		textDateTime.setText ( new SimpleDateFormat ( "EEEE,dd MMMM HH:mm a" , Locale.getDefault ( ) ).format ( new Date ( ) ) );
		ImageView imageSave = findViewById ( R.id.imageSave );
		imageSave.setOnClickListener ( v -> saveNote ( ) );
		
		selectedNoteColor = "#333333";
		
		initMiscellaneous ( );
		setSubtitleIndicator ( );
	}
	
	private void saveNote ( ) {
		if ( inputNoteTitle.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
			Toast.makeText ( this , "Note title can't be empty." , Toast.LENGTH_SHORT ).show ( );
			return;
		}
		else if ( inputNoteSubtitle.getText ( ).toString ( ).trim ( ).isEmpty ( ) && inputNoteText.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
			Toast.makeText ( this , "Note can't be empty" , Toast.LENGTH_SHORT ).show ( );
			return;
		}
		
		final Note note = new Note ( );
		note.setTitle ( inputNoteTitle.getText ( ).toString ( ) );
		note.setSubtitle ( inputNoteSubtitle.getText ( ).toString ( ) );
		note.setNoteText ( inputNoteText.getText ( ).toString ( ) );
		note.setDateTime ( textDateTime.getText ( ).toString ( ) );
		note.setColor ( selectedNoteColor );
		
		@SuppressLint ( "StaticFieldLeak" )
		class SaveNoteTask extends AsyncTask < Void, Void, Void > {
			
			@Override
			protected Void doInBackground ( Void... voids ) {
				NotesDataBase.getDatabase ( getApplicationContext ( ) ).noteDao ( ).insertNote ( note );
				return null;
			}
			
			@Override
			protected void onPostExecute ( Void unused ) {
				super.onPostExecute ( unused );
				Intent intent = new Intent ( );
				setResult ( RESULT_OK , intent );
				finish ( );
			}
		}
		new SaveNoteTask ( ).execute ( );
	}
	
	private void initMiscellaneous ( ) {
		final LinearLayout layoutMiscellaneous = findViewById ( R.id.layout_miscellaneous );
		final BottomSheetBehavior < LinearLayout > bottomSheetBehavior = BottomSheetBehavior.from ( layoutMiscellaneous );
		layoutMiscellaneous.findViewById ( R.id.textMiscellaneous ).setOnClickListener ( v -> {
			if ( bottomSheetBehavior.getState ( ) != BottomSheetBehavior.STATE_EXPANDED ) {
				bottomSheetBehavior.setState ( BottomSheetBehavior.STATE_EXPANDED );
			}
			else {
				bottomSheetBehavior.setState ( BottomSheetBehavior.STATE_COLLAPSED );
			}
		} );
		
		final ImageView imageColor1 = layoutMiscellaneous.findViewById ( R.id.imageColor1 );
		final ImageView imageColor2 = layoutMiscellaneous.findViewById ( R.id.imageColor2 );
		final ImageView imageColor3 = layoutMiscellaneous.findViewById ( R.id.imageColor3 );
		final ImageView imageColor4 = layoutMiscellaneous.findViewById ( R.id.imageColor4 );
		final ImageView imageColor5 = layoutMiscellaneous.findViewById ( R.id.imageColor5 );
		
		layoutMiscellaneous.findViewById ( R.id.viewColor1 ).setOnClickListener ( v -> {
			selectedNoteColor = "#333333";
			imageColor1.setImageResource ( R.drawable.done );
			imageColor2.setImageResource ( 0 );
			imageColor3.setImageResource ( 0 );
			imageColor4.setImageResource ( 0 );
			imageColor5.setImageResource ( 0 );
			setSubtitleIndicator ( );
		} );
		
		layoutMiscellaneous.findViewById ( R.id.viewColor2 ).setOnClickListener ( v -> {
			selectedNoteColor = "#ff9800";
			imageColor1.setImageResource ( 0 );
			imageColor2.setImageResource ( R.drawable.done );
			imageColor3.setImageResource ( 0 );
			imageColor4.setImageResource ( 0 );
			imageColor5.setImageResource ( 0 );
			setSubtitleIndicator ( );
		} );
		
		layoutMiscellaneous.findViewById ( R.id.viewColor3 ).setOnClickListener ( v -> {
			selectedNoteColor = "#ED4848";
			imageColor1.setImageResource ( 0 );
			imageColor2.setImageResource ( 0 );
			imageColor3.setImageResource ( R.drawable.done );
			imageColor4.setImageResource ( 0 );
			imageColor5.setImageResource ( 0 );
			setSubtitleIndicator ( );
		} );
		
		layoutMiscellaneous.findViewById ( R.id.viewColor4 ).setOnClickListener ( v -> {
			selectedNoteColor = "#FFBB86FC";
			imageColor1.setImageResource ( 0 );
			imageColor2.setImageResource ( 0 );
			imageColor3.setImageResource ( 0 );
			imageColor4.setImageResource ( R.drawable.done );
			imageColor5.setImageResource ( 0 );
			setSubtitleIndicator ( );
		} );
		
		layoutMiscellaneous.findViewById ( R.id.viewColor5 ).setOnClickListener ( v -> {
			selectedNoteColor = "#121212";
			imageColor1.setImageResource ( 0 );
			imageColor2.setImageResource ( 0 );
			imageColor3.setImageResource ( 0 );
			imageColor4.setImageResource ( 0 );
			imageColor5.setImageResource ( R.drawable.done );
			setSubtitleIndicator ( );
		} );
	}
	
	private void setSubtitleIndicator ( ) {
		GradientDrawable gradientDrawable = ( GradientDrawable ) viewSubtitleIndicator.getBackground ( );
		gradientDrawable.setColor ( Color.parseColor ( selectedNoteColor ) );
	}
}