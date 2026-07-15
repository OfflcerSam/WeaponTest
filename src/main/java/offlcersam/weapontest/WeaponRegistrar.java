package offlcersam.weapontest;

import illuminatus.core.graphics.Color;
import items.ItemTypeConstantsInterface;
import items.TypeTag;
import items.lists.WeaponList;
import mods.ModLogger;
import offlcersam.weapontest.mixin.WeaponListAccessorMixin;

import java.util.ArrayList;
import java.util.List;

public final class WeaponRegistrar {
    private static boolean registered;

    // Stores the base IDs of every weapon we add
    private static final List<Integer> REGISTERED_WEAPON_IDS = new ArrayList<>();

    private WeaponRegistrar() { }

    // Registers a weapon ID and remembers it for later use.
    private static int registerWeaponID(int id) {
        REGISTERED_WEAPON_IDS.add(id);
        ModLogger.log("[WeaponTest] Added weapon ID to registry: " + id);
        return id;
    }

    // Returns database ID for all weapons.
    public static int[] getWeaponDatabaseIDs() {
        int[] ids = new int[REGISTERED_WEAPON_IDS.size()];
        for (int i = 0; i < REGISTERED_WEAPON_IDS.size(); i++) {
            ids[i] = ItemTypeConstantsInterface.WEAPON * 10000 + REGISTERED_WEAPON_IDS.get(i);
        }
        return ids;
    }


