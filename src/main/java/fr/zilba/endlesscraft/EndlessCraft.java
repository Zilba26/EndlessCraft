package fr.zilba.endlesscraft;

import com.mojang.logging.LogUtils;
import fr.zilba.endlesscraft.block.ModBlocks;
import fr.zilba.endlesscraft.block.entity.ModBlocksEntities;
import fr.zilba.endlesscraft.item.ModCreativeModTabs;
import fr.zilba.endlesscraft.item.ModItems;
import fr.zilba.endlesscraft.recipe.ModRecipes;
import fr.zilba.endlesscraft.recipe.ingredient.LevelNbtIngredient;
import fr.zilba.endlesscraft.screen.EndlessUpgraderScreen;
import fr.zilba.endlesscraft.screen.ModMenuTypes;
import fr.zilba.endlesscraft.structure.ModStructures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EndlessCraft.MOD_ID)
public class EndlessCraft
{
    public static final String MOD_ID = "endless_craft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EndlessCraft()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlocksEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        ModStructures.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::registerSerializers);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.ENDLESS_UPGRADER_MENU.get(), EndlessUpgraderScreen::new);
        }
    }

    public void registerSerializers(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
            helper -> CraftingHelper.register(LevelNbtIngredient.Serializer.NAME, LevelNbtIngredient.Serializer.INSTANCE)
        );
    }
}
