package be.tbot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    static final String BOT_TOKEN = System.getenv("BOT_TOKEN");

    public static void main(String[] args) {
        var jda = JDABuilder.createDefault(
                BOT_TOKEN,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MODERATION
        ).build();

        jda.addEventListener(new BotConfig());
    }
}
