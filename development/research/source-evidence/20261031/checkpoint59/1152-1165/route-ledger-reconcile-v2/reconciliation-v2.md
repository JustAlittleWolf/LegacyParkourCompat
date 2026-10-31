# Reconciled movement-route ledger v2 — 1.15.2 → 1.16.5

## Disposition

This source-only overlay reconciles later exact member reports and independent reviews against the corrected ordinal route ledger. It does not claim pair freeze or runtime parity. The full ledger preserves the 2,617 exact-path join, endpoint hashes, route ownership and all unaffected rows. Named later closures replace stale partial annotations; unrelated methods in the same class are not demanded.

Outputs are `reconciled-route-ledger-v2.tsv` and `remaining-movement-open-v2.tsv`. `input-hashes.tsv` binds the base ledger and earlier closure inputs; `supplemental-evidence-hashes.tsv` binds newly added auto-jump and Farmland evidence.

The refreshed route classification is 188 movement-reachable, 584 no-movement-dependency and 1,845 excluded (2,617 total). The two FishingHook path rows are movement-reachable and closed as an unchanged direct client event-31 local-player impulse with synced hooked-entity association. `IronBarsBlock.java` is now correctly reclassified as a conditional movement-reachable state producer.

## Reconciled later closures

- `MultiPlayerGameMode.handleBlockBreakAck` is closed as a paired direct receiver. The predicate change begins in 1.16; saved-position and position-only restore begins in 1.16.2. The exact boundary review is ACCEPTED. Producer frequency is not a source dependency.
- The client bed-wake position response is closed across `BedBlock`, `BlockGetter`, and `DismountHelper` with its accepted witness and canonical boundary. Server vehicle-dismount callers remain excluded.
- LocalPlayer’s accepted shift timer, water descent, ground jump, ladder suppression, mounted correction, opened-Shulker escape and all three suffocation registration responses are reused. The fall-flying request gate is conditionally accepted only for changed effective `CLIMBABLE` membership. `updateAutoJump` remains the precise caller-specific collision candidate question; no whole-class requirement is retained.
- Farmland, Soul Sand and Grass Path suffocation findings are independently accepted at **1.16.1→1.16.2**. Farmland canonical SHA-256: `9636fa6596307fc1caeb8418fb73a2bb1a89549678384a929aa5844ff9ba025d`; final binding ACCEPT: `a19f019761d3143e7bfd85dfa0545794114f4c29fb7d26ae75763740d723f7fb`; witness SHA: `c8277296919e394e4861f2d6d5264e09f7effe54f79db412d6e0968047e8df97`. Soul Sand/Grass Path canonical SHA-256: `4cebd421b1627cbb8007589bb89292229a4d82a15f03715462c8f6cd681fa87d`; ACCEPT review: `3e8b9bbb94e3c54fc3561651ebaf9b348536f1a2dcbf3804d41304fd3ade0baf`. Each claim is conditional on its stated block state, embedded pre-tick box and surrounding-air inputs; no ordinary-walking or producer reachability is claimed.
- Changed Player members are enumerated; airborne near-support `maybeBackOffFromEdge` is independently conditionally accepted. Vehicle exit and other excluded groups remain outside movement scope.
- The bounded LivingEntity inventory closes its changed local movement members under equal sampled state/effect/tag inputs. Fluids and Leaves are likewise closed for their explicitly named fixed-state consumers under equal logical tag/sample inputs; this premise does not assert arbitrary effective payload equality.
- The accepted CollisionSpliterator canonical-block fast-path witness closes the named `Entity.move` query route under its exact coordinates. That witness is not transferred to `LocalPlayer.updateAutoJump`.
- Piston provider formulas and consumer are closed under equal sampled client block-entity/state input; server production or update frequency is not required.

## Remaining movement-reachable rows

Eight exact route rows remain open or partial: LocalPlayer’s one auto-jump question plus seven other routes.

1. `LocalPlayer.java`: `updateAutoJump`’s caller-specific collision candidate effect remains unresolved. Its body and empty-set-to-always-true entity-filter migration are closed equivalent for this caller.
2. `ClientPacketListener.java`: whether the post-death copied sprint flag without the sprint-speed modifier is normalized before the first eligible movement read (`handleRespawn` → LocalPlayer attributes → Player speed/ground travel).
3. `EntityGetter.java`: changed general local-player candidate filtering and remote-entity push/collision selection beyond the closed pose-fit and chunk-cache slices.
4. `Level.java`, `LevelAccessor.java`, `LevelReader.java`: exact remaining movement-query members/callers are not yet enumerated. These are source-member inventory gaps, not a requirement to prove every method in those classes.
5. `WallBlock.java`: fixed-state collision arms are closed. `connectsTo`/`updateShape` neighbor-derived properties are bounded on full old `connectsTo == false` and supplied neighbor/upper-block state; effective tag payload is not inferred.
6. `IronBarsBlock.java`: `attachsTo` gains an effective `BlockTags.WALLS` arm. Placement/updateShape can produce a changed directional state only if the tag predicate is true and old `attachsTo` is false; fixed-state geometry is closed.

The 8 rows above span 8 file paths; Level, LevelAccessor and LevelReader are three separately tracked files sharing one bounded inventory issue. No route requires historical movement on a server, packet producer cadence, or unrelated whole-class equivalence. The route classification remains 188 movement-reachable, 584 no-movement-dependency and 1,845 excluded across the 2,617 changed-path ledger rows. No builds, tests, game launches, decompilation, product edits, wiki edits or backlog writes were performed.
