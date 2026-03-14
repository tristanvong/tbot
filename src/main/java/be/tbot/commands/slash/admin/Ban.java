package be.tbot.commands.slash.admin;

import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Ban extends ListenerAdapter {

    final static Logger logger = LoggerFactory.getLogger(Ban.class);

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

                banThem(event, usrSnowflake, reason);
            } catch (NullPointerException e) {
                banThem(event, usrSnowflake, "No reason provided.");
            }
        }
    }

    static private void banThem(SlashCommandInteractionEvent event, UserSnowflake usrSnowflake, String reason){
        event.getGuild().ban(usrSnowflake, 0, TimeUnit.SECONDS)
                .reason(reason)
                .queue();
    }
}
