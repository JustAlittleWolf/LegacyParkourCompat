# Independent blind review: F-26.2-SLIME-LANDING-RESTITUTION

- Decision: **ACCEPT** (finding snapshot only)
- Pair: 26.1.2 → 26.2; the pair remains `PARTIAL`.
- No implementation, other pair finding, wiki or MCPK material was read.

## Immutable snapshot binding

- Commit: `8eb345d5973158e7e1bc26a2044d63fb77b45e19`
- Path: `workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-SLIME-LANDING-RESTITUTION.md`
- Blob: `7e1487f380b34b900408d7ad33be3c410a0b2bf9`
- Raw SHA-256: `c14302ffa5b0a773d1e27adbb7f8fbe12188066c80763bdac4a8a02ef0446a05`

The exact source publications are 26.1.2 unobfuscated (source/artifact manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` / `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`) and 26.2 unobfuscated (`a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` / `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`). Manifest bytes match ready markers; cited source hashes match their bytes and manifests. The 26.1.2 and 26.2 original client-jar hashes match the finding. In 26.2 the suppression-tag resource's raw JSON lists only `minecraft:honey_block` (its bytes omit an explicit `replace:false` field); slime is not suppressed by the default tag.

## Source and witness review

In A, the local player reaches `Entity.move` and, on a downward clipped collision, the selected slime block's callback reads the still-negative entity Y velocity and reverses it for a `LivingEntity`. In B, `Entity.move` calls generic restitution after collision resolution. Its downward gate sets restitution to zero whenever `-currentMovement.y < getEffectiveGravity()`. In that zero-restitution branch, the later Y formula is zero regardless of the nonzero clipped `movement.y`; this finding's low-speed conclusion does not make the bed finding's zero-clipped-distance assumption.

A bounded vanilla witness is a standing player with no Slow Falling on a 1/16-high carpet, walking off onto an adjacent existing slime block one carpet-height below. Both endpoint `CarpetBlock` shapes have height `1/16`; slime is a full block and predates both endpoints. At default gravity `0.08`, the first unobstructed falling travel step from zero Y velocity produces approximately `currentMovement.y = -0.0784` after the ordinary `0.98F` Y drag. The downward clip to the slime top is `movement.y = -0.0625`. Thus B compares `0.0784 < 0.08`, selects zero restitution, and writes Y zero; A's callback sees a negative Y velocity and writes a positive value. The player is a local `Player` (`canSimulateMovement()` returns true), is not shift-suppressing bounce, and default slime is not in `SUPPRESSES_BOUNCE`. Player BOUNCINESS does not affect this B outcome because the low-speed branch explicitly resets restitution to zero. The witness uses existing carpet/slime blocks, not modern-only block states.

The default gravity bound is the living/player gravity default `0.08` with no Slow Falling; the effective-gravity method returns that value for the witness. The proof is source-level and makes no runtime trajectory claim. First-change version, wider restitution inventory, full-pair closure and implementation/runtime status remain open.
