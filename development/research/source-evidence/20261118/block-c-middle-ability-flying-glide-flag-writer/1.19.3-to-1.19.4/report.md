# 1.19.3→1.19.4 ability-flight glide clear

## Bounded result

Compared the local player’s lawful creative ability-flight branch while already fall-flying with an Elytra equipped in the chest slot, airborne, dry, and not a passenger. This slice has **no movement or post-move glide-clear difference** between 1.19.3 and 1.19.4. This is a bounded no-difference report, not a finding.

The relevant gate in both versions is `Player.travel`: `abilities.flying && !isPassenger()`. It saves the incoming Y velocity, calls `super.travel`, restores vertical velocity as the saved value times `0.6`, resets fall distance, then clears shared flag 7. In the stipulated already-gliding, dry case, `LivingEntity.travel` takes the `isFallFlying()` branch, applies its Elytra velocity math and performs the real `move(MoverType.SELF, ...)` before returning. The player’s post-travel flag-7 clear follows that move. The two versions preserve this order and math.

1.19.3 additionally saves `flyingSpeed`, sets it to `abilities.getFlyingSpeed() * (isSprinting() ? 2 : 1)` during `super.travel`, and restores it afterward. That is a source difference, but it does not change this stipulated glide movement: the glide branch in either `LivingEntity.travel` does not read `flyingSpeed`. It derives the candidate velocity from current velocity, look angle, pitch, and gravity; then multiplies by `(0.99F, 0.98F, 0.99F)`, moves, and returns. Fall-distance reset and flag-7 clear are separate, unchanged post-super operations, in that order.

## Source references

- 1.19.3 Mojmap: `net/minecraft/client/player/LocalPlayer.java:652–814` (`aiStep`, including `super.aiStep()`); `net/minecraft/world/entity/LivingEntity.java:2052–2156` (`travel` glide branch); `net/minecraft/world/entity/player/Player.java:1448–1478` (`travel` ability-flight branch).
- 1.19.4 Mojmap: `net/minecraft/client/player/LocalPlayer.java:645–795` (`aiStep`, including `super.aiStep()`); `net/minecraft/world/entity/LivingEntity.java:1983–2087` (`travel` glide branch); `net/minecraft/world/entity/player/Player.java:1440–1467` (`travel` ability-flight branch).

The local player’s inherited living-entity tick reaches `updateFallFlying()` and then virtual `travel(...)`; `LocalPlayer` reaches the inherited tick through `super.aiStep()`. The no-passenger and ability-flight gate selects the player override. Dry airborne state bypasses the fluid branches, and active flag 7 selects the glide branch. Elytra equipment is an activation/revalidation condition, not a differing condition in the already-active travel branch.

## Pinned source identity

Both source trees are exact decompilations from the same pinned campaign invocation recorded by their provenance files: `decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G`; Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Gson 2.14.0, Mapping-IO 0.9.1. The ready markers say `status: ready`, exact `versionId` 1.19.3 / 1.19.4, `mapping: mojmap`, and `movementDiagnosticsReview` 21 / 22 respectively. Source-manifest and selected source hashes were checked against the published files; all selected-file checks matched.

| Version | Provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 | Ready marker source-manifest SHA-256 |
|---|---|---|---|---|
| 1.19.3 | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

Selected source SHA-256 values, verified against each `mojmap.sources.sha256` manifest:

| Version | `Player.java` | `LivingEntity.java` | `LocalPlayer.java` |
|---|---|---|---|
| 1.19.3 | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| 1.19.4 | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |

Source root read-only: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. No source generation, product inspection, runtime execution, or tests were used.