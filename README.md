# MCBansLookupAPI
MCBans の Ban を検索できる API です。

[MCBans](https://github.com/MCBans/MCBans) のコードを一部使っています。

# Maven
- Repository
```XML
  <repositories>
    <repository>
      <id>net.simplyrin</id>
      <name>api</name>
      <url>https://api.simplyrin.net/maven/</url>
    </repository>
  </repositories>
```

- Dependency
```XML
  <dependencies>
    <dependency>
      <groupId>net.simplyrin.mcbansbanlookup</groupId>
      <artifactId>MCBansBanLookup</artifactId>
      <version>1.2-SNAPSHOT</version>
    </dependency>
  </dependencies>
```

# Usage
```Java
BanLookup banLookup = new BanLookup("YOUR_API_KEY_HERE");
BanData banData = banLookup.getBans("PLAYERNAME");

System.out.println("[MCBans] Player " + banData.getPlayerName() + " has " + banData.getTotalBans() + " ban(s) and " + banData.getReputation() + " REP.");

if(banData.hasBans()) {
	System.out.println("[MCBans] Global bans");
	for(String msg : banData.getGlobals()) {
		System.out.println("[MCBans] " + msg);
	}

	System.out.println("[MCBans] Local bans");
	for(String msg : banData.getLocals()) {
		System.out.println("[MCBans] " + msg);
	}
} else {
	System.out.println("[MCBans] " + banData.getPlayerName() + " is not banned from mcbans servers!");
}
```
