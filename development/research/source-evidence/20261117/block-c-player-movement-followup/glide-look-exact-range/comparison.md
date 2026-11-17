# Gliding look: body yaw and float lookup path

## Bounded result

This source-only review finds two observed transitions in the supplied endpoints:

1. **Look provider and trig expression: 1.11.2 to 1.12.2 for body yaw; 1.12.2 to 1.13 for the expression.** In 1.11.2, `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`, whose override uses `headYaw`. In 1.12.2, the inherited `Entity.getLookVector()` directly calls the two-angle final method with `pitch, yaw`; the glide branch consumes that result. From 1.13 through 1.14.4, the same body-yaw provider remains, while the two-angle float formula is the direct-radian form. The 1.13, 1.13.1, 1.13.2, 1.14 and 1.14.4 inspected endpoints all retain that formula and the same float lookup scale/table construction.
2. **Lookup index arithmetic: 1.14.4 to 1.21.11 is an observed endpoint difference, retained at 26.2.** The older helper accepts a `float` and multiplies by `10430.378F`; both newer `Mth` implementations accept `double` and use `10430.378350470453`. This is a demonstrated arithmetic change, but the exact intervening release where it first appeared is not established by the examined endpoints.

The 1.12.2→1.13 expression change preserves the ideal real-valued direction but not necessarily the lookup result. It also changes the cardinal result at the bounded witness below. The head/body provider transition is a distinct change and is not evidence about the trig expression by itself.

## Source trace and exact spans

Mappings: 1.11.2–1.14.4 use Ornithe Feather; 1.21.11 uses Mojmap; 26.2 is published unobfuscated source.

- **1.11.2:** `LivingEntity.java:1915–1928` overrides `getLookVector()` to call `getRotationVector(1.0F)` and routes that overload to `pitch, headYaw`. `LivingEntity.java:1397` feeds `getLookVector()` into the fall-flying branch. `Entity.java:1664–1666` is the superclass stub. Thus divergent head/body angles distinguish this source provider.
- **1.12.2:** `LivingEntity.java:1434` consumes `getLookVector()` in fall-flying movement. `Entity.java:1239–1244` computes `cos(-yaw·rad−π)`, `sin(-yaw·rad−π)`, `-cos(-pitch·rad)`, `sin(-pitch·rad)` and returns `(g·h, i, f·h)`. `Entity.java:1690–1692` passes `pitch, yaw` directly. The player’s inherited living-entity travel path therefore uses body yaw for this glide look. This differs from the 1.11.2 head-yaw provider for reachable states where headYaw and yaw diverge.
- **1.13:** `LivingEntity.java:1474` consumes `getLookVector()`. `Entity.java:1250–1257` computes `f=pitch·rad`, `g=−yaw·rad`, then `cos(g), sin(g), cos(f), sin(f)` and returns `(sin(g)·cos(f), −sin(f), cos(g)·cos(f))`. `Entity.java:1690–1692` still passes body `yaw`.
- **1.13.1 / 1.13.2:** same method body and same direct-body-yaw provider at `Entity.java:1251–1258, 1691–1693`; gliding consumes it at `LivingEntity.java:1493`. The local Entity, LivingEntity and MathHelper file hashes are identical across 1.13.1 and 1.13.2.
- **1.14 / 1.14.4:** same direct formula at 1.14 `Entity.java:1181–1188`, 1.14.4 `Entity.java:1233–1241`; body-yaw `getLookVector()` at 1.14 `1632–1634`, 1.14.4 `1684–1686`. The glide call remains `LivingEntity.getLookVector()` (1.14 `1796`; 1.14.4 `1803`). `MathHelper.java` retains `sin(float)`/`cos(float)` and the `10430.378F` lookup index in both.
- **1.21.11:** `LivingEntity.java:2462–2473` gets `this.getLookAngle()` for fall-flying movement. `Entity.java:2448–2450` implements it with `getXRot(), getYRot()` (body yaw). `Entity.java:1828–1836` retains the direct-radian formula. `Mth.java:31–37, 50–56` builds the 65,536-float sine table using `i / 10430.378350470453` and indexes it from `double` input using that double scale.
- **26.2:** `LivingEntity.java:2602–2612` uses `getLookAngle()` in the glide update; `Entity.java:2579–2581` passes `getXRot(), getYRot()`; `Entity.java:1966–1974` retains the direct formula. `Mth.java:32–37, 50–56` retains the double-scale table and index arithmetic from 1.21.11.

