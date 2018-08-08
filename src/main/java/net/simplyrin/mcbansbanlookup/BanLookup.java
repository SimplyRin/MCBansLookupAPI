package net.simplyrin.mcbansbanlookup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

/**
 * Created by SimplyRin on 2018/06/16.
 *
 * Copyright (c) 2018 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class BanLookup {

	private String apiKey, playerName;

	public BanLookup(String apiKey) {
		this.apiKey = apiKey;
	}

	public BanLookup(String apiKey, String playerName) {
		this.apiKey = apiKey;
		this.playerName = playerName;
	}

	public String getBans() {
		return this.playerName;
	}

	public BanData getBans(String playerName) {
		this.playerName = playerName;

		if(this.playerName == null) {
			return null;
		}

		try {
			URL url = new URL("http://api.mcbans.com/v3/" + this.apiKey);
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000);
			connection.setDoOutput(true);

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());

			outputStreamWriter.write("admin=Console&exec=playerLookup&player=" + this.playerName);
			outputStreamWriter.flush();

			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}

			outputStreamWriter.close();
			bufferedReader.close();

			String result = stringBuilder.toString();

			// System.out.println("[MCBans] Result: " + result);

			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
			} catch (Exception e) {
				if(result.contains("error")) {
					System.out.println("[MCBans] JSON error while trying to parse lookup data!");
				}
				if(result.contains("Server Disabled")) {
					System.out.println("[MCBans] Server Disabled by an MCBans Staff Member");
					System.out.println("[MCBans] To appeal this decision, please file ticket on forums.mcbans.com");
				}
				return null;
			}

			return new BanData(this.playerName, jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
