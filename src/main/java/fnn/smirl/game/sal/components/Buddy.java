package fnn.smirl.game.sal.components;
import android.graphics.*;
import fnn.smirl.game.sal.GameView;

public class Buddy {
 private Paint p = new Paint();
 private Rect rect;
 private int id;
 private int value;
 private int color;
 public Buddy(Box home, int color) {
	Rect r = home.getRectangle();
	rect =new Rect(r.left, r.top, r.right, r.bottom);
	id = home.getId();
	value = home.value;
	this.color = color;
 }
 
 public void setRect(Box box){
	rect.set(box.getRectangle());
	id = box.getId();
	value = box.value;
 }

 public void moveTo(Box box) {
	rect.offsetTo(box.getLeft(), box.getTop()); 
	id = box.getId();
	value = box.value;
 }

 public int getId() {
	return id;
 }
 public int getValue() {
	return value;
 }

 public void onDraw(Canvas c) {

	p.setColor(color);
	p.setStyle(Paint.Style.FILL);
	float scale = rect.width() < rect.height() ?rect.width(): rect.height();
	scale *= 0.4f;
	c.drawCircle(rect.centerX(), rect.centerY(), scale, p);
	try {
	 Thread.sleep(GameView.SLEEP_TIME);
	}
	catch (InterruptedException e) {}
	
	}

 public Rect getRectangle() {
	return rect;
 }

 public boolean contains(Point p) {
	return rect.contains(p.x, p.y);
 }
 public boolean contains(int x, int y) {
	return rect.contains(x, y);
 }

 public boolean contains(int left, int top, int right, int bottom) {
	return rect.contains(left, top, right, bottom);
 }
 public boolean contains(Rect rectangle) {
	return rect.contains(rectangle);
 }
}
