# Lobby plugin free to use 
Hey, this is the Lobby plugin for a Minecraft server project I was Lead Developer in.<br>
The server gained some traction with other gamemodes that might be reused on other servers so they are mostly private (Check out <a href="https://github.com/brentspine/TTT-Gamemode">TTT</a>). We decided to make this repository public for any upcoming servers that might wanna use it or parts of it. If you do so I would like a part in the credits if you use parts of it or a visible message in the scoreboard for example if you use it as a template to build around it. This contains some german messages so you might wanna go through it and change them to an english version or implement a language system.

# Features
 <h3>Commands</h3>

 + Discord
   + Sends the link to the old DC server
 + Gamemode
   + This was a test command added by a new developer, supports /gamemode [0,1,2,3]
 + LobbyTest <span id="lobbytest-command"></span>
   + A tempory command to test code. Currently contains a test calculation on the winrate of the Scratch-off Minigame
 + MessageLater
   + This is a command I experimented a bit, it sends a direct message to the given friend when they enter the lobby the next time
 + Setup
   + Used to setup custom locations used in the plugin. Check out <a href="#setup">Setup</a> for more info
 + Spawn
   + Teleports the executing player to spawn, which is setup with `/setup spawn`
 + <a href="#coins-image">Coins</a> (API)
   + For normal players it displays all coins
   + Players with the `coins.<action>` can modify the coins amount of any player
     + Set, Add, remove, reset, check
     + No matter what you do don't look at the code it is not mine
 + Playtime (API)
   + Every minute playtime is added to all players online (By the API)
   + For normal players it displays their playtime in a readable string
   + Players with the `playtime.<action>` (idk too) can modify the playtime of any player
     + set, add, remove, reset, check, (pause)
   + Players get flagged as AFK when not having moved for 1 minute

 + ApiTest (API)
   + See <a href="#lobbytest-command">LobbyTest command</a>
 + Friends (API)
   + Add or remove friends
   + See your friend list and since when you are friends with them
   + Lobby plugin contains GUI for the system (not polished as far as I know)
 + Keys (API)
   + For normal players it displays all coins
   + Players with the `api.keys` (idk act.) can modify the key amount of any player
     + Set, Add, remove, reset, check
     + Only stored in the database, not actual items
   + Ability to modify every single key type for the given player
 + <a href="#chat-image">Ignore</a> (API)
   + Acts like a list from which the player can add or remove anybody
   + Messages from ignored players are displayed as `<Ignored Message>`
 
 <h3>Items</h3>
 
 + <a href="#teleporter-image">Teleporter</a> (Firework)
   + Opens up a menu, from which you can teleport to different gamemode NPCs. Locations are set with the <a href="#setup">setup command</a>
 + Inventory (Chest)
   + Opens up a menu where you can see all your owned items
   + 3 Possible categories to pick from
   + Only BlockTrails where implemented at the time development ended
 + Profile (Player Head)
   + Opens up a menu from where you can select different submenus like
   + <a href="#friendpage-image">Friendpage</a>
   + <a href="#stats-image">Stats</a>
 + Player Hider (Dye)
   + Select which players are shown (All, VIP, none)

 <h3>Cosmetics and Economy</h3>
 
 + BlockTrail System
   + Players can equip different blocktrails which will leave a path of the block they selected behind them.
   + It was planned that players can either buy them with coins or obtain them from loot chests (Cosmetical only)
   + Inspired from Hypixel duels
 + Coinsystem
   + Part of the API System, have a look at the <a href="#commands">command list</a>
 + 3 types of gambling
   + <a href="#keys-image">Different types of keys</a>
     + Keys for every game
     + Special universal keys, which can be dropped for example
     + Only stored in a database, not an actual item
   + Scratch Off
     + The player wins on the long run (Mostly they lose but there are jackpots, win percentage is ca. 120% in 1m runs)
     + After opening the player is shown an inventory full of enderchests, behind all of them is a specific coins amount they will win.
     + The player can open 5 chests, then it transitions to the end phase.
     + Different types of wins, from Common to Jackpot
       + Common, Uncommon, Rare, Epic, Legendary I-III, Godlike I-V, Jackpot
     + Inspired from GommeHD coin system
   + Dices
     + A very unpolished and unbalanced game, which was in development at the time it stopped
     + Might wanna remove, rewrite or finish it.
   + Slots
     + As far as I know there was no development on this and it was just a placeholder

 <h3>Rank and Chatsystem Support</h3>
 We used Cloudnet Ranks on the server, the plugin provides a custom chatsystem, with prefixes and colors.<br>
 You can add new ranks or modify the ones that are already set-up <a href="https://github.com/brentspine/Kanyuji-DE-API/blob/main/src/main/resources/ranks.yml">here</a>. How the rank design currently looks like can you see <a href="#chat-image">here</a>.<br>
 The Chatsystem also contains an Anti-Spam filter, that prevents people from sending the same or a similiar message twice. It also mutes players spamming messages in chat for a small amount of time.<br><br>
<a href="https://www.buymeacoffee.com/brentspine"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=brentspine&button_colour=FF5F5F&font_colour=ffffff&font_family=Comic&outline_colour=000000&coffee_colour=FFDD00" /></a>


