package offlcersam.weapontest;

import game.Player;
import mods.ModLogger;

import static offlcersam.weapontest.MarketRegistrar.*;

public final class DebugItemGrant {
    // Set to true to automatically deposit the ship when loading your character
    // Maybe make into config option if a config manager is made.
    private static final boolean ENABLE_DEBUG_GRANT = true;
    private static final String DEBUG_CHARACTER_NAME = "STEST";

    private DebugItemGrant() { }

    public static void grantWeaponsToDebugCharacter() {
        if (!ENABLE_DEBUG_GRANT) {
            return;
        }
        if (!DEBUG_CHARACTER_NAME.equalsIgnoreCase(Player.currentName)) {
            return;
        }
        if (Player.ship == null || Player.ship.cargo == null) {
            ModLogger.log("[ShipTest] Could not grant Ships: player cargo is not loaded.");
            return;
        }
        /*
        // Doesn't actually work but whatever
        if (Player.ship.cargo.exists(targetItemId)) {
            ModLogger.log("[ShipTest] STEST already has Arrowhead in cargo.");
            return;
        }
        */
        int[] weapons = WeaponRegistrar.getWeaponDatabaseIDs();
        for (int weaponID : weapons) {Player.ship.cargo.add(weaponID, 1);}
        ModLogger.log(
                "[WeaponTest] Granted "
                        + weapons.length
                        + " weapons to STEST cargo hold successfully."
        );
    }
}
