package me.hypherionmc.craterlib.common;

import me.hypherionmc.craterlib.network.CraterNetworkHandler;
import me.hypherionmc.craterlib.network.CraterPacket;
import me.hypherionmc.craterlib.network.ForgeNetworkHandler;
import me.hypherionmc.craterlib.platform.services.LibCommonHelper;
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
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public class ForgeCommonHelper implements LibCommonHelper {

    public static Map<ResourceLocation, CreativeModeTab> TABS = new HashMap<>();

    public ForgeCommonHelper() {}

    @Override
    public CraterNetworkHandler createPacketHandler(String modid) {
        return ForgeNetworkHandler.of(modid, true, true);
    }

    @Override
    public MinecraftServer getMCServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    @Override
    public void registerServerReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        // UNUSED
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, @Nullable Consumer<FriendlyByteBuf> initialData) {
        if (initialData != null) {
            NetworkHooks.openScreen(player, menu, initialData);
        } else {
            NetworkHooks.openScreen(player, menu, player.getOnPos());
        }
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor) {
        return IForgeMenuType.create(constructor::apply);
    }
}
