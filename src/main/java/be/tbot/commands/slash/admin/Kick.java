package be.tbot.commands.slash.admin;

import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kick extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Kick.class);

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

                logger.debug("USER INPUT: [reason] possible null: {}", reason);
                //TODO put this in a static method like in Ban.java (banThem)
                event.getGuild().kick(usrSnowflake).reason(reason).queue();
            } catch (NullPointerException e) {
                event.getGuild().kick(usrSnowflake).reason("No reason provided.").queue();
            }
        }
    }
}
