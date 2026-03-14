package be.tbot.commands.slash.admin;

import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unban extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Unban.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("unban")) {
            //TODO add reason (optional) to unban

            event.deferReply().queue();

            var targetUser = event.getOption("username").getAsUser();
            var usrSnowflake = UserSnowflake.fromId(targetUser.getId());
            var eventUser = event.getUser();

            logger.info("SLASH_COMMAND unban called by {} on {}", eventUser, targetUser);

            event.getGuild().unban(usrSnowflake).queue();
        }
    }
}
