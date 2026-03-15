package be.tbot.commands.slash.admin;

import be.tbot.BotConfig;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Mute extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Mute.class);
    private static final long DARWIN_CHANNEL_ID = BotConfig.DARWIN_CHANNEL_ID;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("mute")) {

            event.deferReply().queue();

            var target = event.getOption("username").getAsUser();
            var usrSnowflake = UserSnowflake.fromId(target.getId());
            var minutes = event.getOption("minutes").getAsLong();
            long days;

            try {
                days = event.getOption("days").getAsLong();
            } catch (NullPointerException e) {
                days = 0;
            }

            var daysInMinutes = days * 24 * 60;
            var totalMinutes = minutes + daysInMinutes;
            var eventUser = event.getUser();

            logger.info("SLASH_COMMAND mute called by {} on {}", eventUser, target);

            event.getGuild()
                    .timeoutFor(usrSnowflake, Duration.ofMinutes(totalMinutes))
                    .queue(success -> {
                        event.getGuild()
                                .getChannelById(TextChannel.class, DARWIN_CHANNEL_ID)
                                .sendMessage("Kicking user: " + usrSnowflake.getAsMention()).queue();
                    });
        }
    }
}
