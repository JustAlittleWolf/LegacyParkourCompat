# Retracting Stone alias continuity and 1.11 cutover

## Bounded question

For the already accepted `1.8.9--1.9` ordinary non-source Stone retraction witness, does standard local-player westward movement still query the old current/last union provider through exact `1.10.2`, and what changes at exact `1.11`? This only covers the fixed/query phase described below. It does not infer event scheduling or a full trajectory.

Frozen input: `domain-b-old-stone-retraction-source/source.md`, SHA-256 `df7c99bc2c5f67396184ace0a7d8204540ab8ea6dd30e36a3e45c376653535b5`.

## Source identity and integrity

The canonical sources are under `decompiled_minecraft/<exact-version>/ornithe-feather/`. Read-only source review found the same `MovingBlock.java` and `MovingBlockEntity.java` bytes in 1.9.1 and 1.9.2 as 1.9; the same provider files in 1.9.3, 1.9.4, 1.10, 1.10.1 and 1.10.2; and the current-only `addCollisions` dispatch in 1.11. This establishes the content present in each named directory. It does not establish authentic decompiler provenance for directory trees without manifests.

Raw SHA-256 by selected provider files:

| Exact tree(s) | `MovingBlock.java` | `MovingBlockEntity.java` |
| --- | --- | --- |
| 1.9.1, 1.9.2 | `a5b2584bc575dc888f1d32d9d4b683b91fc8b145ee079e66ff4722718fc8906f` | `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c` |
| 1.9.3, 1.9.4, 1.10, 1.10.1, 1.10.2 | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `50eb847d3ff4ff78441057f397c43e15fb894177f7c5a736a61c22a80f1de467` for 1.9.3/1.9.4; `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` for 1.10/1.10.1/1.10.2 |
| 1.11 | `35799c009f0e01655622c1939e6b35342789c5abd19016c5a09528b3fac9e620` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` |

Manifest-backed checkpoint hashes:

| Tree | Provenance SHA-256 | `ornithe-feather.sources.sha256` SHA-256 |
| --- | --- | --- |
| 1.9.4 | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| 1.10.2 | `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |

The source-manifest entries were checked against current bytes for the cited classes in those three trees. Their manifests identify Feather build.2 for the corresponding exact releases. Trees 1.9.1, 1.9.2, 1.9.3, 1.10 and 1.10.1 have no local provenance or source-hash sidecars; only their current raw file hashes and code were inspected. Consequently, exact behavior is established for the code present at every listed path, while upstream provenance remains open for those five trees. No other mappings were consulted.

## Same fixed ordinary-movement query through 1.10.2

Fixture and query are inherited from the frozen source: a sticky piston has pulled Stone to moving-block owner `(1,0,0)`, `source=false`, `extending=false`, `facing=EAST`; the first observed partial state is `progress=0.5F`, `lastProgress=0.0F`. The player box is `[3.01,3.61] x [0,1.8] x [0.2,0.8]`; a normal westward `dx=-0.02` gives the swept X interval `[2.99,3.61]`.

In source present at 1.9.1/1.9.2 (`MovingBlockEntity.java:75-88`) and 1.9.3 through 1.10.2 (`MovingBlockEntity.java:75-88` in 1.9.4; `:75-88`-equivalent method body at 1.10.2 `:76-89`), retract amount is `1.0F-progress`; `getShape` unions current progress then last progress. The two Stone shapes at the stated phase translate EAST by `.5F` and `1.0F`, giving world X `[1.5,2.5]` and `[2,3]`, union `[1.5,3]`.

The ordinary collision scan admits the moving-block owner at x=1: 1.10.2 `World.getCollisions` starts x at `floor(shape.minX)-1` and loops to `< ceil(shape.maxX)+1` (`World.java:903-910, 918-937`). For `[2.99,3.61]`, the owner x=1 is the first scanned X. `MovingBlock.getCollisionShape` supplies `MovingBlockEntity.getShape` (`MovingBlock.java:134-137`); inherited `Block.addCollisions` moves it by the owner position and appends it on strict `shape.intersects(box)` (`Block.java:342-351`). The union intersects the query, so ordinary X clipping sees its maxX=3. `Entity.move` asks `World.getCollisions(this, getShape().expanded(dx,dy,dz))` then applies `Box.intersectX` (`Entity.java:514-527`). At player minX 3.01 and negative `dx=-0.02`, the clip is `3 - 3.01 = -0.01`.

