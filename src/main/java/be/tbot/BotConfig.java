package be.tbot;

import be.tbot.commands.non_slash.PingPongCommand;
import be.tbot.commands.slash.admin.Ban;
import be.tbot.commands.slash.admin.Kick;
import be.tbot.commands.slash.admin.Mute;
import be.tbot.commands.slash.admin.Unban;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class BotConfig extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        var jda = event.getJDA();

        jda.addEventListener(
                new Ban(),
                new Unban(),
                new Kick(),
                new Mute(),
                new PingPongCommand());

        jda.updateCommands().addCommands(
                Commands.slash("ban", "Ban a user.")
                        .addOption(OptionType.USER, "username", "The user to be banned.", true)
                        .addOption(OptionType.STRING,"reason", "Reason for ban (optional).", false),
                Commands.slash("unban", "Unban a user.")
                        //TODO add reason option
                        .addOption(OptionType.USER, "username", "The user to be unbanned.", true),
                Commands.slash("kick", "Kick a user.")
                        .addOption(OptionType.USER, "username", "The user to be kicked.", true)
                        .addOption(OptionType.STRING, "reason", "Reason for kick (optional).", false),
                Commands.slash("mute", "Mute a user (time out).")
                        .addOption(OptionType.USER, "username", "The user to be muted.", true)
                        //TODO think about a more custom way to mute instead of only minutes (including days etc.)
                        .addOption(OptionType.INTEGER, "minutes", "The amount of minutes the user needs to be muted for.", true)
        ).queue();
    }
}
