# Bounded audit: portal client packet-response follow-up

Verdict: **ACCEPT** — the packet-application and server-transfer claims in the assigned follow-up are supported by the paired exact sources. This accepts only this bounded response path; it does not establish complete pair coverage or pair freeze.

## Audited inputs

- Follow-up report: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/portal-client-response-followup.md`
- Follow-up SHA-256: `0347619441b7237d4483cfa987ffdec55726c4721f3926545300a2c57b9fe482`
- Reused accepted portal-gate correction: `dependencies/piston-portal-player-response-r2.md`, SHA-256 `84f5160938a961bcd3de8bab9f066b9909ec1162bf4055de37ca6f53224d920b`
- Reused bounded gate verdict: `dependencies/piston-portal-audit-r2/verdict.md`, SHA-256 `127b73971637681565c1ce2d9a3be8ce8c6b70fdee7db3053cbc20a0812ad175`
- Full-pair audit context: `audit/full-pair-verdict-r2.md`, SHA-256 `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`; it remains PARTIAL and explicitly leaves packet application open.
- Exact source roots: `decompiled_minecraft/1.11.2/ornithe-feather/` and `decompiled_minecraft/1.12/ornithe-feather/`, both `ornithe-feather` mapping family. Verified 1.11.2 source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, ready marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`, and 1.12 mapping-local provenance SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

## Audit disposition

- The already accepted `Entity.onPortalCollision` wording is retained: `inPortal` can be set on the logical client, while portal position/offset/facing capture is server-gated. The distinct portal timer and dimension-transfer branch in `Entity.tick` is guarded in both endpoints by `!world.isClient && world instanceof ServerWorld`; the paired bodies increment `portalTime`, select the destination and call `changeDimension` only inside this gate. This audit does not reopen the gate correction.
- `PlayerManager.changeDimension(ServerPlayerEntity, int)` sends `PlayerRespawnS2CPacket`, performs the server-side world and dimension position transition, then calls `ServerPlayNetworkHandler.teleport` to emit the position correction. Its paired method body is identical across endpoints. The handler’s paired `teleport(..., relativeArgs)` body is likewise identical: it computes the requested position, updates the server player and sends `PlayerMoveS2CPacket`.
- The client’s paired `ClientPlayNetworkHandler.handlePlayerRespawn` and `handlePlayerMove` bodies are identical. Respawn updates the client world/dimension as needed and reconstructs the local player via `Minecraft.respawnPlayer`; that path resets the new player’s position. The position receiver resolves relative coordinates, clears velocity on non-relative axes, updates local-player position/angles and acknowledges the teleport. In 1.12, `respawnPlayer` adds recipe-book construction while retaining the same movement setup (`dimension`, `resetPos`, world insertion and player initialization).
- `ServerPlayerEntity.changeDimension` adds `enteredNetherPos = new Vec3d(this.x, this.y, this.z)` in 1.12 when entering the nether from the overworld. This is a snapshot written in the server player transfer path; no paired client position, velocity or movement-state write is introduced by it. Its placement therefore does not change the client packet-application conclusion.

The follow-up accurately closes the previously open portal-to-client-packet-application response slice: the receiver can write local-player position/velocity, but its relevant paired behavior is unchanged. Other client lifecycle/event paths, the other open dependencies in the full-pair verdict, intermediate version boundaries and runtime parity remain outside this verdict.
