package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.client.mentions.MentionsController;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author HypherionSA
 * Allow Users, Roles and Channels to be pingable from MC chat (Client Side)
 */
@Mixin(CommandSuggestions.class)
public abstract class ChatInputSuggestorMixin {

    @Shadow
    public abstract void showSuggestions(boolean p_93931_);

    @Shadow @Final
    EditBox input;

    @Shadow
    private static int getLastWordIndex(String p_93913_) {
        return 0;
    }

    @Inject(
            method = "updateCommandInfo",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/components/CommandSuggestions;pendingSuggestions:Ljava/util/concurrent/CompletableFuture;",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER,
                    ordinal = 0
            ),
            slice = @Slice(
                from = @At(
                        value = "INVOKE",
                        target = "Lnet/minecraft/client/gui/components/CommandSuggestions;getLastWordIndex(Ljava/lang/String;)I"
                )
            )
    )
    private void injectSuggestions(CallbackInfo ci) {
        if (MentionsController.hasMentions() && MentionsController.isLastMentionConditional()) {
            this.showSuggestions(true);
        }
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyVariable(method = "updateCommandInfo", at = @At(value = "STORE"), ordinal = 0, name = "collection")
    private Collection<String> injectMentions(Collection<String> vanilla) {
        if (!MentionsController.hasMentions())
            return vanilla;

        ArrayList<String> newSuggest = new ArrayList<>(vanilla);

        String currentInput = this.input.getValue();
        int currentCursorPosition = this.input.getCursorPosition();

        String textBeforeCursor = currentInput.substring(0, currentCursorPosition);
        int startOfCurrentWord = getLastWordIndex(textBeforeCursor);

        String currentWord = textBeforeCursor.substring(startOfCurrentWord);
        String finalWord = currentWord.replace("[", "").replace("]", "");

        Collection<String> mentions = MentionsController.getMentions(finalWord);

        if (!mentions.isEmpty()) {
            mentions.forEach(m -> newSuggest.add("[" + m + "]"));
        }

        return newSuggest;
    }
}