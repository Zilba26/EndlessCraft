package fr.zilba.endlesscraft.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StarEntity extends BlockEntity implements GeoBlockEntity {

  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  private static final RawAnimation ROTATE = RawAnimation.begin().thenLoop("rotate");


  public StarEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlocksEntities.STAR_BE.get(), pPos, pBlockState);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this, state -> state.setAndContinue(ROTATE)));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.cache;
  }
}
