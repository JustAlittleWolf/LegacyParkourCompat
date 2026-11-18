# Frozen source audit: `Entity.turn` pitch path, 1.19 → 1.19.1

## Verdict and scope

**Bounded result: no source change found in the assigned ordinary `Entity.turn` pitch-setter/clamp path or in the following active Elytra pitch read.** This verdict covers only the direct ordinary Mojmap `Entity.turn(double,double)` path, its first pitch setter and outer clamp, plus proof that the player’s next active Elytra travel reads the resulting pitch. It is not a full-pair coverage audit or a finding that every 1.19→1.19.1 movement operation is unchanged.

The exact older Mojang version ID is `1.19` (the release commonly written `1.19.0`); the newer exact ID is `1.19.1`. No online mappings or secondary implementation were used.

## Frozen source identity and integrity

Read-only canonical source roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.19\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.19.1\mojmap`

The `mojmap.ready.json` markers report `status: ready`, exact version IDs above, Mojmap namespace, Vineflower and Java 25. They record source counts/bytes of 4,430 / 21,697,172 (`1.19`) and 4,480 / 21,921,512 (`1.19.1`). Marker `outputPath` fields preserve the preparation staging publication path; the canonical read-only source directories consumed here are the roots listed above.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 (verified against marker) | Artifact manifest SHA-256 (verified against marker) |
|---|---|---|---|---|
| 1.19 | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf` |
| 1.19.1 | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `aae6327967e07f5cdd92ac9bfa6d9c606e1be9a96e24dd30acad07fd2425df41` |

The listed manifests’ file hashes match their ready-marker values. The cited source file bytes also match each exact path entry in `mojmap.sources.sha256`:

| Source relative path | 1.19 SHA-256 | 1.19.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| `net/minecraft/world/entity/LivingEntity.java` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| `net/minecraft/world/entity/player/Player.java` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| `net/minecraft/client/player/LocalPlayer.java` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |

The provenance identifies official client and client-mapping inputs. For `1.19`, client SHA-256 is `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764`, client-mappings SHA-256 `9e31de7fc15b1bac0e58abc441a5e56c320266112feaea981bcffd41dd6201e1`, and mapped-client SHA-256 `596aae86fdfb3f7912e37cf4516842edf0908e8dcfc4ed0a857c10f1fcec3cd7`. For `1.19.1`, these are respectively `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52`, `9b2dfe745719f4de1ce3fb23baeb24e31623c1fcc249e39bbdb068ad7f600236`, and `d0a828070e9516eaf0d853b07198a0b27aa006fd68b1b93d66df5aee5b236f4b`. Both provenance records report Mojmap access correction for one package-private client class and no Vineflower errors; this warning is outside the cited movement source files.

## Exact pitch trace

In both versions, `Entity.java`, `Entity.turn(double $$0, double $$1)` (1.19 lines 389–402; 1.19.1 lines 389–402) has this same ordered body:

```java
float $$2 = (float)$$1 * 0.15F;
float $$3 = (float)$$0 * 0.15F;
this.setXRot(this.getXRot() + $$2);
this.setYRot(this.getYRot() + $$3);
this.setXRot(Mth.clamp(this.getXRot(), -90.0F, 90.0F));
this.xRotO += $$2;
this.yRotO += $$3;
this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
```

The pitch input is `$$1` (a `double`). Java narrows it to `float` before multiplying by the float literal `0.15F`; the resulting local `$$2` and the addition to `getXRot()` are float-width operations. The first pitch setter receives the un-clamped sum. The second pitch setter occurs only after the outer `Mth.clamp` to `[-90.0F, 90.0F]`. The previous-tick pitch field is updated afterward from the same delta and then separately clamped. Yaw is a separate path and does not affect this trace.

`Entity.setXRot(float $$0)` (1.19 lines 3059–3065; 1.19.1 lines 3059–3065) is byte-content-equivalent in the two sources: if `!Float.isFinite($$0)`, it logs/pauses in IDE and discards; otherwise it assigns `this.xRot = $$0`. It does not normalize or clamp finite values. Therefore a finite first-setter input above 360 degrees is accepted transiently. Concrete legal input: with initial pitch `0.0F`, ordinary `turn(0.0, 2401.0)` computes `$$2 = (float)2401.0 * 0.15F`, a finite float greater than `360.0F`; the first setter stores it, and the later outer clamp stores `90.0F`. This exhibits why the finite setter itself is not the effective pitch bound.

## Live next active Elytra movement read

This is an executable player movement path in both sources, not an unused helper:

