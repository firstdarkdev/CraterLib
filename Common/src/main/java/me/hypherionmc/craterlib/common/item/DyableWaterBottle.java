package me.hypherionmc.craterlib.common.item;

import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

/**
 * Custom Water Bottle item that supports Dye and can be used as Dye
 */
public class DyableWaterBottle extends DyeItem implements ItemDyable {

    private final DyeColor color;
    private final boolean isGlowing;

    public DyableWaterBottle(DyeColor color, boolean isGlowing, Properties properties) {
        super(color, properties);
        this.color = color;
        this.isGlowing = isGlowing;
    }

    /**
     * Normally this is used for enchanted items, in this case, it's used to check if the fluid is a glowing fluid
     *
     * @param stack
     * @return
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return this.isGlowing;
    }

    /**
     * Return the color of the item for the Color Handler
     *
     * @return
     */
    @Override
    public DyeColor getColor() {
        return this.color;
    }

    /**
     * This is basically the same as the vanilla method for water bottles
     *
     * @param stack
     * @param level
     * @param user
     * @return
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        Player playerEntity;
        Player playerEntity2 = playerEntity = user instanceof Player ? (Player) user : null;
        if (playerEntity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerEntity, stack);
        }
        if (!level.isClientSide()) {
            List<MobEffectInstance> list = PotionUtils.getMobEffects(stack);
            for (MobEffectInstance statusEffectInstance : list) {
                if (statusEffectInstance.getEffect().isInstantenous()) {
                    statusEffectInstance.getEffect().applyInstantenousEffect(playerEntity, playerEntity, user, statusEffectInstance.getAmplifier(), 1.0);
                    continue;
                }
                user.addEffect(new MobEffectInstance(statusEffectInstance));
            }
            if (stack.getItem() == this && isGlowing) {
                user.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 3600));
            }
        }
        if (playerEntity != null) {
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        if (playerEntity == null || !playerEntity.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            if (playerEntity != null) {
                playerEntity.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        level.gameEvent(user, GameEvent.DRINK, user.getOnPos());
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
