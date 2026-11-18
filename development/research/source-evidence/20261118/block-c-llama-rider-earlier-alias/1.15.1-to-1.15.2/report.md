# 1.15.1 → 1.15.2 Llama rider position comparison

## Scope

Exact Mojmap decompiled client sources for a standard adult `Llama` or inherited `TraderLlama` with one direct ordinary `Player` passenger. Compared the Y placement expression, numeric types and order, rider and mount offsets, dynamic dispatch, and applicable guards/superclass behavior. No difference was found in this slice. This does not claim full-pair coverage.

## Exact source references

- 1.15.1, Mojmap: `decompiled_minecraft/1.15.1/mojmap/net/minecraft/world/entity/animal/horse/Llama.java:149-160`; `.../TraderLlama.java:22`; `.../Entity.java:1544-1559`; `.../Player.java:458-469, 985-988`; `.../animal/horse/AbstractHorse.java:900-905`.
- 1.15.2, Mojmap: same relative files and line ranges under `decompiled_minecraft/1.15.2/mojmap/`.

Both Llama source files are byte-identical: SHA-256 `bbe98226c5afaddce8ca7458c721fbefff592cc75e02bc261d6b440ca6877f19`. The checked `Entity.java`, `TraderLlama.java`, and `AbstractHorse.java` files are also byte-identical across versions (respectively `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`, `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4`, and `a967d0834d3c28080725a8b639d2c8c182ee6a7209a361d462fb0225a2836e6d`). `Player.java` differs as a whole between versions, but the cited `rideTick` and `getRidingHeight` bodies are text-identical; the selected `getRidingHeight` is `return -0.35;` in both.

## Behavior comparison

`Llama.positionRider(Entity)` checks `this.hasPassenger(entity)` and, when true, calls `entity.setPos(...)`. The Y argument in both releases is exactly:

```java
this.getY() + this.getRideHeight() + entity.getRidingHeight()
```

Java evaluates this left-associatively. Each operand and addition is `double`: `getY`, `Llama.getRideHeight`, and `Entity.getRidingHeight` return `double`; there is no intermediate float conversion or cast in the Y expression. The llama override returns `this.getBbHeight() * 0.67` (`0.67` is a double literal); a `Player` rider override returns `-0.35` (double). Thus the Y is mount base Y plus 0.67 of the llama's bounding-box height minus 0.35, with the same operation order in both releases.

The same method keeps the horizontal passenger offset: body yaw is converted through float arithmetic, and the rider is placed at X `mountX + 0.3F * sin(yBodyRot)` and Z `mountZ - 0.3F * cos(yBodyRot)`. This is unchanged and does not feed into Y.

For a client-side player, `Player.rideTick` takes its ordinary `else` path because the stop-riding guard requires `!level.isClientSide`; `Entity.rideTick` then invokes `this.getVehicle().positionRider(this)` when the player is a passenger. That is a virtual call, so an ordinary `Llama` dispatches to `Llama.positionRider`. `TraderLlama` extends `Llama` and declares no `positionRider` override, so it reaches the same method. The Llama method's `hasPassenger` guard is present in both versions.

`Llama.positionRider` does not call `super.positionRider`. This bypasses the inherited generic placement and `AbstractHorse.positionRider`'s `Mob` body-yaw adjustment; the override and this superclass behavior are unchanged across the pair.

## Provenance and hash verification

Used only the canonical adjacent version roots, with no substitution:

- `decompiled_minecraft/1.15.1/mojmap/` identifies version `1.15.1`, namespace `mojmap`; `mojmap.provenance.json` run ID is `prep-1.15-1.15.1-mojmap-2026-10-09-r1`. Its recorded source-manifest SHA-256 is `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`, matching the actual `mojmap.sources.sha256` hash. The Llama entry and actual file hash both equal the SHA-256 above.
- `decompiled_minecraft/1.15.2/mojmap/` identifies version `1.15.2`, namespace `mojmap`; `mojmap.provenance.json` records the exact version and run inputs, and `mojmap.ready.json` records source-manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, matching the actual `mojmap.sources.sha256` hash. Its Llama manifest entry and actual file hash both equal the SHA-256 above.

The per-version source manifests were checked against actual SHA-256 hashes for each cited file. The roots contain the per-mapping `mojmap.provenance.json` and ready markers; no `.decompile-provenance.json` filename exists in either adjacent version root.

## Exclusions

Other mounts, rider classes, passenger stacks, non-player/vehicle physics, and unrelated passenger placement paths were not examined.
