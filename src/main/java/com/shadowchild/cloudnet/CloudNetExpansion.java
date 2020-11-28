package com.shadowchild.cloudnet;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.provider.ServiceTaskProvider;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class CloudNetExpansion extends PlaceholderExpansion {

    private final String VERSION = getClass().getPackage().getImplementationVersion();
    private Wrapper cloudnet = Wrapper.getInstance();
    private ServiceTaskProvider task = cloudnet.getServiceTaskProvider();

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "cloudnet";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ShadowChildUK";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String rawParams) {
        String[] args;
        ServiceInfoSnapshot serviceInfoSnapshot = cloudnet.getCurrentServiceInfoSnapshot();
        String params = rawParams.toLowerCase();
        IPlayerManager players = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

        if (params.startsWith("playercount_")) {
            args = params.split("playercount_");
            if (args.length < 2) return null;
            return String.valueOf(ServiceInfoSnapshotUtil.getTaskOnlineCount(args[1]));
        } else {
            switch (params) {
                case "service_name":
                    return serviceInfoSnapshot.getServiceId().getName();
                case "service_static":
                    return "" + serviceInfoSnapshot.getConfiguration().isStaticService();
                case "service_autodelete":
                    return "" + serviceInfoSnapshot.getConfiguration().isAutoDeleteOnStop();
                case "service_port":
                    return "" + serviceInfoSnapshot.getAddress().getPort();
                case "service_host":
                    return "" + serviceInfoSnapshot.getAddress().getHost();
                case "service_address":
                    return serviceInfoSnapshot.getAddress().getHost() + ":" + serviceInfoSnapshot.getAddress().getPort();
                case "task_name":
                    return serviceInfoSnapshot.getServiceId().getTaskName();
                case "total_online":
                    return "" + players.getOnlineCount();
                case "service_number":
                    return "" + serviceInfoSnapshot.getServiceId().getTaskServiceId();
            }
        }
        return null;
    }

}
