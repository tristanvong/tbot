package be.tbot.commands.slash.admin;

import be.tbot.BotConfig;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unban extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Unban.class);
    private static final long DARWIN_CHANNEL_ID = BotConfig.DARWIN_CHANNEL_ID;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("unban")) {

            event.deferReply().queue();

            var targetUser = event.getOption("username").getAsUser();
            var usrSnowflake = UserSnowflake.fromId(targetUser.getId());
            var eventUser = event.getUser();

            logger.info("SLASH_COMMAND unban called by {} on {}", eventUser, targetUser);

            try {
                var reason = event.getOption("reason").getAsString();
                unban(event, usrSnowflake, reason);
            } catch (NullPointerException e) {
                unban(event, usrSnowflake, "No reason provided.");
                logger.error("NullPointerException: \"reason\" in ban command null. \n{}", e.getMessage());
            }
        }
    }

    private static void unban(SlashCommandInteractionEvent event, UserSnowflake usrSnowflake, String reason) {
        event.getGuild().unban(usrSnowflake)
                .reason(reason)
                .queue(success -> {
                    event.getHook().getInteraction().getGuild()
                            .getChannelById(TextChannel.class, DARWIN_CHANNEL_ID)
                            .sendMessage("Unbanned user: " + usrSnowflake.getAsMention()).queue();
                });
    }
}
