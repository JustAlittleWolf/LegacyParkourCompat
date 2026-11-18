# 1.20.1 → 1.20.2: active ability flight with Elytra glide flag

## Bounded result

**No source difference in the assigned operation.** On the logical client, when lawful ability flight is already active for an airborne, dry, nonpassenger local player with an enabled Elytra in the chest slot and shared flag 7 set, the tick still performs the Elytra glide travel and real self-move first. `Player.travel` then restores vertical velocity to the saved pre-travel Y velocity multiplied by `0.6`, resets fall distance, and directly clears shared flag 7, in that order. The fall-distance reset and glide-flag clear are distinct writes. This behavior and ordering match in both releases.

## Trace and evidence

- The local player’s `aiStep` keeps an existing lawful `abilities.flying` state (the mayfly input logic only changes it on its stated toggle/always-flying paths); the Elytra start request is gated on `!abilities.flying`, so it does not replace the already-flying case. 1.20.1 `LocalPlayer.java:718–745,801–806`; 1.20.2 `LocalPlayer.java:717–744,800–804`.
- `LivingEntity.aiStep` invokes `updateFallFlying()` before `travel(...)`. With shared flag 7 already set, the airborne/nonpassenger/non-levitation gate and enabled chest Elytra retain the glide flag on the client; the server-only flag write does not run client-side. 1.20.1 `LivingEntity.java:2562,2572,2610–2640`; 1.20.2 `LivingEntity.java:2591,2601,2639–2669`.
- The dry, locally controlled travel path enters its `isFallFlying()` branch while that flag is still set. It applies glide movement and calls `move(MoverType.SELF, ...)` before returning. 1.20.1 `LivingEntity.java:2004–2010,2068–2094`; 1.20.2 `LivingEntity.java:2004–2010,2102–2128`.
- The player ability-flight branch is `abilities.flying && !isPassenger()`. It calls `super.travel(...)` first, then sets vertical velocity from the saved pre-travel Y value (`* 0.6`), calls `resetFallDistance()`, and calls `setSharedFlag(7, false)`. The branch does not require a particular chest item; the enabled Elytra matters to the preceding glide gate. 1.20.1 `Player.java:1446–1473`; 1.20.2 `Player.java:1450–1477`.
- `Entity.move(MoverType, Vec3)` was also compared as the downstream real-move operation; its complete method body matches. 1.20.1 `Entity.java:593`; 1.20.2 `Entity.java:595`.

The compared complete method bodies for `Player.travel`, `LivingEntity.travel`, and `Entity.move` are identical. The relevant ability-flight and Elytra gates in `LocalPlayer.aiStep` and `LivingEntity.aiStep/updateFallFlying` preserve the same conditions and ordering. `LocalPlayer.aiStep` has a separate pose-fit predicate change (`canPlayerFitWithinBlocksAndEntitiesWhen` to `canEnterPose`) outside this airborne flight trace; it does not alter these flight gates.

## Source publication identity

Exact canonical sources in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`, read-only; Mojmap namespace; source-set readiness and manifest digest checked against each `mojmap.ready.json`.

- **1.20.1**: ready status `ready`, 4,786 files; ready marker `mojmap.ready.json` SHA-256 `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed`; source manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; provenance SHA-256 `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`.
- **1.20.2**: ready status `ready`, 4,904 files; ready marker `mojmap.ready.json` SHA-256 `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; source manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; provenance SHA-256 `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.
- Both provenance manifests record the same pinned batch run: Mojmap, Gradle 9.7.1, Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0, ASM 9.10.1, Gson 2.14.0; batch versions `1.20.1,1.20.2,1.20.4,1.20.6`.

## Cited source SHA-256

Hashes below are the exact source bytes and were checked against the matching entry in that release’s `mojmap.sources.sha256` manifest.

| Release | Relative source file | SHA-256 |
| --- | --- | --- |
| 1.20.1 | `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| 1.20.2 | `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| 1.20.1 | `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| 1.20.2 | `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |
| 1.20.1 | `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| 1.20.2 | `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| 1.20.1 | `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.2 | `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |