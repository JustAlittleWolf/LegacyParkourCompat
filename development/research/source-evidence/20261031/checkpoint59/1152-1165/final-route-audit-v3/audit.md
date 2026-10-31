# Independent source-route audit v3 — Mojmap 1.15.2 → 1.16.5

## Verdict: INCOMPLETE — do not freeze the source pair

The requested route artifacts and their joins are consistent. The new member evidence closes several formerly broad or stale row gates, but eligible source paths remain open: the block-break acknowledgment response predicate; the ordinary-death respawn attribute normalization gate; unpaired LocalPlayer/LivingEntity/Player movement members; the local entity-collision query path; and remaining block registration/neighbor-update movement providers. The exact 33-row unresolved movement-positive list from the current ledger is copied to `ledger-unresolved-movement-positive-groups.tsv` (SHA-256 below). Some rows are overbroad or now have an exact conditional closure; this audit identifies those cases so they are not mistaken for a whole-class proof requirement.

This verdict is source coverage only. Accepted individual findings remain accepted only within their bound claims. No implementation or runtime parity is inferred. Server producer frequency, packet timing, and equal-input state producers are not required to establish a conditional client movement response. The 425 no-movement rows remain unclassified; this audit does not treat them as 425 eligible movement findings or impose whole-application caller proof.

## Bound route artifacts and joins

| Artifact | SHA-256 |
|---|---|
| Current reconciled route ledger | `aa66f881f36b3fb69a1bebe640c43e8700bdc1563ca4b7ea0f7e17c7691a536e` |
| Current OPEN/PARTIAL rows | `857a0944bac6891bd581e8174389aadcae442db7b97e269c29e0cb99d2028c56` |
| Movement-positive crosswalk (60 paths) | `9aad622cbce92d8b5753c22dd0494d6057498c9a74f126ab2836b967a3480681` |
| Ledger unresolved movement-positive subset (33 paths) | `e34e0e9e2adcb637587c75761b7d2d0857ee45076a3be413273e68e5bf9c2a8e` |
| Current input-hash manifest | `3a0ef40a24304ffb5e63eb59f17f7cd438b70fe4fb6ae72c1bc3738b28a12797` |
| Reconciliation narrative | `96d9744c7285f841dfbd1dc58fa654aee51273a61bce9a9f0eaffefd43e9f87e` |
| 425-row no-movement open-member scan | `0d318af387aa8c3d6282204989004b1f1c0f33d8ce0f9a9a14a25d613d273441` |
| This copied 33-row list | `e34e0e9e2adcb637587c75761b7d2d0857ee45076a3be413273e68e5bf9c2a8e` |

All 70 entries in the latest input-hash manifest rehash successfully (zero missing/mismatched files). The final LocalPlayer canonical/boundary review is a later independent input: `local-player-tick-candidates-review/final-canonical-boundary-review.md`, SHA-256 `5430db31cf509b268bac880c64f8811c74873d3edb50e76d9008ca9e057ffa5b`. It is not present in the 70-entry manifest, and the reconciliation narrative still says final canonical binding is pending. I verified the review separately and bind it explicitly here; the ledger/narrative should be refreshed if it is to carry that binding itself.

Independent join checks, all with zero errors:

- Ledger: 2,617 unique ordinal `(Delta, exact-case Path)` keys. Dispositions: 319 `CLOSED_IN_SCOPE`, 31 `PARTIAL`, 429 `OPEN`, 1,838 unchanged. Routes: 193 movement-reachable, 585 no-movement-dependency, 1,839 excluded.
- Remaining rows: 460, each exactly OPEN/PARTIAL with matching endpoint hashes.
- Prior checklist: all 779 rows join by exact `(Delta, Path)` and both endpoint hashes; latest dispositions are 319 closed, 31 partial, 429 open.
- Movement crosswalk: all 60 keys and endpoint hashes join exactly; 27 closed, 4 open, 29 partial. Its 33-row residual is exactly the 4 OPEN + 29 PARTIAL subset, with no missing, extra, or status/hash-mismatched keys.
- The 425-row no-movement scan joins to 425 OPEN `no movement dependency` ledger rows with matching endpoint hashes. The scan has no disposition/closure field; the rows remain unclassified and are not promoted to either eligible candidates or proven exclusions.

## Evidence revisions checked

