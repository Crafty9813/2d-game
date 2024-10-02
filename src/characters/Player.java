package characters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
//DDD
import main.GamePanel;
import main.KeyboardIO;

public class Player extends Character{
    
    GamePanel gp;
    KeyboardIO keyIO;

    public Player(GamePanel gp, KeyboardIO keyIO) {
        this.gp = gp;
        this.keyIO = keyIO;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 5;
        direction = "down";
    }
    public void getPlayerImage() {
        
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveUp1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveUp2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveDown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveDown2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveLeft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveLeft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveRight1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/PlayerMoveRight2.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { //gets called 60 times per sec

        //makes sure the player doesn't move unless keys pressed
        if (keyIO.upPressed == true || keyIO.downPressed == true || keyIO.leftPressed == true || keyIO.rightPressed == true) {
            if (keyIO.upPressed) {
                direction = "up";
                y -= speed;
            } else if (keyIO.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyIO.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyIO.rightPressed) {
                direction = "right";
                x += speed;
            }

            spriteCounter++; //every frame adds 1 to the counter
            if (spriteCounter > 4) { //if spriteCounter hits 4 then change img (player img changes every 4 frames)
                if (spriteNum == 1) { 
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
    }
    }
    public void draw(Graphics2D g2) {

        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        switch (direction) {
        case "up":
            if (spriteNum == 1) {
                 image = up1;
            }
            if (spriteNum == 2) {
                image = up2;
            }
            break;
        case "down":
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
            break;
        case "left":
            if (spriteNum == 1) {
            image = left1;
            }
            if (spriteNum == 2) {
                image = left2;
            }
            break;
        case "right":
            if (spriteNum == 1) {
                image = right1;
            }
            if (spriteNum == 2) {
                image = right2;
            }
            break;
        }

        //imageObserver is null bc we dont need it
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
