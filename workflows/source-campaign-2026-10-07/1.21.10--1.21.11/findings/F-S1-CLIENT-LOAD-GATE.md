# F-S1-CLIENT-LOAD-GATE: Local movement ticks wait for load readiness in 1.21.11

- Older version A: 1.21.10
- Newer version B: 1.21.11
- Mechanic / coverage slice IDs: `S1-client-loaded-gate` (parent bucket `S1-local-tick`)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.10, 1.21.11]
- Runtime validation: not performed

## Paired evidence

Sources are from the manifest-verified Mojmap pair: A client jar SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`, source manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; B client jar SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`, source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.

- A `LocalPlayer.tick()V`: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, lines 195-220, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`. At the method entry it calls `tickClientLoadTimeout()` and then gates the rest of the local-player tick on `hasClientLoaded()`. A `Player.hasClientLoaded()Z` and `tickClientLoadTimeout()V`: `world/entity/player/Player.java`, lines 1875-88, SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; the timer is initialized to 60 at fields lines 155-56, decremented while not client-loaded, and `hasClientLoaded` returns true when the timer reaches zero.
- B `LocalPlayer.tick()V`: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, lines 211-35, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. It gates on `connection.hasClientLoaded()` and has no local timeout decrement. B `ClientPacketListener.hasClientLoaded()Z` and `setClientLoaded(Z)V`: `client/multiplayer/ClientPacketListener.java`, lines 2740-45, SHA-256 `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`; `hasClientLoaded` returns only the connection flag. The listener sets it false at join/respawn, lines 509 and 1252.
- A/B listener `tick()` advances the load tracker and sends `ServerboundPlayerLoadedPacket` from `notifyPlayerLoaded()` only when tracker readiness is reported: A `ClientPacketListener.java` lines 2637-51, SHA-256 `1bb341ee18704d882bb68bf917190be1045649026a99545057118b5cb9bfb348`; B lines 2652-66, SHA-256 `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`.
- A/B `LevelLoadTracker.java` hashes: `876841eef72f74decfbb52ca7de27d95604d9474ac2e65ef07a0bcda0e4fec13` / `da4ee92efe3b9e6cbfe312248346e6a2e159acec884cab0a3b84d7483ffb6dc5`. Both use a 30-second client tracker timeout. A's waiting-for-player-chunk state considers the player's section compiled; B requires it compiled and visible; both transition to client-level-ready after the timeout if still not ready.
- `Minecraft.tick()` invokes `MultiPlayerGameMode.tick()` and later ticks level entities if a level exists and the client is unpaused (A/B `Minecraft.java` SHA-256 `ae782d427ff3f2b0a15bd58fe447bc3a07b216faed243a7618420f816516be50` / `e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59`). `MultiPlayerGameMode.tick()` calls the network connection's `tick()` in both versions (A/B `MultiPlayerGameMode.java` SHA-256 `bf6541cc58c82cc167a196cfadce23f258967a4dcc250b9c85dd772eda60a979` / `4d15d5c6b8c280d0e2c6de1b0f41bde83bc0944d400b7f89cabff24679e9a9c0`). The entity tick path reaches a local player either through the nonpassenger tick path or through passenger `rideTick`.

## Source-level difference

A local player decrements a 60-tick fallback timer at the beginning of every entity tick. When that timer expires, `hasClientLoaded()` returns true even if the listener has not sent/received the level-ready notification, so the rest of `LocalPlayer.tick()` runs. B moved the flag to `ClientPacketListener` and `LocalPlayer.tick()` returns early until the listener's load tracker calls `notifyPlayerLoaded()` and sets the flag.

The two load paths are reachable while a client level exists and the client is unpaused: `Minecraft.tick()` ticks entities under those conditions without requiring the loading screen to be absent. During a delayed player-chunk readiness interval, after 60 calls to A's local tick gate but before B marks the client loaded, A runs `super.tick()` and its movement path while B skips those operations. The B load tracker can remain pending until the player section is visible or its 30-second timeout; the A fallback is 60 local-player ticks.

## Reachability and dependencies

`Minecraft.tick()` first advances multiplayer connection processing through `MultiPlayerGameMode.tick()`, then calls `ClientLevel.tickEntities()` when `level != null` and `!pause`. `ClientLevel` ticks a local player through `tickNonPassenger` if it is not a passenger or through `tickPassenger`/`rideTick` if it is. Both paths invoke `LocalPlayer.tick()`, whose A/B loaded gates control the local superclass tick, input packet update and subsequent player movement work. B's readiness flag is set by `ClientPacketListener.notifyPlayerLoaded()` when `LevelLoadTracker.isLevelReady()` returns true. This finding covers that gate's effect on whether local player movement tick work executes; it does not claim server-side movement acceptance or packet-loss behavior.

## Consequence and uncertainty

Source proves that the two versions may run or skip local player movement-tick work under the same delayed-load precondition. It does not prove a particular resulting trajectory, and no runtime validation was performed. The exact release where the gate changed is unknown within the endpoint interval.

## Handoff

Independent source delta: with a level active, unpaused and still not load-ready for over 60 local-player tick calls, A's timeout opens the local tick gate while B's connection flag remains false until load readiness is reported. Related findings: none. Finding-specific independent blind source review is pending; no accepted immutable snapshot or implementation handoff exists.
