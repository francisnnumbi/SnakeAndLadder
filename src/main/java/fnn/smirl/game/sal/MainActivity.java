package fnn.smirl.game.sal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import java.util.HashMap;
import android.content.Context;
import fnn.smirl.game.sal.constant.Sound;

public class MainActivity extends Activity {
 public static HashMap<Sound, MediaPlayer> SOUND_MAP=
 new HashMap<Sound, MediaPlayer>();
 public static int userScore= 0, computerScore=0,
 buddyBoxId = 1, computerBoxId = 1;
 public static Context CTX;

 static MediaPlayer media;
 static Handler mediaHandler;
 public static int stat=0, totTurn = 0, maxEnd = 100;
 public static SharedPreferences configs;
 public static SharedPreferences.Editor editConfigs;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//GameView gView = new GameView(this);
	HomeView gView = new HomeView(this);
	gView.setKeepScreenOn(true);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(
	 WindowManager.LayoutParams.FLAG_FULLSCREEN,
	 WindowManager.LayoutParams.FLAG_FULLSCREEN);

	setContentView(gView);
	CTX = getApplicationContext();
	configs = CTX. getSharedPreferences("snake_n_ladder", 0);
	editConfigs = configs.edit();
	loadConfig();
	loadMedia();
 }

// @Override
// protected void onDestroy() {
//	// TODO: Implement this method
//	super.onDestroy();
//	media.stop();
//	media.release();
// }
//
 @Override
 public void onBackPressed() {
	// TODO: Implement this method
	super.onBackPressed();
	media.stop();
	media.release();
 }
 
 

 @Override
 protected void onStop() {
	// TODO: Implement this method
	super.onStop();
	media.stop();
	media.release();
 }
 
 

 private void loadMedia() {
	SOUND_MAP.put(Sound.DICE_1, MediaPlayer.create(CTX, R.raw.roll_dice));
	SOUND_MAP.put(Sound.BUTTON_CLICK_1, MediaPlayer.create(CTX, R.raw.btn_click1));
	SOUND_MAP.put(Sound.BUTTON_PUSH_1, MediaPlayer.create(CTX, R.raw.btn_push1));
	SOUND_MAP.put(Sound.JUMP_2, MediaPlayer.create(CTX, R.raw.jump_2));
	SOUND_MAP.put(Sound.END_GAME_1, MediaPlayer.create(CTX, R.raw.end_game_1));
	SOUND_MAP.put(Sound.SHOOTING_STAR_1, MediaPlayer.create(CTX, R.raw.shooting_star_1));
	SOUND_MAP.put(Sound.HEAD_CHOP_DOWN_1, MediaPlayer.create(CTX, R.raw.head_chop_down_1));
	
	
 }

 public static void saveConfig() {
	editConfigs.putInt("userScore", userScore);
	editConfigs.putInt("computerScore", computerScore);
	editConfigs.putInt("stat", stat);
	editConfigs.putInt("totTurn", totTurn);
	editConfigs.putInt("maxEnd", maxEnd);
	editConfigs.putInt("buddyBoxId", buddyBoxId);
	editConfigs.putInt("computerBoxId", computerBoxId);
	editConfigs.commit();
 }

 public static void loadConfig() {
	userScore = configs.getInt("userScore", 0);
	computerScore = configs.getInt("computerScore", 0);
	stat = configs.getInt("stat", 0);
	totTurn = configs.getInt("totTurn", 0);
	maxEnd = configs.getInt("maxEnd", 100);
	buddyBoxId = configs.getInt("buddyBoxId", 1);
	computerBoxId = configs.getInt("computerBoxId", 1);
 }

 public static void playSound(Sound label, boolean loop) {
	//currentSoundIndex = index;
	if (mediaHandler == null)mediaHandler = new Handler();
	mediaHandler.post(soundRunnable);
	media = null;
	if (media == null)media = MainActivity.SOUND_MAP.get(label);
	media.start();
	media.setLooping(loop);

 }

 public static void stopSound() {
	if (media != null && media.isPlaying() && media.isLooping()) {
	 media.setLooping(false);
	 media.pause();
	 //media.reset();

	}
	mediaHandler.removeCallbacks(soundRunnable);
 }


 private static Runnable soundRunnable = new Runnable(){

	@Override
	public void run() {
	 // TODO: Implement this method
	 if (media != null && !media.isPlaying() && !media.isLooping()) {
		stopSound();
	 }

	 mediaHandler.postDelayed(soundRunnable, 10);
	}


 };
}
