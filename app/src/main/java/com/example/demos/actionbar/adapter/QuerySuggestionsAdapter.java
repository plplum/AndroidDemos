package com.example.demos.actionbar.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.demos.R;

public class QuerySuggestionsAdapter extends CursorAdapter {

	public QuerySuggestionsAdapter(Context context, Cursor c) {
		super(context, c, 0);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		//View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		View v = inflater.inflate(R.layout.search_item_layout, parent, false);
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		/*TextView tv = (TextView) view;
		//final int textIndex = cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1);
		final int textIndex = cursor.getColumnIndex("tb_name");
		tv.setText(cursor.getString(textIndex));*/
		
		final int textIndex = cursor.getColumnIndex("tb_name");
	    final TextView views = (TextView) view.findViewById(R.id.textview);
	    views.setText(cursor.getString(textIndex));
	}
	
	
	@Override
	public void changeCursor(Cursor cursor) {
		super.changeCursor(cursor);
	}
	
	
}