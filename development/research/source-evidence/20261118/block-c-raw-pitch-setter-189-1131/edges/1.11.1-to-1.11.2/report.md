# C — Feather 1.11.1 → 1.11.2 local pitch turn / Elytra consumer

## Frozen bounded result

**No source difference found in the assigned ordinary local direct-pitch-turn → dry local active-Elytra consumer slice.** The direct camera-turn body is text-identical in the exact mapped sources. The caller and the first gliding pitch reads are also identical. This result closes only the assigned slice; it is not a full-pair inventory or freeze.

## Exact source identity and byte checks

Both published trees are exact release sources mapped with Ornithe Feather Gen2 build.2 (`1.11.1+build.2` and `1.11.2+build.2`), not substitute versions. Their ready markers identify `versionId` 1.11.1 / 1.11.2 and mapping `ornithe-feather`.

- `decompiled_minecraft/1.11.1/ornithe-feather.ready.json`: status `ready`; source manifest SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`; exact client SHA-256 `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`. Its provenance records resolved version 1.11.1 and the two Feather build.2 artifacts.
- `decompiled_minecraft/1.11.2/ornithe-feather.ready.json`: status `ready`; source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; exact client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`. Its provenance records resolved version 1.11.2 and Feather build.2 artifacts. `ornithe-feather.verification.json` records `verified-identical-source-and-input-artifacts`, 1,921 source files checked and zero source differences from its independent regeneration. It separately discloses one regenerated intermediate mapped-JAR difference; raw input artifact differences are empty.
- I recomputed each source-manifest SHA-256 against its ready marker and checked every listed source file against its manifest: both trees contain 1,921/1,921 files with zero missing or hash-mismatched files.

Cited source file SHA-256 values (verified against the manifests):

| Source | 1.11.1 | 1.11.2 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | same |
| `net/minecraft/client/render/GameRenderer.java` | `d8f54f58020e52b117c74cf248a1bac4fc541c4244340f6a0b43f5dbce245db5` | same |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | same |

The `Entity.java` files differ elsewhere, so the conclusion below is based on the cited method text, not a whole-file hash comparison.

## Comparison and movement path

- **Input and direct turn:** `GameRenderer.updateMouse` passes ordinary mouse deltas to `minecraft.player.updateLocalPlayerCamera(h, i * j)` in both smooth and unsmoothed paths (`1.11.1` and `1.11.2`, `net/minecraft/client/render/GameRenderer.java:844–868`, identical full-file hashes above). `Entity.updateLocalPlayerCamera` is text-identical at `net/minecraft/entity/Entity.java:304–315` in both releases:
  1. Save old `pitch` as float `f`.
  2. Compute `pitch - inputPitch * 0.15` with the unsuffixed `0.15` double literal, then narrow the result to float.
  3. Clamp the stored current pitch to `[-90.0F, 90.0F]`.
  4. Only after that clamp, update previous pitch with `lastPitch + (pitch - f)`, using float operands.

  The pitch clamp is therefore applied to current pitch before the previous-pitch delta in both versions; there is no changed operation width, order, or bound in this slice.
- **Finite overrange witness:** from `pitch == lastPitch == 0.0F`, a finite direct pitch input of `-1000.0F` makes the pre-clamp expression `150.0F`; current pitch stores `90.0F`, then previous pitch stores `90.0F`. This is a finite overrange intermediate, not an overrange stored pitch. The same sequence follows from both exact method bodies.
- **Reachable gliding consumer:** local falling input can request `START_FALL_FLYING` only when the player is not already gliding, airborne, descending, not ability-flying, and has an Elytra chest item for which `ElytraItem.canFly` is true (`LocalClientPlayerEntity.java:742–749`). On the living movement path, `flyingTick()` precedes `moveRelative(...)` (`LivingEntity.java:1798–1804`). The relevant `moveRelative` branch requires local control (or logical-side movement updating), not being in water/lava unless player ability-flying applies, and `isFallFlying()` (`LivingEntity.java:1388–1393`). In that active dry gliding branch, the first stored pitch consumer is `getLookVector()` at line 1397: `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`, which passes `this.pitch` into the rotation-vector calculation (`LivingEntity.java:1915–1928`). The next direct pitch read is the float-radian conversion at line 1398. Both `LivingEntity.java` files are byte-identical by manifest hash, so neither the first read nor the later gliding math changes across this pair.

## Source references

- 1.11.1 Feather: `net/minecraft/client/render/GameRenderer.java:844–868`; `net/minecraft/entity/Entity.java:304–315`; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:742–749`; `net/minecraft/entity/living/LivingEntity.java:1388–1427, 1798–1804, 1915–1928`.
- 1.11.2 Feather: same relative paths and line ranges. `GameRenderer`, `LocalClientPlayerEntity`, and `LivingEntity` have matching full-file hashes; the cited `Entity.updateLocalPlayerCamera` range was checked text-for-text despite the surrounding `Entity.java` files differing.
