package net.simplyrin.test;

import java.util.Scanner;

import net.simplyrin.mcbansbanlookup.BanData;
import net.simplyrin.mcbansbanlookup.BanLookup;
import net.simplyrin.mcbansbanlookup.MCBansAPI;

/**
 * Created by SimplyRin on 2018/06/17.
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
public class Test {

	public static void main(String[] args) {
		BanLookup banLookup = new BanLookup(MCBansAPI.KEY);

		while(true) {
			commands(banLookup);
		}
	}

	private static void commands(BanLookup banLookup) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("[MCBans] Please enter the player name to search: ");
		String name = scanner.nextLine();

		BanData banData = banLookup.getBans(name);

		System.out.println("[MCBans] Player " + banData.getPlayerName() + " has " + banData.getTotalBans() + " ban(s) and " + banData.getReputation() + " REP.");

		if(banData.hasBans()) {
			for(String msg : banData.getAllBans()) {
				System.out.println("[MCBans] " + msg);
			}
		} else {
			System.out.println("[MCBans] " + banData.getPlayerName() + " is not banned from mcbans servers!");
		}

		System.out.println("[MCBans] Done.");
	}

}
