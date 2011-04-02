package com.imFind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class credits extends Activity implements OnClickListener {
    Button bback;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bback = (Button)this.findViewById(R.id.bback);
        bback.setOnClickListener(this);
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent(credits.this, homescreen.class);
		credits.this.startActivity(myIntent);
	}
}
