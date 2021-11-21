package de.kanyuji.lobby;

import org.bukkit.Effect;
import org.bukkit.Sound;

public class Settings {

    public static final Sound TELEPORT_SOUND = Sound.ENTITY_ENDERMAN_TELEPORT;
    public static final Integer DOUBLE_JUMP_COOLDOWN_SECONDS = 3;
    public static final Float DOUBLE_JUMP_MULTIPLY_BY = 2f;
    public static final Sound DOUBLE_JUMP_BOOST_SOUND = Sound.ENTITY_GHAST_SHOOT;
    public static final Effect DOUBLE_JUMP_BOOST_EFFECT = Effect.ENDER_SIGNAL;
    public static final String PREFIX = "§b§lLobby §8» §7";
    public static final String NOPERM = PREFIX + "§cDazu hast du keine Berechtigungen!";

}