<h3>API</h3>
You need the API plugin in your plugins folder for the plugin to function, you can add the file as a maven dependency to any other project:
More in <a href="#installation">Installation</a>

 <h3>Other</h3>
 
 + Playerhider
   + Hide or show players by
     + Show all
     + Show only VIPs
     + Hide everyone
 + Doublejump (lobby.doublejump)
 + Fly (lobby.fly)
 + Prebuild API plugin for further use and improvement
 + Example gamemode
 + AFK System
 
# Installation
There are no downloads for this project so you have to compile it yourself (Using <a href="https://maven.apache.org/index.html">Maven</a>), if you don't know how you might wanna check out <a href="https://github.com/brentspine/Kanyuji-DE-Lobby/BUILD_A_SERVER_NETWORK.MD">my experiences on building a server network</a>. <br>
For this plugin to function you need the <a href="https://github.com/brentspine/Kanyuji-DE-API">Kanyuji-DE-API</a> in your plugins folder as well as <a href="https://www.spigotmc.org/resources/protocollib.1997/">ProtocolLib</a>. Additionally you need to put the exported API project into your Maven folder <a href="https://imgur.com/a/dI7aS4U"> `.m2` </a>. Then you can add the library to your project as a dependency:<br>
```
<dependency>
     <groupId>de.brentspine</groupId>
     <artifactId>kanyuji-api</artifactId>
     <version>1.1-SNAPSHOT</version>
 </dependency>
 ```
Have a look at the <a href="https://github.com/brentspine/Kanyuji-DE-API/README.md">README.md</a> file in the repository on further instructions how the MySQL tables are set up.
 
<h3>Disclaimer</h3>
This plugin was made with little expertise and was a programming project by me to learn more about Java and SQL in a fun way. The project is not fully developed, but should be bug free. The code is far from clean and doesn't have the best performance, but if you want to start your a new minecraft server and don't want to spend time on building a simple lobby system this should do the trick, since it also should be a solid foundation together with the API project to build more. 

# Setup
This plugin uses a util file made by <a href="https://linktr.ee/brentspine">me</a>. I might add some more Util files to <a href="https://github.com/spigot-dev-utils">Spigot Dev Utils</a> in the future. **If you use on of these please add a small notice at the beginning of the file :D**<br>
This util file let's you save any location to a `location.yml` config. In this plugin it is implemented with the `/setup <location-name>` command, which sets the location you are currently on to the location-name given in the arguments. 

<h3>Custom Setups</h3>
These are some custom setups you need to run for the plugin to function properly. If not please open an issue or <a href="https://linktr.ee/brentspine">contact me</a> because I might have forgotten something

You need to run `/rewardchestsetup` to setup points for the coin system, like reward villagers and chests. The command is pretty self-explanatory and goes through everything. Only 1 out of the 4 locations works perfectly, the other ones aren't used in this point of development.

<h3>Locations</h3>
This is a list of locations that might be needed if you make little changes to the plugin. I will add them soon, but don't expect it, I might forget it.<br>
<i>To be added</i><br>
<i>You can find the needed positions in the class for the teleporter item. I advise you to completly redesign it. If a position doesn't work check the console there should be displayed which location is missing</i>

# Permissions <br>

**Commands** <br>
Discord         - none <br>
Gamemode        - lobby.gamemode <br>
Lobbytest       - lobby.debug <br>
Messagelater    - lobby.messagelater <br>
Setup           - lobby.setup <br>
Spawn           - none <br>
<br>
**Cosmetics** <br>
Blocktrails     - system.blocktrail. + material <br>
<br>
**Ranks** <br>
Playerhider-vip - system.vip <br>
<br>
**Other** <br>
Doublejump      - lobby.doublejump <br>
Fly             - lobby.fly <br>
 <br>


# Screenshots
This is not everything but I the server is dead since a long time ago and the admin stopped paying. I searched some old discord chats but this is everything I could find in a reasonable amount of time. This also includes images from the API plugin. They go hand in hand and you need the API for the Lobby function to function so it doesn't matter.
Also the rank system and NPCs are not part of the Lobby system itself, but rather part of the <a href="https://cloudnetservice.eu/">CloudNet</a> server management system. 
<img src="https://media.discordapp.net/attachments/843864143026847804/943832803068477471/unknown.png?width=1266&height=663" id="coins-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/941414394942877756/unknown.png" id="chat-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/941414395295195176/unknown.png" id="keys-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/941414395924316260/unknown.png" id="apicommands-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/941414396142424124/kjdsafnlkjasndlkfjasndkjf.JPG" id="tab-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/941414396373123113/unknown.png" id="lobbycommands-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/941414396628983920/skdjflaksdjfnlkajsndfkasd.JPG" id="teleporter-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/941414397400711238/unknown.png" id="friendpage-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/918499857185013770/unknown.png" id="friendrequest-image">
<img src="https://media.discordapp.net/attachments/843864143026847804/916442134247252028/unknown.png?width=1270&height=662" id="stats-image">
<img src="https://images-ext-2.discordapp.net/external/-q3EXh9VEqXpwVFgoKe5qH2mXNI8BK1WgJj1CWo5HNU/https/i.imgur.com/YZABtNI.png?width=1270&height=662" id="kanyuji-image">
