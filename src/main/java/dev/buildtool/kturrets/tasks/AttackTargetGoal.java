package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Turret;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class AttackTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private final Turret turret;

    public AttackTargetGoal(Turret turret) {
        super(turret, LivingEntity.class, 0, true, true, livingEntity -> {
            if (turret.isProtectingFromPlayers() && livingEntity instanceof Player)
                return turret.alienPlayers.test(livingEntity);
            return turret.decodeTargets(turret.getTargets()).contains(livingEntity.getType());
        });
        this.turret = turret;
    }

    @Override
    public boolean canUse() {
        return turret.isArmed() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return turret.isArmed() && super.canContinueToUse();
    }

    @Override
    protected AABB getTargetSearchArea(double range) {
        return mob.getBoundingBox().inflate(range, range, range);
    }
}
