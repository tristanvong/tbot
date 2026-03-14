package be.tbot.commands.slash.admin;

import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Mute extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(Mute.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("mute")) {

            event.deferReply().queue();

            var target = event.getOption("username").getAsUser();
            var usrSnowflake = UserSnowflake.fromId(target.getId());
            var minutes = event.getOption("minutes").getAsLong();
            var eventUser = event.getUser();

            logger.info("SLASH_COMMAND mute called by {} on {}", eventUser, target);

            event.getGuild().timeoutFor(usrSnowflake, Duration.ofMinutes(minutes)).queue();
        }
    }
}
