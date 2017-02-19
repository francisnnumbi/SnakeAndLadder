package fnn.smirl.game.sal;
import android.graphics.*;
import android.view.*;
import android.widget.*;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import fnn.smirl.game.sal.components.Box;
import fnn.smirl.game.sal.components.Buddy;
import java.util.ArrayList;
import java.util.Random;
import android.os.Handler;
import fnn.smirl.game.sal.components.DiceSide;
import java.util.Collections;
import android.media.MediaPlayer;
import fnn.smirl.game.sal.constant.Sound;

public class GameView extends View {
 private static int NUMBER_GRID_HORIZONTAL = 10, 
 NUMBER_GRID_VERTICAL = 10;
 public static Context ctx;
 public static Resources res;



 private int gX = 0, gY = 0;
 private float scale;
 private int shuffle = 0;
 private boolean partyOn = true;
 private String info = "";
 private int decayTime = 0;
 private int tally=0;

 private static int gWidth, gHeight, sWidth, sHeight;
 private static Bitmap board, diceBmp;
 private boolean myTurn = true;
 private int moveSteps = 0, moveComputerSteps = 0;
 private Buddy buddy;
 private Buddy computer;
 private String winner = "";
 private ArrayList<Box> boxArray =
 new ArrayList<Box>();
 private ArrayList<DiceSide> dice =
 new ArrayList<DiceSide>();
 private Point touchCoordinate = new Point();
 Random random = new Random();
 public static final long SLEEP_TIME = 100;
 private Paint paint;

 public GameView(Context ctx) {
	super(ctx);
	this.ctx = ctx;
	res = getResources();
	scale = res.getDisplayMetrics().density;
	board = BitmapFactory.decodeResource(res, R.drawable.board);
	diceBmp = BitmapFactory.decodeResource(res, R.drawable.dice_thumb1);
 }

 private void setBoardGrid() {
	int boxWidth = gWidth / NUMBER_GRID_HORIZONTAL;
	int boxHeight = gHeight / NUMBER_GRID_VERTICAL;

	for (int i =0; i < NUMBER_GRID_VERTICAL;i++) {
	 for (int j = 0; j < NUMBER_GRID_HORIZONTAL; j++) {
		int id = ((9 - i) * 10) + (j + 1);
		int left = j * boxWidth;
		int top = (i * boxHeight) + gY;
		Box b = new Box(id, left , top, left + boxWidth, top + boxHeight);
		b.value = getBoxValueAsPer(id);
		boxArray.add(b);
		if (id == 1) {
		 computer = new Buddy(b, Color.RED);
		 buddy = new Buddy(b, Color.BLUE); 
		}
	 }
	}
	for (int j = 0; j < boxArray.size(); j++) {
	 if (boxArray.get(j).getId() == MainActivity.buddyBoxId) {
		buddy.moveTo(boxArray.get(j));
	 }
	 if (boxArray.get(j).getId() == MainActivity.computerBoxId) {
		computer.moveTo(boxArray.get(j));
	 }
	}

 }

 private void setDice() {
	for (int i = 1; i < 7; i++) {
	 dice.add(new DiceSide(diceBmp, i));
	}
 }

 private int getBoxValueAsPer(int id) {
	switch (id) {
	 case 3:
		return 51;
	 case 6:
		return 27;
	 case 20:
		return 70;
	 case 25:
		return 5;
	 case 34:
		return 1;
	 case 36:
		return 55;
	 case 47:
		return 19;
	 case 63:
		return 95;
	 case 65:
		return 52;
	 case 68:
		return 98;
	 case 87:
		return 57;
	 case 91:
		return 61;
	 case 99:
		return 69;
	 default:
		return id;
	}
 }

