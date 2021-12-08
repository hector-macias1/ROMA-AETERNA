package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.neet.Handlers.Keys;
import com.neet.TileMap.Background;

public class Finale2 extends GameState {
	
	private Background background;
	
	private Font font;
	
	public Finale2(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			font = new Font("Times New Roman", Font.PLAIN, 12);
			background = new Background("/Backgrounds/galos.gif", 0);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		
		// check keys
		handleInput();
		
	}
	
	public void draw(Graphics2D g) {
		
		background.draw(g);
		
		//Fondo
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//Texto
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Una década más tarde, en el 390, Roma fue saqueada por los", 16, 60);
		g.drawString("galos. Por primera vez la ciudad eterna había sido profanada", 16, 80);
		g.drawString("por un pueblo extranjero.", 16, 100);
		g.drawString("Enter para continuar", 112, 220);
		
	}
		
	//Teclas
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) {
			gsm.setState(GameStateManager.FINALE3);
		}
	}
}
