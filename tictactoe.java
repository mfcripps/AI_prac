import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class tictactoe extends Applet implements MouseListener, ActionListener, ItemListener{
	BufferedImage board;
	int[] spaces = new int[27];
	int turn; 
	int moveidx;
	boolean p1comp = false;
	boolean p2comp = false;
	boolean userMoved = false;
	private static class point{
		public int x;
		public int y;
	}
	
	public void init(){
		addMouseListener(this);
		this.setLayout(null);
		Button reset = new Button("Reset");
		add(reset);
		reset.setBounds(75, 25, 60, 20);
		Button start = new Button("Start");
		add(start);
		reset.addActionListener(this);
		start.addActionListener(this);
		
		start.setBounds(150, 25, 60, 20);
		CheckboxGroup p1 = new CheckboxGroup();
		Checkbox p1comp = new Checkbox("Computer", p1, true);
		Checkbox p1human = new Checkbox("Human", p1, true);
		
		CheckboxGroup p2 = new CheckboxGroup();
		Checkbox p2comp = new Checkbox("Computer", p2, true);
		Checkbox p2human = new Checkbox("Human", p2, true);
		
		p1comp.addItemListener(this);
		p1human.addItemListener(this);
		p2comp.addItemListener(this);
		p2human.addItemListener(this);
		
		Label p1l = new Label("Player 1 (O)");
		Label p2l = new Label("Player 2 (X)");
		
		add(p1l);
		add(p1comp);
		add(p1human);
		add(p2l);
		add(p2comp);
		add(p2human);
		
		p1l.setBounds(5, 50, 100, 25);
		p1comp.setBounds(25, 75, 100, 40);
		p1human.setBounds(25, 110, 100, 40);
		
		p2l.setBounds(5, 160, 100, 25);
		p2comp.setBounds(25, 185, 100, 40);
		p2human.setBounds(25, 220, 100, 40);
		
		for (int i = 0; i < spaces.length; i++){
			spaces[i] = -1;
		}
		turn = 0;
		moveidx = -1;
		repaint();
		repaint();
	}
	public void paint(Graphics g){
		try {
			board = ImageIO.read(new File("board.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(board, 300, 5, 300, 600, null);
		for (int i = 0; i < spaces.length; i++){
			if(spaces[i] != -1){
				BufferedImage mark;
				try {
					if (spaces[i] == 0){
						mark = ImageIO.read(new File("O.png"));
					}
					else{
						mark = ImageIO.read(new File("x.png"));
					}
					point location = getCenter(i+1);
					g.drawImage(mark, location.x - 25, location.y - 25, 50, 50, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}
	
	public void printArray(int[] array){
		for (int i = 0; i < array.length; i++){
			System.out.println(array[i]);
		}
	}
	public boolean isWin(int[] state, int turn){
		//dice into faces
		//layers
		int [] face = Arrays.copyOfRange(state, 0, 9);
		if (checkFace(face, turn)){
			return true;
		}
		face = Arrays.copyOfRange(state, 9, 18);
		if (checkFace(face, turn)){
			return true;
		}
		face = Arrays.copyOfRange(state, 18, 27);
		if (checkFace(face, turn)){
			return true;
		}
		//column faces
		for (int i = 0; i < 3; i++){
			int k = 0;
			for(int j = i; j < 27; j+=3){
				face[k] = state[j];
				k++;
			}
			if (checkFace(face, turn)){
				return true;
			}
		}
		//get across faces
		for (int i = 0; i < 3; i++){
			int k = 0;
			for (int j = 3*i; j < 27; j+=9){
				for (int q = 0; q < 3; q++){
					face[k] = state[(j+q)];
					//System.out.println(j+q);
					k++;
				}
			}
			if (checkFace(face, turn)){
				return true;
			}
		}
		//get diagonal face
		int[] diag1 = {spaces[0], spaces[4], spaces[8], spaces[9], spaces[13], spaces[17], spaces[18], spaces[22], spaces[26]};
		int[] diag2 = {spaces[2], spaces[4], spaces[6], spaces[11], spaces[13], spaces[15], spaces[20], spaces[22], spaces[24]};
		if (checkFace(diag1 ,turn) || checkFace(diag2, turn)){
			return true;
		} 
		return false;
	}
	public boolean checkFace(int[] state, int turn){
		for (int i = 0; i < state.length; i++){
			if (state[i] == turn){
				int player = turn;
				//Check face for win
				//Across
				if (i%3 == 0 ){
					if (state[i+1] == player && state[i+2] == player){
						return true;
					}
				}
				//Down
				if( i >= 0 && i <= 2){
					if (state[i + 3] == player && state[i+6] == player){
						return true;
					}
				}
				//Diagonal Top Left
				if(i == 0){
					if ((state[i+4] == player) && state[i+8] == player){
						return true;
					}
				}
				//Diagonal Top Right 
				if (i == 2){
					if((state[i+2] == player && state[i+4] == player)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args){
		tictactoe t = new tictactoe();
		t.init();
	}
	
	public int whichSquare(int x, int y){
		if ( (x > 333) && (x < 387) && (y > 11) && (y < 56)){
			return 1;
		}
		else if ( (x > 396) && (x < 499) && (y > 11) && (y < 56)){
			return 2;
		}
		else if ( (x > 500) && (x < 563) && (y > 11) && (y < 56)){
			return 3;
		}
		else if ( (x > 325) && (x < 382) && (y > 56) && (y < 115)){
			return 4;
		}
		else if ( (x > 383) && (x < 503) && (y > 56) && (y < 115)){
			return 5;
		}
		else if ( (x > 504) && (x < 574) && (y > 56) && (y < 115)){
			return 6;
		}
		else if ( (x > 313) && (x < 376) && (y > 115) && (y < 173)){
			return 7;
		}
		else if ( (x > 376) && (x < 508) && (y > 115) && (y < 173)){
			return 8;
		}
		else if ( (x > 508) && (x < 584) && (y > 115) && (y < 173)){
			return 9;
		}
		else if ( (x > 333) && (x < 388) && (y > 224) && (y < 265)){
			return 10;
		}
		else if ( (x > 388) && (x < 497) && (y > 224) && (y < 265)){
			return 11;
		}
		else if ( (x > 497) && (x < 563) && (y > 224) && (y < 265)){
			return 12;
		}
		else if ( (x > 325) && (x < 380) && (y > 265) && (y < 328)){
			return 13;
		}
		else if ( (x > 380) && (x < 502) && (y > 265) && (y < 328)){
			return 14;
		}
		else if ( (x > 502) && (x < 573) && (y > 265) && (y < 328)){
			return 15;
		}
		else if ( (x > 313) && (x < 375) && (y > 330) && (y < 382)){
			return 16;
		}
		else if ( (x > 375) && (x < 507) && (y > 330) && (y < 382)){
			return 17;
		}
		else if ( (x > 507) && (x < 583) && (y > 330) && (y < 382)){
			return 18;
		}
		else if ( (x > 334) && (x < 386) && (y > 439) && (y < 482)){
			return 19;
		}
		else if ( (x > 386) && (x < 495) && (y > 439) && (y < 482)){
			return 20;
		}
		else if ( (x > 495) && (x < 582) && (y > 439) && (y < 482)){
			return 21;
		}
		else if ( (x > 325) && (x < 380) && (y > 482) && (y < 540)){
			return 22;
		}
		else if ( (x > 380) && (x < 499) && (y > 482) && (y < 540)){
			return 23;
		}
		else if ( (x > 499) && (x < 571) && (y > 482) && (y < 540)){
			return 24;
		}
		else if ( (x > 314) && (x < 373) && (y > 540) && (y < 598)){
			return 25;
		}
		else if ( (x > 373) && (x < 506) && (y > 540) && (y < 598)){
			return 26;
		}
		else if ( (x > 506) && (x < 582) && (y > 540) && (y < 598)){
			return 27;
		}
		return -1;
	}
	
	public point getCenter(int square){
		int x = -1;
		int y = -1;
		switch(square){
		case 1 : 
			x = 375;
			y = 30;
			break;
		case 2:
			x = 441;
			y = 31;
			break;
		case 3: 
			x = 528;
			y = 30;
			break;
		case 4:
			x = 351;
			y = 88;
			break;
		case 5:
			x = 440;
			y = 85;
			break;
		case 6:
			x = 535;
			y = 85;
			break;
		case 7:
			x = 341;
			y = 146;
			break;
		case 8:
			x = 441;
			y = 143;
			break;
		case 9:
			x = 547;
			y = 143;
			break;
		case 10:
			x = 359;
			y = 246;
			break;
		case 11:
			x = 441;
			y = 247;
			break;
		case 12:
			x = 521;
			y = 245;
			break;
		case 13:
			x = 350;
			y = 297;
			break;
		case 14:
			x = 441;
			y = 299;
			break;
		case 15:
			x = 535;
			y = 299;
			break;
		case 16:
			x = 341;
			y = 356;
			break;
		case 17:
			x = 435;
			y = 357;
			break;
		case 18:
			x = 547;
			y = 358;
			break;
		case 19:
			x = 358;
			y = 462;
			break;
		case 20:
			x = 438;
			y = 462;
			break;
		case 21:
			x = 524;
			y = 464;
			break;
		case 22:
			x = 353;
			y = 514;
			break;
		case 23:
			x = 442;
			y = 514;
			break;
		case 24: 
			x = 534;
			y = 514;
			break;
		case 25:
			x = 341;
			y = 573;
			break;
		case 26:
			x = 437;
			y = 570;
			break;
		case 27:
			x = 543;
			y = 570;
			break;		
		}
		
		point result = new point();
		result.x = x;
		result.y = y;
		return result;
	}
	public void makeMove(int t){
		spaces[moveidx] = t;
		turn = (turn == 0) ? 1: 0;	
	}
	public void mouseClicked(MouseEvent e) {
		int square = whichSquare(e.getX(), e.getY());
		if (square == -1){
			return;
		}
		if (turn == 0){
			spaces[square-1] = 0;
			turn = 1;
		}
		else if (turn == 1){
			spaces[square-1] = 1;
			turn = 0;
		}
		if (isWin(spaces, 0)){
			JOptionPane.showMessageDialog(null, "Player 1 Wins!");
		}
		if (isWin(spaces, 1)){
			JOptionPane.showMessageDialog(null, "Player 2 Wins!");
		}
		repaint();
		if (turn == 0 && p1comp){
			nextMove(spaces);
			makeMove(0);
			turn = 1;
			
		}
		else if (turn == 1 && p2comp){
			nextMove(spaces);
			makeMove(1);
			turn = 0;
		}
		if (isWin(spaces, 0)){
			JOptionPane.showMessageDialog(null, "Player 1 Wins!");
		}
		if (isWin(spaces, 1)){
			JOptionPane.showMessageDialog(null, "Player 2 Wins!");
		}
		repaint();
		
	}
	
	boolean hasPrinted = false;
	public double DFS(int[] states, int thisTurn, int level, double alpha, double beta){
		if (isWin(states, turn)){
			return 1 - .005*level; // penalize for wins that are further away
		}
		int op = (turn == 0) ? 1 : 0;
		if (isWin(states, op)){
			if (level <= 2){
				System.out.println("Loss at level: " + level);
			}
			return -1 + .005*level; //immediate losses are more important
		}
		int numMoves = 0;
		for (int i = 0; i < states.length; i++){
			if (states[i] == -1){
				numMoves++;
			}
		}
		if (numMoves == 0){
			return 0;
		}
		if (level == 7){
			return 0;
		}
		double [] scores = new double[numMoves];
		int fill;
		if (thisTurn == turn){ //maximizer, fill with minints
			fill = Integer.MIN_VALUE;
		}
		else{
			fill = Integer.MAX_VALUE;
		}
		for (int i = 0; i < scores.length; i++){
			scores[i] = fill;
		}
		int k = 0;
		for(int i = 0; i < states.length; i++){
			if (states[i] == -1){
				states[i] = thisTurn;
				scores[k] = DFS(states, (thisTurn == 0) ? 1: 0, level+1, alpha, beta);
				if (fill == Integer.MIN_VALUE){
					if (scores[k] > alpha){
						alpha = scores[k];
					}
				}
				else{
					if (scores[k] < beta){
						beta = scores[k];
					}
				}
				states[i] = -1;
				if ( alpha >= beta){
					if (level == 0){
						System.out.println("Prune!");
					}
					break;
				}
				k++;
			}
		}
		//printArray(scores);
		//need to identify the index of the best move, maximizer
		if (level == 0){
			int idx = -1;
			double max = Integer.MIN_VALUE;
			for (int i = 0; i < numMoves; i++){
				if(scores[i] > max){
					max = scores[i];
					idx = i;
				}
			}
			//find "idxth" empty hole
			int emptyidx = 0;
			for (int i = 0; i < states.length; i++){
				if(states[i] == -1){
					if (emptyidx == idx){
						moveidx = i;
						break;
					}
					emptyidx++;
				}
			}
		}
		Arrays.sort(scores);
		//return max score if an arbitraty level
		if (thisTurn == turn){
			return scores[numMoves-1];
		}
		//return min score
		else{
			if (level == 3 && scores[0] != -1){
				//System.out.println("Score not -1: " + scores[0]);
			}
			return scores[0];
		}
		
	}
	
	public int nextMove(int[] states){
		DFS(states, turn, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return moveidx;
	}
	
	public void begin(){
		System.out.println(p1comp);
		System.out.println(p2comp);
		if(p2comp && p1comp){
			while(!isWin(spaces, 0) && !isWin(spaces, 1)){
				nextMove(spaces);
				makeMove(turn);
				repaint();
			}
			if (isWin(spaces, 0)){
				JOptionPane.showMessageDialog(null, "Player 1 Wins!");
			}
			if (isWin(spaces, 1)){
				JOptionPane.showMessageDialog(null, "Player 2 Wins!");
			}
		}
		else if (p1comp){
			nextMove(spaces);
			makeMove(turn);
			repaint();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Button source = (Button)e.getSource();
		if (source.getLabel() == "Reset"){
			for (int i = 0; i < spaces.length; i++){
				spaces[i] = -1;
			}
			turn = 0;
			repaint();
		}
		if(source.getLabel() == "Start"){
			begin();
		}
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		Checkbox source = (Checkbox)e.getSource();
		if(source.getY() == 75){
			p1comp = true;
		}
		else if (source.getY() == 110){
			p1comp = false;
		}
		else if (source.getY() == 185){
			p2comp = true;
		}
		else{
			p2comp = false;
		}
		
	}
	
}
