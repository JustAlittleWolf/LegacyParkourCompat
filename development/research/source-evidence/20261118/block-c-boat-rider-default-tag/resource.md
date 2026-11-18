# Successor evidence: 1.20.4 boat rider tag bytes

Supersedes the artifact-availability blocker in `report.md` with the recovered local archive evidence. The original note remains unchanged. Read-only inspection only; no repository source was edited.

## Archive identity

Recovered local archive: `C:\Users\Wolfi\.codex\visualizations\2026\11\18\01a1f01a-11a3-7520-b7e4-32fb64b744bd\source-1.20.4\client.jar`.

- File size: 24,445,539 bytes.
- SHA-256: `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` (matches 1.20.4 ready/provenance artifact identity).
- SHA-1: `fd19469fed4a4b4c15b2d5133985f0e3e7816a8a` (the recovered expected local archive SHA-1).
- Official metadata SHA-1 supplied with this recovery: `2c592ccd71ca2d5dadebeb6543ad6b18e6f43305`.

## Exact tag resource bytes and membership

A ZIP entry inventory shows there is no ordinary default resource at `data/minecraft/tags/entity_types/can_turn_in_boats.json`. The only archive entry with that name is nested in the future feature pack:

`data/minecraft/datapacks/update_1_21/data/minecraft/tags/entity_types/can_turn_in_boats.json`

- Uncompressed length: 44 bytes.
- SHA-256 of exact uncompressed resource bytes: `342edad98ebf6fc00d1189d1fb6bf5840d80658e7fbf9915442e8d1d256b083f`.
- Exact UTF-8 content:

```json
{
  "values": [
    "minecraft:breeze"
  ]
}
```

There are no nested tag references. The ordinary default resource is absent; the bundled future-pack tag contributes Breeze only if that pack is enabled. Neither default nor the bundled future-pack entry includes `minecraft:player`; effective default Player membership is false (and remains false if the feature pack is enabled).

The future pack's adjacent `pack.mcmeta` enables `minecraft:update_1_21` and declares pack format 26. Its presence is not evidence that this future pack is enabled in a default 1.20.4 world; the Player conclusion is unaffected either way.

Exact source context, from the published 1.20.4 Mojmap tree `decompiled_minecraft/1.20.4/mojmap`:

- `net/minecraft/world/entity/vehicle/Boat.java:665-676`: `positionRider` calls `super`, then tests `!rider.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS)`. For Player, the tag test is false, so the guarded path runs: add `deltaRotation` to yaw and head yaw, then clamp rotation. The later full-boat animal-only body/head adjustment does not apply to Player.
- `net/minecraft/tags/EntityTypeTags.java:27`: declares the tag key.
- `net/minecraft/tags/TagLoader.java:41-84, 100-128`: scans resources under the configured tag directory and builds the loaded entries; the only can-turn resource in the JAR is under the separate future-pack tree above.
- Source manifest SHA-256s: Boat.java `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109`; EntityTypeTags.java `3805f34f55ae616145b2c10b4f6e6c78223096248f4a45779f698cc9f8482d97`; TagLoader.java `86c8757c33af3be0f507a311118fe6b93d8205a19ea98e72b7947b88763f4b7a`.

For the bounded older side, exact 1.20.2 Mojmap `net/minecraft/world/entity/vehicle/Boat.java:700-709` performs the same rider yaw/head-yaw additions and clamp unconditionally after `super.positionRider`. Thus Player executes this behavior in both 1.20.2 and 1.20.4. The new 1.20.4 tag gate does not produce a direct player movement/orientation delta; no player-specific boat change is supported by this claim. This closes the named Player gate only; it does not audit unrelated vehicle behavior.
