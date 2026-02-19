package net.mysticcreations.example.config;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.FileTypes;
import net.mysticcreations.lib.config.fields.BooleanField;
import net.mysticcreations.lib.config.ConfigDefinition;
import net.mysticcreations.lib.config.fields.DoubleField;
import net.mysticcreations.lib.config.fields.IntegerField;
import net.mysticcreations.lib.config.annotations.CloseCat;
import net.mysticcreations.lib.config.annotations.Comment;
import net.mysticcreations.lib.config.annotations.OpenCat;

public class ExampleTomlConfig extends ConfigDefinition {
    public ExampleTomlConfig() {
        super(new ResourceLocation(MysticLib.MODID, "example_config_name"), FileTypes.TOML);
    }
    @OpenCat("NUMBERS")
    @Comment("These are example integer config values")
    public IntegerField dropPercentage = new IntegerField(25, 0, 100, 1);
    public IntegerField maxStack = new IntegerField(999, 0, 999, 1);

    public DoubleField someDouble = new DoubleField(16.5, 8.0, 255.0);
    @CloseCat

    @OpenCat("BOOLEANS")
    @Comment("Example boolean (true/false) values")
    public BooleanField enabledByDefault = new BooleanField(true);
    public BooleanField disabledByDefault = new BooleanField(false);
    @CloseCat

    //There's also StringField and LongField

    //Just to show how to use these in IF statements
    public void gettingAndSetting() {
        if (enabledByDefault.getValue()) {
            System.out.println("enabledByDefault is " + enabledByDefault);
        }
        someDouble.setValue(0.0);
        disabledByDefault.setValue(true);
        maxStack.setValue(512);
        System.out.println(String.format("maxStack has a value of %d and has a min-max of %d-%d", maxStack.getValue(), maxStack.min, maxStack.max));
    }
}

