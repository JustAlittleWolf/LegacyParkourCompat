# Independent source audit — 1.11 → 1.11.1 Elytra/climb gate

## Final verdict

**Claim upheld for the assigned bounded path; no source difference found.** In an ordinary, dry local-player travel tick with fall-flying flag 7 already active and a flyable chest Elytra, `LivingEntity.moveRelative` enters its fall-flying arm before the normal arm that checks `isClimbing()`. A ladder or vine makes `isClimbing()` true for a non-spectator player, but that predicate's climb cleanup and post-move climb boost are not reached while flag 7 is active. Before glide acceleration and the first `move`, `velocityY > -0.5` assigns `fallDistance = 1.0F`; equality at `-0.5` does not assign it. The compared `moveRelative` and `flyingTick` declarations are identical between these releases.

This verdict accepts only the bounded no-difference claim. It does not establish complete 1.11/1.11.1 movement-pair coverage or runtime parity.

## Start: frozen input and exact source identities

- Frozen report: `c-glide-climb-111-1111-alias-gate/report.md`; SHA-256 `9ea7c07c412bdb42651033c338b98dfc24c324cb3007ccdaa5933058f11f2769`.
- Sources: canonical local `decompiled_minecraft/<version>/ornithe-feather/`, mapping `ornithe-feather` (Feather generation 2), exact releases 1.11 and 1.11.1.
- Ready markers identify the exact version and mapping. Their manifest hashes, source counts and byte totals match the adjacent files and computed source trees.

| Version | Source files / bytes | Source manifest SHA-256 | Provenance SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
|---|---:|---|---|---|---|
| 1.11 | 1,918 / 9,871,205 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd` | `e8d7baae858bc9fa47eceef91694809da8f105268e0af2c5c025caa6e4175610` |
| 1.11.1 | 1,921 / 9,899,007 | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `ebbf181f60d7bc922df56826f528d1ea8a064783ac0f0f2b576e73db1fc2a958` | `534d2601990ffd466f3fc7ae3234dd3e03c32c00f681c8d03408139176fd6d8d` |

Independently, all source-manifest entries were rehashed against their local source bytes (1,918/1,918 and 1,921/1,921 matched), and source totals match the ready markers. Ready-marker hashes for provenance, source manifest, artifact manifest and diagnostics match the corresponding files. Relevant local source-file SHA-256 values:

| Source file | 1.11 | 1.11.1 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | same |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `45b27314866ba66e7f0208dd236ee7e6919aee84a32d22f8ef07db09433f3aeb` | `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `f939a9c8b592feff053f2b0988c527c05bb8d8c8f328d882cc7468dc9a9583fa` | `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197` |

The provenance JSON records exact requested/resolved IDs, Feather artifacts, client hashes, tools and successful decompile runs. The referenced upstream artifact payloads are not present in this checkout, so I verified the local provenance/ready/source chain and published source bytes, but did not independently rehash those client and mapping payloads.

## Independent A/B trace

### A — 1.11

- `LocalClientPlayerEntity.tickMovement`, lines 742–749: jumping while airborne, descending (`velocityY < 0.0`), not already fall-flying or creative-flying, and wearing a flyable Elytra sends `START_FALL_FLYING`.
- `ServerPlayNetworkHandler`, lines 835–840: the server rechecks airborne, descending, not already fall-flying, not in water, and flyable chest Elytra; it then calls `setFlying()`. `ServerPlayerEntity.setFlying`, lines 1046–1048, sets shared flag 7 true.
- `LivingEntity.isFallFlying`, lines 2143–2145, reads flag 7. In `travel`, lines 1796–1801, `flyingTick()` precedes `moveRelative()`.
- `flyingTick`, lines 1808–1826: the updater begins from flag 7; when active, airborne and not riding, it validates the chest Elytra. It only writes the recomputed flag when `!world.isClient`, leaving client movement to use the synchronized active flag.
- `moveRelative`, lines 1386–1478: after the movement-update, water and lava gates, the fall-flying branch is tested first at line 1390. Lines 1391–1392 apply the strict `velocityY > -0.5` test and `1.0F` assignment before glide velocity updates and `move`. The `else` begins at line 1439; its `isClimbing()` cleanup at lines 1460–1471 and collision climb boost at line 1478 are not executed in the glide arm. The glide post-move flag clear at lines 1436–1437 is server-only.
- `isClimbing`, lines 978–990: for a non-spectator player, ladder and vine block states return true.

### B — 1.11.1

The corresponding sites are `LocalClientPlayerEntity` 742–749; `ServerPlayNetworkHandler` 841–846; `ServerPlayerEntity.setFlying` 1067–1069; `LivingEntity.travel` 1798–1803; `flyingTick` 1810–1828; `moveRelative` 1388–1480; `isClimbing` 978–990; and `isFallFlying` 2145–2147. The client input class file is itself byte-identical to A. The authoritative updater/flag flow and the branch ordering, strict threshold, `1.0F` literal, and climb-arm placement match A.

Method comparison independently recomputed from the local UTF-8 declarations through each matching closing brace:

| Method | 1.11 bytes / SHA-256 | 1.11.1 bytes / SHA-256 | Result |
|---|---|---|---|
| `LivingEntity.moveRelative(float sideways, float forwards)` | 8,528 / `37da15b2aaad8d56f1f5908759fa60da8ce4fd49ca314b9fb28de8c4f70bd1bb` | same | identical |
| `LivingEntity.flyingTick()` | 666 / `0d7580a470858a9fa8a7e1eb3bf75816aaf9310d0bfad1d1ada3952389a4019f` | same | identical |

## Final scope and checks

No source difference is supported for the requested ordinary dry player path with an already-active fall-flying flag and climbable ladder/vine state. The local activation request's `velocityY < 0.0` gate is separate from the pre-move glide threshold; it does not change the strict `> -0.5` behavior. This audit did not inspect wiki lanes, product implementations or Git history, and did not run generation, builds, tests or runtime programs. Pair-wide tick/inventory closure remains open.
