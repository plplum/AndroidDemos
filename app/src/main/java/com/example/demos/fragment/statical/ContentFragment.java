package com.example.demos.fragment.statical;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demos.R;


public class ContentFragment extends Fragment
{
	private String mArgument;  
	public static final String ARGUMENT_USERNANE = "argument_username";
	public static final String ARGUMENT_USERACCOUNT = "argument_useraccount";  
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		if (mArgument != null)
			Toast.makeText(getActivity(), "Activity传入的参数username= " + mArgument, Toast.LENGTH_LONG).show();
		return inflater.inflate(R.layout.fragment_content, container, false);
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取Activity传入的参数
		Bundle bundle = getArguments();
		if (bundle != null)
			mArgument = bundle.getString(ARGUMENT_USERNANE);
		
	}
}
