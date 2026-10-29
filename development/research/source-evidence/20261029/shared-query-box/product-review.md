# Product review: shared final query box for callback scan

## Verdict

**ACCEPT**, limited to the 1.21.2/1.21.3 final-query-box operation in product commit `02611d82c73c15e60d597f48f51141fbd7e3bfb0`. The assigned worktree is clean at that commit, and `git diff --check` passes. This review does not accept or claim full callback traversal restoration.

## Operation and source math

The accepted source comparison reports byte-identical 1.21.2/1.21.3 `Entity.java` and shows `checkInsideBlocks(List, ...)` computes one `getBoundingBox().deflate(1.0E-5F)` before iterating its movement records. The product captures that same final entity box and exact float literal once per list invocation, then makes the existing per-segment `AABB.deflate(D)` redirect return the captured box. This reproduces the source query box without changing the separate per-segment reverse sweep predicate.

The context is an identity-keyed map on a `ThreadLocal`, with a stack per entity. This supports nested calls for the same entity and interleaved scopes for different entities on one thread. `try/finally` closes the scope on normal return and exceptions; `close()` validates LIFO ownership, rejects double close, removes the entity entry, and removes the thread local when empty. The active box is only read in the existing per-segment inset redirect. With no scope, it falls through to the preexisting `InsideBlockContactHook` behavior.

## Applicability and wiring

The static resolver projection on this exact tree selects `EntityInsideBlocksQueryBoxNativeFence` for `V1_21` (1.21/.1), `EntityInsideBlocksSharedQueryBox` for `V1_21_2` (1.21.2/.3), and no eligible provider for `V1_21_4`, `V1_21_6`, or `CURRENT`. Thus the shared-box operation is bounded to the accepted .2/.3 source alias; .4+ and current behavior remain native. The lower fence returns false, and the mixin defaults missing providers to false.

The wrapper is a `@WrapMethod` on the existing list-based `Entity.checkInsideBlocks(List, StepBasedCollector)` method. It requires a `LocalPlayer`, client-side level, nonremoved entity, physics enabled, and a selected provider requesting the shared box. Those gates align with the source method's block-affecting guard (`!isRemoved() && !noPhysics`) and the local-player client scope. The original method receives the exact movements and collector unchanged. The new mixin is registered in `legacyparkourcompat.client.mixins.json`; the existing deflate redirect is wired to read the scoped value.

The wrapper leaves DDA cell enumeration, endpoint nudges, visitor iteration labels, retained movement-list contents, collector staging, and axis order unchanged. In particular, it neither clears nor snapshots the list and does not substitute a visitor. These independent operations remain open.

## Evidence

- Accepted source report: `.native-source/20261029/partial-sweep-1212-alias-closure/report.md`, SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4`.
- Independent source audit: `.native-source/20261029/partial-sweep-1212-alias-audit/audit.md`, SHA-256 `601e1ec94848d2651ceecb8249f08fe3b138c476e2103c5a7fc5410ca72aa34e`.
- Neutral predicate candidate: `3235831862f95ec3357f2e7b626d23a0e594672c`; candidate review SHA-256 `622e86851048dabbb6d33af726aec4ab07047c0ade3b16e5c50099eaeeae8501`.
- Separate traversal candidate's native-fence follow-up: `62136c0cc78f0cc3c8b5cd2e163bbce08a4f4fa9`; review SHA-256 `8eedfcbc6316ab0b95987679c0d309761e8769f44ca7fd711579deb14c075b08`. That candidate is not integrated by this commit.

No build, tests, or runtime launch was performed.
