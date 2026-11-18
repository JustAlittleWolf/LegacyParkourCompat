# 1.15.2 → 1.16 ability-flight Elytra glide clear

## Scope and disposition

Bounded blind Mojmap comparison of the already-active, ordinary dry airborne local player path: `abilities.flying` is lawfully enabled, the player is not a passenger, flag 7 is already set, and a usable Elytra is equipped in the chest slot. The slice follows the client tick into the real travel/move path and the direct post-travel flag-7 clear. Fall distance is recorded as a distinct write. This is not a full pair inventory or pair-freeze audit.

**Disposition: no source difference for this scoped behavior.** In both 1.15.2 and 1.16, player ability flight enters the same `Player.travel` branch. Its `super.travel` runs the active Elytra travel path and performs `move(MoverType.SELF, ...)`; on return, the player override restores the prior vertical velocity scaled by `0.6`, restores `flyingSpeed`, clears `fallDistance`, and unconditionally clears flag 7. The new 1.16 levitation gate in `updateFallFlying` is inert under this ordinary no-effect precondition.

## Trace and comparison

- Client ability-flight toggle: `LocalPlayer.aiStep` tests `abilities.mayfly` before changing `abilities.flying` (1.15.2 `LocalPlayer.java:695–713`; 1.16 `LocalPlayer.java:730–748`). The assigned path takes the already-active flying state; the Elytra auto-start branch requires `!abilities.flying`, so this comparison does not treat auto-start as the witness.
- Tick-to-travel caller: `LivingEntity.aiStep` calls `updateFallFlying()` and then virtual `this.travel(new Vec3(...))` (1.15.2 `LivingEntity.java:2285–2288`; 1.16 `LivingEntity.java:2455–2458`). Dynamic dispatch reaches the `Player.travel` override for the local player.
- The override gate and post-travel sequence are identical: 1.15.2 `Player.java:1463–1475`; 1.16 `Player.java:1395–1407`. Under `abilities.flying && !isPassenger()`, it saves the incoming `deltaMovement.y` and `flyingSpeed`, sets ability flight speed, calls `super.travel`, sets X/Z to the returned movement and Y to the saved value times `0.6`, restores `flyingSpeed`, writes `fallDistance = 0.0F`, then calls `setSharedFlag(7, false)`. There is no creative-mode test in this movement gate; lawful ability authorization is upstream in the active ability state.
- On this dry airborne, already-gliding path, `LivingEntity.travel` reaches its `isFallFlying()` branch and executes the Elytra equations followed by `setDeltaMovement(...multiply(0.99F, 0.98F, 0.99F))` and the real `move(MoverType.SELF, this.getDeltaMovement())`. This occurs before `Player.travel` clears the glide flag: 1.15.2 `LivingEntity.java:1840–1871`; 1.16 `LivingEntity.java:1977–2006`. The fall-flying branch also writes `fallDistance = 1.0F` when incoming Y velocity is greater than `-0.5` in both versions (`1.15.2:1843–1846`, `1.16:1978–1981`); for a player taking the ability-flight override, the later independent `Player.travel` write resets it to zero after the move.
- The server-only `LivingEntity.travel` clear guarded by `onGround && !level.isClientSide` is separate (`1.15.2:1882–1884`; `1.16:2017–2019`). It is false for this local client case and is not the direct clear being traced.
- Adjacent predicate check: `updateFallFlying` retains the shared flag only while airborne/nonpassenger and with an enabled chest Elytra; 1.16 adds `!hasEffect(LEVITATION)` to that gate (`1.15.2 LivingEntity.java:2299–2317`; `1.16:2476–2494`). That predicate is outside the stated ordinary no-effect case. It changes neither the matched `Player.travel` gate nor its post-move clear.

## Exact source identity and integrity

Canonical source roots read-only: `decompiled_minecraft/1.15.2/mojmap/` and `decompiled_minecraft/1.16/mojmap/`. Mapping family: Mojmap for both.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Player.java SHA-256 | LivingEntity.java SHA-256 | LocalPlayer.java SHA-256 |
|---|---|---|---|---|---|---|
| 1.15.2 | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| 1.16 | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |

Both ready markers report `status: ready`, exact version IDs and Mojmap mapping. Each ready marker's `sourceManifestSha256` matches the corresponding `mojmap.sources.sha256` bytes. The six cited source-file hashes match their source-manifest entries and the bytes in the canonical source directories. The 1.15.2 ready marker does not bind a provenance hash field; its provenance file hash above is an observed hash. The 1.16 ready marker binds the provenance hash shown above.

## Coverage and open boundary

Compared the client ability-flight gate, `LivingEntity.aiStep` call order, `Player.travel`, the dry airborne Elytra branch through its `move`, the direct post-move state writes, the separate ground/server clear, and the adjacent `updateFallFlying` precondition. No difference was found in the scoped direct post-move flag clear or fall-distance reset. This does not establish full-pair movement coverage or an intermediate patch boundary; no runtime, build, game or test verification was performed.
