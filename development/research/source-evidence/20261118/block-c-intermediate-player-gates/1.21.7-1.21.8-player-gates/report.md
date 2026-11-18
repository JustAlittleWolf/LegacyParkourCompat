# 1.21.7 → 1.21.8 player-state and locomotion source comparison

## Disposition

No player-state/locomotion source difference was found in the bounded slices below: the six paired Java files have identical SHA-256 digests. Treat the no-difference result as **provisional for exact 1.21.8 provenance**. The 1.21.8 adjacent ready marker identifies `1.21.8/mojmap` and `ready`, but its recorded output path and source-manifest file are absent from this checkout, so its manifest digest cannot be checked against these local files. The 1.21.7 files do match their hidden decompiler provenance entries. Do not use this report as a fully provenance-closed acceptance until the 1.21.8 source-preparation owner binds the local tree to the ready manifest (or republishes its manifest).

This is the intermediate **1.21.7 → 1.21.8** boundary for the earlier 1.21.5 → 1.21.8 endpoint comparison. It establishes no behavior or introduction boundary for 1.21.5 → 1.21.7, and it does not revise any prior endpoint finding.

## Exact evidence and hashes

Both source directories are `decompiled_minecraft/<version>/mojmap/` (Mojmap). The six files below have the same digest in both trees; each 1.21.7 digest matches the corresponding entry in `1.21.7/mojmap/.decompile-provenance.json`:

| Relative Java file | SHA-256 in both trees |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/client/player/ClientInput.java` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| `net/minecraft/client/player/KeyboardInput.java` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |
| `net/minecraft/client/KeyMapping.java` | `d5475012d2849e7547e23d1584d5b099661a2cb1e60daebe6ec19cb28b779111` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |

1.21.7 provenance file SHA-256: `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`.

The adjacent `decompiled_minecraft/1.21.8/mojmap.ready.json` has SHA-256 `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`; it says version `1.21.8`, mapping `mojmap`, status `ready`, 6,105 sources, and source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`. Its `outputPath` (`build/movement-campaign-2026-10-07/ready/1.21.8/mojmap`) and `mojmap.sources.sha256` are not present. Local source tree contains 6,105 files, but this count does not bind its content to that unavailable manifest.

## Paired slices checked

The corresponding 1.21.8 locations have the same line numbers and bytes as the cited 1.21.7 slices.

- `LocalPlayer.java:197-222,234-239`: local tick calls `super.tick()` before sending changed `lastSentInput` and movement/sprint state. No ordering difference.
- `LocalPlayer.java:683-744,837-856,1061-1083`: `aiStep` snapshots jump/shift/forward before `input.tick()`, then later consumes refreshed key state/forward impulse. Auto-jump mutates jump after refresh. Sprint trigger reset, double-tap timer (`7`), sprint-key initiation, run/swim continuation gates, blindness, food, forward input, collision, water and vehicle checks are unchanged. `canStartSprinting` still excludes local item use; sprint reset still checks `isUsingItem() && !isPassenger()`.
- `ClientInput.java:6-31`, `KeyboardInput.java:7-36`, `KeyMapping.java:99`: base input tick remains empty; keyboard tick samples each key mapping, computes impulses and normalizes the move vector. Key freshness and threshold (`1.0E-5F`) are unchanged.
- `LocalPlayer.java:473-510,593-639`: local `startedUsingItem` state, item-use start/stop, `isUsingItem`, and the `0.2F` input scale while using an item are unchanged. `LocalPlayer.java:693-701,718-734` also retains the same crouch-state calculation and the same pre-/post-refresh sprint gate order.
- `Player.java:354,459-491`: player tick still invokes pose update; the pose-fit guard, desired pose selection, crouch fallback and swimming fallback are unchanged. `LocalPlayer.java:664-680` still resets pose to standing in `resetPos`.
- `LocalPlayer.java:854-856,1065-1083` and `LivingEntity.java:2192-2217,2777-2813`: blindness sprint predicate, jump-boost addition and amplifier math, jump gate/order, and slow-falling/levitation fall-distance reset are unchanged. These direct effect readers were checked; effect production/timing outside these movement-facing predicates was not separately inventoried.

## Bounds

This assignment covers local-player input consumption and freshness, sprint start/continuation gates, item-use slowdown/gating, player crouch/pose reset, and direct effect-based sprint/jump/fall predicates, with their input/pose/effect readers in the files listed above. It excludes cosmetics, remote players, collision or auto-jump geometry, fluids/contact, non-player physics, and unrelated effect behavior. It does not close the full pair inventory or independent full-pair audit.
