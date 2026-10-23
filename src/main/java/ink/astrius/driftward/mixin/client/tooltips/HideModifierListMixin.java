package ink.astrius.driftward.mixin.client.tooltips;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.dafuqs.spectrum.helpers.TooltipHelper;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

// Takes too much space without obvious advantages.
@Restriction(require = @Condition("spectrum"))
@Mixin(TooltipHelper.class)
public class HideModifierListMixin {
    @WrapOperation(
        method = "buildEffectTooltipWithChance",
        at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 1)
    )
    private static boolean hideModifierList(List<Object> list, Operation<Boolean> original) {
        return true;
    }
}
