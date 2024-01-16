package fr.zilba.endlesscraft.structure.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.zilba.endlesscraft.block.ModBlocks;
import fr.zilba.endlesscraft.structure.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

// Thanks to TelepathicGrunt for this class
// You can find the original code here: https://github.com/TelepathicGrunt/StructureTutorialMod
public class MeteoriteStructure extends Structure {

  // A custom codec that changes the size limit for our code_structure_sky_fan.json's config to not be capped at 7.
  // With this, we can have a structure with a size limit up to 30 if we want to have extremely long branches of pieces in the structure.
  public static final Codec<MeteoriteStructure> CODEC = RecordCodecBuilder.<MeteoriteStructure>mapCodec(instance ->
      instance.group(MeteoriteStructure.settingsCodec(instance),
          StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
          ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
          Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
          HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
          Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
          Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
      ).apply(instance, MeteoriteStructure::new)).codec();
  private final Holder<StructureTemplatePool> startPool;
  private final Optional<ResourceLocation> startJigsawName;
  private final int size;
  private final HeightProvider startHeight;
  private final Optional<Heightmap.Types> projectStartToHeightmap;
  private final int maxDistanceFromCenter;

  public MeteoriteStructure(Structure.StructureSettings config,
                       Holder<StructureTemplatePool> startPool,
                       Optional<ResourceLocation> startJigsawName,
                       int size,
                       HeightProvider startHeight,
                       Optional<Heightmap.Types> projectStartToHeightmap,
                       int maxDistanceFromCenter)
  {
    super(config);
    this.startPool = startPool;
    this.startJigsawName = startJigsawName;
    this.size = size;
    this.startHeight = startHeight;
    this.projectStartToHeightmap = projectStartToHeightmap;
    this.maxDistanceFromCenter = maxDistanceFromCenter;
  }
  private static Map<String, Integer> extraSpawningChecks(Structure.GenerationContext context) {
    // Grabs the chunk position we are at
    ChunkPos chunkpos = context.chunkPos();
    System.out.println("Chunk middle coords : " + chunkpos.getMiddleBlockX() + " - " + chunkpos.getMiddleBlockZ());

    int min = 255;
    int max = -64;
    int xMax = chunkpos.getMiddleBlockX();
    int zMax = chunkpos.getMiddleBlockZ();
    for (int i = chunkpos.getMinBlockX(); i <= chunkpos.getMaxBlockX(); ++i) {
      for (int j = chunkpos.getMinBlockZ(); j <= chunkpos.getMaxBlockZ(); ++j) {
        int k = context.chunkGenerator().getFirstOccupiedHeight(i, j,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, context.heightAccessor(), context.randomState());
        if (k == -1) return null;
        if (k < min) {
          min = k;
        }
        if (k > max) {
          max = k;
          xMax = i;
          zMax = j;
        }
        if (max - min > 5) {
          return null;
        }
      }
    }

    return Map.of("x", xMax,"y", max, "z", zMax);
  }

  @Override
  public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {

    // Check if the spot is valid for our structure. This is just as another method for cleanness.
    // Returning an empty optional tells the game to skip this spot as it will not generate the structure.
    Map<String, Integer> coords = MeteoriteStructure.extraSpawningChecks(context);
    if (coords == null) {
      return Optional.empty();
    }

    // Set's our spawning blockpos's y offset to be 60 blocks up.
    // Since we are going to have heightmap/terrain height spawning set to true further down, this will make it so we spawn 60 blocks above terrain.
    // If we wanted to spawn on ocean floor, we would set heightmap/terrain height spawning to false and the grab the y value of the terrain with OCEAN_FLOOR_WG heightmap.
    int startY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
    System.out.println("startY: " + startY);
    System.out.println("coords: " + coords);
    // Turns the chunk coordinates into actual coordinates we can use. (Gets corner of that chunk)
    ChunkPos chunkPos = context.chunkPos();
    BlockPos blockPos = new BlockPos(chunkPos.getMaxBlockX(), coords.get("y"), chunkPos.getMaxBlockZ());
    System.out.println("blockPos: " + blockPos);

    Optional<Structure.GenerationStub> structurePiecesGenerator =
        JigsawPlacement.addPieces(
            context, // Used for JigsawPlacement to get all the proper behaviors done.
            this.startPool, // The starting pool to use to create the structure layout from
            this.startJigsawName, // Can be used to only spawn from one Jigsaw block. But we don't need to worry about this.
            this.size, // How deep a branch of pieces can go away from center piece. (5 means branches cannot be longer than 5 pieces from center piece)
            blockPos, // Where to spawn the structure.
            false, // "useExpansionHack" This is for legacy villages to generate properly. You should keep this false always.
            Optional.empty(), // Adds the terrain height's y value to the passed in blockpos's y value. (This uses WORLD_SURFACE_WG heightmap which stops at top water too)
            // Here, blockpos's y value is 60 which means the structure spawn 60 blocks above terrain height.
            // Set this to false for structure to be place only at the passed in blockpos's Y value instead.
            // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
            this.maxDistanceFromCenter); // Maximum limit for how far pieces can spawn from center. You cannot set this bigger than 128 or else pieces gets cutoff.

    // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
    return structurePiecesGenerator;
  }

  @Override
  public void afterPlace(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pChunkGenerator, RandomSource pRandom, BoundingBox pBoundingBox, ChunkPos pChunkPos, PiecesContainer pPieces) {
    super.afterPlace(pLevel, pStructureManager, pChunkGenerator, pRandom, pBoundingBox, pChunkPos, pPieces);

    // Randomly place a star in the structure
    if (pRandom.nextInt(4) == 1) {
      BlockPos center = pPieces.calculateBoundingBox().getCenter().below(2);
      pLevel.setBlock(center, ModBlocks.STAR.get().defaultBlockState(), 2);
      System.out.println("Placed star at " + center);
    }

    System.out.println(pPieces.calculateBoundingBox());
    // Place meteorite blocks below the structure if it's air
    System.out.println("Placing meteorite blocks");
    BoundingBox boundingBox = pPieces.calculateBoundingBox();
    for (int x = boundingBox.minX(); x <= boundingBox.maxX(); x++) {
      for (int z = boundingBox.minZ(); z <= boundingBox.maxZ(); z++) {
        for (int y = boundingBox.minY(); y >= 0; y--) {
          BlockPos pos = new BlockPos(x, y, z);
          if (pLevel.getBlockState(pos).isAir()) {
            System.out.println("Placing meteorite block at " + pos);
            //pLevel.setBlock(pos, Blocks.RED_CONCRETE.defaultBlockState(), 2);
          } else {
            break;
          }
        }
      }
    }
  }

  @Override
  public StructureType<?> type() {
    return ModStructures.METEORITE_STRUCTURE.get();
  }
}
