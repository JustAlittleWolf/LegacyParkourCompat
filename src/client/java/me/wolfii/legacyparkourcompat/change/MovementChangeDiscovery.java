package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/** Client-only discovery of this mod's annotated changes, in folders or packaged JARs. */
public final class MovementChangeDiscovery {
    private static final String PACKAGE = "me.wolfii.legacyparkourcompat.change";
    private static final String PATH = PACKAGE.replace('.', '/');

    private MovementChangeDiscovery() {
    }

    public static void register(MovementChangeRegistry registry) {
        var mod = FabricLoader.getInstance().getModContainer(LegacyParkourCompat.MOD_ID)
            .orElseThrow(() -> new IllegalStateException("Movement change mod container is missing"));
        Set<String> names = new TreeSet<>();
        // Fabric supplies every source-set root in development and an NIO JAR root in production.
        // Do not scan other mods, rely on optional JAR directory resources, or close Fabric's filesystem.
        for (Path root : mod.getRootPaths()) {
            Path directory = root.resolve(PATH);
            if (Files.notExists(directory)) {
                continue;
            }
            try (var files = Files.walk(directory)) {
                files.filter(Files::isRegularFile).forEach(file -> {
                    Path relative = directory.relativize(file);
                    if (relative.getNameCount() < 2
                        || !relative.getName(0).toString().matches("v[0-9]+(?:_[0-9]+)*")
                        || !file.getFileName().toString().endsWith(".class")) {
                        return;
                    }
                    String suffix = relative.toString().replace('\\', '.').replace('/', '.');
                    names.add(PACKAGE + '.' + suffix.substring(0, suffix.length() - ".class".length()));
                });
            } catch (IOException | UncheckedIOException exception) {
                throw new IllegalStateException("Cannot discover movement changes in " + directory, exception);
            }
        }

        int count = 0;
        for (String name : names) {
            try {
                Class<?> type = Class.forName(name, false, MovementChangeDiscovery.class.getClassLoader());
                MovementChange change = type.getDeclaredAnnotation(MovementChange.class);
                if (change == null) {
                    continue;
                }
                registerClass(registry, type, change.emulates());
                count++;
            } catch (ReflectiveOperationException | LinkageError exception) {
                throw new IllegalStateException("Cannot load movement change " + name, exception);
            }
        }
        if (count == 0) {
            throw new IllegalStateException("No annotated movement changes found in " + PACKAGE);
        }
        LegacyParkourCompat.LOGGER.debug("Discovered {} historical movement change classes", count);
    }

    private static void registerClass(MovementChangeRegistry registry, Class<?> type, ParkourVersion version)
        throws ReflectiveOperationException {
        if (!Modifier.isPublic(type.getModifiers()) || Modifier.isAbstract(type.getModifiers())
            || !VersionedMechanic.class.isAssignableFrom(type) || version.isCurrent()) {
            throw new IllegalStateException("Invalid historical movement change: " + type.getName());
        }
        Set<Class<?>> hooks = new TreeSet<>(Comparator.comparing(Class::getName));
        collectHooks(type, hooks);
        if (hooks.isEmpty()) {
            throw new IllegalStateException("Movement change has no annotated hooks: " + type.getName());
        }

        List<Method> factories = Arrays.stream(type.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(MovementChange.Factory.class)).toList();
        List<?> instances;
        if (factories.isEmpty()) {
            instances = List.of(type.getConstructor().newInstance());
        } else {
            if (factories.size() != 1) {
                throw new IllegalStateException("Movement change must have at most one factory: " + type.getName());
            }
            Method factory = factories.getFirst();
            if (!Modifier.isPublic(factory.getModifiers()) || !Modifier.isStatic(factory.getModifiers())
                || factory.getParameterCount() != 0 || !List.class.isAssignableFrom(factory.getReturnType())) {
                throw new IllegalStateException("Movement change factory must be public static List with no arguments: " + factory);
            }
            Object result = factory.invoke(null);
            if (!(result instanceof List<?> values) || values.isEmpty()) {
                throw new IllegalStateException("Movement change factory returned no variants: " + factory);
            }
            instances = values;
        }
        for (Object instance : instances) {
            if (instance == null || instance.getClass() != type) {
                throw new IllegalStateException("Movement change factory must return instances of " + type.getName());
            }
            for (Class<?> hook : hooks) {
                registerHook(registry, hook.asSubclass(VersionedMechanic.class), version, (VersionedMechanic) instance);
            }
        }
    }

    private static void collectHooks(Class<?> type, Set<Class<?>> hooks) {
        for (Class<?> hook : type.getInterfaces()) {
            if (hook.isAnnotationPresent(MechanicType.class)) {
                if (!VersionedMechanic.class.isAssignableFrom(hook)) {
                    throw new IllegalStateException("Movement hook does not extend VersionedMechanic: " + hook.getName());
                }
                hooks.add(hook);
            }
            collectHooks(hook, hooks);
        }
        if (type.getSuperclass() != null) {
            collectHooks(type.getSuperclass(), hooks);
        }
    }

    private static <T extends VersionedMechanic> void registerHook(
        MovementChangeRegistry registry, Class<T> hook, ParkourVersion version, VersionedMechanic instance
    ) {
        registry.register(hook, version, hook.cast(instance));
    }
}
