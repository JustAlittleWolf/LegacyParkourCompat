# Independent audit verdict: 1.16.2 → 1.16.3 ability flight / Elytra clear

**Verdict: ACCEPTED — the source report’s bounded no-difference claim is accurate.** This verdict is bound to the source report SHA-256 `31e530f669845893391b62221a6f77a95428e3805123705e8e67fdea94477f89`. The audit covered a local player in the stated dry, airborne, nonpassenger state with an enabled chest Elytra and lawful creative flight. It does not establish the full pair inventory or the earlier introduction boundary.

## Independent source pins

- **1.16.2 / Mojmap:** `decompiled_minecraft/1.16.2/mojmap.ready.json` identifies version `1.16.2`, mapping `mojmap`, client SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`, and source-manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. The mapping-root provenance records exact run and Mojmap option. The pinned `client_mappings.txt` digest in `artifacts.sha256` is `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`.
- **1.16.3 / Mojmap:** the independently opened hidden root manifest is `decompiled_minecraft/1.16.3/mojmap/.decompile-provenance.json`, SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. Its pinned inputs identify the 1.16.3 client artifact (`client.jar`, SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`), Mojang client mappings (`client.txt`, SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`), and exact metadata input `1.16.3.json` (SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`). The manifest names the decompiler pipeline/tool inputs and lists the source hashes.
- I recomputed the source SHA-256 values on both sides. These are byte-identical across the pair: `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; and `LocalPlayer.java` `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`. The 1.16.3 manifest’s entries match recomputed file hashes.

## Reachable operation and write order

Both releases’ `LocalPlayer.aiStep` reaches `super.aiStep()`; `LivingEntity.aiStep` then calls `updateFallFlying()` followed by virtual `travel(...)`. A local player satisfies the `isControlledByLocalInstance()` arm of `LivingEntity.travel`’s control gate. The dry state excludes the swimming adjustment in `Player.travel`.

In both identical `Player.travel(Vec3)` bodies (1.16.2 and 1.16.3, `net/minecraft/world/entity/player/Player.java:1387–1416`), `abilities.flying && !isPassenger()` saves vertical velocity and flying speed, changes flying speed, and calls `super.travel(vec3)`. In the active fall-flying state, that dispatches to `LivingEntity.travel`; its Elytra branch adjusts velocity and performs the actual `move(MoverType.SELF, ...)` before returning. The creative-flight branch then, in this order, restores vertical velocity to the saved value times `0.6`, restores `flyingSpeed`, writes `fallDistance = 0.0F`, and directly calls `setSharedFlag(7, false)`. Movement statistics follow. Thus the direct flag clear occurs **after** the movement call, and the fall-distance reset is a separate explicit post-travel write; neither is a side effect of the other. The same order exists in both releases.

`tryToStartFallFlying()` and the `startFallFlying()` flag-7 set also match byte-for-byte. A working chest Elytra can support the active glide state in the stated airborne case; the creative-flight post-travel clear is gated by lawful `abilities.flying` and nonpassenger status, not by a newly introduced version-specific branch.

## Signed conclusion

I independently accept the report’s no-difference verdict for this operation and applicability slice. The exact pair and evidence identity above are part of this signed verdict; the report hash remains the acceptance binding. **Signed:** `/root/c_mid_1162_1163_ability_flight_glide_clear_audit`, 2026-11-18.
