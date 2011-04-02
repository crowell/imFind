package com.imFind;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
    }


	public void onClick(View v) {
		if (bcredit.isPressed()) //select which button is pressed
		{
			//this is the code for popup window

			alert();
			
			//Intent myIntent = new Intent(homescreen.this, credits.class); 
			//homescreen.this.startActivity(myIntent);  //start credits action
		}
		else if (btakepic.isPressed())
		{
			TakePhoto();	 
		}
		else if(blearn.isPressed())
		{
			//TODO: add stuff
		}
		else if (brecognize.isPressed())
		{
			//TODO: add stuff
		}

	}
	
	private void TakePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
 
		outputFileUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, TAKE_PICTURE);
 
	}
	private void alert(){
		AlertDialog.Builder alert = new AlertDialog.Builder(homescreen.this);

		alert.setTitle("CREDITS");
		alert.setMessage("Jeff Crowell: GUI");
		alert.setIcon(R.drawable.icon);
		alert.setPositiveButton("OK",
		 new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int id) {
		  }
		 });
		alert.show();
	}
	
	@Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == TAKE_PICTURE) {  
            // do something    
        	//File imageFile = new File(Environment.getExternalStorageDirectory(), "test.jpg");
        	Bitmap myBitmap = BitmapFactory.decodeFile("/sdcard/test.jpg");
        	ImageView myImage = (ImageView) findViewById(R.id.imageView1);
        	myImage.setImageBitmap(myBitmap);
        }  
    }  
}