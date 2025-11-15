package com.hypherionmc.craterlib.utils;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class DiscordMarkdownStripper {

    // Patterns for different markdown syntaxes
    private static final Pattern BOLD = Pattern.compile("\\*\\*(.*?)\\*\\*");
    private static final Pattern ITALIC_UNDERSCORE = Pattern.compile("_(.*?)_");
    private static final Pattern ITALIC_ASTERISK = Pattern.compile("\\*(.*?)\\*");
    private static final Pattern UNDERLINE = Pattern.compile("__(.*?)__");
    private static final Pattern STRIKETHROUGH = Pattern.compile("~~(.*?)~~");
    private static final Pattern CODE_BLOCK = Pattern.compile("```(.+?)```", Pattern.DOTALL);
    private static final Pattern INLINE_CODE = Pattern.compile("`([^`]*)`");
    private static final Pattern BLOCKQUOTE = Pattern.compile("^> (.*?$)", Pattern.MULTILINE);
    private static final Pattern MARKDOWN_LINK = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
    private static final Pattern PLAIN_URL = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    public static String stripMarkdown(@NotNull String text) {
        text = BOLD.matcher(text).replaceAll("$1");
        text = ITALIC_UNDERSCORE.matcher(text).replaceAll("$1");
        text = ITALIC_ASTERISK.matcher(text).replaceAll("$1");
        text = UNDERLINE.matcher(text).replaceAll("$1");
        text = STRIKETHROUGH.matcher(text).replaceAll("$1");
        text = CODE_BLOCK.matcher(text).replaceAll("$1");
        text = INLINE_CODE.matcher(text).replaceAll("$1");
        text = BLOCKQUOTE.matcher(text).replaceAll("$1");
        text = MARKDOWN_LINK.matcher(text).replaceAll("$1");
        text = PLAIN_URL.matcher(text).replaceAll("<$0>");

        return text;
    }
}
