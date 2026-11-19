# Powder snow shipped tag verification

This addendum resolves the shipped resource dependency in coverage.md for Java 26.1.2 and 26.2. The exact raw 26.1.2 client jar was recovered from the local source resource cache and the exact raw 26.2 jar was read from the local Fabric Loom cache. Each complete jar SHA-256 matched its release's decompiled_minecraft artifact manifest before the resource was read.

Each archive contained one matching entry, data/minecraft/tags/entity_type/powder_snow_walkable_mobs.json. The JSON values are minecraft:rabbit, minecraft:endermite, minecraft:silverfish, and minecraft:fox in this order. All four entries are direct entity-type IDs, so the expanded membership is the same list. Minecraft's player type is absent. The entry bytes match between the releases. The bare-foot player result therefore has no shipped tag dependency change. Runtime datapacks or server-synchronized registry tags can change membership and are outside this shipped-default comparison.

| Release | Verified raw client jar SHA-256 | Shipped tag entry SHA-256 |
| --- | --- | --- |
| 26.1.2 | b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 | b3b3e888f3c8590c9439b99bb4d7d8f8df610fdcc1a0b3e5c4418255ad8a000e |
| 26.2 | 40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 | b3b3e888f3c8590c9439b99bb4d7d8f8df610fdcc1a0b3e5c4418255ad8a000e |
