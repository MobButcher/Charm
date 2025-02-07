package svenhjol.charm.mixin.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import svenhjol.charm.event.EntityEquipCallback;

import java.util.Map;

@Mixin(LivingEntity.class)
public class EntityEquipMixin {
    /**
     * Fires the {@link EntityEquipCallback} event.
     */
    @Inject(
        method = "collectEquipmentChanges",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            shift = At.Shift.BEFORE
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void hookGetEquippedItems(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir,
        Map<EquipmentSlot, ItemStack> map, EquipmentSlot[] var2, int var3, int var4,
        EquipmentSlot equipmentSlot, ItemStack itemStack3, ItemStack itemStack4
    ) {
        EntityEquipCallback.EVENT.invoker().interact((LivingEntity)(Object)this, equipmentSlot, itemStack3, itemStack4);
    }
}
