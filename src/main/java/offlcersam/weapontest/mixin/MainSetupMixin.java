package offlcersam.weapontest.mixin;

import offlcersam.weapontest.MarketRegistrar;
import offlcersam.weapontest.WeaponRegistrar;
import game.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Main.class, remap = false)
public class MainSetupMixin {

    @Inject(
            method = "setup",
            at = @At(
                    value = "INVOKE",
                    target = "L_database/ItemDatabase;loadDatabase()V",
                    shift = At.Shift.AFTER
            )
    )
    private void weapontest$registerShips(CallbackInfo ci) {
        WeaponRegistrar.registerWeapons();
    }

    @Inject(
            method = "setup",
            at = @At(
                    value = "INVOKE",
                    target = "Lgame/markets/MarketDatabase;loadDatabase()V",
                    shift = At.Shift.AFTER
            )
    )
    private void weapontest$registerMarkets(CallbackInfo ci) {
        MarketRegistrar.registerMarkets();
    }
}
