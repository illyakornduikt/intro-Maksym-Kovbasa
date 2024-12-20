package org.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.main.GamePanel;
import org.main.KeyHandler;
import org.main.UtilityTool;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;

        setDefoultValues();
        getPlayerImage();
    }

    public void setDefoultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        if (!moving) {
            if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                    || keyH.rightPressed == true) {
                if(keyH.upPressed == true) {
                    direction = "up";

                } else if (keyH.downPressed == true) {
                    direction = "down";

                } else if (keyH.leftPressed == true) {
                    direction = "left";
                } else if (keyH.rightPressed == true) {
                    direction = "right";
                }
                moving = true;
                collisionOn = false;
                gp.cChecker.checkTile(this);
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);
            } else {
                standCounter++;
                if (standCounter > 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }
        if (moving) {
            if (!collisionOn) {
                switch (direction) {
                    case "upleft":
                        worldY -= speed - 1;
                        worldX -= speed - 1;
                        break;
                    case "upright":
                        worldY -= speed - 1;
                        worldX += speed - 1;
                        break;
                    case "downleft":
                        worldY += speed - 1;
                        worldX -= speed - 1;
                        break;
                    case "downright":
                        worldY += speed - 1;
                        worldX += speed - 1;
                        break;
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 13) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            pixelCounter += speed;
            if (pixelCounter >= 48) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }


    //ORIGINAL ORIGANAL ORIGINAL ORIGINAL ORIGINAL ORIGINAL ORIGINAL ORIGINAL ORIGINAL
    
    // if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed ==
    // true
    // || keyH.rightPressed == true) {
    // if (keyH.upPressed == true) {
    // direction = "up";

    // } else if (keyH.downPressed == true) {
    // direction = "down";

    // } else if (keyH.leftPressed == true) {
    // direction = "left";
    // } else if (keyH.rightPressed == true) {
    // direction = "right";

    // }

    // collisionOn = false;
    // gp.cChecker.checkTile(this);

    // int objIndex = gp.cChecker.checkObject(this, true);
    // pickUpObject(objIndex);

    // if (!collisionOn) {
    // switch (direction) {
    // case "up":
    // worldY -= speed;
    // break;
    // case "down":
    // worldY += speed;
    // break;
    // case "left":
    // worldX -= speed;
    // break;
    // case "right":
    // worldX += speed;
    // break;
    // }
    // }

    // spriteCounter++;
    // if (spriteCounter > 13) {
    // if (spriteNum == 1) {
    // spriteNum = 2;
    // } else if (spriteNum == 2) {
    // spriteNum = 1;
    // }
    // spriteCounter = 0;
    // }
    // }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;
            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        hasKey--;
                        gp.obj[i] = null;
                        gp.ui.showMessage("You opened the door!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
                default:
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

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
            default:
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
        // g2.setColor(Color.red);
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width,
        // solidArea.height);
    }

}
