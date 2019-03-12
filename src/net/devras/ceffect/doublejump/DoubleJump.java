package net.devras.ceffect.doublejump;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import net.devras.ceffect.Main;
import net.devras.ceffect.ParticleAPI.EnumParticle;
import net.devras.ceffect.ParticleAPI.Particle;

public class DoubleJump implements Listener{

	public static Sound playSound = Sound.ENTITY_FIREWORK_BLAST;
	public static EnumParticle playParticle = EnumParticle.FIREWORKS_SPARK;
	public static int cooldownTime = 10;
	public static HashMap<UUID, Integer> cooldown = new HashMap<>();

	public DoubleJump() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.Instance, new Runnable() {
			@Override
			public void run() {
				for (UUID uuid : cooldown.keySet()) {
					int n = cooldown.get(uuid);
					n--;
					if (n < 0) {
						cooldown.remove(uuid);
					}else {
						cooldown.put(uuid, n);
					}
				}
			}
		}, 0L, 10L);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR) || e.isCancelled()) {
			return;
		}

		if (p.isOnGround()) {
			if (!p.getAllowFlight()) {
				if (cooldown.containsKey(p.getUniqueId())) {
					return;
				}

				cooldown.put(p.getUniqueId(), cooldownTime);
				p.setAllowFlight(true);
			}
		}

	}

	@EventHandler
	public void onPlayerToggleFly(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR) || e.isCancelled()) {
			return;
		}

		e.setCancelled(true);

		p.setFlying(false);
		p.setAllowFlight(false);

		Location loc = p.getLocation();
		Vector v = loc.getDirection().multiply(1.2f).setY(1.2);
		p.setVelocity(v);
		loc.getWorld().playSound(loc, playSound, 1, 0.2f);
		//loc.getWorld().spawnParticle(playParticle, loc, 80);
		new Particle(playParticle, loc, 0.1f, 0.5f, 0.1f, 10).sendParticle();;

	}
}