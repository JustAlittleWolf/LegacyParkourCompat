# Independent audit: 1.20.3–1.20.4 default boat rider tag

## Frozen input identity

- Input: `c-queue-1203-1204-boat-tag-default/report.md`.
- Supplied body SHA-256 `f30d6ef1463f0eb0cf42f08c7449bf6bebe609bfe95a7bf1fa342c911a8cfaae` verified over UTF-8 content with terminal line and trailing LF omitted. Whole-file SHA-256: `2298a9d0dad0607fbd0489eaad08ff92eebdf49ab6fe540e91195722888a9c3b`.
- Scope: exact local Mojmap sources, source provenance/manifests and existing hash-pinned vanilla archives only. No downloaded artifacts, product code/history, wiki, tests, runtime, or generation.

## Verdict

**ACCEPT the source-defined default-base-tag claim for both 1.20.3 and 1.20.4.** In the exact `net/minecraft/data/tags/EntityTypeTagsProvider.java` implementations, `addTags` does not define `CAN_TURN_IN_BOATS`, and no `PLAYER` membership is added. The `EntityTypeTagsProvider` is registered in the ordinary built-in pack group in `net/minecraft/data/Main.java`. The only located Java membership is `BREEZE` in `UpdateOneTwentyOneEntityTypeTagsProvider`; `Main.java` registers that provider on the separate `update_1_21` feature pack with `FeatureFlags.UPDATE_1_21`. That does not put `PLAYER` in the default base tag.

A missing tag JSON in an exact archive is valid supporting evidence of no packaged definition in that archive. The locally cached 1.20.3 client archive is pinned by `client.jar.pin.json` to SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`; recomputation matches, and the archive has no `data/minecraft/tags/entity_type/can_turn_in_boats.json` entry. Therefore the 1.20.3 packaged base tag is absent/empty by default, absent a datapack override.

**The package-level claim is not established for 1.20.4.** No exact 1.20.4 archive exists in the permitted cache. Keep this as a narrowly scoped evidence limitation; the exact 1.20.4 source-defined default-provider result is still supported.

**The two boat paths are separate.** In both releases, `Boat.positionRider` tests `EntityTypeTags.CAN_TURN_IN_BOATS` and conditionally calls `clampRotation` in the untagged branch. Separately, `Entity.turn` calls `vehicle.onPassengerTurned(this)` after mounted turning, and `Boat.onPassengerTurned` calls `clampRotation`; that callback has no tag predicate. The tag's default absence informs rider positioning but does not gate the mouse-turn clamp. This audit does not reassess placement timing or boat physics.

## Evidence checks

- Exact paths checked in both `decompiled_minecraft/1.20.3/mojmap` and `decompiled_minecraft/1.20.4/mojmap`: `net/minecraft/data/tags/EntityTypeTagsProvider.java`, `net/minecraft/data/tags/UpdateOneTwentyOneEntityTypeTagsProvider.java`, `net/minecraft/data/Main.java`, `net/minecraft/world/entity/vehicle/Boat.java`, and `net/minecraft/world/entity/Entity.java`.
- For all five exact Java files listed above, the recomputed SHA-256 matches the version-specific source provenance/manifest entry in each release. The relevant provider and Main.java hashes also match the frozen report.
- Exact 1.20.3 archive absence was checked by enumerating the pinned client ZIP; it was not inferred from source omission alone.
- No exact 1.20.4 archive was available to independently check generated resource-pack output or packaged data.

## Boundaries

This verdict covers default vanilla tag membership and the direct source gates named above. It does not cover datapack overrides, runtime behavior, placement timing, or boat physics. It does not close the 1.20.4 packaged-artifact evidence gap.
TERMINAL: COMPLETED; verdict-body SHA-256: 25e868b577143a4b72cf0809e320a4b7b3357f4cbe30e5ff3f5d7cc1d7797543; frozen input body SHA-256: f30d6ef1463f0eb0cf42f08c7449bf6bebe609bfe95a7bf1fa342c911a8cfaae; residual evidence gate: no exact 1.20.4 package in permitted cache; bounds: default tag/provider and direct rider-position versus mouse-turn source paths only, no runtime/placement timing/boat physics.