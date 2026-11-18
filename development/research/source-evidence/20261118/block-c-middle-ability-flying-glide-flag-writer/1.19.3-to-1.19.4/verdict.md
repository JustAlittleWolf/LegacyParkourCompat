# Independent source audit verdict: ability-flight glide clear, 1.19.3→1.19.4

**Verdict: ACCEPT — bounded no-difference claim.**

The immutable source report at `c-mid-1193-1194-ability-flight-glide-clear-source/report.md` has SHA-256 `004EBBE5AF04C8BAA8A2030ED06CFB65FB98DACDCA5E264DD0F30ED6FA9840DB`. I froze the exact source identities, manifest and selected-file hashes, and my source trace before opening that report. I then independently retraced the stated player path. The report's no-difference conclusion is supported for its bounded case: ordinary dry airborne local player, nonpassenger, creative ability flight active, default chest Elytra, flag 7 already active.

## Independent trace

- `LocalPlayer.aiStep` in both releases applies ability-flight vertical input before `super.aiStep`: when the local player is the controlled camera and jump/shift yields nonzero input, it adds `0.0, direction * getAbilities().getFlyingSpeed() * 3.0F, 0.0` to delta movement. Its ability-flight toggle precedes the Elytra-start gate; Elytra start is gated by `!abilities.flying`, nonpassenger, and climbable checks. These gates and operations match across this pair for the audited path.
- The no-passenger ability-flight branch in `Player.travel` saves incoming Y velocity, invokes `super.travel`, restores Y as saved-Y `* 0.6`, calls `resetFallDistance()`, then directly calls `setSharedFlag(7, false)`. This order matches in both versions. In 1.19.3 the branch also saves `flyingSpeed`, sets it to `abilities.getFlyingSpeed() * (isSprinting() ? 2 : 1)` around `super.travel`, and restores it; 1.19.4 omits that temporary field change.
- The local living travel gate differs textually: 1.19.3 uses `isEffectiveAi() || isControlledByLocalInstance()`; 1.19.4 uses `isControlledByLocalInstance()`. `LocalPlayer.isEffectiveAi()` is true in both, and this is the ordinary local player path, so the removed disjunct does not exclude this invocation.
- With active flag 7, dry state, and local control, `LivingEntity.travel` takes the fall-flying branch. Both versions run `checkSlowFallDistance`, compute glide velocity from current velocity, look vector, pitch, gravity and the same constants/operation order, scale by `(0.99F, 0.98F, 0.99F)`, and call the actual `move(MoverType.SELF, getDeltaMovement())`. The branch does not read the temporary `flyingSpeed` field or movement input. The server-side flag clear inside this branch is guarded by `onGround && !level.isClientSide`, so it does not clear flag 7 for the stipulated airborne local client. After `super.travel` returns from the real move, `Player.travel` independently resets fall distance and clears flag 7.
- `Entity.move`/`checkFallDamage` updates `fallDistance` from downward displacement (or resets it on ground); the later `Player.travel.resetFallDistance()` is a separate, unconditional operation in the ability-flight branch. Thus the post-move fall-distance reset and direct flag-7 clear are ordered and distinct in both releases. The LocalPlayer `getAbilities().getFlyingSpeed()` term above can affect pre-travel incoming Y and therefore the glide's current-velocity input; it is preserved in both versions. That term is distinct from the `flyingSpeed` field temporarily changed only in 1.19.3, which this glide branch does not read.

## Frozen source identity and hashes

The exact source roots are `decompiled_minecraft/1.19.3/mojmap/` and `decompiled_minecraft/1.19.4/mojmap/`. Their ready markers identify exact releases, `mojmap`, and ready source manifests. Ready-marker manifest hashes equal the actual manifest hashes below. Both provenance files identify the same pinned `decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G` invocation and toolchain (Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Gson 2.14.0, Mapping-IO 0.9.1).

| Identity | 1.19.3 | 1.19.4 |
|---|---|---|
| Ready source-manifest SHA-256 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| `LocalPlayer.java` SHA-256 | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |
| `Player.java` SHA-256 | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| `LivingEntity.java` SHA-256 | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| `Entity.java` SHA-256 | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |

Source references: 1.19.3 `LocalPlayer.java:652–814`, `Player.java:1448–1478`, `LivingEntity.java:2052–2156`, `Entity.java` movement and fall-distance handling; 1.19.4 `LocalPlayer.java:645–795`, `Player.java:1440–1467`, `LivingEntity.java:1983–2087`, `Entity.java` movement and fall-distance handling.

This verdict covers the immutable source report only. It does not establish the boundary within 1.19.3→1.19.4, runtime parity, or other player states.

Signed: `/root/c_mid_1193_1194_ability_flight_glide_clear_audit` (independent exact-source reviewer)
