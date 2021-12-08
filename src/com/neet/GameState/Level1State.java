package com.neet.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.neet.Audio.JukeBox;
import com.neet.Entity.Enemy;
import com.neet.Entity.EnemyProjectile;
import com.neet.Entity.EnergyParticle;
import com.neet.Entity.Explosion;
import com.neet.Entity.HUD;
import com.neet.Entity.Player;
import com.neet.Entity.PlayerSave;
import com.neet.Entity.Teleport;
import com.neet.Entity.Title;
import com.neet.Entity.Enemies.Fuego;
import com.neet.Entity.Enemies.Germano;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

public class Level1State extends GameState {
	
	//Fondo
	private Background sky;
	private Background clouds;
	private Background mountains;
	
	//Entidades
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;
	
	//Instancias
	private HUD hud;
	private BufferedImage hageonText;
	private Title title;
	private Title subtitle;
	private Teleport teleport;
	
	//Eventos
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		//Fondo
		sky = new Background("/Backgrounds/sky.gif", 0);
		clouds = new Background("/Backgrounds/clouds.gif", 0.1);
		mountains = new Background("/Backgrounds/mountains1.gif", 0.2);
		
		//Plataformas
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/ruinstileset.gif");
		tileMap.loadMap("/Maps/level1a.map");
		tileMap.setPosition(140, 0);
		tileMap.setBounds(
			tileMap.getWidth() - 1 * tileMap.getTileSize(),
			tileMap.getHeight() - 2 * tileMap.getTileSize(),
			0, 0
		);
		tileMap.setTween(1);
		
		//Jugador
		player = new Player(tileMap);
		player.setPosition(300, 161);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
		
		//Enemigos y ataques
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		//Particulas
		energyParticles = new ArrayList<EnergyParticle>();
		
		//Inicializacion de jugador
		player.init(enemies, energyParticles);
		
		//Explosiones
		explosions = new ArrayList<Explosion>();
		
		//HUD
		hud = new HUD(player);
		
		//Titulos
		try {
			hageonText = ImageIO.read(
				getClass().getResourceAsStream("/HUD/HageonTemple.gif")
			);
			title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			title.sety(60);
			subtitle = new Title(hageonText.getSubimage(0, 20, 82, 13));
			subtitle.sety(85);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Teletransportacion
		teleport = new Teleport(tileMap);
		teleport.setPosition(3700, 131);
		
		//Inicio de evento
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		//Sonidos
		JukeBox.load("/SFX/teleport.mp3", "teleport");
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");
		
		//Musica
		JukeBox.load("/Music/level1.mp3", "level1");
		JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);
		
	}
	
	//Generar enemigos
	private void populateEnemies() {
		enemies.clear();
		/*Tengu t = new Tengu(tileMap, player, enemies);
		t.setPosition(1300, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1330, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1360, 100);
		enemies.add(t);*/
		Germano gp;
		Fuego g;
		
		gp = new Germano(tileMap, player);
		gp.setPosition(1300, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(1320, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(1340, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(1660, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(1680, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(1700, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(2177, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(2960, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(2980, 100);
		enemies.add(gp);
		gp = new Germano(tileMap, player);
		gp.setPosition(3000, 100);
		enemies.add(gp);
		
		g = new Fuego(tileMap);
		g.setPosition(2600, 100);
		enemies.add(g);
		g = new Fuego(tileMap);
		g.setPosition(3500, 100);
		enemies.add(g);
	}
	
	public void update() {
		
		//verificar teclas
		handleInput();
		
		//Verificar si el nivel necesita ser finalizado
		if(teleport.contains(player)) {
			eventFinish = blockInput = true;
		}
		
		//Verificar si el jugador debe morir
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		//Eventos
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		
		//Mover titulos
		if(title != null) {
			title.update();
			if(title.shouldRemove()) title = null;
		}
		if(subtitle != null) {
			subtitle.update();
			if(subtitle.shouldRemove()) subtitle = null;
		}
		
		//Mover fondo
		clouds.setPosition(tileMap.getx(), tileMap.gety());
		mountains.setPosition(tileMap.getx(), tileMap.gety());
		
		//Actualizar estado del jugador
		player.update();
		
		//Actualizar las plataformas
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.update();
		tileMap.fixBounds();
		
		//Actualizar enemigos 
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}
		
		//Actualizar proyectiles de enemigos
		for(int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if(ep.shouldRemove()) {
				eprojectiles.remove(i);
				i--;
			}
		}
		
		//Actualizar explosiones
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		//Actualizar teletransportacion
		teleport.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		//Fondo
		sky.draw(g);
		clouds.draw(g);
		mountains.draw(g);
		
		//Plataformad
		tileMap.draw(g);
		
		//Enemigos
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		//Proyectiles de enemigos
		for(int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}
		
		//Explosiones
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}
		
		//Jugador
		player.draw(g);
		
		//Teletransportacion
		teleport.draw(g);
		
		//HUD
		hud.draw(g);
		
		//Titulos
		if(title != null) title.draw(g);
		if(subtitle != null) subtitle.draw(g);
		
		//Transiciones
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		player.setDashing(Keys.keyState[Keys.BUTTON2]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTON4)) player.setCharging();
	}


	//Eventos
	
	//Reiniciar nivel
	private void reset() {
		player.reset();
		player.setPosition(300, 161);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		eventStart = true;
		eventStart();
		title = new Title(hageonText.getSubimage(0, 0, 178, 20));
		title.sety(60);
		subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
		subtitle.sety(85);
	}
	
	//Iniciar nivel
	private void eventStart() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 30) title.begin();
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			subtitle.begin();
			tb.clear();
		}
	}
	
	//Muerte
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
				JukeBox.close("level1");
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}
	
	//Finalizar nivel
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			JukeBox.play("teleport");
			player.setTeleporting(true);
			player.stop();
		}
		else if(eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			JukeBox.stop("teleport");
		}
		if(eventCount == 180) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			JukeBox.close("level1");
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		
	}

}
