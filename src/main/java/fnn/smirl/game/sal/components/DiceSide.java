package fnn.smirl.game.sal.components;
import android.graphics.*;
import fnn.smirl.game.sal.GameView;

public class DiceSide
{
 private int width, height;
 private int id;
 private Bitmap bmp;
 private Rect rect, src;
 private int x=0, y=0;

 public DiceSide(Bitmap bmp, int id) {
	this.width = bmp.getWidth() / 6;
	this.height = bmp.getHeight();
	this.bmp = bmp;
	this.id = id;
	rect = new Rect(x, y, (x+width), (y+height));
	int srcX = (id -1)*width;
	int srcY = 0;
	src = new Rect(srcX, srcY, srcX+width, srcY+height);
 }

 public int getId() {
	return id;
 }
 public DiceSide setPosition(int x, int y) {
	this.x = x;
	this.y = y;
	rect.offsetTo(x, y);
	return this;
 }

 public int getWidth(){
	return width;
 }
 public int getHeight(){
	return height;
 }

 public Rect getCoordinate(){
	return rect;
 }

 public void  onDraw(Canvas canvas) {
	canvas.drawBitmap(bmp, src, rect, null);
	try {
	 Thread.sleep(GameView.SLEEP_TIME);
	}
	catch (InterruptedException e) {}
 }
 
 public boolean contains(float x, float y){
	return rect.contains((int)x, (int)y);
 }
}
