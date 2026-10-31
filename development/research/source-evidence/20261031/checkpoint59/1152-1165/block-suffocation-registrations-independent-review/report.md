# Independent review: remaining block-provider routes — Mojmap 1.15.2 → 1.16.5

## Review identity and scope

Reviewed `.native-source/20261030/1152-1165-finish43/existing-block-provider-residual-inventory/report.md` at SHA-256 `a7975c694768b536e60e1d34afaa6ea7bcc48ba2939e6ff1ff5d4ba9d54dba2d`. Its nine listed input files match `input-hashes.tsv`; all 34 cited source rows in `source-hashes.tsv` were rehashed and match. The canonical `mojmap.sources.sha256` files hash to `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` (1.15.2) and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99d2b2a20823b` (1.16.5), matching their ready markers. The exact releases and Mojmap source trees were read-only.

The review is limited to the assigned suffocation, wall/Iron Bars, and piston-provider residuals. No tag payload/default membership was assumed. No product, wiki, backlog, test, build, runtime, or decompilation work was done.

## Per-route verdicts

### Farmland, Soul Sand, and Grass Path suffocation predicate — REVISE (provider delta accepted; movement witness incomplete)

**Provider delta: ACCEPT.** The exact registrations add `.isSuffocating(Blocks::always)` for all three blocks in 1.16.5 (`Blocks.java`: Farmland 494–503; Soul Sand 730–741; Grass Path 1965–1970). The 1.15.2 registrations do not override the inherited `Block.isSuffocating` default (`Blocks.java`: Farmland 432–434, Soul Sand 601–604, Grass Path 1533). That default requires a motion-blocking material and full collision shape (`Block.java` 285–286); each registered collision shape is 15/16 high for Farmland and Grass Path (`FarmBlock.java` 28–29; `GrassPathBlock.java` 17–18) or 14/16 high for Soul Sand (`SoulsandBlock.java` 16; `SoulSandBlock.java` 18). Thus, for the same state and position, the old predicate is false and the new registered predicate is true. This is a suffocation-predicate change, not a collision-shape change.

**Consumer trace: ACCEPT.** In 1.15.2, `LocalPlayer.aiStep` runs the four `checkInBlock` probes under `!noPhysics` (651–655); `blocked` visits every vertical cell overlapped by the player box and delegates to `Player.freeAt`, which calls `BlockState.isSuffocating` (LocalPlayer 431–443; Player 1489–1491). In 1.16.5 the corresponding four `moveTowardsClosestSpace` probes are also under `!noPhysics` (656–661); `suffocatesAt` filters `noBlockCollision` with `BlockState.isSuffocating` and, if it finds a direction whose candidate query is clear, writes a horizontal velocity component of ±0.1 (LocalPlayer 414–447). The predicate is therefore on a real local-player response path.

**Concrete witness/applicability: not established.** The inventory states the necessary condition—an overlapping player box and an available sampled escape direction—but supplies no concrete box, state arrangement, or exact-source reachability argument showing that ordinary LocalPlayer execution can satisfy it. In particular, the partial collision shape must intersect the player query box for the new filtered collision query to succeed. Treat the predicate delta as source-confirmed and the movement consumer as identified, but keep an accepted movement finding/applicability conditional until a concrete feasible player witness is supplied. Do not state that the new velocity write occurs for a normally grounded player merely from the registration census.

### `IronBarsBlock.attachsTo` → effective `BlockTags.WALLS` — ACCEPT (conditional state generation)

The exact predicate changes from `!isExceptionForConnection(block) && sturdy || block instanceof IronBarsBlock` in 1.15.2 (`IronBarsBlock.java` 75–78) to the same expression plus `|| block.is(BlockTags.WALLS)` in 1.16.5 (`:85–88`). Placement and horizontal `updateShape` use `attachsTo` to set directional properties (new `IronBarsBlock.java` 37–46, 49–61; old 39–56). Therefore, for a supplied neighbor state that is in the effective WALLS tag and for which the old predicate is false, the new producer can set a directional connection that the old producer does not. The report bounds this to effective tag membership and the old false result and does not invent tag contents. Fixed-state CrossCollision/IronBars collision geometry remains closed; the movement consequence is through a sampled state with the changed directional property.

### `WallBlock.connectsTo` → Iron Bars neighbor — REVISE (conditional state generation; old predicate boundary omitted)

The source change is real: 1.15.2 `WallBlock.connectsTo` checks `block.is(BlockTags.WALLS)`, sturdy-face eligibility subject to the exception gate, or a connecting fence gate (`WallBlock.java` 60–64); 1.16.5 adds `block instanceof IronBarsBlock` (`:130–134`). Placement and side updates feed this result into the side properties (old `:67–90, 93–123`; new `:137–156, 192–210`). A changed wall state can select a collision arm, and `Entity.move` consumes the supplied-state collision shape.

The inventory's sentence that an Iron Bars neighbor “is not a wall/fence gate, so it does not connect” omits the old `BlockTags.WALLS` branch. Without inspecting tag data (which this review must not assume), source code alone does not establish that the old predicate is false for every Iron Bars state. Keep this route conditional on the full old `connectsTo` result being false; when that condition holds, the new `instanceof IronBarsBlock` term makes it true. This remains a state-generation route and does not reopen the fixed-state LOW/TALL wall collision-provider closure.

### Fixed-state wall collision provider — ACCEPT (closed; no reopening)

The cited wall report's same-direction/post shape comparison is consistent with the exact provider path: old `WallBlock` delegates to its 24-high cross-collision arms and optional post; new `getCollisionShape` returns the state-keyed collision table (`WallBlock.java` 49–53 old; 121–123 new). The LOW/TALL distinction belongs to outline construction; collision table arms use the same 24-high top. Keep only the conditional neighbor-state producer claims above open. No fixed-state provider delta is reopened.

### Piston moving-provider closures and sampled-state boundary — ACCEPT (no reopening)

The paired exact methods confirm the previously audited fixed-input changes: source-piston collision `SHORT` selection changes from the legacy comparison at old `PistonMovingBlockEntity.java` 347–360 to the newer threshold at 349–362; retracting source-piston push state adds `SHORT = progress > 0.25F` at new `:92–99` before `moveCollidedEntities` queries the resulting collision shape (`:102–108`), while the old helper leaves the property at default (`:89–96`). These are already bounded equal-field/provider deltas and are not reopened here. Their live application still depends on the sampled moving-piston state/block entity; 1.16.5 retains a completed client block entity for up to five `deathTicks` (`:281–300`), and `finalTick` has a client-side cleanup route (`:257–273`). No server event frequency or packet-order proof is needed for the conditional equal-input claims, and no unconditional live-state claim is made.

## Reconciliation guidance

- Preserve the three exact suffocation predicate deltas, while recording that the concrete player-position/escape-candidate witness is still required for movement acceptance.
- Preserve the Iron Bars WALLS-tag route exactly as a conditional producer: effective membership plus old `attachsTo == false`.
- Narrow the WallBlock-to-IronBars claim to `old connectsTo == false`; tag membership is an unclosed source input and must not be guessed.
- Keep the fixed-state wall collision closure and the existing equal-input piston provider claims closed. Retain the piston sampled-state condition as an applicability boundary.

This review is an independent source review of the named residuals only; it does not freeze the release pair or claim runtime parity.
