package fnn.smirl.game.sal.components;

import android.graphics.*;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import org.apache.http.message.HeaderGroup;

public class GameButton {
 private Rect rect;
 private int textColor = Color.BLACK;
 Bitmap btn;
 private int left, top, right, bottom;
 float paddingHorizontal = 0, paddingVertical = 0;
 public String text = "";
 int textHeight=0, textWidth = 0;
 private Paint paint = new Paint();;

 public GameButton(Bitmap btn, String text, int textSize) {
	this.text = text;
	this.btn = btn;
	right = btn.getWidth();
	bottom = btn.getHeight();
	paint.setAntiAlias(true);
	paint.setFakeBoldText(true);
	textHeight = textSize;
	paint.setTextSize(textHeight);
	//paint.setTextScaleX(adjustTextScale(text, btn.getWidth()));
	textWidth =(int) paint.measureText(text);
	rect = new Rect(left, top, right, bottom);
 }
 
 public void setTextColor(int color){
	textColor = color;
 }

 public void onDraw(Canvas c) {
	c.drawBitmap(btn, null, rect, null);

	paint.setColor(textColor);
	int df = left+((btn.getWidth()-textWidth)/2);
	int dr = top+(btn.getHeight()-(int)(textHeight/1.5f));
	c.drawText(text, df, dr, paint);
 }

 public String getText() {
	return text;
 }

// public Rect getRectangle() {
//	return rect;
// }

 public void setLocation(int x, int y) {
	left = x; top = y;
	rect.offsetTo(left, top);
 }

public boolean contains(Point p) {
	return rect.contains(p.x, p.y);
 }
 public boolean contains(int x, int y) {
return rect.contains(x, y);
 }
//
// public boolean contains(int left, int top, int right, int bottom) {
//	return rect.contains(left, top, right, bottom);
// }
// public boolean contains(Rect rectangle) {
//	return rect.contains(rectangle);
// }



}
