package org.jurassicraft.server.plugin.waila;

import java.util.List;
import org.jurassicraft.JurassiCraft;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;

public class BlockDataProvider implements IWailaDataProvider {
	
	public static void init(IWailaRegistrar registrar){
		
		JurassiCraft.getLogger().debug("Init WAILA block integration");
		registrar.registerBodyProvider(new BlockDataProvider(), IWailaProvider.class);
		registrar.registerTooltipRenderer("jurassicraft.feeder", new RenderFeederTooltip());
		registrar.registerTooltipRenderer("jurassicraft.incubator", new RenderIncubatorTooltip());
	}
	
	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> body, IWailaDataAccessor data, IWailaConfigHandler config) {
		if(!(data.getTileEntity() instanceof IWailaProvider))
			return body;
		
		List<String> tileData = ((IWailaProvider) data.getTileEntity()).getWailaData(body);
		body = tileData;

		return body;
	}
}
