package fr.zilba.endlesscraft.block.custom;

import fr.zilba.endlesscraft.block.entity.StarEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Star extends BaseEntityBlock {
  public Star(Properties pProperties) {
    super(pProperties);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new StarEntity(pPos, pState);
  }

  @Override
  public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
    return 15;
  }

  @Override
  public RenderShape getRenderShape(BlockState pState) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }

  @Override
  public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
    if (!pLevel.isClientSide) {
      pLevel.destroyBlock(pPos, true, pPlayer);
    }
    return InteractionResult.SUCCESS;
  }

}
