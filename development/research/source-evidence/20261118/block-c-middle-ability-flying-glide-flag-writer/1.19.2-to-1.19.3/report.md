# 1.19.2 → 1.19.3 ability-flight glide-clear source trace

## Source identity and readiness

Read-only canonical source trees: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.19.2\mojmap` and `...\1.19.3\mojmap`; namespace is Mojmap. Both `mojmap.ready.json` markers say `status: ready`, identify the exact release and mapping, and bind the `mojmap.sources.sha256` manifest. The provenance records identify a Vineflower 1.12.0 / Java 25 exact run for versions 1.19.2–1.19.4, with Mojmap. Ready-marker `sourceManifestSha256` values match the SHA-256 of the respective source manifest.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.19.2 | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |

Compared source hashes (SHA-256; also present in each release's source manifest):

| Relative Mojmap source | 1.19.2 | 1.19.3 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` |
| `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |

## Bounded source trace

Scenario: local player, creative ability flight already enabled, airborne and dry, not a passenger, ordinary usable Elytra equipped in the chest slot, and fall-flying flag 7 already set. No touchdown is assumed.

- In both releases, the local ability-flight toggle is inside `if (getAbilities().mayfly)`; always-flying game modes force it on, otherwise the jump toggle flips it and sends an abilities update (1.19.2 `LocalPlayer.java:811–824`; 1.19.3 `LocalPlayer.java:732–746`). This anchors the already-enabled flight state to the server-granted `mayfly` permission. `LocalPlayer.aiStep` calls `super.aiStep` in both releases (1.19.2 `LocalPlayer.java:731–898`; 1.19.3 `LocalPlayer.java:652–819`). The shared `LivingEntity.aiStep` travel phase updates fall-flying state and dispatches `this.travel(...)` (1.19.2 `LivingEntity.java:2583–2589`; 1.19.3 `LivingEntity.java:2594–2600`).
- `Player.travel` checks `abilities.flying && !isPassenger()` after its swimming prelude. The specified state selects this branch in both releases. It saves the incoming Y velocity and `flyingSpeed`, sets flying speed from abilities (times 2 when sprinting), calls `super.travel(input)`, retains the returned X/Z velocity but replaces Y with the saved Y times `0.6`, restores flying speed, calls `resetFallDistance()`, then calls `setSharedFlag(7, false)`. The branch and operation order match exactly: 1.19.2 `Player.java:1464–1493`; 1.19.3 `Player.java:1448–1477`.
- During that `super.travel`, the ordinary dry active-glide route in `LivingEntity.travel` reaches `this.move(MoverType.SELF, this.getDeltaMovement())` before returning. Both releases compute the gliding movement and execute that move (1.19.2 `LivingEntity.java:2107–2136`; 1.19.3 `LivingEntity.java:2117–2143`). On the local logical client, the Elytra branch's own `if (onGround && !level.isClientSide)` glide-flag clear is gated off. The later `Player.travel` call is the direct flag-7 clear after the real move.
- `LocalPlayer.move` delegates to `super.move` and then updates auto-jump in both releases (1.19.2 `LocalPlayer.java:978–984`; 1.19.3 `LocalPlayer.java:899–905`), so the superclass travel call is not merely a state-only path.

## Comparison result

**No difference found for the bounded post-move flag-7 clear operation.** With ability flight active and no passenger, both releases complete the superclass travel/move first, restore horizontal/vertical motion state in the same order, reset fall distance, and directly clear shared flag 7. A client that began the tick actively gliding therefore still performs that tick's Elytra move while the glide flag is set, then leaves `Player.travel` with that flag cleared. This is a no-difference coverage result, not a new movement-change finding.

**Separate fall-distance source delta:** in the Elytra branch of `LivingEntity.travel`, 1.19.2 sets `fallDistance = 1.0F` whenever incoming `deltaMovement.y > -0.5` (`LivingEntity.java:2107–2112`). In 1.19.3 it instead calls `Entity.checkSlowFallDistance()` (`LivingEntity.java:2117–2119`); that new helper only reduces the distance to `1.0F` if `deltaMovement.y > -0.5` **and** existing fall distance is already `> 1.0F` (`Entity.java:2147–2151`). Keep that fall-distance operation separate from the post-move flag clear. In the specified ability-flight branch, `Player.travel` subsequently resets fall distance after the superclass call in both releases; the transient Elytra-branch distinction is not evidence of a changed final fall-distance value for the stated no-touchdown tick. This note does not decide other fall-distance or touchdown scenarios.

## Coverage boundary

Covered: local player tick-to-travel dispatch; ability-flight nonpassenger branch; dry active Elytra travel and its real `SELF` move; client/server gate around LivingEntity's in-branch glide clear; direct post-super flag-7 clear; separate fall-distance handling. Excluded: passenger/spectator or non-creative permission setup, starting/stopping glide, water or other travel branches, collision/landing and fall damage outcomes, server movement, and full movement parity. This bounded trace does not establish pair-wide source coverage or acceptance.

