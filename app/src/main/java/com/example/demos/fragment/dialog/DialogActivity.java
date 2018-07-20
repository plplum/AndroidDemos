package com.example.demos.fragment.dialog;

import com.example.demos.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class DialogActivity extends Activity{
	
	
	private ContentFragment contentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_single_fragment);
		
		FragmentManager fm = getFragmentManager();
		contentFragment = new ContentFragment();
		
		Bundle bundle = new Bundle();
		bundle.putString("text_value", "cilck here");
		contentFragment.setArguments(bundle);
		
		fm.beginTransaction().add(R.id.id_fragment_container, contentFragment).commit();
	}

}
