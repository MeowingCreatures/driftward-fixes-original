package ink.astrius.driftward.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import vectorwing.farmersdelight.common.item.MelonJuiceItem;

@Mixin(MelonJuiceItem.class)
public class MelonJuiceMixin {
    // Remove hard-coded instant health effect, it's configured in KubeJS instead.
    @Overwrite
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {}
}
