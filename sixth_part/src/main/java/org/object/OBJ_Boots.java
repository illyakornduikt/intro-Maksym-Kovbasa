package org.object;

import org.main.GamePanel;

public class OBJ_Boots extends SuperObject {
    GamePanel gp;
    public OBJ_Boots(GamePanel gp) {
        this.gp = gp;
        name = "Boots";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
