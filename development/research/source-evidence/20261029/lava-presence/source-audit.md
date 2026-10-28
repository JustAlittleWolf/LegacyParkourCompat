# Independent audit: lava-presence lifecycle supplement

## Verdict

**ACCEPT** for the bounded `Entity.isInLava()` lifecycle, client movement-consumer, and 1.16.2 alias claims in the report. This does not accept a runtime-parity claim or freeze the full 1.16.1→1.16.2 pair.

## Rebound evidence

The report SHA-256 is `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`.

All four cited source inputs match their reported SHA-256 values:

- `frontier-c/AF/water-lava-fluid-current.md`: `e931848c053565352d3eda9f262dd230fef78765ecc29c822a48a3bfef9eee66`.
- `frontier-c/AF-audit/audit.md`: `1a249eca3e95ba2c5cb8934962eb7c34073de43ad9d7a41db4a916c48136ca88`.
- `frontier-c/AF-alias/AF-patch-alias-closure.md`: `3c64bc4d50fb0bef75822f03fed9fb739cf9691df37804853e6c5b065ca63dd5`.
- `frontier-c/AF-alias-audit/audit.md`: `83f2c218fc05377300a9b235df6eac845f283fa11656aa5d3222bef1012524c5`.

I independently hashed the canonical `Entity.java`, `LiquidBlock.java`, and `LivingEntity.java` files. They match the listed source-manifest rows for 1.16.1 and 1.16.2. The three corresponding hashes are byte-identical across 1.16.2, 1.16.3, 1.16.4, and 1.16.5. The 1.16.1/1.16.2 parent markers and source manifests are recorded in the cited identity report; the unavailable cached binary artifacts remain a provenance limitation. For 1.16.3/.4 the cited alias audit records embedded source provenance without ready markers. The conclusion correctly limits the result to these operation slices.

## Lifecycle and consumer checks

In 1.16.1, `Entity.isInLava()` returns `isTouchingLava`. A source-tree search found its only setter caller in `LiquidBlock.entityInside`; `Entity.move` clears the flag immediately before `checkInsideBlocks`, after the movement/collision and fall-damage work. `checkInsideBlocks` scans the inset bounding-box cells only when `hasChunksAt` admits the region. The liquid callback checks the lava tag and sets the flag for the reported surface predicate. Thus the value read before a player's next travel/AI step is the result of the previous eligible movement callback phase.

In 1.16.2, the callback, setter, and boolean field are absent. The predicate reads `!firstTick && fluidHeight.getDouble(FluidTags.LAVA) > 0.0`. `baseTick` clears and repopulates fluid heights via the lava scan before its lava-related checks, and sets `firstTick=false` near its end. This supports the report's distinction between a false first-tick query and the newly cached result visible to subsequent local-player AI/travel in that tick. The scan's `hasChunksAt`, deflated-AABB, fluid-tag, surface-height, and positive-overlap gates are described consistently with the source.

The cited `LivingEntity` and `Entity` paths establish movement consumers: lava travel/drag/gravity selection, lava-vs-water jump selection, and the fall-distance accumulator update. The report separately excludes damage/fire outcomes and visual particle use. It also keeps the newer numeric-height query distinct from the independently accepted water-first lava-current change and the eye-fluid cache.

## Limitations

The report accurately preserves the missing cached-binary rehash limitation for the 1.16.1/1.16.2 source publication and the embedded-only identity limitation for 1.16.3/.4. Its source establishes a bounded operation change and client movement reachability, not a measured trajectory or complete release-pair coverage. No implementation, build, tests, or runtime were inspected or performed.
