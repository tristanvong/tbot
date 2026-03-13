package be.tbot;

import be.tbot.commands.non_slash.PingPongCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    static final String BOT_TOKEN = System.getenv("BOT_TOKEN");

    public static void main(String[] args) {
        JDA api = JDABuilder.createDefault(
                BOT_TOKEN,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS
        ).build();

        api.addEventListener(new PingPongCommand());
    }
}
