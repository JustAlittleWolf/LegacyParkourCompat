# 1.8.3 → 1.8.4 source-only movement review

## Result

No concrete player movement-code change was found in the reviewed endpoint pair. No change to the historical movement implementation boundary is established by this review. This is a bounded source report, not a full-pair freeze or independent coverage audit.

## Source identity and integrity

- Exact releases: Minecraft `1.8.3` and `1.8.4`, identified by the client-artifact URLs and hashes in each mapping-directory `.decompile-provenance.json`.
- Mapping family: Ornithe Feather; `1.8.3+build.2` merged-v2 artifact SHA-256 `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2`; `1.8.4+build.2` merged-v2 artifact SHA-256 `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c`.
- Minecraft client artifact SHA-256: `1.8.3` `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06`; `1.8.4` `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b`.
- Both mapping-directory manifests record pipeline `lpc-decompile-v1` with matching pipeline options and tool/runtime provenance. Manifest SHA-256: `1.8.3` `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed`; `1.8.4` `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00`.
- Each manifest enumerates 1,612 source files. Manifest source-hash comparison found 45 changed paths and no additions/removals. Actual SHA-256 of the movement-spine files listed below matched each manifest. The version directories contain the mapping directory; provenance is inside each mapping directory. There is no separate release-parent `.decompile-provenance.json` or separate source snapshot file at either release parent.

## Ordered coverage

1. **Client tick and input:** `client/entity/living/player/LocalClientPlayerEntity.java`, `ClientPlayerEntity.java`, `Input.java`, and `KeyboardInput.java` have identical manifest hashes between endpoints; actual file hashes matched the manifests. `client/Minecraft.java` differs only in version strings for the display title and `X-Minecraft-Version` header (diff around lines 483 and 2443), not the tick path. `client/options/GameOptions.java` is unchanged.
2. **Jump, travel, movement state and collision writers:** `entity/Entity.java`, `entity/living/LivingEntity.java`, `entity/living/player/PlayerEntity.java`, `util/math/Box.java`, `util/math/MathHelper.java`, `world/World.java`, and `world/WorldView.java` are identical by manifest hash; sampled actual file hashes match. This closes endpoint source-file differences in the primary position/velocity/box/collision path, but does not replace method-by-method caller and dependency tracing for the complete tick graph.
3. **Movement modifiers, effects and equipment-related predicates:** `entity/living/effect/*.java`, the attribute classes, `enchantment/EnchantmentHelper.java`, and `enchantment/DepthStriderEnchantment.java` have identical manifest hashes. No changed effect or player attribute source was identified.
4. **Fluid and common movement-block predicates:** `block/Block.java`, `Blocks.java`, `block/material/{Material,LiquidMaterial}.java`, `FlowingLiquidBlock.java`, `LiquidBlock.java`, `LiquidSourceBlock.java`, `CobwebBlock.java`, `IceBlock.java`, `LadderBlock.java`, `SlimeBlock.java`, and `SoulSandBlock.java` have identical manifest hashes.
5. **Changed movement-adjacent block candidate:** `block/VineBlock.java` differs. In both sources `getCollisionShape(World, BlockPos, BlockState)` returns `null` (1.8.3 lines 126–129; same method at the same location in 1.8.4). The differing body is `tick`; it is enclosed by `if (!world.isClient)` in both versions (1.8.3 lines 197–200 and 1.8.4 corresponding tick method). The change rearranges vine-growth neighbor lookups and targets during server-side growth. It does not change a player collision shape or a direct player response, so it is not a movement finding under the assigned scope.
6. **Other changed paths inspected for player reachability:** `block/DispenserBlock.java` changes an item-stack zero check to `<= 0`; `DropperBlock.java` makes analogous item-stack checks; neither changes player movement. The remaining 45-path manifest delta includes UI/rendering, NBT, server/network, AI-goal, scoreboard and bookkeeping classes. These are not movement candidates on the evidence checked here; a full blind coverage audit of all reachable responses was not performed.

## Gaps and next bounded action

- Do not freeze the pair from this report. A separate blind reviewer still needs to retrace the entire ordinary player tick and direct-response graph and close dependencies for each terminal slice.
- If the review is extended, inspect all changed-path callers and relevant block callback dependencies, including whether any altered non-movement block behavior can affect an eligible direct player response. Do not promote endpoint evidence into an exact intermediate patch boundary.
- This work did not read partial outputs from other frontier-c lanes and did not modify canonical `decompiled_minecraft/` sources.
