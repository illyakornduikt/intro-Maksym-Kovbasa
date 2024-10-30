package org.object;

public class OBJ_Boots extends SuperObject {
    public OBJ_Boots() {
        name = "Boots";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
