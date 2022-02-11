package de.kanyuji.lobby.listeners.items;

import de.brentspine.kanyujiapi.commands.FriendCommand;
import de.brentspine.kanyujiapi.mysql.data.MySQLFriends;
import de.brentspine.kanyujiapi.mysql.stats.MySQLSurf;
import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import de.kanyuji.lobby.utils.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class Profile implements Listener {


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(event.getItem().hasItemMeta() && event.getItem().getItemMeta() != null) {
                if(event.getItem().getType() == Material.PLAYER_HEAD && event.getItem().getItemMeta().getDisplayName().contains("Profil")) {
                    openProfileGUI(event.getPlayer());
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if(event.getClickedInventory() == null) return;
            if(event.getCurrentItem() == null) return;
            if(!(event.getCurrentItem().hasItemMeta())) return;
            if(event.getView().getTitle().equalsIgnoreCase("§b§lProfile")) {
                switch (event.getCurrentItem().getType()) {
                    case PAPER:
                        openStatisticsGUI(player);
                        break;
                    case REPEATER:
                        openSettingsGUI(player);
                        break;
                    case PLAYER_HEAD:
                        openFriendsGUI(player);
                        break;

                }
                event.setCancelled(true);
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lFriends")) {
                switch (event.getCurrentItem().getType()) {
                    case PLAYER_HEAD:
                        if(event.getClick().isRightClick()) {
                            if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eAdd Player")) {
                                openLobbySettingsGUI(player);
                                return;
                            }
                            ItemStack itemStack = event.getCurrentItem();
                            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                            UUID target = skullMeta.getOwningPlayer().getUniqueId();
                            openFriendDetailsGUI(player, target);
                        }
                        break;
                    case ARROW:
                        openProfileGUI(player);
                        break;
                }
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lFriends - Details")) {
                SkullMeta skullMeta = (SkullMeta) event.getView().getItem(4).getItemMeta();
                UUID target = skullMeta.getOwningPlayer().getUniqueId();
                switch (event.getCurrentItem().getType()) {
                    case BARRIER:
                        openFriendsGUI(player);
                        player.sendMessage(FriendCommand.PREFIX + "Du hast §c" + UUIDFetcher.getNameWithOfflinePlayer(target) + "§7 entfernt");
                        break;
                    case FIREWORK_ROCKET:
                        player.sendMessage("§4todo"); //todo
                        break;
                    case NOTE_BLOCK:
                        player.sendMessage("§4§4todo"); //todo
                        break;
                    case ARROW:
                        openFriendsGUI(player);
                        break;
                }
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lStatistics")) {
                switch (event.getCurrentItem().getType()) {
                    case ARROW:
                        openProfileGUI(player);
                        break;
                }
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lSettings")) {
                switch (event.getCurrentItem().getType()) {
                    case PAPER:
                        openChatSettingsGUI(player);
                        break;
                    case BARRIER:
                        openPrivacySettingsGUI(player);
                        break;
                    case NETHER_STAR:
                        openLobbySettingsGUI(player);
                        break;
                    case COBWEB:
                        openStatisticSettingsGUI(player);
                        break;
                    case ORANGE_DYE:
                        switch (event.getRawSlot()) {
                            case 11:
                                openChatSettingsGUI(player);
                                break;
                            case 12:
                                openPrivacySettingsGUI(player);
                                break;
                            case 14:
                                openLobbySettingsGUI(player);
                                break;
                            case 15:
                                openStatisticSettingsGUI(player);
                                break;
                        }
                        break;
                    case FILLED_MAP:
                        player.sendMessage("§4Todo"); //todo
                        break;
                    case RED_BED:
                        player.sendMessage("§c§4Todo"); //todo
                        break;
                    case LIME_DYE:
                        player.sendMessage("§a§4Todo"); //todo
                        break;
                    case ARROW:
                        openProfileGUI(player);
                        break;
                }
                event.setCancelled(true);
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lSettings - Chat")) {
                switch (event.getCurrentItem().getType()) {
                    case LIME_DYE:
                        player.sendMessage("§a§4Todo"); //todo
                        break;
                    case ARROW:
                        openSettingsGUI(player);
                        break;
                }
                event.setCancelled(true);
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lSettings - Privacy")) {
                switch (event.getCurrentItem().getType()) {
                    case LIME_DYE:
                        player.sendMessage("§a§4Todo"); //todo
                        break;
                    case ARROW:
                        openSettingsGUI(player);
                        break;
                }
                event.setCancelled(true);
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lSettings - Lobby")) {
                switch (event.getCurrentItem().getType()) {
                    case LIME_DYE:
                        player.sendMessage("§a§4Todo"); //todo
                        break;
                    case ARROW:
                        openSettingsGUI(player);
                        break;
                }
                event.setCancelled(true);
            }

            if(event.getView().getTitle().equalsIgnoreCase("§b§lSettings - Statistics")) {
                switch (event.getCurrentItem().getType()) {
                    case LIME_DYE:
                        player.sendMessage("§a§4Todo"); //todo
                        break;
                    case ARROW:
                        openSettingsGUI(player);
                        break;
                }
                event.setCancelled(true);
            }


        }
    }


    public void openProfileGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§b§lProfile");
        inventory.setItem(10, new ItemBuilder(Material.REPEATER).setDisplayName("§9Einstellungen").setLore("§7Hier kannst du Einstellungen verwalten!?").build());
        inventory.setItem(13, new ItemBuilder(Material.PAPER).setDisplayName("§9Statistiken").setLore("§7Klick hier um deine Statistiken zu sehen").build());
        //inventory.setItem(16, new ItemBuilder(Material.PAPER).setDisplayName("§9Discord").setLore("§7Giveaways, Updates und mehr!").build());
        inventory.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getUniqueId()).setDisplayName("§9Freunde").setLore("§7Vernetze dich mit deinen Freunden, indem", "§7du sie als Freunde hinzufügst").build());
        player.openInventory(inventory);
    }

    public void openStatisticsGUI(Player player) {
        UUID uuid = player.getUniqueId();
        Inventory inventory = Bukkit.createInventory(null, 5*9, "§b§lStatistics");
        inventory.setItem(10, new ItemBuilder(Material.STICK).setDisplayName("§4§lTTT").setLore("§4todo").build());
        inventory.setItem(11, new ItemBuilder(Material.WATER_BUCKET).setDisplayName("§3§lSURF").setLore(
                "§7Kills: §c" + MySQLSurf.getKills(uuid),
                "§7Deaths: §c" + MySQLSurf.getDeaths(uuid),
                "§7Points: §c" + MySQLSurf.getPoints(uuid)).build());
        inventory.setItem(12, new ItemBuilder(Material.CRAFTING_TABLE).setDisplayName("§9§lBINGO").setLore("§4todo").build());
        inventory.setItem(13, new ItemBuilder(Material.LAVA_BUCKET).setDisplayName("§2§lCHALLENGES").setLore("§4todo").build());
        inventory.setItem(14, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§e§lEVENT").setLore("§4todo").build());
        inventory.setItem(15, new ItemBuilder(Material.CLAY_BALL).setDisplayName("§c§lSWITCH").setLore("§4todo").build());
        inventory.setItem(16, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayName("§b§lTRYJUMP").setLore("§4todo").build());
        inventory.setItem(19, new ItemBuilder(Material.ELYTRA).setDisplayName("§a§lELYTRAWARS").setLore("§4todo").build());
        inventory.setItem(20, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming soon").build());
        inventory.setItem(21, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming soon").build());
        inventory.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming soon").build());
        inventory.setItem(23, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming soon").build());
        inventory.setItem(24, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming soon").build());
        inventory.setItem(25, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming soon").build());
        inventory.setItem(40, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").build());
        player.openInventory(inventory);
    }





    public void openFriendsGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, "§b§lFriends");
        ItemStack grayPane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§c").build();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, grayPane); //0 - 8
        }
        inventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§eYour Friends").setSkullOwner(player.getUniqueId()).build());
        Integer i = 9;
        for (UUID current : MySQLFriends.getAllFriends(player.getUniqueId())) {
            String name = UUIDFetcher.getNameWithOfflinePlayer(current);
            inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(current).setDisplayName("§9" + name).setLore("§7Friends since " + MySQLFriends.getFriendsSince(player.getUniqueId(), current), " ", "§eRight-Click for options").build());
            i++;
        }
        for (int j = 0; j < 9 ; j++) {
            inventory.setItem(j + 45, grayPane);
        }
        inventory.setItem(45, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").build());
        inventory.setItem(49, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§eAdd Player").setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19").build());
        player.openInventory(inventory);
    }


    public void openFriendDetailsGUI(Player player, UUID details) {
        String detailsName = UUIDFetcher.getNameWithOfflinePlayer(details);
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§b§lFriends - Details");
        ItemStack grayPane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§c").build();
        for (int i = 0; i < 10; i++) {
            inventory.setItem(i, grayPane); //0 - 9
        }
        inventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(details).setDisplayName("§e" + detailsName).setLore("§7Friends since " + MySQLFriends.getFriendsSince(player.getUniqueId(), details)).build());
        inventory.setItem(11, new ItemBuilder(Material.BARRIER).setDisplayName("§cRemove").setLore("§7Remove " + detailsName + " as a friend").build());
        inventory.setItem(13, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§9Party Invite").setLore("§7Send " + detailsName + " a party invite").build());
        inventory.setItem(15, new ItemBuilder(Material.NOTE_BLOCK).setDisplayName("§5Boop").setLore("§7Boop " + detailsName).build());
        for (int i = 17; i < 27; i++) {
            inventory.setItem(i, grayPane);
        }
        inventory.setItem(18, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").build());
        player.openInventory(inventory);
    }


    public void openSettingsGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, "§b§lSettings");
        inventory.setItem(2, new ItemBuilder(Material.PAPER).setDisplayName("§aChat Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        inventory.setItem(3, new ItemBuilder(Material.BARRIER).setDisplayName("§aPrivatsphäre Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        inventory.setItem(5, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§aLobby Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        inventory.setItem(6, new ItemBuilder(Material.COBWEB).setDisplayName("§aStatistik Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        inventory.setItem(11, new ItemBuilder(Material.ORANGE_DYE).setDisplayName("§aKlicken zum öffnen").hideMetaTags(true).build());
        inventory.setItem(12, new ItemBuilder(Material.ORANGE_DYE).setDisplayName("§aKlicken zum öffnen").hideMetaTags(true).build());
        inventory.setItem(14, new ItemBuilder(Material.ORANGE_DYE).setDisplayName("§aKlicken zum öffnen").hideMetaTags(true).build());
        inventory.setItem(15, new ItemBuilder(Material.ORANGE_DYE).setDisplayName("§aKlicken zum öffnen").hideMetaTags(true).build());
        inventory.setItem(29, new ItemBuilder(Material.FILLED_MAP).setDisplayName("§aUpdate Notifications").setLore("§7Stellt ein, ob du bei Updates eine Nachricht mit Informationen erhälst").hideMetaTags(true).build());
        inventory.setItem(33, new ItemBuilder(Material.RED_BED).setDisplayName("§aAutomatische Warteschlange").setLore("§7Stellt ein, ob du nach einem Spiel automatisch zum nächsten weitergeleitet wirst").hideMetaTags(true).build());
        inventory.setItem(38, new ItemBuilder(Material.LIME_DYE).setDisplayName("§aEnabled").hideMetaTags(true).build());
        inventory.setItem(42, new ItemBuilder(Material.LIME_DYE).setDisplayName("§aEnabled").hideMetaTags(true).build());
        inventory.setItem(49, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").hideMetaTags(true).build());
        player.openInventory(inventory);
    }

    public void openChatSettingsGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, "§b§lSettings - Chat");
        inventory.setItem(4, new ItemBuilder(Material.PAPER).setDisplayName("Todo").build());
        inventory.setItem(49, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").hideMetaTags(true).build());
        player.openInventory(inventory);
    }

    public void openPrivacySettingsGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, "§b§lSettings - Privacy");
        inventory.setItem(4, new ItemBuilder(Material.PAPER).setDisplayName("Todo").build());
        inventory.setItem(49, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").hideMetaTags(true).build());
        player.openInventory(inventory);
    }

    public void openLobbySettingsGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, "§b§lSettings - Lobby");
        inventory.setItem(4, new ItemBuilder(Material.PAPER).setDisplayName("Todo").build());
        inventory.setItem(49, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").hideMetaTags(true).build());
        player.openInventory(inventory);
    }

    public void openStatisticSettingsGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, "§b§lSettings - Statistics");
        inventory.setItem(4, new ItemBuilder(Material.PAPER).setDisplayName("Todo").build());
        inventory.setItem(49, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").hideMetaTags(true).build());
        player.openInventory(inventory);
    }


    public void openAddFriendInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.ANVIL, "§b§lFriends - Add Friend");
        player.sendMessage("Try");
        player.openInventory(inventory);
        player.sendMessage("Done");
    }

}
