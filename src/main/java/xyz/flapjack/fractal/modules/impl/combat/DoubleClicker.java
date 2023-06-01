package xyz.flapjack.fractal.modules.impl.combat;

/* Custom. */
import xyz.flapjack.fractal.modules.impl.util.Random;
import xyz.flapjack.fractal.events.impl.MouseEvent;
import xyz.flapjack.fractal.bridge.impl.Player;
import xyz.flapjack.fractal.modules.Setting;
import xyz.flapjack.fractal.modules.Module;

/* Weave. */
import net.weavemc.loader.api.event.SubscribeEvent;

/* Open. */
import net.minecraft.client.settings.KeyBinding;
import java.util.ArrayList;

public class DoubleClicker extends Module {
    private boolean left = false;
    private boolean right = false;

    public DoubleClicker() {
        super("Double Clicker", "Double clicks for you.", Category.Combat, "menu", "bindable");

        this.registerSetting(new Setting("Left click", this, null, true));
        this.registerSetting(new Setting("Right click", this, null, true));

        this.registerSetting(new Setting("Chance", this, null, 75, 1, 100));
    }

    @SubscribeEvent
    public void mouseClick(final MouseEvent event) {
        ArrayList<Boolean> checks = new ArrayList<>();
        checks.add(this.mcInstance.inGameHasFocus);
        checks.add(this.enabled);

        if (this.massCheck(checks)) {
            return;
        }

        if (event.type == MouseEvent.Type.Left && !this.left) {
            if ((boolean) this.getVal("Left click")) {
                this.click(0);

                this.left = true;
            }
        } else if (!this.right) {
            if ((boolean) this.getVal("Right click")) {
                this.click(1);

                this.right = true;
            }
        } else {
            if (this.left) {
                this.left = false;
            }

            if (this.right) {
                this.right = false;
            }
        }
    }

    /**
     * Emulates a double click.
     * @param button the target button.
     */
    private void click(final int button) {
        if ((int) this.getVal("Chance") > Random.nextRandom(0, 100) + 1) {
            return;
        }

        new Thread(() -> {
            try {
                Thread.sleep((long) Random.nextRandom(215, 315));

                Player.mouse(button, true);
                KeyBinding.onTick(button == 0 ? this.mcInstance.gameSettings.keyBindAttack.getKeyCode() : this.mcInstance.gameSettings.keyBindUseItem.getKeyCode());

                Thread.sleep((long) Random.nextRandom(5, 135));

                Player.mouse(button, false);
            } catch (Exception ignored) { }
        }).start();
    }
}
