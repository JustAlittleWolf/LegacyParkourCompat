# 1.20.3 → 1.20.4: local sprint state slice

## Result and boundary

Compared the assigned Mojmap sources for ordinary on-foot local-player sprint input and state transitions. No source difference was found in this slice: `Input.java`, `KeyboardInput.java`, `LocalPlayer.java`, `Options.java`, and `Entity.java` are byte-identical across the two published source trees (the per-file SHA-256 values are below). This is a bounded no-difference result, not full-pair coverage or a pair freeze.

This review covers keyboard input sampling, the pre-/post-sampling reads used by sprint initiation, the trigger timer, sprint start/cancel gates, and the inherited sprint flag writer. It does not examine mounted behavior, sprint-jump behavior, item-use slowdown math, food production, or geometry/contact/fluid producers. Water/underwater and collision predicates are only noted where they appear in the local sprint state machine; their dependencies are not covered here.

## Source provenance and integrity

Paths are relative to the primary checkout `D:\Javastuff\LegacyParkourCompat`.

- **1.20.3 Mojmap:** `decompiled_minecraft/1.20.3/mojmap/`. Its embedded `.decompile-provenance.json` SHA-256 is `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`. The manifest identifies the client artifact from `https://piston-data.mojang.com/v1/objects/b178a327a96f2cf1c9f98a45e5588d654a3e4369/client.jar` (SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`), Mojang client mappings from `https://piston-data.mojang.com/v1/objects/be76ecc174ea25580bdc9bf335481a5192d9f3b7/client.txt` (SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`), and exact 1.20.3 version metadata (SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`). The manifest's embedded hashes for all 5,048 source files were checked against the current tree: 5,048 matched, 0 missing or mismatched.
- **1.20.4 Mojmap:** `decompiled_minecraft/1.20.4/mojmap/`. `mojmap.provenance.json` SHA-256 `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` identifies version `1.20.4`, namespace `mojmap`, and exact run mapping option `mojmap`; recorded tools include Tiny Remapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0, ASM 9.10.1, Java 25.0.3+9-LTS and Gradle 9.7.1. `mojmap.sources.sha256` SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` matches `mojmap.ready.json`'s recorded source-manifest digest; all 5,048 listed sources were checked against the current tree (5,048 matched, 0 missing or mismatched). The ready marker records client SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; `artifacts.sha256` records `client-mojmap.jar` SHA-256 `d20c183016505cceecd33d508973e7ca6f2ee8cbd860f316592f4a09a96bc339` and `client_mappings.txt` SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`. The source manifest, ready marker, and artifact manifest SHA-256 values are respectively `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`, `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`, and `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`.

The 1.20.4 provenance's recorded exact-run staging/ready paths are under `build/movement-campaign-2026-10-07`, and its recorded working directory is a separate worktree; these are not the current `decompiled_minecraft/1.20.4/mojmap` path. The present tree's full source set nevertheless matches the recorded source manifest and ready-marker digest. This verifies current source bytes against that manifest but does not establish that decompilation originally ran directly at the current canonical path.

## Compared source slices

All references below apply to both `1.20.3/mojmap` and `1.20.4/mojmap`; the cited files have equal full-file hashes on both sides.

- `net/minecraft/client/player/KeyboardInput.java:12-35`, `tick`: direction keys are sampled in up/down/left/right order; `calculateImpulse` returns zero when both key states agree, otherwise signed `1.0F`; then forward and left impulses are assigned, jump and shift states are sampled, and optional slow-movement scaling is applied. Sprint itself is not stored in `Input` by this method.
- `net/minecraft/client/player/Input.java:18-24`: movement vector exposes `(leftImpulse, forwardImpulse)`; `hasForwardImpulse()` uses the strict `forwardImpulse > 1.0E-5F` predicate.
- `net/minecraft/client/Options.java:459`: `keySprint` is a `ToggleKeyMapping`; `LocalPlayer` queries its `isDown()` state directly rather than sampling it into `Input`.
- `net/minecraft/client/player/LocalPlayer.java:188-191,645-715`: `tick()` enters through `super.tick()`. In `aiStep`, positive `sprintTriggerTime` is decremented first. Before `input.tick`, the method snapshots old `input.jumping`, old `input.shiftKeyDown`, and old `hasEnoughImpulseToStartSprinting()` into locals. It then computes crouch/sneak speed and samples `input.tick`; tutorial input handling follows. If using an item while unmounted, the method modifies movement impulses and clears the trigger timer. The old shift snapshot clears the timer again before sprint-start logic.
- `LocalPlayer.java:690-703,1013-1034`: `canStartSprinting()` is evaluated after current input sampling; on foot it requires not already sprinting, sufficient current forward impulse, food level above 6 or mayfly, not using an item, no blindness, and not fall-flying. Outside underwater conditions, the start impulse test is `forwardImpulse >= 0.8`; underwater it delegates to `hasForwardImpulse()`. The double-tap branch uses the *old* `!shift` and `!enoughImpulse` snapshots together with current ground/underwater state and the post-sampling `canStartSprinting()` result. If the trigger timer is nonpositive and sprint key is up, it arms the timer to `7`; otherwise it writes sprint true. The held-sprint branch separately writes true when the player is not in water or is underwater and `canStartSprinting()` succeeds.
- `LocalPlayer.java:705-715`: after start checks, if currently sprinting, the method checks current forward impulse and the food gate; it also gates on horizontal collision and water state. Swimming players have the separate conditional stop branch; otherwise the combined stop gate writes sprint false. Exact predicate order is unchanged between this pair.
- `net/minecraft/world/entity/Entity.java:2155-2160,2229-2240`: `isSprinting()` reads shared flag bit 3; `setSprinting(boolean)` writes that bit through `setSharedFlag`, which sets/clears the corresponding bit in entity shared flags.

## Hashes of compared files

| Relative source | 1.20.3 SHA-256 | 1.20.4 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/Input.java` | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | same |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | same |
| `net/minecraft/client/Options.java` | `39ae95ee0def943be3f9ada31c65b5c8a8a258d61fcad09701903bfd93c3f4fa` | same |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | same |

No separate sprint-state finding is warranted for this pair within the assigned slice. Pair-wide tick/inventory coverage and all named excluded or dependent operations remain open.
