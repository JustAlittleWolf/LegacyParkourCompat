# Independent audit: player equipment and movement-attribute lifecycle

Verdict: ACCEPT for the bounded lifecycle coverage report only.

Bound input: `build/source-189-19-20261117/tick-state/equipment-lifecycle-followup.md`, SHA-256 `F9DC9EAA9D036259407A59BC375CEEB2662F53E742FBB3BB50788271D8137EA7`.

The 1.8.9 root source manifest and provenance hashes match the assigned identities (`9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`). The 1.9 mapping-local provenance hash also matches (`8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`). All fourteen cited Java source hashes match disk and their respective source manifest/provenance entries.

The reviewed source supports the report’s scoped conclusions:

- Both player `mobTick` paths set the movement-speed attribute base from abilities only under `!world.isClient`, then cast the attribute value to float for `setSpeed`; `PlayerEntity.getSpeed()` reads the attribute, and the inherited travel code consumes it. The attribute modifier computation order is unchanged.
- Equipment comparison, modifier install/removal, cache writes, and equipment packet production are inside the server-only living-tick path in both versions. The slot API and enumeration differ, but these are server-side synchronization/production operations.
- Client equipment handlers update equipment storage without computing or applying attribute modifiers. Client `handleEntityAttributes` replaces the received base and modifier set. Inventory selection/swap paths update inventory state but do not locally recalculate movement speed.
- Client status-effect packets update the effect collection; the add/upgrade/remove callbacks add or remove effect attribute modifiers only on the server. The report correctly limits this conclusion to the lifecycle checked and does not infer packet scheduling or production policy.

This verdict does not re-audit or extend the separate accepted custom `Slot` mismatch candidate. It accepts only the lifecycle trace, not the candidate itself or full-pair coverage. Pose-shape closure, piston response, other provider dependencies, and independent full-pair coverage audit remain separate open work.
