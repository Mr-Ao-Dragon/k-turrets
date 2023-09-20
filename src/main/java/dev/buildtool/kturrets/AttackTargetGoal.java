package dev.buildtool.kturrets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class AttackTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private final Turret turret;

    public AttackTargetGoal(Turret turret) {
        super(turret, LivingEntity.class, 0, true, true, livingEntity -> {
            if (turret.isProtectingFromPlayers() && livingEntity instanceof PlayerEntity)
                return turret.alienPlayers.test(livingEntity);
            return Turret.decodeTargets(turret.getTargets()).contains(livingEntity.getType());
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
    protected AxisAlignedBB getTargetSearchArea(double p_188511_1_) {
        return mob.getBoundingBox().inflate(p_188511_1_, p_188511_1_, p_188511_1_);
    }
}
