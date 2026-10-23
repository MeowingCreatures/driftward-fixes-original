package ink.astrius.driftward.kube;

import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.stack.EmiStack;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import ink.astrius.driftward.reg.ModLoaded;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.Arrays;

public class DriftwardKubeJSPlugin implements KubeJSPlugin {
    public static EventGroup GROUP = EventGroup.of("Driftward");
    public static EventHandler EMI = GROUP.server("emi", () -> DriftwardEmiEventJS.class);

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        if (!ModLoaded.EMI) {
            return;
        }
        registry.register(GROUP);
    }

    public static void register(IEventBus modBus) {
        if (!ModLoaded.EMI) {
            return;
        }
        modBus.addListener(DriftwardKubeJSPlugin::registerPackets);
        NeoForge.EVENT_BUS.addListener(DriftwardKubeJSPlugin::onDatapackSync);
    }

    public static void registerPackets(final RegisterPayloadHandlersEvent event) {
        final var registry = event.registrar("1");
        registry.playToClient(DriftwardServerData.TYPE, DriftwardServerData.STREAM_CODEC, DriftwardServerData::handle);
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        // Reload is in progress OR a new player join is in progress, and we don't have prepared data
        if (event.getPlayer() == null || DriftwardServerData.preparedInstance == null) {
            final var inst = DriftwardServerData.empty();
            if (EMI.hasListeners()) {
                EMI.post(ScriptType.SERVER, new DriftwardEmiEventJS(inst));
            }
            DriftwardServerData.preparedInstance = inst.freeze();
        }
        final var payload = DriftwardServerData.preparedInstance;
        event.getRelevantPlayers().forEach(player -> PacketDistributor.sendToPlayer(player, payload));
    }

    public static void initEmi(EmiInitRegistry registry) {
        final var data = DriftwardServerData.receivedInstance;
        if (data == null) {
            return;
        }
        final var removedItems = data.removedItems().stream().flatMap(i -> Arrays.stream(i.getItems())).toList();
        for (final var item : removedItems) {
            registry.disableStack(EmiStack.of(item));
        }
    }
}
