# Route reconciliation — Mojmap 1.15.2 to 1.16.5

Source-only route reconciliation. This does not claim complete pair coverage, a pair freeze, implementation, or runtime parity. The ledger joins all 2,617 unique exact-case route paths to the corrected inventory and retains the ordinal path keys, delta, endpoint hashes, and presence fields.

Route totals: 187 movement-reachable; 585 no movement dependency; 1845 excluded. Whole-ledger dispositions: 325 CLOSED_IN_SCOPE, 24 PARTIAL, 430 OPEN, and 1838 unchanged. Of the 779 independent-review rows, 325 are closed, 24 partial, and 430 open; 454 remain unresolved. The movement-positive crosswalk retains its exact 60 paths; 27 remain unresolved (5 OPEN, 22 PARTIAL). Exact lists are `remaining-open-rows.tsv` and `movement-positive-open-groups.tsv`.

`OriginalReview` preserves earlier route annotations verbatim. Current dispositions and member notes apply later exact evidence only to named members and dependencies; no neighboring or whole-class closure is inferred. `input-hashes.tsv` binds this revision's sources and reviews.

## V3 route dispositions

The v3 audit and member table are bound by SHA-256 `716fd06423a861eaa55b93583327e5d1d611b1e4e5abeda8b2d3c5e2af7a79dc` and `9e272762ccc9d959452843328fa765d5d0e827d4fb666d2f2edcd8cf5eeb0ad7`. The table's 26 distinct path/member dispositions were applied individually (two separate `Player.java` slices remain on one exact path row). The changed `Vec3` coordinate helpers are covered by the accepted bed-wake route, while `zRot` is renderer-only; `CollisionContext.canStandOnFluid` and `EntityCollisionContext.canStandOnFluid` are false for LocalPlayer. These three rows are closed as movement blockers without claiming all methods in any neighboring class. The exact chunk-cache, Firework attached-response, and canonical block-collision witnesses are conditionally closed under their stated equal sampled inputs; no packet frequency or event setup proof is required. The v3 report's route-count snapshot predates the separately reconciled exclusions for Boat, Chorus Fruit, thrown ender pearl, server-side player/listener routes, and remote entity interpolation; the current TSV recount above is authoritative for this revision.

## Accepted candidates and named remaining routes

The bed-wake pair witness SHA `d0fdc5ea63ecf1b2cb9f637255280c7873a4971501104b2096302175927e998e` and canonical finding SHA `dfcee13fdf26334878f14bb2a6f5cb8cdd4472c76e1393a2642c156103e18eaf` are ACCEPTED. The paired `BedBlock`/`BlockGetter` response reaches local `setPos`; these source members are bounded without establishing the exact intermediate patch or full pair inventory. `DismountHelper` stays partial because the same helper has both excluded server vehicle-dismount use and eligible bed-wake use.

The three LocalPlayer findings and boundary review are ACCEPTED; final review SHA `5430db31cf509b268bac880c64f8811c74873d3edb50e76d9008ca9e057ffa5b` is now included in `input-hashes.tsv`. They close only the sampled-shift sprint timer clear, ability-flight water descent gate, and inherited ability-flight jump gate. Other LocalPlayer, LivingEntity and Player movement-member inventories remain partial/open as listed; `Player.maybeBackOffFromEdge` awaits independent ordinary-tick reachability review.

Other concrete residuals include the direct block-break acknowledgment correction predicate, ordinary-death sprint-attribute normalization, general entity collision filtering, exact Level/world query member inventory, existing-block registration consumers, WallBlock neighbor-derived state, and the client sampled piston state. Fixed-state fluid formulas are closed; effective WATER/LAVA/LEAVES tag membership and sampled-state presence remain conditional inputs. The 425-row no-movement scan remains unclassified.

## Outputs and limits

- `reconciled-route-ledger.tsv`: all exact route rows and preserved source identity columns.
- `remaining-open-rows.tsv`: unresolved rows from the exact 779-path review set.
- `movement-positive-route-groups.tsv` / `movement-positive-open-groups.tsv`: exact crosswalk and unresolved subset.
- `no-movement-open-member-scan.tsv`: unchanged unclassified residual checklist.

No product/wiki/backlog/history source was used. No tests, build, game/TAS/Gym/server launch, decompilation, or runtime validation was performed. The v3 audit remains INCOMPLETE; no pair freeze is claimed.
