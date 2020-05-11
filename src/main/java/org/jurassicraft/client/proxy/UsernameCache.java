package org.jurassicraft.client.proxy;

import com.google.common.io.Resources;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SyncCosmetics
 */
public final class UsernameCache {
	private static Map<UUID, String> names = new HashMap<UUID, String>();
	private static final ExecutorService threadPool = new ThreadPoolExecutor(0, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

	public static String getPlayerName(final UUID uuid) {
		String name = null;
		try {
			return threadPool.submit(() -> fetchPlayerName(uuid)).get();
			
		}catch (InterruptedException | ExecutionException e) {
			names.put(uuid, "");
			return name;
		}
	};
	
	private static String fetchPlayerName(final UUID uuid) throws IOException {
		if (names.containsKey(uuid)) {
			return names.get(uuid);
		}
		String url = "https://api.mojang.com/user/profiles/%s/names";
		try (BufferedReader reader = Resources.asCharSource(new URL(String.format(url, uuid.toString().replaceAll("-", ""))), StandardCharsets.UTF_8).openBufferedStream()) {
			JsonReader json = new JsonReader(reader);
			json.beginArray();
			String name = "";
			long when = 0;

			while (json.hasNext()) {
				String tempName = null;
				long time = 0;
				json.beginObject();
				while (json.hasNext()) {
					String key = json.nextName();
					switch (key) {
					case "name":
						tempName = json.nextString();
						break;
					case "changedToAt":
						time = json.nextLong();
						break;
					default:
						json.skipValue();
						break;
					}
				}
				json.endObject();

				if (tempName != null && time >= when) {
					name = tempName;
				}
			}

			json.endArray();
			json.close();
			names.put(uuid, name);
			return name;
		}
	}

}