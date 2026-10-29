# 1.21.10–26.2 client player-chunk readiness closure

## Result

All six requested exact source endpoints are present and marked ready: 1.21.10, 1.21.11, 26.1, 26.1.1, 26.1.2, and 26.2. The 1.21 releases use Mojmap roots; the 26.x releases use published unobfuscated names. In each release, the ready marker's version ID and resolved metadata ID (where present) match the requested release. I did not substitute an alias or infer an unavailable release.

The readiness query moves through three source forms:

1. **1.21.10:** the tracker synchronously polls the local player's block position and accepts its render section when its mesh is compiled.
2. **1.21.11 through 26.1.2:** the tracker still synchronously polls the local player's block position, now requiring that section's mesh to be compiled and its evaluated visibility to be at least `0.3F`.
3. **26.2:** the tracker no longer queries a render section itself. While waiting, it exposes a callback that sets an atomic ready flag. The renderer obtains that callback and runs it only when the *camera render-state block position's* section passes `isSectionCompiledAndVisible`. The tracker later consumes the flag. Separately, its predicate checks the player's block Y and the current main camera's block Y for build-height bypasses.

**Precise answer to the camera-state question:** the 26.2 tracker `isReady()` does not read `cameraRenderState.blockPos` directly. End-to-end player-chunk readiness is nevertheless conditioned on that coordinate in the callback producer: `LevelRenderer.render` checks `isSectionCompiledAndVisible(levelRenderState.cameraRenderState.blockPos)` before running the player's compiled-section callback. This is distinct from the tracker’s separate `mainCamera().blockPosition()` build-height check and from the `LocalPlayer.tick()` simulation gate.

The comparison therefore has a camera-versus-player query-boundary change by 26.2. In all earlier endpoints, the section lookup is keyed directly by `player.blockPosition()`. In 26.2, the section lookup that releases the callback is keyed by the extracted camera block position; if that position maps to a different render section, it is that camera section's compiled/visible state that controls callback production. This establishes a client source-path difference only; it does not establish a particular runtime camera position, callback schedule, packet arrival, or movement trajectory.

## Exact source identities and integrity

Canonical read-only roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\<mapping-root>`. For every endpoint, I recomputed the source-manifest SHA-256 and confirmed it equals the value in the corresponding ready marker. The exact cited Java files were also rehashed and each digest matched its own source-manifest row. Mapping family, exact release ID, ready status, and provenance sidecar were checked before tracing methods.

| Release | Mapping root | Ready source files | Source-manifest SHA-256 | Client SHA-256 in ready marker |
|---|---|---:|---|---|
| 1.21.10 | Mojmap | 6,386 | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` |
| 1.21.11 | Mojmap | 6,622 | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| 26.1 | Published unobfuscated | 6,882 | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` | `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` |
| 26.1.1 | Published unobfuscated | 6,882 | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| 26.1.2 | Published unobfuscated | 6,882 | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |
| 26.2 | Published unobfuscated | 7,055 | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | Not declared in ready marker |

The 1.21.10/1.21.11 provenance sidecars identify the exact Mojmap batch `1.21.8,1.21.10,1.21.11` (Vineflower 1.12.0; Java 25.0.3+9-LTS). The 26.1/26.1.1 sidecars identify the exact unobfuscated batch `26.1,26.1.1`, with each endpoint's requested and resolved metadata IDs matching. The 26.1.2 and 26.2 sidecars each identify a single-release unobfuscated run. The 26.2 source tree is ready and its source-manifest digest and every cited file digest are verified; its ready/provenance sidecars do not declare a client artifact SHA-256, so this closure binds claims to the ready source tree and manifest rather than asserting an artifact-payload hash.

### Cited source file digests

Each listed digest was recomputed from the canonical file and matched the exact path row in that endpoint's `*.sources.sha256` manifest.

| Release(s) | `LevelLoadTracker.java` | `ClientPacketListener.java` | `LocalPlayer.java` | `LevelRenderer.java` |
|---|---|---|---|---|
| 1.21.10 | `876841eef72f74decfbb52ca7de27d95604d9474ac2e65ef07a0bcda0e4fec13` | `1bb341ee18704d882bb68bf917190be1045649026a99545057118b5cb9bfb348` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `15588458bdffd99d8c83d6eb6c066cf3f75a5f8cef73cf41bcfa23a63b13d786` |
| 1.21.11 | `da4ee92efe3b9e6cbfe312248346e6a2e159acec884cab0a3b84d7483ffb6dc5` | `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `0ea18aee920604448efb6ccbac101512714da0b4caec3cf59f8c88ca6df21b6a` |
| 26.1, 26.1.1, 26.1.2 | `141426abb6fc196ea351715f5c85a481475f340eeb377e68c1a3a48a64f8ad03` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `1c8bed603b390682a9ef98378ef5e9c2bb39ac2522fbde1cabf447dee33ed1e6` |
| 26.2 | `f08f5d126298ce522d0ca485f89ee6847c4fa986006ffa6d848cf734fbdc3e45` | `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` | `f6df2068842b44764d1598eb9c22dc336299422aad3e5256825e4a336bea1651` |

