package net.mysticcreations.example.config;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.FileTypes;
import net.mysticcreations.lib.config.fields.*;
import net.mysticcreations.lib.config.ConfigDefinition;

public class ExampleTomlConfig extends ConfigDefinition {
    public ExampleTomlConfig() {
        super(new ResourceLocation(MysticLib.MODID, "example_toml_config"), FileTypes.TOML);

        openCat("NUMBERS");

        openCat("NoDecimal");
        exampleInteger.addHeaderComment("Header comment 1").setInlineComment("Example comment for example_int");
        addField(exampleInteger);
        exampleInteger1.setInlineComment("Example comment for example_int_1");
        addField(exampleInteger1);
        exampleLong.setInlineComment("Example comment for example_long");
        addField(exampleLong);
        exampleLong1.setInlineComment("Example comment for example_long_1");
        addField(exampleLong1);
        closeCat();

        openCat("Decimal");
        exampleDouble.addHeaderComment("Header comment 2").setInlineComment("Example comment for example_double");
        addField(exampleDouble);
        exampleDouble1.setInlineComment("Example comment for example_double_1");
        addField(exampleDouble1);
        exampleFloat.setInlineComment("Example comment for example_float");
        addField(exampleFloat);
        exampleFloat1.setInlineComment("Example comment for example_float_1");
        addField(exampleFloat1);
        closeCat();

        closeCat();

        openCat("BOOL");

        openCat("True");
        exampleTrueBool.setInlineComment("True Bool");
        addField(exampleTrueBool);
        exampleTrueBool1.setInlineComment("True Bool 1");
        addField(exampleTrueBool1);
        closeCat();

        exampleFalseBool.setInlineComment("False Bool");
        addField(exampleFalseBool);
        exampleFalseBool1.setInlineComment("False Bool 1");
        addField(exampleFalseBool1);

        closeCat();

        exampleString.setInlineComment("It's a String");
        addField(exampleString);
    }
    public IntegerField exampleInteger = new IntegerField("example_int",25, 0, 100);
    public IntegerField exampleInteger1 = new IntegerField("example_int_1",25, 0, 100, 1);
    public LongField exampleLong = new LongField("example_long", 16L, 8L, 255L);
    public LongField exampleLong1 = new LongField("example_long_1",32L, 8L, 255L, 2L);

    public DoubleField exampleDouble = new DoubleField("example_double",16.0, 8.0, 255.0);
    public DoubleField exampleDouble1 = new DoubleField("example_double_1",32.0, 8.0, 255.0, 0.5);
    public FloatField exampleFloat = new FloatField("example_float",16.0f, 8.0f, 255.0f);
    public FloatField exampleFloat1 = new FloatField("example_float_1",32.0f, 8.0f, 255.0f, 0.5f);

    public BooleanField exampleTrueBool = new BooleanField("example_true_bool",true);
    public BooleanField exampleTrueBool1 = new BooleanField("example_true_bool_1",true);
    public BooleanField exampleFalseBool = new BooleanField("example_false_bool",false);
    public BooleanField exampleFalseBool1 = new BooleanField("example_false_bool_1",false);

    public StringField exampleString = new StringField("example_string", "hi_im_a_string");
}

