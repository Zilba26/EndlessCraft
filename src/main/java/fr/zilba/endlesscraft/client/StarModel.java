package fr.zilba.endlesscraft.client;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.block.entity.StarEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class StarModel extends DefaultedBlockGeoModel<StarEntity> {
  public StarModel() {
    super(new ResourceLocation(EndlessCraft.MOD_ID, "star"));
  }
}
