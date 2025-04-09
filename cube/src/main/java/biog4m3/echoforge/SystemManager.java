package biog4m3.echoforge;

import java.util.Queue;
import java.util.Set;

public class SystemManager {
    private final Set<ISystem> systems = new java.util.HashSet<>();
    private final Queue<ISystem> pendingSystems = new java.util.LinkedList<>();

    public void registerSystem(ISystem system) {
        pendingSystems.add(system);
    }

    public void unregisterSystem(ISystem system) throws Exception {
        if (systems.contains(system)) {
            systems.remove(system);
            system.shutdown(null); // Pass null or appropriate context
        } else {
            pendingSystems.remove(system);
        }
    }

    private void processPendingSystems(GameContext context) throws Exception {
        ISystem system;
        while ((system = pendingSystems.poll()) != null) {
            systems.add(system);
            system.init(context);
        }
    }

    public void init(GameContext context) throws Exception {
        for (ISystem system : systems) {
            system.init(context);
        }
    }

    public void tick(GameContext context) throws Exception {
        processPendingSystems(context);

        for (ISystem system : systems) {
            system.tick(context);
        }
    }

    public void render(GameContext context) throws Exception {
        for (ISystem system : systems) {
            system.render(context);
        }
    }

    public void shutdown(GameContext context) throws Exception {
        for (ISystem system : systems) {
            system.shutdown(context);
        }
        systems.clear();
    }
}
