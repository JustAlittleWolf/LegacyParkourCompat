# 1.21.4 to 1.21.5: sweep and Wind Burst receiver movement

## Bounded disposition

**Sweep:** the `LivingEntity.knockback` math and authoritative motion packet path do not change; the invocation gates do. The client-side speculative writer does change: 1.21.4's `Player.attack` applies sweep knockback before its local `hurt` call and without checking that call's result, while 1.21.5 only calls `hurtServer` and applies knockback inside the `ServerLevel` branch. This is a server-authoritative producer/eligibility change with a direct local victim-velocity consequence; it is not a changed knockback formula. For an accepted server hit, the victim receives the same knockback response and encoded state. On the server, the new gate also suppresses this movement response when `hurtServer` returns false; in 1.21.4 the sweep knockback call preceded `hurt` and did not depend on its result. This is a producer-side response gate, not a knockback-math delta.

**Wind Burst:** no receiver movement difference found. The enchantment's post-attack `ExplodeEffect`, server explosion velocity production/collection, explosion packet payload, and client-side velocity addition have the same relevant source behavior in both releases. Its client receiver operation is an independent direct writer: `ClientPacketListener.handleExplosion` adds the optional packet `playerKnockback` to the local player.

No broad movement-pair coverage is claimed. These are bounded source dispositions for the assigned sweep and Wind Burst slices only.

## Evidence and source facts

All source paths below are relative to `decompiled_minecraft/<version>/mojmap/` and line numbers are inclusive.

- `net/minecraft/world/entity/player/Player.java`: 1.21.4 `attack`, 1206–1223, computes sweep damage and unconditionally calls `$$23.knockback(0.4F, sin(yaw), -cos(yaw))` before `hurt`; 1.21.5 `attack`, 1167–1183, has the corresponding call only in `if (level instanceof ServerLevel && $$23.hurtServer(...))`. The response invocation/math is the same; 1.21.5 no longer performs this sweep victim write in the client-side `Player.attack` execution. `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` 1.21.4 389–395 and 1.21.5 392–398 both send the attack packet and then call `Player.attack` on the client outside spectator mode. Damage calculation and hit eligibility are not evaluated here.
- `net/minecraft/world/entity/LivingEntity.java`: 1.21.4 `knockback`, 1502–1516; 1.21.5, 1531–1545. The relevant algorithm is the same: horizontal direction is normalized, prior velocity is halved and offset by the scaled direction, and grounded vertical velocity is capped with `Math.min(0.4, oldY / 2 + strength)`; airborne vertical velocity is retained. A changed upstream call gate can change whether this operation runs, but not its receiver-side math.
- Authoritative sweep state: `LivingEntity.knockback` sets `hasImpulse` in both versions (1.21.4 1502–1506; 1.21.5 1531–1535), while a successful hurt marks `hurtMarked` (`LivingEntity.hurtServer`, 1.21.4 1168–1170; 1.21.5 1179–1181). `net/minecraft/server/level/ServerEntity.java` 1.21.4 216–219 and 1.21.5 217–220 broadcast `ClientboundSetEntityMotionPacket` when `hurtMarked` is set. The packet schema and quantization are byte-for-byte equal: `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` in both versions, 20–32 and 35–72, clamps each component to ±3.9, truncates `(int)(component * 8000.0)`, and decodes by dividing by 8000. `ClientPacketListener.handleSetEntityMotion`, 1.21.4 538–543 and 1.21.5 549–554, passes those values to `Entity.lerpMotion`; `Entity.lerpMotion` is `setDeltaMovement` in both versions (1.21.4 2284–2286; 1.21.5 2252–2254). Thus the authoritative receiving state writer is unchanged.
- Wind Burst definition: `net/minecraft/world/item/enchantment/Enchantments.java` in both versions, WIND_BURST registration at 1156–1193, uses `POST_ATTACK` on `ATTACKER` with the same `ExplodeEffect` settings. `net/minecraft/world/item/enchantment/effects/ExplodeEffect.java` 57–67 is byte-identical and invokes `ServerLevel.explode` with the configured center/radius/effect parameters. This establishes the source path, without reviewing attack eligibility.
- Wind Burst receiver path: `net/minecraft/world/level/ServerExplosion.java` 1.21.4 and 1.21.5 are byte-identical. In `hurtEntities` (191–215), it derives the impulse vector, calls `push`, and records that vector in `hitPlayers` for eligible player entities. `net/minecraft/server/level/ServerLevel.java` 1.21.4 `explode`, 1079–1113; 1.21.5, 1090–1131, sends each nearby player's optional hit vector in `ClientboundExplodePacket`. The packet schema is byte-identical and carries `Optional<Vec3> playerKnockback` (`ClientboundExplodePacket.java` 15–27). The independent client receiver, `ClientPacketListener.handleExplosion`, is at 1225–1242 in 1.21.4 and 1245–1262 in 1.21.5; both perform `playerKnockback().ifPresent(minecraft.player::addDeltaMovement)`. `Entity.addDeltaMovement` adds the vector to current velocity in both releases (1.21.4 3380–3382; 1.21.5 3378–3380). The Wind Burst velocity is therefore authoritative server payload plus unchanged direct client addition; no client explosion simulation is needed for this response.