- Bed wake: paired witness `d0fdc5ea63ecf1b2cb9f637255280c7873a4971501104b2096302175927e998e`; witness renewal ACCEPT `3a875d9eb05349417a49a3f6efb77e60cb6a244395076c482ca23ff7f264e54d`; canonical finding `dfcee13fdf26334878f14bb2a6f5cb8cdd4472c76e1393a2642c156103e18eaf`; canonical renewal ACCEPT `fa9976eb52cf856750e57d8f17447d7df6cf9a6b353c83a3bc1f992b122822a3`. This establishes the conditional paired non-bunk bed-wake position response, including the candidate order, floor/support arithmetic and collision gates. The missing intermediate patch boundary is separate from endpoint 1.15.2→1.16.5 coverage and is not a reason to demand more bed-method or DismountHelper closure for this pair.
- Fluid: `fluid-movement-route-closure/route-closure.md`, SHA-256 `249e26eb1bb937067cd7c3d641d762e919244525f824d69cf7990cee72b79606`, closes fixed-sampled-state occupancy/height/flow/current and the named `LivingEntity.aiStep`/`travel` consumer math. Residuals are conditional effective WATER/LAVA and LEAVES tag membership and which sampled block/fluid state is present. No changed server production frequency or packet timing is required to assess the same-input client read. Do not report the fixed formulas as open or claim unspecified datapack membership.
- LocalPlayer: candidate report `a0ad62636b1995748e8633a926b54625cae0310531519472362dd8510c919db1`; renewed review `51b9024bed4dc2d802640a12fd472b3160cb88455e7a06844985dc07d3020353`; 1.16.0–1.16.5 boundary report `061f29239fc24920c635abf26b98e0daf56f2228430c2a44519e6bfb00d3d07f`; final canonical/boundary review `5430db31cf509b268bac880c64f8811c74873d3edb50e76d9008ca9e057ffa5b` ACCEPTs all three immutable findings. These close only (1) `LocalPlayer.aiStep` sampled-shift clear of `sprintTriggerTime`, (2) ability-flight gate on water shift descent, and (3) inherited `LivingEntity.aiStep` ability-flight gate on jump selection. They do not close other eligible LocalPlayer/LivingEntity/Player members as a class.
- Direct responses: `direct-response-route-disposition/report.md`, SHA-256 `208160c9a44fab4682a208b57a4664ca41ef68d69f074802af223c45dec78d98`, closes remote-entity interpolation, unchanged FishingHook event-31 local-player impulse, respawn reset receiver, and server-only producers, while retaining the changed same-client mounted correction receiver. Its independent receiver review `98daf5a6d31fbbfcc21d4afca4c5d94e4ced3dbd185592c2c338fad4fe8896df` and draft binding `20a0515ab6fc893f135ee7c797752bec0f5fa0282190b75675cc7da846237b09` are ACCEPT. No server correction frequency/packet timing is needed; the local receiver delta is conditional on processing that correction.
- Client chunk cache: `client-chunk-cache-movement-gate/report.md` proves a movement-reachable velocity difference for identical full-chunk payload and equal tracked entities when a full packet is applied to an already populated resident chunk, then the overlapping local player runs `LivingEntity.pushEntities`. `ClientChunkCache` replacement and entity-section push are the exact route. Packet resend frequency is not a source dependency and is not required.
- Collision: the existing collection report provides a concrete multi-axis `Entity.move` witness for canonical `Shapes.block()` and a positive overlap `0 < δ < 1.25E-8`. It is a conditional source delta. Do not leave its exact witness open solely because world setup/occurrence frequency was not measured.

## Route corrections from the independent member trace

Three current `OPEN` rows are not unresolved eligible members after exact caller tracing:

- `Vec3`: added `upFromBottomCenterOf` / `atBottomCenterOf` are used by the accepted bed-wake response; `zRot` is used by `Player.getRopeHoldPosition`, whose only caller is `MobRenderer`; the other new coordinate helpers are outside the local-player movement route. The paired bed path is already bounded, and the rope point is presentation. Close these exact changed members for movement coverage.
- `CollisionContext.canStandOnFluid(FluidState,FlowingFluid)` and `EntityCollisionContext.canStandOnFluid(...)`: the added implementation is consumed by `LiquidBlock.getShape`. It is false for LocalPlayer because inherited `LivingEntity.canStandOnFluid(Fluid)` returns false and Player/LocalPlayer do not override it. The exact LiquidBlock report already closes this gate. These interface/context rows do not represent an open LocalPlayer collision chain.

Other current partial rows also have narrower closures than their text implies: `BlockGetter`/`BedBlock`/`DismountHelper` for the accepted non-bunk bed-wake route; fixed-state fluid providers; the conditional chunk cache witness; and the named collision witness. Those exact slices are bounded. Their row-level status does not establish that all other movement members or providers in those files are closed.

## Exact eligible residual gates and conditional closures

The following table distinguishes genuinely open routes from member slices that are now conditionally closed. The verbatim 33-row ledger subset is preserved in the copied TSV. Only the OPEN and genuinely unresolved PARTIAL gates block pair freeze; the table does not ask for whole-class equivalence.

