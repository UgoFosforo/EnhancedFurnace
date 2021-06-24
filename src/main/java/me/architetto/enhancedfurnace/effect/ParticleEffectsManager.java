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
        SphereEffect sphereEffect = new SphereEffect(effectManager);
        sphereEffect.setLocation(block.getLocation().toBlockLocation());
        sphereEffect.radius = 0.4;
        sphereEffect.radiusIncrease = 0.3;
        sphereEffect.particle = Particle.FLAME;
        sphereEffect.particles = 20;
        sphereEffect.duration = 600;
        sphereEffect.start();
    }

}
