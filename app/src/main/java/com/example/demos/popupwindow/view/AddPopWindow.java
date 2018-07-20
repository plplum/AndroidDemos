package com.example.demos.popupwindow.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.demos.R;

public class AddPopWindow extends PopupWindow {
	private View conentView;

	public AddPopWindow(final Activity context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conentView = inflater.inflate(R.layout.add_popup_dialog, null);
		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		// 设置SelectPicPopupWindow的View
		this.setContentView(conentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w / 2 );
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0);
		this.setBackgroundDrawable(dw);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimationPreview);
		LinearLayout addTaskLayout = (LinearLayout) conentView.findViewById(R.id.add_task_layout);
		LinearLayout teamMemberLayout = (LinearLayout) conentView.findViewById(R.id.team_member_layout);
		addTaskLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				AddPopWindow.this.dismiss();
			}
		});

		teamMemberLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPopWindow.this.dismiss();
			}
		});
	}

	/**
	 * 显示popupWindow
	 * 
	 * @param parent
	 */
	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
			this.showAsDropDown(parent, 0, 13);
		} else {
			this.dismiss();
		}
	}
}
