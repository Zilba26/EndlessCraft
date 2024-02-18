package fr.zilba.endlesscraft.particle.custom.arcane_gauntlet_projectile_particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.zilba.endlesscraft.particle.ModParticles;
import fr.zilba.endlesscraft.particle.custom.Color;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class ArcaneGauntletProjectileParticleType extends ParticleType<ArcaneGauntletProjectileParticleType> implements ParticleOptions {

  static final ParticleOptions.Deserializer<ArcaneGauntletProjectileParticleType> DESERIALIZER = new ParticleOptions.Deserializer<>() {
    @Override
    public ArcaneGauntletProjectileParticleType fromCommand(ParticleType<ArcaneGauntletProjectileParticleType> type, StringReader reader) throws CommandSyntaxException {
      return new ArcaneGauntletProjectileParticleType(Color.fromString(reader.readString()));
    }

    @Override
    public ArcaneGauntletProjectileParticleType fromNetwork(ParticleType<ArcaneGauntletProjectileParticleType> type, FriendlyByteBuf buffer) {
      return new ArcaneGauntletProjectileParticleType(Color.from(buffer.readNbt()));
    }
  };

  public static final Codec<ArcaneGauntletProjectileParticleType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
          Codec.FLOAT.fieldOf("r").forGetter(d -> d.color.red),
          Codec.FLOAT.fieldOf("g").forGetter(d -> d.color.green),
          Codec.FLOAT.fieldOf("b").forGetter(d -> d.color.blue)
      )
      .apply(instance, ArcaneGauntletProjectileParticleType::new));

  public Color color;
  
  public ArcaneGauntletProjectileParticleType() {
    super(true, DESERIALIZER);
  }

  public ArcaneGauntletProjectileParticleType(float r, float g, float b) {
    this(new Color(r, g, b));
  }

  public ArcaneGauntletProjectileParticleType(Color color) {
    this();
    this.color = color;
  }

  @Override
  public Codec<ArcaneGauntletProjectileParticleType> codec() {
    return CODEC;
  }


  @Override
  public ParticleType<ArcaneGauntletProjectileParticleType> getType() {
    return ModParticles.ARCANE_GAUNTLET_PROJECTILE_PARTICLE.get();
  }

  @Override
  public void writeToNetwork(FriendlyByteBuf packetBuffer) {
    packetBuffer.writeNbt(color.serialize());
  }

  @Override
  public String writeToString() {
    return color.serialize().toString();
  }
}
