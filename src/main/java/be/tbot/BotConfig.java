package be.tbot;

import be.tbot.commands.non_slash.PingPongCommand;
import be.tbot.commands.slash.admin.Ban;
import be.tbot.commands.slash.admin.Kick;
import be.tbot.commands.slash.admin.Mute;
import be.tbot.commands.slash.admin.Unban;
import be.tbot.other.BannedWords;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class BotConfig extends ListenerAdapter {

    public static final long LOG_CHANNEL_ID = Long.parseLong(System.getenv("LOG_CHANNEL_ID"));
    public static final long DARWIN_CHANNEL_ID = Long.parseLong(System.getenv("DARWIN_CHANNEL_ID"));
    public static final String BANNED_WORDS = System.getenv("BANNED_WORDS");
    public static final String[] BANNED_WORDS_LIST = BANNED_WORDS.split(",");

    private static final Logger logger = LoggerFactory.getLogger(BotConfig.class);

    @Override
    public void onReady(ReadyEvent event) {
        var jda = event.getJDA();

        jda.addEventListener(
                new Ban(),
                new Unban(),
                new Kick(),
                new Mute(),
                new BannedWords(),
                //non-slash commands:
                new PingPongCommand());

        jda.updateCommands().addCommands(
                Commands.slash("ban", "Ban a user.")
                        .addOption(OptionType.USER, "username", "The user to be banned.", true)
                        .addOption(OptionType.STRING,"reason", "Reason for ban (optional).", false),
                Commands.slash("unban", "Unban a user.")
                        .addOption(OptionType.USER, "username", "The user to be unbanned.", true)
                        .addOption(OptionType.STRING, "reason", "Reason for unban (optional).", false),
                Commands.slash("kick", "Kick a user.")
                        .addOption(OptionType.USER, "username", "The user to be kicked.", true)
                        .addOption(OptionType.STRING, "reason", "Reason for kick (optional).", false),
                Commands.slash("mute", "Mute a user (time out).")
                        .addOption(OptionType.USER, "username", "The user to be muted.", true)
                        .addOption(OptionType.INTEGER, "minutes", "The amount of minutes the user needs to be muted for.", true)
                        .addOption(OptionType.INTEGER, "days", "The amount of days the user needs to be muted for (optional).", false)
        ).queue();
    }
}