    public static void registerWeapons() {
        if (registered) { return; }
        registered = true;

        /*
        // The following is pulled from game.WeaponFX

        | Weapon Name            | weaponType |
        | ---------------------- | ---------:|
        | EMP                    |        1 |
        | Laser                  |        2 |
        | Disruptor              |        3 |
        | Plasma                 |        4 |
        | Railgun                |        5 |
        | Missile Launcher       |        6 |
        | Shockwave              |        7 |
        | Mining Beam            |        8 |
        | Tether                 |        9 |
        | Healer                 |       10 |
        | Salvager               |       11 |
        | Point Defense          |       12 |
         */

        // SetBaseAttributes should be before each group of weapons you want to be set to that attribute.
        // For this example weaponIDs are set a bit above whatever the highest ID was in WeaponList.

        // Railgun (copied from WeaponList)
        WeaponListAccessorMixin.invokeSetBaseAttributes(
                5,   // Int: weaponType
                3.0f,           // Float: Cargo Volume
                0L,             // Long: Credit value, unsure what this does.
                7.5f,           // Float: Damage
                350,            // Int: Range
                2.0f            // Float: Energy usage ratio?
        );

        WeaponList.write(
                registerWeaponID(700),                  // Int: ID
                WeaponTestIcons.CUSTOM_ICON_BASE + 0,   // Int: Icon, sets Icon according to sprite sheet. If using custom do magic, if using vanilla use integer and see WeaponList.
                Color.WHITE,                            // Color: Unsure of what this sets at the moment.
                "Railgun of Tier 0 variety",            // String: Name
                "Electromagnetic accelerator weapon.",  // String: Description
                0,                                      // Int: Tier, affects level usage.
                TypeTag.COMMON,                         // TypeTag, rarity.
                0,                                      // Int: effectType (graphics)
                0.75F,                                  // Float: Accuracy as a percentage
                3.5F,                                   // Float: Reload time, don't know math behind this yet.
                -1.0F                                   // Float: Bonus Coef, unknown usage all weapons seem to be set to -1.0F(?)
        );

        // Plasma Cannons (copied from WeaponList)
        WeaponListAccessorMixin.invokeSetBaseAttributes(4, 5.0F, 1L, 5.5F, 220, 2.5F);
        WeaponList.write(
                registerWeaponID(701),
                WeaponTestIcons.CUSTOM_ICON_BASE + 1,
                Color.WHITE,
                "Plasma Cannon of Tier 0 variety",
                "A functional plasma weapon.",
                0,
                TypeTag.COMMON,
                0,
                0.7F,
                4.5F,
                -1.0F
        );



        // Lasers (copied from WeaponList)
        WeaponListAccessorMixin.invokeSetBaseAttributes(2, 2.5F, 0L, 5.0F, 325, 3.5F);
        WeaponList.write(
                registerWeaponID(702),
                WeaponTestIcons.CUSTOM_ICON_BASE + 0,
                Color.WHITE,
                "Laser of the Tier 0 variety",
                "A functional laser weapon.",
                0,
                TypeTag.COMMON,
                0,
                0.76F,
                3.5F,
                -1.0F
        );


        // Mining Beams (copied from WeaponList)
        WeaponListAccessorMixin.invokeSetBaseAttributes(8, 2.5F, 0L, 1.0F, 225, 3.5F);
        WeaponList.write(
                registerWeaponID(703),
                WeaponTestIcons.CUSTOM_ICON_BASE + 0,
                Color.WHITE,
                "Mining B of Tier 0 variety",
                "A functional mining beam.",
                0, TypeTag.COMMON,
                23,
                0.87F,
                3.4F,
                0.0F
        );


        // Disruptors (copied from WeaponList)
        WeaponListAccessorMixin.invokeSetBaseAttributes(3, 4.0F, 0L, 5.5F, 250, 3.1F);
        WeaponList.write(
                registerWeaponID(704),
                608,
                Color.WHITE,
                "Radio Waver of Tier 0 variety",
                "A directed energy weapon.",
                0,
                TypeTag.COMMON,
                0,
                0.7F,
                3.5F,
                -1.0F
        );


        // Launchers (copied from WeaponList)
        long startPrice = 25650L;
        WeaponListAccessorMixin.invokeSetBaseAttributes(6, 5.7F, startPrice, 4.0F, 325, 1.5F);

        WeaponList.write(
                registerWeaponID(705),
                672,
                Color.WHITE,
                "Launcher of Tier 0 variety",
                "Basic missile launcher.",
                0, TypeTag.COMMON,
                801,
                0.7F,
                5.25F,
                1.0F
        );



        // Fighter Bay (copied from WeaponList)
        long statPrc = 18250L;
        float rangeMod = 1.15F;
        WeaponListAccessorMixin.invokeSetBaseAttributes(6, 13.0F, statPrc, 1.7F, (int)(330.0F * rangeMod), 1.5F);

        WeaponList.writeBay(
                registerWeaponID(706),
                275,
                Color.WHITE,
                "Tube Fighters of Tier 0 variety",
                "Your basic fighter launching tube.",
                1, TypeTag.COMMON,
                1001,
                0.64F,
                5.2F,
                -1.0F
        );

        // From WeaponList, unsure if this entirely matters here.
        //
        //    public static boolean isTether(int itemBaseID) {
        //        return itemBaseID >= 9000 && itemBaseID <= 9999;
        //    }
        //
        //    public static boolean isSalvager(int itemBaseID) {
        //        return itemBaseID >= 8000 && itemBaseID <= 8999;
        //    }

        // Salvager (copied from WeaponList)
        WeaponList.writeSalvager(
                registerWeaponID(8300),
                1012,
                Color.YELLOW,
                "Salvager of the Tier 0 variety",
                "Pluck the parts right out from under that scrap pile.",
                1,
                TypeTag.COMMON,
                10.0F,
                62500L,
                300.0F,
                0,
                1,
                0.2F,
                10.0F
        );

        // Tethers (copied from WeaponList)
        WeaponList.writeTether(
                registerWeaponID(9300),
                507,
                Color.WHITE,
                "Grappler of the Tier 0 variety",
                "Slow them down a bit.",
                1,
                TypeTag.COMMON,
                10.0F,
                62500L,
                650.0F,
                0.55F,
                0.0F,
                0.0F,
                0.0F,
                0.0F,
                15.0F
        );




        ModLogger.log(
                "[WeaponTest] Registered " + REGISTERED_WEAPON_IDS.size() + " weapons"
        );
    }
}
