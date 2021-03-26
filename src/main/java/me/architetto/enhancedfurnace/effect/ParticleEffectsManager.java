package me.architetto.enhancedfurnace.effect;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.*;
import me.architetto.enhancedfurnace.EnhancedFurnace;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.block.Block;

public class ParticleEffectsManager {

    private static ParticleEffectsManager particleEffectsManager;

    private final EffectManager effectManager;

    private ParticleEffectsManager() {
        if(particleEffectsManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        effectManager = new EffectManager(EnhancedFurnace.getPlugin(EnhancedFurnace.class));

    }

    public static ParticleEffectsManager getInstance() {
        if(particleEffectsManager == null) {
            particleEffectsManager = new ParticleEffectsManager();
        }
        return particleEffectsManager;
    }

    /*
    public void enhancedBurn(Block block, int duration) {
        FountainEffect fountainEffect = new FountainEffect(effectManager);
        fountainEffect.setLocation(block.getLocation().toCenterLocation().add(0,0.5,0));
        fountainEffect.iterations = duration/2;
        fountainEffect.radius = 1;
        fountainEffect.start();

    }


     */
    public void enhancedBurn(Block block, int duration) {

        CircleEffect circleEffect = new CircleEffect(effectManager);
        circleEffect.setLocation(block.getLocation().toCenterLocation().add(0,1,0));

        circleEffect.particle = Particle.REDSTONE;
        circleEffect.color = Color.RED;
        circleEffect.particleSize = 0.5f;

        circleEffect.enableRotation = false;
        circleEffect.radius = 0.5f;

        circleEffect.iterations = duration / 2;
        circleEffect.start();

    }

    public void smeltDone(Block block) {

        ParticleEffect particleEffect = new ParticleEffect(effectManager);

        particleEffect.setLocation(block.getLocation().toCenterLocation());

        particleEffect.particle = Particle.CAMPFIRE_SIGNAL_SMOKE;
        particleEffect.particleCount = 0;
        particleEffect.particleData = 0.2F;
        particleEffect.particleOffsetY = 0.2F;

        particleEffect.period = 10;
        particleEffect.iterations = 2;


        particleEffect.start();

    }

    /*

    public void shootTrace(Entity entity, Location location) {

        TraceEffect traceEffect = new TraceEffect(effectManager);
        traceEffect.setEntity(entity);
        traceEffect.particle = Particle.CAMPFIRE_COSY_SMOKE;
        traceEffect.iterations = 10;
        traceEffect.start();

        ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
        explodeEffect.setLocation(location);
        explodeEffect.amount = 5;
        explodeEffect.start();

    }

    public void shootFail(Location location) {

        ParticleEffect particleEffect = new ParticleEffect(effectManager);
        particleEffect.setLocation(location);
        particleEffect.particle = Particle.CAMPFIRE_SIGNAL_SMOKE;
        particleEffect.particleCount = 0;
        particleEffect.particleData = 0.2F;
        particleEffect.particleOffsetY = 0.2F;
        particleEffect.period = 10;
        particleEffect.iterations = 3;
        particleEffect.start();

    }

    public void cannonExplosionEffect(Location location) {

        SphereEffect sphereEffect2 = new SphereEffect(effectManager);
        sphereEffect2.setLocation(location);
        sphereEffect2.particle = Particle.CAMPFIRE_SIGNAL_SMOKE;
        sphereEffect2.particles = 30;
        sphereEffect2.radiusIncrease = 0.4;
        sphereEffect2.iterations = 4;
        sphereEffect2.particleData = 0.01F;
        sphereEffect2.start();
    }

    public void tntExplosionEffect(Location location) {
        SphereEffect sphereEffect = new SphereEffect(effectManager);
        sphereEffect.setLocation(location);
        sphereEffect.particle = Particle.CAMPFIRE_SIGNAL_SMOKE;
        sphereEffect.particles = 20;
        sphereEffect.radiusIncrease = 0.1;
        sphereEffect.iterations = 2;
        sphereEffect.particleData = 0.01F;
        sphereEffect.start();
    }

     */

}