 @Override
 protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// TODO: Implement this method
	super.onSizeChanged(w, h, oldw, oldh);
	sWidth = w; sHeight = h;
	gWidth = w; gHeight = sHeight * 90 / 100;
	gY = (sHeight - gHeight) / 2;
	board = Bitmap.createScaledBitmap(board, gWidth, gHeight, false);
	diceBmp = Bitmap.createScaledBitmap(diceBmp, (int)(diceBmp.getWidth() * 0.5), (int)(diceBmp.getHeight() * 0.5), false);
	setBoardGrid();
	setDice();
	preparePaint();
 }
 private void preparePaint() {
	paint = new Paint();
	paint.setColor(Color.WHITE);
	paint.setStyle(Paint.Style.FILL);
	paint.setFakeBoldText(true);
	paint.setTextSize(scale * 15);
	paint.setAntiAlias(true);
 }
 @Override
 protected void onDraw(Canvas canvas) {
	// TODO: Implement this method
	super.onDraw(canvas);
	canvas.drawColor(Color.BLACK);
	canvas.drawBitmap(board, gX, gY, null);
	paint.setColor(Color.WHITE);
	paint.setTextSize(scale * 15);
	String vousSc = "Vous: " + MainActivity.userScore;
	canvas.drawText(vousSc, 10, paint.getTextSize() + 5, paint);
	String ordSc = "Ordinateur: " + MainActivity.computerScore;
	canvas.drawText(ordSc, gWidth - paint.measureText(ordSc) - 10, paint.getTextSize() + 5, paint);

	int statPct = MainActivity.totTurn < 1 ?0: (MainActivity.stat * 100) / MainActivity.totTurn;
	String statistics = "statistics: " + MainActivity.stat + "/" + MainActivity.totTurn + " où (" + statPct + "%)";
	canvas.drawText(statistics, 10, sHeight - paint.getTextSize(), paint);

	String maximum = "Max: " + MainActivity.maxEnd;
	canvas.drawText(maximum, gWidth - paint.measureText(maximum) - 10, sHeight - paint.getTextSize(), paint);

	buddy.onDraw(canvas);
	moveBuddy();
	computer.onDraw(canvas);
	moveComputer();
	drawDice(canvas);
	displayInfo(canvas);
	invalidate();
 }

 @Override
 public boolean onTouchEvent(MotionEvent event) {
	// TODO: Implement this method
	int evt = event.getAction();
	touchCoordinate.set((int)event.getX(), (int)event.getY());
	switch (evt) {
	 case MotionEvent.ACTION_DOWN:
		for (int i = 0; i < boxArray.size(); i++) {
		 if (boxArray.get(i).contains(touchCoordinate)) {
//			Toast.makeText(ctx, "box:" + boxArray.get(i).getId()
//										 + ", value:" + boxArray.get(i).value, Toast.LENGTH_SHORT)
//			 .show();
		 }
		}
		break;
	 case MotionEvent.ACTION_MOVE:

		break;
	 case MotionEvent.ACTION_UP:
		if (myTurn) {
		 turnDice();

		}
		break;
	}
	return true;
 }

 private void moveBuddy() {
	if (buddy.getId() + moveSteps > 100) {
	 dispatchInfo("VOUS: au delà!");
	 moveSteps = 0;
	 myTurn = false;
	 try {
		Thread.sleep(SLEEP_TIME);
	 }
	 catch (InterruptedException e) {} 
	 computerTurn();
	} else {
	 if (moveSteps > 0) {
		for (int i = 0; i < boxArray.size(); i++) {
		 if (boxArray.get(i).getId() - buddy.getId() == 1) {
			MainActivity.playSound(Sound.JUMP_2, false);
			buddy.moveTo(boxArray.get(i));
			if (buddy.getId() == 100) {
			 winner = "Vous";
			 partyEnd();
			}
			moveSteps--;
			//MainActivity.stopSound();
			break;
		 }
		}

		if (moveSteps < 1) {
		 MainActivity.stopSound();
		 promoteOrDemote();

		}
	 }
	}
 }

 private void promoteOrDemote() {
	for (int j = 0; j < boxArray.size(); j++) {
	 if (boxArray.get(j).getId() == buddy.getValue()) {
		if (buddy.getId() < boxArray.get(j).getId()) {
		 MainActivity.playSound(Sound.SHOOTING_STAR_1, false);
		} else/* if (buddy.getId() > boxArray.get(j).getId())*/ {
		 MainActivity.playSound(Sound.HEAD_CHOP_DOWN_1, false);
		}
		buddy.moveTo(boxArray.get(j));
		MainActivity.buddyBoxId = boxArray.get(j).getId();
		MainActivity.saveConfig();
		MainActivity.stopSound();
		break;
	 }
	}
	moveSteps = 0;
	myTurn = false;
	try {
	 Thread.sleep(SLEEP_TIME);
	}
	catch (InterruptedException e) {} 
	if (partyOn) {
	 computerTurn();
	}
 }

 private void partyEnd() {
	MainActivity.playSound(Sound.END_GAME_1, false);
	partyOn = false;
	final Dialog endPartyDialog = new Dialog(ctx);
	endPartyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	endPartyDialog.setContentView(R.layout.party_end_dialog);
	updateScore();
	TextView endPartyText = (TextView)
	 endPartyDialog.findViewById(R.id.partyenddialogTextView1);
	String d = "Vous avez";
	if (winner.equalsIgnoreCase("Ordinateur")) {
	 d = "Ordinateur a";
	}
	endPartyText.setText(d + " atteint le but gagnant " + tally + " points. Voulez-vous encore jouer?");
	Button nextPartyButton = (Button)
	 endPartyDialog.findViewById(R.id.partyenddialogButton1);

	nextPartyButton.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // play sound
		 initNewParty();
		 endPartyDialog.dismiss();
		}
	 });
	endPartyDialog.show();
 }

 private void initNewParty() {
	MainActivity.buddyBoxId = 1;
	MainActivity.computerBoxId = 1;
	MainActivity.saveConfig();
	setBoardGrid();
	moveSteps = 0;
	moveComputerSteps = 0;
	partyOn = true;
	myTurn = true;

 }

 private void computerTurn() {
	//moveComputerSteps = 1 + random.nextInt(6);
	turnDice();
 }

 private void moveComputer() {
	if (computer.getId() + moveComputerSteps > 100) {
	 dispatchInfo("ORDINATEUR: au delà!");
	 moveComputerSteps = 0;
	 try {
		Thread.sleep(SLEEP_TIME);
	 }
	 catch (InterruptedException e) {} 
	 myTurn = true;
	} else {
	 if (moveComputerSteps > 0) {
		for (int i = 0; i < boxArray.size(); i++) {
		 if (boxArray.get(i).getId() - computer.getId() == 1) {
			MainActivity.playSound(Sound.JUMP_2, false);
			computer.moveTo(boxArray.get(i));
			if (computer.getId() == 100) {
			 winner = "Ordinateur";
			 partyEnd();
			}
			moveComputerSteps--;
			//MainActivity.stopSound();
			break;
		 }
		}

		if (moveComputerSteps < 1) {
		 MainActivity.stopSound();
		 promoteOrDemoteComputer();

		}
	 }
	}
 }

 private void promoteOrDemoteComputer() {
	for (int j = 0; j < boxArray.size(); j++) {
	 if (boxArray.get(j).getId() == computer.getValue()) {
		if (computer.getId() < boxArray.get(j).getId()) {
		 MainActivity.playSound(Sound.SHOOTING_STAR_1, false);
		} else/* if (computer.getId() > boxArray.get(j).getId())*/ {
		 MainActivity.playSound(Sound.HEAD_CHOP_DOWN_1, false);
		}
		computer.moveTo(boxArray.get(j));
		MainActivity.computerBoxId = boxArray.get(j).getId();
		MainActivity.saveConfig();
		MainActivity.stopSound();
		break;
	 }
	}
	moveComputerSteps = 0;
	try {
	 Thread.sleep(SLEEP_TIME);
	}
	catch (InterruptedException e) {} 
	myTurn = true;
 }

 private void updateScore() {
	if (winner.equalsIgnoreCase("vous")) {
	 tally = 100 - computer.getId();
	 MainActivity.userScore += tally;
	 if (MainActivity.userScore >= MainActivity.maxEnd) {
		MainActivity.	stat++;
		MainActivity.totTurn++;
		MainActivity.userScore = 0;
		MainActivity.computerScore = 0;

	 }
	}
	if (winner.equalsIgnoreCase("ordinateur")) {
	 tally = 100 - buddy.getId();
	 MainActivity.computerScore += tally;
	 if (MainActivity.computerScore >= MainActivity.maxEnd) {

		MainActivity.totTurn++;
		MainActivity.userScore = 0;
		MainActivity.computerScore = 0;
	 }
	}
	MainActivity.saveConfig();
 }

 private void turnDice() {
	new Handler().postDelayed(
	 new Runnable(){

		@Override
		public void run() {

		 MainActivity.playSound(Sound.DICE_1, true);
		 shuffle = 6 + random.nextInt(6); 
		}
	 }, 1000);

 }

 private void drawDice(Canvas c) {
	Collections.shuffle(dice);
	if (shuffle > 0) {
	 dice.get(0).setPosition((gWidth - dice.get(0).getWidth()) / 2,
													 (gHeight - dice.get(0).getHeight()) / 2)
		.onDraw(c);

	 shuffle--;
	 if (shuffle < 1) {
		MainActivity. stopSound();
		if (myTurn)moveSteps = dice.get(0).getId();
		else moveComputerSteps = dice.get(0).getId();
		String _s = dice.get(0).getId() > 1 ?" cases.": " case.";
		dispatchInfo("Avancer " + dice.get(0).getId() + _s);

	 }
	}
 }

 private void dispatchInfo(String i) {
	info = i; decayTime = 5;
 }
 private void displayInfo(Canvas c) {
	paint.setColor(Color.BLACK);
	paint.setTextSize(scale * 30);
	c.drawText(info, (gWidth - paint.measureText(info)) / 2, (int)(gHeight * 0.65), paint);
	if (decayTime > 0) {
	 decayTime--;
	 if (decayTime < 1) {
		info = "";
	 }
	}
 }


}
