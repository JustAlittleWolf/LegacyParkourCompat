# Independent audit verdict: 1.13 → 1.13.1 Elytra glide / creative flight overlap

**Verdict: ACCEPT — bounded no-difference claim is source-confirmed.** The original report's trace is accurate for the stated dry, airborne, local, nonpassenger player with a usable chest Elytra and glide flag 7 set. Both versions execute the glide move through the base movement method before `PlayerEntity.moveRelative` performs creative-flight post-processing. That post-processing resets `fallDistance` independently, then clears flag 7. I found no behavioral difference in this operation across the pair.

This verdict accepts only that reachability and movement slice. It does not claim full 1.13→1.13.1 pair coverage or establish an intermediate patch boundary. The client flight toggle is reachable when `abilities.canFly` is true: the local input code's double-jump transition has no `isFallFlying()` exclusion; that exclusion gates the separate request to start gliding. For the assigned premise, the existing glide flag is supplied.

## Independent trace

The paired `LocalClientPlayerEntity.tickMovement` excerpts show the creative toggle branch under `abilities.canFly`, with transition guards `!bl && input.jumping && !bl4`, then timer or `!isSwimming()`; these guards permit the stipulated double-tap while gliding. The later `!isFallFlying() && !abilities.flying` check is only on the start-gliding packet path. Exact references: `1.13:756–770`, `1.13.1:760–774`.

Both `LivingEntity` movement dispatches call `flyingTick()` and then virtual `moveRelative(...)` (`1.13:1901–1908`; `1.13.1:1920–1926`). `flyingTick()` validates flag 7, airborne/nonriding state, chest Elytra identity, and `ElytraItem.canFly`; its flag writer is gated by `!world.isClient` (`1.13:1919–1937`; `1.13.1:1938–1956`). Thus it does not preemptively clear the local client's stipulated glide flag.

In both releases, `PlayerEntity.moveRelative` selects its creative branch on `abilities.flying && !isRiding()`, caches vertical velocity and `speedInAir`, sets creative air speed, and calls `super.moveRelative(...)`. The base `LivingEntity.moveRelative` accepts locally controlled movement and enters the glide branch when flag 7 is true and the dry fluid gates pass. It sets `fallDistance = 1.0F` only when `velocityY > -0.5`, applies the same look/velocity math and damping, and calls `move(SELF, velocityX, velocityY, velocityZ)` before returning. `Entity.checkFallDamage` independently updates fall distance from the actual negative `dy`, or resets it on landing. After the superclass call, the player branch restores `speedInAir`, sets `velocityY` to the cached value times `0.6`, sets `fallDistance = 0.0F`, and unconditionally clears flag 7. The clear therefore follows the real glide move; its separate fall-distance reset follows any fall-distance accounting from that move.

Paired references: `LivingEntity.moveRelative` (`1.13:1459–1517`; `1.13.1:1478–1536`); `PlayerEntity.moveRelative` (`1.13:1435–1461`; `1.13.1:1442–1468`); `Entity.checkFallDamage` (`1.13:881–890`; `1.13.1:882–891`); `LivingEntity.isFallFlying` (`1.13:2295–2297`; `1.13.1:2314–2316`); `Entity.setFlag` (`1.13:1838–1849`; `1.13.1:1839–1850`). The flag helper clears only bit 7. The audited relevant condition and statement order match in both releases.

## Pinned evidence

The report reviewed was `c-mid-113-1131-ability-flight-glide-clear-source/report.md`, SHA-256 `D7E93ADF48885C9A742DBCD485800F21F23372BBEC353F03479659D75B0AF6EB` (matches the assigned identity). Canonical source roots are `decompiled_minecraft/1.13/ornithe-feather/` and `decompiled_minecraft/1.13.1/ornithe-feather/`, prepared by `prep-1.13-1.13.1-feather-2026-10-09-r1` with Feather coordinates `net.ornithemc:feather-gen2:1.13+build.2` and `1.13.1+build.2`.

| Version | Provenance SHA-256 | Ready SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.13 | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` |
| 1.13.1 | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |

I recomputed these artifact hashes and verified the cited Java files against each source manifest. The relevant file hashes are recorded in the original source report and are bound by the source manifest hashes above. Sources were read-only. No source generation, network lookup, product change, build, test, or runtime check was performed.

## Reviewed report disposition

All report claims relevant to the assigned slice are confirmed. No correction or narrower wording is needed beyond the report's explicit bounded-scope caveat. Its claims also correctly distinguish the client-side player clear from the server-only on-ground clear in the glide branch.
