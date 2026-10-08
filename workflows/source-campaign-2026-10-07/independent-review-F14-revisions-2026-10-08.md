# Independent blind review: F-14 snapshot history

- Pair: 1.21.4 → 1.21.5; the pair remains `PARTIAL`.
- Scope: two immutable revisions of the same F-14 finding. Their decisions below are separately bound; the later revision does not overwrite the earlier one.
- No implementation, other pair finding, wiki or MCPK material was read.

## Revision 1 — REQUEST CHANGES

- Commit: `6f0a567c47d57071e1e22c89efaeb74fbc531491`
- Path: `workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-14-sleep-fall-flying-pose-priority.md`
- Blob: `fe916f3934e06a73348e6332b885f2c3041aa792`
- Raw SHA-256: `c64be4794ee553e3adf11e0b1bb3f57428e2fb9e32292df940526279d44ae14c`

The corrected 1.21.5 `LivingEntity.java` hash is accurate and all source hashes cited in this revision match the exact ready manifests. The stated ability-flight-off precondition is also correct. However, this snapshot does not cite the `Player.canGlide()` override that enforces it (`Player.java` A 1474–1475, B 1452–1453). More importantly, its own reachability section leaves GLIDER/EQUIPPABLE producers open and does not close the ordinary writer path that sets shared fall-flying flag 7. The finding-specific coexistence witness therefore lacks its item/equipment and flag-producer dependencies.

## Revision 2 — REQUEST CHANGES

- Commit: `eaa873aa5402065fb7f0070d11521b4e054f7683`
- Path: `workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-14-sleep-fall-flying-pose-priority.md`
- Blob: `468b181d376ccd5fb014c1a3529f3f1412352ae8`
- Raw SHA-256: `f95d6b53ebd3a43ad775d72998a7dac9e8a9a9fa9296b1d578b9abf99df7353d`

The only change from revision 1 is the addition of the exact A/B `Player.canGlide()` source ranges; those ranges and file hashes are correct. The remaining state/equipment dependency is still open in the finding itself, so this newer immutable revision is not accepted yet.

## Source checks and correction request

The bed path itself is proven. In both versions `BedBlock.useWithoutItem` returns on the client and invokes `Player.startSleepInBed` on the server. `ServerPlayer.startSleepInBed` checks range, obstruction, natural dimension, time and safety but has no on-ground or fall-flying rejection. `LivingEntity.startSleeping` sets sleeping pose/position and zeroes delta movement without clearing shared flag 7. On the next server tick, `LivingEntity.aiStep` calls `updateFallFlying` while flag 7 remains true; the updated `Player.canGlide` gate confirms it remains valid only with abilities flight off, airborne, not riding, no Levitation, and an equipped glider that will not break. The player tick then updates pose after its superclass tick. A and B select `FALL_FLYING` and `SLEEPING` in opposite priority orders when both states persist and the fit checks pass. The bed position leaves `0.125` blocks between its collision top (`9/16`) and the sleep position (`11/16`); the first fall-flying step from zero velocity moves downward by less than that gap, so it need not ground the player before pose update.

To close reachability for a fresh snapshot, add paired evidence for (1) the reachable flag writer: `Player.tryToStartFallFlying`/`startFallFlying`, the local jump-input call, and `ServerGamePacketListenerImpl`'s `START_FALL_FLYING` handler; and (2) a vanilla equipped item satisfying `canGlideUsing`, such as `Items.ELYTRA` carrying `GLIDER` and chest `EQUIPPABLE` components. The verified source ranges are A/B `Items.java` 971–982 / 989–1001, A/B `LivingEntity.canGlideUsing` 3626–3633 / 3622–3629, `Player.tryToStartFallFlying` and `startFallFlying` A 1528–1539 / B 1507–1518, `LocalPlayer.aiStep` A 749–750 / B 770–771, and server command handlers A 1512–1515 / B 1596–1599. Their exact source hashes match the respective 1.21.4 and 1.21.5 Mojmap manifests. Record these as closed finding-specific dependencies in the replacement snapshot; keep the broader pair inventory partial.

The corrected source publications remain 1.21.4 Mojmap (source manifest `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`, artifact manifest `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`) and 1.21.5 Mojmap (source manifest `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, artifact manifest `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`).

## Limits

No runtime trajectory was run. The pose-priority source difference is supported, but the two F-14 immutable revisions above remain `REQUEST CHANGES` for snapshot acceptance until the ordinary glide-state/equipment path and its dependencies are bound in a new snapshot.
