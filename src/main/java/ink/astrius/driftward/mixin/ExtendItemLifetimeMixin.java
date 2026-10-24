package ink.astrius.driftward.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class ExtendItemLifetimeMixin {
    @Shadow
    public abstract void setExtendedLifetime();

    @Inject(
        method = "Lnet/minecraft/world/entity/item/ItemEntity;<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V",
        at = @At("TAIL")
    )
    public void constructor(EntityType type, Level level, CallbackInfo ci) {
        this.setExtendedLifetime();
    }
}
