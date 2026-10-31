# Reconciled movement-route ledger v3 — 1.15.2 → 1.16.5

## Disposition

This source-only overlay reconciles later exact member reports and independent reviews against the corrected ordinal route ledger. It does not claim pair freeze or runtime parity. The full ledger preserves the 2,617 exact-path join, endpoint hashes, route ownership and all unaffected rows. Named later closures replace stale annotations without requiring unrelated same-class methods.

Outputs are `reconciled-route-ledger-v3.tsv`, `remaining-movement-open-v3.tsv`, and `supplemental-evidence-hashes-v3.tsv`. The original base/source input hashes remain in `input-hashes.tsv`; v2 accepted evidence hashes remain in `supplemental-evidence-hashes.tsv`.

The refreshed route classification is **188 movement-reachable, 584 no-movement-dependency and 1,845 excluded** (2,617 total). Both FishingHook path rows are movement-reachable and closed as an unchanged direct client event-31 local-player impulse with synced hooked-entity association. `IronBarsBlock.java` is movement-reachable as a conditional state producer.

## Reconciled closures

- `ClientPacketListener.handleRespawn` is now closed by the accepted receiver candidate/review. In 1.15.2 the replacement player receives copied attributes unconditionally; from 1.16 the receiver gates attribute copy on `shouldKeepAllPlayerData` while copied entity data preserves the sprint bit. Under the candidate's same-client input and no-intervening-attribute-update assumptions, this changes the next eligible grounded movement input. No producer cadence, packet timing, runtime trajectory or server movement claim is required.
- `Level.java`, `LevelAccessor.java`, and `LevelReader.java` are closed for their named movement-facing members. `containsAnyLiquid(AABB)` is equivalent for equal sampled states; Level wrappers preserve the same queried chunks/entities/block entities; LevelAccessor's removed delegations map to same-form lower defaults. Fire/sound, server-only producer, and non-movement member routes are excluded. This is not whole-class equivalence.
- `EntityGetter.java` is closed for vanilla movement-reachable LocalPlayer consumers. Its `getEntityCollisions` behavior is equivalent for LocalPlayer as source, including Boat/Shulker target eligibility; other changed queries are placement/spawn/server or only clip the other source mover. LocalPlayer direct push uses `Level.getEntities` and `Entity.push`, not this changed query. Modded subclasses and non-player source movers are outside this route.
- `MultiPlayerGameMode.handleBlockBreakAck` remains closed as a paired direct receiver. Predicate change begins in 1.16; saved-position and position-only restore begins in 1.16.2. Producer frequency is not a source dependency.
- The client bed-wake position response remains closed across `BedBlock`, `BlockGetter`, and `DismountHelper` with its accepted witness and canonical boundary. Server vehicle-dismount callers remain excluded.
- LocalPlayer’s accepted shift timer, water descent, ground jump, ladder suppression, mounted correction, opened-Shulker escape, Farmland, Soul Sand and Grass Path suffocation responses are reused. The fall-flying request gate is conditionally accepted only for changed effective `CLIMBABLE` membership. Only `updateAutoJump`'s caller-specific collision candidate effect remains open.
- Suffocation findings are accepted at **1.16.1→1.16.2**. Farmland canonical SHA-256: `9636fa6596307fc1caeb8418fb73a2bb1a89549678384a929aa5844ff9ba025d`; final binding ACCEPT: `a19f019761d3143e7bfd85dfa0545794114f4c29fb7d26ae75763740d723f7fb`; witness SHA: `c8277296919e394e4861f2d6d5264e09f7effe54f79db412d6e0968047e8df97`. Soul Sand/Grass Path canonical SHA-256: `4cebd421b1627cbb8007589bb89292229a4d82a15f03715462c8f6cd681fa87d`; ACCEPT review: `3e8b9bbb94e3c54fc3561651ebaf9b348536f1a2dcbf3804d41304fd3ade0baf`. Each is conditional on its stated block state, embedded pre-tick box and surrounding-air inputs; no ordinary-walking or producer reachability is claimed.
- Changed Player members remain enumerated; airborne near-support `maybeBackOffFromEdge` is independently conditionally accepted. Vehicle exit and excluded member groups remain outside movement scope.
- The bounded LivingEntity inventory, fluid and Leaves routes remain closed for their named members under equal sampled state/effect/tag inputs. The accepted CollisionSpliterator canonical-block fast-path witness closes its exact `Entity.move` coordinates and is not transferred to `updateAutoJump`.
- Piston provider formulas and consumer remain closed under equal sampled client block-entity/state input; no server production or update frequency proof is required.

## Exact remaining movement routes

Three exact changed-path rows remain partial:

1. `LocalPlayer.java` — `updateAutoJump` consumes block-collision AABBs, but the already accepted full-cube fast-path fringe witness does not show that a changed candidate intersects either of this method's tested side segments under its actual gates. The body and empty-set-to-always-true entity-filter migration are closed equivalent. Keep only this caller-specific geometry/applicability question open.
2. `WallBlock.java` — fixed-state collision arms are closed. Neighbor-derived `connectsTo`/`updateShape` properties remain conditional on the full old `connectsTo == false` result and the supplied neighbor/upper-block state.
3. `IronBarsBlock.java` — `attachsTo` gains an effective `BlockTags.WALLS` arm; placement/updateShape can produce a changed directional state only if that tag condition holds and old `attachsTo` was false. Fixed-state geometry is closed.

The source route work does not require historical movement on a server, packet producer cadence, or unrelated whole-class equivalence. No builds, tests, game launches, decompilation, product edits, wiki edits or backlog writes were performed.
