package me.architetto.enhancedfurnace.effect;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.*;
import me.architetto.enhancedfurnace.EnhancedFurnace;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;

public class ParticleEffectsManager {

    private static ParticleEffectsManager particleEffectsManager;

    private final EffectManager effectManager;

    private ParticleEffectsManager() {
        if(particleEffectsManager != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        effectManager = new EffectManager(EnhancedFurnace.getPlugin(EnhancedFurnace.class));
    }

    public static ParticleEffectsManager getInstance() {
        if(particleEffectsManager == null) {
            particleEffectsManager = new ParticleEffectsManager();
        }
        return particleEffectsManager;
    }

    public void enfHotBurnExplosion(Block block) {
        block.getWorld()
                .playSound(block.getLocation(), Sound.ENTITY_BLAZE_SHOOT,4f,1);

        SphereEffect sphereEffectFlame = new SphereEffect(effectManager);
        sphereEffectFlame.setLocation(block.getLocation().toBlockLocation());
        sphereEffectFlame.radius = 0.4;
        sphereEffectFlame.radiusIncrease = 0.3;
        sphereEffectFlame.particle = Particle.FLAME;
        sphereEffectFlame.particles = 20;
        sphereEffectFlame.duration = 600;
        sphereEffectFlame.start();

        SphereEffect sphereEffectSmoke = new SphereEffect(effectManager);
        sphereEffectSmoke.setLocation(block.getLocation().toBlockLocation());
        sphereEffectSmoke.radius = 0.1;
        sphereEffectSmoke.radiusIncrease = 0.3;
        sphereEffectSmoke.particle = Particle.CAMPFIRE_COSY_SMOKE;
        sphereEffectSmoke.particles = 5;
        sphereEffectSmoke.duration = 200;
        sphereEffectSmoke.start();
    }

}
