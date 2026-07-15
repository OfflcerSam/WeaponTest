package offlcersam.weapontest;

import game.graphics.GraphicsLoader;
import illuminatus.core.graphics.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.Graphics2D;
import java.io.InputStream;

// This class is now unused, it used to just stitch the new spritesheet but this would cause incompat due to mod's sprite sheets overriding eachother and stuff
@Deprecated
public final class CustomIconStitcher {

    public static final int CUSTOM_ICON_BASE = 1024; // first free index past vanilla's 0-1023

    public static void extendItemIcons() {
        try {
            InputStream vanillaStream = GraphicsLoader.class.getResourceAsStream("/items/items.png");
            InputStream customStream = CustomIconStitcher.class.getResourceAsStream("/items/custom_item_icons.png");

            if (vanillaStream == null || customStream == null) {
                mods.ModLogger.log("[WeaponTest] Could not find icon resource(s) - vanilla="
                        + (vanillaStream != null) + " custom=" + (customStream != null));
                return;
            }

            BufferedImage vanilla = ImageIO.read(vanillaStream);
            BufferedImage custom = ImageIO.read(customStream);


            int width = vanilla.getWidth(); // keep at 1024 to preserve the math
            int height = vanilla.getHeight() + custom.getHeight();

            BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();
            g.drawImage(vanilla, 0, 0, null);
            g.drawImage(custom, 0, vanilla.getHeight(), null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(combined, "PNG", baos);

            org.newdawn.slick.opengl.Texture slickTex =
                    TextureLoader.getTexture("PNG", new ByteArrayInputStream(baos.toByteArray()));

            GraphicsLoader.ITEM_ICONS = new Texture(slickTex);
            GraphicsLoader.ITEM_ICONS_SOURCE_WIDTH = GraphicsLoader.ITEM_ICONS.width;

            mods.ModLogger.log("[WeaponTest] Extended item icon sheet loaded.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
