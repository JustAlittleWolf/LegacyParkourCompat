# 1.9.3 profile-boundary impact plan

## Scope and conclusion

This is a read-only product impact assessment. It used the project guidance and product sources only; it did not inspect campaign records, reports, candidates, or Git history. No product source was changed, and no tests, build, game, or runtime were run.

**Conclusion:** The boundary can be represented with an ordinary `ParkourVersion` enum entry and patch-string group. No `ChangeResolver`, `MovementChangeDiscovery`, or wire/config format refactor is required. However, adding the enum entry alone does not preserve effective operation coverage: after the split, changes annotated only `V1_9` are older than a selected `V1_9_3` profile and are excluded by the existing resolver. Each affected operation must be checked and either (a) have its existing implementation annotated at the new boundary when the same behavior spans both profile buckets, (b) have distinct `V1_9` and `V1_9_3` implementations when the behavior differs, or (c) intentionally retain native/no-change behavior with an explicit boundary fence where needed to block an inappropriate later change. The resolver and discovery already support all three through ordinary enum order and annotations.

## Current version and profile identity

`ParkourVersion.V1_9` currently owns patch IDs `1.9`, `1.9.1`, `1.9.2`, `1.9.3`, and `1.9.4`. Its canonical `id()` is the first patch, `1.9`. `ParkourVersion.of` maps every exact patch string in this list to that same enum value. The next enum entry is `V1_10`.

Recommended representation for an exact split:

- Keep `V1_9` canonical ID `1.9`, with patches `1.9`, `1.9.1`, `1.9.2`.
- Insert `V1_9_3` immediately after `V1_9`, with patches `1.9.3`, `1.9.4`; its canonical ID is `1.9.3`.
- Keep `V1_10` next after the new entry.

The existing display formatter then renders the split groups as `1.9-1.9.2` and `1.9.3/4`. `CURRENT`, its native-version identity, and the empty/vanilla default remain unchanged.

## Resolver and discovery impact

`ChangeResolver` filters out changes whose `emulates()` is older than the selected profile, then picks the smallest ordinal among remaining candidates for each mechanic. A chronological `V1_9_3` inserted between `V1_9` and `V1_10` preserves this algorithm and provides the intended profile cut: a `V1_9` candidate can serve pre-boundary selections when it is the nearest later candidate; a `V1_9_3` candidate is eligible for both `V1_9` and `V1_9_3`, while for `V1_9_3` it wins over `V1_10` candidates. A distinct `V1_9_3` implementation naturally supersedes a `V1_9` implementation only for the new profile and later selections.

`MovementChangeDiscovery` scans all `change.v<digits...>` directories and registers each annotated class at the annotation's enum value. A `change/v1_9_3/` package and `@MovementChange(emulates = ParkourVersion.V1_9_3)` require no discovery change. Package name is not the version authority.

Inserting an enum constant shifts the ordinal of every later constant, including `CURRENT`. Ordinals are used by `ParkourVersion` chronological comparisons/`next()` and by the resolver comparator; a correctly placed new value keeps their ordering semantics correct. Ordinals are not used as a persisted or network profile encoding in the inspected product code. They are nevertheless observable through the public enum API, so consumers that persist or hardcode ordinals would be affected; the repository's own profile formats do not do so. Java enum serialization is not used for profile persistence or packets.

## Existing provider and annotation inventory

The following classes under `src/client/java/.../change/v1_9/` currently declare `@MovementChange(emulates = ParkourVersion.V1_9)`:

- `FallFlyingSavedState` — `FallFlyingSavedStateHook`; includes exact comparisons against `ParkourVersion.V1_9`, so its behavior must be checked explicitly at the new boundary.
- `NativeAutoJumpFallbackTrig` — `AutoJumpFallbackTrigHook`; its source comment says the native double-index lookup is preserved through 1.9.4.
- `NativeHeadingVelocity` — `HeadingVelocityHook`; native fence after the 1.8.9 arithmetic boundary.
- `NativePlayerPushability` — `PlayerPushabilityHook`; a native applicability fence before a later 1.10–1.10.2 implementation.
- `NativeWaterBodyContact` — `WaterBodyContactHook`; native 1.9+ contact fence.
- `NeighborCollisionScan` — `BlockCollisionScanHook`; its comment states 1.9.1–1.9.4 retain the 1.9 scan.
- `PistonHeadCollisionShape` — `BlockCollisionShapeHook`; its comment states 1.9.1–1.9.4 retain 1.9 bounds.
- `UnprovenFishingRetrieval` — `FishingRetrievalImpulseHook` and `FishingRetrievalTargetHook`; an applicability fence where an earlier response is unproven and a later 1.10/1.11 comparison establishes only later behavior.
- `player/PlayerPoseCollision` — `PlayerDimensionsHook` and `PlayerPoseHook`; its comment states the 1.9–1.9.4 pose selection/query/resize/eye-height behavior is identical.

Other files happen to live in this directory but are not `V1_9` annotations: `CocoaCollisionShape` is `V1_10_1`, and `PushAwayVelocity` is `V1_13_1`; their annotation boundaries must not be changed merely because of directory names.

The product comments explicitly establish same 1.9 behavior through 1.9.4 for `NeighborCollisionScan`, `PistonHeadCollisionShape`, `PlayerPoseCollision`, and `NativeAutoJumpFallbackTrig`. Those can be represented by a single implementation at `V1_9_3` if the 1.9–1.9.2 applicability is intended to remain the same; because the resolver chooses later changes for older selections, that candidate still covers `V1_9`. Other current V1_9 providers need operation-level review. In particular, the two explicit V1_9 equality gates in `FallFlyingSavedState` cannot be assumed correct for the newly distinct profile. The native fences on player pushability and fishing retrieval may need a `V1_9_3` continuation to prevent a later annotated implementation from being selected too early. This is an applicability consequence, not a need to alter resolver logic.

