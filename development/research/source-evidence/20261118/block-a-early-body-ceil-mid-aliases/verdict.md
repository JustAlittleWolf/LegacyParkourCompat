# Independent source verdict: early body-contact ceil aliases

**Verdict: ACCEPT — scoped claim.** The existing body-contact Boolean scan and its exclusive `ceil(maxY)` upper bound are aliased across the six specified releases. The fixed upper-only witness remains excluded from the body-contact scan in each release, for an ordinarily ticked loaded local player under the stated loaded-area condition.

## Claim and identity reviewed

- Immutable claim/evidence input: `early-body-ceil-mid-aliases/evidence.md` at commit `f8bf66cbd8cb44266e344147c7e79eab161c01e9`.
- Committed evidence payload SHA-256: `4d801c443adf275b4fc860882627fbc0a5a4110b43c75fd59448ec447877d656` (7,803 bytes).
- Exact releases: 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1 and 1.11.2; each source tree is `ornithe-feather` / Feather Gen2 build 2. Sources were read from the canonical `decompiled_minecraft/<release>/ornithe-feather/` tree. 1.10 and 1.10.1 hidden provenance hashes and selected source hashes match the input record; the selected World.java and Entity.java hashes for all six match the evidence table.
- Scope: the Entity body-contact caller, World liquid-contact Boolean scan, its vertical ceil/loop bound, and the inherited ordinary local-player tick path for the specified witness. The accepted 1.8.9–1.9.4 source finding is reused as supplied and was not re-audited here. No other fluid/current provider, new operation, exact version boundary, or runtime parity is covered.

## Source checks

For all six releases, `Entity.checkWaterCollisions()` passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` and `Material.WATER` to `World.applyLiquidDrag`. A mounted boat skips that scan; otherwise a true result resets fall distance, sets `inWater`, and clears fire, while a false result clears `inWater`. The body method text hashes are `bb008e529fb656f40bc3d62647da8bed0a02acc8a124463eb4ed9baa7217517f` for 1.10/1.10.1/1.10.2 and `8f4a0fdf17238649603c274af68c1c846d08f7331e9146a64f3d3ea793111b50` for 1.11/1.11.1/1.11.2. The only checked caller difference is fire clearing (`onFireTimer = 0` through 1.10.2 versus `extinguish()` in 1.11.x); it does not alter the box or predicate.

Each `World.applyLiquidDrag` body has UTF-8 declaration-to-closing-brace SHA-256 `32a6bd91971a8da63a1fa91710c87be272fdaece8226b64b6395e2818e72e3ec`. It derives the upper vertical endpoint as `l = ceil(bounds.maxY)`, rejects an unloaded area first, and loops with `p < l`. It tests water material and the liquid surface gate, then returns the accumulated Boolean `bl`; velocity drag is a separate side effect and is not needed for this Boolean witness. Its body, gates and endpoint match the supplied already accepted 1.9/1.9.4 comparison.

The ordinary local-player path is present in every release: `LocalClientPlayerEntity extends ClientPlayerEntity`, which extends `PlayerEntity`, `LivingEntity`, and `Entity`; its `tick()` calls `super.tick()` when the player’s chunk is loaded. `Entity.tick()` calls `baseTick()`, and `baseTick()` calls `checkWaterCollisions()` without a player-flight gate. References: `LocalClientPlayerEntity.java:152–154` and `Entity.java:304–312, 366` for 1.10–1.10.2; `LocalClientPlayerEntity.java:159–161` and `Entity.java:312–320, 375/379` for 1.11–1.11.2 (the per-release caller lines are recorded below). `PlayerEntity` inherits the default standing `height = 1.8F` in `Entity` (`Entity.java:170` in 1.10.x; `:174` in 1.11; `:177` in 1.11.1/.2). `Box.grown` adds its `dy` to `maxY`; `contract(0.001)` delegates through `expand(-amount)` to `grown`.

For the accepted fixed position `x=z=0.5`, `y=62.60100005364418`, the standing box top uses float `1.8F`, then the caller applies float `-0.4F` and double `-0.001`: the resulting body-box `maxY` is exactly `64.0`. Thus `l=ceil(64.0)=64`, and the half-open loop does not visit block Y=64. An otherwise loaded level-1 water block occupying only Y=64 cannot make this scan return true or set body `inWater`. This conclusion is limited to body presence; it says nothing about independent fluid-flow effects.

## Exact source locations

- `Entity.checkWaterCollisions`: 1.10, 1.10.1 and 1.10.2 `Entity.java:862–878`; 1.11 `:884–900`; 1.11.1 `:934–950`; 1.11.2 `:927–943`.
- `World.applyLiquidDrag`: 1.10 and 1.10.1 `World.java:1564–1606`; 1.10.2 `:1570–1612`; 1.11 `:1592–1634`; 1.11.1 and 1.11.2 `:1571–1613`.
- Tick callers: 1.10/1.10.1/1.10.2 `Entity.java:309` (`tick` to `baseTick`) and `:366` (`checkWaterCollisions`); 1.11 `:313` and `:375`; 1.11.1 `:317` and `:379`; 1.11.2 `:317` and `:379`. Local player tick gating/call is 1.10.x `LocalClientPlayerEntity.java:152–154`, 1.11 `:159–161`, 1.11.1/.2 `:159–161`.

## Acceptance limit

This accepts only the six-release body-contact alias and upper-only witness claim against the pinned evidence. It does not establish a new change boundary, re-open the 1.8.9–1.9.4 finding, accept any implementation, or establish runtime parity.