| Exact route/member | Residual dependency |
|---|---|
| `ClientPacketListener.handleRespawn` → first local movement read | New local attribute-map copy can retain `sprinting=true` without the sprint-speed modifier. Whether the client default-attribute update normalizes the map before `Player.getSpeed`/ground travel remains open. This is the only named `ClientPacketListener` lifecycle gate; mounted correction is separately accepted. |
| `MultiPlayerGameMode.handleBlockBreakAck` ← same-thread `ClientPacketListener.handleBlockBreakAck` | Old saved `PosAndRot` correction predicate versus new `Vec3`/matching-level/`isColliding` gate, and direct `LocalPlayer.absMoveTo` response. The correction predicate itself remains unreviewed. Do not require acknowledgment frequency or server packet production. |
| `Player.maybeBackOffFromEdge` ← `Entity.move` SELF/PLAYER | The gate changes from grounded-only to `isAboveGround()` with `!abilities.flying`. The source witness is specific (shift held, non-flying, airborne within maxUpStep, collision surface below), but ordinary local-tick reachability of that state/requested horizontal move has not been independently accepted. This is an eligible movement member, not server response timing. |
| `LocalPlayer.aiStep` / `move`; inherited `LivingEntity.aiStep`, `travel`, `Player` movement members | The three accepted LocalPlayer operations above are closed. The remaining route ledger still leaves other changed movement members and their call order unpaired; `LocalPlayer.updateAutoJump` shares the collision collector. Review only those exact changed operations/callers; do not reopen the three accepted findings or require full class closure. |
| `EntityGetter.getEntityCollisions` → `Entity.move` / local-player entity push | The bounded pose-fit predicate/candidate route is closed. General entity filtering and remote-entity-to-local-player collision/push selection outside pose-fit remain open. |
| `Blocks` registrations → `BlockBehaviour.BlockStateBase` providers | Named closures cover Leaves/Liquid/Soul Sand and several suffocation registrations. Remaining changed registrations for existing movement-era blocks and their exact provider dispatch remain unclassified. Close only the remaining eligible registrations and consumers. |
| `WallBlock.updateShape` / neighbor state → collision query | LOW/TALL fixed-state collision shape arms are equivalent. The paired connection/upper-block neighbor-state update, neighbor notifications, tag/side-mask inputs that determine the state supplied to movement remain open. |
| `PistonMovingBlockEntity` provider/state → local collision query | Fixed-state SHORT cutoff and push-volume methods are sourced. Whether the required moving block-entity/state is present in the client collision sample remains a state-input boundary; no server frequency is required. |
| `FireworkRocketEntity.tick` attached-target branch | The player velocity writer formula and gates are paired-identical when the synced target is the LocalPlayer and fall-flying remains true; target/fall-flying values are inputs, not a changed response formula. This is a conditional closed slice under equal sampled inputs, not an unresolved producer-frequency gate. |
| `LeavesBlock.getBlockSupportShape` → `FlowingFluid.isSolidFace` → local fluid current | Retain the accepted conditional domain: queried Leaves state absent from effective `BlockTags.LEAVES` at both endpoints. Tagged at both is false at both; removal only at the new endpoint gives no delta. Effective membership itself is not established by the Java-only route record. |
| Remaining fluid/tag reads | Fixed-state formulas are closed. Effective WATER/LAVA/LEAVES membership and the sampled block/fluid state remain conditional inputs; do not reopen formula closures or demand server producer/packet timing. |

The current 425-row no-movement scan remains unclassified, not a list of 425 eligible movements. It does not warrant a whole-application reverse-caller threshold; it also cannot be relabeled as proved exclusions without exact member dispositions. This is an inventory classification gap distinct from the eligible movement blockers above.

## Freeze criterion and boundaries

Full-pair freeze is not supported: the block-break acknowledgment correction remains an exact open player response, the respawn sprint-attribute lifecycle gate remains open, the edge-backoff witness has not passed independent reachability review, and multiple exact movement-query/provider members remain unclassified. The source inventory additionally retains the 425-row unclassified no-movement scan. The accepted bed-wake, LocalPlayer tick, fluid, mounted correction, chunk-cache and collision slices close only their stated conditional members; none substitutes for the outstanding source coverage. No whole-class or whole-application proof is required where a member is out of scope or a narrower dependency is closed.

Acceptance, pair coverage, implementation and runtime parity remain separate. No product/wiki/backlog/build/tests/game/TAS/Gym/server/runtime/decompile work was performed.

## Row-level audited dispositions

The current ledger's raw 33-row unresolved set is preserved verbatim in ledger-unresolved-movement-positive-groups.tsv. My independent member/caller disposition for the named routes is in `audited-movement-route-dispositions.tsv` (SHA-256 `9e272762ccc9d959452843328fa765d5d0e827d4fb666d2f2edcd8cf5eeb0ad7`). It separates genuine open eligible members from conditional closures and false positive whole-class rows. In particular, Vec3, CollisionContext, and EntityCollisionContext are not movement blockers for the examined changed members; the exact bed wake, render-only rope point, and false LocalPlayer fluid-support gate close those routes. The table also records conditional closures for chunk-cache, Firework attached response, fluid formulas/tag premise, and collision witness without requiring event frequency.

The residual member-inventory gaps for LocalPlayer, LivingEntity, Player, remaining block registrations, and Level/world queries are not a request to prove every method in those classes. The current ledger does not name the remaining changed members precisely enough to decide them one by one; identify the exact changed members and close only those that reach eligible player movement. Player.maybeBackOffFromEdge is one concrete additional candidate awaiting independent ordinary-tick reachability review.
