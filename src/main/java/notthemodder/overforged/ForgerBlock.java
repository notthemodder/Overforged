package notthemodder.overforged;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Block class for the Forger. Handles player interaction (opening the menu)
 * and delegates the server-side tick to {@link ForgerBlockEntity}.
 */
public final class ForgerBlock extends BaseEntityBlock {
    public ForgerBlock(BlockBehaviour.Properties properties) { super(properties); }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() { return MapCodec.unit(() -> this); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgerBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof ForgerBlockEntity forger) {
            player.openMenu(forger);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : (world, pos, blockState, entity) -> {
            if (entity instanceof ForgerBlockEntity forger) ForgerBlockEntity.serverTick(world, pos, forger);
        };
    }
}
