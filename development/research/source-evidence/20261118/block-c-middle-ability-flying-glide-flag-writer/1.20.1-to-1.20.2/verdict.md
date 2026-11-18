# Independent audit verdict: 1.20.1 → 1.20.2 ability-flight Elytra glide clear

**Verdict: ACCEPTED — no movement difference in the assigned trace.** This verdict is bound to the source report SHA-256 `356dd5fd3cfec94d383a214f87190de3a7e02834764040bbbb52e03a3f8db64a` and to the exact canonical Mojmap source identities below. Scope is only an ordinary logical-client local player with lawful creative ability flight already active, airborne, dry, not a passenger, shared flag 7 set, and an enabled default Elytra in the chest slot. It does not assert pair-wide movement coverage or runtime parity.

## Independent trace

In both versions, `LocalPlayer.aiStep` does not request Elytra start while `abilities.flying` is true; with ability flight already active, its mayfly handling only toggles on the documented input/game-mode paths. `LivingEntity.aiStep` calls `updateFallFlying()` before `travel(...)`. Given the assigned preconditions, the flag, airborne/nonpassenger/non-levitation gates, and enabled chest Elytra leave glide active. Its only final shared-flag write is server-gated, so this client pass does not clear the flag there.

The dry, locally controlled `LivingEntity.travel` path therefore sees `isFallFlying()` true. It computes glide velocity and calls `move(MoverType.SELF, this.getDeltaMovement())` before returning. `Player.travel` takes the `abilities.flying && !isPassenger()` branch, saves pre-travel Y velocity, calls `super.travel(...)` (including that real glide move), then restores Y as saved-Y `* 0.6`, resets fall distance, and directly calls `setSharedFlag(7, false)`, in that order. The fall-distance reset and flag clear are distinct writes after the move. The complete relevant method bodies and gates match across the pair; the direct flag clear is present in both.

Compared evidence: `LocalPlayer.aiStep` (1.20.1 lines 718–745; 1.20.2 lines 717–744), `LivingEntity.aiStep/updateFallFlying` (1.20.1 lines 2562, 2572, 2610–2640; 1.20.2 lines 2591, 2601, 2639–2669), `LivingEntity.travel` (1.20.1 lines 2004–2106; 1.20.2 lines 2038–2140), `Player.travel` (1.20.1 lines 1446–1473; 1.20.2 lines 1450–1477), and `Entity.move` (1.20.1 line 593; 1.20.2 line 595). The report's cited LocalPlayer and Player ranges and this call-order conclusion are confirmed. A separate pose-fit predicate change in `LocalPlayer.aiStep` does not enter this bounded trace.

## Evidence identity checked

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`, read-only. Both ready markers state `status: ready`, namespace `mojmap`, and the exact release. I independently hashed each ready marker, provenance JSON, source manifest, and cited Java source; the ready marker's declared source-manifest hash matches the actual manifest hash, and each cited Java hash matches its release manifest entry.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 |
| --- | --- | --- | --- |
| 1.20.1 | `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` | `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa` | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` |
| 1.20.2 | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` |

Both provenance manifests identify the same pinned Mojmap batch run and tools (Gradle 9.7.1, Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0, ASM 9.10.1, Gson 2.14.0; batch `1.20.1,1.20.2,1.20.4,1.20.6`).

The byte hashes below were recomputed and checked against each release's `mojmap.sources.sha256` entry:

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

**Audit signature:** independent exact-source audit of the bounded claim and immutable report identity above; 2026-11-18.
