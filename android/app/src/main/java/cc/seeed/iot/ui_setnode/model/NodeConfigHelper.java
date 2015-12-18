package cc.seeed.iot.ui_setnode.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cc.seeed.iot.util.DBHelper;
import cc.seeed.iot.webapi.model.GroverDriver;
import cc.seeed.iot.yaml.IotYaml;

/**
 * Created by tenwong on 15/8/10.
 */
public class NodeConfigHelper {
    private static final String TAG = "NodeConfigHelper";
    private String node_sn;

    public NodeConfigHelper(String node_sn) {
        this.node_sn = node_sn;
    }

    public List<PinConfig> getNodeConfig() {
        List<PinConfig> pinConfigs;
        pinConfigs = PinConfigDBHelper.getPinConfigs(node_sn);
        return pinConfigs;
    }

    public Boolean addPinNode(int position, GroverDriver groverDriver) {
        String groveInstanceName;
        PinConfig pinConfig = new PinConfig();

        List<PinConfig> pinConfigs = PinConfigDBHelper.getPinConfigs(position, node_sn);
        List<String> groveInstanceNames = new ArrayList<>();
        for (PinConfig p : pinConfigs) {
            groveInstanceNames.add(p.groveInstanceName);
        }

        if (position > 6 || position < 1)
            return false;

        pinConfig.position = position;
        pinConfig.selected = true;
        groveInstanceName = groverDriver.ClassName;
        int i = 1;
        while (true) {
            if (groveInstanceNames.contains(groveInstanceName)) {
                groveInstanceName = groveInstanceName.split("_0")[0] + "_0" + Integer.toString(i);
            } else {
                groveInstanceNames.add(groveInstanceName);
                break;
            }
            i++;
        }
        pinConfig.groveInstanceName = groveInstanceName;
        pinConfig.sku = groverDriver.SKU;
        pinConfig.node_sn = node_sn;

        pinConfig.save();
        return true;
    }

    public Boolean removePinNode(String groveInstanceName) {
        List<PinConfig> pinConfigs = PinConfigDBHelper.getPinConfigs(node_sn);
        for (PinConfig pinConfig : pinConfigs) {
            if (pinConfig.groveInstanceName.equals(groveInstanceName)) {
                PinConfigDBHelper.delPinConfig(groveInstanceName, node_sn);
            }
        }

        return true;
    }

    public List<PinConfig> getPinNode(int position) {
        List<PinConfig> pinConfigs;
        pinConfigs = PinConfigDBHelper.getPinConfigs(position, node_sn);
        return pinConfigs;
    }

    public static String getConfigYaml(List<PinConfig> pinConfigs) {
        String y = "";
        for (PinConfig p : pinConfigs) {
            if (p.selected) {
                int position = p.position;
                String groveInstanceName = p.groveInstanceName;
                String sku = p.sku;
                GroverDriver groverDriver = new GroverDriver();
                try {
                    groverDriver = DBHelper.getGroves(p.sku).get(0);
                } catch (Exception e) {
                    Log.e(TAG, "getConfigYaml:" + e);
                    groverDriver = DBHelper.getGrovesAll().get(0);
                }
                String groveName = groverDriver.GroveName;
                y = y + IotYaml.genYamlItem(position, groveInstanceName, sku, groveName);
            }
        }
        return y;
    }
}