# WORLD-07-nether-portal-entity-eligibility

Predicate differs for generic entities, but the archived finding explicitly leaves passenger/player reachability open; do not promote it to a proven player delta.

## Needed evidence

- Later applicability gate: exact introduction of campaign-WORLD-07-nether-portal-entity-eligibility is unknown within (1.8.9, 1.9.4] Next: Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance.
- Current implementation of campaign-WORLD-07-nether-portal-entity-eligibility Next: After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase.
- Player-specific difference in canUsePortals and passenger-state applicability Next: Resolve the archived conditional player reachability claim from existing source evidence before accepting an implementable operation.

```json
{
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/PortalBlock.java",
      "member": "public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {",
      "lines": "162-166",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bb9d2cba26df71c8383408fcccc93b849eebecd3d36924a63ee80cba8734867e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ea72fa6c3bf26f0135cb762e47b7f8ddd344f0495f5115c02ff9883da264351c."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#canUsePortals",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/PortalBlock.java",
      "member": "public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {",
      "lines": "170-174",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8f6c4502a2b910ad8eaaefed19d162a3319e9afc6802c8ccb3e7e05b3987c598; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 f0a645d052ee72f4733bd812337f072983d0e61f89079b2189150e5cff5791e6."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean canUsePortals() {",
      "lines": "1895-1897",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5e4539512b0df9a1df1bb642ca79455eb1d6304404c6ce04a99249731de74c9a."
    }
  ],
  "sourceReviews": [],
  "implementation": "not admitted",
  "runtime": "unverified"
}
```
