package offlcersam.weapontest;

import game.markets.Market;
import game.markets.MarketDatabase;
import game.markets.MarketItem;
import items.Item;
import illuminatus.core.datastructures.List;
import items.ItemTypeConstantsInterface;
import mods.ModLogger;
import java.lang.reflect.Field;

public final class MarketRegistrar {

    private static boolean registered;

    private MarketRegistrar() { }

    public static void registerMarkets() {
        if (registered) { return; }
        registered = true;

        int updatedMarkets = 0;
        int addedWeapons = 0;
        int[] weapons = WeaponRegistrar.getWeaponDatabaseIDs();

        List<Market> markets = getMarkets();

        if (markets != null) {
            for (int marketIndex = 0; marketIndex < markets.size(); marketIndex++) {
                Market market = markets.getChecked(marketIndex);
                if (market == null) {continue;}

                // Check MarketList for addStationIndices
                if (market.stationMatches(502) || market.stationMatches(512)) {

                    for (int weaponID : weapons) {
                        MarketItem listing = new MarketItem(
                                weaponID,
                                MarketItem.BUY_AND_SELL_ALWAYS
                        );

                        if (listing.item != null) {
                            Item.markAsMarketItem(listing.item);
                        }

                        market.addChecked(listing);
                        addedWeapons++;
                    }

                    MarketDatabase.setMarket(marketIndex, market);
                    updatedMarkets++;
                }
            }
        }
        ModLogger.log(
                "[WeaponTest] Added "
                        + addedWeapons
                        + " custom weapon listings to "
                        + updatedMarkets
                        + " markets"
        );
    }

    @SuppressWarnings("unchecked")
    private static List<Market> getMarkets() {
        try {
            Field field = MarketDatabase.class.getDeclaredField("markets");
            field.setAccessible(true);
            return (List<Market>) field.get(null);
        } catch (ReflectiveOperationException exception) {
            return null;
        }
    }
}
