package at.ac.tuwien.hci.ghost.ui;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.widget.TabHost;
import at.ac.tuwien.hci.ghost.R;
import at.ac.tuwien.hci.ghost.data.dao.GoalDAO;
import at.ac.tuwien.hci.ghost.data.dao.RunDAO;
import at.ac.tuwien.hci.ghost.data.entities.Entity;
import at.ac.tuwien.hci.ghost.data.entities.Goal;
import at.ac.tuwien.hci.ghost.data.entities.Run;
import at.ac.tuwien.hci.ghost.ui.goal.GoalsActivity;
import at.ac.tuwien.hci.ghost.ui.history.HistoryActivity;
import at.ac.tuwien.hci.ghost.ui.route.RoutesActivity;
import at.ac.tuwien.hci.ghost.ui.run.StartRunActivity;
import at.ac.tuwien.hci.ghost.util.AudioSpeaker;
import at.ac.tuwien.hci.ghost.util.Date;
import at.ac.tuwien.hci.ghost.util.AudioSpeaker.InitListener;

public class MainTabActivity extends android.app.TabActivity implements InitListener {
	private AudioSpeaker speaker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// init TTS
		speaker = new AudioSpeaker(this,this);
		// change formatting of numbers
		Locale.setDefault(Locale.US);

		final TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("TabRun").setIndicator(this.getResources().getText(R.string.app_tabRun),
				this.getResources().getDrawable(R.drawable.tab_run)).setContent(new Intent(this, StartRunActivity.class)));

		tabHost.addTab(tabHost.newTabSpec("TabRoutes").setIndicator(this.getResources().getText(R.string.app_tabRoutes),
				this.getResources().getDrawable(R.drawable.tab_routes)).setContent(new Intent(this, RoutesActivity.class)));

		// This tab sets the intent flag so that it is recreated each time the
		// tab is clicked
		tabHost.addTab(tabHost.newTabSpec("TabHistory").setIndicator(this.getResources().getText(R.string.app_tabHistory),
				this.getResources().getDrawable(R.drawable.tab_history)).setContent(
				new Intent(this, HistoryActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		tabHost.addTab(tabHost.newTabSpec("TabGoals").setIndicator(this.getResources().getText(R.string.app_tabGoals),
				this.getResources().getDrawable(R.drawable.tab_goal)).setContent(new Intent(this, GoalsActivity.class)));
	}

	@Override
	public void onDestroy() {
		speaker.shutdown();

		super.onDestroy();
	}

	private void sayStatsAndMotivation() {
		RunDAO runDAO = new RunDAO(this);
		GoalDAO goalDAO = new GoalDAO(this);
		Run lastCompletedRun = runDAO.getLastCompletedRun();
		String lastRunString = "";
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		if (prefs.getBoolean("speakStats", true)) {
			if (lastCompletedRun != null) {
				long dayDifference = lastCompletedRun.getDate().getDayDifference(new Date());
				lastRunString = getResources().getString(R.string.audio_lastRun_1) + " " + dayDifference + " "
						+ getResources().getString(R.string.audio_lastRun_2);
			} else {
				lastRunString = getResources().getString(R.string.audio_lastRunNone);
			}

			speaker.speak(lastRunString, TextToSpeech.QUEUE_FLUSH);

			// TODO: speak goal progress
			List<Entity> goals = goalDAO.getAll();

			for (Entity e : goals) {
				if (e instanceof Goal) {
					Goal g = (Goal) e;

					switch (g.getType()) {
						case RUNS:
							speaker.speak(getResources().getString(R.string.audio_goalRuns_1) 
									   + " " +  g.getProgress() + " " + 
									   getResources().getString(R.string.audio_goalRuns_2));
							break;
						
						case DISTANCE:
							speaker.speak(getResources().getString(R.string.audio_goalDistance_1) 
									   + " " +  g.getProgress() + " " + 
									   getResources().getString(R.string.audio_goalDistance_2));
							break;
							
						case CALORIES:
							speaker.speak(getResources().getString(R.string.audio_goalCalories_1) 
									   + " " +  g.getProgress() + " " + 
									   getResources().getString(R.string.audio_goalCalories_2));
							break;
					}
				}
			}
		}
	}

	@Override
	public void initFinished() {
		sayStatsAndMotivation();
	}
}
