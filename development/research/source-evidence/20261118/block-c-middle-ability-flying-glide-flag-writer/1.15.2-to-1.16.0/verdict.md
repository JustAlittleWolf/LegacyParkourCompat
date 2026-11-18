# Independent audit: 1.15.2 → 1.16 ability-flight Elytra glide clear

## Verdict

**Accepted for the bounded claim: no source difference.** With a local player in ordinary dry airborne travel, `abilities.mayfly` authorizing the ability-flight toggle, `abilities.flying` active, not a passenger, flag 7 already set, and an enabled chest Elytra, both releases run the same relevant sequence. `LivingEntity.travel` executes the fall-flying equations, writes its conditional `fallDistance = 1.0F`, then calls `move(MoverType.SELF, ...)`. Returning to `Player.travel`, ability flight restores the saved vertical component scaled by `0.6`, restores `flyingSpeed`, writes `fallDistance = 0.0F`, and directly clears flag 7 with `setSharedFlag(7, false)`. The latter two are separate state writes, both after `move`. This is a scoped operation verdict, not full pair coverage or runtime parity.

## Independent trace

- In both `LocalPlayer.aiStep` bodies, the toggle is guarded by `abilities.mayfly`; after the jump-trigger sequence and while not swimming, it flips `abilities.flying` and calls `onUpdateAbilities`. The adjacent Elytra auto-start gate explicitly requires `!abilities.flying`, so it is not the path under review. 1.15.2: `LocalPlayer.java:695–719`; 1.16: `LocalPlayer.java:730–755`.
- `LivingEntity.aiStep` calls `updateFallFlying()` immediately before virtual `this.travel(...)` in both releases (1.15.2 `LivingEntity.java:2284–2288`; 1.16 `LivingEntity.java:2452–2458`). With flag 7 already set, no passenger, airborne, enabled Elytra, no Levitation, and client-side level, the existing glide flag remains set into the call. The 1.16-only `!hasEffect(LEVITATION)` condition is true under this no-effect precondition; it does not change this path. 1.15.2 `LivingEntity.java:2299–2318`; 1.16 `LivingEntity.java:2476–2495`.
- The virtual call reaches `Player.travel`. The relevant ability-flight branch has the same gates and statements in both: 1.15.2 `Player.java:1463–1475`; 1.16 `Player.java:1395–1407`. It invokes `super.travel` before doing the post-travel writes; fall-distance reset precedes the direct flag-7 clear.
- `LivingEntity.travel` treats the already-set flag as `isFallFlying()`. Both versions set `fallDistance` to `1.0F` only when incoming Y movement is greater than `-0.5`, execute equivalent glide math, set the damped movement, and call `move(MoverType.SELF, this.getDeltaMovement())` before returning to `Player.travel`. 1.15.2 `LivingEntity.java:1840–1871`; 1.16 `LivingEntity.java:1977–2006`. Thus the conditional glide write is a distinct earlier fall-distance write; the player override's later zero reset still occurs after the actual move.
- The other flag-7 clear in `LivingEntity.travel` is gated by `onGround && !level.isClientSide` (1.15.2 `LivingEntity.java:1882–1884`; 1.16 `LivingEntity.java:2017–2019`). It is not the direct post-move player clear on the local dry airborne client path.

## Frozen source identity and integrity

Read-only canonical roots: `decompiled_minecraft/1.15.2/mojmap/` and `decompiled_minecraft/1.16/mojmap/`. Exact source IDs are 1.15.2 and 1.16; the mapping family is Mojmap on both sides. The ready markers both say `status: ready` and their source-manifest hashes match the local manifest bytes.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.15.2 | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16 | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |

The 1.15.2 ready marker does not bind a provenance digest; its provenance hash above is the observed local file identity. The 1.16 ready marker binds its provenance digest. The six relevant source files match their corresponding source-manifest entries and recomputed file SHA-256:

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.15.2 | `net/minecraft/client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| 1.15.2 | `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| 1.15.2 | `net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.16 | `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| 1.16 | `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| 1.16 | `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |

## Claim audited

Author report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-mid-1152-116-ability-flight-glide-clear-source\report.md`

Author report SHA-256 (verified from its bytes): `9a640c491a5a2dd9918553988302dd4c86625c4933973c22cb11d8ab7060a5e9`.

The report's scoped no-difference conclusion and the separation of post-move flag clear, later fall-distance reset, and earlier conditional Elytra fall-distance write match this independent trace. No tests, runtime, build, source generation, or product changes were performed.
