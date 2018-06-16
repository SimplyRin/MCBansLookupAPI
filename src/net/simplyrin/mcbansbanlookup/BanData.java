package net.simplyrin.mcbansbanlookup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

/**
 * Created by SimplyRin on 2018/06/16.
 *
 *  Copyright 2018 SimplyRin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BanData {

	private String playerName;
	private int totalBans = 0;
	private double reputation = 10.0D;

	private int playerId;
	private UUID uuid;

	private List<String> all, global, local, other = new ArrayList<>();

	public BanData(String playerName, int bans, int reputation) {
		this.playerName = playerName;
		this.totalBans = bans;
		this.reputation = reputation;
	}

	public BanData(final String playerName, final JSONObject response) throws Exception {
		if(playerName == null || response == null) {
			return;
		}

		this.playerName = playerName;
		if(response.has("player")) {
			this.playerName = response.getString("player");
		}

		this.totalBans = response.getInt("total");
		this.reputation = response.getDouble("reputation");
		this.playerId = Integer.valueOf(response.getString("pid"));
		this.uuid = this.toFullUUID(response.getString("uuid"));

		if(response.getJSONArray("global").length() > 0) {
			for(int v = 0; v < response.getJSONArray("global").length(); v++) {
				this.all.add(response.getJSONArray("global").getString(v));
				this.global.add(response.getJSONArray("global").getString(v));
			}
		}
		if(response.getJSONArray("local").length() > 0) {
			for(int v = 0; v < response.getJSONArray("local").length(); v++) {
				this.all.add(response.getJSONArray("local").getString(v));
				this.local.add(response.getJSONArray("local").getString(v));
			}
		}
		if(response.getJSONArray("other").length() > 0) {
			for(int v = 0; v < response.getJSONArray("other").length(); v++) {
				this.all.add(response.getJSONArray("other").getString(v));
				this.other.add(response.getJSONArray("other").getString(v));
			}
		}
	}

	private UUID toFullUUID(String uuid) {
		return UUID.fromString(uuid.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public boolean hasBans() {
		return this.totalBans != 0;
	}

	public int getTotalBans() {
		return this.totalBans;
	}

	public double getReputation() {
		return this.reputation;
	}

	public int getMCBansPlayerId() {
		return this.playerId;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public List<String> getAllBans() {
		return this.all;
	}

	public List<String> getGlobals() {
		return this.global;
	}

	public List<String> getLocals() {
		return this.local;
	}

	public List<String> getOthers() {
		return this.other;
	}

}
