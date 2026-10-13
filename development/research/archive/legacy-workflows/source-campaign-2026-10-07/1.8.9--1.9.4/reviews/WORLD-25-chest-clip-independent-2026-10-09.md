# Independent source review: WORLD-25 Chest clipped-displacement witness

- **Decision:** ACCEPT — the conditional Chest clipped-displacement witness only.
- **Review date:** 2026-10-09.
- **Reviewed candidate commit:** `1faac86dc6a2bd8f739a73d4bd877bcf8b4b7a9c`.
- **Reviewed finding:** `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-25-mutable-bound-writers-anvil-chest.md`; Git blob `2c2312acf202eb02fb14634ca3eed76bc1c93b0b`; raw SHA-256 `e50e3f8bc15e783bdbb7d5510bf6cee5925ba5cedf11025565507e918a428a20`.
- **Pair report binding:** `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/run.md`; Git blob `ecf391733ffd7157eb14285865f5115d99d41219`.
- **Review constraint:** source-only; no implementation or wiki-audit material was inspected. This decision does not review or accept the separate Anvil witness in the same finding file.

## Source publication and hashes

The read-only readiness markers identify the exact 1.8.9 and 1.9.4 Feather Gen 2 build 2 source trees. Marker SHA-256 values are `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` and `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`. Their source-manifest SHA-256 values match the marker fields and the pair report: A `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. Artifact-manifest identities recorded by the markers are A `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and B `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

I recomputed each source file below and matched it to both the finding/run citation and its row in the corresponding source manifest:

| Release | Source file | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/block/ChestBlock.java` | `8072bc317398297d1327a3be4edd8a66381a1a1021a6c4a8f3fbeebca2891d51` |
| 1.8.9 | `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| 1.8.9 | `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| 1.8.9 | `net/minecraft/util/math/Box.java` | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` |
| 1.9.4 | `net/minecraft/block/ChestBlock.java` | `0fc74197f3f4710268944bb802a1621dc8ee359c29289ec8ce878ad0b2b834ce` |
| 1.9.4 | `net/minecraft/block/Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `net/minecraft/world/World.java` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| 1.9.4 | `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| 1.9.4 | `net/minecraft/util/math/Box.java` | `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198` |
| 1.9.4 | `net/minecraft/block/state/StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

The pair report records the original derived mapped JARs as unavailable and their equivalence as unproven. This acceptance is limited to the published, hashed decompiled-source bodies above; it makes no bytecode-equivalence claim.

## Source basis

In A, `ChestBlock` initializes its singleton bounds to the single-chest box (constructor lines 32–38). Its `updateShape` checks north, south, west, then east and writes the corresponding bounds into that same block object (lines 56–68). A `Block.rayTrace` invokes virtual `updateShape` before reading those bounds (lines 456–464), and `World.rayTrace` dispatches through that block ray-trace path (lines 633–653). The player targeting path reaches this writer: `GameRenderer` calls `entity.rayTrace` at line 325 (`GameRenderer.java` SHA-256 `69b0007074901e5bd617f35039f122f21c67402340fe08b3daea1b316c6e7940`), and inherited `Entity.rayTrace` reaches `World.rayTrace` (lines 1029–1034). This establishes a reachable writer and its precedence; it does not establish that any particular movement query is preceded by that writer or that it remains the latest writer.

A's player collision path consumes whatever bounds are present. `World.getCollisions` scans the block cells covered by the expanded movement box and calls `Block.addCollisions` without invoking `updateShape` (lines 891–925); generic `Block.addCollisions` builds the world-space box from the block object's current min/max fields and appends it if the query intersects (lines 339–350). Thus a prior update on another position can affect a later query of the same registered Chest singleton.

In B, `ChestBlock.getShape` resolves the neighbor relationship from the queried position on each call (lines 63–73), with the five shape constants at lines 31–38. B's `World.getCollisions` dispatches the candidate state to `addCollisions` (lines 899–940); `StateDefinition` delegates collision/shape lookup through that state to its block (lines 359–370), and generic `Block.addCollisions` moves the returned shape to the candidate position and applies the intersection check (lines 337–354). The two paths therefore differ exactly in the state source claimed: A singleton's last written bounds versus B's current candidate-state/world shape.

The stated Chest example is arithmetically valid under its explicit conditions. A north-neighbor update writes local Z `[0,0.9375]`; the isolated current chest has B local Z `[0.0625,0.9375]`. For the candidate at `(0,10,0)`, the player box has strict X/Y overlap with both boxes and begins at `maxZ=-0.2`. Positive-Z `Box.intersectZ` clips against a box whose `minZ` is ahead of the player to `block.minZ - player.maxZ`, yielding `0 - (-0.2) = 0.2` in A and `0.0625 - (-0.2) = 0.2625` in B. The requested `0.4` exceeds both limits. The paired `Entity.move` methods preserve Y/X/Z clipping order and compute the step-entry flag after Y clipping; with `onGround=false` and requested/clipped Y both zero, that flag is false, so the step branch cannot replace this result. A and B `World.getCollisions` scan ranges include `(0,10,0)` for the stated box.

This reaches player movement: `LivingEntity.mobTick` hands movement input into `moveRelative`, whose travel branches call the inherited `Entity.move`; the run's `COLL-01-AXES` dependency independently compares the paired axis-clipping bodies. The witness only establishes one possible movement query, under the submitted box/delta and world setup.

## Conditions and limits

The A stale north bounds must be the last write to the same Chest singleton before the query; a later A updater can change the result. The target chest must be isolated at query time. The candidate block must be included by the world scan (including a loaded chunk and ordinary in-border location). No other collision box may clip positive Z more tightly. The claim is not a stable per-state result, mandatory tick ordering, or trajectory/parity result. No runtime test was performed or authorized.

The `WORLD-25-CHEST-CLIP` source slice supports this conditional witness, but the pair remains `active`: the run's collision, world-movement and state inventories remain pending, and no full-pair freeze/audit has passed. Runtime validation is not performed. A duplicate B-evidence bullet in the slice repeats the same Chest/World/Block/Entity citation; this is harmless editorial duplication and does not affect the reviewed Chest proof.
