package me.wolfii.legacyparkourcompat.recording;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/** Version-independent environment saved with the input and expected positions. */
public final class RecordingSetup {
    private RecordingSetup() { }

    public static JsonObject defaults() {
        JsonObject setup = new JsonObject();
        setup.addProperty("gameMode", "survival");
        setup.addProperty("flying", false);
        setup.add("equipment", new JsonObject());
        setup.add("potionEffects", new JsonArray());
        return setup;
    }

    public static JsonObject normalize(JsonObject source) {
        JsonObject setup = defaults();
        if (source == null) return setup;
        for (java.util.Map.Entry<String, com.google.gson.JsonElement> entry : source.entrySet()) {
            setup.add(entry.getKey(), entry.getValue().deepCopy());
        }
        String mode = setup.get("gameMode").getAsString();
        if (!"survival".equals(mode) && !"creative".equals(mode))
            throw new IllegalArgumentException("Recording gameMode must be survival or creative");
        if (!"creative".equals(mode) && setup.get("flying").getAsBoolean())
            throw new IllegalArgumentException("Flying requires Creative mode");
        if (!setup.get("equipment").isJsonObject() || !setup.get("potionEffects").isJsonArray())
            throw new IllegalArgumentException("Invalid recording equipment or potionEffects");
        return setup;
    }
}
