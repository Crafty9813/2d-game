package main;

import javax.swing.JPanel;

import characters.Player;
import tile.TileIO;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable{
    
    final int originalTileSize = 16; //16x16 tile
    final int scale = 2; //character will look like 48 x 48

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 20;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenCol; //
    final int screenHeight = tileSize * maxScreenRow; //px

    int FPS = 60;

    TileIO tileIO = new TileIO(this);
    KeyboardIO keyIO = new KeyboardIO();
    Thread gameThread;
    Player player = new Player(this, keyIO);
    //woah
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyIO);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //THREAD.SLEEP METHOD
    @Override
   /* public void run() {
        double drawInterval = 1000000000/FPS; //1 billion nano sec = 1 sec
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
         //System.out.println("game loop running");

            update(); //updates info like character pos

            repaint(); //draw screen with updated info

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            
       }
    }
    */

    //DELTA METHOD
    public void run() {
        double drawInterval = 1000000000/FPS; //time per frame drawing
        //since we want 60 fps we divide 1 sec (1000000000 nano sec) by 60 to get
        //how much time needs to pass per frame in order to get 60 frames in 1 sec

        double delta = 0; //time accumulated
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            //find time elapsed and divide it by the drawInterval to get how many frames have passed
            delta += (currentTime - lastTime) / drawInterval;
        
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //if at least 1 update frame happens then update and continue
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        tileIO.draw(g2);
        player.draw(g2);
        g2.dispose(); //saves memory by getting rid of any resources graphics is using
    }
}
