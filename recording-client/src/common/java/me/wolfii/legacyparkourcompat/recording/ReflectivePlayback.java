package me.wolfii.legacyparkourcompat.recording;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;

/**
 * Minecraft access that works from 1.8.9 Forge through modern Fabric by using
 * the simulation player/input fields, never the camera.
 */
public final class ReflectivePlayback implements MinecraftPlayback {
    public static final ReflectivePlayback INSTANCE = new ReflectivePlayback();

    private ReflectivePlayback() {
    }

    @Override
    public Path gameDirectory() {
        Object minecraft = minecraft();
        Object dir = first(minecraft, new String[]{"gameDirectory", "mcDataDir", "field_71412_D"});
        if (dir instanceof Path) {
            return (Path) dir;
        }
        if (dir instanceof File) {
            return ((File) dir).toPath();
        }
        throw new IllegalStateException("Cannot resolve Minecraft directory");
    }

    @Override
    public double playerX() {
        Object player = requirePlayer();
        Double value = invokeDouble(player, new String[]{"getX", "getPosX"});
        if (value != null) {
            return value.doubleValue();
        }
        return number(player, new String[]{"posX", "field_70165_t"}).doubleValue();
    }

    @Override
    public double playerY() {
        Object player = requirePlayer();
        Double value = invokeDouble(player, new String[]{"getY", "getPosY"});
        if (value != null) {
            return value.doubleValue();
        }
        return number(player, new String[]{"posY", "field_70163_u"}).doubleValue();
    }

    @Override
    public double playerZ() {
        Object player = requirePlayer();
        Double value = invokeDouble(player, new String[]{"getZ", "getPosZ"});
        if (value != null) {
            return value.doubleValue();
        }
        return number(player, new String[]{"posZ", "field_70161_v"}).doubleValue();
    }

    @Override
    public float playerYaw() {
        Object player = requirePlayer();
        Float value = invokeFloat(player, new String[]{"getYRot", "getYaw"});
        if (value != null) {
            return value.floatValue();
        }
        return number(player, new String[]{"yRot", "rotationYaw", "field_70177_z"}).floatValue();
    }

    @Override
    public float playerPitch() {
        Object player = requirePlayer();
        Float value = invokeFloat(player, new String[]{"getXRot", "getPitch"});
        if (value != null) {
            return value.floatValue();
        }
        return number(player, new String[]{"xRot", "rotationPitch", "field_70125_A"}).floatValue();
    }

