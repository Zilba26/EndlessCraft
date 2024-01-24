package fr.zilba.endlesscraft.block.custom;

import fr.zilba.endlesscraft.block.entity.EndlessUpgraderBlockEntity;
import fr.zilba.endlesscraft.block.entity.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class EndlessUpgrader extends BaseEntityBlock {
  public EndlessUpgrader(Properties pProperties) {
    super(pProperties);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new EndlessUpgraderBlockEntity(pPos, pState);
  }

  @Override
  public RenderShape getRenderShape(BlockState pState) {
    return RenderShape.MODEL;
  }

  @Override
  public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
    if (pState.getBlock() != pNewState.getBlock()) {
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if (blockEntity instanceof EndlessUpgraderBlockEntity) {
        ((EndlessUpgraderBlockEntity) blockEntity).drops();
      }
    }
    super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
  }

  @Override
  public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
    if (!pLevel.isClientSide()) {
      BlockEntity entity = pLevel.getBlockEntity(pPos);
      if(entity instanceof EndlessUpgraderBlockEntity) {
        NetworkHooks.openScreen(((ServerPlayer)pPlayer), (EndlessUpgraderBlockEntity)entity, pPos);
      } else {
        throw new IllegalStateException("Our Container provider is missing!");
      }
    }

    return InteractionResult.sidedSuccess(pLevel.isClientSide());
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    if(pLevel.isClientSide()) {
      return null;
    }

    return createTickerHelper(pBlockEntityType, ModBlocksEntities.ENDLESS_UPGRADER_BE.get(),
        (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
  }
}
