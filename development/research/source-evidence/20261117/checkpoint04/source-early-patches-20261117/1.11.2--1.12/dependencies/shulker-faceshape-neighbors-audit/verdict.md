# Independent audit: Shulker FaceShape effects on adjacent fences and walls — 1.11.2 → 1.12

Verdict: **ACCEPT** for the bounded no-impact conclusion that the 1.12 Shulker FaceShape provider does not change adjacent FenceBlock or WallBlock connection state, and therefore does not change their collision boxes through a Shulker neighbor, compared with 1.11.2. This makes no claim that all fence/wall collision geometry is unchanged, and does not freeze the pair.

## Bound evidence

Audited claim: `dependencies/shulker-faceshape-neighbors.md`, SHA-256 `30BBBC78B5187882ED9A440BE894C4A1B2A091698AEABBE4CDD49834051BAA43`.

The supporting immutable inputs match their declared hashes:

- Shulker provider reachability report: `dependencies/shulker-provider-reachability.md`, SHA-256 `FD714797984F6C590871100B85171B25A7A6BD8965AB87E4415851DC93462E44`; its independent verdict: `dependencies/shulker-provider-reachability-audit/verdict.md`, SHA-256 `AD157596DD942123E1DB378A5CC9AD3D67F91C57E8E8705439B418E09F215CA0`.
- Corrected connected-shape crosswalk r2: `dependencies/connected-shape-state-crosswalk-r2.md`, SHA-256 `EDA620F3A69BF7115B45DEC4328CC48E2E23A720595D66E34B0AE83EAA3694DE`; its independent verdict: `dependencies/connected-shape-state-crosswalk-audit-r2/verdict.md`, SHA-256 `87DA39170D47770FF91890486A11B8713493F9065297A2A57E691A0176C69B65`.

I independently checked the exact `decompiled_minecraft/<version>/ornithe-feather` files. Their SHA-256 values match the source identities in the bound reports: 1.11.2/1.12 `ShulkerBoxBlock.java` `C3A4082E6DFF9B59C61C0EA1FD79FCC6B349C85D4ABF47398E414C1CC52D17F5` / `AF7C152C3752223234EF406485BB667346D3511143B77F3384847F95AC81C076`; 1.11.2/1.12 `FenceBlock.java` `856CB92D4613289747A34A6BC85F306CA9D44B7057A45C79F2020D13025D1CF7` / `633AD3B20250BD010A28D0B1362B24C81A5508BE8A81A9D826953A3C9C4A703E`; 1.11.2/1.12 `WallBlock.java` `17DA6D881365E4DCE5134F88CB9A9E5273D1FA1734812D13410DC6924A3ACBFD` / `7E4378C46E367C32DECA14475E5161B6E4223947D83153CE3E7BA5C8A0D18E50`; 1.12 `Block.java` `E4A90ECA411E7B0E14F5018F7385EC29891F6CDF1A1E917FA648B5712F9B33A1`; 1.11.2/1.12 `Entity.java` `CE8104A17CE783DF639CF9726E7B1CD0936563EAA7BA308603335BBE05D49440` / `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A`; and 1.11.2/1.12 `World.java` `27CFAA5FF45C2D88C492FC5DAE3EA4A2BB4536FD64BFF849A9A7D0CB82EFB58` / `F7AB7A34D456E720E7EE4F34FBD0EB10F7F96151596C8C615AB2EDEF05A884D8`.

## Independent trace

**Older neighbor predicate.** In 1.11.2 `ShulkerBoxBlock.isCube(BlockState)` returns `false`. The ordinary solid-neighbor arms in `FenceBlock.shouldConnectTo` and `WallBlock.shouldConnectTo` require both solid-blocking material and `blockState.isCube()`. The other accepted cases are fence/material or fence-gate for fences, and the same wall or fence-gate for walls. A Shulker box satisfies none of those alternatives, so it leaves all four horizontal connection properties false in 1.11.2.

**New provider and exception gates.** 1.12 adds `ShulkerBoxBlock.getFaceShape`; 1.11.2 has no such method. Its exact return expression yields `SOLID` for every `Direction` while the animation stage is `CLOSED`; `UNDEFINED` for every direction during `OPENING` or `CLOSING`; and, while `OPENED`, `SOLID` only when the query face equals `FACING` or its opposite, otherwise `UNDEFINED`. This covers all six directions, with the opened state solid on the facing axis pair and undefined on the other four faces.

Both 1.12 neighbor resolvers query the adjacent block's face directed toward the fence/wall: north neighbor → `SOUTH`, east → `WEST`, south → `NORTH`, west → `EAST`. For the `SOLID` connection arm, FenceBlock and WallBlock reject `Block.isExceptionForAttachment(block)`. That helper calls `isExceptionForPlacement`, whose exact list includes every `ShulkerBoxBlock`. A Shulker therefore cannot connect through its `SOLID` faces. The remaining fence arm requires `MIDDLE_POLE` (and matching material or a fence gate); the remaining wall arm requires `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` with a fence gate. Shulker FaceShape returns neither pole shape. Consequently all four horizontal neighbor cases remain disconnected in every animation stage and facing configuration. The two vertical FaceShape values do not enter these four horizontal connection resolvers.

**Player collision route.** In both endpoints, ordinary `Entity.move` gathers swept collision boxes through `World.getCollisions`; that method invokes each visited state's `addCollisions` with `forceShape=false`. FenceBlock and WallBlock then resolve their virtual connection properties before emitting collision boxes. Thus the no-connection result above is used by the normal player movement collision query. The Shulker provider does not change the fence/wall connection state feeding those boxes.

## Conclusion

The bounded no-impact claim is source-supported. A 1.12 Shulker can report directional `SOLID`, but the fence/wall solid-face exception gate rejects it; in 1.11.2 the cube predicate already rejects it. No Shulker-induced adjacent fence/wall connection or collision-box change is established. This does not assert that fence/wall collision boxes are generally identical across releases, audit unrelated neighbors or providers, or close full-pair coverage.