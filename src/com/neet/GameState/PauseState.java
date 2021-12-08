package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.Audio.JukeBox;
import com.neet.Entity.PlayerSave;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;

public class PauseState extends GameState {
		
	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Reanudar",
		"Salir"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			//Miniatura
			head = ImageIO.read(
				getClass().getResourceAsStream("/HUD/Hud.gif")
			).getSubimage(0, 12, 12, 11);
			
			//Titulos 
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 28);
			font = new Font("Times New Roman", Font.PLAIN, 14);
			
			//Sonidos
			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
			JukeBox.load("/SFX/menuselect.mp3", "menuselect");
			
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
		
		//Fondo
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//Titulo
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("PAUSA", 115, 90);
		
		//Opciones
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Reanudar", 145, 165);
		g.drawString("Salir", 145, 185);
		
		//Miniatura
		if(currentChoice == 0) g.drawImage(head, 125, 154, null);
		else if(currentChoice == 1) g.drawImage(head, 125, 174, null);
		
	}
	
	//Seleccion
	private void select() {
		if(currentChoice == 0) {
			//Sonido de seleccion
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setPaused(false);
		}
		else if(currentChoice == 1) {
			System.exit(0);
		}
	}
	
	//Teclas
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				JukeBox.play("menuoption", 0);
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) {
				JukeBox.play("menuoption", 0);
				currentChoice++;
			}
		}
	}

}
