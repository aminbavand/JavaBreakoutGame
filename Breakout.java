/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/** Delay of ball movement */
private static final int DELAY = 10;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setup();
		play();
	}
	
	private void setup() {
//		setSize(WIDTH+14,HEIGHT+57);
		setSize(WIDTH,HEIGHT);	
		setupBricks();
		setupPaddle();
	}
	
	private void play() {
		createBall();
		while(!gameOver()) {
			moveBall();
			GObject collider = getCollidingObject();
			if (collider!=null) {				
				if (collider==paddle) vy=-vy;
				else {
					remove(collider);
					bricks_left-=1;
					vy = -vy;
				}				
			}		
			
			pause(DELAY);
		}
		
	}
	
	
	private void setupBricks(){
		for (int row=1; row<=NBRICK_ROWS; row++) {
			
			int brick_y_loc = BRICK_Y_OFFSET + (row-1)*(BRICK_HEIGHT+BRICK_SEP);
			
			for (int Nbrick=1; Nbrick<=NBRICKS_PER_ROW; Nbrick++) {
				
				int brick_x_loc = 1+(Nbrick-1)* (BRICK_WIDTH+BRICK_SEP);
				GRect brick = new GRect(BRICK_WIDTH,BRICK_HEIGHT);
				brick.setFilled(true);
				if (row==1 || row==2) {
					brick.setColor(Color.RED);
				}
				if (row==3 || row==4) {
					brick.setColor(Color.ORANGE);
				}
				if (row==5 || row==6) {
					brick.setColor(Color.YELLOW);
				}
				if (row==7 || row==8) {
					brick.setColor(Color.GREEN);
				}
				if (row==9 || row==10) {
					brick.setColor(Color.CYAN);
				}
				add(brick,brick_x_loc,brick_y_loc);
			}
		}
		
	}

	private void setupPaddle() {
		
		paddle = new GRect(PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle, (WIDTH-PADDLE_WIDTH)/2,HEIGHT-PADDLE_Y_OFFSET);
		addMouseListeners();
	}
	
	public void mouseMoved(MouseEvent e) {		
		paddle.move(e.getX()-paddle.getX(),0);
	}

	private void createBall() {		
		ball = new GOval(BALL_RADIUS,BALL_RADIUS);
		ball.setFilled(true);
		add(ball,(WIDTH-2*BALL_RADIUS)/2, (HEIGHT-2*BALL_RADIUS)/2);
		
		vy = 3.0;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
	}
	
	private void moveBall() {

		ball.move(vx, vy);
		if (ball.getX() >= getWidth() -2*BALL_RADIUS || ball.getX() <= 0) vx = -vx;
		if (ball.getY() >= getHeight() +2*BALL_RADIUS || ball.getY() <= 0) vy = -vy;
		if (ball.getY() >= getHeight() +2*BALL_RADIUS) hit_bottom = true;
	}
	
	private GObject getCollidingObject() {
		GObject collObj = getElementAt(ball.getX(), ball.getY());
		if (collObj!=null) return collObj;
		else {
			
			collObj = getElementAt(ball.getX(), ball.getY()+2*BALL_RADIUS);
			if (collObj!=null) return collObj;
			else {
				
				collObj = getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY());
				if (collObj!=null) return collObj;
				else {
					
					collObj = getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY()+2*BALL_RADIUS);
					if (collObj!=null) return collObj;
					else {
						
						return null;
					}
				}
			}
		}
		
	}
	
	private boolean gameOver() {
		return (bricks_left==0 || hit_bottom==true);
	}
	
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GRect paddle;
	private GOval ball;
	private double vx, vy;
	private int bricks_left = NBRICKS_PER_ROW*NBRICK_ROWS;
	private boolean hit_bottom = false;
}