The fixed witness does not select direct push. During partial movement the 1.10.2 moving block queries entities in the current/last union (`MovingBlockEntity.java:91-93, 166-182`). The entity candidate filter uses strict box intersection (`WorldChunk.java:676`; `Box.java:207-209`). Player minX 3.01 is strictly beyond union maxX 3, so this player is absent from the direct-push entity list for this phase. The frozen source supplies the same exclusion for the 1.9 side.

The local-player path remains ordinary movement: `LivingEntity` calls `this.move(velocityX,velocityY,velocityZ)` in 1.10.2 (`LivingEntity.java:1371,1421,1450,1481`); `LocalClientPlayerEntity.move` delegates to `super.move` before autojump (`LocalClientPlayerEntity.java:812-817`). No entity-specific collision bypass is taken by the moving block's normal shape provider.

## Exact 1.11 current-only dispatch

The stable 1.11 source introduces `MovingBlock.addCollisions` (`MovingBlock.java:135-140`), which dispatches to `MovingBlockEntity.addCollisions`; ordinary `World.getCollisions` uses `BlockState.addCollisions` and scans the same expanded coordinate region (`World.java:960-967, 975-994`). The inherited provider method `MovingBlock.getCollisionShape` still delegates to `getShape` (`MovingBlock.java:129-132`), and that getter still unions current and last (`MovingBlockEntity.java:96-104`). However, standard block collision enumeration now takes the override path, not the legacy getter.

For this non-source Stone, `MovingBlockEntity.addCollisions` selects `movedState`, computes `facingOffset * getAmountExtended(progress)`, calls `movedState.addCollisions` against the query shifted backward by that current translation, then translates newly added boxes forward (`MovingBlockEntity.java:295-320`). It reads `progress` only; it does not query `lastProgress` in this method. With `progress=.5F`, the normal block shape is therefore world X `[1.5,2.5]`. This does not intersect the fixed swept player box whose minX is 2.99. The owner x=1 is still scanned, so the changed result is shape admission, not broadphase omission. The 1.11 getter's `[1.5,3]` union remains available to other getter callers; the cutover claim here is specifically the normal `World.getCollisions` path.

The ordinary player query has no piston noclip flag set: 1.11 `Entity.move(MoverType,...)` queries collisions with `this` and the swept box (`Entity.java:523-539`); `LivingEntity` issues ordinary `MoverType.SELF` movement (`LivingEntity.java:1425,1475,1504,1535`); and `LocalClientPlayerEntity.move` delegates to `super.move` (`LocalClientPlayerEntity.java:824-829`). `NOCLIP_DIR` initializes false and is toggled true only around piston-driven entity moves (`MovingBlockEntity.java:28-33, 158-160, 219-221`). For `source=false`, the source-only piston-head branch is skipped (`:296-298`), and with the default false flag the moved-state current-only collision provider runs (`:300-320`).

## Bounded conclusion and proof cone

The observed provider contract continues in the code present in 1.9.1, 1.9.2, 1.9.3, 1.9.4, 1.10, 1.10.1 and 1.10.2: first-phase retracting non-source Stone contributes `[1.5,3]` to the ordinary swept query and clips the fixed westward input to `-0.01`; the fixed player is strictly excluded from direct push. At exact stable 1.11, the standard collision query instead contributes only the current-progress `[1.5,2.5]` shape, so this fixed query no longer clips at X=3. This establishes the first stable-version cutover checked here, not the exact patch introduction boundary or intermediate versions between these named checkpoints.

Proof is limited to the specified EAST-facing pulled non-source Stone, progress pair, player box and ordinary westward query, source-provider path, one direct-push exclusion, and standard player collision dispatch. It does not cover other directions/states, source piston branches, newer piston rewrites, scheduling/runtime behavior, other collision callers, release aliases in app code, product implementation, wiki/backlog state, or any trajectory after this query.
