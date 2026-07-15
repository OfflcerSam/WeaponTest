package offlcersam.weapontest;

import illuminatus.core.graphics.Texture;
import items.Item;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class WeaponTestIcons {

    public static Texture TEXTURE;
    private static int sheetWidth;

    // Limitation warning, if other mods use the same starting base then they might overwrite each other.
    // Until better methods are added, increase this number and it should work still.
    public static final int CUSTOM_ICON_BASE = 1024;

    private WeaponTestIcons() {}

    public static void load() {
        try {
            InputStream in = WeaponTestIcons.class.getResourceAsStream("/items/custom_item_icons.png");
            if (in == null) {
                mods.ModLogger.log("[WeaponTest] custom_item_icons.png not found on classpath.");
                return;
            }
            BufferedImage img = ImageIO.read(in);
            sheetWidth = img.getWidth();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "PNG", baos);
            org.newdawn.slick.opengl.Texture slickTex =
                    TextureLoader.getTexture("PNG", new ByteArrayInputStream(baos.toByteArray()));
            TEXTURE = new Texture(slickTex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isCustomIcon(Item item) {
        return item.getIcon() >= CUSTOM_ICON_BASE;
    }

    public static int localX(Item item) {
        int localIndex = item.getIcon() - CUSTOM_ICON_BASE;
        return localIndex * 32 % sheetWidth;
    }

    public static int localY(Item item) {
        int localIndex = item.getIcon() - CUSTOM_ICON_BASE;
        return localIndex * 32 / sheetWidth * 32;
    }
}