Additional 26.2 source digests (each also manifest-matched): `LevelExtractor.java` `6d7773878a44555e8dde9acc7cb33df05331ff27187e6d97e6729b84975a8a26`; `Camera.java` `c874a48edb69ce9b93c77035126815232e7ca7184fbf0fb1e2b5b8baf7ee3024`; `GameRenderer.java` `cf97b3ff92bc0ddf3e03b4820322d58b24586852c145eb3e66ad214849753ac0`; `ClientLevel.java` `f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631`; `MultiPlayerGameMode.java` `35bbed576b007d610e1892c95dd6fc1ffaa7f216535709cc07bd422c2e039566`; `Connection.java` `c81e56aee5d903ce8a3dfa85bb76a713db298f312283b4d5f2db24dbe9bbf6e7`; `LevelRenderState.java` `e9763fa9e046f6e41bec4fe6769baa1bbe89b4eaa1014e5281397577c396371d`.

## Readiness state and predicate by endpoint

### 1.21.10

`LevelLoadTracker.startClientLoad` stores `(LocalPlayer, ClientLevel, LevelRenderer)` and a 30-second deadline (`LevelLoadTracker.java:43–45`). The client load-start event transitions `WaitingForServer` to `WaitingForPlayerChunk` through `loadingPacketsReceived` (`ClientPacketListener.java:1533–1549`; tracker state transition at `LevelLoadTracker.java:131–135`). `tickClientLoad` polls that state (`:47–55`). Its `isReady` first accepts at `Util.getMillis() > timeoutAfter`; otherwise it reads `player.blockPosition()` and, for a player within build height who is alive and not a spectator, calls `levelRenderer.isSectionCompiled(playerPos)` (`:112–127`). That renderer method maps the passed block position to a render section and accepts a non-null section whose mesh is not `UNCOMPILED` (`LevelRenderer.java:1283–1286`). This is the player's current section and does not require the visibility threshold.

When the tracker reports level-ready, `ClientPacketListener.tick` calls `notifyPlayerLoaded()` and clears the tracker (`ClientPacketListener.java:2637–2643`). The notification sends `ServerboundPlayerLoadedPacket` before setting the local player loaded flag (`:2646–2650`). The local simulation consumer is separately visible in `LocalPlayer.tick`: it decrements its load timeout and only enters `super.tick()`, input packet handling, and movement packet production inside `hasClientLoaded()` (`LocalPlayer.java:195–219`). The prior lifecycle closure establishes that version's `Player.hasClientLoaded()` is the loaded flag or its 60-tick timeout.

### 1.21.11

The tracker still polls `player.blockPosition()`, but the ordinary alive, in-height, non-spectator branch now calls `isSectionCompiledAndVisible(playerPos)` (`LevelLoadTracker.java:108–124`). The renderer requires a present section, a compiled mesh, and `getVisibility(Util.getMillis()) >= 0.3F` (`LevelRenderer.java:1345–1348`; threshold declaration at `:127`). Timeout and unconditional outside-build-height/dead/spectator branches remain in the tracker. The listener callback sequence remains tracker tick → readiness query → local notification → tracker clear (`ClientPacketListener.java:2652–2665`). `LocalPlayer.tick()` now enters its body only when `connection.hasClientLoaded()` (`LocalPlayer.java:211–234`); it has no 1.21.10 Player-side fallback in this gate, as confirmed in the permitted lifecycle closure.

### 26.1, 26.1.1, and 26.1.2

Each exact endpoint's source manifest lists the same digests for `LevelLoadTracker`, `ClientPacketListener`, `LocalPlayer`, and `LevelRenderer`; I verified those file bytes against all three manifests. Their client-side ordinary readiness predicate is therefore source-identical in those inspected files: `WaitingForPlayerChunk.isReady()` reads `player.blockPosition()` and calls `isSectionCompiledAndVisible(playerPos)` when not timed out, out of build height, spectator, or dead (`LevelLoadTracker.java:108–125`). The renderer predicate requires a present render section, compiled mesh, and visibility `>= 0.3F` (`LevelRenderer.java:1458–1463`). Listener readiness tick and notification ordering matches 1.21.11's form (`ClientPacketListener.java:2792–2809`), and local tick is guarded by `connection.hasClientLoaded()` (`LocalPlayer.java:227–251`). No `getPlayerCompiledSectionCallback` API exists in these checked client roots.

### 26.2

`startClientLoad` now takes only player and level (`LevelLoadTracker.java:41–43`). After `loadingPacketsReceived`, `WaitingForServer` creates `WaitingForPlayerChunk` with a new `AtomicBoolean` readiness flag (`:139–143`). The tracker exposes `getPlayerCompiledSectionCallback()` only while the tracker is waiting for the player chunk; the returned `Runnable` sets that wait state's atomic flag (`:96–100`). `ClientPacketListener.getPlayerCompiledSectionCallback()` passes this through while its tracker exists (`ClientPacketListener.java:2808–2809`).

