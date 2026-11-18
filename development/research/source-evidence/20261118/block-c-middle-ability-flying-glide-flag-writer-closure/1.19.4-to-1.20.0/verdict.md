# Independent audit verdict: ability flight glide clear

Verdict: **ACCEPT — bounded no-difference claim supported.**

Reviewed immutable candidate `c-mid-1194-120-ability-flight-glide-clear-source/report.md`, SHA-256 `a59e4aca2e31334df91e271f9eee54ba37cf1f36b30a02abd618bb552d332e62`.

## Source identity frozen before candidate review

- Host: Microsoft Windows 10.0.26200, PowerShell 7.6.9.
- 1.19.4 Mojmap root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.19.4/mojmap/`. The version-root metadata consists of `mojmap.provenance.json` (SHA-256 `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34`), `mojmap.ready.json` (`59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0`), source manifest (`6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`), and artifact manifest (`e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`). The ready metadata binds version ID `1.19.4`, Mojmap, and client hash `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`.
- 1.20.0 Mojmap root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20/mojmap/`. Its hidden `.decompile-provenance.json` SHA-256 is `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`; it binds Mojmap sources and pinned decompiler/toolchain inputs. Local cached `client.jar`, `client_mappings.txt`, and `version.json` matched their pins and manifest input hashes: `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, and `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`.
- Relevant source bytes verified against the published manifests: `Player.java` 1.19.4 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`, 1.20.0 `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac`; `LivingEntity.java` 1.19.4 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`, 1.20.0 `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f`; `LocalPlayer.java` 1.19.4 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`, 1.20.0 `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2`.

The 1.19.4 provenance references a campaign artifact cache path that is not present in this checkout. I verified the published source files against its source manifest and the exact ready/provenance metadata against their recorded hashes; I did not rehash that absent original raw-input cache. This does not affect the reviewed source-byte comparison.

## Audit

The stated reachable case is coherent: `LocalPlayer.aiStep` gives the lawful `mayfly` ability toggle the same gates and writes in both releases (1.19.4 `LocalPlayer.java` lines 712–739; 1.20.0 lines 718–745), then calls `super.aiStep` (lines 795 and 801 respectively). `LivingEntity.aiStep` dispatches to `this.travel(...)` for a nonpassenger (1.19.4 lines 2548–2551; 1.20.0 lines 2570–2573), so the player's override is reached. A running fall-flying state is not cleared by that ability-toggle path before the move.

For the assigned nonpassenger case, `Player.travel` is materially identical: it snapshots current Y velocity, calls `super.travel`, uses the snapshot times `0.6` for the resulting Y velocity, resets fall distance, and then directly clears shared flag 7. See 1.19.4 `Player.java` lines 1455–1464 and 1.20.0 lines 1461–1470. In `LivingEntity.travel`, both releases run the fall-flying math and `move(MoverType.SELF, ...)` first; the fall-flying landing clear is guarded by `onGround && !level.isClientSide`, so it does not clear the airborne logical-client player's flag. See 1.19.4 lines 2048–2087 and 1.20.0 lines 2068–2107. Thus the later direct clear in `Player.travel` is a separate write, and fall-distance reset is a separate preceding operation, in both releases.

The chest Elytra start gate is also unchanged for the cited ordinary case (`Player.tryToStartFallFlying`, 1.19.4 lines 1570–1580; 1.20.0 lines 1576–1586). It requires airborne, not already fall-flying, out of water, and no Levitation; the locally controlled initiation site also has the same flight-off/nonpassenger gates. No contrary order, cast, numeric, or relevant gate change was found.

Scope remains bounded to the candidate's described local dry airborne nonpassenger case with active flight ability and active glide. This verdict does not close full-pair coverage or establish runtime parity.
