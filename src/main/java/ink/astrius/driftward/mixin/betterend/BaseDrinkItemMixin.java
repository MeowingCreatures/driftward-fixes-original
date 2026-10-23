package ink.astrius.driftward.mixin.betterend;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.betterx.bclib.items.BaseDrinkItem;
import org.betterx.bclib.items.ModelProviderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Restriction(require = @Condition("betterend"))
@Mixin(BaseDrinkItem.class)
public class BaseDrinkItemMixin extends ModelProviderItem {
    public BaseDrinkItemMixin() {
        super(null);
    }

    // Align remainder behavior with vanilla and stop unconditionally clearing effects. Upstream
    // uses very questionable code here, this approach is lifted from HoneyBottleItem.
    @Overwrite
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        super.finishUsingItem(stack, level, user);
        if (user instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof Player player && !player.hasInfiniteMaterials()) {
            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
            if (!player.getInventory().add(bottle)) {
                player.drop(bottle, false);
            }
        }
        return stack;
    }
}
