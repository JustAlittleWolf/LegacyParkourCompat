# Independent source audit: LocalPlayer auto-jump

Verdict: **ACCEPT** — the bounded `LocalPlayer` auto-jump decision/probe/timer-to-input handoff has no source difference between 1.21.11 and 26.1.2, for the claim as scoped by the reviewed report.

## Immutable audited claim

- Candidate: `build/later-source-20261116/1.21.11--26.1.2/autojump/source-coverage.md`
- Candidate SHA-256: `39D5BB2EAC741DF6B038D49C8E2D0F2114114A9EC91753E6C4BBBC04FF668E9F`
- Candidate bytes were re-hashed before review; the hash matches the assignment.

## Exact source identity

- 1.21.11 Mojmap: ready marker `decompiled_minecraft/1.21.11/mojmap.ready.json` (SHA-256 `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B`); source-manifest bytes SHA-256 `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555`; artifact-manifest bytes SHA-256 `C8210B15012DCC4E109C2F73015B4117ABEC1F8AB04FDD375DBC11419E9E246C`; provenance `decompiled_minecraft/1.21.11/mojmap.provenance.json` SHA-256 `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72` records an exact 1.21.8/1.21.10/1.21.11 Mojmap decompile run.
- 26.1.2 unobfuscated: ready marker `decompiled_minecraft/26.1.2/unobfuscated.ready.json` (SHA-256 `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`); source-manifest bytes SHA-256 `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`; artifact-manifest bytes SHA-256 `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92`; provenance `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` SHA-256 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A` records the exact unobfuscated 26.1.2 run.
- Each ready marker's declared source-manifest and artifact-manifest SHA-256 matches the corresponding files. No source archives were regenerated.

The candidate's cited `LocalPlayer.java` hashes were independently recomputed and matched both source-manifest entries:

| Endpoint | Canonical source | SHA-256 |
| --- | --- | --- |
| 1.21.11 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 26.1.2 unobfuscated | `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |

## Independent comparison

- `LocalPlayer.move` snapshots X/Z, calls `super.move`, casts the resulting X/Z position deltas to `float`, and passes those actual deltas to `updateAutoJump` in both endpoints. The call order and casts match.
- `canAutoJump` has the same gates and order: enabled, timer nonpositive, on ground, not staying on a ground surface, not riding, nonzero move vector, and block jump factor at least `1.0`. `isMoving` uses the same `lengthSquared() > 0.0F` predicate.
- `updateAutoJump` matches in its fallback rotated-input vector and `0.001F` cutoff, facing dot cutoff `-0.15F`, two empty collision-shape probes above the bounding box, jump-height base/amplifier arithmetic, look-ahead calculation, inflated AABB, `+0.51F` side-segment probes, half-width offsets, collision AABB iteration, obstacle-height checks, return/break positions, `0.5F` lower bound and timer assignment. The detector writes only `autoJumpTime`; it does not directly mutate position, velocity or the bounding box.
- I independently checked the generic dependencies used by the probe. `CollisionGetter.getCollisions` still concatenates entity and block collision shapes; `EntityGetter.getEntityCollisions` retains its area cutoff, tiny inflation, selector and entity-box conversion; `BlockCollisions` retains the cursor bounds, filters, context shape lookup, full-block fast path and intersection check. Its `ChunkPos.asLong`→`ChunkPos.pack` rename preserves the identical two-int bit packing expression. `VoxelShape.toAabbs` and `AABB.intersects(Vec3, Vec3)` preserve the same enumeration and strict min/max tests. `CollisionContext.of(player)`, entity context creation and shape forwarding keep the same relevant values and gates.
- `autoJumpTime` is decremented in `aiStep` in both sources; a positive timer marks the auto-jump-origin flag and calls `input.makeJump()`. The flag excludes the same creative-flight toggle case and is absent from the adjacent fall-flying predicate in both endpoints. `ClientInput.makeJump()` has identical bytes and sets the record's `jump` field while retaining all six other input fields; the `Input` record has the same field order and jump meaning at both endpoints.

Relevant dependency file hashes were also verified against their endpoint source manifests. `ClientInput.java` is byte-identical across endpoints (SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`). The remaining inspected classes were `Input`, `CollisionGetter`, `EntityGetter`, `BlockCollisions`, `ChunkPos`, `CollisionContext`, `EntityCollisionContext`, `VoxelShape`, and `AABB`; every inspected on-disk hash matched its source-manifest entry.

## Boundary

This accepts only the reviewed LocalPlayer auto-jump controller and generic probe/handoff semantics for these endpoints. It does not freeze the pair or establish complete player-movement coverage. Block-specific collision-shape implementations/resources, inherited `super.move` collision/step behavior, ordinary travel/input movement, other player movement operations and runtime parity remain outside this bounded audit. No runtime was exercised.
