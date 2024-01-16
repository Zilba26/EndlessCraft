package fr.zilba.endlesscraft.block.entity;

import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgradeItem;
import fr.zilba.endlesscraft.recipe.EndlessUpgraderRecipe;
import fr.zilba.endlesscraft.screen.EndlessUpgraderMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EndlessUpgraderBlockEntity extends BlockEntity implements MenuProvider {

  private final ItemStackHandler itemHandler = new ItemStackHandler(3);
  private static final int TOOL_SLOT = 0;
  private static final int UPGRADE_SLOT = 1;
  private static final int RESULT_SLOT = 2;

  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

  public EndlessUpgraderBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlocksEntities.ENDLESS_UPGRADER_BE.get(), pPos, pBlockState);
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER) {
      return lazyItemHandler.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> itemHandler);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    lazyItemHandler.invalidate();
  }

  public void drops() {
    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
    inventory.setItem(TOOL_SLOT, itemHandler.getStackInSlot(TOOL_SLOT));
    inventory.setItem(UPGRADE_SLOT, itemHandler.getStackInSlot(UPGRADE_SLOT));
    Containers.dropContents(level, worldPosition, inventory);
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("block.endless_craft.endless_upgrader");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
    return new EndlessUpgraderMenu(pContainerId, pPlayerInventory, this);
  }

  @Override
  protected void saveAdditional(CompoundTag pTag) {
    super.saveAdditional(pTag);
    pTag.put("inventory", itemHandler.serializeNBT());
  }

  @Override
  public void load(CompoundTag pTag) {
    super.load(pTag);
    itemHandler.deserializeNBT(pTag.getCompound("inventory"));
  }

  public void tick(Level pLevel1, BlockPos pPos, BlockState pState1) {
    if (hasRecipe()) {
      craftItem();
    } else {
      itemHandler.setStackInSlot(RESULT_SLOT, ItemStack.EMPTY);
    }
  }

  private boolean hasRecipe() {
    Optional<EndlessUpgraderRecipe> recipe = getCurrentRecipe();
    if (recipe.isPresent()) {
      EndlessCraftUpgradeItem upgrade = (EndlessCraftUpgradeItem) itemHandler.getStackInSlot(UPGRADE_SLOT).getItem();
      return itemHandler.getStackInSlot(TOOL_SLOT).getOrCreateTag().getInt(upgrade.getKeyName()) < upgrade.getMaxLevel();
    }
    return false;
  }

  private Optional<EndlessUpgraderRecipe> getCurrentRecipe() {
    SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
    for(int i = 0; i < itemHandler.getSlots(); i++) {
      inventory.setItem(i, this.itemHandler.getStackInSlot(i));
    }

    return this.level.getRecipeManager().getRecipeFor(EndlessUpgraderRecipe.Type.INSTANCE, inventory, level);
  }

  private void craftItem() {
    Optional<EndlessUpgraderRecipe> recipe = getCurrentRecipe();
    ItemStack tool = itemHandler.getStackInSlot(TOOL_SLOT);
    ItemStack result = recipe.get().getResultByTool(tool);

    itemHandler.setStackInSlot(RESULT_SLOT, result);
  }

  public void shrinkIngredientsSlots() {
    itemHandler.setStackInSlot(TOOL_SLOT, ItemStack.EMPTY);
    itemHandler.getStackInSlot(UPGRADE_SLOT).shrink(1);
  }
}
