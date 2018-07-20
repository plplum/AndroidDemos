package com.example.demos.contacts.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AlphabetIndexer;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.contacts.adapter.ContactAdapter;
import com.example.demos.contacts.model.Contact;
import com.example.demos.contacts.view.LetterListView;
import com.example.demos.contacts.view.LetterListView.OnItemClickListener;

public class ContactsActivity extends Activity {

	
	private LetterListView letterListView;
	
	/**
	 * 分组的布局
	 */
	private LinearLayout titleLayout;

	/**
	 * 分组上显示的字母
	 */
	private TextView title;
	
	
	
	//private RelativeLayout sectionToastLayout;

	/**
	 * 弹出式分组上的文字
	 */
	//private TextView sectionToastText;

	/**
	 * 联系人ListView
	 */
	private ListView contactsListView;

	/**
	 * 联系人列表适配器
	 */
	private ContactAdapter adapter;

	/**
	 * 用于进行字母表分组
	 */
	private AlphabetIndexer indexer;

	/**
	 * 存储所有手机中的联系人
	 */
	private List<Contact> contacts = new ArrayList<Contact>();

	/**
	 * 定义字母表的排序规则
	 */
	private String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 上次第一个可见元素，用于滚动时记录标识。
	 */
	private int lastFirstVisibleItem = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		adapter = new ContactAdapter(this, R.layout.contact_item, contacts);
		titleLayout = (LinearLayout) findViewById(R.id.title_layout);
		title = (TextView) findViewById(R.id.title);
		contactsListView = (ListView) findViewById(R.id.contacts_list_view);
		//sectionToastLayout = (RelativeLayout) findViewById(R.id.section_toast_layout);
		//sectionToastText = (TextView) findViewById(R.id.section_toast_text);
		
		
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		Cursor cursor = getContentResolver().query(uri,
				new String[] { "display_name", "sort_key" }, null, null,
				"sort_key");
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(0);
				String sortKey = getSortKey(cursor.getString(1));
				Contact contact = new Contact();
				contact.setName(name);
				contact.setSortKey(sortKey);
				contacts.add(contact);
			} while (cursor.moveToNext());
		}
		startManagingCursor(cursor);
		indexer = new AlphabetIndexer(cursor, 1, alphabet);
		adapter.setIndexer(indexer);
		if (contacts.size() > 0) {
			setupContactsListView();
			//setAlpabetListener();
		}
		letterListView = (LetterListView) this.findViewById(R.id.letter_view);
		letterListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(String s) {
				System.out.println("s =   " + s);
			//	int position = indexer.getPositionForSection(2);
			//	contactsListView.setSelection(position);
			}

			@Override
			public void onItemClick(int index) {
				System.out.println("index =   " + index);
				int position = indexer.getPositionForSection(index);
				contactsListView.setSelection(position);
			}
		});
		
	}

	/**
	 * 为联系人ListView设置监听事件，根据当前的滑动状态来改变分组的显示位置，从而实现挤压动画的效果。
	 */
	private void setupContactsListView() {
		contactsListView.setAdapter(adapter);
		contactsListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int section = indexer.getSectionForPosition(firstVisibleItem);
				int nextSecPosition = indexer.getPositionForSection(section + 1);
				if (firstVisibleItem != lastFirstVisibleItem) {
					MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
					params.topMargin = 0;
					titleLayout.setLayoutParams(params);
					title.setText(String.valueOf(alphabet.charAt(section)));
				}
				if (nextSecPosition == firstVisibleItem + 1) {
					View childView = view.getChildAt(0);
					if (childView != null) {
						int titleHeight = titleLayout.getHeight();
						int bottom = childView.getBottom();
						MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
						if (bottom < titleHeight) {
							float pushedDistance = bottom - titleHeight;
							params.topMargin = (int) pushedDistance;
							titleLayout.setLayoutParams(params);
						} else {
							if (params.topMargin != 0) {
								params.topMargin = 0;
								titleLayout.setLayoutParams(params);
							}
						}
					}
				}
				lastFirstVisibleItem = firstVisibleItem;
			}
		});

	}

	/**
	 * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
	 * 
	 * @param sortKeyString
	 *            数据库中读取出的sort key
	 * @return 英文字母或者#
	 */
	private String getSortKey(String sortKeyString) {
		//alphabetButton.getHeight();
		String key = sortKeyString.substring(0, 1).toUpperCase();
		if (key.matches("[A-Z]")) {
			return key;
		}
		return "#";
	}

}
