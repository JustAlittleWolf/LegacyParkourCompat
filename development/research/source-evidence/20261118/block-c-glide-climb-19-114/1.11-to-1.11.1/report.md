# C lane — 1.11 → 1.11.1 Elytra/climb gate

## Verdict

No source difference in the assigned dry, ordinary-player, already-active chest Elytra path at a ladder/vine. The relevant `LivingEntity.moveRelative` and `flyingTick` method bodies are byte-for-byte identical between 1.11 and 1.11.1 (method-body SHA-256 values below). With fall-flying flag 7 active, the gliding arm takes precedence over the ordinary climb cleanup: it does not clamp horizontal velocity, clear fall distance, or apply the climb descent cap. The strict pre-move test and `1.0F` assignment are unchanged.

This is a bounded no-difference finding for the requested path, not complete pair coverage or a runtime parity verdict.

## Exact inputs and integrity

Canonical mapping: `ornithe-feather` (Feather generation 2). Both adjacent ready markers say `status: ready`, exact `versionId`/mapping match, and cite exact source count/byte totals. For each version, the ready marker's full SHA-256 values for source manifest, artifact manifest, provenance, and diagnostics match the adjacent files. Every source manifest entry was re-hashed from the actual source bytes; all counts and byte totals match the ready marker.

| Version | Ready counts | Actual source verification | Source manifest SHA-256 | Artifact manifest SHA-256 | Provenance SHA-256 | Diagnostics SHA-256 |
|---|---:|---|---|---|---|---|
| 1.11 | 1,918 files / 9,871,205 bytes | 1,918/1,918 matched | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `e8d7baae858bc9fa47eceef91694809da8f105268e0af2c5c025caa6e4175610` |
| 1.11.1 | 1,921 files / 9,899,007 bytes | 1,921/1,921 matched | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `ebbf181f60d7bc922df56826f528d1ea8a064783ac0f0f2b576e73db1fc2a958` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `534d2601990ffd466f3fc7ae3234dd3e03c32c00f681c8d03408139176fd6d8d` |

Source provenance records exact requested/resolved release IDs, Feather mapping artifacts, client hashes, decompiler/remapper tool versions, Java runtime, and successful exact decompile run. The 37-entry artifact manifests themselves hash to their declared identities and match the ready markers. Their referenced campaign artifact payloads are absent from `build/movement-campaign-2026-10-07/artifacts/`, so those payload bytes could not be re-hashed in this checkout. This does not affect the independent verification of all published decompiled source bytes.

Relevant source-file SHA-256:

| File | 1.11 | 1.11.1 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `45b27314866ba66e7f0208dd236ee7e6919aee84a32d22f8ef07db09433f3aeb` | `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf` |

The compared method-body hashes are SHA-256 over the UTF-8 bytes of the source text from method signature through matching closing brace:

- `LivingEntity.moveRelative(float sideways, float forwards)`: 8,528 bytes on both sides; `37da15b2aaad8d56f1f5908759fa60da8ce4fd49ca314b9fb28de8c4f70bd1bb` on both sides.
- `LivingEntity.flyingTick()`: 666 bytes on both sides; `0d7580a470858a9fa8a7e1eb3bf75816aaf9310d0bfad1d1ada3952389a4019f` on both sides.

## Ordered source trace

1. A local jump input requests `START_FALL_FLYING` only when airborne, current `velocityY < 0.0`, not already fall-flying, not creative-flying, and the chest item is a flyable Elytra: `LocalClientPlayerEntity.java`, `tickMovement`/input handling, 1.11 and 1.11.1 lines 742–746. The local class file SHA is identical for both releases.
2. Server packet handling rechecks airborne, `velocityY < 0.0`, not already fall-flying, not in water, and flyable chest Elytra, then calls `ServerPlayerEntity.setFlying()` (`ServerPlayNetworkHandler.java`: 1.11 lines 835–840; 1.11.1 lines 841–846). `setFlying()` sets shared flag 7 true (`ServerPlayerEntity.java`: 1.11 lines 1046–1048; 1.11.1 lines 1067–1069). The state read by `isFallFlying()` is flag 7 (`LivingEntity.java`: 1.11 lines 2143–2145; 1.11.1 lines 2145–2147). Thus local input requests activation, while the server-owned flag is the movement branch's authority.
3. On the travel path, `LivingEntity.travel` runs `flyingTick()` before `moveRelative()` (1.11 lines 1796–1801; 1.11.1 lines 1798–1803). The updater starts from flag 7; while flagged, airborne and not riding, it validates the chest Elytra. It clears invalid state, and only on the logical server writes the recomputed flag (`flyingTick`: 1.11 lines 1808–1826; 1.11.1 lines 1810–1828). The already-active ordinary Elytra scenario passes this equipment check.
4. `moveRelative` reaches the fall-flying branch before the normal branch containing `isClimbing()` cleanup (`LivingEntity.java`: 1.11 lines 1386–1460; 1.11.1 lines 1388–1462). For the requested dry, normal player case, `isFallFlying()` therefore wins even when the current block makes `isClimbing()` true. The climb cleanup is in the `else` arm (1.11 lines 1439–1478; 1.11.1 lines 1441–1480): it is skipped during glide, so no climb `fallDistance = 0.0F`, X/Z ±0.15 clamp, downward `-0.15` cap, or post-move climb collision boost occurs.
5. At the top of the glide arm, before glide acceleration and before `move`, `if (velocityY > -0.5)` sets `fallDistance = 1.0F` (a float literal). Equality at `-0.5` does not enter this assignment. The start request's separate `velocityY < 0.0` check is a looser activation boundary; it is not an alternative form of the pre-move glide threshold (`LivingEntity.java`: 1.11 lines 1390–1394; 1.11.1 lines 1392–1396).
6. The glide branch updates velocity, performs its first `move`, and only afterward checks horizontal collision damage and on-ground clearing; those effects are guarded by `!world.isClient` (1.11 lines 1422–1438; 1.11.1 lines 1424–1440). The ordinary local client does not clear flag 7 at this post-move site; server flag synchronization remains authoritative. No source change in this ordering or boundary exists across the pair.

## Exact references

All references are relative to `decompiled_minecraft/<version>/ornithe-feather/`:

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, lines 742–749.
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, 1.11 lines 835–846; 1.11.1 lines 841–852.
- `net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, 1.11 lines 1046–1053; 1.11.1 lines 1067–1074.
- `net/minecraft/entity/living/LivingEntity.java`, travel ordering and `flyingTick`: 1.11 lines 1796–1826; 1.11.1 lines 1798–1828. Branch and threshold: 1.11 lines 1386–1478; 1.11.1 lines 1388–1480. `isFallFlying`: 1.11 lines 2143–2145; 1.11.1 lines 2145–2147.

No wiki inventory, product implementation, Git history, or non-assigned fluid/geometry lane was consulted. No source generation, build, tests, or runtime launch was performed.