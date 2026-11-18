# Modern-observed negative-Y vector on the frozen slab aggregate fixture

## Result

**The same old signed-Y aggregation operation applies to the exact 26.2-observed vector `(+2, -50/16383, 0)` when that vector is presented to the unchanged 1.13 / 1.13.1 / 1.13.2 dry, airborne, no-input `SELF` movement call.** This is operation equivalence at an admissible observed input only. It does not establish that the old packet path decodes this value, that a modern client runs old movement, or packet/state parity between releases.

The independent dry-producer audit (raw SHA-256 `210A88579C0B7D0575EC570CFB1AE05887AC7FF9EE6AFAF6E8A9F1DA22219C2F`) corrects the codec arithmetic: 26.2 decodes the corresponding motion codec value to `-50/16383`, approximately `-0.0030525`, not zero. Since `50/16383 > 3/1000` (`50000 > 49149`), the old strict `abs(velocityY) < 0.003` cleanup leaves this component nonzero. Its negative sign also selects the same Y start-exclusion shift as the fixture's old `-0.003` input.

## Dry no-input path to the movement operation

In 1.13 `LivingEntity`'s ordinary dry, non-flying branch chooses the horizontal acceleration and friction factors, calls `updateVelocity(forwards, sideways, upwards, g)`, then calls `move(MoverType.SELF, velocityX, velocityY, velocityZ)` before applying friction and gravity (`LivingEntity.java:1598-1631`). For no input, all three input components are zero. The exact `Entity.updateVelocity` guard sees their squared sum below `1.0E-4` and does not modify any velocity component (`Entity.java:1056-1073`). Thus, provided the observed vector is the state presented to this old local travel call, its pre-collision movement request remains exactly `(+2, -50/16383, 0)`. Gravity and horizontal friction occur after `move`, so neither changes this request.

This establishes a source-supported old dry travel consumer for the vector, not a source-supported old packet producer for it. The old 1.13 packet route in the accepted producer evidence instead establishes `-0.003`; this note does not change that producer result.

## Frozen geometry and query result

Retain the already frozen setup unchanged: airborne and not sneaking; ordinary `SELF`; no cobweb or piston adjustment; no border/entity shapes; all other scanned cells air; bottom slabs at `(2,64,0)` and `(3,64,0)`; actor box `minY=64.1`, `maxY=65.9`, `maxX=1.99999995` and every other coordinate unchanged; `onGround=false`. Use `(dx,dy,dz)=(2,-50/16383,0)`.

`Entity.move` snapshots the adjusted request into `n/o/p`, makes one primary collision query for nonzero movement, and clips Y then X then Z (`Entity.java:530-602`; exact compared releases have the same queried method hashes as cited below). The Y query's X orthogonal upper bound is `box.maxX - 1.0E-7 = 1.99999985`, still strictly below the first slab's X start `2` (1.13 `VoxelShapes.java:224-231`; 1.13.1/1.13.2 `VoxelShape.java:201-208`). Neither slab participates in Y clipping, so the clipped Y stays `-50/16383` and the box's Y coordinates do not change before X clipping. The small extra downward sweep relative to `-0.003` leaves the same slabs within the swept query; the unchanged scene contains no other shapes.

For negative Y, `WorldView.getCollisions` moves the starting exclusion shape by `+1.0E-7` in Y. For positive X it moves that shape by `-1.0E-7` in X, and zero Z uses `+1.0E-7` in Z. These signs and the X exclusion are unchanged from the `-0.003` witness (`1.13 WorldView.java:191-196`; `1.13.1/1.13.2 WorldView.java:158-163`). The slabs remain outside the start box in X, while the swept shape still reaches them.

The first slab's positive X face gap remains `2 - 1.99999995 = 4.9999999918171056E-8`; changing dy does not change the X gap. In 1.13 the single already-aggregated shape is clipped once, retaining that sub-epsilon positive offset. In 1.13.1/1.13.2 the partial-shape stream visits the X=2 slab before X=3. After clipping against the first slab leaves the same gap, `calculateMaxOffset` tests strict `abs(maxDist) < 1.0E-7` before consuming the next shape and returns zero, so the second slab is not read by that loop (`VoxelShapes.java:194-205`). The horizontal outcomes on this fixed operation remain 1.13 `4.9999999918171056E-8` and 1.13.1/1.13.2 zero.

The step-up gate is still closed. `o` is the requested Y after the unchanged pre-clip gates; because Y is unclipped, `o == y == -50/16383`. With `onGround=false`, `bl = onGround || o != y && o < 0.0` is false (`Entity.java:601`). Horizontal clipping therefore does not open the step-up branch, and its second collector query is not reached.

## Boundary

This extends the existing fixture's query/clip derivation to a different Y value admitted by the audited 26.2 codec and retained by the old strict cleanup. All geometry, gates, input/no-input condition, and operation order are held fixed. It is not a new shape/provider finding, runtime result, common-trajectory claim, cross-version packet match, implementation, or parity result. The preexisting exact `-0.003` author/audit remains bound to its original input; this note does not rewrite it.

## Canonical source identity

Read-only canonical sources identify Ornithe Feather Gen2 build 2 for 1.13, 1.13.1, and 1.13.2 (`decompiled_minecraft/<version>/ornithe-feather.ready.json`) and unobfuscated 26.2 (`decompiled_minecraft/26.2/unobfuscated.ready.json`). The 1.13.1 and 1.13.2 movement, collector and shape-clipping files cited for this operation have identical hashes.

- 1.13 `net/minecraft/entity/living/LivingEntity.java:1598-1631`, `net/minecraft/entity/Entity.java:530-602,1056-1073`, `net/minecraft/world/WorldView.java:191-196`, `net/minecraft/block/shape/VoxelShapes.java:224-231,207-266`.
- 1.13.1 and 1.13.2 `net/minecraft/entity/Entity.java:530-602`, `net/minecraft/world/WorldView.java:116-164`, `net/minecraft/block/shape/VoxelShapes.java:194-205`, `net/minecraft/block/shape/VoxelShape.java:184-231`.
- Relevant source hashes were read directly and match the source-file hashes recorded by the earlier independent fixture audit: 1.13 `Entity.java` `09514488D9055449C47E2E4BCD23B5773203CA634B7ECBA2323A99D0AE016642`; `LivingEntity.java` `C38E95C2C13E088B03D77FC2C6B98D4EDD7E6CA5AEF12B187C8E3F3EA782E864`; `WorldView.java` `B0D1406FB04EACBC644A78548F858D5FF6C14AD781927832CB374E985F32A543`; `VoxelShapes.java` `6AE1A6FA0AB76098252208FAD00A6D4F3D3F1CF2023D76F63D450855DF00A330`. 1.13.1/1.13.2 `Entity.java` `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269`; `LivingEntity.java` `BB691358C9A43C9F46E85575BF4D0A4AD671D0EB912502ACC3A6A3F625E42F1C`; `WorldView.java` `9577360B1A1BDDE9280F0B72593E5E6DDE45D9A54719CD7FF933F3ECD31B971F`; `VoxelShapes.java` `313968D4E5855B5EC380272B6ABABCC5849D478AB771926355912A8CB6AA9C95`; `VoxelShape.java` `D95B0607647DA11595E24E1E26E9615CB7DA7705EA7885908544362DF62275C1`.

The independent original fixture audit is `domain-b-aggregate-negative-y-audit/negative-y-source-audit.md`; the later producer audit is `domain-b-aggregate-dry-producer-audit/verdict.md`. The source notes were read without product code, wiki material, backlog, or Git history. No test, build, decompilation, or runtime launch was performed.
