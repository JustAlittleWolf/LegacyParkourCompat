# 1.15 to 1.15.1: ability flight over active Elytra glide

## Result

For the assigned client-player slice, the 1.15 and 1.15.1 Mojmap source bodies are byte-identical in the compared files. No 1.15-to-1.15.1 behavior difference is established. The observed behavior is still concrete: when a local player enters `Player.travel` with `abilities.flying == true`, not a passenger, and glide flag 7 already true, its `super.travel` performs the active Elytra glide move first; the ability-flight wrapper then clears glide flag 7 unconditionally and separately resets fall distance. This is a shared behavior, not a patch-boundary finding.

## Reachable setup and ordered path

- Lawful flight availability is represented by `abilities.mayfly`; `LocalPlayer.aiStep` can toggle `abilities.flying` after the jump trigger (or enable it for always-flying mode): `LocalPlayer.java:695-712`. While `abilities.flying` is true, the same method's local Elytra-start path is gated by `!abilities.flying` (`:715-719`), so an already-active Elytra glide here must predate this ability-flight state or otherwise already have flag 7 set.
- On each living-player AI step, `LivingEntity.aiStep` invokes `updateFallFlying`, then virtual `travel(...)` (`LivingEntity.java:2282-2287`). For an already-active flag, ordinary airborne nonpassenger player with a working chest Elytra, `updateFallFlying` keeps the flag on its client side: it checks flag 7, `!onGround`, `!isPassenger()`, chest-slot `ELYTRA`, and `ElytraItem.isFlyEnabled`; client-side code does not rewrite the flag (`LivingEntity.java:2299-2317`). This check does not require `abilities.flying` to be false.
- The `Player.travel` ability-flight branch is selected by `abilities.flying && !isPassenger()` (`Player.java:1471-1483`). It saves incoming vertical velocity and flying speed, applies ability flying speed, and calls `super.travel(vec3)` (`:1472-1475`). In a local player controlled by the local instance and dry of water/lava, `LivingEntity.travel` reaches the `isFallFlying()` branch and runs the Elytra steering math; this branch calls `move(MoverType.SELF, this.getDeltaMovement())` after setting the glide velocity (`LivingEntity.java:1831-1842,1848-1871`).
- The glide branch's own grounded flag-7 clear is explicitly server-only (`LivingEntity.java:1882-1884`), so it does not clear the flag on this local client during that move. On return, `Player.travel` restores vertical velocity to saved `i * 0.6`, restores flying speed, sets `fallDistance = 0.0F`, then directly calls `setSharedFlag(7, false)` (`Player.java:1476-1480`). The latter clear is unconditional within the selected ability-flight/nonpassenger branch, regardless of whether the move ended grounded. These are distinct state writes: glide flag 7 is cleared and fall distance is reset independently.
- This is an actual movement operation, not only a state transition: `Entity.move` collision-resolves the requested displacement and, when the result is nonzero, translates the bounding box and updates entity position (`Entity.java:450-474`). The local-player override delegates to `super.move` before auto-jump bookkeeping (`LocalPlayer.java:851-856`).
- Elytra item is required to establish/maintain valid glide state, not by the ability-flight wrapper itself: `Player.tryToStartFallFlying` requires airborne, not already gliding, not in water, and a fly-enabled chest Elytra before setting flag 7 (`Player.java:1586-1600`); subsequent `updateFallFlying` checks the chest item. The flight wrapper only checks abilities and passenger state.

## Compared slices and dependencies

Compared both sides for `LocalPlayer.aiStep` (flight permission/toggle, Elytra initiation, post-super grounded ability-flight handling), `LivingEntity.aiStep` (fall-flying refresh and travel call order), `LivingEntity.updateFallFlying`, `LivingEntity.travel` (Elytra glide move and server-only ground clear), `Player.tryToStartFallFlying`, `Player.travel` (ability-flight wrapper and post-move writes), `LocalPlayer.move`, and `Entity.move`. All listed method bodies and their containing source files are byte-identical between these two mapped trees. The inventory is limited to this requested operation and its immediate call/state dependencies; it is not a full-pair movement coverage audit.

## Exact source identity and hashes

Canonical read-only trees: `decompiled_minecraft/1.15/mojmap/` and `decompiled_minecraft/1.15.1/mojmap/`. Both ready records say `status=ready`, `mapping=mojmap`, 3,329 source files, produced by Vineflower on Java 25. Both provenance records identify run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`, exact requested releases `1.15` and `1.15.1`, and official Mojang client mappings.

| Release | Client SHA-256 | Official client mappings SHA-256 | Source manifest SHA-256 | Ready record SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|---|
| 1.15 | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` | `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` |
| 1.15.1 | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` | `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` |

The source-manifest entries and SHA-256 file hashes match for each pair member below, confirming the byte-identical source files used for the comparison:

| Relative source file | SHA-256 (both releases) |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
