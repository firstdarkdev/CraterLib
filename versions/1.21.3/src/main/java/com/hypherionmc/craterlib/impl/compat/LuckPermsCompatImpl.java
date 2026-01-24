package com.hypherionmc.craterlib.impl.compat;

import com.hypherionmc.craterlib.api.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.api.events.compat.LuckPermsCompatEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class LuckPermsCompatImpl implements LuckPermsCompat {

    public static final LuckPermsCompatImpl INSTANCE = new LuckPermsCompatImpl();
    private final LuckPerms luckPerms = LuckPermsProvider.get();

    LuckPermsCompatImpl() {
        EventBus bus = luckPerms.getEventBus();

        bus.subscribe(NodeMutateEvent.class, e -> {
            if (!e.isUser())
                return;

            User user = (User) e.getTarget();

            Set<InheritanceNode> addedInheritance = e.getDataAfter().stream()
                    .filter(node -> node.getType() == NodeType.INHERITANCE && !e.getDataBefore().contains(node))
                    .map(NodeType.INHERITANCE::cast)
                    .collect(Collectors.toSet());

            Set<InheritanceNode> removedInheritance = e.getDataBefore().stream()
                    .filter(node -> node.getType() == NodeType.INHERITANCE && !e.getDataAfter().contains(node))
                    .map(NodeType.INHERITANCE::cast)
                    .collect(Collectors.toSet());

            if(addedInheritance.isEmpty() && removedInheritance.isEmpty()) return;

            addedInheritance.forEach(node -> {
                CraterEventBus.INSTANCE.postEvent(LuckPermsCompatEvents.GroupAddedEvent.of(
                        node.getGroupName(),
                        user.getUniqueId(),
                        user.getUsername(),
                        (username, uuid) -> BridgedGameProfile.mojang(uuid, username)));
            });

            removedInheritance.forEach(node -> {
                CraterEventBus.INSTANCE.postEvent(LuckPermsCompatEvents.GroupRemovedEvent.of(
                        node.getGroupName(),
                        user.getUniqueId(),
                        user.getUsername(),
                        (username, uuid) -> BridgedGameProfile.mojang(uuid, username)));
            });
        });
    }

    public boolean hasPermission(ServerPlayer player, String perm) {
        User luckPermsUser = luckPerms.getPlayerAdapter(ServerPlayer.class).getUser(player);
        return luckPermsUser.getCachedData().getPermissionData().checkPermission(perm).asBoolean();
    }

    @Override
    public boolean hasGroup(UUID uuid, String group) {
        return getUserGroups(uuid).stream().anyMatch(g -> g.equalsIgnoreCase(group));
    }

    @Override
    public Set<String> getUserGroups(UUID uuid) {
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user == null)
            return new HashSet<>();

        return user.getNodes(NodeType.INHERITANCE).stream().map(InheritanceNode::getGroupName).collect(Collectors.toSet());
    }

    @Override
    public boolean addGroupToUser(UUID uuid, String group) {
        AtomicBoolean added = new AtomicBoolean(false);

        Group g = luckPerms.getGroupManager().getGroup(group);
        if (g == null) {
            return false;
        }

        luckPerms.getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
            if (user == null) return;
            user.data().add(InheritanceNode.builder(group).build());
            luckPerms.getUserManager().saveUser(user);
            added.set(true);
        });

        return added.get();
    }

    @Override
    public boolean removeGroupFromUser(UUID uuid, String group) {
        AtomicBoolean removed = new AtomicBoolean(false);

        luckPerms.getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
            if (user == null) return;
            user.data().remove(InheritanceNode.builder(group).build());
            luckPerms.getUserManager().saveUser(user);
            removed.set(true);
        });

        return removed.get();
    }

}
