package net.mysticcreations.test;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.ConfigFileType;
import net.mysticcreations.lib.config.specification.ConfigSpecification;
import net.mysticcreations.lib.config.specification.IntegerField;
import net.mysticcreations.lib.config.specification.annotations.CloseCat;
import net.mysticcreations.lib.config.specification.annotations.OpenCat;

public class ExampleConfig extends ConfigSpecification {
    public ExampleConfig() {
        super(new ResourceLocation(MysticLib.MODID, "example_config"), ConfigFileType.TOML);
    }
    @OpenCat("FEET")
    public IntegerField maxBlocks = new IntegerField(40, 20, 20, 3);

    @CloseCat
    public IntegerField minBlocks = new IntegerField(40, 20, 20, 3);

}

