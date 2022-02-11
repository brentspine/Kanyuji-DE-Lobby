package de.kanyuji.lobby.commands;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.Dice;
import org.apache.commons.rng.sampling.distribution.DiscreteSampler;
import org.apache.commons.rng.sampling.distribution.RejectionInversionZipfSampler;
import org.apache.commons.rng.simple.RandomSource;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;

public class LobbyTestCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 0) {
            sender.sendMessage(Main.PREFIX + "§c/lobbytest coinchest");
            return true;
        }
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(args[0].equalsIgnoreCase("1")) {
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 0f);
        }
        if(args[0].equalsIgnoreCase("2")) {
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0f, 1f);
        }
        if(args[0].equalsIgnoreCase("3")) {
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
        }
        if(args[0].equalsIgnoreCase("4")) {
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0f, 0f);
        }
        if(args[0].equalsIgnoreCase("coinchest")) {
            if(args.length <= 1) {
                sender.sendMessage(Main.PREFIX + "§cUsage: /lobbytest coinchest <exponent>");
            }
            sender.sendMessage(Main.PREFIX + "Printing the test to the console");
            int below50 = 0;
            int below100 = 0;
            int below1000 = 0;
            int below2500 = 0;
            int below5000 = 0;
            int below10k = 0;
            int below25k = 0;
            int below50k = 0;
            int below75k = 0;
            int below100k = 0;
            int below150k = 0;
            int below250k = 0;
            int below500k = 0;
            int below750k = 0;
            int below1000k = 0;
            int above1000k = 0;
            float coinsWon = 0.0F;
            DiscreteSampler sampler = RejectionInversionZipfSampler.of(RandomSource.JDK.create(), 999_850, Double.valueOf(args[1]));
            HashMap<Integer, Integer> numbers = new HashMap();

            for (int i = 0; i < 1000000; ++i) {
                int random = sampler.sample();
                random+= Dice.generateNumberBetween(0, 150);
                if (!numbers.containsKey(random)) {
                    numbers.put(random, 1);
                }

                coinsWon += (float) random;
                if (random < 50) {
                    ++below50;
                } else if (random < 100) {
                    ++below100;
                } else if (random < 1000) {
                    ++below1000;
                } else if (random < 2500) {
                    ++below2500;
                } else if (random < 5000) {
                    ++below5000;
                } else if (random < 10000) {
                    ++below10k;
                } else if (random < 25000) {
                    ++below25k;
                } else if (random < 50000) {
                    ++below50k;
                } else if (random < 75000) {
                    ++below75k;
                } else if (random < 100000) {
                    ++below100k;
                } else if (random < 150000) {
                    ++below150k;
                } else if (random < 250000) {
                    ++below250k;
                } else if (random < 500000) {
                    ++below500k;
                } else if (random < 750000) {
                    ++below750k;
                } else if (random < 1000000) {
                    ++below1000k;
                } else {
                    ++above1000k;
                }

                numbers.put(random, numbers.get(random) + 1);
            }

            System.out.println(" ");
            Iterator var22 = numbers.keySet().iterator();

            while (var22.hasNext()) {
                Integer i = (Integer) var22.next();
                System.out.println("Random: " + i + " Amount: " + numbers.get(i));
            }

            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("Below 50: " + below50);
            System.out.println("Below 100: " + below100);
            System.out.println("Below 1000: " + below1000);
            System.out.println("Below 2500: " + below2500);
            System.out.println("Below 5000: " + below5000);
            System.out.println("Below 10.000: " + below10k);
            System.out.println("Below 25.000: " + below25k);
            System.out.println("Below 50.000: " + below50k);
            System.out.println("Below 75.000: " + below75k);
            System.out.println("Below 100.000: " + below100k);
            System.out.println("Below 150.000: " + below150k);
            System.out.println("Below 250.000: " + below250k);
            System.out.println("Below 500.000: " + below500k);
            System.out.println("Below 750.000: " + below750k);
            System.out.println("Below 1.000.000: " + below1000k);
            System.out.println("Above 1.000.000: " + above1000k);
            System.out.println(" ");
            System.out.println("Coins won in 1m rolls: " + coinsWon);
            System.out.println("Average coins: " + coinsWon / 1000000.0F);
            System.out.println(" ");
        }
        return false;
    }
}
