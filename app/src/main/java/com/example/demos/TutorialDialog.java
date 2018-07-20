package com.example.demos;


import android.app.Dialog;
import android.content.Context;



public class TutorialDialog extends Dialog {

	/**
	 * @param context
	 */
	public TutorialDialog(Context context) {
		super(context);
		initialize(context);
	}

	/**
	 * @param context
	 * @param theme
	 */
	public TutorialDialog(Context context, int theme) {
		super(context, theme);
		initialize(context);
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public TutorialDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initialize(context);
	}

	/**
	 * Common initialization code
	 */
	private final void initialize(final Context context) {
		setContentView(R.layout.tutorial);
		setTitle(R.string.app_welcome_info);
	}
}
