# Honey contact scan inset comparison — corrected r1

## Verdict

The exact scan cutover is **1.19.2 → 1.19.3**: `Entity.checkInsideBlocks` changes its inset from `0.001` to `1.0E-7`. Exact 1.19 and 1.19.1 sources match 1.19.2 for this method. Exact 1.19.4 retains `1.0E-7` and replaces the double `BlockPos` constructor with `BlockPos.containing`, both using floor semantics; no new candidate boundary is established there.

The coordinate/gate example below is a verified geometric state. The inset admits it in 1.19.3, Honey's side-slide checks pass, and its collision shape does not obstruct it. The normal local-player movement path can reach the open gap as a source-level inference, but no exact ordinary key/tick sequence or displacement trace was verified. Command placement of coordinates is a separate geometric demonstration and is not evidence of an ordinary-input trajectory.

## Exact-source identity and integrity

All five ready markers identify the exact release and `mojmap` output. Their source-manifest SHA-256 values match the manifests; each manifest includes the relevant `Entity.java` and `HoneyBlock.java` hashes below. Artifact manifests distinguish each exact client and Mojmap output. The 1.19 and 1.19.1 provenance records additionally carry version metadata and mapping artifact hashes. For 1.19.2–1.19.4, the ready marker and artifact manifest are the retained identity evidence; their provenance files omit those metadata fields.

| Exact release | Ready client SHA-256 | Mojmap client-mappings SHA-256 | Entity.java SHA-256 | HoneyBlock.java SHA-256 | source-manifest SHA-256 |
|---|---|---|---|---|---|
| 1.19 | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` | `9e31de7fc15b1bac0e58abc441a5e56c320266112feaea981bcffd41dd6201e1` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52` | `9b2dfe745719f4de1ce3fb23baeb24e31623c1fcc249e39bbdb068ad7f600236` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` | `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` | `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec` | `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `bca533675a23fb01ef535e226e7eb780d440d46ebbfd3bf1b98654c82fd0b40a` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

The source manifests' computed SHA-256 values equal their ready markers. The retained artifact manifests list exact-version `client.jar`, `client_mappings.txt`, and `client-mojmap.jar` hashes.

## Difference and geometric/contact witness

- 1.19, 1.19.1, 1.19.2: `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/Entity.java`, `checkInsideBlocks`, 886–912. The candidate corners use `new BlockPos(AABB edge ± 0.001)`.
- 1.19.3: same relative source path, 894–920. All six candidate corners use `AABB edge ± 1.0E-7`.
- 1.19.4: same relative source path, 896–922. It retains `1.0E-7` and uses `BlockPos.containing`; older `BlockPos(double)` reaches `Mth.floor` via `Vec3i(double)`, and `containing` also calls `Mth.floor`.

For Honey at `(0,0,0)`, a standing player at `x=1.2995` has width `0.6F` (`Player.java:126–136`) and `minX≈0.9994999881` after the float width is halved. The old scan's `minX+0.001≈1.0004999881` floors to x=1 and misses Honey; the new scan's `minX+1.0E-7≈0.9995000881` floors to x=0. The AABB overlaps the cell by about `0.0005000119`. Honey's collision shape is `Block.box(1,0,1,15,15,15)` in 1/16-block units (`HoneyBlock.java:28,38–40`), so its maximum x is 0.9375; this candidate overlap is outside the shape and requires no penetration through the collision face.

With feet y=0.9, `onGround=false`, and pre-move `vy=-0.2`, the side-slide gates pass (`HoneyBlock.java:66–82`): y is below `blockY+0.9375−1.0E-7`, vy is below −0.08, and x-center distance 0.7995 exceeds `0.4375+(0.6F/2.0F)=0.737500011920929`. The z gate is irrelevant because the code uses OR. The callback is not conditioned on client/server side: `entityInside` tests `isSlidingDown` and calls `doSlideMovement` before `maybeDoSlideEffects` (`:55–63`). Effect/achievement gates do not suppress the movement write.

## Exact local-player timing and gates

The corrected path is movement-driven; `tryCheckInsideBlocks` is **not** called from `Entity.tick`:

