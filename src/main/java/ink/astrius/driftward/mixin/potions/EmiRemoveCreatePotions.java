package ink.astrius.driftward.mixin.potions;

import com.simibubi.create.compat.jei.CreateJEI;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition("create"))
@Mixin(CreateJEI.class)
public class EmiRemoveCreatePotions {
    @Inject(method = "registerExtraIngredients", at = @At("HEAD"), cancellable = true)
    public void skipRegisteringPotions(IExtraIngredientRegistration registration, CallbackInfo ci) {
        ci.cancel();
    }
}
