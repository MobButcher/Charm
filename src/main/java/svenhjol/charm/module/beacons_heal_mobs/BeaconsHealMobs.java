package svenhjol.charm.module.beacons_heal_mobs;

import svenhjol.charm.Charm;
import svenhjol.charm.annotation.CommonModule;
import svenhjol.charm.event.ApplyBeaconEffectsCallback;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import svenhjol.charm.loader.CharmModule;

@CommonModule(mod = Charm.MOD_ID, description = "Passive and friendly mobs will heal themselves within range of a beacon with the regeneration effect.")
public class BeaconsHealMobs extends CharmModule {
    @Override
    public void runWhenEnabled() {
        ApplyBeaconEffectsCallback.EVENT.register(this::handleApplyBeaconEffects);
    }

    private void handleApplyBeaconEffects(Level world, BlockPos pos, int levels, MobEffect primaryEffect, MobEffect secondaryEffect) {
        if (!Charm.LOADER.isEnabled("charm:beacons_heal_mobs"))
            return;

        if (!world.isClientSide) {
            double d0 = levels * 10 + 10;
            AABB bb = (new AABB(pos)).inflate(d0).inflate(0.0D, world.getMaxBuildHeight(), 0.0D);

            if (primaryEffect == MobEffects.REGENERATION || secondaryEffect == MobEffects.REGENERATION) {
                List<AgeableMob> list = world.getEntitiesOfClass(AgeableMob.class, bb);
                list.forEach(mob -> mob.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 4 * 20, 1)));
            }
        }
    }
}
