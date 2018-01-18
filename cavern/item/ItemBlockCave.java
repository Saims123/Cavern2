package cavern.item;

import cavern.block.BlockCave.EnumType;
import cavern.core.Cavern;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCave extends ItemBlock
{
	public ItemBlockCave(Block block)
	{
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "tile." + EnumType.byItemStack(stack).getUnlocalizedName();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String name = super.getItemStackDisplayName(stack);

		switch (EnumType.byItemStack(stack))
		{
			case FISSURED_STONE:
			case FISSURED_PACKED_ICE:
				return ("" + Cavern.proxy.translateFormat("tile.fissured.name", name)).trim();
			default:
		}

		return name;
	}
}