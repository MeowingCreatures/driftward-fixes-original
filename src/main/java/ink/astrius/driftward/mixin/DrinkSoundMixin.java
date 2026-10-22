package ink.astrius.driftward.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class DrinkSoundMixin {
    // Multiple mods, like Better End and Farmer's Delight, don't overload `getEatingSound` for
    // drinks, causing the wrong sound to be played when finishing drinking. This fixes the problem
    // in a centralized manner.
    @WrapOperation(
        method = "Lnet/minecraft/world/entity/LivingEntity;eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getEatingSound(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/sounds/SoundEvent;")
    )
    private SoundEvent adjustSound(LivingEntity entity, ItemStack stack, Operation<SoundEvent> original) {
        if (stack.getUseAnimation() == UseAnim.DRINK) {
            return stack.getDrinkingSound();
        }
        return original.call(entity, stack);
    }
}
