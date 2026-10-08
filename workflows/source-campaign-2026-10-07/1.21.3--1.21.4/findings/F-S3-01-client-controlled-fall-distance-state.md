# F-S3-01: 1.21.4 leaves client-controlled player fall distance unchanged on landing

- Older version A: 1.21.3
- Newer version B: 1.21.4
- Mechanic / coverage slice IDs: collision completion and player movement state; S3-06
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.3, 1.21.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: artifact A in `run.md`; `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, `Entity#move()`, lines 619-702, SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`. After collision, the check is guarded by `!level.isClientSide() || isControlledByLocalInstance()`. LocalPlayer's `isEffectiveAi()` returns true, so its local-instance condition is true on the client. `Entity#checkFallDamage()`, lines 1216-1231, resets fall distance when grounded; the `LivingEntity#checkFallDamage()` override, lines 341-371, delegates to it. `Player#isControlledByClient()`, lines 2146-2148, returns true, but A has no controlled-by-client exclusion here.
- B manifest: artifact B in `run.md`; `Entity#move()`, lines 627-713, SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`. The call now additionally requires `!this.isControlledByClient()`, which excludes Player/LocalPlayer because the Player override still returns true. `Entity#checkFallDamage()`, lines 1237-1252, still resets fall distance when grounded; `LivingEntity#checkFallDamage()`, lines 339-369, is otherwise unchanged. `Player#isControlledByClient()`, lines 2166-2168, returns true.
- Local-player identity/control evidence: A `LocalPlayer#isLocalPlayer()` / `isEffectiveAi()`, lines 347-349 / 478-480, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`; B lines 352-354 / 483-485, SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`.
- Movement consumer: A/B `Player#maybeBackOffFromEdge()` and `isAboveGround()`, A lines 1066-1123, B lines 1069-1126, SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` / `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`. When the player is holding shift and its requested vertical movement is non-positive, `maybeBackOffFromEdge()` consults `isAboveGround(maxUpStep)`, whose non-grounded branch compares `fallDistance < maxUpStep` before checking support geometry.

## Source-level difference

On A's client-local movement path, a grounded collision invokes `LivingEntity.checkFallDamage()` and then `Entity.checkFallDamage()`, which resets the player's fall distance. On B, `Player.isControlledByClient()` is true, so the new negative guard skips the whole callback for the local player. B therefore retains the previous fall-distance value after landing. This is a local movement-state change distinct from whether fall damage is calculated or applied.

## Reachability and dependencies

The ordinary player travel path calls `Entity.move()` after movement collision resolution. On A, LocalPlayer's `isEffectiveAi()` makes it controlled by the local instance; on B the added controlled-by-client guard excludes the Player. The reset's state is read by `Player.maybeBackOffFromEdge()`. A reachable contrast is a player that lands after accumulating fall distance greater than its `maxUpStep`, then is airborne again while holding shift during a non-positive-Y movement: A starts the new descent with the landing reset, while B still has the prior larger value. That value can make `isAboveGround()` false before the support-geometry check, selecting a different edge-backoff branch.

## Consequence and uncertainty

The source proves that B does not clear this client-side player movement state through the landing collision callback and that the state feeds a crouch-edge movement guard. It does not establish the resulting trajectory in a runtime simulation. Fall-damage calculation, attack/damage effects, and any health outcome are outside this finding. The exact release introduction is unknown within the endpoint interval.

## Handoff

This is one client-controlled player movement-state delta. Collision-shape and full step-up resolution remain under the pair's open source inventory; pair-level freeze and independent source audit remain open.