The direct threshold references to `V1_9` outside those provider annotations are in `HistoricalElytra`, `HistoricalRideables`, and later change logic. Ordinal threshold checks (`olderThan`/`newerThanOrEqual`) remain correctly chronological with the new enum. `HistoricalRideables` uses `V1_9` as the first version containing the 1.9 boat types; the new 1.9.3 bucket remains newer than that introduction threshold. Exact-identity checks (the two in `FallFlyingSavedState`) need deliberate split semantics.

## APIs, parser, UI, and persistence

- `ParkourVersion.of`, `tryOf`, `patches`, `id`, `toString`, and the `MovementController.select(String)` overload use patch strings and enum mapping, not ordinals. The new exact strings map to `V1_9_3`; `1.9` continues mapping to `V1_9`.
- `MovementController.selectableVersions()` / `ParkourVersion.selectable()` expose enum entries. The UI obtains the list dynamically, adds `CURRENT`, reverses historical order, and labels each entry with `displayLabel()`. Thus the new selectable item and split labels arise without special UI ordering code. `MovementVersions` keeps its typed input in static session state; its comments explicitly say closing the game returns to vanilla, so the inspected client does not persist this selection.
- `MovementVersions.parkourVersion` accepts canonical IDs or any `patches()` alias. `MovementVersions.selectedVersion()` returns the canonical `id()`, not the raw input string.
- The durable profile input found in the product is the dedicated-server `config/legacyparkourcompat.properties` key `movementVersion`; blank remains the default and means native/vanilla. It is parsed through `ParkourVersion.of` and is not rewritten by a migration. `movementVersion=1.9` remains V1_9. A preexisting `movementVersion=1.9.3` will now resolve to V1_9_3 rather than the old grouped V1_9; this is an intentional meaning change if the ID denotes the exact Minecraft release. Keeping that old alias meaning while simultaneously using the same exact release ID for the new profile would require an additional legacy-compatibility rule and would defeat unambiguous exact-version selection. The project says not to preserve compatibility with prior mod releases; no data-format migration is otherwise necessary.
- A native game release of 1.9.3 becomes `ParkourVersion.running() == V1_9_3`; existing selectable/canonicalization logic treats the native version and any equal-or-newer target as `CURRENT`, so it remains unmodified native behavior and is not offered as a historical selection on that game.

## Server and handshake impact

The server config, force payload, client acknowledgement, ViaVersion mapping, and server acknowledgement all carry or compare string IDs. `ForceParkourVersionPayload` and `ParkourHandshakeAckPayload` use `STRING_UTF8`; their packet shape and payload IDs need no format change. A server selecting V1_9 continues sending/expecting `1.9`; a server selecting V1_9_3 sends/expects `1.9.3`. The current updated client parser applies the exact profile and echoes its canonical ID; server acknowledgement remains exact string equality.

`ViaVersionLookup` iterates enum patch strings, so it will map protocol releases 1.9/1.9.1/1.9.2 to V1_9 and 1.9.3/1.9.4 to V1_9_3. The no-mod exact-version admission check compares these enum values. Consequently, a no-mod client at 1.9.3 no longer counts as matching a server explicitly configured for 1.9; it matches a server profile of 1.9.3. This follows the profile split and is an admission-policy consequence, not a codec change.

Older mod clients are not a compatibility target under repository guidance. If they receive forced ID `1.9.3` and do not recognize it, their current unknown-version fallback is native/`CURRENT`, so their acknowledgement will not equal the server's required `1.9.3` and the server rejects the mismatch. No protocol-versioning layer is needed for the current mod protocol.

## Product files/areas to account for if implemented

- `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java`: split patch lists, new chronological entry, updated grouping/API documentation.
- `src/main/java/me/wolfii/legacyparkourcompat/api/MovementController.java` and `src/main/java/me/wolfii/legacyparkourcompat/mechanic/MovementChange.java`: profile-group/annotation documentation that currently says 1.9 covers through 1.9.4.
- Existing provider annotations listed above and, as evidence determines, new `change/v1_9_3/` providers for the distinct boundary or continued native fences. Keep each operation's independently resolved version in the annotation.
- Any exact V1_9 predicates in the product provider (currently `FallFlyingSavedState`) and any future exact-version gates introduced for the split.
- `src/client/java/.../config/MovementVersionScreen.java` / `config/version/MovementVersions.java`: no mechanism change appears needed; check displayed labels and exact string input after enum update.
- `src/server/java/.../config/ServerMovementConfig.java` and `src/main/java/.../network/ViaVersionLookup.java`, `ParkourHandshake.java`, `ServerMovementPolicy.java`, payload records, and client handshake receiver: no serialized-format change appears needed; confirm intended exact-ID effects and keep packet strings consistent.

## Compatibility decision summary

1. Preserve existing canonical profile ID `1.9` and empty/native default.
2. Make exact release IDs `1.9.3` and `1.9.4` resolve to the new profile; do not retain their old grouped alias semantics.
3. Preserve string-based config/API/wire representation and packet codec shape.
4. Accept enum ordinal shifts as chronological API behavior, not serialized IDs; do not reorder earlier enum members.
5. Do not change resolver or discovery. Annotate/re-home or add changes per operation so that every existing boundary still has an eligible implementation/fence, and model verified 1.9.3 differences at V1_9_3.
6. Leave client default/session behavior and server blank/default-to-vanilla behavior unchanged.
