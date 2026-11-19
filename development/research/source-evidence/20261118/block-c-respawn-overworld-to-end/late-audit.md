# Independent late-span respawn applicability audit

Status: **COMPLETED**

## Scope and bound inputs

Read-only source applicability audit of replacement-client respawn behavior in exact local stable snapshots 1.15–1.20.6 inclusive. This span only; the separate accepted 1.21.1→1.21.2 keep-data change and 1.21 alias are unchanged and were not audited here. No product or shared research files were read or changed; no tests, builds, launches, generation, or network lookups were performed.

Bound source-only input `c-external-respawn-late-span/evidence.md` SHA-256: `5055A923F8E5DD62BE4119FF116B96EC7DE313BFD5387A5A7138F33AFF0F37BB` (matches assignment). Identity supplement `c-external-respawn-late-span/identity-supplement.md` SHA-256: `1D3D4AC00C76D4CC74AA481C699B1348F2ED82CDF0F44D3260A11B829A00F3D3`.

## Verdict

For every one of the 26 stable release directories from 1.15 through 1.20.6, `ClientPacketListener.handleRespawn` creates a replacement `LocalPlayer`, assigns it as the local player, and invokes `resetPos()` before adding it to the level. The replacement reset explicitly assigns `Vec3.ZERO` to delta movement. No branch in these handlers copies old-player velocity, and keep-data branches occur before reset and copy entity data, attributes, or (from 1.20) sneak/sprint constructor inputs only. Thus in this bounded span every examined replacement-client respawn clears/retains zero delta movement independently of keep-data flags.

The reset implementation has a `level != null` guard around its position work and zeroing. The respawn handler passes its current `ClientLevel` into `MultiPlayerGameMode.createPlayer`, whose inspected factory overloads directly construct a `LocalPlayer` with that level. For the normal reachable respawn path, the guard is therefore satisfied. The handler's dimension-change condition governs level reconstruction; it does not guard `resetPos()`, which is called on both same-dimension and changed-dimension respawns.

## Coverage: all stable source directories

I independently extracted the complete `public void handleRespawn(...)` body from each local Mojmap `ClientPacketListener.java`, balanced braces to the method end, and recomputed UTF-8 SHA-256. All 26 hashes match the bound evidence. Identical digest means identical complete handler body. Grouping:

| Releases | `handleRespawn` SHA-256 |
|---|---|
| 1.15, 1.15.1, 1.15.2 | `9c594f4ed5c99e969b7364fdd6c2527ea258e08f60678f0aaa019ff7a16f3698` |
| 1.16, 1.16.1 | `1ae7893cd9880e7240a407f1863a554ec5754aedda9e9f2fe46b18569ba0d880` |
| 1.16.2, 1.16.3, 1.16.4, 1.16.5 | `ea1ed567de61ee0a4c2ddb21e4ac485525f532c09f9e0db957f4d25e3a0975b4` |
| 1.17, 1.17.1 | `c0ece0307bdd616dcb43e8a8f7935f59d4ad268b0437f08942519ab4aba575f2` |
| 1.18, 1.18.1 | `cb42b079b17c8b9adeb67490978840bfecd1cc4aedc0e01d1dba3debad00f2d0` |
| 1.18.2 | `607ccd4705d5ce818b8925023ef7ac8bd4844f38d763487a004b316ac180717a` |
| 1.19 | `cd38ef2c816e6dba75256aed4931fa696e16b08e071abc419d84a9a3e2970e44` |
| 1.19.1, 1.19.2 | `9cca8b4721f3555b71227b11496e854f063642523fc88d3436a67a485700bfa6` |
| 1.19.3 | `491a263cb1a90f6df555e3a44a5460ce152fb50f3282801ce7f7d7d728419a24` |
| 1.19.4 | `2482065a34566f6ac78fd2522ae95c24bcce894004113cfa585e2df4d5a95c6d` |
| 1.20, 1.20.1 | `4df9b4fe9b542356eccb487405dbca7cb2ae301caf6efc3d42b492c18d5f2240` |
| 1.20.2 | `a7d81a5fd684cb6d2cbdcd395219e8c5a379c8717c7bb5780bc25cf957228b73` |
| 1.20.3, 1.20.4 | `ceb5f522ccb1cabb88edbd33c552864f40d000f01fb8ec605547cc1687494ce` |
| 1.20.5, 1.20.6 | `d62157f9cda67d3002a053e631f03fb9c6b9604be0f8db07371db4a12324fb54` |

### Reset and construction dependencies

In 1.15–1.16.5, dispatched `Player.resetPos()` sets standing pose then delegates to `Entity.resetPos()`. The inherited entity method has a non-null-level guard and calls `setDeltaMovement(Vec3.ZERO)` within it. From 1.17 through 1.20.6, `LocalPlayer.resetPos()` directly sets standing pose, performs its collision/height scan under the level guard, and then calls `setDeltaMovement(Vec3.ZERO)` under that same guard. The supplied source evidence groups identical helper bodies across those release spans; representative exact locations:

- 1.15: `decompiled_minecraft/1.15/mojmap/net/minecraft/world/entity/player/Player.java:482-485` delegates; `.../world/entity/Entity.java:284-295` guarded zero assignment.
- 1.16.5: `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/player/Player.java:468-471`; `.../world/entity/Entity.java:293-304`.
- 1.17: `decompiled_minecraft/1.17/mojmap/net/minecraft/client/player/LocalPlayer.java:632-645`.
- 1.20.6: `decompiled_minecraft/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:629-642`.

Factory endpoints inspected: 1.15 `MultiPlayerGameMode.createPlayer` constructs `new LocalPlayer(..., clientLevel, ...)` (`decompiled_minecraft/1.15/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java:323-324`); the same factory shape exists at 1.16.5 and 1.17.1 with constructor flags; 1.20.6 uses `new LocalPlayer(..., $$0, ...)` at lines 375-380. Respawn handlers pass their current `this.level` into these factories. Across handler variants, `resetPos()` follows old-data/attribute assignment; subsequent handler statements do not assign movement delta.

## Exact identity and unresolved aliases

The source-only evidence reports complete marker/manifest provenance for 21 versions. I independently checked that each of the five formerly missing snapshots (1.16.3, 1.16.4, 1.17, 1.20, 1.20.3) has `mojmap/.decompile-provenance.json`, and that each nested `sources` entry for `ClientPacketListener.java`, `LocalPlayer.java`, `Player.java`, and `Entity.java` matches the file on disk. Their nested input records bind the exact version metadata package, Mojang client JAR, and Mojmap client mappings as detailed in the identity supplement. This closes the five provenance gaps for this claim.

All assigned version labels were covered: 1.15, 1.15.1, 1.15.2; 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, 1.16.5; 1.17, 1.17.1; 1.18, 1.18.1, 1.18.2; 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4; 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6. **No aliases or stable versions are uncovered within the assigned 1.15–1.20.6 span.** Intermediate patch introduction dates were not inferred; no cutover inside this no-copy span is claimed.

COMPLETED
