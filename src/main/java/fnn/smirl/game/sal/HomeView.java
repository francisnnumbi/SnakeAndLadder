package fnn.smirl.game.sal;
import android.graphics.*;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import fnn.smirl.game.sal.components.GameButton;
import fnn.smirl.game.sal.constant.Sound;


public class HomeView extends View {
 public Context ctx;
 private Resources res;
 private String refText ="Snakes and Ladders";
 private GameButton gButton, sButton, hButton;
 private Bitmap logo, play, settings, help;
 private int hWidth, hHeight;
 private Point touchCoordinate = new Point();


 public HomeView(Context ctx) {
	super(ctx);
	this.ctx = ctx;
	res = getResources();
	logo = BitmapFactory.decodeResource(res, R.drawable.snakes_n_ladders);

 }

 @Override
 protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// TODO: Implement this method
	super.onSizeChanged(w, h, oldw, oldh);
	hWidth = w;
	hHeight = h;
	preparePlayButton();
	prepareSettingsButton();
	prepareHelpButton();
 }

 private void preparePlayButton() {
	String text ="JOUER";
	int textSize = 30;
	Point po = getBmpMeasureFrom(text, refText, textSize);
	play = getBitmap(R.drawable.play, po.x, po.y);
	gButton = new GameButton(play, text, textSize);
	gButton.setLocation((hWidth - po.x) / 2, (int)(hHeight * 0.6f));
	gButton.setTextColor(Color.CYAN);
 }
 private void prepareSettingsButton() {
	String text ="CONFIGURATIONS";
	int textSize = 30;
	Point po = getBmpMeasureFrom(text, refText, textSize);
	settings = getBitmap(R.drawable.play, po.x, po.y);
	sButton = new GameButton(settings, text, textSize);
	sButton.setLocation((hWidth - po.x) / 2, (int)(hHeight * 0.7f));
	sButton.setTextColor(Color.CYAN);
 }


 private void prepareHelpButton() {
	String text ="AIDE";
	int textSize = 30;
	Point po = getBmpMeasureFrom(text, refText, textSize);
	help = getBitmap(R.drawable.play, po.x, po.y);
	hButton = new GameButton(help, text, textSize);
	hButton.setLocation((hWidth - po.x) / 2, (int)(hHeight * 0.8f));
	hButton.setTextColor(Color.CYAN);
 }

 private Point getBmpMeasureFrom(String text, String refText, int textSize) {
	Point p = new Point();
	Paint pa = new Paint();
	pa.setTextSize(textSize);
	p.y = (int)(pa.getTextSize() * 2.2f);
	String tt = refText.length() > text.length() ?refText: text;
	p.x = (int) (pa.measureText(tt) * 1.2f);
	return p;
 }

 @Override
 public boolean onTouchEvent(MotionEvent event) {
	// TODO: Implement this method
	int evt = event.getAction();
	touchCoordinate.x = (int)event.getX();
	touchCoordinate.y = (int)event.getY();
	switch (evt) {
	 case MotionEvent.ACTION_DOWN:
MainActivity.playSound(Sound.BUTTON_CLICK_1, false);
		break;
	 case MotionEvent.ACTION_MOVE:

		break;
	 case MotionEvent.ACTION_UP:
		MainActivity.stopSound();
		if (gButton.contains(touchCoordinate)) {
		 Intent intent = new Intent(ctx, GameActivity.class);
		 ctx. startActivity(intent);
		}
		if (sButton.contains(touchCoordinate)) {
		 
//		 Intent intent = new Intent(ctx, GameActivity.class);
//		 ctx. startActivity(intent);
		}
		if (hButton.contains(touchCoordinate)) {
		 
//		 Intent intent = new Intent(ctx, GameActivity.class);
//		 ctx. startActivity(intent);
		}	

		break;
	}
	return true;
 }



 @Override
 protected void onDraw(Canvas canvas) {
	// TODO: Implement this method
	super.onDraw(canvas);
	canvas.drawColor(Color.GRAY);

	canvas.drawBitmap(logo, (hWidth - logo.getWidth()) / 2, (int)((hHeight - logo.getHeight()) * 0.1), null);
	gButton.onDraw(canvas);
	sButton.onDraw(canvas);
	hButton.onDraw(canvas);
//	Paint p= new Paint();
//	p.setColor(Color.RED);
//	canvas.drawRect(100, 200, 400, 500, p);
	invalidate();
 }

 private Bitmap getBitmap(int drawableRes, int width, int height) {
	Drawable d = res.getDrawable(drawableRes);
	Canvas c = new Canvas();
	Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	c.setBitmap(b);
	d.setBounds(0, 0, width, height);
	d.draw(c);
	return b;
 }

}
