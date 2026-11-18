# 1.11.1 → 1.11.2: active Elytra at ladder/vine feet

## Bounded result

No source difference was found for the requested operation. The compared `LivingEntity.java` is byte-identical in both exact prepared Feather trees (SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`). The adjacent patch boundary therefore does not change the active-glide versus ordinary-climb behavior described below. This is a bounded result, not full-pair coverage.

## Exact source behavior

Both releases, Feather mapping, `net/minecraft/entity/living/LivingEntity.java`:

- `isClimbing()` (lines 978–996) reads the block at `floor(x), floor(shape.minY), floor(z)` and returns true for a ladder or vine (except spectator players). Thus the ladder/vine-at-feet precondition is the ordinary climb path's predicate.
- `moveRelative(float sideways, float forwards)` (lines 1388–1561) first requires local control or logical-side authority for updating movement (line 1389). Within that gate, `isFallFlying()` selects its branch before the normal branch's climb handling (lines 1392 onward versus 1462 onward).
- With the fall-flying flag already active, `velocityY > -0.5` is a strict comparison against the double literal `-0.5`; if true, it writes `fallDistance = 1.0F` before look-vector calculations and the movement call (lines 1392–1436). This branch does not execute the normal climb branch's horizontal `[-0.15F, 0.15F]` clamps, `fallDistance = 0.0F`, downward `-0.15` floor, sneaking downward stop, or post-move colliding-horizontal `velocityY = 0.2` cleanup (lines 1462–1479). The gliding branch moves using its glide-adjusted velocity at line 1429; its later on-ground flag clear is server-only (lines 1438–1439).
- `flyingTick()` (lines 1810–1827) takes the current flag 7 as its input, keeps it only while airborne and not riding with an exact chest-slot Elytra for which `ElytraItem.canFly` is true, and writes the recomputed flag only when `!world.isClient`. This authority gate is unchanged. For the bounded already-active local-player case, `moveRelative` reads the current flag under its local-control/authority gate; this comparison does not cover initial activation or server synchronization.

`ClientPlayerEntity.java` is byte-identical too. A paired diff of `PlayerEntity.java` found only the unrelated serialized `DataVersion` change (921 → 922 at line 752), outside this movement operation.

## Provenance and integrity

Both sides are exact `1.11.1` / `1.11.2`, `ornithe-feather` prepared sources. Version-root provenance, ready, source-list, artifact-list, and diagnostics sidecar hashes were checked against the corresponding ready marker where recorded. Relevant source hashes below were recomputed and matched the version-root source manifests.

| Source | 1.11.1 SHA-256 | 1.11.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` |

| Version | Provenance sidecar SHA-256 | Ready sidecar SHA-256 | Source-list SHA-256 | Artifact-list SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|---|---|
| 1.11.1 | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `ebbf181f60d7bc922df56826f528d1ea8a064783ac0f0f2b576e73db1fc2a958` | `534d2601990ffd466f3fc7ae3234dd3e03c32c00f681c8d03408139176fd6d8d` |
| 1.11.2 | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` | `8bb3b1169a21958a6570fc304a0a95d9c51cc3a2101869bcbd453c3588925cdc` |

Provenance identifies Feather Gen2 build 2 for each side (`feather-gen2-1.11.1+build.2` and `feather-gen2-1.11.2+build.2`). The exact resolved release IDs and raw-client SHA-256 values recorded in the provenance/ready metadata are `1.11.1` / `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` and `1.11.2` / `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.

The version-root source and artifact sidecars are present and their hashes match their ready metadata. The cached raw client and mapping-artifact files referenced by the provenance manifests were absent at their recorded cache paths when checked. That cache absence is separate from, and does not invalidate, the present prepared sources whose per-file hashes match the source manifests.

## Exact references

- `decompiled_minecraft/1.11.1/ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, `artifacts.sha256`, `movement-diagnostics.txt`.
- `decompiled_minecraft/1.11.2/ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, `artifacts.sha256`, `movement-diagnostics.txt`.
- `decompiled_minecraft/{1.11.1,1.11.2}/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`.
- `decompiled_minecraft/{1.11.1,1.11.2}/ornithe-feather/net/minecraft/client/entity/living/player/ClientPlayerEntity.java`.
- `decompiled_minecraft/{1.11.1,1.11.2}/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`.
