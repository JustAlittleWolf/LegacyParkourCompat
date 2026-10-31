# Independent source review — residual local-player tick candidates

Pair: Minecraft 1.15.2 Mojmap → 1.16.5 Mojmap. This is a source-only review of the three claims in `../local-player-tick-uncovered-members/report.md`.

## Source identity and hashes

The canonical roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`. Their `mojmap.ready.json` files identify versions 1.15.2 and 1.16.5 and Mojmap. Their `mojmap.sources.sha256` files hash to `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, matching the ready records and the pair inventory. The referenced `.native-source/20261030/1152-1165-pool33/manifest-verification.json` has SHA-256 `673bc4bd8b0317d8fdc1f5b4f0ff8b8cd4eec105e92b272a078f868f26571085`; it reports 3,332 and 3,523 files with zero missing, unlisted, or mismatched entries.

All six rows in the candidate `source-hashes.tsv` were recomputed from the canonical roots and match both the ledger and their exact path entries in the corresponding source manifests. These are the LocalPlayer, LivingEntity, and Player files for each release. Every row in the candidate `input-hashes.tsv` also recomputes successfully. Minor ledger-path correction: the report text says the manifest verification is in its own directory; it is actually the pool33 file pinned by `input-hashes.tsv`.

## Candidate verdicts

### 1. Shift-held clears sprint-trigger timer — ACCEPT

In 1.15.2 `LocalPlayer.aiStep`, positive `sprintTriggerTime` is decremented at lines 627–629, and the item-use branch may clear it at 637–641; no shift clear occurs before the automatic sprint branch at 657–671. In 1.16.5 the corresponding timer decrement and item-use branch are at 629–647, followed by `if (bl2) this.sprintTriggerTime = 0` at 663–665, before the sprint branch at 668–681. `bl2` is captured from `input.shiftKeyDown` earlier in each method. The sprint branch excludes `bl2`, so this cannot start sprint during that shift-held tick. If a positive double-tap window has more than one tick remaining on entry, 1.15.2 retains its decremented positive value while 1.16.5 clears it; after shift is released, an eligible forward press can start sprint immediately in the old source or initialize a fresh seven-tick window in the new source. The later sprint-state change is consumed by the ordinary player movement path. The report's narrow timing condition is material: a timer that reaches zero during the shift-held tick does not produce this distinction.

Exact source refs: 1.15.2 and 1.16.5 `net/minecraft/client/player/LocalPlayer.java`, `aiStep`, ranges above; `hasEnoughImpulseToStartSprinting` is the LocalPlayer helper at 1.15.2 lines 977–980 and 1.16.5 lines 989–992.

### 2. Ability-flight gate on water shift descent — ACCEPT

1.15.2 `LocalPlayer.aiStep` calls `goDownInWater()` for `isInWater() && input.shiftKeyDown` at lines 723–725. The 1.16.5 equivalent at 733–735 adds `isAffectedByFluids()`. In 1.16.5 `Player.isAffectedByFluids()` returns `!abilities.flying` (`net/minecraft/world/entity/player/Player.java:1004–1007`); the 1.15.2 direct call has no corresponding ability-flight gate. Both `LivingEntity.goDownInWater` implementations add `(0.0, -0.04F, 0.0)` to delta movement (1.15.2 lines 1819–1821; 1.16.5 lines 1898–1900).

For an ability-flying local player in water with shift held, the old call changes incoming Y velocity by `-0.04`; the new call does not. Both `Player.travel` implementations capture incoming Y velocity before flying travel and restore 60% of that captured value afterward (1.15.2 lines 1463–1470; 1.16.5 lines 1402–1409), preserving a `-0.024` difference in the post-travel Y velocity for this isolated input response. This is a distinct LocalPlayer operation from water-current handling. The claim is limited to the call's delta-velocity effect; it does not assert a fixed position delta independent of fluid travel and collision.

### 3. Ability-flight gate on inherited ground-jump selection — REVISE

The source difference and movement consequence are valid. In 1.15.2 `LivingEntity.aiStep` tests `jumping` and can call `jumpFromGround()` when grounded and `noJumpDelay == 0` (`net/minecraft/world/entity/LivingEntity.java:2265–2278`). In 1.16.5 the selector is guarded by `jumping && isAffectedByFluids()` and otherwise resets `noJumpDelay` (`:2427–2451`). The Player override returns false while ability-flying (`net/minecraft/world/entity/player/Player.java:1004–1007`). Both Player travel implementations retain 60% of incoming Y velocity during ability-flight as cited above, so a ground-jump impulse selected in 1.15.2 is retained in that movement path while 1.16.5 omits it.

Revise the call-order description: LocalPlayer does **not** set its `jumping` field before its outer `super.aiStep()` call. During that inherited `LivingEntity.aiStep()` execution, `LivingEntity` invokes virtual `serverAiStep()` before entering the jump-selection block (1.15.2 `LivingEntity.java:2253–2266`; 1.16.5 `:2414–2427`). The `LocalPlayer.serverAiStep` override copies `input.jumping` into `this.jumping` when this is the controlled camera (1.15.2 `LocalPlayer.java:607–616`; 1.16.5 `:609–618`). This precise order supports the same reachable path, but the report's phrase “before `super.aiStep`” should be replaced with “inside inherited `LivingEntity.aiStep`, through `serverAiStep`, before jump selection.”

For a dry, grounded, ability-flying local player with jump input and a zero delay at the selector, the old code applies the ground jump while the new gate skips it and clears the delay. This is an endpoint difference only; the intermediate introduction patch remains unknown. The specific consequence requires the player still to be ability-flying when `LivingEntity.aiStep` reaches `Player.travel`; `LocalPlayer.aiStep` may turn flight off afterward when grounded, which does not undo the current tick's travel.

## Review boundary

No product implementation, wiki material, game runtime, tests, build, decompilation, or intermediate release was examined. The verdicts establish source behavior only, not accepted pair coverage, implementation, or runtime parity.
