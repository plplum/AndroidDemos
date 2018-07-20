package com.example.demos.contacts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.demos.R;

public class LetterListView extends View {
	
	/**字母列表点击监听器*/
	private OnItemClickListener mOnItemClickListener;
	
	/**字母列表*/
	String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };
	
	/**当前选中项目序号*/
	int choose = -1;
	
	/**绘图工具*/
	Paint paint = new Paint();
	
	/**是否显示字母列表背景*/
	boolean showBkg = false;
	
	/**用于展示字母列表*/
	private PopupWindow mPopupWindow;
	
	/**当前选中的字母*/
	private TextView mPopupText;
	
	/**字体大小*/
	private int mCharHeight = 15;

	public LetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mCharHeight = context.getResources().getDimensionPixelSize(
				R.dimen.letter_text_size);
	}

	public LetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCharHeight = context.getResources().getDimensionPixelSize(
				R.dimen.letter_text_size);
	}

	public LetterListView(Context context) {
		super(context);
		mCharHeight = context.getResources().getDimensionPixelSize(
				R.dimen.letter_text_size);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#4d000000"));
		} else {
			canvas.drawColor(Color.parseColor("#00000000"));
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.parseColor("#ff555555"));
			// paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(mCharHeight);
			paint.setFakeBoldText(true);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
			}
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	protected Parcelable onSaveInstanceState() {
		dismissPopup();
		return super.onSaveInstanceState();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c) {
				if (c > 0 && c < b.length) {
					performItemClicked(c);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c) {
				if (c > 0 && c < b.length) {
					performItemClicked(c);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			dismissPopup();
			invalidate();
			break;
		}
		return true;
	}

	private void showPopup(int item) {
		if (mPopupWindow == null) {
			mPopupText = new TextView(getContext());
			mPopupText
					.setBackgroundResource(R.drawable.ic_contacts_index_backgroud_sprd);
			mPopupText.setTextColor(Color.CYAN);
			mPopupText.setTextSize(50);
			mPopupText.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL);
			mPopupWindow = new PopupWindow(mPopupText,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}

		String text = "";
		if (item == 0) {
			text = "#";
		} else {
			text = Character.toString((char) ('A' + item - 1));
		}
		mPopupText.setText(text);
		if (mPopupWindow.isShowing()) {
			mPopupWindow.update();
		} else {
			mPopupWindow.showAtLocation(getRootView(),
					Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
		}
	}

	private void dismissPopup() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
	}

	private void performItemClicked(int item) {
		if (mOnItemClickListener != null) {
			//mOnItemClickListener.onItemClick(b[item]);
			mOnItemClickListener.onItemClick(item);
			showPopup(item);
		}
	}

	public interface OnItemClickListener {
		void onItemClick(String s);
		void onItemClick(int index);
	}

}
