package svenhjol.charm.module;

import net.minecraft.util.Identifier;
import svenhjol.charm.Charm;
import svenhjol.charm.base.CharmModule;
import svenhjol.charm.base.helper.ModHelper;
import svenhjol.charm.base.iface.Config;
import svenhjol.charm.base.iface.Module;
import svenhjol.charm.client.CoreClient;

@Module(mod = Charm.MOD_ID, client = CoreClient.class, alwaysEnabled = true, description = "Core configuration values.")
public class Core extends CharmModule {
    public static final Identifier MSG_SERVER_OPEN_INVENTORY = new Identifier(Charm.MOD_ID, "server_open_inventory");

    @Config(name = "Debug mode", description = "If true, routes additional debug messages into the standard game log.")
    public static boolean debug = false;

    public static boolean BETTER_END = false;

    @Override
    public void init() {
        BETTER_END = ModHelper.isLoaded("betterend");
    }
}