1. On the client, `ClientLevel.tick` sends unremoved non-passenger ticking entities through `tickNonPassenger`, which calls `Entity.tick` (`ClientLevel.java:243–263`). `LocalPlayer.tick` proceeds only when its current chunk is loaded, then calls `super.tick` (`LocalPlayer.java:188–206`). `Player.tick` sets `noPhysics` to `isSpectator()` and calls `super.tick` (`Player.java:224–252`). `LivingEntity.tick` calls `super.tick` and later dynamically calls `this.aiStep()` (`LivingEntity.java:2263–2305`).
2. For the local player, `LocalPlayer.aiStep` processes input and delegates to `Player.aiStep` (`LocalPlayer.java:652–668,814`); `Player.aiStep` delegates to `LivingEntity.aiStep` (`Player.java:496–514`). `LivingEntity.aiStep` calls `this.travel(new Vec3(this.xxa, this.yya, this.zza))` (`LivingEntity.java:2593–2600`). `LocalPlayer.isEffectiveAi()` returns true (`LocalPlayer.java:493–495`), so the controlled-player travel branch is active.
3. For a normal non-swimming, non-flying, non-passenger player, `Player.travel` delegates to `super.travel` (`Player.java:1448–1478`). In airborne ordinary travel, `LivingEntity.travel` calls `handleRelativeFrictionAndCalculateMovement` (`LivingEntity.java:2158–2161`); that helper calls `move(MoverType.SELF, getDeltaMovement())` (`:2201–2205`).
4. `LocalPlayer.move` delegates to `super.move` (`LocalPlayer.java:899–905`). In the normal-physics `Entity.move` branch (`noPhysics == false`; spectators set it true), `Entity.move` first writes the collision-resolved position with `setPos` (`Entity.java:547–579`), then performs collision/rest bookkeeping and calls `tryCheckInsideBlocks` (`:581–657`). `tryCheckInsideBlocks` wraps `checkInsideBlocks` (`Entity.java:685–694`); that method enumerates admitted cells and invokes `BlockState.entityInside` (`:894–920` in 1.19.3; 1.19.2 `:685–694` for the wrapper and `:886–912` for the scan). Thus Honey sees the post-position AABB and pre-travel-gravity velocity. It cannot change the displacement already applied by this `move`.
5. In 1.19.3, Honey writes the transient vector after that displacement: for `vy=-0.2`, `doSlideMovement` writes `vy=-0.05` and scales x/z by 0.25, then resets fall distance (`HoneyBlock.java:91–100`). `Entity.move` next multiplies x/z by block speed factor while preserving y (`Entity.java:657–659`). After the helper returns, ordinary `LivingEntity.travel` subtracts gravity 0.08 and applies vertical drag 0.98 (`:2161–2179`). With no slow-falling/levitation/fluid/no-gravity/unloaded-chunk special branch, the 1.19.3 end-of-tick vertical velocity is `(-0.05−0.08)×0.98 = -0.1274`. If Honey was not scanned, the same setup retains `-0.2` through the move and gives `(-0.2−0.08)×0.98 = -0.2744`. The changed end-of-tick velocity affects a later move; it does not retroactively change the contact tick's position. The horizontal callback factor 0.25 is likewise transient before Entity block-speed scaling and LivingEntity friction.

This validates a measurable **fall/travel** response under the explicit gates. It does not establish a jump trajectory: the witness is airborne and the relevant response changes falling velocity/fall distance.

## Reachability labels

- **Geometric state:** verified by coordinate arithmetic, collision shape and Honey gates above.
- **Command placement:** can demonstrate the candidate position separately, but does not establish an ordinary-input path or the full falling state unless the relevant velocity and gates are also arranged. No command-driven evidence is used here.
- **Ordinary input/contact:** the local-player input → travel → move → contact path exists, and the candidate strip is outside Honey's collision shape, so reaching it is physically unobstructed. This is a source-level reachability inference only. No exact key sequence, initial state, tick-by-tick displacement or exact ordinary-input landing in the ~0.001-wide band was verified; do not call the coordinate example alone an ordinary-input witness.