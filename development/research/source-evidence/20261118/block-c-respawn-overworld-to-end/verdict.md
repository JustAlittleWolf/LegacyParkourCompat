# Independent source applicability verdict: respawn transfer

Status: COMPLETED

## Binding and scope

Audited source report: `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5064-75c0-a594-b2e7fa3b99e2/c-external-respawn-transfer-applicability/report.md`, SHA-256 `BB45C4CE0D66510D1822D761D1F8F785C2D4FB15FFCF0CC8570DEF3476B6023E` (verified before this audit). This verdict covers only ordinary Overworld-to-End route connectivity in the report's sampled source families and 26.2 respawn-packet emission/observability. It does not repeat the historical local client handler range audit or establish runtime parity.

## Route applicability

The sampled sources support the report's route claim: an ordinary End portal changes the server player to the End route and reaches that release's respawn packet before the position correction/placement completes. The evidence groups are:

- 1.8 and 1.12.2, Ornithe/Feather: `EndPortalBlock.onEntityCollision` calls `changeDimension(1)` (`decompiled_minecraft/1.8/ornithe-feather/net/minecraft/block/EndPortalBlock.java:63`; 1.12.2 equivalent `:69`). The player dimension path enters `PlayerManager.changeDimension`, sends `PlayerRespawnS2CPacket`, then calls network teleport (`PlayerManager.java:386-401` in 1.8; `:487-503` in 1.12.2).
- 1.14.4, Mojmap: `EndPortalBlock` chooses THE_END for the Overworld case and calls `changeDimension` (`.../EndPortalBlock.java:47`); ordinary `ServerPlayer.changeDimension` sends `ClientboundRespawnPacket` (`.../ServerPlayer.java:585-604`) before placement/teleport. The credits-return special case is a separate branch.
- 1.16.5 and 1.18.2, Mojmap: EndPortalBlock calls the dimension-change path (`.../EndPortalBlock.java:56` and `:52` respectively); ServerPlayer sends `ClientboundRespawnPacket` in that transfer (`.../ServerPlayer.java:642-660` and `:706-724`).
- 1.20.6 and 1.21.1, Mojmap: portal transition reaches the cross-dimension ServerPlayer path, which sends the packet with keep mask 3 before placement (`.../ServerPlayer.java:788-804` and `:840-861`). 1.21.2's report-cited teleport path is consistent with this body (`.../ServerPlayer.java:1029-1069`).

This establishes ordinary transfer connectivity at the sampled bodies, not every intermediate patch or every possible transfer cause. The supplied historical receiver audit remains the evidence for the historical local reset result.

### 1.8 provenance correction

The report understates 1.8 provenance availability: the source tree has a nested `decompiled_minecraft/1.8/ornithe-feather/.decompile-provenance.json` (SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`). Its inputs identify Feather 1.8 and the `feather-gen2/1.8+build.2` mapping artifact. The sidecar's source hashes match the current files exactly: `EndPortalBlock.java` `d5887c633e4154e5e1c1a726ac5793b6b79e980f2f756d07adafbc4ae010667c`; `PlayerManager.java` `55431db3599f902d5f93f497f64d5767fee24e4ee6bbc70c0a2e7dd05882c0e8`. Thus 1.8 is a provenance-backed source witness; it is not merely an unidentified body-shape sample. This correction does not alter the route conclusion.

## 26.2 complete packet send-site and reason audit

Source identity is the `26.2` / `unobfuscated` ready source set: `unobfuscated.ready.json` names version 26.2 and manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. A whole-tree search finds exactly two `new ClientboundRespawnPacket(...)` send sites:

1. `net/minecraft/server/level/ServerPlayer.java:1121`, in `teleport(TeleportTransition)`. After a same-dimension early return, this path sends mask `(byte)3` for a cross-dimension transfer. That mask includes `KEEP_ENTITY_DATA = 2` (and `KEEP_ATTRIBUTE_MODIFIERS = 1`). Ordinary End portal processing in `EndPortalBlock.getPortalDestination` selects `Level.END` from a non-End source (`EndPortalBlock.java:79-91`); `Entity` processes the portal transition and invokes `teleport` (`Entity.java:2620-2632`), dispatching to the `ServerPlayer` transfer implementation (`Entity.java:3122`).
2. `net/minecraft/server/players/PlayerList.java:420`, in `respawn(...)`, sends `dataToKeep = (byte)(keepAllPlayerData ? 1 : 0)`. The caller at `ServerGamePacketListenerImpl.java:1895-1906` supplies `true` for End-credits completion (`wonGame`, `CHANGED_DIMENSION`) and `false` for ordinary death (`KILLED`, after the health guard). Thus these masks are 1 and 0 respectively; neither sets bit 2. In particular, the server-side `keepAllPlayerData` name does not mean this client packet keeps entity data or velocity: the End-credits replacement keeps the attribute-modifier bit only.

`ClientboundRespawnPacket.java:13,34-35` defines bit 2 and tests it with `(dataToKeep & mask) != 0`. In `ClientPacketListener.handleRespawn` (`:1251-1321`), the client reads the destination `dimensionKey` from common spawn info and the old key from `oldPlayer.level().dimension()` before replacing the player. It evaluates the bit-2 predicate when creating the replacement and again when copying old entity data, delta movement, and rotation (`:1285-1315`). Therefore the client can observe the exact old/new dimension-key pair and whether entity data is kept. It is not sent a reason/portal-source enum that distinguishes an End portal from a command, custom transition, or another server authority that yields the same dimension pair.

## Applicability verdict and limits

The route can be selected narrowly by the observable key pair `Level.OVERWORLD` → `Level.END`, optionally requiring bit 2 for the modern velocity-copy case. This is a dimension-pair constraint, not proof that the cause was specifically a physical End portal: other server-authorized transitions producing that same pair are indistinguishable in this packet context. That limitation is explicit and does not invalidate using the pair as a bounded older-profile applicability condition.

The only 26.2 send site setting `KEEP_ENTITY_DATA` is the cross-dimension `ServerPlayer.teleport` path (mask 3). End-credits respawn (mask 1) and death respawn (mask 0) do not trigger the native bit-2 delta copy, so they need no velocity-copy undo/fallback. If a policy is intended to emulate the historical replacement reset for every respawn, its applicability must be expressed independently of bit 2; do not interpret bit 1 or server `keepAllPlayerData` as a velocity-preservation signal. The pre-existing local-handler audit is required for the historical reset claim itself.

