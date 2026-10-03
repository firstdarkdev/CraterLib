package com.hypherionmc.craterlib.api.client.mentions;

import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Based on <a href="https://github.com/SarahIsWeird/MoreChatSuggestions/blob/main/src/main/java/com/sarahisweird/morechatsuggestions/client/MoreChatSuggestions.java">...</a>
 */
public class MentionsController {

    private static final Map<CraterIdentifier, Collection<String>> mentions = new LinkedHashMap<>();
    private static final Map<CraterIdentifier, MentionCondition> mentionConditions = new LinkedHashMap<>();
    @Getter
    private static boolean lastMentionConditional = true;

    public static void registerMention(CraterIdentifier mentionClass, Collection<String> suggestions, MentionCondition condition) {
        mentions.put(mentionClass, suggestions);
        mentionConditions.put(mentionClass, condition);
    }

    public static Collection<String> getMentions(String currentWord) {
        ArrayList<String> applicableMentions = new ArrayList<>();
        lastMentionConditional = false;

        mentionConditions.forEach((mention, condition) -> {
            boolean shouldSuggest = condition.shouldAddMention(currentWord);
            if (!shouldSuggest) return;

            if (!lastMentionConditional && condition != MentionCondition.ALWAYS) {
                lastMentionConditional = true;
            }

            applicableMentions.addAll(mentions.get(mention));
        });

        return applicableMentions;
    }

    public static boolean hasMentions() {
        return !mentions.isEmpty();
    }
}
