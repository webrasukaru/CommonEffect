package net.devras.ceffect.arrowtrail;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;

import net.devras.ceffect.ParticleAPI.EnumParticle;
import net.devras.ceffect.ParticleAPI.Particle;

public class Trail {
	private EnumParticle e;
	private ArrayList<Arrow> arrows = new ArrayList<>();
	public Trail(EnumParticle e) {
		this.e = e;
	}
	public void addArrow(Arrow a) {
		arrows.add(a);
	}
	public void tick() {
		try {
			for (Arrow a : arrows) {
				if (a.isOnGround() || a.isDead() || a == null) {
					arrows.remove(a);
					continue;
				}else {
					particle(a.getLocation());
				}
			}
		} catch (Exception e) {
			//
		}
	}
	private void particle(Location loc) {
		new Particle(e, loc, 0.1f, 0.5f, 0.1f, 10).sendParticle();;
	}
}
