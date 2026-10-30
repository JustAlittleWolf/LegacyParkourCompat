# Independent review: 1.8.4 → 1.8.5 same-input closure supplement

Reviewed report: `.native-source/20261030/ordinary-pool/184-185-same-input-closure/report.md`
Report SHA-256: `9cce308ea50f348e52a720b3612bb9c2a2a47c771497c2bed2feca1a4d2d1f18`

## Verdict

The source delta, client/server effect handling, packaged-resource conclusion, and equal-received-state boundary are verified. The report is sound for its bounded same-input conclusion. One concrete precondition should be stated more explicitly: a non-vanilla client can send an unsupported beacon effect ID through the existing `MC|Beacon` custom-payload handler; this is not limited to malformed persisted data or an unspecified `setData` caller.

This review does not redo the Java inventory or the separately accepted `EntityAttributes.java` cap operation, and it does not claim pair-wide freeze, runtime parity, or movement behavior for unequal received state.

## Input and provenance checks

The cached archives are at `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/{1.8.4,1.8.5}/client.jar`. Independently computed SHA-256 values are:

| Release | Cached JAR SHA-256 | Source manifest / cache pin |
| --- | --- | --- |
| 1.8.4 | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` | Match |
| 1.8.5 | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | Match |

Both `.decompile-provenance.json` file hashes match the report (`b2207bf9…bb03a00` and `986d25df…ea99ce`). The `client.jar.pin.json` URLs and hashes match each manifest's client input. The cached `version.json` files match their `version.json.pin.json` hashes, identify 1.8.4 and 1.8.5 respectively, and declare the same client URLs, Mojang SHA-1 object IDs, and byte sizes. The client URLs' object IDs therefore agree with the pinned release metadata. The archives opened and all non-directory entries were read and hashed; no missing, unreadable, or duplicate-path issue was observed.

Hashes for the checked exact-source files also agree with their corresponding provenance-manifest `sources` entries. The checked set included both `BeaconBlockEntity.java` files and the shared `BeaconScreen`, `StatusEffect`, `ServerPlayerEntity`, `LivingEntity`, `PlayerEntity`, `ClientPlayNetworkHandler`, and effect/attribute packet sources. These were read from the canonical, read-only source roots.

## Beacon effect filter and reachability

- In 1.8.4, `BeaconBlockEntity.readNbt` assigns `Primary` and `Secondary` directly, and `setData` assigns the supplied values directly. In 1.8.5, `getBeaconEffect` accepts exactly Speed, Haste, Resistance, Jump Boost, Strength, and Regeneration; both methods filter through it. The hashes and method bodies match the supplement.
- `BeaconBlockEntity.EFFECTS` and the unchanged `BeaconScreen` build selectable buttons from those six effects. Thus the normal UI cannot select Slowness (ID 2).
- A concrete injected path exists in both server versions: `ServerPlayNetworkHandler` handles `MC|Beacon`, reads two raw integers, and—when the player has a `BeaconMenu` open and its payment slot has an item—calls `inventory.setData(1, k)` and `setData(2, l)` without validating the effect IDs. In 1.8.4 this reaches the unfiltered setter, so a raw primary ID of 2 can persist and be spread by an active beacon. In 1.8.5 the same call reaches the filtering setter and maps ID 2 to 0. The 1.8.4 saved-NBT case is independently valid as another precondition. The report's phrase “equivalent non-UI `setData` caller” is technically broad enough, but naming this actual packet route would make the reachability evidence reviewable.
- `spreadEffects` is unchanged and requires an active beacon, positive levels, server world, and positive primary ID. `StatusEffect.SLOWNESS` remains ID 2 with a `-0.15F` `MOVEMENT_SPEED` modifier.

## Client packet path and same-input boundary

The report's separation of server production from client movement is accurate. `ServerPlayerEntity.onStatusEffectApplied` sends `EntityStatusEffectS2CPacket`; the unchanged client handler resolves the entity and calls `LivingEntity.addStatusEffect`. The unchanged `LivingEntity.onStatusEffectApplied` only applies effect attribute modifiers when `!world.isClient`, so that callback does not install Slowness's movement-speed modifier on the client. Separately, `handleEntityAttributes` installs base values and modifiers from `EntityAttributesS2CPacket`, and `PlayerEntity` reads `MOVEMENT_SPEED` from its attribute instance.

Therefore, the relevant Beacon consequence reaches client movement through server-produced/received state. With equal received status state, equal received movement-attribute state, and equal movement-relevant world/player state, no Beacon-driven 1.8.4 → 1.8.5 client movement difference remains established by this change. If the Beacon delta results in different received movement-relevant state, the inputs are unequal and this is outside the supplement's same-input closure. This boundary does not resolve the separate `EntityAttributes.java` operation.

## Packaged-resource comparison

Only after verifying both cached JAR hashes against their source-manifest and cache pins, I independently compared every non-directory ZIP entry by path and uncompressed-content SHA-256:

- 5,597 entries in each archive; zero path additions/removals.
- 43 content differences.
- The four differing non-class entries are `META-INF/MANIFEST.MF`, `META-INF/MOJANGCS.SF`, `META-INF/MOJANGCS.RSA`, and `assets/minecraft/texts/splashes.txt`.
- No changed packaged data/registry JSON or movement-default resource was found.

This confirms the report's narrow default packaged-resource conclusion. It does not cover user resource packs, world data, or server-produced inputs.

## Disposition

Accept the report's same-input closure and resource conclusion with the injected-payload reachability clarification above. The clarification strengthens evidence that the unusual state is reachable by a nonconforming client; it does not change the equal-received-state freeze boundary. Keep pair-wide freeze and inherited 1.8.3 → 1.8.4 boundary dispositions governed by their separate coverage and evidence gates.
