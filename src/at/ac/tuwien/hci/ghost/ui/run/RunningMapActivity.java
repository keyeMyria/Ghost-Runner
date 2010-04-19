package at.ac.tuwien.hci.ghost.ui.run;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import at.ac.tuwien.hci.ghost.R;
import at.ac.tuwien.hci.ghost.data.dao.GoalDAO;
import at.ac.tuwien.hci.ghost.data.entities.Goal;
import at.ac.tuwien.hci.ghost.ui.WeatherActivity;
import at.ac.tuwien.hci.ghost.ui.goal.NewGoalActivity;
import at.ac.tuwien.hci.ghost.util.Constants;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class RunningMapActivity extends MapActivity {
	private MapView mapView = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.runningmap);

		mapView = (MapView) findViewById(R.id.overviewMap);
		mapView.setBuiltInZoomControls(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, Constants.MENU_SWITCH_VIEW, 0, getResources().getString(R.string.run_info));
    	menu.add(0, Constants.MENU_SETTINGS, 1, getResources().getString(R.string.app_settings));
    	menu.add(0, Constants.MENU_WEATHER, 2, getResources().getString(R.string.app_weather));
    	
        return true;
    }
    
    /* Handles menu item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case Constants.MENU_SWITCH_VIEW:
        	Intent viewIntent = new Intent(this, RunningInfoActivity.class); 
    		this.startActivity(viewIntent);
            
            return true;
        case Constants.MENU_SETTINGS:
        	// TODO code for doing settings for goals
        	return true;
        	
        case Constants.MENU_WEATHER:
        	Intent weatherIntent = new Intent(this, WeatherActivity.class); 
    		this.startActivity(weatherIntent);
    		
        	return true;
        }
        return false;
    }

}