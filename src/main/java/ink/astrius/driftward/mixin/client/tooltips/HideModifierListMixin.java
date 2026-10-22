package ink.astrius.driftward.mixin.client.tooltips;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.dafuqs.spectrum.helpers.TooltipHelper;
import java.util.List;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

// Takes too much space without obvious advantages.
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
