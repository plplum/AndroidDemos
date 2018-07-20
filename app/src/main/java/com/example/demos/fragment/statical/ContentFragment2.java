package com.example.demos.fragment.statical;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demos.R;

public class ContentFragment2 extends Fragment
{
	
	
	@Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setHasOptionsMenu(true);  
    }  
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_content2, container, false);
	}

	
	@Override  
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)  
    {  
        inflater.inflate(R.menu.menu, menu);  
    }  
	
	
	@Override  
    public boolean onOptionsItemSelected(MenuItem item)  
    {  
        switch (item.getItemId())  
        {  
        case R.id.menu_about:  
            Toast.makeText(getActivity(), "ContentFragment2 MenuItem1", Toast.LENGTH_SHORT).show();  
            break;  
        }  
        return true;  
    }  
	
}
