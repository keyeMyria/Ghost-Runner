package at.ac.tuwien.hci.ghost.ui;

import android.app.Activity;
import android.os.Bundle;
import at.ac.tuwien.hci.ghost.R;

public class WeatherActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.weather);
	}
}
