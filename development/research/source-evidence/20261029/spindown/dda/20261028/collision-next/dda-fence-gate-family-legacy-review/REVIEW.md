# Independent review: legacy fence and gate provider closure

Verdict: **REVISE** (bounded family/provider findings are otherwise supported).

Reviewed `../dda-fence-gate-family-legacy/FINDING.md` against canonical 1.8.9 and 26.2 source. Every source-file digest listed in the candidate's `sources.sha256` matches the corresponding canonical file. The candidate report and digest-list SHA-256 values are recorded below.

## Required correction

The report says `FenceBlock.getMetadataFromState` is inherited from `Block` and rejects a nonempty property set. That is false for fences: `FenceBlock.java:149-152` overrides the method and returns `0` unconditionally. `Block.java:186-191` has the rejecting implementation, but it does not apply to this subclass. Replace the claim with: fences decode metadata through the inherited `Block.getStateFromMetadata` to the default state (whose four virtual connection properties are false), and `FenceBlock.getMetadataFromState` always returns zero. Collision collection then independently probes live neighbors. The rejection statement remains true for classes which actually inherit the base serializer, not for `FenceBlock`.

This is a source-report correction; it does not change the provider algorithm or invalidate the alias/provider closure.

## Verified claims and limits

- The listed old IDs and flattened aliases are consistent with the old `Block` registrations and current `EntityBlockStateFix` mappings. The separate item-flattening mapping is properly not used as block-state evidence.
- The old fence provider recomputes N/S/W/E connections in that order, assembles the N/S and then W/E boxes with the described conditional arms, clips each box through `Block.addCollisions`, and restores height 1 bounds after the height-1.5 collision boxes. Its admission rule matches the report: barrier rejected; same-material fences or any fence gate accepted; otherwise solid-blocking cubes accepted except pumpkin.
- Old gate collision is null when OPEN; otherwise it is the FACING-axis 4/16 center strip to Y=1.5. POWERED and IN_WALL do not affect this collision shape. `updateShape` separately refreshes the mutable outline bounds to Y=1, and base `Block.rayTrace` calls it before reading those bounds.
- The current provider divergences are accurately bounded: current fences use stored directional state and modern tag/sturdy-face/gate-axis rules; current gate collision uses the same open/facing dimensions, while `IN_WALL` changes outline/occlusion behavior. No full DDA visitation or modern-neighbor parity is claimed.

## Digest bindings

- Candidate finding: SHA-256 `1a573fc598c9a35e4a3a89d593519272c534680301639dea35606b651f49b3db`
- Candidate source manifest: SHA-256 `6a77193d91a7acdcd6f791dfa1cfc45a83b686cf4f2249a9dd7b5d3d9d039da6`
- All 13 source entries in that manifest were independently rehashed and matched.
