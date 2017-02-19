package fnn.smirl.game.sal.components;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.Point;

public class Box
{
 private Rect rect;
 public int value = 0;
 private  int id;
 
 public Box(int id, int left, int top,int right, int bottom){
	rect = new Rect(left,top, right, bottom);
	this.id = id;
 }
 
 public int getId(){
	return id;
 }
 
 public Rect getRectangle(){
	return rect;
 }
 
 public int getLeft(){
	return rect.left;
 }
 
 public int getTop(){
	return rect.top;
 }
 
 public boolean contains(Point p){
	return rect.contains(p.x, p.y);
 }
 public boolean contains(int x, int y){
	return rect.contains(x, y);
 }
 
 public boolean contains(int left, int top, int right, int bottom){
	return rect.contains(left, top, right, bottom);
 }
 public boolean contains(Rect rectangle){
	return rect.contains(rectangle);
 }
 
}
