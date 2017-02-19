package fnn.smirl.game.sal;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity
{
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
	super.onCreate(savedInstanceState);
	//GameView gView = new GameView(this);
	GameView gView = new GameView(this);
	gView.setKeepScreenOn(true);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(
	 WindowManager.LayoutParams.FLAG_FULLSCREEN,
	 WindowManager.LayoutParams.FLAG_FULLSCREEN);

	setContentView(gView);
 }
}
