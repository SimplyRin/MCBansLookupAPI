package net.simplyrin.mcbansbanlookup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

/**
 * Created by SimplyRin on 2018/06/16.
 *
 * Copyright (c) 2018 SimplyRin
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

	private boolean validPlayer = false;

	private String playerName;
	private int totalBans = 0;
	private double reputation = 10.0D;

	private int playerId;
	private UUID uuid;

	private List<String> all = new ArrayList<>();
	private List<String> global = new ArrayList<>();
	private List<String> local = new ArrayList<>();
	private List<String> other = new ArrayList<>();

	public BanData(String playerName, int bans, int reputation) {
		this.playerName = playerName;
		this.totalBans = bans;
		this.reputation = reputation;
	}

	public BanData(final String playerName, final JsonObject response) throws Exception {
		if(playerName == null || response == null) {
			return;
		}

		this.playerName = playerName;
		if(response.has("player")) {
			this.playerName = response.get("player").getAsString();
		} else {
			this.validPlayer = false;
			return;
		}

		this.totalBans = response.get("total").getAsInt();
		this.reputation = response.get("reputation").getAsDouble();
		this.playerId = Integer.valueOf(response.get("pid").getAsInt());
		this.uuid = this.toUniqueId(response.get("uuid").getAsString());

		if(response.get("global").getAsJsonArray().size() > 0) {
			for(int integer = 0; integer < response.get("global").getAsJsonArray().size(); integer++) {
				this.all.add(response.get("global").getAsJsonArray().get(integer).getAsString());
				this.global.add(response.get("global").getAsJsonArray().get(integer).getAsString());
			}
		}
		if(response.get("local").getAsJsonArray().size() > 0) {
			for(int integer = 0; integer < response.get("local").getAsJsonArray().size(); integer++) {
				this.all.add(response.get("local").getAsJsonArray().get(integer).getAsString());
				this.local.add(response.get("local").getAsJsonArray().get(integer).getAsString());
			}
		}
		if(response.get("other").getAsJsonArray().size() > 0) {
			for(int integer = 0; integer < response.get("other").getAsJsonArray().size(); integer++) {
				this.all.add(response.get("other").getAsJsonArray().get(integer).getAsString());
				this.other.add(response.get("other").getAsJsonArray().get(integer).getAsString());
			}
		}

		this.validPlayer = true;
	}

	private UUID toUniqueId(String uuid) {
		return UUID.fromString(uuid.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
	}

	public boolean isValidPlayer() {
		return this.validPlayer;
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
