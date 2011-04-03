package com.imFind;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

//this is the main activity, allows selection to take picture, look at credits
//creation of this activity as mainly to test activity switching

public class homescreen extends Activity implements OnClickListener {
	Button bcredit;  //define out buttons
	Button btakepic;
	Button blearn;
	Button brecognize;
	private static int TAKE_PICTURE = 1;
	private Uri outputFileUri;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        bcredit = (Button)this.findViewById(R.id.bcredit);  //set up buttons
        btakepic = (Button)this.findViewById(R.id.btakepic);
        blearn = (Button)this.findViewById(R.id.blearn);
        brecognize = (Button)this.findViewById(R.id.brecognize);
		blearn.setOnClickListener(this);  //listen for the clicks
		brecognize.setOnClickListener(this);
		bcredit.setOnClickListener(this);
		btakepic.setOnClickListener(this);	
		File imageFile = new File(Environment.getExternalStorageDirectory(), "test.jpg");
		if (imageFile.exists()) //checks to see if we have a "current" image
		{
        	Bitmap myBitmap = BitmapFactory.decodeFile("/sdcard/test.jpg");  //oh good, now we can load it
        	ImageView myImage = (ImageView) findViewById(R.id.imageView1);  // thats the picture that will be recognized/learned
        	myImage.setImageBitmap(myBitmap);
		}
    }


	public void onClick(View v) {
		if (bcredit.isPressed()) //select which button is pressed
		{
			//this is the code for popup window
			popupcredit();
		}
		else if (btakepic.isPressed())
		{
			TakePhoto();	 
		}
		else if(blearn.isPressed())
		{
			popuplearn(); //saves what was learned to /sdcard/currentlearned.txt
			//we need the recognizer to load the imFind.jpg and the currentlearned.txt to do its algorithm
		}
		else if (brecognize.isPressed())
		{
			//TODO: some things with the learned
			//then return what is it
		}

	}
	
	private void TakePhoto() {
		new File("/sdcard/test.jpg").delete() ;  //deletes last image
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //launch android camera
		File file = new File("/sdcard/test.jpg"); //saves test.jpg
		outputFileUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, TAKE_PICTURE);
	}
	private void popupcredit(){ //this is the popup function
		AlertDialog.Builder alert = new AlertDialog.Builder(homescreen.this);
		alert.setTitle("imFind: Credits");
		alert.setMessage("Jeff Crowell: GUI // Other Names: Other Duties // Check if we can fit all of the names// I think this should work");
		alert.setIcon(R.drawable.icon); //we need an actual icon, not urgent though
		alert.setPositiveButton("OK",
		 new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int id) {
		  }
		 });
		alert.show();
	}
	private void popuplearn(){
		new File("/sdcard/currentlearning.txt").delete();
		AlertDialog.Builder alert = new AlertDialog.Builder(homescreen.this);

		alert.setTitle("imFind Learner");
		alert.setMessage("what did you take a picture of?");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  // Do something with value!
			Editable value = input.getText();
			String learned=value.toString();
            FileWriter fWriter;
            try{
                 fWriter = new FileWriter("/sdcard/currentlearning.txt");
                 fWriter.write(learned);
                 fWriter.flush();
                 fWriter.close();
             }catch(Exception e){
                      e.printStackTrace();
             }
			//saves it to a file
		  }
		});
		alert.show();
	}
	@Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == TAKE_PICTURE) {  //we took a picture
        	new File("/sdcard/imFind.jpg").delete(); //delete previous bitmap
        	Bitmap myBitmap = BitmapFactory.decodeFile("/sdcard/test.jpg"); //load picture from camera
        	ImageView myImage = (ImageView) findViewById(R.id.imageView1);
        	myImage.setImageBitmap(myBitmap);
        	int width=myBitmap.getWidth();
        	int height=myBitmap.getHeight();
        	int newWidth=600;  //scale to a known size, as different cameras output different sizes
        	int newHeight=800;
        	float scaleWidth = ((float) newWidth) / width;  //fancy manipulation for scaling
        	float scaleHeight = ((float) newHeight) / height;
        	Matrix matrix = new Matrix();
        	matrix.postScale(scaleWidth, scaleHeight);
        	matrix.postRotate(0);
        	Bitmap resizedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true); 
    		File file = new File(Environment.getExternalStorageDirectory(), "imFind.jpg");  //saves the new bitmap
            try{  //required this exception, don't know why
            	OutputStream fOut = null;
            	fOut = new FileOutputStream(file);
            	resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            	fOut.flush();
            	fOut.close();
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
        	
        }  
    }  
}