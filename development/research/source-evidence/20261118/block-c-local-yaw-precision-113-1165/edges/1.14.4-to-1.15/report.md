# 1.14.4 → 1.15: direct local yaw and first relative-acceleration use

## Bounded result

For the requested ordinary local yaw slice, **no source behavior difference was found** between exact 1.14.4 and 1.15 Mojmap sources. `Entity.turn(double, double)` has byte-for-byte identical decompiled method text (SHA-256 of extracted method text: `f04ca2fa5fe922491290326d935076bef2091aec8564845779a347e73d2143d0`). The first yaw-dependent ordinary ground/air acceleration remains the same in `Entity.moveRelative` → `getInputVector`; only the helper's access changes from `protected static` (1.14.4) to `private static` (1.15). The relevant normal `LivingEntity.travel` branch still calls `moveRelative` after selecting its dry, non-fall-flying path.

This closes only the assigned yaw-turn / first ordinary relative-acceleration slice. It is not a full 1.14.4→1.15 movement-pair freeze.

## Exact source identity

All compared files are under `decompiled_minecraft/<version>/mojmap/` and use Mojmap names.

| Release | Exact source inputs and recorded identity |
|---|---|
| 1.14.4 | `mojmap.ready.json`: `versionId=1.14.4`, `versionMetadataId=1.14.4`, client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; source manifest `mojmap.sources.sha256`, SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; artifact manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`. The artifact manifest pins `1.14.4/client_mappings.txt` to `2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910` and `1.14.4/client.jar` to the client hash above. |
| 1.15 | Run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`; `mojmap.provenance.json` and `mojmap.ready.json` both identify `versionId=1.15`; metadata SHA-256 `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785`; client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`; official client mappings SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`; source manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; artifact manifest SHA-256 `acc29974470e4f4864c695e1f1d084e746874663c47a365ca1fa41ff9fcc0cae`. |

The 1.14.4 ready marker and version metadata both say exact `1.14.4`; no alias is indicated by those fields or by the recorded exact command `--versions=1.14.4`. Its provenance is an older, thinner schema than 1.15: it records `mappingOption=feather,mojmap` and Feather mapping artifact names, but omits `requestedVersionId`, `rawInputs`, input URLs/hashes and tool/source-root fields. The companion artifact manifest does pin the client and `client_mappings.txt`, and the ready marker's source-manifest digest matches the actual manifest. Treat the local source and those sidecars as the evidence identity; do not claim that the 1.14.4 provenance JSON alone supplies the newer complete provenance chain.

Relevant decompiled-file hashes, verified against each source manifest:

| Source file | 1.14.4 SHA-256 | 1.15 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086b6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/util/Mth.java` | `57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |

## Compared operation and movement meaning

- **Direct yaw input:** 1.14.4 `Entity.java:319–331`; 1.15 `Entity.java:326–338`. Both take `double` arguments; compute pitch and yaw deltas as `double` (`0.15` multiplier); add each delta to the current `float` field and cast back to `float`; clamp current pitch; then add the same deltas to previous pitch/yaw fields and clamp previous pitch; then notify a vehicle if present. The yaw writes are ordered current (`yRot`) then previous (`yRotO`) in both versions. Neither yaw field is clamped, wrapped, or checked for finite values. Pitch clamp and callback ordering are also unchanged. For ordinary local input, a sufficiently large/non-finite yaw delta therefore remains unbounded/non-finite at this operation in both versions.
- **First ordinary yaw consumer:** 1.14.4 `Entity.java:1080–1095`; 1.15 `Entity.java:1063–1078`. `moveRelative` passes `this.yRot` to `getInputVector` and adds the resulting vector to delta movement. The helper leaves an input with squared length below `1.0E-7` at zero; otherwise it normalizes only when squared length exceeds `1.0`, scales by the acceleration, converts yaw degrees to radians in `float`, and rotates horizontal input using `Mth.sin`/`Mth.cos`. The helper's access modifier changes only: `protected static` → `private static`; its body is otherwise identical. `Mth.sin` and `Mth.cos` retain the same float lookup-index expressions in both versions (`sin`: `(int)(f * 10430.378F) & 65535`; `cos`: `(int)(f * 10430.378F + 16384.0F) & 65535`). The masked table index makes this consumer periodic without changing the stored yaw; it does not add a finite-value guard.
- **Reachable ordinary route:** 1.14.4 `LivingEntity.travel(Vec3)` starts at `LivingEntity.java:1782`; 1.15 starts at `:1831`. Each has water/lava and fall-flying branches, then reaches the ordinary `else` path for dry, non-fall-flying ground/air movement; that path invokes `moveRelative` at 1.14.4 `:1840` and 1.15 `:1889`. The direct yaw effect on horizontal input is consequently the same for an ordinary player following this route. Other travel branches and other yaw consumers are outside this assignment.

## Coverage boundary

Examined the exact `Entity.turn` operation, the first ordinary `yRot`-dependent relative-acceleration helper and its trigonometric lookup, and the `LivingEntity.travel` route that reaches that helper. Not examined: the rest of either tick graph, special travel branches, later yaw consumers, patch releases between these endpoints, or non-yaw movement differences. No difference finding is warranted for this slice; patch introduction elsewhere in the pair remains unaddressed.
