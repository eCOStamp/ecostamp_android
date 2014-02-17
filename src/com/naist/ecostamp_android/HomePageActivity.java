package com.naist.ecostamp_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class HomePageActivity extends Activity implements OnMenuItemClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	// Menu Handler 
	//http://developer.android.com/guide/topics/ui/menus.html
	public void showRightMenu(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		popup.setOnMenuItemClickListener(this);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.home_menu, popup.getMenu());
		popup.show();
	}

	// Menu Item Handler
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		boolean handled = true;
		switch (item.getItemId()) {
		case R.id.home_menu_logout:
			break;
		case R.id.home_menu_add_stamp:
			break;
		case R.id.home_menu_setting:
			break;
		default:
			handled = false;
		}
		return handled;
	}
	
}
