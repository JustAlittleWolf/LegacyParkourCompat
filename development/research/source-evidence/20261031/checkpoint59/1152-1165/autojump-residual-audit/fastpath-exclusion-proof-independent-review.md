# Independent review: auto-jump full-cube fast-path exclusion

## Verdict: REVISE

Reviewed proof `.native-source/20261030/1152-1165-finish43/autojump-residual-audit/fastpath-exclusion-proof.md`, SHA-256 `98cdda70fe2d755f0d0caf3f7806283e78675e50817b669cd62054baf4345938`. Its geometric idea is sound for the stated ordinary standing box: the broadphase is built before the +0.51F side-segment offsets; the offsets lie within the swept centerline AABB plus approximately half-width, while the query adds a full width; and the resulting margins dwarf the `findBits` endpoint tolerance. But the proof as written does not yet support its full-caller closure wording because it states an overly narrow maximum fringe, assumes an exactly unit vector after approximate float normalization, and scopes its dimensions to standing without closing the other vanilla LocalPlayer poses.

## Checks that pass

- Exact order in both LocalPlayer endpoints: 1.15.2 `LocalPlayer.java:905` and 1.16.5 `:917` construct `new AABB(vec36, vec37.add(0.0, t, 0.0)).inflate(s, 0.0, s)` **before** `vec36/vec37.add(0.0,0.51F,0.0)` and the lateral vector/segments. Old segment endpoint construction is `:906–913`; new is `:918–925`.
- For a standing player `s=0.6`; the side offset uses `s*0.5F`, while the broadphase adds `s` to both horizontal ends. Even allowing float normalization error, this leaves roughly `0.3` horizontal margin. For `t=1.8`, a side hit on a unit cube has at least `0.51` vertical query overlap. These values safely exceed any snap change from `findBits`.
- The updateAutoJump response checks the side segments before assigning `u` and eventually `autoJumpTime`; the broadphase-only candidate cannot affect the result unless a tested segment intersects it. No other `updateAutoJump` operation change is implicated here.

## Corrections needed

1. The `1.25E-8` value is the per-coordinate tolerance at resolution `i=3` (1/8 grid), not a global upper bound for every accepted `Shapes.create(AABB)` grid. `Shapes.findBits` (`1.16.5 Shapes.java:89–104`) tests resolutions `i=0..3` with scaled tolerance `<1.0E-7`; raw endpoint rounding is therefore `<1.0E-7 / 2^i`, up to `<1.0E-7` at `i=0`. For the standing margin this does not alter the result, but revise the asserted global bound or prove the query always selects `i=3` when rounded.
2. `vec34` uses float `Mth.fastInvSqrt(i)` (`Mth.java:374–380`) rather than exact real normalization, so `vec38` is not source-proven to have exact unit length. Replace “at most `s/2`” / exact `0.3` with a conservative bound that includes the float normalization error. The remaining margin still exceeds `1.0E-7` by a wide factor.
3. The proof says “For an ordinary local player standing box” but its disposition closes the fast path for `updateAutoJump` generally. `canAutoJump()` does not check pose and computes `s/t` from the current player box. Either scope this closure explicitly to the standing-box route, or close all vanilla poses using the exact paired `Player.getDimensions(Pose)` table and show why each pose cannot produce a grid-rounded query or retains a sufficient margin. Do not imply whole-member closure until that direct caller-state boundary is handled.
4. Correct the new LocalPlayer source line references: the broadphase construction is line 917, not 918–921; offsets/cross vector are 918–921, side endpoints 922–925, iterator starts 926. The old equivalent construction is line 905, followed by offsets/cross 906–909, endpoints 910–913, iterator 914. The checked arithmetic/order is otherwise identical.

## Hash and evidence checks

The cited proof-input hashes match current files: proof `98cdda70fe2d755f0d0caf3f7806283e78675e50817b669cd62054baf4345938`; collision report `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`; collision source hash table `6cee71d9366c803e94e3523d0dffd7c400d7b41bd1962e8b260ddafc58ed9c9a`.

The cited canonical sources rehash to their own manifests: 1.15.2 manifest `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; 1.16.5 manifest `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99d2b2a20823b`. Specifically, both LocalPlayer files, 1.16.5 `AABB.java`, `CollisionGetter.java`, `CollisionSpliterator.java`, and `Shapes.java` all matched. The supporting `Mth.java` hash is `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` and also matches the 1.16.5 manifest. The cited independent collision report pins these exact source identities.

This review leaves only the narrow updateAutoJump/full-cube collector dependency scoped as above; it makes no pair-wide closure or runtime claim. No product, wiki, backlog, build, test, decompilation or runtime files were changed or used.
