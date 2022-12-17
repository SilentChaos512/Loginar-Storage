package net.silentchaos512.loginar.network;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.silentchaos512.loginar.LoginarMod;

import java.util.Objects;
import java.util.regex.Pattern;

public class LsNetwork {
    public static final String VERSION = "loginar-net-1";
    private static final Pattern NET_VERSION_PATTERN = Pattern.compile("loginar-net-\\d+$");
    private static final Pattern MOD_VERSION_PATTERN = Pattern.compile("^\\d+\\.\\d+\\.\\d+$");
    public static SimpleChannel channel;

    static {
        channel = NetworkRegistry.ChannelBuilder.named(LoginarMod.getId("network"))
                .clientAcceptedVersions(s -> Objects.equals(s, VERSION))
                .serverAcceptedVersions(s -> Objects.equals(s, VERSION))
                .networkProtocolVersion(() -> VERSION)
                .simpleChannel();

        channel.messageBuilder(OpenUrnSwapperPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenUrnSwapperPacket::decode)
                .encoder(OpenUrnSwapperPacket::encode)
                .consumerMainThread(OpenUrnSwapperPacket::handle)
                .add();
    }

    private LsNetwork() {}

    public static void init() {}
}
