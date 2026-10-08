# Independent blind review: MC1204-1206-04

- Decision: **ACCEPT** (finding snapshot only)
- Pair: 1.20.4 → 1.20.6; the pair remains `PARTIAL`.
- Scope: the corrected immutable finding, its paired vanilla source proof, and the fall-flying correction. No implementation, other pair finding, wiki or MCPK material was read.

## Snapshot binding

- Commit: `13cf725ab0ac943f78c2ec7e5851a473022edfb4`
- Path: `workflows/source-campaign-2026-10-07/1.20.4--1.20.6/findings/MC1204-1206-04.md`
- Blob: `a0f45a5a2ae43c5f3bce2fcc4931c9ecb329f8d0`
- Raw SHA-256: `3354ad673e73ec35247699184eb26fcf82519f4dd2cf6963164d71c35add0a2f`

Ready markers resolve the exact 1.20.4 and 1.20.6 Mojmap trees. Their source-manifest hashes are `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` and `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact-manifest hashes are `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` and `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`. Manifest bytes match their ready markers. Each source-file digest cited in the finding was checked against both the source bytes and corresponding manifest.

## Review basis

The addition requested in the previous review is supported: A's `LivingEntity.travel(Vec3)` uses its same local gravity in the fall-flying update (A lines 2035–2166, with the glide consumer at 2099–2125); B reads `getGravity()` and uses that local in the corresponding fall-flying update (B lines 2104–2233, consumer at 2168–2194). `Player.travel` delegates to `super.travel` on the non-abilities-flying path, and the client jump/Elytra path can start shared fall-flying flag 7. The corrected finding cites these call and state paths.

At default effective gravity `0.08`, the descending Slow Falling local is `0.01` in both versions (`Math.min(0.08, 0.01)` in B). At a non-default value it can differ; when the local is zero because `noGravity` is set, A's fall-flying expression still consumes its fixed `0.08` while B consumes zero. This branch has no `isNoGravity()` guard. Conversely, A's land and fluid helper gates use `!isNoGravity()`, while B's helper uses effective-gravity nonzero; `Entity.getGravity()` in B returns zero when `isNoGravity()` is true. Those gates do not make the corrected fall-flying consumer unreachable. The source result is conditional on vanilla state and effective gravity; this review makes no decision about emulation scope for the modern attribute.

The reported zero-gravity fluid-helper edge is also source-supported: if `noGravity` is false but the B effective gravity value is zero, A enters its `!isNoGravity()` helper and can apply the near-terminal `-0.003` clamp, while B skips the helper on `gravity != 0.0`. A and B's exact helper ranges and the server-to-client attribute receiver are bound to the hashes recorded in the finding.

## Limits

This accepts only the corrected finding's source evidence and reachability. It does not establish a first release within the interval, a producer for non-default synchronized gravity, full gravity-inventory closure, or runtime behavior. Pair status remains `PARTIAL`.