1. `LocalPlayer.tick()` calls `super.tick()` (1.19 line 200; 1.19.1 line 202). `Player.tick()` calls `super.tick()` (1.19 line 256; 1.19.1 line 257); `LivingEntity.tick()` runs `this.aiStep()` (1.19 line 2296; 1.19.1 line 2297).
2. `LocalPlayer.aiStep()` calls `super.aiStep()` (1.19 line 887; 1.19.1 line 893). In `LivingEntity.aiStep()`, the travel section calls `updateFallFlying()` and then virtual `this.travel(new Vec3(this.xxa, this.yya, this.zza))` (1.19 line 2585; 1.19.1 line 2588).
3. Player’s override `Player.travel(Vec3)` delegates to `super.travel($$0)` in its non-creative-flight branch (1.19 lines 1459–1489, especially 1485; 1.19.1 lines 1464–1494, especially 1490). `LivingEntity.travel(Vec3)` tests `this.isFallFlying()` and, in that active branch, reads `this.getXRot()` into `float $$15 = this.getXRot() * (float)(Math.PI / 180.0)` before using it for the gliding lift and pitch-dependent dive term (1.19 lines 2107–2135; 1.19.1 lines 2107–2135). `isFallFlying()` reads shared flag 7 (1.19 line 3046; 1.19.1 line 3049).

Thus, after an ordinary turn returns with active fall flying still true, the next player `aiStep` enters the actual player travel override, delegates into the live `LivingEntity` Elytra branch, and consumes the outer-clamped pitch. For the concrete input above it consumes `90.0F`; there is no movement opportunity between the first finite setter and the outer clamp within synchronous `turn`.

## Pair comparison disposition

The relevant `Entity.turn` and `setXRot` bodies are unchanged between 1.19 and 1.19.1. The active `LivingEntity.travel` Elytra branch’s pitch read and the pitch-dependent calculations are likewise unchanged. The player tick/travel dispatch reaches that branch on both sides. The bounded direct-turn pitch claim therefore yields no 1.19→1.19.1 difference in this trace. Do not infer a broader source-pair freeze from this bounded verdict.

## Phase-two independent comparison and bounded review verdict

**Reviewed author bytes:** `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-raw-pitch-setter-119-1191-source\report.md`, SHA-256 `be35361679d2e9669a072d32ee84e37c5ec5de55eb96e95d6ca9bacedaa171ea`. The supplied identity was verified before reading/comparison. The author report was not edited.

**Independent result: concur with the author’s bounded no-difference verdict.** I retraced the cited 1.19 and 1.19.1 Mojmap methods from the frozen canonical source sets. Extracted method text compares byte-for-byte equal within each version pair for `Entity.turn(double,double)`, `Entity.setXRot(float)`, `LivingEntity.travel(Vec3)`, `Player.travel(Vec3)`, and `MouseHandler.turnPlayer()`. The full `MouseHandler.java` bytes are also identical and match both source-manifest entries. Ready/provenance and source/artifact-manifest identities remain consistent with the source identities already recorded above.

The author’s added finite-overflow deduction is source-correct under a finite ordinary prior pitch: a finite `Double.MAX_VALUE` input narrows to positive `float` infinity; multiplication by `0.15F` remains positive infinity; the first pitch setter rejects that non-finite value and leaves the prior `xRot` untouched; the final pitch clamp then reads/clamps that retained finite pitch. The later `xRotO += pitchDelta` becomes positive infinity, and the exact `Mth.clamp(float,float,float)` implementation (`if (value < min) min else value > max ? max : value`) maps it to `90.0F`. `Mth.java` is distinct by full-file hash between the releases, but this cited float clamp implementation is text-identical. This arithmetic deduction was source-inspected, not executed.

I also confirmed `MouseHandler.turnPlayer()` reaches `minecraft.player.turn(...)` when the local player exists, and the active client-player tick path reaches `LivingEntity.aiStep()` then the virtual travel call. With fall-flying active, the delegated `LivingEntity.travel` branch reads current `getXRot()` into its pitch radians and uses it in the Elytra terms. That establishes the requested live movement consumer without extending this operation-specific comparison into whole-pair inventory or runtime claims.

**Signed bounded verdict:** `c-raw-pitch-setter-119-1191-audit` — independent source comparator. The direct ordinary 1.19→1.19.1 pitch-response claim remains a no-difference coverage result; no implementation delta is supported by this reviewed trace. The verdict applies only to the author report bytes and source identity listed above, and to the bounded caller/setter/clamp/active-Elytra-read path.
