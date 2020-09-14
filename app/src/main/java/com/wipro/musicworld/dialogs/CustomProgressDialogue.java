package com.wipro.musicworld.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomProgressDialogue {
	private static Dialog dialog;

	public static Dialog getProgressDialogue(Context context, String message) {
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout dialogLayout = new LinearLayout(context);
		dialogLayout.setOrientation(LinearLayout.VERTICAL);
		dialogLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		ProgressBar progressBar = new ProgressBar(context);
		dialogLayout.addView(progressBar, new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		TextView messageView = new TextView(context);
		messageView.setText(message);
		messageView.setTextColor(Color.WHITE);
		dialogLayout.addView(messageView, new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		dialog.setContentView(dialogLayout, new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.setCancelable(false);
		return dialog;
	}
}