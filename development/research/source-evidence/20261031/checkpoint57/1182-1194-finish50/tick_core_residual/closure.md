# Movement-core residual closure — 1.18.2 to 1.19.4

## Scope and evidence

Read-only exact Mojmap comparison for these movement-core paths and their direct local-player callers/providers: `Minecraft`, `ClientLevel`, `LocalPlayer`, `Player`, `LivingEntity`, `Entity`, `Input`, and `KeyboardInput`. The five edges are 1.18.2→1.19→1.19.1→1.19.2→1.19.3→1.19.4. Canonical source roots were `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`; no product source, wiki, Git history, runtime, test, build, decompilation, or network was used.

The six exact release manifest identities are those in the frozen roster: 1.18.2 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; 1.19 `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`; 1.19.1 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`; 1.19.2 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; 1.19.3 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; and 1.19.4 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. `source-hashes.tsv` records SHA-256 for all eight assigned paths at all six releases. Those 48 hashes were cross-checked against the frozen adjacent-path roster; there were zero mismatches or unrostered changes. Member rows below refer to those exact file hashes.

`member-dispositions.tsv` is the member-level ledger for the compared movement paths. It records paired source anchors and file hashes for every terminal disposition, accepted operation, or excluded path described here. Existing accepted claims are reused by their immutable source-only review references; they are not redrafted or rebound.

## Edge dispositions

### 1.18.2→1.19

- `Minecraft.tick` and `handleKeybinds`, plus `ClientLevel.tickEntities`/`tickNonPassenger`, retain the local-player tick dispatch. Differences in `Minecraft.runTick` are frame/render work; `Minecraft.tick` changes adjacent to the route are UI component API/text changes. `ClientLevel` adds block-prediction/reconciliation operations, which change world-state production and are outside the equal fixed block/fluid state slice.
- `LocalPlayer.aiStep` changes `Input.tick` to accept a slow-movement multiplier. `KeyboardInput.tick` uses that value instead of its own `0.3F` constant; LocalPlayer supplies `clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus, 0, 1)`. This is movement-affecting but is the new Swift Sneak feature and is modern-only. The vanilla provider and registration route are closed in `input_provider_closure/closure.md`.
- `Player.maybeBackOffFromEdge` adds the outer `request.y <= 0.0` condition. Positive-Y requests now bypass the old horizontal edge-backoff loops. Reuse the accepted operation in `independent_coverage_audit/edge-backoff-review.md`; this is a candidate for the canonical source inventory.
- `Entity.move` switches its contact block lookup to `getOnPosLegacy()`, which preserves the 1.18.2 on-block query (including its old y offset/support fallback). It also removes the outer careful-step check for `stepOn`; the changed 1.19 callbacks for the four existing movement-sensitive blocks retain the careful-step gate internally. This is terminal for old on-foot callbacks under the paired callback review. The changed GameEvent step/hit-ground predicates and actor context can affect event listeners/world state, but do not write the player's position or velocity; server-only vibration/state effects are excluded by the callback report.
- `LivingEntity.aiStep` resets packet-position state via `syncPacketPositionCodec` instead of assigning a raw `Vec3`; this is packet-codec bookkeeping, not a local position/velocity/box write. The exact clientbound correction/respawn routes have their own bounded source-only closure (`packet_state_residual_closure/member-closure.md`). `LivingEntity.updateFallFlying` only renames the Elytra GameEvent. `Player.setItemSlot` changes equip sound/event timing around the inventory slot write, with no movement-state write.

### 1.19→1.19.1

- `LivingEntity.tick` wraps `aiStep` in `!isRemoved()`. The ordinary `ClientLevel` tick route already skips removed entities; `LocalPlayer.isEffectiveAi()` remains true. This does not change an ordinary live LocalPlayer tick.
- `Entity.move` and `checkFallDamage` change GameEvent actor attribution from the responsible entity to the entity itself. No local player movement-state write changes. These callback/event dispositions reuse `callbacks_environment/coverage.md`.

### 1.19.1→1.19.2

- Among the assigned paths, only `Minecraft.java` changes. Its ordinary tick path and call order into `ClientLevel.tickEntities` remain intact; no movement-core member delta was found.

### 1.19.2→1.19.3

- `ClientLevel.tickNonPassenger` changes only the profiler label's registry lookup.
- `LocalPlayer.aiStep` broadens sprint entry for passengers and lets a passenger satisfy the food gate. Under the accepted exact precondition (low food, underwater passenger, held sprint input, then a same-trace dismount with shift held), sprint survives dismount and changes on-foot water travel. Reuse the accepted applicability addendum and finding under `client_input/mounted-sprint-dismount-applicability.md`; retain its limit that an incoming shared-flags update can overwrite the divergent state.
- `LocalPlayer.tick` also sends the current sprint state while the player is mounted. This sends existing local state; it does not itself change position/velocity. Its relevant state/applicability context is covered by the mounted-sprint review above.
- `Player.tick` changes attack-strength reset item equality and removes the Warden tracker tick from its logical-server food/stat branch. The former is attack/damage state; the latter is server-only and neither changes local-player movement.
- `LivingEntity.travel` delegates its slow-fall fall-distance update to `checkSlowFallDistance`. Reuse the accepted fall-distance/edge-backoff consumer closure; this member still reaches `Player.maybeBackOffFromEdge`/`isAboveGround` as already reviewed.
- `Entity.checkInsideBlocks` changes all six AABB sample insets from `0.001` to `1.0E-7`. Reuse the accepted contact-inset operation; unchanged iteration dispatch reaches existing player block responses.

### 1.19.3→1.19.4

- `LocalPlayer.updateAutoJump` changes `fastInvSqrt` to `invSqrt`; reuse the accepted auto-jump operation. The paired `BlockPos` constructor migrations in this method and `moveTowardsClosestSpace` preserve the same three `Mth.floor` operations.
- `LocalPlayer.aiStep` factors sprint eligibility through `canStartSprinting`; its new fall-flying gate affects whether sprint can persist into the following ordinary airborne movement. Reuse the accepted fall-flying applicability addendum, which closes this as part of the existing airborne sprint-speed operation.
- `Player.aiStep` removes the post-super `flyingSpeed` assignment, `LivingEntity` air movement calls virtual `getFlyingSpeed`, and `Player.getFlyingSpeed` supplies sprint/flight speed from current state. For a non-flying on-foot sprint, old `0.02F + 0.006F` rounds to `0.026F` (float bits `0x3cd4fdf4`); new `0.025999999F` is one ULP lower (bits `0x3cd4fdf3`). The provider also changes transition timing. Reuse the independently accepted airborne sprint-speed finding; do not treat these call-site/provider details as a second operation.
- `Player.travel` removes temporary flying-speed save/set/restore around `super.travel`; the virtual override yields the same ability fly speed and sprint multiplier for creative flight. Its new `BlockPos.containing` call preserves flooring. The walking/swimming ordinary route has no new delta beyond the accepted provider operation.
- `Player.isAboveGround`/`maybeBackOffFromEdge` and `Entity.collide` replace `maxUpStep` field reads with the `maxUpStep()` method. The accepted max-up-step scope closure reports no player-on-foot value change.
- `LivingEntity.travel` simplifies `isEffectiveAi() || isControlledByLocalInstance()` to the latter. Trace of the actual dynamic LocalPlayer override closes this generic gate: LocalPlayer returns `true` from `isEffectiveAi()` in both versions; in 1.19.3 the old travel OR is therefore true even though the Entity client fallback for a player on foot is false. In 1.19.4 the Entity fallback delegates to `this.isEffectiveAi()`, making the new gate true too. `LivingEntity.aiStep`'s new `travelRidden` branch requires a controlling passenger; it does not select the ordinary LocalPlayer on-foot path and is excluded vehicle behavior.
- `Entity.checkInsideBlocks` and `getBlockPosBelowThatAffectsMyMovement`, plus LocalPlayer auto-jump/move-towards helpers and Player swimming lookup, migrate to `BlockPos.containing`. The old constructors independently floor x/y/z; no numeric position or collision-predicate delta remains.
- `LocalPlayer.handleNetherPortalClient` changes its confusion timer predicate from `duration > 60` to `!endsWithin(60)`. These gates differ for an infinite duration, but the `LocalPlayer.portalTime` float is consumed by `Gui` and `GameRenderer` only. It is distinct from `Entity.portalTime` (the integer used by portal transition); this is a rendering-only change, excluded from movement scope.

## Direct responses, exclusions, and limits

Direct client position/velocity correction, block-ack restoration, respawn movement-field copying, and effect packet delivery are considered even when producer behavior is not established. The already reviewed packet-state and callback notes are reused at their bounded extent; no server producer, response ordering beyond those notes, or custom server/datapack behavior was inferred. The mounted sprint finding retains its same-packet-trace caveat.

The assigned eight class paths and direct on-foot graph are closed to the dispositions listed in `member-dispositions.tsv`; no new unreviewed movement-core method remains in this bounded slice. This does not terminalize the full 1,624-row catch-all `other` residual, block/fluid/shape providers, resources/tags, all callbacks, or every other source role; it is not a full-pair freeze. Vehicle and non-player physics, damage/attack resolution, food/health production, rendering, and server-side movement remain excluded. No tests, build, launch, or runtime parity claim was produced.
