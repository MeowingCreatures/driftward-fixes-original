package ink.astrius.driftward.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class EatInstantEffectMixin {
    @WrapOperation(
        method = "addEatEffect",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"
        )
    )
    private boolean addEffect(LivingEntity entity, MobEffectInstance instance, Operation<Boolean> original) {
        // Follow the logic in `PotionItem` to avoid effect icon flickering caused by the effect
        // being active for one tick.
        var effect = instance.getEffect().value();
        if (effect.isInstantenous()) {
            effect.applyInstantenousEffect(entity, entity, entity, instance.getAmplifier(), 1.0);
            return true;
        }
        return original.call(entity, instance);
    }
}