## Exact source publication identity

Both sides are exact Mojmap source trees prepared by the same recorded batch run: `.\gradlew.bat decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G` (the recorded provenance additionally names staging/cache paths). Tool provenance records Mapping-IO 0.9.1, Tiny Remapper 0.14.1, Vineflower 1.12.0, Gradle 9.7.1, Java 25.0.3+9-LTS, and ASM 9.10.1. The exact Mojang client mapping files are pinned in `artifacts.sha256`: 1.21.4 `client_mappings.txt` SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`; 1.21.5 `client_mappings.txt` SHA-256 `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`. The ready markers identify exact version metadata IDs and client SHA-256 values below. Relevant source-file hashes were checked against each tree's source manifest.

| Version | Ready marker source manifest SHA-256 | Ready marker client SHA-256 | Provenance file SHA-256 | Ready marker file SHA-256 |
|---|---|---|---|---|
| 1.21.4 Mojmap | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` | `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` | `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` |
| 1.21.5 Mojmap | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` | `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e` | `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e` |

Artifact-manifest SHA-256: 1.21.4 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; 1.21.5 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`.

## Relevant source file SHA-256

| Source file | 1.21.4 | 1.21.5 |
|---|---|---|
| `world/entity/player/Player.java` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` |
| `world/entity/LivingEntity.java` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` |
| `client/multiplayer/MultiPlayerGameMode.java` | `3ad84a305c8a7fe2b6f41cc908251774aa4f5c69c2f59cceefd820f0a0156701` | `f046ccbbbbda0dfa23e0d495633eb49e5d8af11afa513ff61b86399e1b60fd4c` |
| `server/level/ServerEntity.java` | `ce0e41ba8c82eee4a39b56eb2b2c0ce05cad58a806161ce7f6e86ece42922c32` | `7bfafc72617c21d3618eeba9f58389d0b653f641d6986a3bc9ff608902b7b4c0` |
| `client/multiplayer/ClientPacketListener.java` | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` |
| `world/entity/Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| `network/protocol/game/ClientboundSetEntityMotionPacket.java` | `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91` | same |
| `world/item/enchantment/Enchantments.java` | `01bdf94faa8089e50a282ec0b50c2b9956ddfb4260ae37dd9ec23e460e9d1bd1` | `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356` |
| `world/item/enchantment/effects/ExplodeEffect.java` | `c62612e19828b0684973b6ffb420cc891dd78526b5f92f0e9d1a71413930e367` | same |
| `world/level/ServerExplosion.java` | `d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71` | same |
| `server/level/ServerLevel.java` | `a333513bebf4caec4489441dded1029330353adaa80e77b9aaf19c7606718b35` | `90d1d545fc3a8fa6a21257b1cdab30c7775e8b392e622dfb7a2ae04cda0b8cda` |
| `network/protocol/game/ClientboundExplodePacket.java` | `54ccf7ddc395f360de1b6f84ed4d0489fdc4a36db3ce83e71d02c31a9138fcc7` | same |

## Limits

This reports only exact 1.21.4 and 1.21.5 Mojmap evidence for the assigned response paths. It does not establish the intermediate patch boundary, broader tick coverage, runtime parity, attack/damage correctness, eligibility behavior beyond the visible call gate, or any product implementation status. No code, tests, source generation, gameplay launch, wiki, product implementation, Git history, or backlog was used.

**TERMINAL: COMPLETED**

