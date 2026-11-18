# Independent audit verdict: ability-flight glide clear (1.19.2 → 1.19.3)

## Reviewed identity

Audited the source author's report only after verifying its exact payload SHA-256: `95e1e90d331cffbf0ddcac0e7c02b53784b968fd09670ee8c1f6917fb53bd9e5`, at `c-mid-1192-1193-ability-flight-clear-source/report.md`. The reviewed bounded case is a local, dry, airborne, nonpassenger player with lawful creative ability flight, ordinary enabled chest Elytra, and flag 7 already set; no touchdown is assumed. The claim under audit is that a post-move direct flag-7 clear changed across 1.19.2→1.19.3.

Read-only Mojmap source identity (each source file hash also matches its version's `mojmap.sources.sha256` entry):

| Source | 1.19.2 SHA-256 | 1.19.3 SHA-256 |
|---|---|---|
| `LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| `Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` |
| `LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| `Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |

Canonical provenance and manifest identities:

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.19.2 | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490` |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652` |

The ready markers identify Mojmap and the exact release; their source and artifact manifest hashes match the canonical files. Provenance records pin the Vineflower 1.12.0 / Java 25 Mojmap run for 1.19.2–1.19.4. No source generation or non-source lane evidence was used.

## Independent trace and verdict

**Verdict: accept the author's no-difference result for the bounded post-move flag-7 clear.** I independently followed the local movement path. `LocalPlayer.aiStep` calls `super.aiStep`; `LivingEntity.aiStep` dispatches `this.travel(...)`. When `abilities.flying && !isPassenger()` is true, `Player.travel` in both versions saves incoming Y velocity and flying speed, sets ability flying speed, calls `super.travel`, replaces returned Y with saved Y × `0.6`, restores flying speed, resets fall distance, and then invokes `setSharedFlag(7, false)`. The branch, gates, and ordering are the same in 1.19.2 `Player.java:1464–1493` and 1.19.3 `Player.java:1448–1477`.

The active Elytra route inside the superclass performs the actual displacement before `Player.travel` returns: both `LivingEntity.travel` bodies compute glide velocity, set it, and call `move(MoverType.SELF, getDeltaMovement())` (1.19.2 `LivingEntity.java:2107–2136`; 1.19.3 `LivingEntity.java:2117–2143`). `LocalPlayer.move` delegates to `super.move` in both releases (1.19.2 `LocalPlayer.java:978–984`; 1.19.3 `LocalPlayer.java:899–905`). The superclass Elytra clear is gated by `onGround && !level.isClientSide`, so it does not clear flag 7 on the stated local dry airborne path. The direct `Player.travel` clear therefore follows the real glide move in each version. The fixed Elytra equipment does not alter this `Player.travel` branch predicate.

**Separate fall-distance delta:** 1.19.2's `LivingEntity.travel` sets `fallDistance = 1.0F` whenever the gliding branch sees Y velocity greater than `-0.5F`. In 1.19.3 it calls `Entity.checkSlowFallDistance()`, whose assignment additionally requires current `fallDistance > 1.0F`. That is a separate operation and does not establish a changed flag-clear operation. For the audited no-touchdown ability-flight path, `Player.travel` subsequently calls `resetFallDistance()` after `super.travel` in both releases. This verdict makes no claim about landing/touchdown consequences or other fall-distance paths.

This is a bounded independent source audit, not pair-wide coverage, implementation, or runtime-parity evidence.
