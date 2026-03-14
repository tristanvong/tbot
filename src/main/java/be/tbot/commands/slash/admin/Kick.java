package be.tbot.commands.slash.admin;

import be.tbot.BotConfig;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kick extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Kick.class);
    private static final long DARWIN_CHANNEL_ID = BotConfig.DARWIN_CHANNEL_ID;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("kick")) {

            event.deferReply().queue();

            var target = event.getOption("username").getAsUser();
            var usrSnowflake = UserSnowflake.fromId(target.getId());
            var eventUser = event.getUser();

            logger.info("SLASH_COMMAND unban called by {} on {}", eventUser, target);

            try {
                var reason = event.getOption("reason").getAsString();
                kick(event, usrSnowflake, reason);

            } catch (NullPointerException e) {
                kick(event, usrSnowflake, "No reason provided.");
                logger.error("NullPointerException: \"reason\" in ban command null. \n{}", e.getMessage());
            }
        }
    }

    private static void kick(SlashCommandInteractionEvent event, UserSnowflake usrSnowflake, String reason) {
        event.getGuild()
                .kick(usrSnowflake)
                .reason(reason)
                .queue(success -> {
                    event.getHook().getInteraction().getGuild()
                            .getChannelById(TextChannel.class, DARWIN_CHANNEL_ID)
                            .sendMessage("Kicked user: " + usrSnowflake.getAsMention()).queue();
                });
    }
}
