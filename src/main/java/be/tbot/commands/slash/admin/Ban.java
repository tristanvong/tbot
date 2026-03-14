package be.tbot.commands.slash.admin;

import be.tbot.BotConfig;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Ban extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Ban.class);
    private static final long DARWIN_CHANNEL_ID = BotConfig.DARWIN_CHANNEL_ID;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ban")) {

            event.deferReply().queue();

            var targetUser = event.getOption("username").getAsUser();
            var usrSnowflake = UserSnowflake.fromId(targetUser.getId());
            var eventUser = event.getUser();

            logger.info("SLASH_COMMAND ban called by {} on {}", eventUser, targetUser);

            try {
                var reason = event.getOption("reason").getAsString();

                logger.debug("USER INPUT: [reason] possible null: {}", reason);

                ban(event, usrSnowflake, reason);
            } catch (NullPointerException e) {
                ban(event, usrSnowflake, "No reason provided.");
                logger.error("NullPointerException: \"reason\" in ban command null. \n{}", e.getMessage());
            }
        }
    }

    private static void ban(SlashCommandInteractionEvent event, UserSnowflake usrSnowflake, String reason){
        event.getGuild().ban(usrSnowflake, 0, TimeUnit.SECONDS)
                .reason(reason)
                .queue(unused -> {
                    event.getHook().getInteraction().getGuild()
                            .getChannelById(TextChannel.class, DARWIN_CHANNEL_ID)
                            .sendMessage("Banned user: " + usrSnowflake.getAsMention()).queue();
                });
    }
}
