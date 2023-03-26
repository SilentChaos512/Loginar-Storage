package net.silentchaos512.loginar.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsSounds;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    protected ModSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator.getPackOutput(), LoginarMod.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(LsSounds.LOGINAR_ATTACK, definition()
                .subtitle("subtitles.entity.loginar.attack")
                .with(
                        sound(modId("loginar_attack"))
                )
        );
        add(LsSounds.LOGINAR_DEATH, definition()
                .subtitle("subtitles.entity.loginar.death")
                .with(
                        sound(modId("loginar_death1")),
                        sound(modId("loginar_death2"))
                )
        );
        add(LsSounds.LOGINAR_HURT, definition()
                .subtitle("subtitles.entity.loginar.hurt")
                .with(
                        sound(modId("loginar_hurt1")),
                        sound(modId("loginar_hurt2")),
                        sound(modId("loginar_hurt3")),
                        sound(modId("loginar_hurt4"))
                )
        );
        add(LsSounds.LOGINAR_IDLE, definition()
                .subtitle("subtitles.entity.loginar.idle")
                .with(
                        sound(modId("loginar_idle"))
                )
        );
        add(LsSounds.URN_LID, definition()
                .subtitle("subtitles.block.loginar.urn.lid")
                .with(
                        sound(modId("urn_lid"))
                )
        );
        add(LsSounds.URN_OPEN, definition()
                .subtitle("subtitles.block.loginar.urn.open")
                .with(
                        sound(modId("urn_open"))
                )
        );
    }

    private static ResourceLocation modId(String path) {
        return LoginarMod.getId(path);
    }
}
