package net.devras.ceffect.arrowtrail;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.devras.ceffect.Main;
import net.devras.ceffect.ParticleAPI.EnumParticle;

public class ArrowTrail {

	public static void init() {
		new ArrowShoot();
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Trail t : players.values()) {
					t.tick();
				}
			}
		}.runTaskTimerAsynchronously(Main.Instance, 0, 1);

		Main.Instance.getCommand("trail").setExecutor(new TrailCommand());

	}

	public static HashMap<UUID, Trail> players = new HashMap<>();

	public static void addTrail(Player p, EnumParticle part) {
		if (part == null) {
			return;
		}
		players.put(p.getUniqueId(), null);
	}
}
