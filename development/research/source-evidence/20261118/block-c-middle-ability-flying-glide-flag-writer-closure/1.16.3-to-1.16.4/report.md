# 1.16.3 → 1.16.4: local creative flight over active Elytra

## Scope and conclusion

Trace: ordinary local player, dry and airborne, not a passenger, default Elytra in the chest slot with fall-flying flag 7 active, and lawful ability flight (`abilities.flying` true). Under these conditions the player travel override delegates into the Elytra travel branch, performs its movement, then the player override unconditionally clears flag 7 and independently resets `fallDistance`. **No source difference was found for this operation in 1.16.3 → 1.16.4.**

This report is a bounded source result for the assigned operation, not full pair coverage or proof of runtime parity.

## Exact inputs and byte pins

Both outputs are exact Mojmap (`mojmap`) decompilations. Their complete `.decompile-provenance.json` files are pinned below; each manifest records the full exact-version inputs, tools/runtime and source-file manifest. The relied-on source-file hashes below match the corresponding manifest entries and the actual file bytes.

- 1.16.3 manifest `decompiled_minecraft/1.16.3/mojmap/.decompile-provenance.json`: SHA-256 `513271804EAD6757345D305717E7E0BFC1641434C35A3B254F287BD8FD281498`. Exact client input `https://piston-data.mojang.com/v1/objects/1321521b2caf934f7fc9665aab7e059a7b2bfcdf/client.jar`: `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; official Mojmap input `https://piston-data.mojang.com/v1/objects/faac5028fbca3859db970cc4ca041aeec55f6d9d/client.txt`: `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4 manifest `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json`: SHA-256 `1E04555C915527A398A2C7150679FB24D056F54BD680261E3C1DD55DA821147C`. Exact client input `https://piston-data.mojang.com/v1/objects/1952d94a0784e7abda230aae6a1e8fc0522dba99/client.jar`: `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; official Mojmap input `https://piston-data.mojang.com/v1/objects/0837de813d1a6b67e23da3c520a84e872c8d2f0e/client.txt`: `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.
- Both releases' `net/minecraft/world/entity/player/Player.java`: SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- Both releases' `net/minecraft/world/entity/LivingEntity.java`: SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.

## Paired source trace

In both versions, `LivingEntity.aiStep` damps horizontal input, calls `updateFallFlying()`, then dispatches `travel(...)` (LivingEntity.java:2453–2460). `updateFallFlying` retains active fall-flying only while flag 7 is already set, the entity is airborne and not a passenger, has no Levitation, and the chest slot contains an enabled Elytra (2478–2496). For the stated default chest Elytra, `isFallFlying()` reads flag 7 directly (2891–2893). The dry-state condition skips the water and lava branches in `LivingEntity.travel`.

`Player.travel` checks `abilities.flying && !isPassenger()` (Player.java:1402). With the stipulated lawful creative flight, it saves current vertical velocity and flying speed; raises `flyingSpeed` to the ability speed (doubled if sprinting); then invokes `super.travel(vec3)`. Because flag 7 is active and the player is dry, `LivingEntity.travel` enters fall-flying: it sets `fallDistance = 1.0F` when incoming `deltaMovement.y > -0.5`, applies the Elytra glide math, writes attenuated velocity (`multiply(0.99F, 0.98F, 0.99F)`), and calls `move(MoverType.SELF, getDeltaMovement())` (LivingEntity.java:1979–2008). Its own flag-7 clear is separately gated by `onGround && !level.isClientSide` (2019–2021); it does not apply to the stated airborne local-client case.

After `super.travel` returns, `Player.travel` restores vertical velocity as the saved value times `0.6`, restores prior flying speed, then sets `fallDistance = 0.0F` and directly clears shared flag 7 with `setSharedFlag(7, false)` (Player.java:1407–1411). These are two distinct post-move state writes: the fall-distance reset does not perform the flag clear, and the flag clear is unconditional inside the ability-flight branch (including on the logical client). The cleanup happens even though the delegated Elytra branch just performed a movement. The source therefore predicts that this path ends the tick's fall-flying state and zeroes fall distance after glide movement; it does not imply an additional movement change between the releases.

The paired `Player.travel`, `LivingEntity.travel`, `updateFallFlying`, and flag reader bodies are byte-identical by the file hashes above, and the cited operations retain the same gates and order. No difference claim is warranted for this pair and bounded operation.