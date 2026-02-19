package net.mysticcreations.example.config;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.FileTypes;
import net.mysticcreations.lib.config.fields.BooleanField;
import net.mysticcreations.lib.config.ConfigDefinition;
import net.mysticcreations.lib.config.fields.DoubleField;
import net.mysticcreations.lib.config.fields.IntegerField;

public class ExampleTomlConfig extends ConfigDefinition {
    public ExampleTomlConfig() {
        super(new ResourceLocation(MysticLib.MODID, "example_toml_config"), FileTypes.TOML);

        openCat("NUMBERS");

        dropPercentage.setInlineComment("skibidi toilet");
        dropPercentage.addHeaderComment("67");
        dropPercentage.addHeaderComment("sigma boy");
        addField(dropPercentage);
        addField(maxStack);

        addField(someDouble);

        closeCat();

        openCat("BOOLEANS");

        addField(enabledByDefault);
        addField(disabledByDefault);

        closeCat();


    }
    public IntegerField dropPercentage = new IntegerField("drop_percentage",25, 0, 100, 1);
    public IntegerField maxStack = new IntegerField("max_stack",999, 0, 999, 1);

    public DoubleField someDouble = new DoubleField("some_double",16.5, 8.0, 255.0);

    public BooleanField enabledByDefault = new BooleanField("enabled_by_default",true);
    public BooleanField disabledByDefault = new BooleanField("disabled_by_default",false);

    //There's also StringField and LongField

    //Just to show how to use these in IF statements
    public void gettingAndSetting() {
        if (enabledByDefault.getValue()) {
            System.out.println("enabledByDefault is " + enabledByDefault);
        }
    }
}

