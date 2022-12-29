package me.hypherionmc.craterlib.platform.services;

import me.hypherionmc.craterlib.network.CraterNetworkHandler;
import me.hypherionmc.craterlib.network.CraterPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.function.TriFunction;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public interface LibCommonHelper {

    public CraterNetworkHandler createPacketHandler(String modid);

    public MinecraftServer getMCServer();

    public void openMenu(ServerPlayer player, MenuProvider menu, @Nullable Consumer<FriendlyByteBuf> initialData);

    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor);

    /* FABRIC ONLY */
    public void registerServerReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory);

}