    @Override
    public void teleport(double x, double y, double z, float yaw, float pitch) {
        Object player = requirePlayer();
        if (invoke(player, new String[]{"absSnapTo", "snapTo", "moveTo", "setPosAndOldPos", "setPositionAndRotation"},
            new Class[]{double.class, double.class, double.class, float.class, float.class},
            new Object[]{Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), Float.valueOf(yaw), Float.valueOf(pitch)})) {
            zeroMotion(player);
            return;
        }
        invoke(player, new String[]{"setPos", "setPosition"},
            new Class[]{double.class, double.class, double.class},
            new Object[]{Double.valueOf(x), Double.valueOf(y), Double.valueOf(z)});
        applyFacing(yaw, pitch);
        zeroMotion(player);
    }

    @Override
    public int currentButtons() {
        Object player = requirePlayer();
        Object input = first(player, new String[]{"input", "movementInput", "field_71158_b"});
        boolean use = keyDown(new String[]{"keyUse", "keyBindUseItem"});
        boolean sprint = keyDown(new String[]{"keySprint", "keyBindSprint"}) || boolInvoke(player, new String[]{"isSprinting"});
        if (input == null) {
            return TickButtons.pack(false, false, false, false, false, false, sprint, use);
        }
        float forward = impulse(input, new String[]{"forwardImpulse", "moveForward", "field_78900_b"});
        float left = impulse(input, new String[]{"leftImpulse", "moveStrafe", "field_78902_a"});
        boolean jump = bool(input, new String[]{"jumping", "jump", "field_78901_c"}) || keyPress(input, "jump");
        boolean sneak = bool(input, new String[]{"shiftKeyDown", "sneak", "shift", "field_78899_d"}) || keyPress(input, "shift");
        boolean forwardKey = bool(input, new String[]{"up"}) || forward > 0.0F || keyPress(input, "forward");
        boolean backKey = bool(input, new String[]{"down"}) || forward < 0.0F || keyPress(input, "backward");
        boolean leftKey = bool(input, new String[]{"left"}) || left > 0.0F || keyPress(input, "left");
        boolean rightKey = bool(input, new String[]{"right"}) || left < 0.0F || keyPress(input, "right");
        boolean sprintKey = sprint || keyPress(input, "sprint");
        return TickButtons.pack(forwardKey, backKey, leftKey, rightKey, jump, sneak, sprintKey, use);
    }

    @Override
    public void applyButtons(int buttons) {
        Object player = requirePlayer();
        Object input = first(player, new String[]{"input", "movementInput", "field_71158_b"});
        if (input != null) {
            setNumber(input, new String[]{"forwardImpulse", "moveForward", "field_78900_b"},
                TickButtons.isSet(buttons, TickButtons.FORWARD) ? 1.0F : TickButtons.isSet(buttons, TickButtons.BACK) ? -1.0F : 0.0F);
            setNumber(input, new String[]{"leftImpulse", "moveStrafe", "field_78902_a"},
                TickButtons.isSet(buttons, TickButtons.LEFT) ? 1.0F : TickButtons.isSet(buttons, TickButtons.RIGHT) ? -1.0F : 0.0F);
            setBool(input, new String[]{"up"}, TickButtons.isSet(buttons, TickButtons.FORWARD));
            setBool(input, new String[]{"down"}, TickButtons.isSet(buttons, TickButtons.BACK));
            setBool(input, new String[]{"left"}, TickButtons.isSet(buttons, TickButtons.LEFT));
            setBool(input, new String[]{"right"}, TickButtons.isSet(buttons, TickButtons.RIGHT));
            setBool(input, new String[]{"jumping", "jump", "field_78901_c"}, TickButtons.isSet(buttons, TickButtons.JUMP));
            setBool(input, new String[]{"shiftKeyDown", "sneak", "field_78899_d"}, TickButtons.isSet(buttons, TickButtons.SNEAK));
            applyKeyPresses(input, buttons);
        }
        setKeyDown(new String[]{"keyUse", "keyBindUseItem"}, TickButtons.isSet(buttons, TickButtons.USE));
        setKeyDown(new String[]{"keySprint", "keyBindSprint"}, TickButtons.isSet(buttons, TickButtons.SPRINT));
    }

    @Override
    public void applyFacing(float yaw, float pitch) {
        Object player = requirePlayer();
        if (!invoke(player, new String[]{"setYRot"}, new Class[]{float.class}, new Object[]{Float.valueOf(yaw)})) {
            setNumber(player, new String[]{"yRot", "rotationYaw", "field_70177_z"}, yaw);
        }
        if (!invoke(player, new String[]{"setXRot"}, new Class[]{float.class}, new Object[]{Float.valueOf(pitch)})) {
            setNumber(player, new String[]{"xRot", "rotationPitch", "field_70125_A"}, pitch);
        }
        setNumber(player, new String[]{"yRotO", "prevRotationYaw", "field_70126_B"}, yaw);
        setNumber(player, new String[]{"xRotO", "prevRotationPitch", "field_70127_C"}, pitch);
    }

    @Override
    public void sendGameMessage(String message) {
        Object player = player();
        if (player == null) {
            return;
        }
        Object text = literal(message);
        if (invoke(player, new String[]{"displayClientMessage", "sendSystemMessage"},
            new Class[]{text.getClass(), boolean.class}, new Object[]{text, Boolean.FALSE})) {
            return;
        }
        invoke(player, new String[]{"addChatMessage", "sendChatToPlayer"},
            new Class[]{text.getClass()}, new Object[]{text});
    }

    private Object minecraft() {
        Class<?> type;
        try {
            type = Class.forName("net.minecraft.client.Minecraft");
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("Minecraft client is missing", exception);
        }
        Object instance = invokeStatic(type, new String[]{"getInstance", "getMinecraft"});
        if (instance == null) {
            throw new IllegalStateException("Minecraft has not started");
        }
        return instance;
    }

    private Object player() {
        return first(minecraft(), new String[]{"player", "thePlayer", "field_71439_g"});
    }

    private Object requirePlayer() {
        Object player = player();
        if (player == null) {
            throw new IllegalStateException("No local player");
        }
        return player;
    }

    private Object options() {
        return first(minecraft(), new String[]{"options", "gameSettings", "field_71474_y"});
    }

    private boolean keyDown(String[] names) {
        Object binding = first(options(), names);
        if (binding == null) {
            return false;
        }
        Boolean down = invokeBoolean(binding, new String[]{"isDown", "isKeyDown", "isPressed"});
        if (down != null) {
            return down.booleanValue();
        }
        Object pressed = first(binding, new String[]{"isDown", "down", "pressed", "field_74513_e"});
        return pressed instanceof Boolean && ((Boolean) pressed).booleanValue();
    }

    private void setKeyDown(String[] names, boolean down) {
        Object binding = first(options(), names);
        if (binding == null) {
            return;
        }
        setBool(binding, new String[]{"isDown", "down", "pressed", "field_74513_e"}, down);
    }

    private void applyKeyPresses(Object input, int buttons) {
        try {
            Field field = input.getClass().getField("keyPresses");
            Class<?> type = field.getType();
            Constructor<?> constructor = type.getConstructor(
                boolean.class, boolean.class, boolean.class, boolean.class,
                boolean.class, boolean.class, boolean.class
            );
            Object presses = constructor.newInstance(
                Boolean.valueOf(TickButtons.isSet(buttons, TickButtons.FORWARD)),
                Boolean.valueOf(TickButtons.isSet(buttons, TickButtons.BACK)),
                Boolean.valueOf(TickButtons.isSet(buttons, TickButtons.LEFT)),
                Boolean.valueOf(TickButtons.isSet(buttons, TickButtons.RIGHT)),
                Boolean.valueOf(TickButtons.isSet(buttons, TickButtons.JUMP)),
                Boolean.valueOf(TickButtons.isSet(buttons, TickButtons.SNEAK)),
                Boolean.valueOf(TickButtons.isSet(buttons, TickButtons.SPRINT))
            );
            field.set(input, presses);
        } catch (ReflectiveOperationException ignored) {
            // older inputs have no keyPresses record
        }
    }

    private boolean keyPress(Object input, String accessor) {
        try {
            Field field = input.getClass().getField("keyPresses");
            Object presses = field.get(input);
            if (presses == null) {
                return false;
            }
            Method method = presses.getClass().getMethod(accessor);
            return Boolean.TRUE.equals(method.invoke(presses));
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private void zeroMotion(Object player) {
        invoke(player, new String[]{"setDeltaMovement", "setMotion", "setVelocity"},
            new Class[]{double.class, double.class, double.class},
            new Object[]{Double.valueOf(0.0), Double.valueOf(0.0), Double.valueOf(0.0)});
        setNumber(player, new String[]{"motionX", "field_70159_w"}, 0.0);
        setNumber(player, new String[]{"motionY", "field_70181_x"}, 0.0);
        setNumber(player, new String[]{"motionZ", "field_70179_y"}, 0.0);
    }

    private Object literal(String message) {
        try {
            Class<?> component = Class.forName("net.minecraft.network.chat.Component");
            Method literal = component.getMethod("literal", String.class);
            return literal.invoke(null, message);
        } catch (ReflectiveOperationException ignored) {
            // 1.8 chat components
        }
        String[] names = {
            "net.minecraft.network.chat.TextComponent",
            "net.minecraft.util.text.StringTextComponent",
            "net.minecraft.util.text.TextComponentString",
            "net.minecraft.util.ChatComponentText"
        };
        for (int index = 0; index < names.length; index++) {
            try {
                return Class.forName(names[index]).getConstructor(String.class).newInstance(message);
            } catch (ReflectiveOperationException ignored) {
                // try the next class
            }
        }
        return message;
    }

    private static Object invokeStatic(Class<?> type, String[] names) {
        for (int index = 0; index < names.length; index++) {
            try {
                Method method = type.getMethod(names[index]);
                return method.invoke(null);
            } catch (ReflectiveOperationException ignored) {
                // try the next name
            }
        }
        return null;
    }

    private static boolean invoke(Object target, String[] names, Class<?>[] types, Object[] args) {
        for (int index = 0; index < names.length; index++) {
            try {
                Method method = target.getClass().getMethod(names[index], types);
                method.invoke(target, args);
                return true;
            } catch (ReflectiveOperationException ignored) {
                // try the next name
            }
        }
        return false;
    }

    private static Double invokeDouble(Object target, String[] names) {
        for (int index = 0; index < names.length; index++) {
            try {
                Object value = target.getClass().getMethod(names[index]).invoke(target);
                if (value instanceof Number) {
                    return Double.valueOf(((Number) value).doubleValue());
                }
            } catch (ReflectiveOperationException ignored) {
                // try the next name
            }
        }
        return null;
    }

    private static Float invokeFloat(Object target, String[] names) {
        for (int index = 0; index < names.length; index++) {
            try {
                Object value = target.getClass().getMethod(names[index]).invoke(target);
                if (value instanceof Number) {
                    return Float.valueOf(((Number) value).floatValue());
                }
            } catch (ReflectiveOperationException ignored) {
                // try the next name
            }
        }
        return null;
    }

    private static Boolean invokeBoolean(Object target, String[] names) {
        for (int index = 0; index < names.length; index++) {
            try {
                Object value = target.getClass().getMethod(names[index]).invoke(target);
                if (value instanceof Boolean) {
                    return (Boolean) value;
                }
            } catch (ReflectiveOperationException ignored) {
                // try the next name
            }
        }
        return null;
    }

    private static boolean boolInvoke(Object target, String[] names) {
        Boolean value = invokeBoolean(target, names);
        return value != null && value.booleanValue();
    }

    private static Object first(Object target, String[] names) {
        if (target == null) {
            return null;
        }
        for (int index = 0; index < names.length; index++) {
            try {
                Field field = target.getClass().getField(names[index]);
                Object value = field.get(target);
                if (value != null) {
                    return value;
                }
            } catch (ReflectiveOperationException ignored) {
                // try the next name
            }
        }
        Class<?> type = target.getClass();
        while (type != null && type != Object.class) {
            for (int index = 0; index < names.length; index++) {
                try {
                    Field field = type.getDeclaredField(names[index]);
                    field.setAccessible(true);
                    Object value = field.get(target);
                    if (value != null) {
                        return value;
                    }
                } catch (ReflectiveOperationException ignored) {
                    // try the next name
                }
            }
            type = type.getSuperclass();
        }
        return null;
    }

    private static Number number(Object target, String[] names) {
        Object value = first(target, names);
        if (value instanceof Number) {
            return (Number) value;
        }
        throw new IllegalStateException("Missing numeric field");
    }

    private static float impulse(Object target, String[] names) {
        Object value = first(target, names);
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        return 0.0F;
    }

    private static boolean bool(Object target, String[] names) {
        Object value = first(target, names);
        return value instanceof Boolean && ((Boolean) value).booleanValue();
    }

    private static void setBool(Object target, String[] names, boolean value) {
        for (int index = 0; index < names.length; index++) {
            if (write(target, names[index], Boolean.valueOf(value))) {
                return;
            }
        }
    }

    private static void setNumber(Object target, String[] names, float value) {
        for (int index = 0; index < names.length; index++) {
            if (write(target, names[index], Float.valueOf(value))) {
                return;
            }
        }
    }

    private static void setNumber(Object target, String[] names, double value) {
        for (int index = 0; index < names.length; index++) {
            if (write(target, names[index], Double.valueOf(value))) {
                return;
            }
        }
    }

    private static boolean write(Object target, String name, Object value) {
        try {
            Field field = target.getClass().getField(name);
            field.set(target, coerce(field.getType(), value));
            return true;
        } catch (ReflectiveOperationException ignored) {
            // try declared fields
        }
        Class<?> type = target.getClass();
        while (type != null && type != Object.class) {
            try {
                Field field = type.getDeclaredField(name);
                field.setAccessible(true);
                field.set(target, coerce(field.getType(), value));
                return true;
            } catch (ReflectiveOperationException ignored) {
                type = type.getSuperclass();
            }
        }
        return false;
    }

    private static Object coerce(Class<?> type, Object value) {
        if (!(value instanceof Number) || type == value.getClass()) {
            return value;
        }
        Number number = (Number) value;
        if (type == float.class || type == Float.class) {
            return Float.valueOf(number.floatValue());
        }
        if (type == double.class || type == Double.class) {
            return Double.valueOf(number.doubleValue());
        }
        return value;
    }
}