## Bounded source-real witnesses

### Formula cutover at cardinal yaw

Use a player already in fall-flying travel, with `pitch = 0.0F`, body `yaw = 0.0F`, and any divergent head yaw. Activation and gravity are outside this witness. The glide look call reads body yaw at both 1.12.2 and 1.13.

The 1.12.2 helper has a 65,536-entry float sine table initialized as `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` (`MathHelper.java:426–429`). Its lookup uses `(int)(x * 10430.378F) & 65535` and a cosine offset of `16384.0F` (`15–20`). At this input, the old yaw sine argument is `-πF`, whose float lookup truncates to index **32768**; the table value there is `(float)Math.sin(π)`, a nonzero positive float (`0x250D3132`, about `1.2246469e−16`). The cosine lookup is index **49152**, whose table value is exactly `−1.0F`. Pitch cosine uses index **16384** (`+1.0F`) and pitch sine index **0** (`+0.0F`). The old vector is therefore `(-1.2246469e−16, +0.0, +1.0)` at float precision.

At 1.13, the yaw radians are `−0.0F`; sine indexes table entry **0** (`+0.0F`) and cosine indexes **16384** (`+1.0F`). Pitch has the same exact cardinal table values. The new vector is `(0.0, −0.0, +1.0)`. This is a bounded source-level directional difference in a reachable glide state, not a claim about a full trajectory or a same-tick activation case.

### Later lookup-index change

Use `pitch = 0.0F`, body `yaw = 3.2547F`, already in fall-flying travel. The direct formula is unchanged across these compared endpoints; the helper’s input type and scale differ. The float radian passed to yaw trig is `g = −0.05680523F` (bits `0xBD68AC9A`). With the 1.14.4 `float` multiply by `10430.378F`, sine and cosine truncate to indices **64944** and **15792**. With 1.21.11/26.2 `double` multiply by `10430.378350470453`, they truncate to **64943** and **15791**. The corresponding table entries yield approximately `(-0.05672682, 0, 0.9983897)` versus `(-0.05682254, -0.0, 0.9983843)` for the pitch-zero look vector. The one-index difference and source inputs establish a bounded witness; the decimal values are rounded displays of the source table entries.

## Local evidence identity and limits

The supplied audit artifact was read from `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\glide-look-audit\verdict.md`; its SHA-256 is `72b5b0f13ed876b34e76630446427580d9c5ea1d34d395b678605173366b1f0e`. Its relevant 1.12.2→1.13 trig and 1.11.2→1.12.2 head/body findings were independently retraced above.

Relevant local source-file SHA-256 values (the exact canonical directory path is `decompiled_minecraft/<version>/<mapping>/...`):

| Version/mapping | Entity.java | LivingEntity.java | MathHelper.java / Mth.java |
|---|---|---|---|
| 1.11.2 / Ornithe Feather | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | not needed for provider verdict |
| 1.12.2 / Ornithe Feather | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |
| 1.13 / Ornithe Feather | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f` |
| 1.13.1 / Ornithe Feather | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| 1.13.2 / Ornithe Feather | same as 1.13.1 for all three files | same | same |
| 1.14 / Ornithe Feather | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 1.14.4 / Ornithe Feather | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 1.21.11 / Mojmap | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c` |
| 26.2 / unobfuscated | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |

**Unresolved provenance limit:** none of the inspected source directories contains `.decompile-provenance.json`. The file hashes bind this review to the local source bytes, but do not independently authenticate their client artifacts, mapping builds or decompiler inputs. The 1.14.4 directory also contains an unrelated `.yarn.staging-*` directory; this review used only the named `ornithe-feather` files. No exact intermediate patch boundary for either observed transition is claimed. No activation, gravity, collision, or full tick/trajectory behavior was reviewed.
