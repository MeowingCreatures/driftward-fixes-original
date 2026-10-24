package ink.astrius.driftward.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.spectrum.blocks.conditional.QuitoxicReedsBlock;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import io.github.jamalam360.rightclickharvest.RightClickHarvest;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Restriction(
    require = {
        @Condition("rightclickharvest"),
        @Condition("spectrum")
    }
)
@Mixin(RightClickHarvest.class)
public class RightClickHarvestMixin {
    @Shadow
    private static InteractionResult completeHarvest(Level level, BlockState state, BlockPos pos, Player player, InteractionHand hand, ItemStack stackInHand, boolean hoeInUse, boolean removeReplant, Runnable setBlockAction) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(
        method = "onBlockUse",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/InteractionResult;PASS:Lnet/minecraft/world/InteractionResult;",
            ordinal = 8,
            opcode = Opcodes.GETSTATIC
        ),
        cancellable = true
    )
    private static void handleQuitoxicReeds(
        Player player,
        Level level,
        InteractionHand hand,
        BlockHitResult hitResult,
        boolean initialCall,
        CallbackInfoReturnable<InteractionResult> cir,
        @Local(name = "state") BlockState state,
        @Local(name = "stackInHand") ItemStack stackInHand,
        @Local(name = "hoeInUse") boolean hoeInUse
    ) {
        if (!(state.getBlock() instanceof QuitoxicReedsBlock)) {
            return;
        }
        if (hitResult.getDirection() == Direction.UP && stackInHand.getItem() == SpectrumBlocks.QUITOXIC_REEDS.asItem()) {
            return;
        }

        final var lookingFor = SpectrumBlocks.QUITOXIC_REEDS;
        var bottom = hitResult.getBlockPos();
        while (level.getBlockState(bottom.below()).is(lookingFor)) {
            bottom = bottom.below();
        }

        if (!level.getBlockState(bottom.above()).is(lookingFor)) {
            return;
        }

        final var breakPos = bottom.above(1);
        cir.setReturnValue(completeHarvest(level, state, breakPos, player, hand, stackInHand, hoeInUse, false, () -> level.removeBlock(breakPos, false)));
    }
}
