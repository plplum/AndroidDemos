package com.example.demos.slidingmenu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demos.R;



public class MenuFragment extends Fragment  {

	private View inflate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflate = inflater.inflate(R.layout.slidingmenu_layout_menu, null);
		
		
		ImageView imageView = (ImageView) inflate.findViewById(R.id.one);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast=Toast.makeText(getActivity(), "按钮被点击！", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		return inflate;
	}


}
