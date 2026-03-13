package be.tbot.commands.non_slash;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingPongCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (event.getMessage().getContentRaw().equals("!ping")){
            event.getChannel().sendMessage("pong!").queue();
        }
    }
}
