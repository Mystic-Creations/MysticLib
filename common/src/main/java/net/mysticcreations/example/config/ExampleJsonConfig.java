package net.mysticcreations.example.config;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.ConfigFileType;
import net.mysticcreations.lib.config.specification.BooleanField;
import net.mysticcreations.lib.config.specification.ConfigSpecification;
import net.mysticcreations.lib.config.specification.DoubleField;
import net.mysticcreations.lib.config.specification.IntegerField;
import net.mysticcreations.lib.config.specification.annotations.CloseCat;
import net.mysticcreations.lib.config.specification.annotations.OpenCat;

public class ExampleJsonConfig extends ConfigSpecification {
    public ExampleJsonConfig() {
        super(new ResourceLocation(MysticLib.MODID, "example_config_name"), ConfigFileType.JSON);
    }

    @OpenCat("NUMBERS")
    //@Comment("These are example integer config values") COMMENTS ARE NOT AVAILABLE FOR JSON!
    public IntegerField dropPercentage = new IntegerField(25, 0, 100, 1);
    public IntegerField maxStack = new IntegerField(999, 0, 999, 1);

    public DoubleField someDouble = new DoubleField(16.5, 8.0, 255.0);
    @CloseCat

    @OpenCat("BOOLEANS")
    //@Comment("Example boolean (true/false) values") COMMENTS ARE NOT AVAILABLE FOR JSON!
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
