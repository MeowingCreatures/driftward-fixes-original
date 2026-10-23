package ink.astrius.driftward.mixin;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import vectorwing.farmersdelight.common.item.MelonJuiceItem;

@Restriction(require = @Condition("farmersdelight"))
@Mixin(MelonJuiceItem.class)
public class MelonJuiceMixin {
    // Remove hard-coded instant health effect, it's configured in KubeJS instead.
    @Overwrite
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }
}
