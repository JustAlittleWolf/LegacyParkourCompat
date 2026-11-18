# 1.21.1 → 26.2 pitch-setter destination witness

## Result

The bounded claim is source-supported, with a normal finite mouse-input witness. In 1.21.1 `Entity.turn` clamps the cumulative pitch after `setXRot`; in 26.2, `setXRot` itself first wraps the value with `% 360.0F` and clamps it, and `turn` then clamps once more. For a cumulative pitch of exactly `720.0F`, the old path stores `90.0F` while the new path stores `0.0F`. The accumulated input is a finite ordinary mouse delta; the source has no cap on the accumulated cursor displacement. This is a source-level reachability witness, not a runtime observation.

## Caller order and default-input witness

Both releases accumulate callback displacement additively (`MouseHandler.onMove`), only while the window is active, and call `turnPlayer` from `handleAccumulatedMovement` while the mouse is grabbed and the local player exists. The accumulator is reset after handling. Mouse scaling is otherwise the same for the witness: default sensitivity is `0.5`; with smooth camera off and not scoping, `ss = 0.5 * 0.6F + 0.2F = 0.5`, `sensitivityMod = ss^3 = 0.125`, and `sens = 1.0`. Default Y inversion is false. Thus a net accumulated vertical displacement of `4800` is passed as finite `yo = 4800`; `Entity.turn` computes `xDelta = (float) yo * 0.15F = 720.0F`.

Starting from pitch `0`, 1.21.1 `turn` calls `setXRot(720)`, which accepts the finite value unchanged, then calls `setXRot(clamp(720, -90, 90))`, storing `90`. In 26.2 the first setter call stores `clamp(720 % 360, -90, 90) = 0`; the second clamp leaves it at `0`. Yaw is irrelevant to this pitch witness.

The source path permits a sum of 4800 because it adds callback coordinate differences and applies no displacement limit before scaling. The exact pair does not prove that every mouse device/OS can produce a single such interval; the ordinary input witness is conditional on that finite callback sequence. No crafted network value or setter-only injection is involved.

`turn` separately updates `xRotO` by `xDelta`, then clamps `xRotO` to `[-90,90]`. Immediately after this witness, `xRotO` is `90` on both releases while authoritative `getXRot()` is respectively `90` and `0`. At the end of `LivingEntity.tick`, both versions assign `xRotO = getXRot()`, so the interpolation/history field is then synchronized to the authoritative pitch; it does not undo the immediate setter difference.

## Concrete movement read

Fall-flying movement reads the authoritative pitch after `turn`: 1.21.1's `LivingEntity.travel` fall-flying branch uses `getXRot()` for `cos(leanAngle)^2` and for the pitch-dependent lift term; 26.2's `LivingEntity.updateFallFlyingMovement` does the corresponding read. With the witness pitch, the first branch sees `90°` (`cos²` approximately zero) and the second sees `0°` (`cos² = 1`), changing vertical movement calculation. This read requires the player to be in the ordinary fall-flying state; no network crafting is needed. The report makes no trajectory/runtime claim.

## Source identities and integrity

Canonical read-only source trees were present and marked `ready` for exact release `1.21.1` / `mojmap` and `26.2` / `unobfuscated`. Ready markers, provenance files, source manifests, and cited Java files were hashed locally with SHA-256. Each cited Java hash below matches its path entry in the corresponding `.sources.sha256`; each ready marker's `sourceManifestSha256` matches the manifest file hash.

| Evidence | Relative path | SHA-256 |
| --- | --- | --- |
| Ready | `decompiled_minecraft/1.21.1/mojmap.ready.json` | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` |
| Provenance | `decompiled_minecraft/1.21.1/mojmap.provenance.json` | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` |
| Source manifest | `decompiled_minecraft/1.21.1/mojmap.sources.sha256` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` |
| `MouseHandler.java` | `decompiled_minecraft/1.21.1/mojmap/net/minecraft/client/MouseHandler.java` | `824c52842483eb2899bd5a76c39701c3a616f249dcb64283da556dff4bcd50f7` |
| `Options.java` | `decompiled_minecraft/1.21.1/mojmap/net/minecraft/client/Options.java` | `77ebfa588da0edb355037ea40ab90ea57b19659abfbafcff84f621f5211f250e` |
| `Entity.java` | `decompiled_minecraft/1.21.1/mojmap/net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `LivingEntity.java` | `decompiled_minecraft/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| Ready | `decompiled_minecraft/26.2/unobfuscated.ready.json` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` |
| Provenance | `decompiled_minecraft/26.2/unobfuscated.provenance.json` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |
| Source manifest | `decompiled_minecraft/26.2/unobfuscated.sources.sha256` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |
| `MouseHandler.java` | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/MouseHandler.java` | `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15` |
| `Options.java` | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/Options.java` | `26bbc6e61b2c072105d0f5a5b2d7afea4baeed28140e03b4e6793ceb5f6be831` |
| `Entity.java` | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `LivingEntity.java` | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |

Provenance states Mojmap tools/runtime: Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, ASM 9.10.1, Gson 2.14.0. The 26.2 source is published unobfuscated; provenance records the same Java/decompiler tool versions. No source generation or runtime was performed.

## Source anchors

- 1.21.1 `MouseHandler.onMove` 207–224; `handleAccumulatedMovement` 228–252; `turnPlayer` 255–287. `Options` sensitivity default 101–107; Y-inversion default 399. `Entity.turn` 401–410; `Entity.setXRot` 3363–3369. `LivingEntity.travel` fall-flying branch begins 2149; pitch read and lift math 2155–2167. `LivingEntity.tick` copies authoritative pitch to `xRotO` at 481.
- 26.2 `MouseHandler.onMove` 263–278; `handleAccumulatedMovement` 282–347; `turnPlayer` 350–377. `Options` sensitivity default 101–107; Y-inversion default 526. `Entity.turn` 489–501; `Entity.setXRot` 3910–3916. `LivingEntity.updateFallFlyingMovement` reads pitch and computes lift at 2600–2621. `LivingEntity.tick` copies authoritative pitch to `xRotO` at 501.
