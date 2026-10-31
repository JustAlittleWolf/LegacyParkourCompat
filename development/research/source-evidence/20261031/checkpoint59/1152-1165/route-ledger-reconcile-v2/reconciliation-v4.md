# Reconciled movement-route ledger v4 — 1.15.2 → 1.16.5

## Disposition

This source-only overlay reconciles named exact-member reports and independent reviews with the corrected ordinal path ledger. It preserves all 2,617 changed-path rows, route IDs, and endpoint hashes. It does not claim pair freeze or runtime parity.

Outputs are `reconciled-route-ledger-v4.tsv`, `remaining-movement-open-v4.tsv`, and `supplemental-evidence-hashes-v4.tsv`. Earlier source and accepted-finding pins remain in `input-hashes.tsv` and the v3 supplemental hash file.

Route crosswalk totals are **188 movement-reachable, 584 no-movement-dependency, and 1,845 excluded**. These add to the 2,617 non-identical path rows. Both FishingHook source rows are correctly movement-reachable and closed as an unchanged direct client event-31 LocalPlayer impulse under equal synchronized hook/owner inputs.

## Reconciled scope closures

- The accepted ClientPacketListener respawn receiver changes sprint-attribute copying beginning in 1.16 under the `shouldKeepAllPlayerData` gate; the copied sprint bit can therefore disagree with the replacement player's movement-speed modifier on the stated same-client inputs. The candidate and independent ACCEPT are bound. No server producer, packet frequency, or timing proof is required.
- Exact movement-facing Level, LevelAccessor and LevelReader members are closed by the route audit: same-form query wrappers/delegations, equivalent `containsAnyLiquid` under equal sampled states, and direct exclusions for non-movement helper consumers. This is member-bounded; no whole-class claim is made.
- EntityGetter is closed for vanilla LocalPlayer-source collision consumers. Its changed candidate method has equivalent behavior for LocalPlayer as source; other changed members either affect another source mover, placement/spawn, or server callers. LocalPlayer direct push has a separate `Level.getEntities`/`Entity.push` route.
- MultiPlayerGameMode block-break acknowledgment, direct bed-wake position response, accepted LocalPlayer movement findings, the three suffocation findings, Player and LivingEntity member inventories, fixed-state fluid/Leaves providers, the named CollisionSpliterator Entity.move witness, and equal-input piston providers remain closed at their prior exact scopes. Producer timing and unrelated class methods are not required.
- Final Wall/IronBars scope erratum SHA-256 `DE2F616E8E68AC0887E50FB75B0A0FED84AFAEB77D4B40F36DBA942ECFE63777` excludes the `getStateForPlacement`/`updateShape` connection predicate outputs as generated vanilla block-state production. Equal supplied-state WallBlock and IronBars/CrossCollisionBlock collision providers are closed. Their previous conditional generated-state displacement witnesses do not count as eligible movement gaps.

## Exact remaining movement route

One row remains partial: `LocalPlayer.updateAutoJump`.

The method body and its empty-set to always-true entity-filter migration are paired equivalent. A new proof argues that the canonical full-cube collector fringe cannot intersect its tested side segments for standing, crouching, fall-flying, swimming, and spin-attack dimensions with height at least 0.6. That proof is not yet independently accepted; the current independent review is bound to an older hash and is REVISE. The proof explicitly leaves SLEEPING and DYING pose dimensions, height 0.2, unresolved. Keep this one fast-path applicability question open until an independent review binds the current proof and resolves or retains the 0.2-height case. Do not transfer the separate Entity.move witness.

No historical movement on a logical server, packet producer cadence, or unrelated whole-class proof is required by the remaining route. No builds, tests, game launches, decompilation, product changes, wiki changes, or backlog writes were performed.
