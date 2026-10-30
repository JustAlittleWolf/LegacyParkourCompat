# Independent blind collision audit: 1.11.2 → 1.12.2

## Verdict

**Confirm** the bed landing velocity difference as a real, direct local-player movement response. The claim and evidence in `collision/bed-bounce-on-landing.md` are supported by the exact endpoint sources; no correction to its operation or gates is needed. **Exclude** the farmland position correction from client movement: replacement and correction are guarded by `!world.isClient` in both endpoints. No second player-relevant collision/provider delta was found in this bounded collision lane.

This is a bounded independent collision audit, not a full-pair freeze. The report's cross-lane ordinary candidate and any areas expressly left to the travel/ordinary lane remain governed by their own review and inventory.

## Inputs and evidence identity

- Pair and mapping family: Minecraft 1.11.2 → 1.12.2, Ornithe Feather Gen2 build 2, read-only trees `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.11.2\ornithe-feather` and `...\1.12.2\ornithe-feather`.
- The audited coverage report binds client SHA-256s `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` → `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, with intact source manifests and per-file verification.
- Audited input SHA-256s: `collision/coverage.md` `08925604ea1f5ce47888ed26bcf0a71b4ba2b19d8ac978334a4b22bb56f08d8e`; `collision/bed-bounce-on-landing.md` `065582cbbf16452d55a350c01f2a9b2ef822e8031266059d6539829ce200f621`; `collision/farmland-server-side-replacement-disposition.md` `7527f4fefca90c98d4e60fcf0ea866216330a2055aae0ea320a168b7a2f0fcdc`.
- Selected source hashes from the verified manifests: `entity/Entity.java` `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` → `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `block/Block.java` `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` → `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`. The source coverage report records the complete manifest and toolchain identities.

## Paired trace: bed bounce

- **1.11.2:** `net/minecraft/block/Block.java`, `beforeCollision`, lines 624–626, sets `entity.velocityY = 0.0`; `net/minecraft/block/BedBlock.java` has no override. `net/minecraft/entity/Entity.java`, `move`, lines 722–725, calls the selected support block's hook only when resolved Y (`o`) differs from requested Y (`y`).
- **1.12.2:** `net/minecraft/block/BedBlock.java`, `beforeCollision`, lines 141–150: sneaking delegates to the same base zeroing hook; otherwise, if `velocityY < 0.0`, writes `-velocityY * 0.66F`, with another `* 0.8` only for non-`LivingEntity`. The base hook remains `net/minecraft/block/Block.java:643–645`. `Entity.move` still calls the selected support block's hook under `o != y` at lines 725–728.
- In both `Entity.move` bodies the support location is selected from `floor(x)`, `floor(y - 0.2F)`, `floor(z)` after collision resolution (falling back one block for fence/wall/fence-gate support when the sampled state is air), then `checkFallDamage` runs before `beforeCollision`. The bed shape is an era-existing collision support in both versions. No `world.isClient` guard surrounds the hook.
- The ordinary report's local class chain is `LocalClientPlayerEntity → ClientPlayerEntity → PlayerEntity → LivingEntity → Entity`; its normal tick reaches `move`, and local-player movement delegates to `super.move`, reaching this solver. The separately audited caller/order trace does not identify a gate that prevents this local route; the local entity is not required to be riding or non-player for the hook to execute.
- **Concrete consequence:** for a non-sneaking local player whose downward movement is clipped by a bed, the velocity response in 1.12.2 is upward at 66% of the incoming magnitude, while the 1.11.2 base response zeros it. This is direct player velocity mutation during movement, not merely a change in landing damage (bed `onFallenOn` damage scaling is not the claim here). The exact introduction release inside the interval is unknown.

## Solver, callback and provider cross-check

- Compared both `Entity.move` collision-resolution bodies at the reported operations: no-clip and piston timing gates, cobweb handling, sneaking edge clipping, collision enumeration, Y/X/Z resolution, step-up candidate choice, collision flags/ground test, fall/support selection, axis velocity zeroing, `beforeCollision`, step/contact calls and final block-contact scan. No additional player solver delta was found. 1.12.2's extra air flap sound branch is not a player movement effect.
- Compared `World` block-collision enumeration, border/chunk handling, block-state collision dispatch, `Block`/`BlockState` collision-shape routing, entity collision-shape contribution and `Box` intersection geometry. Coverage report's paired `Box.java` hash is byte-identical. No ordering or bounds delta affecting the local player was found.
- Cross-checked callback overrides (`beforeCollision`, `onSteppedOn`, `onFallenOn`, `onEntityCollision`) for blocks existing in both eras. Slime's bounce callback is byte-identical; stairs delegation and the checked pressure plate, cactus, cauldron, cobweb, portal, tripwire, soul-sand and other existing callbacks showed no additional local movement delta. The added `Entity.onBlockCollision(BlockState)` invocation in 1.12.2 follows `onEntityCollision`; the base is empty and the only located override is server-player advancement bookkeeping, so it does not add a client local-player movement response. End portal's predicate reorder still gates its dimension-change action to server worlds.
- Independently reconsidered existing block collision/outline shape providers and conditional collision assembly, including moving blocks and state-dependent providers. The paired inventory reports all existing block-package declarations checked; no changed historical provider or omitted collision-shape movement dependency was identified. New 1.12 blocks are outside the 1.11.2 historical block set. This audit does not claim review of arbitrary modern blocks or non-player/entity physics.

## Farmland disposition

- **1.11.2:** `FarmlandBlock.onFallenOn` checks `!world.isClient` before random/player-or-mobGriefing/size gates; `setDirt` is also reached by that gated call. Its intersecting-entity position correction is within the server-only replacement operation.
- **1.12.2:** `onFallenOn` retains the same outer `!world.isClient` gate before the replacement call; `setDirt` changes how it computes/interprets the correction (shape-height delta and `+0.001`) but remains under this server-only call. The relevant bodies are `FarmlandBlock.java:60–79` on the newer endpoint and `:59–79` on the older endpoint.
- `Entity.checkFallDamage` calls `onFallenOn` after a landing, including on clients, but farmland's own first gate prevents both block replacement and this correction on the logical client. Its being a positional write does not make the server-side world mutation a client movement finding. The supplied bounded exclusion is correct.

## Remaining limits

- No builds, tests, source generation or launches were used.
- This verdict confirms one accepted collision candidate and one bounded exclusion; it does not independently establish complete all-pair tick coverage, close other lane dependencies, or determine a within-interval patch boundary.
