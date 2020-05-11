package org.jurassicraft.server.plugin.waila;

import java.util.List;

import org.jurassicraft.JurassiCraft;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;

public class EntityDataProvider implements IWailaEntityProvider {
	
	public static void init(IWailaRegistrar registrar){
		
		JurassiCraft.getLogger().debug("Init WAILA entity integration");
		registrar.registerBodyProvider(new EntityDataProvider(), IWailaProvider.class);
	}
	
	@Override
	public List<String> getWailaBody(Entity entity, List<String> body, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		if(!(entity instanceof IWailaProvider))
			return body;
		
		List<String> tileData = ((IWailaProvider) entity).getWailaData(body);
		body = tileData;

		return body;
	}
}
