# Moving-fence product review

## Immutable review identity

- Product repository: `C:/Users/Wolfi/.codex/worktrees/56a8/LegacyParkourCompat`
- Product commit reviewed: `817034e51b53ec382a4e4ab06598581dfdb27a85` (`fix/resume-movement-20261030`)
- Product worktree was clean at review start; HEAD matched the requested commit.
- Accepted source input: `.native-source/20261030/moving-fence-scan-closure/closure.md`, SHA-256 `DF144E443FEAA6D13D67B5915E396422FCD4E73777CD35F8E503FC55975FFB01`, with immutable correction `.native-source/20261030/moving-fence-scan-closure/correction-001.md`, SHA-256 `08F1EB5513C31FF65F714E203155BE6B1CA6C21994518806AA31FC63C81896E9`.
- Prior candidate review read as reconciliation context: `.native-source/20261030/moving-fence-candidate-review/review.md`, SHA-256 `A147E685F57B54ACB6E8D3464A7C0F6DB88699619DB8EA4074E53BBF361F7066`.
- Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`; no canonical sources were generated or changed.
- No product files were changed. No build, tests, game, or runtime checks were run.

## Verdict

**Do not accept this commit as operational until the `@WrapOperation` target descriptor is corrected.** The hook and change logic otherwise reconcile the candidate review's version-resolution, local-player, fence-domain, and query-face concerns for the bounded resident moving-fence case. Static bytecode inspection shows that the target call is `BlockState.is(Object)Z`, while the mixin names `BlockState.is(Block)Z` and declares the wrapped argument as `Block`. Because the descriptor does not identify the invocation in `computeNext`, the required wrapper cannot bind to this call. The client mixin configuration sets `defaultRequire` to 1, so the mismatch is expected to fail mixin application rather than silently provide the compatibility behavior.

## Blocking finding: invocation descriptor mismatch

At product `BlockCollisionsMixin.java:59–63`, the wrapper targets:

```text
Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z
```

The 26.2 client development jar used by this checkout is `minecraft-merged-deobf-26.2.jar`, SHA-256 `0468742D98FE5EC68508493EF2155C6E5209F991055BC0C17DDF26872EB0D110`. `javap -p -c net.minecraft.world.level.BlockCollisions` shows one `computeNext` call at bytecode offset 144:

```text
BlockState.is:(Ljava/lang/Object;)Z
```

This is the sole call after the `cursorFaceType == 2` branch and the `Blocks.MOVING_PISTON` field read, so the intended injection site is unambiguous but its descriptor in the product does not match. `BlockBehaviour.BlockStateBase` implements `TypedInstance<Block>`; `TypedInstance.is(T)` erases to `(Ljava/lang/Object;)Z`, which is also what `javap -p -s net.minecraft.core.TypedInstance` reports. The Java source expression `blockState.is(Blocks.MOVING_PISTON)` does not retain a `Block` parameter descriptor after erasure.

The wrapper handler's second parameter is likewise declared `Block` at line 61 although the invoked method parameter is `Object`. Use the erased invocation descriptor and compatible handler argument (`Object`), or another exact unique target form verified against this bytecode. Do not treat Java source-level generic typing as the bytecode descriptor.

The scanned 26.2 `BlockCollisions` bytecode confirms the relevant instance fields and their types: `context: CollisionContext`, `box: AABB`, `pos: BlockPos.MutableBlockPos`, `cachedBlockGetter: BlockGetter`, and `onlySuffocatingBlocks: boolean`. The source field shadows are consistent with those fields. The constructor descriptor used by the existing scan-bounds injection also matches the inspected class. The `computeNext` source hash is `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`; `Cursor3D.java` is `9a1fdc92bb95013c7e70da467a0b5e5b1c9787daafcb0e7e3a8c950dd0e7287f`.

## Reconciliation of the previous candidate findings

- **Resolver direction is corrected.** `NativeMovingFenceEdgeScan` is annotated at `V1_13`; it returns false. `MovingFenceEdgeScan` is annotated at `V1_13_1`. `ChangeResolver` chooses the closest change whose emulated version is greater than or equal to the selected profile. Thus 1.12 and 1.13 resolve the no-op entry, while 1.13.1 and 1.13.2 resolve the omission entry. The enum aliases 1.13.1 and 1.13.2 together.
- **Local player and logical client are gated.** The wrapper requires `MovementRuntime.playerFrom(context)` to be a `LocalPlayer`; `MovementRuntime.find` then applies the existing enabled-profile and client-side player check. Other client-side players and server-side contexts keep the original result.
- **Suffocation, placement, and fluid-placement contexts retain native behavior.** The wrapper bypasses when `onlySuffocatingBlocks`, `context.isPlacement()`, or `context.alwaysCollideWithFluid()` is true.
- **The cached-client-chunk and block-entity conditions are conservative.** The wrapper uses only the already-resolved `cachedBlockGetter`, returns native admission if it is absent or the matching moving-piston block entity is absent, and does not request a chunk. This agrees with the bounded resident-data applicability in the closure.
- **The EAST-only candidate gate has been removed.** The change restricts moved states to the seven fence blocks existing in the scoped legacy domain, and checks the two-face predicate against the historical inclusive envelope `floor(min)-1 .. ceil(max)`. It does not inspect fence connection direction, piston direction, extension progress, or geometric intersection, consistent with correction 001's statement that the old cell-pruning predicate is independent of those facts.
- **The source cutover and target site are supported.** Canonical 1.13 `WorldView.java` has SHA-256 `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543`. Corrected 1.13.1 and 1.13.2 `WorldView.java` hashes are both `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`. The 1.13.1 predicate rejects paired face flags before `isChunkLoaded` and state access. In 26.2, `Cursor3D` classifies two-face cells as type 2, and `computeNext` uses the single `BlockState.is(Object)` call to admit only `MOVING_PISTON` at those edges.

## Applicability limit

The product currently adapts only a native type-2 candidate whose position also lies on at least two faces of the reconstructed old floor/ceil envelope. That is a defensible narrow gate for the accepted concrete witness and prevents modern epsilon-expanded cursor faces from being treated as historical faces. It does **not** establish full equivalence for every old two-face cell: when an exact integer query bound shifts the modern cursor face by one cell, an old-envelope face cell may be an interior or single-face cell in `Cursor3D`; the vanilla type-2 `BlockState.is` call then does not execute, so this wrapper cannot prune that cell. Keep the implementation and its claim bounded to coincident native type-2/old-envelope faces unless a broader interception handles these cases. This limitation does not invalidate the stated nonintegral moving-fence witness.

## Product file identities

Hashes are SHA-256 at the reviewed product commit:

| File | SHA-256 |
|---|---|
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_13/NativeMovingFenceEdgeScan.java` | `a95ac3b683b58eb2f59c65b6372a4b220acb2453a14dfb72af82d6dfbff6fa15` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_13_1/MovingFenceEdgeScan.java` | `445009b2679ec6eb2553d2ce12330eb15e1d93def8248a1ba212920f6d0f228b` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/collision/MovingFenceEdgeScanHook.java` | `667d5ef5f3213bd3b552fc166d6b70873c80fe5c75c97583240ddacff01c007d` |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/BlockCollisionsMixin.java` | `3579ee9c16066d76f19512c2f47cb45929595415a5e6cb5cfd15c5c105cd6db3` |
