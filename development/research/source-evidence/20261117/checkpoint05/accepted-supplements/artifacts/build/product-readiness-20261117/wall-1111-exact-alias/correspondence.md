# Wall height effective-provider correspondence

Result: `COVERED_NO_CODE`.

Scope: literal `1.11.1` profile routing for the accepted MCPK wall-height bug, and its `1.11.2` restored-height boundary. This is product correspondence only; no source rediscovery, code review, build, test, or runtime work was performed.

## Profile and resolver correspondence

- `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java` maps the literal patch `1.11.1` only to `V1_11_1("1.11.1")`; there is no multi-patch alias on that constant. Adjacent mappings are `V1_11("1.11")`, `V1_11_2("1.11.2")`, then `V1_12("1.12", "1.12.1", "1.12.2")`. `ParkourVersion.of` uses the exact `BY_PATCH` mapping before falling back to `CURRENT`. Enum order places these four constants consecutively in that order.
- For mechanic key `block.wall.collision-height`, exhaustive concrete implementors are `change/v1_11/WallCollisionHeight.java` (`V1_11`), `change/v1_11_1/WallCollisionHeight.java` (`V1_11_1`) and `change/v1_12/WallCollisionHeight.java` (`V1_12`). There is no `V1_11_2` provider. `ChangeResolver` filters each hook's registrations to `emulates >= selected`, then chooses the minimum enum ordinal. Thus literal `1.11.1` selects the `V1_11_1` provider; literal `1.11.2` skips both earlier providers and selects `V1_12`, whose provider supplies the restored 1.5 height. `V1_11` itself resolves to its same-version 1.5 provider.
- Client `MovementChangeDiscovery` discovers annotated classes, registers every implemented `@MechanicType` interface at the annotation version, and invokes the `V1_11_1` provider's local factory for the two wall block variants. No profile alias or package-name inference changes the selected hook version.

## Dispatch and source-backed behavior correspondence

`WallBlockMixin` obtains the local-player profile through `MovementRuntime.find(WallCollisionHeightHook.class, collisionState.getBlock(), player)`, applies the selected hook to the collision shape and passes its max-Y to the wall shape hook. This dispatch is player-context based and the historical hook is bypassed when the collision context has no player. For the selected `V1_11_1` wall provider, block state determines maxY: connection masks 5 or 10 produce `0.875`; all other masks produce `1.0`. The accepted exact-pair evidence says Feather 1.11.1 wall collision collection uses state-indexed `SHAPES` (all maxY 1.0 except entries 5 and 10 at 0.875), while 1.11.2 uses `COLLISION_SHAPES` with maxY 1.5. The later `V1_12` provider also returns 1.5, matching the fixed side of this boundary.

## Evidence identities

- Accepted candidate: `development/research/source-evidence/20261117/checkpoint04/source-early-patches-20261117/1.11.1--1.11.2/findings/wall-collision-height.md` — SHA-256 `0485037e506b5681fe0d92d9006738dd1add36ab89154fc6e1c9b1bdea992718`.
- Bounded acceptance: `development/research/source-evidence/20261117/checkpoint04/source-early-patches-20261117/1.11.1--1.11.2/audit/verdict.md` — SHA-256 `9a9d9ff163f72eabcfc1e09869bcc2789423350bb21842d80c66b75422234de7`.
- Product findings index entry: `development/research/findings/1.12.2--1.13.2/wall-single-arm-gaps.md` includes `WallCollisionHeight @V1_11_1` in its integrated Changes list; no separate literal-profile alias note is needed because `ParkourVersion` supplies the exact alias and resolver correspondence above.

The product routing already covers the accepted `1.11.1` behavior and the `1.11.2` restoration. No code change is indicated.