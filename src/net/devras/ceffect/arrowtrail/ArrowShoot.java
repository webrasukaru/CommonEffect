package net.devras.ceffect.arrowtrail;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.devras.ceffect.Main;
import net.devras.ceffect.ParticleAPI.EnumParticle;

public class ArrowShoot implements Listener{

	public static EnumParticle defaultParticle = EnumParticle.LAVA;

	public ArrowShoot() {
		Bukkit.getPluginManager().registerEvents(this, Main.Instance);
	}

	@EventHandler
	public void onPlayerShootArrow(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() != null) {
			if (e.getEntity().getShooter() instanceof Player) {
				if (e.getEntity() instanceof Arrow) {
					Player p = (Player) e.getEntity().getShooter();
					if (ArrowTrail.players.containsKey(p.getUniqueId())) {
						ArrowTrail.players.get(p.getUniqueId()).addArrow((Arrow) e.getEntity());
					}
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (ArrowTrail.players.containsKey(p.getUniqueId())) {
			ArrowTrail.players.remove(p.getUniqueId());
		}
	}

}
