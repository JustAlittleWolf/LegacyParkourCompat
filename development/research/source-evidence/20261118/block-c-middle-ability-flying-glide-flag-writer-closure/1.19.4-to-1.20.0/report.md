# 1.19.4 to 1.20.0 ability-flight glide-clear source comparison

## Bounded result

No source difference was found for the assigned ordinary dry, airborne, locally controlled, nonpassenger player case with `abilities.flying` active and a usable chest-slot Elytra while fall-flying is active. In both releases, `Player.travel` performs `super.travel` first, then restores vertical velocity from the pre-travel value multiplied by `0.6`, resets fall distance, and directly clears shared flag 7, in that order. The glide clear and fall-distance reset are distinct operations. This is a bounded no-difference result, not pair-complete coverage.

## Exact source identity and integrity

- 1.19.4, Mojmap: `decompiled_minecraft/1.19.4/mojmap/`; ready marker identifies version `1.19.4`, mapping `mojmap`, client SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`. The marker's source-manifest SHA-256 is `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`; its artifact-manifest SHA-256 is `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`.
- 1.20.0, Mojmap: the canonical mapping root is `decompiled_minecraft/1.20/mojmap/`; its hidden `.decompile-provenance.json` declares exact version ID `1.20`, Mojang client mapping and the decompiler pipeline. Provenance file SHA-256: `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. Cached `client.jar`, `client_mappings.txt`, and `version.json` are present under `.minecraft-decompile-cache/1.20/`; each file's actual SHA-256 matched its pin sidecar and the provenance input: respectively `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, and `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`.
- The exact relied `LivingEntity.java` and `Player.java` bytes matched the 1.19.4 source manifest and 1.20.0 provenance source hashes. Player source hashes: 1.19.4 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; 1.20.0 `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac`. LivingEntity source hashes: 1.19.4 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`; 1.20.0 `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f`.

## Compared source slices

- 1.19.4 Mojmap `net/minecraft/world/entity/player/Player.java`, `travel`, lines 1440–1467; 1.20.0 Mojmap `net/minecraft/world/entity/player/Player.java`, `travel`, lines 1446–1473. The ability-flight branch has the same gate, nested call and post-call writes in the same order. Release-specific `onGround`/level accessor naming elsewhere does not change this branch.
- 1.19.4 Mojmap `net/minecraft/world/entity/LivingEntity.java`, `travel`, fall-flying branch and landing clear, lines 2048–2087; 1.20.0 Mojmap same class/member, lines 2068–2107. The elytra movement calculations, `move(MoverType.SELF, ...)`, and server-only landing clear have the same operation order and gates. On the logical client, the latter landing clear is gated off; the player's outer ability-flight branch still performs its own direct flag clear after `super.travel`.
- 1.19.4 Mojmap `net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying` and start/stop methods, lines 1570–1588; 1.20.0 same, lines 1576–1594. Airborne, not already fall-flying, not in water, and no levitation are the same start gates; a usable chest Elytra starts flag 7. Ability flight is not an additional start exclusion. Ordinary effect-specific paths are outside this report's claim.

## Movement meaning

For the assigned reachable case, the locally controlled player's Elytra branch in `LivingEntity.travel` moves first. Returning to `Player.travel`, active ability flight restores the pre-travel vertical velocity scaled by `0.6`, clears fall distance, then clears flag 7 even while airborne. The corresponding movement-state result is the same in 1.19.4 and 1.20.0. This report does not claim behavior for passenger, wet, grounded, nonlocal, or effect-specific cases.
