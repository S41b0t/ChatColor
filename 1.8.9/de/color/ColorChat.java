package de.color;

import net.labymod.api.LabyModAddon;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;

import java.util.List;

public class ColorChat extends LabyModAddon {

    private boolean modEnable;
    private String colorCode;

    @Override
    public void onEnable() {

        getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String s) {

                String msg;

                // Check if mod enabled & string doesn't starts with /
                if ((!modEnable) || (s.startsWith("/"))) return false;

                msg = ColorChat.this.colorCode + s;

                Minecraft.getMinecraft().thePlayer.sendChatMessage(msg);
                return true;

            }
        });

    }

    @Override
    public void loadConfig() {
        ColorChat.this.modEnable = getConfig().has("modEnable") ? getConfig().get("modEnable").getAsBoolean() : true;
        ColorChat.this.colorCode = getConfig().has("colorCode") ? getConfig().get("colorCode").getAsString() : "";
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

        list.add(new BooleanElement("Aktiv", this, new ControlElement.IconData(Material.EMERALD), "modEnable", this.modEnable));

        StringElement style = new StringElement("Farbcode & Format", new ControlElement.IconData(Material.PAPER), ColorChat.this.colorCode, new Consumer<String>() {
            @Override
            public void accept(String s) {
                ColorChat.this.colorCode = s;
                ColorChat.this.getConfig().addProperty("colorCode", s);
                ColorChat.this.saveConfig();
            }
        });

        list.add(style);

    }
}

