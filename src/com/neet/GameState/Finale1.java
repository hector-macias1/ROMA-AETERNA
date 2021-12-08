package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import com.neet.Handlers.Keys;
import com.neet.TileMap.Background;

public class Finale1 extends GameState {
	
	private Background background;
	
	private Font font;
	
	public Finale1(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			font = new Font("Times New Roman", Font.PLAIN, 12);
			background = new Background("/Backgrounds/adrianopolis.gif", 0);
			
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
		g.drawString("Con la derrota en la batalla de Adrianópolis en el 378 d.C.", 16, 60);
		g.drawString("el ejército romano empezaba una horda de fracasos contra los", 16, 80);
		g.drawString("numerosos pueblos germánicos del norte. La decadencia militar", 16, 100);
		g.drawString("del Imperio Romano quedó evidenciada.", 16, 120);
		g.drawString("Enter para continuar", 112, 220);
		
	}
		
	//Teclas
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) {
			gsm.setState(GameStateManager.FINALE2);
		}
	}
}
