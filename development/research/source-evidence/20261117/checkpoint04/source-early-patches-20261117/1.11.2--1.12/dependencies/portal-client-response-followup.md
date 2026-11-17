# 1.11.2 → 1.12 portal client packet-response follow-up

Status: bounded source-only closure of the portal/client packet-application slice. This does not claim pair freeze.

## Inputs and provenance

The compared endpoints are `decompiled_minecraft/1.11.2/ornithe-feather/` and `decompiled_minecraft/1.12/ornithe-feather/`, both using the `ornithe-feather` mapping family. The source publication identities are those recorded in `piston-portal-player-response-r2.md` (report SHA-256 `84f5160938a961bcd3de8bab9f066b9909ec1162bf4055de37ca6f53224d920b`): 1.11.2 source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, ready marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; 1.12 mapping-local `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. The accepted bounded portal-gate wording is reused from `dependencies/piston-portal-audit-r2/verdict.md`, SHA-256 `127b73971637681565c1ce2d9a3be8ce8c6b70fdee7db3053cbc20a0812ad175`. The full-pair audit identity is `audit/full-pair-verdict-r2.md`, SHA-256 `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`; it leaves packet application and portal/client lifecycle open and explicitly marks the pair PARTIAL.

Endpoint source-file SHA-256 values (paths relative to each `ornithe-feather` source root):

| Source file | 1.11.2 | 1.12 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` |
| `net/minecraft/server/PlayerManager.java` | `5f92907db38432cc50b65efe23d125a56502a95be3edd0e31b361dd56d664163` | `882937cb6794bc34366dd67a0b43f2c8122b54608ed822b5bd67880af503d53d` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf` | `c9c5d46564e2d9b2f66a79f712a5cabcf2976d019675d1c129d1e08bef2e9ac0` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | `633c59b54bdbe79a2430b59215033206c886249b154044ca95965b375ba3f8e6` |
| `net/minecraft/client/Minecraft.java` | `3ca93038adf4cea512c21ae86d653376e963396640a02eb50cb42a67302f6236` | `308ea5b049c7410d03039a12acde604b8f6f184710cbc8ec53240524cb5e795a` |

## Paired event and packet path

The reused portal gate wording remains: `PortalBlock.onEntityCollision` (both endpoints, lines 170–173) calls `Entity.onPortalCollision(pos)` only for an entity that is not riding, has no passengers, and can use portals. As accepted in the r2 audit, `onPortalCollision` (1.11.2 `Entity.java:1676–1697`; 1.12 `:1702–1723`) refreshes `portalCooldown` when it is positive; otherwise its accepted gate sets `inPortal = true` on either logical side. Only portal-position/offset/facing capture has the `!world.isClient` and changed-position checks. No position or velocity is written by this collision response.

`Entity.tick` consumes that flag only inside `!world.isClient && world instanceof ServerWorld` (1.11.2 `Entity.java:341–372`; 1.12 `:344–375`). If nether travel is enabled and the entity is not riding, it increments `portalTime`; at `getMaxNetherPortalTime()` it sets `portalCooldown`, selects the destination dimension and calls `changeDimension`. It then clears `inPortal`. The paired decision body and cooldown tick have the same relevant behavior. Thus the local client's portal-contact flag does not itself transfer or reposition the local player; the server-side player path decides and performs the transfer.

For a `ServerPlayerEntity`, `changeDimension` dispatches to `PlayerManager.changeDimension` (1.11.2 `ServerPlayerEntity.java:483–512`, `PlayerManager.java:468–490`; 1.12 `ServerPlayerEntity.java:544–579`, `PlayerManager.java:487–509`). The player manager sends `PlayerRespawnS2CPacket`, updates the server world and position through its paired dimension positioning routine, then sends a `PlayerMoveS2CPacket` through `ServerPlayNetworkHandler.teleport` (1.11.2 `:498–525`; 1.12 `:527–554`). The packet is a server-authoritative position/angles correction; the producer keeps the same ordering and calculations across these endpoints.

On the client, `ClientPlayNetworkHandler.handlePlayerRespawn` (1.11.2 `:896–914`; 1.12 `:903–921`) updates the dimension/world when needed and calls `Minecraft.respawnPlayer`. `respawnPlayer` reconstructs the `LocalClientPlayerEntity` and calls `resetPos` (1.11.2 `Minecraft.java:1906–1930`; 1.12 `:1965–1992`). The following `handlePlayerMove` applies relative coordinates, zeros velocity components for non-relative axes, calls `updatePositionAndAngles`, and acknowledges the teleport (1.11.2 `ClientPlayNetworkHandler.java:565–606`; 1.12 `:576–617`). This receiver can write the local player's position, angles and velocity, so the packet path is movement-relevant; however, the compared `handlePlayerRespawn` and `handlePlayerMove` bodies have the same movement behavior. `respawnPlayer` adds the 1.12 recipe-book argument when constructing the replacement player; its position reset and movement setup remain the same.

## Difference disposition and remaining gate

The paired server player `changeDimension` body has a 1.12 addition that records `enteredNetherPos = new Vec3d(this.x, this.y, this.z)` for nether-return handling and adjusts end-credit bookkeeping (`ServerPlayerEntity.java:544–579`; the 1.11.2 body is `:483–512`). This is a server-only coordinate snapshot, not a write to the local client's position, velocity, or movement state. The server dimension-transfer and packet-producer bodies have no identified movement-response delta. Client packet application writes position/velocity as described above, but the compared receiver bodies do not differ in that behavior. **No changed body in this traced portal-to-packet path writes eligible local-player position, velocity, or movement state for a fixed client profile.**

This closes only the previously open portal/client packet-application response slice. Other client lifecycle/event routes and the specific open slices listed by the full-pair r2 audit remain unreviewed; this report does not claim complete pair coverage or freeze.
