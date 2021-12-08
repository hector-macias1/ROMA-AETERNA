package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.neet.Handlers.Keys;
import com.neet.TileMap.Background;

public class Finale3 extends GameState {
	
	private Background background;
	
	private Font font;
	
	public Finale3(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			font = new Font("Times New Roman", Font.PLAIN, 12);
			background = new Background("/Backgrounds/romedark.gif", 0);
			
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
		g.drawString("El de septiembre del 476, Rómulo Augústulo, último emperador", 10, 40);
		g.drawString("del Imperio Romano de Occidente, fue obligado a renunciar al", 10, 60);
		g.drawString("trono por Odoacro, rey de los hérulos. Después de esto el ", 10, 80);
		g.drawString("poder de Roma desapareció, siendo reemplazado por numerosos", 10, 100);
		g.drawString("reinos germánicos.", 10, 120);
		g.drawString("Enter para continuar", 112, 220);
		
	}
		
	//Teclas
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
}