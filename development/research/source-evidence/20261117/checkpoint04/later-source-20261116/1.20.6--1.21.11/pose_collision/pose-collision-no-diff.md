# Bounded source coverage: local-player pose and collision fit

## Verdict

**NO_DIFF for the assigned ordinary standing/crouching and ceiling-forced-pose slice.** In both endpoint releases, `Player.updatePlayerPose` first requires a fit for `Pose.SWIMMING`; then it selects the desired pose if spectator/passenger or the desired box fits, otherwise chooses crouching if that box fits and swimming as the final fallback. The desired standing/crouching decision uses `isShiftKeyDown() && !abilities.flying`. The order, gates, collision epsilon, and fallback are equivalent for ordinary standing/crouching states.

The ordinary local-player crouching flag also uses the same expression and checks in both `LocalPlayer.aiStep` implementations: not flying, swimming, or passenger; crouching box fits; and shift held or standing box does not fit (with sleeping excluded). `isShiftKeyDown()` reads the input shift field in each version. The keyboard input refreshes that field from the shift key during `aiStep`, before `Player.tick` calls `updatePlayerPose`; the field/API changed from mutable `shiftKeyDown` to `keyPresses.shift()`, but this source path preserves the key value used by the pose decision.

Player standing/crouching dimensions are unchanged through the refactor from `Player` to `Avatar`: standing is scalable `0.6 × 1.8`, eye height `1.62`; crouching is scalable `0.6 × 1.5`, eye height `1.27`, with the same vehicle attachment; swimming/fall-flying/spin dimensions are `0.6 × 0.6`, eye height `0.4`. `LivingEntity.getDimensions` still applies sleeping dimensions specially and otherwise scales the selected default dimensions by entity scale.

`Entity.onSyncedDataUpdated` still refreshes dimensions on a pose update. `refreshDimensions` still assigns dimensions and eye height then calls `reapplyPosition`; the later position-fudging branch excludes `Player` at both endpoints. The fit query still constructs dimensions at the player's position and deflates the AABB by `1.0E-7`. `CollisionGetter.noCollision` was factored into block, entity, and border helpers in 1.21.11, but uses the same short-circuit order and predicates as the 1.20.6 inline implementation; its no-block default still selects ordinary block collisions (`false` for the added liquid flag).

## Exact evidence and source identity

Both endpoint roots are exact Mojmap decompilations. Each ready marker's source-manifest and artifact-manifest digests matched the corresponding on-disk files; every listed Java file's local SHA-256 matched its source-manifest entry.

- 1.20.6: `decompiled_minecraft/1.20.6/mojmap`; ready `versionId=1.20.6`, `mapping=mojmap`; `mojmap.ready.json` SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`, `mojmap.provenance.json` SHA-256 `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`; source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`.
- 1.21.11: `decompiled_minecraft/1.21.11/mojmap`; ready `versionId=1.21.11`, `mapping=mojmap`; `mojmap.ready.json` SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`, `mojmap.provenance.json` SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.

Relevant exact source files (repository-relative paths; line anchors refer to these bytes):

| Version | File | Lines examined | SHA-256 |
|---|---|---:|---|
| 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/player/Player.java` | 393–425, 1997–1999; tick call at 314 | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` | 352–384; tick call at 290 | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/Avatar.java` | absent; pose dimension table is in `Player.java` 132–148 | not applicable |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/Avatar.java` | 20–36, 64–66 | `a3f54b9ff81ee203f77efcf5dcab50d69ec9a3d2c5430ce4c4b2ae0abb4bfca9` |
| 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java` | 3240–3246 | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` | 3561–3567 | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java` | 188–196, 657–667; shift read at 598–600 | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` | 211–218, 738–747; shift read at 639–641 | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` || 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/client/player/Input.java` | 5–23; shift state at 13 | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` |
| 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/client/player/KeyboardInput.java` | 20–34 | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/ClientInput.java` | 6–31 | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/KeyboardInput.java` | 22–36 | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |
| 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/Entity.java` | 2720–2755 | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` | 3211–3241 | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.20.6 | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/level/CollisionGetter.java` | 38–63 | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/level/CollisionGetter.java` | 39–80 | `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70` |

## Coverage boundary and remaining gates

Examined the ordinary local-player crouching decision, shift-input read/update path, `Player.updatePlayerPose` reachability through player tick, standing/crouching/swimming fit boxes, player dimensions/eye heights, pose-triggered dimension refresh, and no-collision helper semantics. Sleeping, vehicle/passenger eligibility, bed entry, damage/status behavior, and source predicate-only climbing are excluded as assigned. The source inventory beneath collision shape production, block/resource membership, and unrelated movement effects of the modern client input/vector pipeline were not audited here. The changed priority between sleeping and fall-flying in `getDesiredPose` is outside this ordinary pose slice and is not claimed as covered. This result is a bounded slice only: it does not freeze or accept the pair, close other movement areas/dependencies, establish an intermediate version boundary, or make a runtime claim.



