package de.kanyuji.lobby.utils;

import jdk.javadoc.internal.doclets.formats.html.Contents;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

public class ClickableMessage {

    private String[] args;
    private ComponentBuilder message;

    /*
    Made by Brentspine 2022, posted on spigotmc.org and github.com
    */
    public ClickableMessage(String prefix) {
        this.message = new ComponentBuilder(prefix);
    }

    public static void clickableCommand(Player player, String message, String command) {
        TextComponent component = new TextComponent(message);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        player.spigot().sendMessage(component);
    }

    public static TextComponent clickableURLComponent(String message, String url) {
        TextComponent component = new TextComponent(message);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return component;
    }

    public ClickableMessage addClickEvent(String text, ClickEvent.Action actionType, String action) {
        message.append(text);
        //message.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("")));
        message.event(new ClickEvent(actionType, action));
        return this;
    }

    public ClickableMessage addClickHoverEvent(String text, ClickEvent.Action actionType, String action, HoverEvent.Action hoverAction, String hoverText) {
        message.append(text);
        message.event(new ClickEvent(actionType, action));
        message.event(new HoverEvent(hoverAction, TextComponent.fromLegacyText(hoverText)));
        message.append("");
        return this;
    }

    public ClickableMessage addClickHoverEvent(String text, ClickEvent.Action actionType, String action, HoverEvent.Action hoverAction, String hoverText, ChatColor hoverChatColor) {
        message.append(text);
        message.event(new ClickEvent(actionType, action));
        message.event(new HoverEvent(hoverAction, TextComponent.fromLegacyText(hoverText, hoverChatColor)));
        message.append("");
        return this;
    }

    public ClickableMessage addText(String text) {
        message.append(text);
        message.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ""));
        message.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(" ")));
        message.append("");
        return this;
    }

    public ClickableMessage addHoverEvent(String text, HoverEvent.Action action, String hoverText) {
        message.append(text);
        message.event(new HoverEvent(action, TextComponent.fromLegacyText(hoverText)));
        message.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ""));
        message.append("");
        return this;
    }

    public BaseComponent[] build() {
        return message.create();
    }


}