The 26.2 `WaitingForPlayerChunk.isReady()` body (`LevelLoadTracker.java:115–137`) has three paths:

- After the same 30-second deadline, it accepts readiness.
- Otherwise it reads `player.blockPosition()` and `Minecraft.getInstance().gameRenderer.mainCamera().blockPosition()`. If either Y is outside build height, or the player is a spectator or dead, it accepts without waiting for the atomic flag.
- For an alive, non-spectator player with both positions inside build height, it returns `playerSectionReady.get()`.

There is no render-section lookup in this tracker predicate. The callback's producer is in the client render pipeline: `GameRenderer.extract` invokes camera and level render-state extraction (`GameRenderer.java:397–408`); `extractCamera` copies `mainCamera` into `CameraRenderState` (`:646–658`); `Camera.extractRenderState` stores `cameraState.blockPos = this.blockPosition()` (`Camera.java:115–128`). `LevelExtractor.extract` obtains the public client-listener callback and stores it in `LevelRenderState.playerCompiledSectionCallback` (`LevelExtractor.java:207–210`; field at `renderer/state/level/LevelRenderState.java:25`). During `LevelRenderer.render`, after section compilation/upload and occlusion update, the renderer runs it only under this gate (`LevelRenderer.java:251–273`):

```java
if (playerCompiledSectionCallback != null
    && this.isSectionCompiledAndVisible(this.levelRenderState.cameraRenderState.blockPos)) {
    playerCompiledSectionCallback.run();
}
```

`isSectionCompiledAndVisible` maps that `BlockPos` to the view-area render section and returns true only for an existing section with a compiled mesh and time-evaluated visibility `>= 0.3F` (`LevelRenderer.java:898–907`). Thus the camera render-state position is the input to the section predicate that invokes the player-section callback; the player block position is not the callback predicate input in this endpoint. The tracker uses a separately obtained main-camera block position only for a build-height bypass (`LevelLoadTracker.java:127–134`).

## Client tick and callback caller chain

The readiness flag and the local simulation gate are separate consumers of client state:

- The client-side connection tick route is `Minecraft.tick()` → `MultiPlayerGameMode.tick()` (`Minecraft.java:1780–1784`) → `Connection.tick()` (`MultiPlayerGameMode.java:302–309`) → `TickablePacketListener.tick()` (`Connection.java:346–350`). `ClientPacketListener.tick()` implements that listener path and advances the tracker before calling `notifyPlayerLoaded()` (`ClientPacketListener.java:2772–2809`).
- The local player route is `Minecraft.tick()` → `ClientLevel.tickEntities()` (`Minecraft.java:1804–1811`) → `tickNonPassenger(entity)` → `entity.tick()` (`ClientLevel.java:450–476`) → `LocalPlayer.tick()`. The local player's body is gated by `connection.hasClientLoaded()` (`LocalPlayer.java:227–251`). The same gate family begins at 1.21.11; 1.21.10 also decrements its local timeout before checking its flag.
- The section callback route is render-state extraction (`GameRenderer` → `Camera`/`LevelExtractor`) → `LevelRenderer.render()` → camera-section visibility predicate → callback sets the wait state's atomic flag. A subsequent `ClientPacketListener.tick()` can observe that flag through `tickClientLoad()` and move the tracker to `ClientLevelReady`; `isLevelReady()` then gates the listener notification. This describes source call relationships only and does not assert frame/tick scheduling or a runtime callback ordering beyond the listed method bodies.

Client-reachable adapter inputs in 26.2 are the public `ClientPacketListener.getPlayerCompiledSectionCallback()` (`ClientPacketListener.java:2808–2809`), the local player and its `blockPosition()` available from `Minecraft.player`, and the camera's `blockPosition()`/extracted `CameraRenderState.blockPos` (`Camera.java:115–128`). The renderer's `isSectionCompiledAndVisible(BlockPos)` accepts the section-query coordinate explicitly. This identifies available client source inputs without asserting how an external adapter is scheduled.

## Bounded conclusion

The verified predicate boundary is 1.21.10 compiled-only/player-position → 1.21.11 through 26.1.2 compiled-and-visible/player-position → 26.2 render callback gated by compiled-and-visible/camera-render-state-position, with the tracker consuming a readiness flag and applying its own player/camera build-height and player-state bypasses. The 26.2 callback is a render-pipeline producer for a client tick readiness state; it does not make `LocalPlayer.tick()` call renderer code directly. The `LocalPlayer.tick()` gate remains the client-loaded connection state in 26.2.

This closure does not assess rendered output, server packet handling, runtime packet timing, release-specific movement arithmetic, intermediate versions outside the named endpoints, or whether player and camera positions differ in a particular run. No source generation, product/history/wiki work, builds, tests, launches, or Git operations were performed.
