package me.hypherionmc.craterlib.common;

import me.hypherionmc.craterlib.network.CraterNetworkHandler;
import me.hypherionmc.craterlib.network.CraterPacket;
import me.hypherionmc.craterlib.network.FabricNetworkHandler;
import me.hypherionmc.craterlib.platform.services.LibCommonHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public class FabricCommonHelper implements LibCommonHelper {

    public static MinecraftServer server;

    @Override
    public CraterNetworkHandler createPacketHandler(String modid) {
        return FabricNetworkHandler.of(modid);
    }

    @Override
    public Minecraft getClientInstance() {
        return Minecraft.getInstance();
    }

    @Override
    public Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Connection getClientConnection() {
        return Minecraft.getInstance().getConnection().getConnection();
    }

    @Override
    public MinecraftServer getMCServer() {
        return server;
    }

    @Override
    public void registerClientReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        ClientPlayNetworking.registerGlobalReceiver(channelName, (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) -> {
            CraterPacket<?> packet = factory.apply(buf);
            client.execute(() -> packet.handle(client.player, client));
        });
    }

    @Override
    public void registerServerReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        ServerPlayNetworking.registerGlobalReceiver(channelName, (MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) -> {
            CraterPacket<?> packet = factory.apply(buf);
            server.execute(() -> packet.handle(player, server));
        });
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, Consumer<FriendlyByteBuf> initialData) {
        ExtendedScreenHandlerFactory factory = new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                initialData.accept(buf);
            }

            @Override
            public Component getDisplayName() {
                return menu.getDisplayName();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return menu.createMenu(i, inventory, player);
            }
        };

        player.openMenu(factory);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor) {
        return new ExtendedScreenHandlerType<>(constructor::apply);
    }
}
