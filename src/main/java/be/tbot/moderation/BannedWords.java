package be.tbot.moderation;

import be.tbot.BotConfig;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class BannedWords extends ListenerAdapter {

    private static final List<String> listBannedWords = Arrays.stream(BotConfig.BANNED_WORDS_LIST).toList();
    private static final Logger logger = LoggerFactory.getLogger(BannedWords.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        var msg = event.getMessage().getContentRaw();

        if (listBannedWords.contains(msg)) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("You said a bad word.").queue();
        }
    }
}
