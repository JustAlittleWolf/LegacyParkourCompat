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

    private boolean wasUseDown;
    private Object mutedAudioCategory;
    private Object mutedAudioOption;
    private Number previousMasterVolume;

    private ReflectivePlayback() {
    }

    @Override
    public Path gameDirectory() {
        Object minecraft = minecraft();
        // MCP 1.8–1.12 calls this field gameDir.  Mojmap/Yarn clients use
        // gameDirectory (and the obfuscated name is retained for production
        // launches).  Missing gameDir used to make .playback crash 1.12.2.
        Object dir = first(minecraft, new String[]{"gameDirectory", "gameDir", "mcDataDir", "field_71412_D"});
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
        return number(player, new String[]{"x", "posX", "field_70165_t"}).doubleValue();
    }

    @Override
    public double playerY() {
        Object player = requirePlayer();
        Double value = invokeDouble(player, new String[]{"getY", "getPosY"});
        if (value != null) {
            return value.doubleValue();
        }
        return number(player, new String[]{"y", "posY", "field_70163_u"}).doubleValue();
    }

    @Override
    public double playerZ() {
        Object player = requirePlayer();
        Double value = invokeDouble(player, new String[]{"getZ", "getPosZ"});
        if (value != null) {
            return value.doubleValue();
        }
        return number(player, new String[]{"z", "posZ", "field_70161_v"}).doubleValue();
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
        boolean useHold = keyDown(new String[]{"keyUse", "keyBindUseItem"});
        boolean useClick = keyClicked(new String[]{"keyUse", "keyBindUseItem"}) || (useHold && !this.wasUseDown);
        this.wasUseDown = useHold;
        boolean sprint = keyDown(new String[]{"keySprint", "keyBindSprint"}) || boolInvoke(player, new String[]{"isSprinting"});
        if (input == null) {
            return TickButtons.pack(false, false, false, false, false, false, sprint, useHold, useClick);
        }
        float forward = impulse(input, new String[]{"forwardImpulse", "moveForward", "field_78900_b"});
        float left = impulse(input, new String[]{"leftImpulse", "moveStrafe", "field_78902_a"});
        boolean jump = bool(input, new String[]{"jumping", "jump", "field_78901_c"}) || keyPress(input, "jump");
        boolean sneak = bool(input, new String[]{"shiftKeyDown", "sneakKeyDown", "sneak", "shift", "field_78899_d"}) || keyPress(input, "shift");
        boolean forwardKey = bool(input, new String[]{"up"}) || forward > 0.0F || keyPress(input, "forward");
        boolean backKey = bool(input, new String[]{"down"}) || forward < 0.0F || keyPress(input, "backward");
        boolean leftKey = bool(input, new String[]{"left"}) || left > 0.0F || keyPress(input, "left");
        boolean rightKey = bool(input, new String[]{"right"}) || left < 0.0F || keyPress(input, "right");
        boolean sprintKey = sprint || keyPress(input, "sprint");
        return TickButtons.pack(forwardKey, backKey, leftKey, rightKey, jump, sneak, sprintKey, useHold, useClick);
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
            setBool(input, new String[]{"shiftKeyDown", "sneakKeyDown", "sneak", "field_78899_d"}, TickButtons.isSet(buttons, TickButtons.SNEAK));
            applyKeyPresses(input, buttons);
            applyMoveVector(input, buttons);
        }
        setKeyDown(new String[]{"keyUse", "keyBindUseItem"}, TickButtons.isSet(buttons, TickButtons.USE_HOLD));
        if (TickButtons.isSet(buttons, TickButtons.USE_CLICK)) {
            clickKey(new String[]{"keyUse", "keyBindUseItem"});
        }
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
        invokeAssignable(player, new String[]{"addChatMessage", "sendChatToPlayer", "sendSystemMessage", "sendMessage"}, text);
    }

    @Override
    public boolean sendChatMessage(String message) {
        Object player = player();
        if (player == null || message == null || message.trim().isEmpty()) {
            return false;
        }
        String trimmed = message.trim();
        if (invokeAssignable(player, new String[]{"chat", "sendChatMessage", "sendChat"}, trimmed)) {
            return true;
        }
        if (invokeAssignable(player, new String[]{"sendChat"}, trimmed, Boolean.FALSE)) {
            return true;
        }
        Object connection = first(player, new String[]{"connection", "sendQueue", "netHandler", "field_71174_a"});
        return invokeAssignable(connection, new String[]{"sendChat"}, trimmed);
    }

    @Override
    public boolean sendServerCommand(String command) {
        Object player = player();
        if (player == null || command == null || command.trim().isEmpty()) {
            return false;
        }
        String trimmed = command.trim();
        Object connection = first(player, new String[]{"connection", "sendQueue", "netHandler", "field_71174_a"});
        if (invokeAssignable(connection, new String[]{"sendCommand"}, trimmed)) {
            return true;
        }
        return invokeAssignable(player, new String[]{"chat", "sendChatMessage"}, "/" + trimmed);
    }

    @Override
    public boolean isConnected() {
        Object minecraft = minecraft();
        Object connection = first(minecraft, new String[]{"connection"});
        if (connection == null) {
            connection = invokeValue(minecraft, new String[]{"getConnection", "getConnectionState"});
        }
        if (connection != null) {
            return true;
        }
        Object player = player();
        return player != null && first(player, new String[]{"connection", "sendQueue", "netHandler", "field_71174_a"}) != null;
    }

    @Override
    public boolean isConnecting() {
        Object minecraft = minecraft();
        if (first(minecraft, new String[]{"pendingConnection"}) != null) {
            return true;
        }
        Object screen = currentScreen(minecraft);
        if (screen == null) {
            return false;
        }
        String name = screen.getClass().getName().toLowerCase(java.util.Locale.ROOT);
        return name.contains("connect") || name.contains("connecting");
    }

    @Override
    public boolean isReadyForAutoJoin() {
        Object minecraft = minecraft();
        if (first(minecraft, new String[]{"overlay"}) != null) {
            return false;
        }
        Object gui = first(minecraft, new String[]{"gui"});
        return gui == null || invokeValue(gui, new String[]{"overlay"}) == null;
    }

    @Override
    public boolean connectToServer(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }
        Object minecraft = minecraft();
        Object screen = currentScreen(minecraft);
        Object serverAddress = createServerAddress(address.trim());
        Object serverData = createServerData(address.trim());

        // 1.14+ has a static ConnectScreen helper.  Use assignable argument
        // matching because the final boolean/screen parameters changed over
        // time while the first three stayed stable.
        Class<?> connectScreen = loadClass("net.minecraft.client.multiplayer.ConnectScreen");
        if (connectScreen == null) {
            connectScreen = loadClass("net.minecraft.client.gui.screens.ConnectScreen");
        }
        if (connectScreen == null) {
            connectScreen = loadClass("net.minecraft.client.gui.screen.ConnectScreen");
        }
        if (serverAddress != null && connectScreen != null && (invokeStaticAssignable(connectScreen, "startConnecting",
            new Object[]{screen, minecraft, serverAddress, serverData, Boolean.FALSE, null})
            || invokeStaticAssignable(connectScreen, "startConnecting",
                new Object[]{screen, minecraft, serverAddress, serverData, Boolean.FALSE})
            || invokeStaticAssignable(connectScreen, "startConnecting",
                new Object[]{screen, minecraft, serverAddress, serverData}))) {
            return true;
        }

        if (connectScreen != null) {
            Object connecting = constructAssignable(connectScreen,
                new Object[]{screen, minecraft, serverData},
                new Object[]{screen, minecraft, host(address), Integer.valueOf(port(address))});
            if (connecting != null && setScreen(minecraft, connecting)) {
                return true;
            }
        }

        // Forge 1.8–1.12 connects by replacing the current GuiScreen with a
        // GuiConnecting instance.  Constructor signatures differ slightly,
        // therefore try each known shape without linking to old Minecraft
        // classes at compile time.
        Class<?> guiConnecting = loadClass("net.minecraft.client.multiplayer.GuiConnecting");
        if (guiConnecting == null) {
            guiConnecting = loadClass("net.minecraft.client.gui.GuiConnecting");
        }
        if (guiConnecting != null) {
            Object connecting = serverAddress == null
                ? constructAssignable(guiConnecting,
                    new Object[]{screen, minecraft, serverData},
                    new Object[]{screen, minecraft, host(address), Integer.valueOf(port(address))})
                : constructAssignable(guiConnecting,
                    new Object[]{screen, minecraft, serverAddress},
                    new Object[]{screen, minecraft, serverData},
                    new Object[]{screen, minecraft, host(address), Integer.valueOf(port(address))});
            if (connecting != null && setScreen(minecraft, connecting)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean selectParkourVersion(String version) {
        Class<?> controllerType = loadClass("me.wolfii.legacyparkourcompat.api.MovementController");
        if (controllerType == null) {
            return false;
        }
        Object controller = invokeStatic(controllerType, new String[]{"get"});
        if (controller == null) {
            throw new IllegalStateException("Legacy Parkour movement controller is not initialized");
        }
        if (!invokeAssignable(controller, new String[]{"select"}, version)) {
            throw new IllegalStateException("Cannot select Legacy Parkour version '" + version + "'");
        }
        return true;
    }

    @Override
    public void muteAudio() {
        Object minecraft = minecraft();
        Object options = options();
        if (options == null) {
            return;
        }
        // Older GameSettings exposes setSoundLevel(SoundCategory,float),
        // while modern Options uses setSoundCategoryVolume(SoundSource,float).
        Class<?> categoryType = loadClass("net.minecraft.client.audio.SoundCategory");
        if (categoryType == null) {
            categoryType = loadClass("net.minecraft.util.SoundCategory");
        }
        if (categoryType == null) {
            categoryType = loadClass("net.minecraft.sounds.SoundSource");
        }
        if (categoryType != null && categoryType.isEnum()) {
            Object master = enumConstant(categoryType, "MASTER");
            if (master != null) {
                Object volume = invokeValueAssignable(options, new String[]{"getSoundCategoryVolume", "getSoundLevel", "getVolume", "getSoundSourceVolume"}, master);
                if (volume instanceof Number) {
                    this.previousMasterVolume = (Number) volume;
                    this.mutedAudioCategory = master;
                }
                if (invokeAssignable(options, new String[]{"setSoundLevel", "setSoundCategoryVolume", "setVolume"}, master, Float.valueOf(0.0F))) {
                    return;
                }
                Object soundOption = invokeValueAssignable(options, new String[]{"getSoundSourceOptionInstance"}, master);
                if (soundOption != null) {
                    Object optionVolume = invokeValue(soundOption, new String[]{"get"});
                    if (optionVolume instanceof Number && invokeAssignable(soundOption, new String[]{"set"}, Double.valueOf(0.0D))) {
                        this.previousMasterVolume = (Number) optionVolume;
                        this.mutedAudioOption = soundOption;
                        this.mutedAudioCategory = master;
                        return;
                    }
                }
            }
        }
        // Last resort for 1.8–1.12's public masterVolume setting.  This still
        // takes effect on the next sound refresh and is safe when absent.
        Object oldVolume = first(options, new String[]{"masterVolume", "field_74315_B"});
        if (oldVolume instanceof Number) {
            this.previousMasterVolume = (Number) oldVolume;
        }
        if (oldVolume == null) {
            throw new IllegalStateException("Cannot mute Minecraft master audio");
        }
        setNumber(options, new String[]{"masterVolume", "field_74315_B"}, 0.0F);
        // Accessing the manager is intentionally best-effort: its method name
        // changed several times and a missing refresh must not stop playback.
        Object manager = first(minecraft, new String[]{"soundManager", "soundEngine", "field_90993_d"});
        if (manager != null) {
            invoke(manager, new String[]{"reload", "resume"}, new Class[0], new Object[0]);
        }
    }

    @Override
    public void restoreAudio() {
        if (this.previousMasterVolume == null) {
            return;
        }
        Object options = options();
        if (this.mutedAudioOption != null) {
            if (!invokeAssignable(this.mutedAudioOption, new String[]{"set"}, this.previousMasterVolume)) {
                throw new IllegalStateException("Cannot restore Minecraft master audio");
            }
            saveRestoredAudio(options);
            this.previousMasterVolume = null;
            this.mutedAudioCategory = null;
            this.mutedAudioOption = null;
            return;
        }
        if (this.mutedAudioCategory != null && invokeAssignable(options,
            new String[]{"setSoundLevel", "setSoundCategoryVolume", "setVolume"},
            this.mutedAudioCategory, Float.valueOf(this.previousMasterVolume.floatValue()))) {
            saveRestoredAudio(options);
            this.previousMasterVolume = null;
            this.mutedAudioCategory = null;
            return;
        }
        setNumber(options, new String[]{"masterVolume", "field_74315_B"}, this.previousMasterVolume.floatValue());
        Object minecraft = minecraft();
        Object manager = first(minecraft, new String[]{"soundManager", "soundEngine", "field_90993_d"});
        if (manager != null) {
            invoke(manager, new String[]{"reload", "resume"}, new Class[0], new Object[0]);
        }
        saveRestoredAudio(options);
        this.previousMasterVolume = null;
        this.mutedAudioCategory = null;
    }

    private static void saveRestoredAudio(Object options) {
        if (!invoke(options, new String[]{"save", "saveOptions"}, new Class[0], new Object[0])) {
            throw new IllegalStateException("Cannot save restored Minecraft master audio");
        }
    }

    @Override
    public void requestShutdown() {
        Object minecraft = minecraft();
        if (!invoke(minecraft, new String[]{"stop", "shutdown", "destroy"}, new Class[0], new Object[0])) {
            // Some old clients only expose a boolean running flag.  Do not
            // throw during a completed run if it is not available.
            setBool(minecraft, new String[]{"running", "field_71425_J"}, false);
        }
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

    private static Object currentScreen(Object minecraft) {
        Object screen = first(minecraft, new String[]{"screen", "currentScreen", "field_71462_r"});
        if (screen != null) {
            return screen;
        }
        Object gui = first(minecraft, new String[]{"gui"});
        return invokeValue(gui, new String[]{"screen"});
    }

    private boolean keyDown(String[] names) {
        Object binding = first(options(), names);
        if (binding == null) {
            return false;
        }
        Boolean down = invokeBoolean(binding, new String[]{"isDown", "isKeyDown"});
        if (down != null) {
            return down.booleanValue();
        }
        Object pressed = first(binding, new String[]{"isDown", "down", "pressed", "field_74513_e"});
        return pressed instanceof Boolean && ((Boolean) pressed).booleanValue();
    }

    private boolean keyClicked(String[] names) {
        Object binding = first(options(), names);
        if (binding == null) {
            return false;
        }
        Object clicks = first(binding, new String[]{"clickCount", "pressTime", "timesPressed", "field_74511_a"});
        return clicks instanceof Number && ((Number) clicks).intValue() > 0;
    }

    private void setKeyDown(String[] names, boolean down) {
        Object binding = first(options(), names);
        if (binding == null) {
            return;
        }
        setBool(binding, new String[]{"isDown", "down", "pressed", "field_74513_e"}, down);
    }

    private void clickKey(String[] names) {
        Object binding = first(options(), names);
        if (binding == null) {
            return;
        }
        Object clicks = first(binding, new String[]{"clickCount", "pressTime", "timesPressed", "field_74511_a"});
        int next = clicks instanceof Number ? Math.max(1, ((Number) clicks).intValue()) : 1;
        setNumber(binding, new String[]{"clickCount", "pressTime", "timesPressed", "field_74511_a"}, next);
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

    private void applyMoveVector(Object input, int buttons) {
        if (first(input, new String[]{"moveVector"}) == null) {
            return;
        }
        Class<?> vectorType = loadClass("net.minecraft.world.phys.Vec2");
        if (vectorType == null) {
            throw new IllegalStateException("Minecraft input vector type is missing");
        }
        float forward = TickButtons.isSet(buttons, TickButtons.FORWARD) ? 1.0F
            : TickButtons.isSet(buttons, TickButtons.BACK) ? -1.0F : 0.0F;
        float left = TickButtons.isSet(buttons, TickButtons.LEFT) ? 1.0F
            : TickButtons.isSet(buttons, TickButtons.RIGHT) ? -1.0F : 0.0F;
        try {
            Object vector = vectorType.getConstructor(float.class, float.class)
                .newInstance(Float.valueOf(left), Float.valueOf(forward));
            Object normalized = vectorType.getMethod("normalized").invoke(vector);
            if (!write(input, "moveVector", normalized)) {
                throw new IllegalStateException("Cannot set Minecraft input vector");
            }
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Cannot apply Minecraft input vector", exception);
        }
    }

    private static boolean invokeAssignable(Object target, String[] names, Object... args) {
        if (target == null) {
            return false;
        }
        for (int nameIndex = 0; nameIndex < names.length; nameIndex++) {
            Method method = findCompatibleMethod(target.getClass(), names[nameIndex], args, false);
            if (method == null) {
                continue;
            }
            try {
                method.invoke(target, coerceArguments(method.getParameterTypes(), args));
                return true;
            } catch (ReflectiveOperationException ignored) {
                // try the next version-specific method shape
            }
        }
        return false;
    }

    private static boolean invokeStaticAssignable(Class<?> type, String name, Object... args) {
        Method method = findCompatibleMethod(type, name, args, true);
        if (method == null) {
            return false;
        }
        try {
            method.invoke(null, coerceArguments(method.getParameterTypes(), args));
            return true;
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private static boolean invokeAssignable(Object target, String[] names, Object first, Object second) {
        return invokeAssignable(target, names, new Object[]{first, second});
    }

    private static boolean invokeAssignable(Object target, String[] names, Object argument) {
        return invokeAssignable(target, names, new Object[]{argument});
    }

    private static Method findCompatibleMethod(Class<?> type, String name, Object[] args, boolean staticOnly) {
        Method[] methods = type.getMethods();
        for (int index = 0; index < methods.length; index++) {
            Method method = methods[index];
            if (!method.getName().equals(name) || method.getParameterTypes().length != args.length) {
                continue;
            }
            if (staticOnly && (method.getModifiers() & java.lang.reflect.Modifier.STATIC) == 0) {
                continue;
            }
            if (!staticOnly && (method.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0) {
                continue;
            }
            if (parametersCompatible(method.getParameterTypes(), args)) {
                return method;
            }
        }
        return null;
    }

    private static boolean parametersCompatible(Class<?>[] types, Object[] args) {
        for (int index = 0; index < types.length; index++) {
            if (args[index] == null) {
                if (types[index].isPrimitive()) {
                    return false;
                }
                continue;
            }
            Class<?> actual = args[index].getClass();
            if (types[index].isPrimitive()) {
                if (!primitiveWrapper(types[index]).isAssignableFrom(actual)) {
                    return false;
                }
            } else if (!types[index].isAssignableFrom(actual)) {
                return false;
            }
        }
        return true;
    }

    private static Class<?> primitiveWrapper(Class<?> primitive) {
        if (primitive == boolean.class) return Boolean.class;
        if (primitive == byte.class) return Byte.class;
        if (primitive == short.class) return Short.class;
        if (primitive == int.class) return Integer.class;
        if (primitive == long.class) return Long.class;
        if (primitive == float.class) return Float.class;
        if (primitive == double.class) return Double.class;
        if (primitive == char.class) return Character.class;
        return primitive;
    }

    private static Object[] coerceArguments(Class<?>[] types, Object[] args) {
        Object[] result = new Object[args.length];
        for (int index = 0; index < args.length; index++) {
            result[index] = coerce(types[index], args[index]);
        }
        return result;
    }

    private static Object createServerAddress(String address) {
        Class<?> type = loadClass("net.minecraft.client.multiplayer.ServerAddress");
        if (type == null) {
            type = loadClass("net.minecraft.client.multiplayer.resolver.ServerAddress");
        }
        if (type == null) {
            return null;
        }
        Object parsed = invokeStaticValue(type, "parseString", address);
        if (parsed == null) {
            parsed = invokeStaticValue(type, "fromString", address);
        }
        if (parsed == null) {
            parsed = invokeStaticValue(type, "fromIp", address);
        }
        if (parsed == null) {
            parsed = constructAssignable(type, new Object[]{address});
        }
        return parsed;
    }

    private static Object createServerData(String address) {
        Class<?> type = loadClass("net.minecraft.client.multiplayer.ServerData");
        if (type == null) {
            return null;
        }
        Class<?> serverType = loadClass("net.minecraft.client.multiplayer.ServerData$Type");
        Object otherType = serverType == null ? null : enumConstant(serverType, "OTHER");
        return constructAssignable(type,
            new Object[]{"Legacy Parkour Gym", address, otherType},
            new Object[]{"Legacy Parkour Gym", address, Boolean.FALSE},
            new Object[]{"Legacy Parkour Gym", address});
    }

    private static Object invokeStaticValue(Class<?> type, String name, Object argument) {
        Method method = findCompatibleMethod(type, name, new Object[]{argument}, true);
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(null, coerceArguments(method.getParameterTypes(), new Object[]{argument}));
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static Object constructAssignable(Class<?> type, Object[]... candidates) {
        Constructor<?>[] constructors = type.getConstructors();
        for (int candidateIndex = 0; candidateIndex < candidates.length; candidateIndex++) {
            Object[] candidate = candidates[candidateIndex];
            for (int constructorIndex = 0; constructorIndex < constructors.length; constructorIndex++) {
                Constructor<?> constructor = constructors[constructorIndex];
                if (constructor.getParameterTypes().length != candidate.length
                    || !parametersCompatible(constructor.getParameterTypes(), candidate)) {
                    continue;
                }
                try {
                    return constructor.newInstance(coerceArguments(constructor.getParameterTypes(), candidate));
                } catch (ReflectiveOperationException ignored) {
                    // try the next constructor
                }
            }
        }
        return null;
    }

    private static boolean setScreen(Object minecraft, Object screen) {
        return invokeAssignable(minecraft, new String[]{"setScreen", "displayGuiScreen", "func_147108_a"}, screen);
    }

    private static Class<?> loadClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    private static Object enumConstant(Class<?> type, String name) {
        Object[] constants = type.getEnumConstants();
        if (constants == null) {
            return null;
        }
        for (int index = 0; index < constants.length; index++) {
            if (name.equalsIgnoreCase(((Enum<?>) constants[index]).name())) {
                return constants[index];
            }
        }
        return null;
    }

    private static String host(String address) {
        String value = address.trim();
        if (value.startsWith("[")) {
            int end = value.indexOf(']');
            return end > 0 ? value.substring(1, end) : value;
        }
        int colon = value.lastIndexOf(':');
        return colon > 0 && value.indexOf(':') == colon ? value.substring(0, colon) : value;
    }

    private static int port(String address) {
        String value = address.trim();
        int colon = value.lastIndexOf(':');
        if (colon < 0 || colon == value.length() - 1 || value.indexOf(':') != colon) {
            return 25565;
        }
        try {
            return Integer.parseInt(value.substring(colon + 1));
        } catch (NumberFormatException ignored) {
            return 25565;
        }
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

    private static Object invokeValue(Object target, String[] names) {
        for (int index = 0; index < names.length; index++) {
            try {
                return target.getClass().getMethod(names[index]).invoke(target);
            } catch (ReflectiveOperationException ignored) {
                // try the next accessor
            }
        }
        return null;
    }

    private static Object invokeValueAssignable(Object target, String[] names, Object argument) {
        if (target == null) {
            return null;
        }
        for (int index = 0; index < names.length; index++) {
            Method method = findCompatibleMethod(target.getClass(), names[index], new Object[]{argument}, false);
            if (method == null) {
                continue;
            }
            try {
                return method.invoke(target, coerceArguments(method.getParameterTypes(), new Object[]{argument}));
            } catch (ReflectiveOperationException ignored) {
                // try the next version-specific accessor
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

    private static void setNumber(Object target, String[] names, int value) {
        for (int index = 0; index < names.length; index++) {
            if (write(target, names[index], Integer.valueOf(value))) {
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
        if (type == int.class || type == Integer.class) {
            return Integer.valueOf(number.intValue());
        }
        if (type == long.class || type == Long.class) {
            return Long.valueOf(number.longValue());
        }
        return value;
    }
}
