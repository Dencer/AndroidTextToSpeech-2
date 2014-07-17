package hiSpark2014.puzzle.texttospeech;



import java.util.Locale;







import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AndroidTextToSpeechActivity extends Activity implements
		TextToSpeech.OnInitListener {
	/** Called when the activity is first created. */
	String name;
	private TextToSpeech tts;
	private Button btnSpeak;
	private EditText txtText;
	private DatabaseHandler dbhelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tts = new TextToSpeech(this, this);

		btnSpeak = (Button) findViewById(R.id.btnSpeak);

		txtText = (EditText) findViewById(R.id.txtText);

		// button on click event
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				speakOut();
				Toast.makeText(getApplicationContext(),"Saved into database",Toast.LENGTH_LONG).show();
			}

		});
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);
          
			 tts.setPitch(1); // set pitch level

		     tts.setSpeechRate(-5); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				btnSpeak.setEnabled(true);
				//speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {
		
		dbhelper=new DatabaseHandler(this);
		String text = txtText.getText().toString();
		if(text.length()>0)
    	{
		dbhelper.open();
		dbhelper.updatename(text);
//		Cursor chData=dbhelper.getname();
//		if(chData.moveToFirst())
//		{
//			do
//			{
//				name = chData.getString(0);
//				
//				System.out.println("name===="+name);
//			
//				
//				
//			}while(chData.moveToNext());
//		}
//		if(name!=null)
//		{
//			dbhelper.updatename(text);
//		}else
//		{
//			dbhelper.insertName(text);
//		}
		tts.speak("Welcome"+text+"to the game of puzzle", TextToSpeech.QUEUE_FLUSH, null);
    	}
		else
		{
			AlertDialog alertDialog = new AlertDialog.Builder(
					AndroidTextToSpeechActivity.this).create();
    		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alertDialog.setTitle("Invalid Data..");
			alertDialog.setMessage("Please enter the Kidname..");
    		
			tts.speak("Please enter the Kidname", TextToSpeech.QUEUE_FLUSH, null);
    		    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
    	
				public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
					
				}
			});
    		    
    		    alertDialog.show();
		}
	}
}