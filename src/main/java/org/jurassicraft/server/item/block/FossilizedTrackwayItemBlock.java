package org.jurassicraft.server.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.block.FossilizedTrackwayBlock;
import org.jurassicraft.server.util.LangUtils;

public class FossilizedTrackwayItemBlock extends ItemBlock {
    public FossilizedTrackwayItemBlock(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        FossilizedTrackwayBlock.TrackwayType type = getType(stack);
        return this.block.getLocalizedName().replace("{variant}", LangUtils.translate("trackway." + type.getName() + ".name"));
    }

    private static FossilizedTrackwayBlock.TrackwayType getType(ItemStack stack) {
        FossilizedTrackwayBlock.TrackwayType[] values = FossilizedTrackwayBlock.TrackwayType.values();
        return values[stack.getItemDamage() % values.length];
    }

    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + getType(stack).getName();
    }
}
