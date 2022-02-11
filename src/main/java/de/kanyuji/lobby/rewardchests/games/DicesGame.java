package de.kanyuji.lobby.rewardchests.games;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.Dice;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DicesGame {

    private Main plugin;
    private Player player;
    private Inventory inventory;
    //Equal to amount of rolls
    private int phase;
    private int goldPosition;
    private HashMap<Integer, Integer> moves;
    private HashMap<Integer, ItemStack> glassPanes;
    private ArrayList<Integer> unClickedSlots;
    private int total;

    private static int MAX_PHASES = 5;

    public DicesGame(Main plugin, Player player, Inventory inventory) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = inventory;
        this.moves = new HashMap<>();
        this.glassPanes = new HashMap<>();
        this.unClickedSlots = new ArrayList<>();
        this.goldPosition = 2;
        phase = 0;
    }

    public int openNext(int multi) {
        phase++;
        int upOrBelow3 = Dice.generateNumberBetween(1, 5);
        if(multi > 1)
            goldPosition = phase;
        if(upOrBelow3 <= 3) {
            int random = Dice.generateNumberBetween(1, 3) * multi;
            total+= random;
            moves.put(phase, random / multi);
            return random;
        }
        int random = Dice.generateNumberBetween(3, 6) * multi;
        total+= random;
        moves.put(phase, random / multi);
        return random;
    }

    public boolean isMoveAvailable() {
        return phase < MAX_PHASES;
    }

    public int getAmountOfSames() {
        return 3;
    }

    public int getWin() {
        int amountOfSames = getAmountOfSames();
        int win = 0;

        switch (amountOfSames) {
            case 3:
                win+= 1000;
                break;
            case 4:
                win+= 2500;
                break;
            case 5:
                win+= 5000;
                break;
            default:
                break;
        }
        win+= total * 130;
        return win;
    }

    @Deprecated
    public ChatColor getColor(int i) {
        switch (i) {
            case 1:
            case 2:
                return ChatColor.GRAY;
            case 3:
            case 4:
                return ChatColor.BLUE;
            case 5:
            case 6:
                return ChatColor.DARK_PURPLE;
            default:
                return ChatColor.WHITE;
        }
    }

    public String getDiceSkullURL(int i) {
        switch (i) {
            case 1:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0=";
            case 2:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNkOWVlZWU4ODM0Njg4ODFkODM4NDhhNDZiZjMwMTI0ODVjMjNmNzU3NTNiOGZiZTg0ODczNDE0MTk4NDcifX19";
            case 3:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19";
            case 4:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJlNzhmYjIyNDI0MjMyZGMyN2I4MWZiY2I0N2ZkMjRjMWFjZjc2MDk4NzUzZjJkOWMyODU5ODI4N2RiNSJ9fX0=";
            case 5:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ1N2UzYmM4OGE2NTczMGUzMWExNGUzZjQxZTAzOGE1ZWNmMDg5MWE2YzI0MzY0M2I4ZTU0NzZhZTIifX19";
            case 6:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM0YjM2ZGU3ZDY3OWI4YmJjNzI1NDk5YWRhZWYyNGRjNTE4ZjVhZTIzZTcxNjk4MWUxZGNjNmIyNzIwYWIifX19";
            default:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19";
        }
    }

    public String getGoldDiceSkullURL(int i) {
        switch (i) {
            case 1:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU1ZmMyYzFiYWU4ZTA4ZDNlNDI2YzE3YzQ1NWQyZmY5MzQyMjg2ZGZmYTNjN2MyM2Y0YmQzNjVlMGMzZmUifX19";
            case 2:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM2MWIwNGUxMmE4Nzk3NjdiM2I3MmQ2OTYyN2YyOWE4M2JkZWI2MjIwZjVkYzdiZWEyZWIyNTI5ZDViMDk3In19fQ==";
            case 3:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyM2Y3NzU1OGNhNjA2MGI2ZGM2YTRkNGIxZDg2YzFhNWJlZTcwODE2NzdiYmMzMzZjY2I5MmZiZDNlZSJ9fX0=";
            case 4:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFiOWM0ZDZmNzIwOGIxNDI0Zjg1OTViZmMxYjg1Y2NhYWVlMmM1YjliNDFlMGY1NjRkNGUwYWNhOTU5In19fQ==";
            case 5:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmMxNDE1OTczYjQyZjgyODZmOTQ4ZTIxNDA5OTJiOWEyOWQ4MDk2NTU5M2IxNDU1M2Q2NDRmNGZlYWZiNyJ9fX0=";
            case 6:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZmNWZiZmJjODk0NGE1MDc3NzExMzc5OGU5ZmUzYWVhYzJlMzk2NDg5NDdiNzBjYzEwM2RlYjZjOWU4NjYxIn19fQ==";
            default:
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU1YmI4YjMxZjQ2YWE5YWYxYmFhODhiNzRmMGZmMzgzNTE4Y2QyM2ZhYWM1MmEzYWNiOTZjZmU5MWUyMmViYyJ9fX0=";
        }
    }

    public int getLastRoll() {
        return moves.get(phase);
    }

    public Main getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getPhase() {
        return phase;
    }

    public HashMap<Integer, Integer> getMoves() {
        return moves;
    }

    public HashMap<Integer, ItemStack> getGlassPanes() {
        return glassPanes;
    }

    public ArrayList<Integer> getUnClickedSlots() {
        return unClickedSlots;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGoldPosition() {
        return goldPosition;
    }

}
