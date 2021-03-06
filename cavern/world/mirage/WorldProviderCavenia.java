package cavern.world.mirage;

import cavern.client.CaveMusics;
import cavern.config.CaveniaConfig;
import cavern.config.manager.CaveBiomeManager;
import cavern.config.property.ConfigBiomeType;
import cavern.world.CaveDimensions;
import cavern.world.WorldProviderCavern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderCavenia extends WorldProviderCavern
{
	@SideOnly(Side.CLIENT)
	private int musicCounter;

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkGeneratorCavenia(world);
	}

	@Override
	public DimensionType getDimensionType()
	{
		return CaveDimensions.CAVENIA;
	}

	@Override
	public ConfigBiomeType.Type getBiomeType()
	{
		return CaveniaConfig.biomeType.getType();
	}

	@Override
	public int getWorldHeight()
	{
		return CaveniaConfig.worldHeight;
	}

	@Override
	public CaveBiomeManager getBiomeManager()
	{
		return CaveniaConfig.biomeManager;
	}

	@Override
	public int getMonsterSpawn()
	{
		return CaveniaConfig.monsterSpawn;
	}

	@Override
	public double getBrightness()
	{
		return CaveniaConfig.caveBrightness;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public MusicType getMusicType()
	{
		Minecraft mc = FMLClientHandler.instance().getClient();

		if (mc.ingameGUI.getBossOverlay().shouldDarkenSky())
		{
			if (++musicCounter < 200)
			{
				return super.getMusicType();
			}

			return CaveMusics.CAVENIA;
		}

		musicCounter = 0;

		return super.getMusicType();
	}
}